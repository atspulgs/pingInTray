package tray;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class NativePing {

	public int obtainPingTime(String host) {
		try{
			String nativeCommand = "";
			if(System.getProperty("os.name").startsWith("Windows")) {   
				// For Windows
				nativeCommand = "ping -n 1 " + host;
			} else {
				// For Linux and OSX
				nativeCommand = "ping -c 1 " + host;
			}

			Process myProcess = Runtime.getRuntime().exec(nativeCommand);
			try {
				myProcess.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			BufferedReader reader=new BufferedReader(new InputStreamReader(myProcess.getInputStream())); 
			String nativeCommandResult=reader.readLine(); 
			String pingTimeResultLine="";
			while(nativeCommandResult!=null) 
			{ 

				if(nativeCommandResult.contains("time="))
				{
					pingTimeResultLine=nativeCommandResult;
				}
				nativeCommandResult=reader.readLine(); 


			} 

			String split1[] = pingTimeResultLine.split(" ");
			String timeNeedsCleaning= split1[4];
			timeNeedsCleaning= timeNeedsCleaning.replace("time=", "");
			String timeString = timeNeedsCleaning.replace("ms", "");
			//System.out.println(timeString);

			int time = Integer.parseInt(timeString);
			return time;

		} catch( Exception e ) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean checkIsReachable(String host) {
		boolean isReachable = false;
	    try {
	        Process proc = new ProcessBuilder("ping", host).start();

	        int exitValue = proc.waitFor();
	        System.out.println("Exit Value:" + exitValue);
	        if(exitValue == 0)
	            isReachable = true;
	    } catch (IOException e1) {
	        System.out.println(e1.getMessage());
	        e1.printStackTrace();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    return isReachable;
	}

}