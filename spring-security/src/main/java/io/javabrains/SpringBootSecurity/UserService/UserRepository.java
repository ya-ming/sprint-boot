package io.javabrains.SpringBootSecurity.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    Map<String, CustomUser> users;

    private PasswordEncoder passwordEncoder;

    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        this.users = new HashMap<String, CustomUser>();
        this.users.put("user",  new CustomUser(1, "user", passwordEncoder.encode("user"), "USER"));
        this.users.put("admin", new CustomUser(2, "admin", passwordEncoder.encode("admin"), "USER"));
    }

    public CustomUser findByUsername(String username) {
        return users.get(username);
    }

    public void updatePassword(CustomUser user, String newPassword) {
        user.setPassword(newPassword);
    }
}
