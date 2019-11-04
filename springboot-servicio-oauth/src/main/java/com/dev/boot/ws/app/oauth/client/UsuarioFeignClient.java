package com.dev.boot.ws.app.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.boot.ws.app.usuarios.commons.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/search/buscar-username")
	Usuario findByUsername(@RequestParam String username);
	
	@PutMapping("/usuarios/{id}")
	Usuario update(@RequestBody Usuario usuario, @PathVariable(value = "id") Long id);
}
