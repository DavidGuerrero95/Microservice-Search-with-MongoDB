package com.appcity.app.busqueda.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appcity.app.busqueda.clients.MuroFeignClient;
import com.appcity.app.busqueda.clients.ProyectosFeignClient;
import com.appcity.app.busqueda.clients.RecomendacionesFeignClient;
import com.appcity.app.busqueda.models.Busqueda;
import com.appcity.app.busqueda.models.Muro;
import com.appcity.app.busqueda.models.Proyectos;
import com.appcity.app.busqueda.models.Recomendaciones;
import com.appcity.app.busqueda.repository.BusqueaRepository;
import com.appcity.app.busqueda.repository.recovery.MuroRepository;
import com.appcity.app.busqueda.repository.recovery.ProyectosRepository;
import com.appcity.app.busqueda.repository.recovery.RecomendacionesRepository;
import com.appcity.app.busqueda.request.Busquedas;

@RestController
public class BusquedaControllers {

	@Autowired
	BusqueaRepository busquedaRepository;

	@Autowired
	ProyectosFeignClient proyectosFeignClient;

	@Autowired
	MuroFeignClient muroFeignClient;
	
	@Autowired
	RecomendacionesFeignClient recomendaciones;
	
	@Autowired
	MuroRepository muroRepository;
	
	@Autowired
	ProyectosRepository proyectosRepository;
	
	@Autowired
	RecomendacionesRepository recomendacionesRepository;

	@PostMapping("/busqueda/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> crearBusqueda() {
		Busqueda bus = new Busqueda();
		bus.setNombre("busqueda");
		bus.setPalabrasClaveProyectos(new ArrayList<String>());
		bus.setPalabrasClaveMuros(new ArrayList<Integer>());
		busquedaRepository.save(bus);
		return ResponseEntity.ok("Creado correctamente");
	}
	
	@PutMapping("/busqueda/editarProyecto")
	@ResponseStatus(code = HttpStatus.OK)
	public void editarProyecto(@RequestParam String nombre) {
		Busqueda busqueda = busquedaRepository.findByNombre("busqueda");
		List<String> proyectos = busqueda.getPalabrasClaveProyectos();
		proyectos.add(nombre);
		busqueda.setPalabrasClaveProyectos(proyectos);
		busquedaRepository.save(busqueda);
	}
	
	@PutMapping("/busqueda/editarMuro")
	@ResponseStatus(code = HttpStatus.OK)
	public void editarMuro(@RequestParam Integer nombre) {
		Busqueda busqueda = busquedaRepository.findByNombre("busqueda");
		List<Integer> muro = busqueda.getPalabrasClaveMuros();
		muro.add(nombre);
		busqueda.setPalabrasClaveMuros(muro);
		busquedaRepository.save(busqueda);
	}

	@PutMapping("/busqueda/actualizarDatos")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> actualizarDatos() {
		Busqueda busqueda = busquedaRepository.findByNombre("busqueda");
		List<String> palabrasClaveProyecto = new ArrayList<String>();
		List<Integer> palabrasClaveMuro = new ArrayList<Integer>();

		List<Proyectos> proyectos = proyectosFeignClient.getProyectos();
		List<Muro> muros = muroFeignClient.getMuros();

		for (int i = 0; i < muros.size(); i++) {
			palabrasClaveMuro.add(muros.get(i).getCodigoMuro());
		}

		for (int i = 0; i < proyectos.size(); i++) {
			palabrasClaveProyecto.add(proyectos.get(i).getNombre());
		}

		busqueda.setPalabrasClaveMuros(palabrasClaveMuro);
		busqueda.setPalabrasClaveProyectos(palabrasClaveProyecto);

		busquedaRepository.save(busqueda);

		return ResponseEntity.ok("listo");
	}

	@GetMapping("/busqueda/buscar")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Proyectos> buscarProyectos(@RequestBody Busquedas busquedas) {
		List<String> palabras = busquedaRepository.findByNombre("busqueda").getPalabrasClaveProyectos();
		List<Integer> resultado = new ArrayList<Integer>();
		List<Proyectos> allProyectos = proyectosFeignClient.getProyectos();
		List<Proyectos> proyectos = new ArrayList<Proyectos>();
		try {
			recomendaciones.editarBusqueda(busquedas.getUsername(), busquedas.getBusqueda());
		} catch (Exception e) {
			System.out.println("Erroor "+e.getMessage());
		}
		for (int i = 0; i < palabras.size(); i++) {
			if (palabras.get(i).toLowerCase().contains(busquedas.getBusqueda().toLowerCase())) {
				resultado.add(palabras.indexOf(palabras.get(i)));
			}
		}

		if (resultado.isEmpty()) {
			return allProyectos;
		} else {
			for(int i=0; i<resultado.size();i++) {
				proyectos.add(allProyectos.get(resultado.get(i)));
			}
			return proyectos;
		}
	}
	
	@PutMapping("/busqueda/eliminarProyecto")
	@ResponseStatus(code = HttpStatus.OK)
	public void eliminarProyecto(@RequestParam String nombre) {
		Busqueda busqueda = busquedaRepository.findByNombre("busqueda");
		List<String> listaProyectos = busqueda.getPalabrasClaveProyectos();
		listaProyectos.remove(nombre);
		busqueda.setPalabrasClaveProyectos(listaProyectos);
		busquedaRepository.save(busqueda);
	}
	
	@PutMapping("/busqueda/eliminarMuro")
	@ResponseStatus(code = HttpStatus.OK)
	public void eliminarMuro(@RequestParam List<Integer> listaMuro) {
		Busqueda busqueda = busquedaRepository.findByNombre("busqueda");
		busqueda.setPalabrasClaveMuros(listaMuro);
		busquedaRepository.save(busqueda);
	}
	
	@PostMapping("/busqueda/arreglar")
	@ResponseStatus(code = HttpStatus.OK)
	public void arreglar() {
		List<Muro> listaMuros = muroFeignClient.getMuros();
		List<Proyectos> listaProyectos = proyectosFeignClient.getProyectos();
		List<Recomendaciones> listaRecomendaciones = recomendaciones.listarRecomendaciones();
		
		for(int i=1; i<listaMuros.size(); i++) {
			muroRepository.save(listaMuros.get(i));
		}
		
		for(int i=1; i<listaProyectos.size(); i++) {
			proyectosRepository.save(listaProyectos.get(i));
		}
		
		for(int i=1; i<listaRecomendaciones.size(); i++) {
			recomendacionesRepository.save(listaRecomendaciones.get(i));
		}
		
	}
	
}
