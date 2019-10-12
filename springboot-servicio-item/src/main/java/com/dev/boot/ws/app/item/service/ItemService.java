package com.dev.boot.ws.app.item.service;

import java.util.List;

import com.dev.boot.ws.app.item.model.ItemDTO;
import com.dev.boot.ws.app.item.model.ProductoDTO;

public interface ItemService {

	List<ItemDTO> findAll();
	
	ItemDTO findById(Long id, Integer cantidad);
	
	ProductoDTO save(ProductoDTO productoDTO);
	
	ProductoDTO update(ProductoDTO productoDTO, Long id);
	
	public void delete(Long id);
}
