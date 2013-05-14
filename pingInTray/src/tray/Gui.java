package tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class Gui implements ActionListener{
	 
	TrayIcon trayIcon;
	 SystemTray tray;
	 
	public Gui(){
		
		if (!SystemTray.isSupported()) {
			//TODO make a msg box
			System.out.println("SystemTray is not supported");
			return;
		}
		else
		{	
			
			MenuItem exitItem = new MenuItem("Exit");
			MenuItem configure = new MenuItem("Configure");
			exitItem.addActionListener(this);
			configure.addActionListener(this);
			exitItem.setActionCommand("Exit");
			configure.setActionCommand("Configure");
			PopupMenu mainMenu = new PopupMenu();
			mainMenu.add(exitItem);
			mainMenu.add(configure);

			String currentDir = System.getProperty("user.dir");
			Image image = new ImageIcon(currentDir+"\\src\\tray\\test.jpg").getImage();
			trayIcon = new TrayIcon(image, "tray icon",mainMenu);
			trayIcon.setImageAutoSize(true);
			tray = SystemTray.getSystemTray();
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				//TODO msg box
				System.out.println("Failed to add the tray icon");
			}

			startUpdater();

		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String action = event.getActionCommand();

		if(action.equals("Exit"))
		{
			//TODO msg box or don't bother printing a message
			System.out.println("Exiting");
			System.exit(0);

		}
		
		if(action.equals("Configure"))
		{
			//TODO msg box or don't bother printing a message
			System.out.println("Configure");
			ConfigurationGui configurer = new ConfigurationGui();
			configurer.setVisible(true);

		}
		

	}

	public void startUpdater(){ 	
		Thread updater = new Thread(new TrayUpdater(trayIcon,tray));
		updater.start();   
	}

}

