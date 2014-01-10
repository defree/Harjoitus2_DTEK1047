/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapviewer;

/**
 * Näkymä, joka sitoo mallin webservicen kutsu URL:ksi
 * @author Kalle
 */
public class MapView {
    // URL malli
    private final String UrlModel ="http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=%d,%d,%d,%d&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS=%s&STYLES=&FORMAT=image/png&TRANSPARENT=true";

    public MapView()
    {
        
    }
    
    // Sitoo malli URL -malliin 
    public String Bind(MapModel model)
    {
        return String.format(UrlModel, model.GetX(), model.GetY(), model.GetWidth(), model.GetHeight(), StringUtils.Join(model.GetLayers(), ","));
    }
    
}
