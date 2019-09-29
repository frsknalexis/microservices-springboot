package com.dev.boot.ws.app.item.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dev.boot.ws.app.item.model.ItemDTO;
import com.dev.boot.ws.app.item.model.ProductoDTO;
import com.dev.boot.ws.app.item.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

	private static final String URL_API_PRODUCTOS = "http://localhost:8001/api/productos/";
	
	private static final String URL_SERVICIO_PRODUCTOS = "http://servicio-productos/api/productos/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<ItemDTO> findAll() {
		
		List<ProductoDTO> productos = new ArrayList<ProductoDTO>();
		ProductoDTO[] productosReturn = restTemplate.getForObject(URL_SERVICIO_PRODUCTOS + "listar", ProductoDTO[].class);
		
		for (int i = 0; i < productosReturn.length; i++) {
			productos.add(productosReturn[i]);
		}
		
		List<ItemDTO> itemsDTO = productos.stream().map(p -> new ItemDTO(p, 1)).collect(Collectors.toList());
		return itemsDTO;
	}

	@Override
	public ItemDTO findById(Long id, Integer cantidad) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		ProductoDTO producto = restTemplate.getForObject(URL_SERVICIO_PRODUCTOS + "getOne/{id}", ProductoDTO.class, pathVariables);
		ItemDTO itemDTO = new ItemDTO(producto, cantidad);
		return itemDTO;
	}

}
