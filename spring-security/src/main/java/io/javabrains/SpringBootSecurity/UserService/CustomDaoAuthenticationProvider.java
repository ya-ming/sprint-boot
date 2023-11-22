package io.javabrains.SpringBootSecurity.UserService;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider{
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public CustomDaoAuthenticationProvider(CustomUserDetailsService customUserDetailsService,
    UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.setUserDetailsService(customUserDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }
}
