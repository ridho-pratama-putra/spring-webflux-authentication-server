spring webflux as auth server.



# walaupun /login dan /logout harus di define secara manual, tapi kenyataan nya /login harus POST dan /logout harus dengan method selain POST. (29 sep '23)
# urutan filter security dulu baru kemudian HTTP method validation
# onAuthenticationSuccess will only called when header authorization have bearer value
# return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));