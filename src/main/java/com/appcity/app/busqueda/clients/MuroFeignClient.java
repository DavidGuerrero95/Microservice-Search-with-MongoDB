package com.appcity.app.busqueda.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appcity.app.busqueda.models.Muro;

@FeignClient(name = "app-muro")
public interface MuroFeignClient {

	@GetMapping("/muros/listar")
	public List<Muro> getMuros();

	@GetMapping("/muros/buscar/{codigo}")
	public Muro getMuroCodigo(@PathVariable Integer codigo);

}
