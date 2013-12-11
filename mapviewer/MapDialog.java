package mapviewer;    

// Kartankatseluohjelman graafinen kï¿½yttï¿½liittymï¿½
     
    import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
     

    public class MapDialog extends JFrame {
     
      // Kï¿½yttï¿½liittymï¿½n komponentit
     
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
     
      public MapDialog(final String MapURL, final String[][] layerArray) throws Exception {
    	  this.MapURL = MapURL;
    	  this.layerArray = layerArray;
  	  
    	// Valmistele ikkuna ja lisï¿½ï¿½ siihen komponentit
		     
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      getContentPane().setLayout(new BorderLayout());
	     
	      // ALLA OLEVAN TESTIRIVIN VOI KORVATA JOLLAKIN MUULLA ERI ALOITUSNï¿½KYMï¿½N
	      // LATAAVALLA RIVILLï¿½
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
	      // ALLA OLEVIEN KOLMEN TESTIRIVIN TILALLE SILMUKKA JOKA LISï¿½ï¿½ Kï¿½YTTï¿½LIITTYMï¿½ï¿½N
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
      // KAIKKIEN NAPPIEN YHTEYDESSï¿½ VOINEE HYï¿½DYNTï¿½ï¿½ updateImage()-METODIA
      private class ButtonListener implements ActionListener{
        
 
		  public void actionPerformed(ActionEvent e) {
			  if(e.getSource() == refreshB) {
				  try { updateImage(); } catch(Exception ex) { ex.printStackTrace(); }
			  }
	          if(e.getSource() == leftB) {
	            // TODO:
	            // VASEMMALLE SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
	          if(e.getSource() == rightB) {
	            // TODO:
	            // OIKEALLE SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
	          if(e.getSource() == upB) {
	            // TODO:
	            // YLï¿½SPï¿½IN SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
	          if(e.getSource() == downB) {
	            // TODO:
	            // ALASPï¿½IN SIIRTYMINEN KARTALLA
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
	          if(e.getSource() == zoomInB) {
	            // TODO:
	            // ZOOM IN -TOIMINTO
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
	          if(e.getSource() == zoomOutB) {
	            // TODO:
	            // ZOOM OUT -TOIMINTO
	            // MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA Pï¿½IVITï¿½ KUVA
	          }
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
      // tehdään uudesta karttakuvasta pyyntö palvelimelle ja päivitetään kuva
      public void updateImage() throws Exception {
        String s = "";
     
        // Tutkitaan, mitkï¿½ valintalaatikot on valittu, ja
        // kerï¿½tï¿½ï¿½n s:ï¿½ï¿½n pilkulla erotettu lista valittujen kerrosten
        // nimistï¿½ (kï¿½ytetï¿½ï¿½n haettaessa uutta kuvaa)
        Component[] components = leftPanel.getComponents();
        for(Component com:components) {
            if(com instanceof LayerCheckBox)
              if(((LayerCheckBox)com).isSelected()) s = s + com.getName() + ",";
        }
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
        	System.out.println(s);
        
        String url = "http://demo.mapserver.org/cgi-bin/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=-180,-90,180,90&SRS=EPSG:4326&WIDTH=953&HEIGHT=480&LAYERS="+s+"&STYLES=&FORMAT=image/png&TRANSPARENT=true";	
        System.out.println(url);
        // TODO:
        // getMap-KYSELYN URL-OSOITTEEN MUODOSTAMINEN JA KUVAN PÄIVITYS ERILLISESSÄ SÄIKEESSÄ
        
        MapURL = url;
        
        imageLabel.setIcon(new ImageIcon(new URL(MapURL)));

      }
     
    } // MapDialog
