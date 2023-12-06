package io.javabrains.SpringBootSecurity.Internationalization.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        CookieLocaleResolver resolver = new CookieLocaleResolver();
//        resolver.setDefaultLocale(Locale.US); // Set a default locale if needed
//        resolver.setCookieName("yourLocaleCookie"); // Set a custom cookie name
//        resolver.setCookieMaxAge(365 * 24 * 60 * 60); // Set the cookie expiration time
//        return resolver;
//    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US); // Set a default locale if needed
        return resolver;
    }
}

