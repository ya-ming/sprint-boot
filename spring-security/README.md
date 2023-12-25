# Spring Security

* Application security framework
    * Login and logout functionality
    * Allow/block access to URLs to logged in users
    * Allow/block access to URLs to logged in users AND with certain roles
* Handles common vulnerabilities
    * Session fixation
    * Clickjacking
    * Click site request forgery
* Widely adopted
    * Popular target for hackers, vulnerabilities get the most attention and quick response
* What Spring Security can do
    * Username / password authentication
    * SSO / Okta / LDAP
    * App level Authorization
    * Intra App Authorization like OAuth
    * Microservice security (using tokens, JWT)
    * Method level security
* 5 Core Concepts in Spring Security
    * Authentication
    * Authorization
    * Principal
        * Currently logged in user
    * Granted Authority
        * Operations allowed
        * Fine-grained
    * Roles
        * Group of authorities
        * Coarse-grained
* Filters
    * Intercept every request
        * Request -> Filters -> Servlets
        * [Spring’s DefaultSecurityFilterChain](image.png)
```
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Trying to match request against DefaultSecurityFilterChain [RequestMatcher=any request, Filters=[org.springframework.security.web.session.DisableEncodeUrlFilter@79b4cff, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@58ac0823, org.springframework.security.web.context.SecurityContextPersistenceFilter@1736273c, org.springframework.security.web.header.HeaderWriterFilter@780a91d0, org.springframework.security.web.csrf.CsrfFilter@79e16dd9, org.springframework.security.web.authentication.logout.LogoutFilter@3fc5d397, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@4d770bcd, org.springframework.security.web.session.ConcurrentSessionFilter@7d82ca56, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@36eb8e07, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@39ac8c0c, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@2d705998, org.springframework.security.web.session.SessionManagementFilter@3387ab0, org.springframework.security.web.access.ExceptionTranslationFilter@25e353dc, io.javabrains.SpringBootSecurity.Filter.TenantFilter@15c487a8, org.springframework.security.web.access.intercept.AuthorizationFilter@3a4cb483]] (1/1)
[https-jsse-nio-8443-exec-3] DEBUG o.s.security.web.FilterChainProxy - Securing GET /login
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking DisableEncodeUrlFilter (1/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking WebAsyncManagerIntegrationFilter (2/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking SecurityContextPersistenceFilter (3/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.c.HttpSessionSecurityContextRepository - Did not find SecurityContext in HttpSession 8E91A7A7996A99647C02B7A7CC25C83B using the SPRING_SECURITY_CONTEXT session attribute
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.c.HttpSessionSecurityContextRepository - Created SecurityContextImpl [Null authentication]
[https-jsse-nio-8443-exec-3] DEBUG o.s.s.w.c.SecurityContextPersistenceFilter - Set SecurityContextHolder to empty SecurityContext
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking HeaderWriterFilter (4/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking CsrfFilter (5/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.csrf.CsrfFilter - Did not protect against CSRF since request did not match CsrfNotRequired [TRACE, HEAD, GET, OPTIONS]
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking LogoutFilter (6/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.a.logout.LogoutFilter - Did not match request to Ant [pattern='/logout', POST]
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking UsernamePasswordAuthenticationFilter (7/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.a.UsernamePasswordAuthenticationFilter - Did not match request to Ant [pattern='/login', POST]
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking ConcurrentSessionFilter (8/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking RequestCacheAwareFilter (9/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.s.HttpSessionRequestCache - Did not match request /login to the saved one DefaultSavedRequest [https://localhost:8443/css/main.css]
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking SecurityContextHolderAwareRequestFilter (10/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking AnonymousAuthenticationFilter (11/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.a.AnonymousAuthenticationFilter - Set SecurityContextHolder to AnonymousAuthenticationToken [Principal=anonymousUser, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=8E91A7A7996A99647C02B7A7CC25C83B], Granted Authorities=[ROLE_ANONYMOUS]]
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking SessionManagementFilter (12/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking ExceptionTranslationFilter (13/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking TenantFilter (14/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.security.web.FilterChainProxy - Invoking AuthorizationFilter (15/15)
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.a.i.RequestMatcherDelegatingAuthorizationManager - Authorizing SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@1bbf088c]
[https-jsse-nio-8443-exec-3] TRACE o.s.s.w.a.i.RequestMatcherDelegatingAuthorizationManager - Checking authorization on SecurityContextHolderAwareRequestWrapper[ org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@1bbf088c] using org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$$Lambda$740/0x000001add46cb6d0@273a1d87
```

* Spring Security default behavior
    * Adds mandatory authentication for URLs
    * Adds login form
    * Handles login error
    * Creates a user and sets a default password
* AuthenticationManager
    * authenticate()
* ProviderManager implements AuthenticationManager
* AuthenticationManagerBuilder
* AuthenticationProvider
    * Input - Credentials
    * authenticate()
    * Output - Principal
    * supports()
* UserDetailsService
    * Input - Username
    * loadUserByUserName()
    * Output - User Object
* HttpSecurity!
* Authentication Object
    * The Authentication Object as a result of a successful authentication is store in Security Context in the thread local object for use in authorization
* SessionManagementFilter
* HTTPS
    * Generate self-signed certificate using `keytool`, passphase `springboot`
        * `keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650`
    * Configure HTTPS in `application.properties`
* Login
* Logout
    * Ensure that your security configuration allows unauthenticated access to the "/login" endpoint. If access to "/login" is restricted, the redirect after logout might not work as expected.
* Session management
* Configuration
    * Read/Write path of config.json `spring-security\build\resources\main\config.json`
* Internationalization
  * LocaleResolver
  * LocaleChangeInterceptor
* Certificate Verification
  * Using bouncycastle
* Use chain of cert
  * Should follow https://jamielinux.com/docs/openssl-certificate-authority/index.html
  * Generate a Root Certificate
```shell
# Generate a private key for the root CA
openssl genpkey -algorithm RSA -out root-key.pem

# Generate a self-signed root certificate
openssl req -x509 -new -key root-key.pem -out root-cert.pem
```
	* Generate an Intermediate Certificate
```shell
# Generate a private key for the intermediate CA
openssl genpkey -algorithm RSA -out intermediate-key.pem

# Generate a CSR (Certificate Signing Request) for the intermediate CA
openssl req -new -key intermediate-key.pem -out intermediate-csr.pem

# Sign the intermediate CSR with the root CA to create the intermediate certificate
openssl x509 -req -in intermediate-csr.pem -CA root-cert.pem -CAkey root-key.pem -CAcreateserial -out intermediate-cert.pem

openssl x509 -in intermediate-cert.pem -text -noout
```
	* Generate an End-Entity (Leaf) Certificate
```shell
# Generate a private key for the end-entity
openssl genpkey -algorithm RSA -out entity-key.pem

# Generate a CSR for the end-entity with IP address in the SAN extension
openssl req -new -key entity-key.pem -out entity-csr.pem -config san.cnf -extensions 'v3_req'

openssl req -config openssl_intermediate.cnf -key ~/myCA/intermediateCA/private/www.example.com.key.pem -new -sha256 -out ~/myCA/intermediateCA/csr/www.example.com.csr.pem -batch

# Inspect csr
openssl req -noout -text -in entity-csr.pem

# Sign the entity CSR with the intermediate CA to create the end-entity certificate
# Use -extfile san.cnf -extensions 'v3_req' to add SANs to cert
openssl x509 -req -in entity-csr.pem -CA intermediate-cert.pem -CAkey intermediate-key.pem -CAcreateserial -out entity-cert.pem  -extfile san.cnf -extensions 'v3_req'

# Inspect the cert
openssl x509 -in entity-cert.pem -text -noout
```

	* Verify the Certificate Chain
```shell
openssl verify -CAfile root-cert.pem intermediate-cert.pem entity-cert.pem
```

	* Package the Chain
```shell
# Package the Chain
cat entity-cert.pem intermediate-cert.pem root-cert.pem > certificate-chain.pem
# or powershell
Get-Content entity-cert.pem, intermediate-cert.pem, root-cert.pem | Set-Content certificate-chain.pem
Get-Content leaf.pem, intermediate.pem, root.pem | Set-Content certificate-chain.pem
```
	* Convert pem to crt
```shell
openssl x509 -outform der -in leaf.pem -out leaf.crt
openssl x509 -outform der -in root.pem -out root.crt
openssl x509 -outform der -in intermediate.pem -out intermediate.crt

openssl verify -CAfile root.pem intermediate.pem leaf.pem

Get-Content leaf.pem, intermediate.pem, root.pem | Set-Content certificate-chain.pem
```

Update SAN
```shell
cd src\main\resources\myCA
# generate CSR
openssl req -config .\openssl.cnf -key intermediateCA/private/www.example.com.key.pem -new -sha256 -out intermediateCA/csr/www.example.com.csr.pem
# inspect the CSR
openssl req -noout -text -in intermediateCA\csr\www.example.com.csr.pem
# sign the cert
openssl ca -config .\openssl.cnf -extensions server_cert -days 375 -notext -md sha256 -in intermediateCA/csr/www.example.com.csr.pem -out intermediateCA/certs/www.example.com.cert.pem
# inspect the cert
openssl x509 -in intermediateCA/certs/www.example.com.cert.pem -text -noout
# repackage the chain cert
#   www.example.com.cert.pem
#   ca-chain.cert.pem
# into
#   chain.cert.pem
```

Trust server cert when making request as a HTTP Client
```shell
# On Windows
keytool -import -alias server-alias -keystore %JAVA_HOME%\lib\security\cacerts -file AbsolutePathTo\intermediateCA\certs\chain.cert.pem
# Default password is `changeit`

# delete existing cert
keytool -delete -alias server-alias -keystore %JAVA_HOME%\lib\security\cacerts
```
* Repost a post to another endpoint
  * Model attribute should match the name specified in Thymeleaf form
    ```html
    <form th:action="@{/repost}" method="post" th:object="${postValue}">
    ```
    ```java
        @GetMapping("/repost")
        public String repostPage(Model model) {
            // Add the model attribute for the form
            model.addAttribute("postValue", new PostValue());
            return "repost";
        }
    ```
  * Disable csrf and authentication for rest API
    ```java
        .csrf()
        .ignoringAntMatchers("/post-rest")
        .and()
    ```