package com.dev.boot.ws.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.boot.ws.app.entity.Producto;

@Repository("productoRepository")
public interface ProductoRepository extends CrudRepository<Producto, Long> {

}
