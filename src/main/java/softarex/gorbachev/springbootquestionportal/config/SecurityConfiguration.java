package softarex.gorbachev.springbootquestionportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
import softarex.gorbachev.springbootquestionportal.config.security.JWTAuthenticationFilter;
import softarex.gorbachev.springbootquestionportal.config.security.JWTTokenHelper;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.model.PasswordGenerator;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

import static softarex.gorbachev.springbootquestionportal.constant.requ_map.UsersRequestMappingConst.*;

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
    PasswordGenerator passwordGenerator() {
        return new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(
                                        (USERS_CONTROLLER + USERS_LOGIN),
                                        (USERS_CONTROLLER + USERS_REGISTER),
                                        (USERS_CONTROLLER + USERS_CHANGEPASSWORD),
                                        (USERS_CONTROLLER + USERS_RESETPASSWORD)).permitAll()
                                .requestMatchers(HttpMethod.PUT, USERS_CONTROLLER + "/").permitAll() // on the update user
                                .requestMatchers(HttpMethod.GET, USERS_CONTROLLER + "/").permitAll()
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
