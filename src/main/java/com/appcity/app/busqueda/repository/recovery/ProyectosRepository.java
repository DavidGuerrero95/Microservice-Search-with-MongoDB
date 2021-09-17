package com.appcity.app.busqueda.repository.recovery;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.appcity.app.busqueda.models.Proyectos;

public interface ProyectosRepository extends MongoRepository<Proyectos, String>{

	@RestResource(path = "buscar")
	public Proyectos findByNombreOrCodigoProyecto(@Param("name") String nombre, @Param("name") Integer codigoProyecto);
	
	@RestResource(path = "buscar-name")
	public Proyectos findByNombre(@Param("name") String nombre);
	
	@RestResource(path = "buscar-label")
	public Proyectos findByCodigoProyectoOrNombre(@Param("label") Integer codigoProyecto, @Param("label") String nombre);
	
}
