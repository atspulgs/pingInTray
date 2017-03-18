package pTray;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
    //I will assign these in the constructors as well.
    private Color bgColor;
    private int bgIndex;
    private Color fgColor;
    private int fgIndex;
    private Font font;
    private int fontIndex;
    
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
            this.bgColor = Color.WHITE;
            this.bgIndex = 0;
            this.fgColor = Color.BLACK;
            this.fgIndex = 1;
            this.font = new Font("Arial", Font.BOLD, 12);
            this.fontIndex = 0;
            this.instantiate();
        }
    }
    
    private void instantiate() {
        this.tray = SystemTray.getSystemTray();
        this.ticon = new TrayIcon(this.generateImage("00"), "Ping In Tray", this.pMenu);
        Pinger p = new Pinger("google.com",this);
        Thread updater = new Thread(p);
        updater.start();
        MenuItem settings = new MenuItem("Settings");
        settings.setActionCommand("_action_settings");
        settings.addActionListener((ActionEvent e) -> {
            if(e.getActionCommand().equals("_action_settings")) {
                Settings s = new Settings(this, p);
                s.setSize(300, 280);
                s.setResizable(false);
                s.setVisible(true);
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                s.setLocation(width-300, height-320);
                //Frame Decoration??
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setActionCommand("_action_exit");
        exit.addActionListener((ActionEvent e) -> {
            if(e.getActionCommand().equals("_action_exit")) {
                System.exit(0);
            } 
        });
        this.pMenu.add(settings);
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
    // Setters
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
    public void setBgIndex(int ind) {
        this.bgIndex = ind;
    }
    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }
    public void setFgIndex(int ind) {
        this.fgIndex = ind;
    }
    public void setFont(Font font) {
        this.font = font;
    }
    public void setFontIndex(int ind) {
        this.fontIndex = ind;
    }
    // Getters
    public Color getBgColor() { return this.bgColor; }
    public int getBgIndex() { return this.bgIndex; }
    public Color getFgColor() { return this.fgColor; }
    public int getFgIndex() { return this.fgIndex; }
    public Font getFont() { return this.font; }
    public int getFontIndex() { return this.fontIndex; }
}
