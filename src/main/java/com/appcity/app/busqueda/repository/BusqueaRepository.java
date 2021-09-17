package com.appcity.app.busqueda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.appcity.app.busqueda.models.Busqueda;

public interface BusqueaRepository extends MongoRepository<Busqueda, String>{

	@RestResource(path = "find-name")
	public Busqueda findByNombre(@Param("nombre") String nombre);
	
}
