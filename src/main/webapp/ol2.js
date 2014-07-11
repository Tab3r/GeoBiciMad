/**
 * 
 */

var EPSG4326 = new OpenLayers.Projection("EPSG:4326"); 
var EPSG900913 = new OpenLayers.Projection("EPSG:900913"); 

var map = new OpenLayers.Map({
    div: "map",
    layers: [
        new OpenLayers.Layer.OSM("OSM (without buffer)"),
        new OpenLayers.Layer.OSM("OSM (with buffer)", null, {buffer: 2})
    ],
    controls: [
        new OpenLayers.Control.Navigation({
            dragPanOptions: {
                enableKinetic: true
            }
        }),
        new OpenLayers.Control.PanZoom(),
        new OpenLayers.Control.Attribution()
    ],
    center: new OpenLayers.LonLat(-3.6904525, 40.4165605).transform(EPSG4326, EPSG900913),
    zoom: 14
});

map.addControl(new OpenLayers.Control.LayerSwitcher());

var bikeStationLayer = new OpenLayers.Layer.Vector("BiciMad Station", 
{
	strategies : [new OpenLayers.Strategy.Fixed({ preload: true })],
	projection : new OpenLayers.Projection('EPSG:4326'),
	protocol: new OpenLayers.Protocol.HTTP({
	   	url: '/GeoBiciMadrid/rest/geojson',
    	format: new OpenLayers.Format.GeoJSON()    	
    }) 
});

map.addLayers([bikeStationLayer]);

var bikeIconContext = 
{
	 getMarker : function(feature) 
     {
		 var freeStations = (feature.attributes.free / feature.attributes.bases) * 100;
		 
		 if (freeStations < 25)
			 return '/GeoBiciMadrid/img/bikestationRed.png';
		 else if (freeStations < 50)
			 return '/GeoBiciMadrid/img/bikestationYellow.png';
		 else 
			 return '/GeoBiciMadrid/img/bikestationGreen.png';		 
	}, 
	getLabel : function(feature)
	{
		if (map.getZoom() > 15)
			return feature.attributes.name;
		else
			return "";
			
	},
	getGraphicWidth : function(feature)
	{
		return (32 * (map.getZoom() / 14));
	},
	getGraphicHeight : function(feature)
	{
		return (37 * (map.getZoom() / 14));
	}
	
};

var bikeStationStyle = new OpenLayers.Style({
	externalGraphic : '${getMarker}',
	graphicOpacity: 1,
	graphicWidth: '${getGraphicWidth}',
	graphicHeight: '${getGraphicHeight}',
	label: '${getLabel}',
	fontFamily: "Arial",
	fontSize: '11px',
	labelYOffset: -25,
	labelOutlineColor: "white",
	labelOutlineWidth: 3
  }, { context: bikeIconContext });

bikeStationLayer.styleMap = new OpenLayers.StyleMap({
    "default": bikeStationStyle
});

var timeout;
bikeStationLayer.events.register('loadend', bikeStationLayer, function(e) {
	// Quito el antiguo timeout para cambiarlo segun el zoom
    if(timeout) {
      window.clearTimeout(timeout);
    }

    //timeout = window.setTimeout("resourcesLayer.refresh({force:true, params: { 'rand': Math.random()} })", interval);
    timeout = window.setTimeout(function() { 
    	bikeStationLayer.refresh({force:true, params: { 'rand': Math.random()} });
	}, 10000);
});

