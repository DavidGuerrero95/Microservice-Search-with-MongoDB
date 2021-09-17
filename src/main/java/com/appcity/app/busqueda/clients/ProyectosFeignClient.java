package com.appcity.app.busqueda.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appcity.app.busqueda.models.Proyectos;

@FeignClient(name = "app-proyectos")
public interface ProyectosFeignClient {

	@GetMapping("/proyectos/obtenerProyectoByNombre/{nombre}")
	public Proyectos getProyectosByNombre(@PathVariable("nombre") String nombre);

	@GetMapping("/proyectos/listar")
	public List<Proyectos> getProyectos();

}
