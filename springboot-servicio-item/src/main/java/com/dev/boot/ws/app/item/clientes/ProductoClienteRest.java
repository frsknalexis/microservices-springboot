package com.dev.boot.ws.app.item.clientes;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dev.boot.ws.app.commons.model.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/api/productos/listar")
	List<Producto> findAll();
	
	@GetMapping("/api/productos/getOne/{id}")
	Producto getOneById(@PathVariable(value = "id") Long id);
	
	@PostMapping("/api/productos/saveProducto")
	Producto saveProducto(@Valid @RequestBody Producto producto);
	
	@PutMapping("/api/productos/editar/{id}")
	Producto editarProducto(@Valid @RequestBody Producto producto, @PathVariable(value = "id") Long id);
	
	@DeleteMapping("/api/productos/eliminar/{id}")
	void eliminar(@PathVariable(value = "id") Long id);
}
