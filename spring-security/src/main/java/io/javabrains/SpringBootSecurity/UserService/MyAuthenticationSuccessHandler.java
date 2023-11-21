package io.javabrains.SpringBootSecurity.UserService;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationRepository;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ConfigurationRepository configurationRepository;

    public MyAuthenticationSuccessHandler(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication)
            throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            request.getSession(false).setMaxInactiveInterval(30);
        } else {
            int sessionTimeoutTimer = configurationRepository.getConfigurationEntity().getConfiguredValue();
            if (sessionTimeoutTimer != 0) {
                System.out.println("Auth success, set session timeout timer to " + sessionTimeoutTimer);
                request.getSession(false)
                        .setMaxInactiveInterval(sessionTimeoutTimer);
            } else {
                System.out.println("Auth success, set session timeout timer to default 60");
                request.getSession(false).setMaxInactiveInterval(60);
            }

        }
        // Your login success url goes here, currently login success url="/"
        response.sendRedirect(request.getContextPath());
    }
}
