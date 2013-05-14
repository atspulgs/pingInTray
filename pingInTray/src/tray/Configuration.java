package tray;

public class Configuration {

	private static Configuration configuration; 
	private String host = "www.google.com";
	private int interval= 3000;
	
	private Configuration(){}
	
	public static Configuration getInstance() {
		if (configuration == null){
			configuration = new Configuration();
		} 
		return configuration;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	

}
