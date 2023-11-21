package io.javabrains.SpringBootSecurity.UserService;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import javax.servlet.ServletException;
import java.io.IOException;

public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy   {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // Your custom logic for session expiration

        // Redirect with the session expired parameter
        event.getResponse().sendRedirect("/login?sessionexpired");
    }
}

