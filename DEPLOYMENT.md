# Deployment Guide

This guide covers the full deployment flow for this project:

- local verification with Docker
- backend deployment with Docker
- frontend deployment to Netlify
- Auth0 production configuration

## Project layout

- Frontend: `03-frontend/angular-ecommerce`
- Backend: `02-backend/spring-boot-ecommerce`
- Local compose file: `docker-compose.yml`
- Local environment template: `.env.example`

## 1. Prerequisites

Before deploying, make sure the following are available:

- Docker Desktop
- A Netlify account
- An Auth0 tenant and application
- A public host for the backend API
- A public hostname for the backend, or a platform-provided URL

Recommended examples used below:

- Frontend URL: `https://your-store.netlify.app`
- Backend URL: `https://api.yourdomain.com`
- Auth0 domain: `dev-xxxx.us.auth0.com`

## 2. Local backend verification with Docker

From the repository root:

```bash
cp .env.example .env
docker compose up --build
```

Update `.env` before or after the copy as needed:

```env
MYSQL_DATABASE=full-stack-ecommerce
MYSQL_USER=ecommerceapp
MYSQL_PASSWORD=ecommerceapp
MYSQL_ROOT_PASSWORD=rootpassword

ALLOWED_ORIGINS=http://localhost:4200
ANTHROPIC_API_KEY=your-real-anthropic-key
OKTA_OAUTH2_ISSUER=https://dev-xxxx.us.auth0.com/
OKTA_OAUTH2_CLIENT_ID=YOUR_AUTH0_CLIENT_ID
OKTA_OAUTH2_AUDIENCE=http://localhost:8080
```

Verify the backend is running:

```bash
curl http://localhost:8080/api/products
```

If this returns JSON, the backend and MySQL are connected correctly.

## 3. Frontend local verification

During local verification, keep the frontend pointed at the local backend.

Check:

- `03-frontend/angular-ecommerce/src/environments/environment.ts`
- `03-frontend/angular-ecommerce/src/app/config/my-app-config.ts`

For local development, the effective values should be:

- API base URL: `http://localhost:8080/api`
- Auth0 redirect URI: `http://localhost:4200`
- Auth0 audience: `http://localhost:8080`

Then run:

```bash
cd 03-frontend/angular-ecommerce
npm install
npm start
```

Verify these flows:

- product list loads
- category navigation works
- checkout API calls succeed
- chat widget can reach `/api/chat`
- protected order endpoints can request tokens

## 4. Prepare production frontend config

Update `03-frontend/angular-ecommerce/src/environments/environment.prod.ts`:

```ts
export const environment = {
  production: true,
  apiBaseUrl: 'https://api.yourdomain.com/api'
};
```

Create or update `03-frontend/angular-ecommerce/src/app/config/my-app-config.ts` using the production values from `my-app-config.ts.template`:

```ts
import { environment } from 'src/environments/environment';

export default {
  auth: {
    domain: 'dev-xxxx.us.auth0.com',
    clientId: 'YOUR_AUTH0_CLIENT_ID',
    authorizationParams: {
      redirect_uri: 'https://your-store.netlify.app',
      audience: 'https://api.yourdomain.com',
    },
  },
  httpInterceptor: {
    allowedList: [
      `${environment.apiBaseUrl}/orders/**`,
      `${environment.apiBaseUrl}/checkout/purchase`
    ],
  },
}
```

Important:

- `redirect_uri` must be the Netlify frontend URL
- `audience` must exactly match the API Identifier in Auth0
- `environment.prod.ts` must point to the public backend URL, including `/api`

## 5. Prepare production backend config

When deploying the backend container, set these environment variables:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://YOUR_DB_HOST:3306/full-stack-ecommerce?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=ecommerceapp
SPRING_DATASOURCE_PASSWORD=your-db-password

ALLOWED_ORIGINS=https://your-store.netlify.app
ANTHROPIC_API_KEY=your-real-anthropic-key
OKTA_OAUTH2_ISSUER=https://dev-xxxx.us.auth0.com/
OKTA_OAUTH2_CLIENT_ID=YOUR_AUTH0_CLIENT_ID
OKTA_OAUTH2_AUDIENCE=https://api.yourdomain.com
```

Important:

- `ALLOWED_ORIGINS` must be the Netlify frontend URL
- `OKTA_OAUTH2_ISSUER` must end with `/`
- `OKTA_OAUTH2_AUDIENCE` must match the frontend `audience`

## 6. Build and run the backend image

From the backend directory:

```bash
cd 02-backend/spring-boot-ecommerce
docker build -t ecommerce-backend .
```

Run the container by passing production environment variables from your platform or host.

Example `docker run`:

```bash
docker run --name ecommerce-backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL='jdbc:mysql://YOUR_DB_HOST:3306/full-stack-ecommerce?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC' \
  -e SPRING_DATASOURCE_USERNAME='ecommerceapp' \
  -e SPRING_DATASOURCE_PASSWORD='your-db-password' \
  -e ALLOWED_ORIGINS='https://your-store.netlify.app' \
  -e ANTHROPIC_API_KEY='your-real-anthropic-key' \
  -e OKTA_OAUTH2_ISSUER='https://dev-xxxx.us.auth0.com/' \
  -e OKTA_OAUTH2_CLIENT_ID='YOUR_AUTH0_CLIENT_ID' \
  -e OKTA_OAUTH2_AUDIENCE='https://api.yourdomain.com' \
  ecommerce-backend
```

After deployment, verify the public API:

```bash
curl https://api.yourdomain.com/api/products
```

## 7. Configure Auth0 for production

In Auth0, update the application and API settings.

Application settings:

- Allowed Callback URLs: `https://your-store.netlify.app`
- Allowed Logout URLs: `https://your-store.netlify.app`
- Allowed Web Origins: `https://your-store.netlify.app`

API settings:

- Identifier: `https://api.yourdomain.com`

Keep these values aligned:

- Frontend `authorizationParams.audience`
- Backend `OKTA_OAUTH2_AUDIENCE`
- Auth0 API Identifier

## 8. Deploy the frontend to Netlify

From the frontend directory:

```bash
cd 03-frontend/angular-ecommerce
npm install
npm run build
```

The build output will be in:

```text
03-frontend/angular-ecommerce/dist/angular-ecommerce
```

This project already includes a Netlify `_redirects` file in the build output, so Angular client-side routing can work after deployment.

In Netlify, configure:

- Base directory: `03-frontend/angular-ecommerce`
- Build command: `npm run build`
- Publish directory: `dist/angular-ecommerce`

After deployment, verify:

- home page loads
- deep links such as `/products` or `/checkout` do not 404 on refresh
- frontend requests go to `https://api.yourdomain.com/api`

## 9. Final production checklist

- `environment.prod.ts` uses the real backend URL
- `my-app-config.ts` uses the real Netlify URL and Auth0 audience
- backend `ALLOWED_ORIGINS` is the Netlify frontend URL
- backend `OKTA_OAUTH2_*` values match Auth0
- Auth0 callback and web origin settings use the Netlify URL
- backend public `/api/products` endpoint returns JSON
- Netlify build output includes `_redirects`

## 10. Common issues

### CORS errors in the browser

Check:

- backend `ALLOWED_ORIGINS`
- Netlify frontend URL spelling
- whether frontend is calling the correct backend URL

### Login works locally but fails in production

Check:

- Auth0 Allowed Callback URLs
- Auth0 Allowed Web Origins
- frontend `redirect_uri`
- frontend and backend `audience` values

### Netlify routes return 404 on refresh

Check that the deployed output includes `_redirects`.

### MySQL changes do not show up locally

If you changed initialization SQL, reset local volumes:

```bash
docker compose down -v
docker compose up --build
```
