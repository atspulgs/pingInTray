package tray;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;

public class TrayUpdater implements Runnable {

	TrayIcon trayIcon;
	SystemTray tray;
	
	@SuppressWarnings("unused")
	private TrayUpdater(){}
	
	public TrayUpdater(TrayIcon trayIcon, SystemTray tray) {
		this.trayIcon=trayIcon;
		this.tray=tray;
	}

	@Override
	public void run() {
		NativePing ping = new NativePing();
		int time = 0;
		NumberToImage formatConverter = new NumberToImage();
		
		while(true){
			
			time = ping.obtainPingTime(Configuration.getInstance().getHost());
			Image pingTimeAsImage = formatConverter.intToImage(time);

			trayIcon.setImage(pingTimeAsImage);

			try {
				Thread.sleep(Configuration.getInstance().getInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}

	}

}
