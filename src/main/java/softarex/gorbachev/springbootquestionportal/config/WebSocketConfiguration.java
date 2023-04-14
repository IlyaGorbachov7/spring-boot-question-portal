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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import softarex.gorbachev.springbootquestionportal.config.security.JWTTokenHelper;

import java.util.ArrayList;
import java.util.Map;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private UserDetailsService userDetailsService;

    private JWTTokenHelper jwtTokenHelper;

    private PasswordEncoder passwordEncoder;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins(CROSS_ORIGIN_LOCALHOST3000).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.enableSimpleBroker("/topic"/*, "/user"*/);
//        messageBrokerRegistry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authToken = jwtTokenHelper.getToken(accessor.getNativeHeader("Authorization").get(0));

                    if (null != authToken) {
                        String userName = jwtTokenHelper.getUsernameFromToken(authToken);

                        if (null != userName) {

                            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                            // нигде не проверяется пророль из uerDetail и
                            if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                                String password = jwtTokenHelper.getPasswordFromToken(authToken);
                                if (password != null && passwordEncoder.matches(password, userDetails.getPassword())) {
                                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    accessor.setUser(authentication);
                                }
                            }

                        }
                    }
                }
                return message;
            }
        });
    }
}
