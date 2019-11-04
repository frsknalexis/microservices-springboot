package com.dev.boot.ws.app.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.boot.ws.app.commons.model.Producto;
import com.dev.boot.ws.app.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
	
	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	@Qualifier("productoService")
	private ProductoService productoService;
	
	@PostMapping("/saveProducto")
	public ResponseEntity<Producto> saveProducto(@Valid @RequestBody Producto producto) {
		
		try {
			
			if(producto != null) {
				producto.setCreatedAt(new Date());
				Producto productoReturn = productoService.saveProducto(producto);
				return new ResponseEntity<Producto>(productoReturn, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<Producto> editarProducto(@Valid @RequestBody Producto producto, @PathVariable(value = "id") Long id) {
		
		try {
			
			Producto productoDb = productoService.findById(id);
			if(productoDb == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			productoDb.setNombre(producto.getNombre());
			productoDb.setPrecio(producto.getPrecio());
			
			Producto productoReturn = productoService.saveProducto(productoDb);
			return new ResponseEntity<Producto>(productoReturn, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable(value = "id") Long id) {
		
		try {
			
			productoService.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Producto>> findAll() {
		
		try {
			
			List<Producto> productos = productoService.findAll();
			if(productos.isEmpty()) {
				return new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
			}
			
			List<Producto> productosReturn = productos.stream().map(producto -> {
				//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
				producto.setPort(port);
				return producto;
			}).collect(Collectors.toList());
			
			return new ResponseEntity<List<Producto>>(productosReturn, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<Producto> getOneById(@PathVariable(value = "id") Long id) {
		
		try {
			
			Producto producto = null;
			if(id.intValue() > 0) {
				 producto = productoService.findById(id);
				 if(producto == null) {
						return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
				}
			}
			else {
				return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
			}
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			
			/*
			try {
				Thread.sleep(2000L);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			*/
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.BAD_REQUEST);
		}
	}
}
