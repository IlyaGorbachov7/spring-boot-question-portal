package softarex.gorbachev.springbootquestionportal.config.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Base64;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenHelper {

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long expiresIn;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String password) {
        String usernameAndPassword = email + ":" + password;
        Date now = new Date();
        Date dateExpire = generateExpirationDate(now);
        return Jwts.builder()
                .setSubject(usernameAndPassword)
                .setIssuedAt(new Date())
                .setExpiration(dateExpire)
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getUsernameAndPassword(token)[0];
    }

    public String getPasswordFromToken(String token) {
        return getUsernameAndPassword(token)[1];
    }

    private Date generateExpirationDate(Date now) {
        return new Date(now.getTime() + 99999999999L);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationException("", e) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return claims;
    }

    private String[] getUsernameAndPassword(String token) {
        String usernameAndPassword = "";
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            usernameAndPassword = claims.getSubject();
            String username = usernameAndPassword.substring(0, usernameAndPassword.indexOf(':'));
            String password = usernameAndPassword.substring(usernameAndPassword.indexOf(':') + 1,
                    usernameAndPassword.length());
            return new String[]{
                    username, password
            };
        } catch (Exception e) {
            throw new AuthenticationException("JWT token is invalid", e) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };

        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token)
        );
    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }


    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }


    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null) {
            authHeader = authHeader.trim();
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }

        return null;
    }

    public String getToken(String tokenB) {
        if (tokenB.startsWith("Bearer ")) {
            return tokenB.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}