package io.javabrains.SpringBootSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationRepository;
import io.javabrains.SpringBootSecurity.UserService.CustomSessionInformationExpiredStrategy;
import io.javabrains.SpringBootSecurity.UserService.CustomUserDetailsService;
import io.javabrains.SpringBootSecurity.UserService.MyAuthenticationSuccessHandler;
import io.javabrains.SpringBootSecurity.UserService.UserRepository;

@Configuration
public class AppConfig {
    @Autowired
    private ConfigurationRepository configurationRepository;
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler(configurationRepository);
    }
    // end of injection

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(new CustomUserDetailsService(new UserRepository(passwordEncoder)));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Redirect HTTP to HTTPS
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}
