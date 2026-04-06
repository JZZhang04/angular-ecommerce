import { environment } from 'src/environments/environment';

export default {
  auth: {
    // Auth0 tenant domain used for local development
    domain: 'dev-duuf521z85fwio7f.us.auth0.com',
    clientId: '0TaZpdH2XrsV69pRZCoxFsHSWTzY9ZPt',
    authorizationParams: {
      // Uses the current site origin so local and deployed environments stay aligned
      redirect_uri: window.location.origin,
      // Must match the API Identifier configured in Auth0
      audience: environment.authAudience,
    },
  },
  httpInterceptor: {
    allowedList: [
      `${environment.apiBaseUrl}/orders/**`,
      `${environment.apiBaseUrl}/checkout/purchase`
    ],
  },
}
