package com.dev.boot.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.boot.ws.app.commons.model.Producto;

@Repository("productoRepository")
public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
