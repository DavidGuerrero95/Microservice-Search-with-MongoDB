package com.appcity.app.busqueda.request;

public class Busquedas {

	private String username;

	private String busqueda;

	public Busquedas() {

	}

	public Busquedas(String username, String busqueda) {
		super();
		this.username = username;
		this.busqueda = busqueda;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

}
