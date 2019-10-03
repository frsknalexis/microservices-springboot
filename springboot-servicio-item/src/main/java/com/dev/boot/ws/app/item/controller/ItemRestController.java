package com.dev.boot.ws.app.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.boot.ws.app.item.model.ItemDTO;
import com.dev.boot.ws.app.item.model.ProductoDTO;
import com.dev.boot.ws.app.item.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api/v1/item")
public class ItemRestController {

	private static Logger logger = LoggerFactory.getLogger(ItemRestController.class);
	
	@Autowired
	@Qualifier("itemServiceFeign")
	private ItemService itemService;
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ItemDTO>> findAll() {
		
		try {
			
			List<ItemDTO> itemsDTO = itemService.findAll();
			if(itemsDTO.isEmpty()) {
				return new ResponseEntity<List<ItemDTO>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<ItemDTO>>(itemsDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<ItemDTO>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/getOne/{id}/cantidad/{cantidad}")
	public ResponseEntity<ItemDTO> getOne(@PathVariable(value = "id") Long id, @PathVariable(value = "cantidad") Integer cantidad) {
		
		try {
			
			ItemDTO item = null;
			if(id.intValue() > 0) {
				item = itemService.findById(id, cantidad);
				if(item == null) {
					return new ResponseEntity<ItemDTO>(HttpStatus.NO_CONTENT);
				}	
			}
			else { 
				return new ResponseEntity<ItemDTO>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<ItemDTO>(item, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<ItemDTO>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<ItemDTO> metodoAlternativo(Long id, Integer cantidad) {
		ItemDTO item = new ItemDTO();
		ProductoDTO producto = new ProductoDTO();
		
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return new ResponseEntity<ItemDTO>(item, HttpStatus.OK);
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String puerto) {
		
		logger.info(texto);
		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
}
