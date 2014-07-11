package es.nipalante.geobicimad.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import es.nipalante.geobicimad.Estacion;
import es.nipalante.geobicimad.OriginalJsonResponse;

public class EstacionSerializer extends JsonSerializer<OriginalJsonResponse> {

	@Override
	public void serialize(OriginalJsonResponse value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		// GeoJSON Style!!!!
		
		jgen.writeStartObject();
		jgen.writeFieldName( "type" );
		jgen.writeString( "FeatureCollection" );
		// Adding the projection 
		jgen.writeFieldName("properties");
		jgen.writeStartObject();
		jgen.writeFieldName("name");
		jgen.writeString( "urn:ogc:def:crs:OGC:1.3:CRS84" );
		jgen.writeEndObject();
		
		// Features;
		jgen.writeFieldName( "features" );
		
		jgen.writeStartArray();
		for (Estacion e : value.getlEstaciones())
		{
			jgen.writeStartObject();
	
			jgen.writeFieldName( "type" );
			jgen.writeString( "Feature" );
	
			jgen.writeFieldName( "geometry" );
			jgen.writeStartObject();
			jgen.writeFieldName( "type" );
			jgen.writeString( "Point" );
			jgen.writeFieldName( "coordinates" );
			jgen.writeStartArray();
			jgen.writeNumber( e.getLongitud() );
			jgen.writeNumber( e.getLatitud() );
			jgen.writeEndArray();
			jgen.writeEndObject();
	
			// Comienzo de las propiedades del formato GeoJSON
			jgen.writeFieldName( "properties" );
			jgen.writeStartObject();
			
			jgen.writeFieldName( "idstation" );
			jgen.writeString( e.getIdestacion() );
			jgen.writeFieldName( "name" );
			jgen.writeString( e.getNombre() );
			jgen.writeFieldName( "direction" );
			jgen.writeString( e.getDireccion() );
			jgen.writeFieldName( "bases" );
			jgen.writeNumber( e.getNumero_bases() );
			jgen.writeFieldName( "free" );
			jgen.writeNumber( e.getLibres() );
			
			jgen.writeEndObject();
			jgen.writeEndObject();
		}
		jgen.writeEndArray();

		jgen.writeEndObject();
		
	}

}
