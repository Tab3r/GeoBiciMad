/**
 * 
 */

var EPSG4326 = new OpenLayers.Projection("EPSG:4326"); 
var EPSG900913 = new OpenLayers.Projection("EPSG:900913"); 


function onPopupClose(evt) {
    selectControl.unselect(selectedFeature);
}
function onFeatureSelect(feature) {
    selectedFeature = feature;
    popup = new OpenLayers.Popup.FramedCloud("chicken", 
                             feature.geometry.getBounds().getCenterLonLat(),
                             null,
                             "<div>" +
                             "<b>Name: " + feature.attributes.name + "</b>" +
                             "<br>ID: " + feature.attributes.idstation +
                             "<br>Direction: " + feature.attributes.direction + 
                             "<br>Bases: " + feature.attributes.bases +
                             "<br>Free: " + feature.attributes.free + 
                             "</div>",
                             null, true, onPopupClose);
    feature.popup = popup;
    map.addPopup(popup);
}
function onFeatureUnselect(feature) {
    map.removePopup(feature.popup);
    feature.popup.destroy();
    feature.popup = null;
}    

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
var selectControl = new OpenLayers.Control.SelectFeature(bikeStationLayer,
{
	onSelect: onFeatureSelect, 
	onUnselect: onFeatureUnselect
});
map.addControl(selectControl);

selectControl.activate();

var bikeIconContext = 
{
	 getMarker : function(feature) 
     {
		 var freeStations = (feature.attributes.free / feature.attributes.bases) * 100;
		 
		 if (freeStations == 0)
			 return '/GeoBiciMadrid/img/bikestationBlack.png';
		 else if (freeStations <= 30)
			 return '/GeoBiciMadrid/img/bikestationRed.png';
		 else if (freeStations <= 70)
			 return '/GeoBiciMadrid/img/bikestationOrange.png';
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
	fontFamily: 'Verdana',
	fontSize: '11px',
	fontWeight: 'bold',
	fontColor: 'white',
    labelYOffset: -25,
	labelOutlineColor: 'black',
	labelOutlineWidth: 4
  }, { context: bikeIconContext });

bikeStationLayer.styleMap = new OpenLayers.StyleMap({
    "default": bikeStationStyle
});

var timeout;
bikeStationLayer.events.register('loadstart', bikeStationLayer, function(e) {
	document.getElementById("loading").style.display="block";
});
bikeStationLayer.events.register('loadend', bikeStationLayer, function(e) {
	document.getElementById("loading").style.display="none";
});

var timeout = window.setInterval(function() {
	if (map.popups.length == 0)
		bikeStationLayer.refresh({force:true, params: { 'rand': Math.random()} });		
}, 15000);

