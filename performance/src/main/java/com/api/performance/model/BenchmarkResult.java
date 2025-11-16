package com.api.performance.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BenchmarkResult {
    private String url;
    private int totalRequests;
    private int concurrentUsers;
    private long totalTimeMs;
    private long averageResponseTimeMs;
    private int successfulRequests;
    private int failedRequests;
    private Map<Integer, Long> responseTimePercentiles; // Key: percentile (80, 90, 95, 99), Value: response time in ms
    private Map<Long, Integer> responseTimeDistribution; // Key: response time bucket, Value: count
    
    public BenchmarkResult() {
        this.responseTimePercentiles = new ConcurrentHashMap<>();
        this.responseTimeDistribution = new ConcurrentHashMap<>();
    }

    public String getResponseTimeDistributionJson() {
        try {
            return new ObjectMapper().writeValueAsString(responseTimeDistribution);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public int getConcurrentUsers() {
        return concurrentUsers;
    }

    public void setConcurrentUsers(int concurrentUsers) {
        this.concurrentUsers = concurrentUsers;
    }

    public long getTotalTimeMs() {
        return totalTimeMs;
    }

    public void setTotalTimeMs(long totalTimeMs) {
        this.totalTimeMs = totalTimeMs;
    }

    public long getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }

    public void setAverageResponseTimeMs(long averageResponseTimeMs) {
        this.averageResponseTimeMs = averageResponseTimeMs;
    }

    public int getSuccessfulRequests() {
        return successfulRequests;
    }

    public void setSuccessfulRequests(int successfulRequests) {
        this.successfulRequests = successfulRequests;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public void setFailedRequests(int failedRequests) {
        this.failedRequests = failedRequests;
    }

    public Map<Integer, Long> getResponseTimePercentiles() {
        return responseTimePercentiles;
    }

    public void setResponseTimePercentiles(Map<Integer, Long> responseTimePercentiles) {
        this.responseTimePercentiles = responseTimePercentiles;
    }

    public Map<Long, Integer> getResponseTimeDistribution() {
        return responseTimeDistribution;
    }

    public void setResponseTimeDistribution(Map<Long, Integer> responseTimeDistribution) {
        this.responseTimeDistribution = responseTimeDistribution;
    }
}
