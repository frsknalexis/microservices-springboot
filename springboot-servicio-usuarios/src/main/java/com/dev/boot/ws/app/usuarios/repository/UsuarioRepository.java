package com.dev.boot.ws.app.usuarios.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.dev.boot.ws.app.usuarios.commons.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	@RestResource(path = "buscar-username")
	Usuario findByUsername(@Param(value = "username") String username);
	
	@Query("select u from Usuario u where u.username=:username")
	Usuario obtenerPorUsername(@Param(value = "username") String username);
}
