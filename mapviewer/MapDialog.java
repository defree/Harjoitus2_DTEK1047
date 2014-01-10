package mapviewer;    

// Kartankatseluohjelman graafinen k�ytt�liittym�
     
    import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
     

    public class MapDialog extends JFrame {
     
      // K�ytt�liittym�n komponentit
     
      private JLabel imageLabel = new JLabel();
      private JPanel leftPanel = new JPanel();
     
      private ImageIcon icon;
      private Timer timer;
      
      private JButton refreshB = new JButton("Refresh");
      private JButton leftB = new JButton("<");
      private JButton rightB = new JButton(">");
      private JButton upB = new JButton("^");
      private JButton downB = new JButton("v");
      private JButton zoomInB = new JButton("+");
      private JButton zoomOutB = new JButton("-");
      
      private String MapURL;
      private String[][] layerArray;
      
      private MapModel model;
      private MapView  view; 
     
      public MapDialog(final String MapURL, final String[][] layerArray) throws Exception {
    	  this.MapURL = MapURL;
    	  this.layerArray = layerArray;
  	  this.model = new MapModel();
          this.view = new MapView();
          
           //Alustetaan malli oletusarvoilla.
           model.SetX(-180);
           model.SetY(-90);
           model.SetX1(180);
           model.SetY1(90);
    	// Valmistele ikkuna ja lis�� siihen komponentit
		     
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      getContentPane().setLayout(new BorderLayout());
	     
	      // ALLA OLEVAN TESTIRIVIN VOI KORVATA JOLLAKIN MUULLA ERI ALOITUSN�KYM�N
	      // LATAAVALLA RIVILL�
	      	icon = new ImageIcon(new URL(MapURL));
	      	imageLabel.setIcon(new ImageIcon(new URL(MapURL)));
		     
	        add(imageLabel, BorderLayout.EAST);
    	  //loadMap(MapURL);
    	  
	        ButtonListener bl = new ButtonListener();
	        refreshB.addActionListener(bl);  
	        leftB.addActionListener(bl);
	        rightB.addActionListener(bl);
	        upB.addActionListener(bl);
	        downB.addActionListener(bl);
	        zoomInB.addActionListener(bl);
	        zoomOutB.addActionListener(bl);
      
	      // foo
	      leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	      leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	      leftPanel.setMaximumSize(new Dimension(100, 600));
	   
	      // TODO:
	      // ALLA OLEVIEN KOLMEN TESTIRIVIN TILALLE SILMUKKA JOKA LIS�� K�YTT�LIITTYM��N
	      // KAIKKIEN XML-DATASTA HAETTUJEN KERROSTEN VALINTALAATIKOT MALLIN MUKAAN
	      
	      for (String[] row : layerArray) {
	          leftPanel.add(new LayerCheckBox(row[0], row[1], true));
	
	      }
	      
	      //leftPanel.add(new LayerCheckBox("bluemarble", "Maapallo", true));
	      //leftPanel.add(new LayerCheckBox("cities", "Kaupungit", false));
	   
	      leftPanel.add(refreshB);
	      leftPanel.add(Box.createVerticalStrut(20));
	      leftPanel.add(leftB);
	      leftPanel.add(rightB);
	      leftPanel.add(upB);
	      leftPanel.add(downB);
	      leftPanel.add(zoomInB);
	      leftPanel.add(zoomOutB);
	   
	      add(leftPanel, BorderLayout.WEST);
     
	      pack();
	      setVisible(true);
    	  
      }
   
     
      // Kontrollinappien kuuntelija
      // KAIKKIEN NAPPIEN YHTEYDESS� VOINEE HY�DYNT�� updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        
 
		  public void actionPerformed(ActionEvent e) {
			  if(e.getSource() == refreshB) {
				  try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
			  }
	          if(e.getSource() == leftB) {
	            // TODO:
	            // VASEMMALLE SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                    int increment = GetIncrement(model.GetX(),model.GetWidth());
                    int x0 = model.GetX()-increment;
                    int x1 = model.GetWidth()-increment;
                    model.SetX(x0);
                    model.SetX1(x1);  
	          }
	          if(e.getSource() == rightB)
                  {
	            // TODO:
	            // OIKEALLE SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                      int increment = GetIncrement(model.GetX(),model.GetWidth());
                      int x0 = model.GetX()+increment;
                      int x1 = model.GetWidth()+increment;
                      model.SetX(x0);
                      model.SetX1(x1);  
	          }
	          if(e.getSource() == upB) {
	            // TODO:
	            // YL�SP�IN SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                      int increment = GetIncrement(model.GetY(),model.GetHeight());
                      int y0 = model.GetY()-increment;
                      int y1 = model.GetHeight()-increment;
                      model.SetY(y0);
                      model.SetY1(y1);
	          }
	          if(e.getSource() == downB) {
	            // TODO:
	            // ALASP�IN SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                      
                      int increment = GetIncrement(model.GetY(),model.GetHeight());
                      int y0 = model.GetY()+increment;
                      int y1 = model.GetHeight()+increment;
                      model.SetY(y0);
                      model.SetY1(y1);  
	          }
	          if(e.getSource() == zoomInB) {
	            // TODO:
	            // ZOOM IN -TOIMINTO
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                      int x0 = model.GetX();
                      int x1 = model.GetWidth();
                      int y0 = model.GetY();
                      int y1 = model.GetHeight();
                      
                      // lasketaan laatikon uudet nurkat edellisten arvojen perusteella
                      double x3 = 0.853553*x0 + 0.146447*x1;
                      double y3 = 0.853553*y0 + 0.146447*y1;
                      double x4 = 0.146447*x0 + 0.853553*x1;
                      double y4 = 0.146447*y0 + 0.853553*y1;
                      
                      // Zoomauksessa tulee pieni virhe korkeussuunnassa, lasketaan virheen suuruus
                      // kun tiedetään sivujen suhde. 
                      double yError = (x4-x3)-2*(y4-y3);
                      
                      // Virheen suunnasta riippuen, 
                      y3-=yError/2;
                      y4+=yError/2;
                      
                      // Tämän matalampaa kuvaa ei voi olla.
                      if ((y4-y3) > 1)
                      {
                        model.SetX((int)x3);
                        model.SetY((int)y3);
                        model.SetX1((int)x4);
                        model.SetY1((int)y4);
                      }
	          }
	          if(e.getSource() == zoomOutB) {
	            // TODO:
	            // ZOOM OUT -TOIMINTO
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA P�IVIT� KUVA
                    
                      int x0 = model.GetX();
                      int x1 = model.GetWidth();
                      int y0 = model.GetY();
                      int y1 = model.GetHeight();
                      
                      // lasketaan laatikon uudet nurkat edellisten arvojen perusteella
                      double x3 = 1.20711*x0 - 0.207107*x1;
                      double y3 = 1.20711*y0 - 0.207107*y1;
                      double x4 = 1.20711*x1 - 0.207107*x0;
                      double y4 = 1.20711*y1 - 0.207107*y0;
                      
                      // Lasketaan kuinka suuri virhe sivujen suhteessa on.
                      double yError = (x4-x3)-2*(y4-y3);
                     
                      // korjataan virhettä
                      y3-=yError/2;
                      y4+=yError/2;
                      
                      model.SetX((int)x3);
                      model.SetY((int)y3);
                      model.SetX1((int)x4);
                      model.SetY1((int)y4);
	          }
                  
                  try
                  {
                       // päivitetään kuva
                       updateImage();
                  }
                  catch(Exception ex) { ex.printStackTrace(); }
                  }
                  
                  // Metodi, joka laskee paljonko karttaa liikutettaessa
                  // liikutaan
                  private int GetIncrement(int a0, int a1)
                  {
                      // liikutetaan kuvaa 10% sen leveydestä
                      int inc = (int)(0.1*(a1-a0));
                      
                      // Jos liikuman määrä on pienempi kuin 1 liikutaan kuitenkin
                      // yhden verran
                      return inc>0?inc:1;
                  }

      }
     
      // Valintalaatikko, joka muistaa karttakerroksen nimen
      private class LayerCheckBox extends JCheckBox {
        private String name = "";
        public LayerCheckBox(String name, String title, boolean selected) {
          super(title, null, selected);
          this.name = name;
        }
        public String getName() { return name; }
      }
     
      // Tarkastetaan mitkä karttakerrokset on valittu,
      // tehd��n uudesta karttakuvasta pyynt� palvelimelle ja p�ivitet��n kuva
      public void updateImage() throws Exception {
     
        // Tutkitaan, mitk� valintalaatikot on valittu, ja
        // ker�t��n s:��n pilkulla erotettu lista valittujen kerrosten
        // nimist� (k�ytet��n haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) 
                  model.AddLayer(com.getName());
              else
                  model.RemoveLayer(com.getName());
        }
        
        /*
        String url = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS="+s+"&STYLES=&FORMAT=image/png&TRANSPARENT=true";	
        System.out.println(url);
        */
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN P�IVITYS ERILLISESS� S�IKEESS�
        
        loadImage();
        imageLabel.setIcon(new ImageIcon(new URL(MapURL)));

      }
     
      // Lataa kuvan mallin perusteella  asynkronisesti
      private void loadImage()
      {
          // luodaan kuvan lataaja, joka sitoo mallin, näkymään ja muodostaa
          // siitä url:n, jolla haetaan kuva.
          Runnable loader= new Runnable(){
            @Override
            public void run(){
                MapURL = view.Bind(model);
                System.out.println(MapURL);
                try {
                    final ImageIcon map = new ImageIcon(new URL(MapURL));
                
                    // Päivitetään UI-elementti, kunhan kuva ollaan saatu haettua.
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            imageLabel.setIcon(map);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
          };
          
          // Haetaan kuva ja sidotaan se GUI:hin
          new Thread(loader).start();
      }
    } // MapDialog
