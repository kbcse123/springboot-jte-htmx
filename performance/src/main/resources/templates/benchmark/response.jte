@import com.api.performance.model.BenchmarkResult
@param BenchmarkResult result

    <style>
        .stat-card {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
        }
        .stat-value {
            font-size: 1.5rem;
            font-weight: bold;
            color: #0d6efd;
        }
        .stat-label {
            color: #6c757d;
            font-size: 0.9rem;
        }
        .chart-container {
            background: #fff;
            border-radius: 8px;
            padding: 20px;
            margin: 20px 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .chart-wrapper {
            position: relative;
            height: 400px;
            margin-bottom: 20px;
        }
    </style>

<div class="container py-4">
    <div class="result-container">
        <h3 class="mb-4">Benchmark Results</h3>
        
        <!-- Stats Overview -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="stat-card">
                    <div class="stat-value">${result.getUrl()}</div>
                    <div class="stat-label">Target URL</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getConcurrentUsers()}</div>
                    <div class="stat-label">Concurrent Users</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getTotalRequests()}</div>
                    <div class="stat-label">Total Requests</div>
                </div>
            </div>
        </div>

        <!-- Performance Metrics -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getSuccessfulRequests()}</div>
                    <div class="stat-label">Successful</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getFailedRequests()}</div>
                    <div class="stat-label">Failed</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getAverageResponseTimeMs()} ms</div>
                    <div class="stat-label">Avg. Response Time</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${String.format("%.2f", result.getTotalTimeMs() / 1000.0)} s</div>
                    <div class="stat-label">Total Duration</div>
                </div>
            </div>
        </div>

        <!-- Response Time Distribution Chart -->
        <div class="chart-container">
            <h5>Response Time Distribution</h5>
            <div class="chart-wrapper">
                <canvas id="distributionChart"></canvas>
            </div>
        </div>

        <!-- Percentiles Chart -->
        <div class="chart-container">
            <h5>Response Time Percentiles</h5>
            <div class="chart-wrapper">
                <canvas id="percentilesChart"></canvas>
            </div>
        </div>

        <!-- Percentile Stats -->
        <div class="row mt-4">
            <%--<div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getResponseTimePercentiles().get(50)} ms</div>
                    <div class="stat-label">50th Percentile (Median)</div>
                </div>
            </div>--%>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getResponseTimePercentiles().get(90)} ms</div>
                    <div class="stat-label">90th Percentile</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getResponseTimePercentiles().get(95)} ms</div>
                    <div class="stat-label">95th Percentile</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card">
                    <div class="stat-value">${result.getResponseTimePercentiles().get(99)} ms</div>
                    <div class="stat-label">99th Percentile</div>
                </div>
            </div>
        </div>
<%--
        <div class="d-grid gap-2 mt-4">
            <a href="/benchmark" class="btn btn-outline-primary">Run Another Test</a>
        </div>
        --%>
    </div>
</div>

<script>
    // Wait for the DOM to be fully loaded
    document.addEventListener('DOMContentLoaded', function() {
        // Prepare data for the chart
        const labels = [];
        const data = [];
        const colors = [];

        // Get the response time distribution from the model
        const distribution = ${result.getResponseTimeDistributionJson()};

            // Sort the keys (response time buckets)
            const sortedBuckets = Object.keys(distribution).sort((a, b) => parseInt(a) - parseInt(b));

            // Prepare data for the chart
            sortedBuckets.forEach(bucket => {
                const time = parseInt(bucket);
                const count = distribution[bucket];

                // Create a nice label (e.g., "0-100ms", "100-200ms")
                const nextBucket = time + 100;
                labels.push(time - nextBucket 'ms`);
                data.push(count);

                // Generate a color based on the response time (red for slower times)
                const hue = Math.max(0, 120 - (time / 10)); // Green to red gradient
                colors.push(`hsl(hue, 70%, 50%)`);
            });

            // Create the chart
            const ctx = document.getElementById('responseTimeChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Number of Requests',
                        data: data,
                        backgroundColor: colors,
                        borderColor: 'rgba(0, 0, 0, 0.1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Number of Requests'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Response Time Range (ms)'
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return `context.parsed.y requests ((context.parsed.y / ${result.getTotalRequests()} * 100).toFixed(1)%)`;
                                }
                            }
                        }
                    }
                }
            });
        });
</script>
