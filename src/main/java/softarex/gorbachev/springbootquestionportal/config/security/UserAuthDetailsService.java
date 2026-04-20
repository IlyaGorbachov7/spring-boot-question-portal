package softarex.gorbachev.springbootquestionportal.config.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
		return new UserDetailsImpl(userMapper.userToUserDto(user));
	}
}
