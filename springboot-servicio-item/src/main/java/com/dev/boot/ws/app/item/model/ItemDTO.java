package com.dev.boot.ws.app.item.model;

import com.dev.boot.ws.app.commons.model.Producto;

public class ItemDTO {

	private Producto producto;
	
	private Integer cantidad;

	public ItemDTO() {
		
	}
	
	public ItemDTO(Producto producto, Integer cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}
}
