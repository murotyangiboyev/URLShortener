# URL Shortener API

A REST API that shortens long URLs, built with Java and Spring Boot.

## Tech Stack
- Java 17
- Spring Boot 3
- PostgreSQL
- Swagger UI

## How to run locally
1. Clone the repo
2. Set up PostgreSQL
3. Run the app
4. Visit http://localhost:8088/swagger-ui/index.html

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/shorten | Shorten a URL |
| GET | /api/url/{code} | Redirect to original URL |
