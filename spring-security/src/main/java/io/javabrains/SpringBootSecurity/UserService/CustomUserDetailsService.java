package io.javabrains.SpringBootSecurity.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Simulated database or repository to fetch user details
    // Replace this with your actual data access mechanism
    private final UserRepository userRepository;

    // public CustomUserDetailsService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    public CustomUserDetailsService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from the database based on the username
        CustomUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
                //  .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create a UserDetails object with the fetched user details
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
