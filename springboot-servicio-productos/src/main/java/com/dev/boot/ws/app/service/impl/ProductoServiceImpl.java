package com.dev.boot.ws.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.boot.ws.app.entity.Producto;
import com.dev.boot.ws.app.repository.ProductoRepository;
import com.dev.boot.ws.app.service.ProductoService;

@Service("productoService")
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	@Qualifier("productoRepository")
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		
		Iterable<Producto> productos = productoRepository.findAll();
		List<Producto> productosReturn = new ArrayList<Producto>();
		productos.forEach(producto -> {
			productosReturn.add(producto);
		});
		return productosReturn;
	}

	@Override
	public Producto findById(Long id) {
		Producto producto = productoRepository.findById(id).orElse(null);
		return producto;
	}

	@Override
	@Transactional
	public Producto saveProducto(Producto producto) {
		
		Producto productoReturn = productoRepository.save(producto);
		return productoReturn;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoRepository.deleteById(id);
	}
}
