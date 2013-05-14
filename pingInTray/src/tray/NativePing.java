package tray;


import java.io.BufferedReader;
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

}