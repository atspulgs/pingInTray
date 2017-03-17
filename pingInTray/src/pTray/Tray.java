package pTray;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author Atspulgs
 */
public class Tray implements Updatable<String> {
    //I will assign these in thje constructors as well.
    private Color bgColor = Color.WHITE;
    private Color fgColor = Color.BLACK;
    private Font font = new Font("Arial", Font.BOLD, 12);
    
    private SystemTray tray = null;
    private TrayIcon ticon = null;
    private final PopupMenu pMenu = new PopupMenu();
    
    public enum State {
        S01("OK: Execution Successful"),
        S02("FAIL: SystemTray on this system is not supported."),
        S03("FAIL: TrayIcon could not be added to the SystemTray.");
        
        private final String description;
        
        private State(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return this.description;
        }
    }
    private State state = State.S01;
    
    public Tray() {
        if(!SystemTray.isSupported()) {
            this.state = State.S02;
            System.out.println(this.state.getDescription());
        } else {
            this.instantiate();
        }
    }
    
    private void instantiate() {
        this.tray = SystemTray.getSystemTray();
        this.ticon = new TrayIcon(this.generateImage("00"), "Ping In Tray", this.pMenu);
        //other MenuItems....
        //Settings Menu item
        MenuItem exit = new MenuItem("Exit");
        exit.setActionCommand("_action_exit");
        exit.addActionListener((ActionEvent e) -> {
            switch(e.getActionCommand()) {
                case "_action_exit":
                    System.exit(0);
            } 
        });
        this.pMenu.add(exit);
        try {
            this.tray.add(this.ticon);
        } catch (AWTException ex) {
            this.state = State.S03;
            System.out.println(this.state.getDescription());
        }
        System.out.println(this.state.getDescription());
    }
    
    //Update the Image.
    @Override
    public synchronized void update(String upd) {
        this.ticon.setImage(this.generateImage(upd));
    }
    
    //Generate Images
    private Image generateImage(String text) {
        if(text.length() == 1) text = "0"+text;
        else if(text.length() < 1) text = "00";
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        //Sounds like a good idea, dont quite get these
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        //Draw background
        g2d.setColor(this.bgColor);
        g2d.fill(new RoundRectangle2D.Double(0,0,16,16,10,10));
        
        //Draw Font
        g2d.setFont(this.font);
        g2d.setColor(this.fgColor);
        g2d.drawString(text, 1, 12);
        
        g2d.dispose();
        
        return img;
    }
}
