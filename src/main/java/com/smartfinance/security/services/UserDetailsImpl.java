package com.smartfinance.security.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartfinance.entity.Usuario;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;

	private String nombre;

	@JsonIgnore
	private String password;

	public UserDetailsImpl(Long id, String email, String nombre, String password) {
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.password = password;
	}

	public static UserDetailsImpl build(Usuario usuario) {
		return new UserDetailsImpl(usuario.getId(), usuario.getEmail(), usuario.getNombre(), usuario.getPassword());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null; // No se usaran roles
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return null; // No se usaran nombres de usuario
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
}
