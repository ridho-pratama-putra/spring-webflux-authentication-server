// package com.example.springwebfluxauthenticationserver.filter;

// import java.util.Arrays;
// import java.util.Collection;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.ReactiveAuthenticationManager;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.web.server.WebFilterExchange;
// import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
// import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
// import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
// import org.springframework.web.server.ServerWebExchange;
// import org.springframework.web.server.WebFilterChain;

// import reactor.core.publisher.Mono;

// // this is unused due to cant work properly on part function filter
// public class CustomAuthenticationWebFilter extends AuthenticationWebFilter {
//     Logger logger = LoggerFactory.getLogger(CustomAuthenticationWebFilter.class);

//     ReactiveAuthenticationManager reactiveAuthenticationManager;

//     public CustomAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager, ServerAuthenticationConverter serverAuthenticationConverter) {
//         super(authenticationManager);
//         super.setServerAuthenticationConverter(serverAuthenticationConverter);
//         this.reactiveAuthenticationManager = authenticationManager;
//         logger.info("masuk constructor : JWTFilter");
//     }

//     @Override
//     protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
//         logger.info("onAuthenticationSuccess : CustomAuthenticationWebFilter");
//         return super.onAuthenticationSuccess(authentication, webFilterExchange);
//     }
    
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

//         logger.info("filter : JWTFilter " + ServerWebExchangeMatchers.pathMatchers("/login").matches(exchange) + " " + exchange.getRequest().getMethod() + " :: " + exchange.getRequest().getURI().getPath());
//         if (exchange.getRequest().getMethod() == HttpMethod.POST && exchange.getRequest().getURI().getPath().equals("/login")) {
//             // return super.filter(exchange, chain);
//             this.reactiveAuthenticationManager.authenticate(new Authentication() {

//                 private boolean isAuthenticated;

//                 @Override
//                 public String getName() {
//                     // TODO Auto-generated method stub
//                     // throw new UnsupportedOperationException("Unimplemented method 'getName'");
//                     return "authentication Name";
//                 }

//                 @Override
//                 public Collection<? extends GrantedAuthority> getAuthorities() {
//                     GrantedAuthority grantedAuthority = new GrantedAuthority() {

//                         @Override
//                         public String getAuthority() {
//                             return "BOLEH_LIHAT";
//                         } 
//                     };
//                     return Arrays.asList(grantedAuthority);
//                 }

//                 @Override
//                 public Object getCredentials() {
//                     // TODO Auto-generated method stub
//                     throw new UnsupportedOperationException("Unimplemented method 'getCredentials'");
//                 }

//                 @Override
//                 public Object getDetails() {
//                     return "new getDetails";
//                 }

//                 @Override
//                 public Object getPrincipal() {
//                     // TODO Auto-generated method stub
//                     // throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
//                     return "new getPrincipal";

//                 }

//                 @Override
//                 public boolean isAuthenticated() {
//                     return isAuthenticated;
//                 }

//                 @Override
//                 public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//                     isAuthenticated = isAuthenticated;
//                 }
//             });
//         }

//         return chain.filter(exchange);
//     }
// }