package softarex.gorbachev.springbootquestionportal.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import softarex.gorbachev.springbootquestionportal.config.security.JWTTokenHelper;

import java.util.Objects;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private UserDetailsService userDetailsService;

    private JWTTokenHelper jwtTokenHelper;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins(CROSS_ORIGIN_LOCALHOST3000).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.enableSimpleBroker("/topic", "/user");
        messageBrokerRegistry.setUserDestinationPrefix("/user");
    }

    /**
     * The reason I couldn't solve the handshake issure was the presence of an annotation EnableWebSecurity -- remove it
     *
     * @see <a href="https://dzone.com/articles/build-a-secure-app-using-spring-boot-and-websocket">topic</a>
     * @see <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-authentication-token-based">Official doc</a>
     * @see <a href="https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/websocket.html">WebSocket Securtty</a>
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
                    String authToken = jwtTokenHelper.getToken(Objects.requireNonNull(
                            accessor.getNativeHeader("Authorization")).get(0));
                    if (null != authToken) {
                        String userName = jwtTokenHelper.getUsernameFromToken(authToken);
                        if (null != userName) {
                            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());
                            accessor.setUser(authentication);
                        }
                    }
                }
                return message;
            }
        });
    }
}
