package io.javabrains.SpringBootSecurity.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    Map<String, CustomUser> users;

    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    public UserRepository() {
        this.users = new HashMap<String, CustomUser>();
        this.users.put("user",  new CustomUser(1, "user", passwordEncoder().encode("user"), "USER"));
        this.users.put("admin", new CustomUser(2, "admin", passwordEncoder().encode("admin"), "USER"));
    }

    public CustomUser findByUsername(String username) {
        return users.get(username);
    }

    public void updatePassword(CustomUser user, String newPassword) {
        user.setPassword(newPassword);
    }
}
