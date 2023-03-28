public class Job {
	private String submitTime;
	private String jobID;
	private String estRuntime;
	private String core;
	private String memory;
	private String disk;
	
	public String[] Job(String str) {
		String[] arr = str.split(" ", 10);
		submitTime = Integer.parseInt(arr[0]);
		jobID = Integer.parseInt(arr[1]);
		estRuntime = Integer.parseInt(arr[2]);
		core = Integer.parseInt(arr[3]);
		memory = Integer.parseInt(arr[4]);
		disk = Integer.parseInt(arr[5]);
		return arr;
	}
	
	public int jobID() {
		return jobID;
	}
	
	public List[] fillArray(Integer[] list, Integer[] curr) {
		list[0] = 
	}

}
