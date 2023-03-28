public class Job {
	private int submitTime;
	private int jobID;
	private int estRuntime;
	private int coreNum;
	private int memory;
	private int diskNum;
	
	public Job(String str) {
		String[] arr = str.split(" ", 10);
		this.submitTime = Integer.parseInt(arr[1]);
		this.jobID = Integer.parseInt(arr[2]);
		this.estRuntime = Integer.parseInt(arr[3]);
		this.coreNum = Integer.parseInt(arr[4]);
		this.memory = Integer.parseInt(arr[5]);
		this.diskNum = Integer.parseInt(arr[6]);
	}
	
	public int ID() {
		return this.jobID;
	}

	public int sTime() {
		return this.submitTime;
	}

	public int runTime() {
		return this.estRuntime;
	}

	public int core() {
		return this.coreNum;
	}

	public int mem() {
		return this.memory;
	}

	public int disk() {
		return this.diskNum;
	}
	

}
