package com.dev.boot.ws.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.dev.boot.ws.app.oauth.service.IUsuarioService;
import com.dev.boot.ws.app.usuarios.commons.entity.Usuario;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService iUsuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> info = new HashMap<String, Object>();
		Usuario usuario = iUsuarioService.findByUsername(authentication.getName());
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("correo", usuario.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
