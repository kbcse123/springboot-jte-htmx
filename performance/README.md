# API Benchmark Tool

A web-based tool for benchmarking HTTP APIs with support for concurrent users and detailed response time analysis.

## Features

- Test any HTTP/HTTPS endpoint
- Configure number of concurrent users and total requests
- View detailed response time metrics including percentiles (80th, 90th, 95th, 99th)
- Visualize response time distribution with interactive charts
- Responsive design that works on desktop and mobile

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository
2. Build the application:
   ```
   mvn clean package
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```
4. Open your browser and navigate to `http://localhost:8080`

## Usage

1. Enter the target URL of the API endpoint you want to test
2. Set the number of concurrent users (virtual users)
3. Set the total number of requests to send
4. Click "Run Benchmark" to start the test
5. View the results including response times, percentiles, and distribution

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework
- [JTE](https://github.com/casid/jte) - Java Template Engine for server-side rendering
- [Bootstrap 5](https://getbootstrap.com/) - For responsive UI components
- [HTMX](https://htmx.org/) - For dynamic content loading
- [Chart.js](https://www.chartjs.org/) - For data visualization
- [Apache HttpClient](https://hc.apache.org/) - For making HTTP requests

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
