package tray;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class NumberToImage {

	public Image intToImage(Integer pingValue) {
		
		String stringOfPingValue = Integer.toString(pingValue); 
	    JLabel text = new JLabel(stringOfPingValue);
	    text.setSize(26,20);
	    text.setFont(new Font("MONOSPACED", Font.BOLD,18)); 
	    text.setBackground(Color.orange);
	    BufferedImage image = getImage(text);
	    return  image;
	}
	
	private BufferedImage getImage(JComponent c) {
	    Rectangle region = c.getBounds();
	    BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = image.createGraphics();
	    g2d.translate(-region.x, -region.y);
	    g2d.setColor(c.getBackground() );
	    g2d.fillRect(region.x, region.y, region.width, region.height);
	    c.paint(g2d);
	    g2d.dispose();
	    return image;
	    
	}

}

