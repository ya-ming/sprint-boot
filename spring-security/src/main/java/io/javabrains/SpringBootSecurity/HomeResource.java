package io.javabrains.SpringBootSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.javabrains.SpringBootSecurity.UserService.ChangePasswordResult;
import io.javabrains.SpringBootSecurity.UserService.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
public class HomeResource {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/home")
    public String home(Model model) {
        ChangePasswordResult result = ChangePasswordResult.SUCCESS;
        model.addAttribute("result", result);
        return "home";
    }

    @GetMapping("/changePassword")
    public String getChangePassword() {
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String postChangePassword(Authentication authentication, String oldPassword, String newPassword,
            String confirmPassword, Model model) {
        String username = authentication.getName();
        System.out.println("Change password for user: " + username);
        System.out.println("Old password: " + oldPassword);
        System.out.println("New password: " + newPassword);
        System.out.println("Con password: " + confirmPassword);

        ChangePasswordResult result = userService.changeUserPassword(username, oldPassword, newPassword,
                confirmPassword);

        // Add the result to the model to be displayed in the view
        model.addAttribute("result", result);

        // Redirect to the change password page
        return "changePassword";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout"; // You can redirect wherever you want, but generally it's a good practice to
//                                         // show login screen again.
//    }
}
