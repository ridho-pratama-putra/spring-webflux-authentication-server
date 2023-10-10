- walaupun /login dan /logout harus di define secara manual, tapi kenyataan nya /login harus POST dan /logout harus dengan method selain POST. (29 sep '23)
- urutan filter security dulu baru kemudian HTTP method validation
- onAuthenticationSuccess will only called when header authorization have bearer value
- return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

spring:
  security:
    oauth2:
      authorizationserver:
        client:
          client-1:
            registration:
              client-id: "admin-client"
              # the client secret is "secret" (without quotes)
              client-secret: "{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC"
              client-authentication-methods: "client_secret_basic"
              authorization-grant-types: "client_credentials"
              scopes: "user.read,user.write"