package com.api.performance.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BenchmarkRequest {
    @NotBlank(message = "URL is required")
    private String url="http://localhost:8085/contact";
    
    @NotNull(message = "Number of concurrent users is required")
    @Min(value = 1, message = "Must have at least 1 concurrent user")
    private Integer concurrentUsers=100;
    
    @NotNull(message = "Total requests is required")
    @Min(value = 1, message = "Must have at least 1 request")
    private Integer totalRequests=1000;

    public BenchmarkRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getConcurrentUsers() {
        return concurrentUsers;
    }

    public void setConcurrentUsers(Integer concurrentUsers) {
        this.concurrentUsers = concurrentUsers;
    }

    public Integer getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Integer totalRequests) {
        this.totalRequests = totalRequests;
    }
}
