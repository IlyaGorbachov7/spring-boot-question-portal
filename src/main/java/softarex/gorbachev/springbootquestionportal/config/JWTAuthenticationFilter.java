package softarex.gorbachev.springbootquestionportal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JWTTokenHelper jwtTokenHelper;

    private PasswordEncoder passwordEncoder;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService, JWTTokenHelper jwtTokenHelper, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = jwtTokenHelper.getToken(request);

        if (null != authToken) {
            String userName = jwtTokenHelper.getUsernameFromToken(authToken);

            if (null != userName) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                // нигде не проверяется пророль из uerDetail и
                if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                    String password = jwtTokenHelper.getPasswordFromToken(authToken);
                    if (password != null && passwordEncoder.matches(password, userDetails.getPassword())) {

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }

        }

        filterChain.doFilter(request, response);
    }

}