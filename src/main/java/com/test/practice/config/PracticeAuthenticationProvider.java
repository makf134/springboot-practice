package com.test.practice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eazybytes.model.Authority;
import com.test.practice.dao.UserDAO;
import com.test.practice.entity.User;

public class PracticeAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDAO userDAO;
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		List<User> user = userDAO.findByEmail(email);
		if(user.size() > 0) {
			Boolean isPwdMatch = passwordEncoder.matches(pwd, user.get(0).getPassword());
			if(isPwdMatch) {
				
				return new UsernamePasswordAuthenticationToken(email,pwd,);
				
			}else {
				throw new BadCredentialsException("Wrong Credentials");
			}
			
			
		}else {
			throw new BadCredentialsException("No user with this email");
		}
		return null;
	}
	
	
    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
