public class Server {
	private String serverType;
	private String serverID;
	private String currStartTime;
	private String core;
	private String memory;
	private String disk;
	private String wJobs;
	private String rJobs;
	
	public void Server(String str) {
		String[] arr = str.split(" ", 10);
		serverType = arr[0];
		serverID = arr[1];
		currStartTime = arr[2];
		core = arr[3];
		memory = arr[4];
		disk = arr[5];
		wJobs = arr[6];
		rJobs = arr[7];
	}
	
	public String type() {
		return this.serverType();
	}
	
	public String id() {
		return this.serverID();
	}
	
	public int intID() {
		int id = Integer.parseInt(this.serverID());
		return id;
	}
}
