package pTray;

/**
 * @version 0.1
 * @author Atspulgs
 */
public class PingInTray {
    public static void main(String... args) {  
        Tray tray = new Tray();
        Thread updater = new Thread(new Pinger("google.com",tray));
        updater.start();
    }
}
