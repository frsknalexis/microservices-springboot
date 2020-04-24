package com.dev.boot.ws.app.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.boot.ws.app.oauth.client.UsuarioFeignClient;
import com.dev.boot.ws.app.usuarios.commons.entity.Usuario;

import brave.Tracer;
import feign.FeignException;

@Service("usuarioService")
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private static final Logger logger =LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient usuarioFeignClient;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
			Usuario usuario = usuarioFeignClient.findByUsername(username);
			
			List<GrantedAuthority> authorities = usuario.getRoles()
												.stream()
												.map(role -> new SimpleGrantedAuthority(role.getNombre()))
												.peek(authority -> logger.info("Role: " + authority.getAuthority()))
												.collect(Collectors.toList());
			logger.info("Usuario autenticado: " + username);
			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), 
					true, true, true, authorities);
		} catch(FeignException e) {
			String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
			logger.error(error);
			tracer.currentSpan().tag("error.mensaje", error + ": " + e.getMessage());
			throw new UsernameNotFoundException("User not found");
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioFeignClient.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return usuarioFeignClient.update(usuario, id);
	}
}
