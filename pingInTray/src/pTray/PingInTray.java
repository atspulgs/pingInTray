package pTray;

/**
 * @version 0.1
 * @author Atspulgs
 */
public class PingInTray {
    private static int counter = 0;
    public static synchronized int count() {
        return ++counter;
    }
    public static void main(String... args) {
        
        Tray tray = new Tray();
        Thread updater = new Thread(() -> {
            while(counter < 100) {
                try {
                    Thread.sleep(1000);
                    tray.update(""+(count()));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updater.start();
    }
}
