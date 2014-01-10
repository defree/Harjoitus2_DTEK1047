/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapviewer;

import java.util.*;

/**
 * Kartta malli
 * @author Kalle
 */
public class MapModel {
    
    // kartan kärkipisteiden koordinaatit
    private int x;
    private int y;
    private int x1;
    private int y1;
    
    // Karttakerrosjoukko
    private Set<String> layers;
    
    public MapModel(int x, int y, int x1, int y1, Set<String> layers)
    {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        
        this.layers = layers;
    }
    
    public MapModel()
    {
        layers = new HashSet<String>();
    }
    
    // Lisää karttakerroksen malliin
    public void AddLayer(String layer)
    {
        layers.add(layer);
    }
    
    // Poistaa karttakerroksen mallista
    public void RemoveLayer(String layer)
    {
        layers.remove(layer);
    }
    
    // luokan jäsenten setterit.
    public void SetX(int x)
    {
        this.x = x;
    }

    public void SetY(int y)
    {
        this.y = y;
    }
     
    public void SetX1(int x1)
    {
        this.x1 = x1;
    }
    
    public void SetY1(int y1)
    {
        this.y1 = y1;
    }
    
    // Luokan jäsenten getterit
    public int GetX()
    {
        return this.x;
    }
    
    public int GetY()
    {
        return this.y;
    }
    
    public int GetWidth()
    {
        return this.x1;
    }
    
    public int GetHeight()
    {
        return this.y1;
    }
    
    public Set<String> GetLayers()
    {
        return this.layers;
    }
    
}
