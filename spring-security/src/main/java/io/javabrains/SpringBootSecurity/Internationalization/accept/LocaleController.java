package io.javabrains.SpringBootSecurity.Internationalization.accept;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class LocaleController {

    private final LocaleResolver localeResolver;

    public LocaleController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @GetMapping("/setLocale")
    public String setLocale(HttpServletRequest request, HttpServletResponse response) {
        // The locale will be automatically resolved based on the "Accept-Language" header
        Locale locale = localeResolver.resolveLocale(request);

        // Set the locale in the resolver
        response.setLocale(locale);

        // Set a cookie to remember the locale
        Cookie cookie = new Cookie(CookieLocaleResolver.DEFAULT_COOKIE_NAME, locale.toString());
        cookie.setMaxAge(365 * 24 * 60 * 60); // Set the cookie expiration time
        response.addCookie(cookie);

        return "redirect:/"; // Redirect to the home page or another page
    }
}

