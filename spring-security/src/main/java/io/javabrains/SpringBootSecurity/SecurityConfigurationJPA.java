package io.javabrains.SpringBootSecurity;

import io.javabrains.SpringBootSecurity.Filter.TenantFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationRepository;
import io.javabrains.SpringBootSecurity.UserService.CustomDaoAuthenticationProvider;
import io.javabrains.SpringBootSecurity.UserService.CustomSessionInformationExpiredStrategy;
import io.javabrains.SpringBootSecurity.UserService.CustomUserDetailsService;
import io.javabrains.SpringBootSecurity.UserService.MyAuthenticationSuccessHandler;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationJPA {
    // @Autowired to mark a dependency which Spring is going to resolve and inject
    // @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    private TenantFilter tenantFilter;

    public SecurityConfigurationJPA(
            AuthenticationSuccessHandler authenticationSuccessHandler,
            SessionInformationExpiredStrategy sessionInformationExpiredStrategy,
            TenantFilter tenantFilter) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
        this.tenantFilter = tenantFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(new AntPathRequestMatcher("/admin")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/user")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(new AntPathRequestMatcher("/home")).permitAll()
                                .anyRequest().authenticated()
                )
                // .formLogin(withDefaults());
                // .formLogin(withDefaults()).successHandler(new MyAuthenticationSuccessHandler())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(authenticationSuccessHandler)
                        .failureUrl("/login?error"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())  // permitAll() is needed to redirect user to /login with ?logout
                .addFilterBefore(tenantFilter, AuthorizationFilter.class);
                // .logout();//.logoutUrl("/logout");//.logoutSuccessHandler(new CustomLogoutSuccessHandler()).invalidateHttpSession(true);
                // .logout(logout -> logout
                //         // .logoutUrl("/logout")
                //         .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                //         .invalidateHttpSession(true))
                // ;

        http.sessionManagement(management -> management
            // .sessionAuthenticationErrorUrl("/login?sessionautherror=true")
            .maximumSessions(5)
            .expiredSessionStrategy(sessionInformationExpiredStrategy)); // this doesn't work
            // .expiredUrl("/login?sessionexpired"));



            //             .and().sessionAuthenticationErrorUrl("/login?sessionautherror=true")
            // .and().sessionManagement().invalidSessionUrl("/login?invalidsession=true"));

        // @formatter:on
        return http.build();
    }
}
