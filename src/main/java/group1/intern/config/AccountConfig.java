package group1.intern.config;

import group1.intern.repository.AccountRepository;
import group1.intern.util.exception.NotFoundObjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AccountConfig {
    private final AccountRepository repo;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            if (email != null && !email.isBlank()) {
                return repo.findByEmail(email)
                    .orElseThrow(() -> new NotFoundObjectException("Account not found"));
            }
            throw new NotFoundObjectException("Account not found");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}