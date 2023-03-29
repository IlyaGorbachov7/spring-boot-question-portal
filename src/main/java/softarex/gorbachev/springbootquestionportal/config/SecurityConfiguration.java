package softarex.gorbachev.springbootquestionportal.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration implements UserDetailsService {

    private final UserRepository userRepository;

    private final JWTTokenHelper jwtTokenHelper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/api/v1/login", "/api/v1/register").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/").permitAll() // on the update user
                                .anyRequest().authenticated().and()
                                .addFilterBefore(new JWTAuthenticationFilter(this, jwtTokenHelper, passwordEncoder()),
                                        UsernamePasswordAuthenticationFilter.class));

        http.csrf().disable().cors().and().headers().frameOptions().disable();
        return http.build();
    }

    /**
     * Login call-back method
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
        return new UserDetailsImpl(user);
    }
}
