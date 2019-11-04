package com.dev.boot.ws.app.item.service;

import java.util.List;

import com.dev.boot.ws.app.commons.model.Producto;
import com.dev.boot.ws.app.item.model.ItemDTO;

public interface ItemService {

	List<ItemDTO> findAll();
	
	ItemDTO findById(Long id, Integer cantidad);
	
	Producto save(Producto productoDTO);
	
	Producto update(Producto productoDTO, Long id);
	
	public void delete(Long id);
}
