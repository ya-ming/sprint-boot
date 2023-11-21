package io.javabrains.SpringBootSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationRepository;
import io.javabrains.SpringBootSecurity.UserService.CustomSessionInformationExpiredStrategy;
import io.javabrains.SpringBootSecurity.UserService.CustomUserDetailsService;
import io.javabrains.SpringBootSecurity.UserService.MyAuthenticationSuccessHandler;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationJPA {
    // Inject ConfigurationRepository
    private final ConfigurationRepository configurationRepository;

    public SecurityConfigurationJPA(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler(configurationRepository);
    }
    // end of injection

    @Bean
    public SessionInformationExpiredStrategy customSessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
                        .successHandler(myAuthenticationSuccessHandler())
                        .failureUrl("/login?error"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());  // permitAll() is needed to redirect user to /login with ?logout
                // .logout();//.logoutUrl("/logout");//.logoutSuccessHandler(new CustomLogoutSuccessHandler()).invalidateHttpSession(true);
                // .logout(logout -> logout
                //         // .logoutUrl("/logout")
                //         .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                //         .invalidateHttpSession(true))
                // ;

        http.sessionManagement(management -> management
            // .sessionAuthenticationErrorUrl("/login?sessionautherror=true")
            .maximumSessions(5)
            .expiredSessionStrategy(customSessionInformationExpiredStrategy())); // this doesn't work
            // .expiredUrl("/login?sessionexpired"));



            //             .and().sessionAuthenticationErrorUrl("/login?sessionautherror=true")
            // .and().sessionManagement().invalidSessionUrl("/login?invalidsession=true"));

        // @formatter:on
        return http.build();
    }

    // @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    // @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // need to remove @Bean from the above functions, otherwise got error
    // org.springframework.beans.factory.BeanCurrentlyInCreationException: Error
    // creating bean with name 'securityConfigurationJPA': Requested bean is
    // currently in creation: Is there an unresolvable circular reference?
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    // Redirect HTTP to HTTPS
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}
