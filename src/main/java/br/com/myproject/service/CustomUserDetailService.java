package br.com.myproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.myproject.model.User;
import br.com.myproject.repository.UserRepository;


/*
 * Classe responsavel pelas regras "ROLES" do sistema,
 * caso seja necessario mais Roles necessario informar 
 * na lista em: List <GrantedAuthority>
 * 
 * */

@Component 
public class CustomUserDetailService implements UserDetailsService{

	private final UserRepository userRepository;	
	
	@Autowired
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		userRepository.findByUsername(username);
		User user = Optional.ofNullable(userRepository.findByUsername(username))
		                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		List <GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		List <GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		
		//alteracao: posteriormente alterar o campo para receber a string da ROLE
		return new org.springframework.security.core.userdetails.User
				       (user.getName(), 
				        user.getPassword(), 
				        user.isAdmin() ? authorityListAdmin : authorityListUser);
	}
	
	
	
}
