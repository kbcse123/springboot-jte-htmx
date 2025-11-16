package com.api.performance.controller;

import com.api.performance.model.BenchmarkRequest;
import com.api.performance.model.BenchmarkResult;
import com.api.performance.service.BenchmarkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BenchmarkController {

    private final BenchmarkService benchmarkService;

    public BenchmarkController(BenchmarkService benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @GetMapping("/benchmark")
    public String showForm(BenchmarkRequest benchmarkRequest) {
        return "benchmark/form";
    }

    @PostMapping("/benchmark")
    public String runBenchmark(
            @Valid BenchmarkRequest benchmarkRequest,
            BindingResult bindingResult,
            Model model) throws Exception {
        
        if (bindingResult.hasErrors()) {
            return "benchmark/form";
        }
        
        BenchmarkResult result = benchmarkService.runBenchmark(benchmarkRequest);
        model.addAttribute("result", result);
        
        return "benchmark/result";
    }
    @PostMapping("/execute")
    public String executeBenchmark(
            @Valid BenchmarkRequest benchmarkRequest,
            BindingResult bindingResult,
            Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            return "benchmark/form";
        }

        BenchmarkResult result = benchmarkService.runBenchmark(benchmarkRequest);
        model.addAttribute("result", result);

        return "benchmark/response";
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Working...");
    }
}
