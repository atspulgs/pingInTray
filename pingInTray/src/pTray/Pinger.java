package pTray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Atspulgs
 */
public class Pinger implements Runnable {
    private String target;
    private final Updatable upd;
    private Elevation elev;
    private int delay;
    
    public enum Elevation {
        Minimum,
        Maximum,
        Average;
    }
    
    public Pinger(String target, Updatable upd) {
        this(target,upd,Elevation.Average, 1000);
    }
    
    public Pinger(String target, Updatable upd, Elevation elev, int delay) {
        this.target = target;
        this.upd = upd;
        this.elev = elev;
        this.delay = delay;
    }
    
    @Override
    public void run() {
         while(true) {
             try {
                 this.upd.update(""+this.ping());
                 Thread.sleep(this.delay);
             } catch (InterruptedException ex) {
                 System.out.println("Interruped!");
             }
        }
    }
    
    private int ping() {
        int min = 0, max = 0, mid = 0;
        String nativeCommand;
        if(System.getProperty("os.name").startsWith("Windows")) {   
            nativeCommand = "ping -n 1 " + this.target;
        } else { // For Linux and OSX
            nativeCommand = "ping -c 1 " + this.target;
        }
        try {
            Process myProcess = Runtime.getRuntime().exec(nativeCommand);
            myProcess.waitFor();
            BufferedReader reader=new BufferedReader(new InputStreamReader(myProcess.getInputStream()));
            String result;
            while((result = reader.readLine()) != null) {
                if(!result.contains("Minimum = "))
                    continue;
                String[] temp = result.split(", ");
                min = Integer.parseInt(temp[0].replace("Minimum = ", "").replace("ms", "").trim());
                max = Integer.parseInt(temp[1].replace("Maximum = ", "").replace("ms", "").trim());
                mid = Integer.parseInt(temp[2].replace("Average = ", "").replace("ms", "").trim());
                break;
            }
        } catch (IOException | InterruptedException ex) {
            
        }

        switch(this.elev) {
            case Average: return mid;
            case Minimum: return min;
            case Maximum: return max;
            default: return 0;
        }
    }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
