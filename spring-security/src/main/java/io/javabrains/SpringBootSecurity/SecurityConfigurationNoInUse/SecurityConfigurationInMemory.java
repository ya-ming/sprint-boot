package io.javabrains.SpringBootSecurity.SecurityConfigurationNoInUse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// @EnableWebSecurity
public class SecurityConfigurationInMemory {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/admin")).hasRole("ADMIN")//.hasAuthority("USER")
                        .requestMatchers(new AntPathRequestMatcher("/user")).hasRole("USER")//.hasAuthority("USER")
                        .requestMatchers(new AntPathRequestMatcher("/home")).permitAll()
                        .anyRequest().authenticated()
                )
                // .httpBasic(withDefaults()) // Http Basic authentication
                // .formLogin((form) -> form  // Form based authentication
                //         .loginPage("/login")
                //         .permitAll()
                // );
                .formLogin(withDefaults());

        // @formatter:on
        return http.build();
    }

    // @formatter:off
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                //.authorities("USER")
                .roles("USER")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                //.authorities("ADMIN")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, user2);
    }
    // @formatter:on

    // @Bean
    // public PasswordEncoder getPasswordEncoder() {
    // return NoOpPasswordEncoder.getInstance();
    // }
}
