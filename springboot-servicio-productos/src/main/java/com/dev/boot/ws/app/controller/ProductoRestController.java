package com.dev.boot.ws.app.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.boot.ws.app.entity.Producto;
import com.dev.boot.ws.app.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

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
	
	@GetMapping("/listar")
	public ResponseEntity<List<Producto>> findAll() {
		
		try {
			
			List<Producto> productos = productoService.findAll();
			if(productos.isEmpty()) {
				return new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
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
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Producto>(HttpStatus.BAD_REQUEST);
		}
	}
}
