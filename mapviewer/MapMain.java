package mapviewer;
/**
 * P‰‰luokka. M‰‰ritet‰‰n mist‰ URLista haetaan kartan XML ja aloituskartta.
 * Haetaan kartan layer-tiedot XML-parserista getMapLayers-functiolla.
 * Muunnetaan arraylist arrayksi.
 * Tehd‰‰n uusi MapDialog, joka piirt‰‰ kartankatseluikkunan.
 */

import java.util.List;

public class MapMain {
	
	public static void main(String[] args) throws Exception {
		
		String capabilitiesURL = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities";
		String mapURL = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=bluemarble,country_bounds,continents,cities&STYLES=&FORMAT=image/png&TRANSPARENT=true";
		
		MapXML xml = new MapXML();
		
		List<MapLayers> layerList = xml.getMapLayers(capabilitiesURL);
		
		String[][] layerArray = new String[layerList.size()][2];
		
		for (int i = 0; i < layerList.size(); i++) {
			MapLayers layer = layerList.get(i);
			//System.out.println(layer);
			
			layerArray[i][0] = layer.name;
			layerArray[i][1] = layer.title;
		}	
		
		MapDialog MapWindow = new MapDialog(mapURL,layerArray);
		
		
		
	}
}
