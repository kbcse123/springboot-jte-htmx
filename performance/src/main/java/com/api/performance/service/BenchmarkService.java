package com.api.performance.service;

import com.api.performance.model.BenchmarkRequest;
import com.api.performance.model.BenchmarkResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BenchmarkService {

    private ExecutorService executorService;

    //@Autowired
    public BenchmarkService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public BenchmarkResult runBenchmark(BenchmarkRequest request) throws InterruptedException {
        final String url = request.getUrl();
        final int totalRequests = request.getTotalRequests();
        final int concurrentUsers = request.getConcurrentUsers();
        
        BenchmarkResult result = new BenchmarkResult();
        result.setUrl(url);
        result.setTotalRequests(totalRequests);
        result.setConcurrentUsers(concurrentUsers);
        
        // Create a thread-safe list to store response times
        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());
        
        // Create a countdown latch to wait for all requests to complete
        CountDownLatch latch = new CountDownLatch(totalRequests);
        
        // Create a thread pool for concurrent requests
        //ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();//newFixedThreadPool(concurrentUsers);
        
        // Record start time
        long startTime = System.currentTimeMillis();
        
        // Submit all requests to the thread pool
        for (int i = 0; i < totalRequests; i++) {
            executorService.submit(() -> {
                try (CloseableHttpClient httpClient = createHttpClient()) {
                    HttpGet httpGet = new HttpGet(url);
                    long requestStart = System.currentTimeMillis();
                    
                    try {
                        int statusCode = httpClient.execute(httpGet, response -> {
                            long requestEnd = System.currentTimeMillis();
                            long responseTime = requestEnd - requestStart;
                            responseTimes.add(responseTime);
                            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                                result.setSuccessfulRequests(result.getSuccessfulRequests() + 1);
                            } else {
                                result.setFailedRequests(result.getFailedRequests() + 1);
                            }
                            return response.getStatusLine().getStatusCode();
                        });
                    } catch (IOException e) {
                        result.setFailedRequests(result.getFailedRequests() + 1);
                    }
                } catch (Exception e) {
                    result.setFailedRequests(result.getFailedRequests() + 1);
                } finally {
                   latch.countDown();
                }
            });
        }
        // Wait for all requests to complete
       latch.await(2, TimeUnit.MINUTES);

        // Record end time and calculate total duration
        long endTime = System.currentTimeMillis();
        result.setTotalTimeMs(endTime - startTime);
        
        // Calculate average response time
        if (!responseTimes.isEmpty()) {
            double avgResponseTime = responseTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
            result.setAverageResponseTimeMs((long) avgResponseTime);
            
            // Calculate percentiles
            calculatePercentiles(responseTimes, result);
            
            // Prepare response time distribution
            prepareResponseTimeDistribution(responseTimes, result);
        }
        return result;
    }
    
    private CloseableHttpClient createHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(10)
            .setConnectionRequestTimeout(30)
            .build();
            
        return HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
    }
    
    private void calculatePercentiles(List<Long> responseTimes, BenchmarkResult result) {
        // Sort the response times
        List<Long> sortedTimes = responseTimes.stream()
            .sorted()
            .collect(Collectors.toList());
        
        // Calculate percentiles
        calculatePercentile(sortedTimes, 80, result);
        calculatePercentile(sortedTimes, 90, result);
        calculatePercentile(sortedTimes, 95, result);
        calculatePercentile(sortedTimes, 99, result);
    }
    
    private void calculatePercentile(List<Long> sortedTimes, int percentile, BenchmarkResult result) {
        if (sortedTimes.isEmpty()) return;
        
        double index = (percentile / 100.0) * (sortedTimes.size() - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);
        
        if (lower == upper) {
            result.getResponseTimePercentiles().put(percentile, sortedTimes.get(lower));
        } else {
            long lowerValue = sortedTimes.get(lower);
            long upperValue = sortedTimes.get(upper);
            long value = (long) (lowerValue + (upperValue - lowerValue) * (index - lower));
            result.getResponseTimePercentiles().put(percentile, value);
        }
    }
    
    private void prepareResponseTimeDistribution(List<Long> responseTimes, BenchmarkResult result) {
        // Group response times into buckets (e.g., 0-100ms, 100-200ms, etc.)
        Map<Long, Integer> distribution = new TreeMap<>();
        
        for (Long time : responseTimes) {
            // Round to nearest 100ms for bucketing
            long bucket = (time / 100) * 100;
            distribution.put(bucket, distribution.getOrDefault(bucket, 0) + 1);
        }
        
        result.setResponseTimeDistribution(distribution);
    }
}
