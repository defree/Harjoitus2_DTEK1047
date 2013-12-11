package mapviewer;

/**
 * 
 *
 */
public class MapLayers {

	public String name;
	public String title;
	
	
    public void setName(String name) {
        this.name = name;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
	
    @Override
	public String toString() {
		return "Name: "+name + " Title: " + title;
	}
	
}
