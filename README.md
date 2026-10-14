# rhb-assessment

Spring Boot 3 / Java 17 / Maven e-commerce API (customers and orders) on an in-memory H2 database.

## Run
    ./mvnw spring-boot:run

App: http://localhost:8080, H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:rhbdb`, user `sa`, no password).

## Test
    ./mvnw test

## Endpoints
| Method | Path | Notes |
|---|---|---|
| GET | /api/customers/{id} | |
| GET | /api/customers/search | `name`, `city`, `page`, `size`, `sort` |
| POST | /api/customers | |
| PUT | /api/customers/{id} | full replace |
| PATCH | /api/customers/{id} | partial update |
| DELETE | /api/customers/{id} | |
| HEAD | /api/customers/{id} | existence check |
| GET | /api/orders/with-customer | JOIN of orders and customers, optional `city` |
| POST | /api/orders | |
| PATCH | /api/orders/{id}/status | `status` query param |
| GET | /api/external/google | calls https://www.google.com |

Requests and responses of every controller are logged by `RequestResponseLoggingAspect` (AspectJ).

Postman collection: `postman/rhb-assessment.postman_collection.json`
