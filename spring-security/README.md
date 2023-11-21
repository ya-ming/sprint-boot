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
* Waht Spring Security can do
    * User name / password authentication
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