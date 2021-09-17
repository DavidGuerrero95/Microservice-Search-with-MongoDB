package com.appcity.app.busqueda.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.appcity.app.busqueda.models.Recomendaciones;


@FeignClient(name = "app-recomendacion")
public interface RecomendacionesFeignClient {
	
	@PutMapping("/recomendaciones/editarBusqueda/{nombre}")
	public void editarBusqueda(@PathVariable String nombre, @RequestParam String busqueda);
	
	@GetMapping("/recomendaciones/listarRecomendaciones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Recomendaciones> listarRecomendaciones();

}
