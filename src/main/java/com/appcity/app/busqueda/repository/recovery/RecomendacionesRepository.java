package com.appcity.app.busqueda.repository.recovery;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.appcity.app.busqueda.models.Recomendaciones;

public interface RecomendacionesRepository extends MongoRepository<Recomendaciones, String>{
	
	@RestResource(path = "buscar")
	public Recomendaciones findByNombre(@Param("nombre") String nombre);

}
