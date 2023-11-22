package io.javabrains.SpringBootSecurity.UserService;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy   {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // Your custom logic for session expiration

        // Redirect with the session expired parameter
        event.getResponse().sendRedirect("/login?sessionexpired");
    }
}

