/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rest;

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
