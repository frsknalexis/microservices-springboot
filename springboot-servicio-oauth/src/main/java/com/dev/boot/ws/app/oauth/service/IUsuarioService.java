package com.dev.boot.ws.app.oauth.service;

import com.dev.boot.ws.app.usuarios.commons.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);
	
	Usuario update(Usuario usuario, Long id);
}
