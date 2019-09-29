package com.dev.boot.ws.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dev.boot.ws.app.item.model.ProductoDTO;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/api/productos/listar")
	List<ProductoDTO> findAll();
	
	@GetMapping("/api/productos/getOne/{id}")
	ProductoDTO getOneById(@PathVariable(value = "id") Long id);
}
