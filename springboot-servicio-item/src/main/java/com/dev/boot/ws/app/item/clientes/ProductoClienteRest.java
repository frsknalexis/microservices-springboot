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

import com.dev.boot.ws.app.item.model.ProductoDTO;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/api/productos/listar")
	List<ProductoDTO> findAll();
	
	@GetMapping("/api/productos/getOne/{id}")
	ProductoDTO getOneById(@PathVariable(value = "id") Long id);
	
	@PostMapping("/api/productos/saveProducto")
	ProductoDTO saveProducto(@Valid @RequestBody ProductoDTO productoDTO);
	
	@PutMapping("/api/productos/editar/{id}")
	ProductoDTO editarProducto(@Valid @RequestBody ProductoDTO productoDTO, @PathVariable(value = "id") Long id);
	
	@DeleteMapping("/api/productos/eliminar/{id}")
	void eliminar(@PathVariable(value = "id") Long id);
}
