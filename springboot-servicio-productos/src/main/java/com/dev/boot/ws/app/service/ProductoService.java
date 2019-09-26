package com.dev.boot.ws.app.service;

import java.util.List;

import com.dev.boot.ws.app.entity.Producto;

public interface ProductoService {

	List<Producto> findAll();
	
	Producto findById(Long id);
	
	Producto saveProducto(Producto producto);
}
