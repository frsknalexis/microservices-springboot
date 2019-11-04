package com.dev.boot.ws.app.item.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.boot.ws.app.commons.model.Producto;
import com.dev.boot.ws.app.item.clientes.ProductoClienteRest;
import com.dev.boot.ws.app.item.model.ItemDTO;
import com.dev.boot.ws.app.item.service.ItemService;

@Service("itemServiceFeign")
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest productoClienteFeignRest;
	
	@Override
	public List<ItemDTO> findAll() {
		
		List<Producto> productos = productoClienteFeignRest.findAll();
		List<ItemDTO> itemsDTO = productos.stream().map(p -> new ItemDTO(p, 1)).collect(Collectors.toList());
		return itemsDTO;
	}

	@Override
	public ItemDTO findById(Long id, Integer cantidad) {
		
		Producto producto = productoClienteFeignRest.getOneById(id);
		ItemDTO itemDTO = new ItemDTO(producto, cantidad);
		return itemDTO;
	}

	@Override
	public Producto save(Producto producto) {
		
		Producto productoDTOReturn = productoClienteFeignRest.saveProducto(producto); 
		return productoDTOReturn;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		
		Producto productoReturn = productoClienteFeignRest.editarProducto(producto, id);
		return productoReturn;
	}

	@Override
	public void delete(Long id) {
		
		productoClienteFeignRest.eliminar(id);
	}
}
