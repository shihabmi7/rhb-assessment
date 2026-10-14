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
| PATCH | /api/customers/{id} | partial update |
| DELETE | /api/customers/{id} | |
| GET | /api/orders/with-customer | JOIN of orders and customers, optional `city` |
| POST | /api/orders | |
| PUT | /api/orders/{id}/status | body `{"status": "SHIPPED"}` |
| GET | /api/external/google | calls https://www.google.com |

Requests and responses of every controller are logged by `RequestResponseLoggingAspect` (AspectJ).

Postman collection: `postman/rhb-assessment.postman_collection.json`

## Example responses

Saved as examples in the Postman collection too.

### Get a customer
`GET /api/customers/1`

```json
{
  "id": 1,
  "name": "Alice Tan",
  "email": "alice@example.com",
  "city": "Kuala Lumpur"
}
```

### Search with pagination
`GET /api/customers/search?name=a&city=Penang&page=0&size=5&sort=name,asc`

```json
{
  "content": [
    {
      "id": 5,
      "name": "Ethan Ng",
      "email": "ethan@example.com",
      "city": "Penang"
    }
  ],
  "page": 0,
  "size": 5,
  "totalElements": 1,
  "totalPages": 1
}
```

### Create a customer (201)
`POST /api/customers`

```json
{
  "id": 6,
  "name": "Farah Hassan",
  "email": "farah@example.com",
  "city": "Ipoh"
}
```

### Validation error (400)
`POST /api/customers`

```json
{
  "timestamp": "2026-10-14T04:00:14.410577Z",
  "status": 400,
  "message": "Validation failed",
  "details": [
    "email: must be a well-formed email address",
    "name: must not be blank"
  ]
}
```

### Orders joined with customers
`GET /api/orders/with-customer?city=Kuala Lumpur`

```json
[
  {
    "orderId": 1,
    "customerName": "Alice Tan",
    "city": "Kuala Lumpur",
    "product": "Laptop",
    "amount": 3499.0,
    "status": "PAID"
  },
  {
    "orderId": 2,
    "customerName": "Alice Tan",
    "city": "Kuala Lumpur",
    "product": "Mouse",
    "amount": 59.9,
    "status": "SHIPPED"
  },
  {
    "orderId": 4,
    "customerName": "Charlie Wong",
    "city": "Kuala Lumpur",
    "product": "Monitor",
    "amount": 899.0,
    "status": "PAID"
  }
]
```

### External API call
`GET /api/external/google`

```json
{
  "url": "https://www.google.com",
  "status": 200,
  "contentLength": 85104
}
```

