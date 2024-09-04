package in.ashokit.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import in.ashokit.entity.Users;
import in.ashokit.repo.UserRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Users> byEmail = userRepo.findByEmail(email);
		
		return byEmail.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
	}

}
