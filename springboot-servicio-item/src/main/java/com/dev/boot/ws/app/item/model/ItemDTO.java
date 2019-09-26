package com.dev.boot.ws.app.item.model;

public class ItemDTO {

	private ProductoDTO producto;
	
	private Integer cantidad;

	public ItemDTO() {
		
	}
	
	public ItemDTO(ProductoDTO producto, Integer cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
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
