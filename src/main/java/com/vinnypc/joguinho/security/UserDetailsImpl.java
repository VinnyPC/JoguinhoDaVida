package com.vinnypc.joguinho.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.model.enums.ProfileEnum;

import lombok.Getter;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String password;
	//private List<GrantedAuthority> authorities;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String userName, String password, Set<ProfileEnum> profileEnums) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorities = profileEnums.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}
	
	public boolean hasRole(ProfileEnum profileEnum) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
	}

}
