package io.javabrains.SpringBootSecurity.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isTruePassword(String username, String oldPassword) {
        System.out.println(oldPassword);

        CustomUser user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("Find User True");
            boolean check = passwordEncoder.matches(oldPassword, user.getPassword());
            if (check) {
                System.out.println("Password verification pass");
                return check;
            } else {
                System.out.println("Password verification failed");
                return false;
            }
        }
        System.out.println("Password verification failed, user not found");
        return false;
    }

    public ChangePasswordResult changeUserPassword(String username, String oldPassword, String newPassword,
            String confirmPassword) {
        ChangePasswordResult result = ChangePasswordResult.OTHER_ERROR;

        CustomUser user = userRepository.findByUsername(username);
        boolean checkPassword = isTruePassword(username, oldPassword);
        if (checkPassword) {
            if (newPassword.equals(confirmPassword)) {
                userRepository.updatePassword(user, passwordEncoder.encode(newPassword));

                result = ChangePasswordResult.SUCCESS;
            } else {
                result = ChangePasswordResult.PASSWORD_MISMATCH;
            }
        } else {
            result = ChangePasswordResult.BAD_CREDENTIAL;
        }

        return result;
    }

    public boolean isUserAllowed() {
        return true;
    }
}
