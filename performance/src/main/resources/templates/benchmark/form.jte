@import com.api.performance.model.BenchmarkRequest
@param BenchmarkRequest benchmarkRequest

@template.layout.page( content = @`
    <div class="form-container">
        <h2 class="mb-4">API Benchmark Tool</h2>
        
        <form hx-post="/benchmark" hx-target="#benchmark-results" hx-swap="innerHTML" >
            <div class="mb-3">
                <label for="url" class="form-label">Target URL</label>
                <input type="url" class="form-control" id="url" name="url" required
                       placeholder="https://example.com/api/endpoint" value="${benchmarkRequest.getUrl()}">
                <div class="form-text">Enter the full URL of the API endpoint to test.</div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="concurrentUsers" class="form-label">Concurrent Users</label>
                    <input type="number" class="form-control" id="concurrentUsers" name="concurrentUsers" 
                           min="1" max="1000" required value="${benchmarkRequest.getConcurrentUsers()}">
                    <div class="form-text">Number of concurrent users to simulate.</div>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="totalRequests" class="form-label">Total Requests</label>
                    <input type="number" class="form-control" id="totalRequests" name="totalRequests" 
                           min="1" required value="${benchmarkRequest.getTotalRequests()}">
                    <div class="form-text">Total number of requests to send.</div>
                </div>
            </div>
            
            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary btn-lg" >
                    <span class="spinner-border spinner-border-sm d-none" role="status" aria-hidden="true" id="spinner"></span>
                    Run Benchmark
                </button>
            </div>
        </form>
        
        <div id="benchmark-results" class="mt-4"></div>
    </div>
    <script>
        document.body.addEventListener('htmx:beforeRequest', function(evt) {
            if (evt.detail.elt.matches('form')) {
                document.getElementById('spinner').classList.remove('d-none');
            }
        });
        
        document.body.addEventListener('htmx:afterRequest', function(evt) {
            if (evt.detail.elt.matches('form')) {
                document.getElementById('spinner').classList.add('d-none');
            }
        });
    </script>
`)

