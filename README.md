# Full-Stack E-Commerce Application

A full-stack electronics e-commerce platform with an AI-powered shopping assistant, built with Angular and Spring Boot.

**Live Demo:** [Frontend](https://ecommerce-angular-front.netlify.app) | [Backend API](https://ecommerce-backend-latest.onrender.com/api/products)

---

## Tech Stack

**Frontend**
- Angular 14, TypeScript
- Auth0 (`@auth0/auth0-angular`) for authentication
- Stripe integration for checkout

**Backend**
- Spring Boot 3.4.3, Java 17
- Spring Data JPA + Spring Data REST
- MySQL (production) / H2 (testing)
- Anthropic Java SDK (Claude Haiku) for AI chat
- Spring Boot Actuator + Micrometer for metrics

**Infrastructure**
- Netlify (frontend deployment)
- Render (backend deployment)
- GitHub Actions CI (automated build + test)

---

## Features

- Browse 40+ products across 4 categories (Keyboards, Headphones, Mice, Monitor Stands)
- Search and filter by category
- Shopping cart with localStorage persistence
- Secure checkout with shipping/billing address forms
- Auth0 login/logout with user welcome message
- AI shopping assistant chat widget (Claude Haiku, ~2.3s avg response)
- REST API with read-only enforcement on product/category endpoints

---

## Project Structure

```
ecommerce-project/
├── 02-backend/spring-boot-ecommerce/   # Spring Boot REST API
│   ├── src/main/java/com/jzzhang/ecommerce/
│   │   ├── controller/    # CheckoutController, ChatController
│   │   ├── service/       # CheckoutService, AiChatService
│   │   ├── entity/        # JPA entities
│   │   ├── dao/           # Spring Data repositories
│   │   ├── config/        # CORS, Actuator, Anthropic client
│   │   └── filter/        # Security headers filter
│   └── src/test/          # 11 unit tests (JUnit 5 + Mockito)
├── 03-frontend/angular-ecommerce/      # Angular SPA
│   └── src/app/
│       ├── components/    # ProductList, Cart, Checkout, ChatWidget, LoginStatus
│       └── services/      # CartService, ProductService
└── .github/workflows/
    ├── backend-ci.yml     # Java build + test + JaCoCo coverage check
    └── frontend-ci.yml    # Angular build
```

---

## Local Development

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8+
- Anthropic API key

### Backend

```bash
cd 02-backend/spring-boot-ecommerce
export ANTHROPIC_API_KEY=your_key_here
./mvnw spring-boot:run
```

API runs on `http://localhost:8080`

Key endpoints:
| Endpoint | Description |
|---|---|
| `GET /api/products` | List all products |
| `GET /api/products/search/findByCategoryId?id=1` | Filter by category |
| `POST /api/checkout/purchase` | Place an order |
| `POST /api/chat` | AI shopping assistant |
| `GET /actuator/health` | Health check |
| `GET /actuator/metrics/ai.chat.response.time` | AI latency metrics |

### Database Setup

```bash
mysql -u root -p < 01-starter-files/db-scripts/create-user.sql
mysql -u root -p < 01-starter-files/db-scripts/create-products.sql
mysql -u root -p full-stack-ecommerce < 01-starter-files/db-scripts/countries-and-states.sql
```

### Frontend

```bash
cd 03-frontend/angular-ecommerce

# Configure Auth0 (copy template and fill in values)
cp src/app/config/my-app-config.ts.template src/app/config/my-app-config.ts

npm install
ng serve
```

App runs on `http://localhost:4200`

---

## CI/CD

GitHub Actions runs on every push to `02-backend/**` or `03-frontend/**`:

- **Backend CI**: compiles, runs 11 unit tests, enforces JaCoCo 70%+ line coverage, uploads coverage report as artifact
- **Frontend CI**: installs dependencies, runs Angular production build

---

## Testing

```bash
cd 02-backend/spring-boot-ecommerce
./mvnw test
```

11 unit tests covering:
- `CheckoutServiceImpl` — order placement, UUID tracking number generation
- `CheckoutController` — request delegation
- `ChatController` — AI service delegation
- `AiChatServiceImpl` — empty response fallback, exception propagation

JaCoCo HTML report generated at `target/site/jacoco/index.html`

---

## Security

Every API response includes the following HTTP security headers (via `SecurityHeadersFilter`):

- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `Referrer-Policy: strict-origin-when-cross-origin`
- `Content-Security-Policy`
- `Strict-Transport-Security`
- `Permissions-Policy`
