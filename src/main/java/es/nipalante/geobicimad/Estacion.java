package es.nipalante.geobicimad;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Estacion {

	private String idestacion;
	
	private String nombre;
	
	private Double longitud;
	
	private Double latitud;
	
	private String direccion;
	
	private Integer numero_bases;
	
	private Integer libres;

	public Estacion() {}

	public String getIdestacion() {
		return idestacion;
	}

	public void setIdestacion(String idestacion) {
		this.idestacion = idestacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getNumero_bases() {
		return numero_bases;
	}

	public void setNumero_bases(Integer numero_bases) {
		this.numero_bases = numero_bases;
	}

	public Integer getLibres() {
		return libres;
	}

	public void setLibres(Integer libres) {
		this.libres = libres;
	}
	
}
