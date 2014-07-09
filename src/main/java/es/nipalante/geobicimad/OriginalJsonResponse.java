package es.nipalante.geobicimad;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.nipalante.geobicimad.serializer.EstacionSerializer;

/**
 * @author David
 *
 */
@JsonSerialize(using = EstacionSerializer.class)
public class OriginalJsonResponse {

	@JsonProperty("estaciones")
	private List<Estacion> estaciones;
	
	public OriginalJsonResponse() {}

	public List<Estacion> getlEstaciones() {
		return estaciones;
	}

	public void setlEstaciones(List<Estacion> lEstaciones) {
		this.estaciones = lEstaciones;
	}
	
	
	
}
