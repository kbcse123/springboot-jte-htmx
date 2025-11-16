@import com.api.performance.model.BenchmarkRequest
@import com.api.performance.model.BenchmarkResult
@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API Benchmark Tool</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- HTMX -->
    <script src="https://unpkg.com/htmx.org@1.9.6"></script>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Custom CSS -->
    <style>
        .form-container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 2rem;
            background-color: #2d2d2d;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .result-container {
            margin-top: 2rem;
            padding: 1.5rem;
            background-color: #2d2d2d;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .chart-container {
            position: relative;
            height: 300px;
            margin: 1rem 0;
        }

        .stat-card {
            background-color: #3a3a3a;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 1rem;
        }

        .stat-value {
            font-size: 1.5rem;
            font-weight: bold;
            color: #00bc8c;
        }

        .stat-label {
            font-size: 0.9rem;
            color: #aaa;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">API Benchmark Tool</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/benchmark">New Test</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container my-4">
        ${content}
    </div>


</body>
</html>
