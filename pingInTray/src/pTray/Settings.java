package pTray;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author Atspulgs
 */
public class Settings extends JFrame {
    public enum F {
        Style1(new Font("Arial", Font.BOLD, 12),"Arial Bold"),
        Style2(new Font("Comic Sans MS", Font.BOLD, 11),"Comic Bold");
        private final Font font;
        private final String name;
        private F(Font font, String name) {
            this.font = font;
            this.name = name;
        }

        public Font getFont() { return this.font; }
        public String getName() { return this.name; }
    }
    
    private final String[] colors = {
        "White",
        "Black",
        "Red",
        "Green",
        "Blue",
        "Orange",
        "Yellow",
        "Cyan",
        "Purple"
    };
    private final String[] fonts = {
        F.Style1.getName(),
        F.Style2.getName()
    };
    
    private final Tray tray;
    private final Pinger pinger;
    private final JPanel pane = new JPanel();
    private final JLabel title = new JLabel("Settings");
    private final JLabel bgLabel = new JLabel("Background color:");
    private final JLabel fgLabel = new JLabel("Foreground color:");
    private final JLabel fontLabel = new JLabel("Text Font:");
    private final JLabel targetLabel = new JLabel("Target to Ping:");
    private final JLabel delayLabel = new JLabel("Ping Delay:");
    private final JComboBox bgColorBox = new JComboBox(colors);
    private final JComboBox fgColorBox = new JComboBox(colors);
    private final JComboBox fontBox = new JComboBox(fonts);
    private final JTextField targetBox = new JTextField();
    private final JTextField delayBox = new JTextField();
    private final JButton applyButton = new JButton("Apply");
    private final GroupLayout mainLayout = new GroupLayout(pane);
    public Settings(Tray tray, Pinger pinger) {
        this.tray = tray;
        this.pinger = pinger;
        this.title.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        this.bgColorBox.setSelectedIndex(this.tray.getBgIndex());
        this.fgColorBox.setSelectedIndex(this.tray.getFgIndex());
        this.fontBox.setSelectedIndex(this.tray.getFontIndex());
        this.targetBox.setText(this.pinger.getTarget());
        this.delayBox.setText(""+this.pinger.getDelay());
        this.applyButton.setActionCommand("_action_settings_apply");
        this.applyButton.addActionListener((ActionEvent e) -> {
            if(e.getActionCommand().equals("_action_settings_apply")) {
                this.tray.setBgColor(this.getColor(colors[this.bgColorBox.getSelectedIndex()]));
                this.tray.setBgIndex(this.bgColorBox.getSelectedIndex());
                this.tray.setFgColor(this.getColor(colors[this.fgColorBox.getSelectedIndex()]));
                this.tray.setFgIndex(this.fgColorBox.getSelectedIndex());
                this.tray.setFont(this.getFont(fonts[this.fontBox.getSelectedIndex()]));
                this.tray.setFontIndex(this.fontBox.getSelectedIndex());
                this.pinger.setTarget(this.targetBox.getText());
                this.pinger.setDelay(Integer.parseInt(this.delayBox.getText()));
            }
        });
        this.setContentPane(pane);
        this.pane.setLayout(mainLayout);
        this.mainLayout.setAutoCreateGaps(true);
        this.mainLayout.setAutoCreateContainerGaps(true);
        this.mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup()
            .addComponent(title)
            .addGroup(
                mainLayout.createSequentialGroup()
                .addGroup(
                    mainLayout.createParallelGroup()
                    .addComponent(bgLabel)
                    .addComponent(fgLabel)
                    .addComponent(fontLabel)
                    .addComponent(targetLabel)
                    .addComponent(delayLabel)
                ).addGroup(
                    mainLayout.createParallelGroup()
                    .addComponent(bgColorBox)
                    .addComponent(fgColorBox)
                    .addComponent(fontBox)
                    .addComponent(targetBox)
                    .addComponent(delayBox)
                    .addComponent(applyButton)
                )
            )
        );
        this.mainLayout.setVerticalGroup(
            mainLayout.createSequentialGroup()
            .addComponent(title)
            .addGroup(
                mainLayout.createParallelGroup()
                .addComponent(bgLabel)
                .addComponent(bgColorBox)
            ).addGroup(
                mainLayout.createParallelGroup()
                .addComponent(fgLabel)
                .addComponent(fgColorBox)
            ).addGroup(
                mainLayout.createParallelGroup()
                .addComponent(fontLabel)
                .addComponent(fontBox)
            ).addGroup(
                mainLayout.createParallelGroup()
                .addComponent(targetLabel)
                .addComponent(targetBox)
            ).addGroup(
                mainLayout.createParallelGroup()
                .addComponent(delayLabel)
                .addComponent(delayBox)
            ).addComponent(applyButton)
        );
        this.mainLayout.linkSize(SwingConstants.VERTICAL,bgLabel,bgColorBox,fgLabel,fgColorBox,fontLabel,fontBox,targetLabel,targetBox,delayLabel,delayBox);
    }
    
    private Font getFont(String name) {
        switch(name) {
            case "Arial Bold": return F.Style1.getFont();
            case "Comic Bold": return F.Style2.getFont();
            default: return F.Style1.getFont();
        }
    }
    
    private Color getColor(String name) {
        switch(name) {
            case "White": return Color.WHITE;
            case "Black": return Color.BLACK;
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            case "Orange": return Color.ORANGE;
            case "Yellow": return Color.YELLOW;
            case "Cyan": return Color.CYAN;
            case "Purple": return new Color(102,0,204);
            default: return Color.BLACK;
        }
    }
}
