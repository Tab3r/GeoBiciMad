package es.nipalante.geobicimad.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import es.nipalante.geobicimad.OriginalJsonResponse;

/**
 * Stupid and simple converter:
 * 			* Simple JSON from BiciMad service 
 * 			* Standard GeoJSON 
 * 
 * @author david.tabernero@gmail.com
 * 
 */

@Path("/")
public class EstacionRESTService {
	
	
	@GET
	@Path("/geojson")
	@Produces( MediaType.APPLICATION_JSON )
	public OriginalJsonResponse getGeoJson()
	{
		URL url;
		HttpURLConnection conn;
		try {
			// alternativa: http://bicimad.herokuapp.com/bicimad.json
			url = new URL("http://5.56.56.139:16080/functions/get_all_estaciones.php");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
			
			StringBuilder buff = new StringBuilder();

		    String inputStr;
		    while ((inputStr = br.readLine()) != null)
		        buff.append(inputStr);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			OriginalJsonResponse myObjects = mapper.readValue(buff.toString(), OriginalJsonResponse.class);
			
			return myObjects;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return new OriginalJsonResponse();
		
	}

}
