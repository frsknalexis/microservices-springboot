package com.dev.boot.ws.app.item.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.boot.ws.app.item.clientes.ProductoClienteRest;
import com.dev.boot.ws.app.item.model.ItemDTO;
import com.dev.boot.ws.app.item.model.ProductoDTO;
import com.dev.boot.ws.app.item.service.ItemService;

@Service("itemServiceFeign")
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest productoClienteFeignRest;
	
	@Override
	public List<ItemDTO> findAll() {
		
		List<ProductoDTO> productos = productoClienteFeignRest.findAll();
		List<ItemDTO> itemsDTO = productos.stream().map(p -> new ItemDTO(p, 1)).collect(Collectors.toList());
		return itemsDTO;
	}

	@Override
	public ItemDTO findById(Long id, Integer cantidad) {
		
		ProductoDTO producto = productoClienteFeignRest.getOneById(id);
		ItemDTO itemDTO = new ItemDTO(producto, cantidad);
		return itemDTO;
	}

	@Override
	public ProductoDTO save(ProductoDTO productoDTO) {
		
		ProductoDTO productoDTOReturn = productoClienteFeignRest.saveProducto(productoDTO); 
		return productoDTOReturn;
	}

	@Override
	public ProductoDTO update(ProductoDTO productoDTO, Long id) {
		
		ProductoDTO productoReturn = productoClienteFeignRest.editarProducto(productoDTO, id);
		return productoReturn;
	}

	@Override
	public void delete(Long id) {
		
		productoClienteFeignRest.eliminar(id);
	}
}
