 import java.io.*;  
 import java.net.*;  
 
 public class geekClient {
 
 	private Socket s;
 	private BufferedReader din;
 	private DataOutputStream dout;
 	
 	public void startConnection(String ip, int port) {
	 	try {
			s = new Socket(ip, port);
			dout = new DataOutputStream(s.getOutputStream());  
			din = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}  catch (IOException e) {
	    			System.out.println(e);
	    	} 
	}
	
	public void sendMessage(String msg) {
		try {
			dout.write((msg + "\n").getBytes());
			dout.flush();
			}  catch (IOException e) {
	    			System.out.println(e);
	    		} 
		}
	
	public String receiveMessage() {
		String result = "";
		try {
			String msg = din.readLine();
			System.out.println(msg);
			result = msg;

		} catch (IOException e) {
    			System.out.println(e);
    		}  
    		return result;
	}
	
	public int dataExtract(String str) {
		String[] arr = str.split(" ", 3);
		int nRecs = Integer.parseInt(String.valueOf(arr[1]));	
		return nRecs;	
	}
	
	public void handShake() {
		this.sendMessage("HELO");
		this.receiveMessage();
		this.sendMessage("AUTH nguy315");
		this.receiveMessage();
		this.sendMessage("REDY");
	}
	
	public void schedule(String jobID, String sType, String sID) {
		this.sendMessage("SCHD " + jobID + " " + sType + " " + sID);	
	}
	
	public void stopConnection() {
		try {
			dout.write(("QUIT\n").getBytes());
			dout.flush();
    			dout.close();  
    			s.close(); 
		}  catch (IOException e) {
    			System.out.println(e);
    		} 
	}
	
	public static void main(String[] args) throws IOException{  
		geekClient client = new geekClient();
		client.startConnection("localhost",50000);
		client.handShake();
		
		String str = client.receiveMessage();
		
		while(str != "NONE") {
			client.receiveMessage();
			client.sendMessage("GETS All");
			
			String data = client.receiveMessage();
			int nRecs = client.dataExtract(data);
			
			int j = 0;
			List<String> list = new ArrayList<String>();
				
			for(int i = 0; i < nRecs; i++) {
				String input = client.receiveMessage();
				
				String[] arr = input.split(" ", 9);
				int coreNum = Integer.parseInt(String.valueOf(arr[4]));
				
				if (j < coreNum) {
					
				} 
			}
	
		}
		
		client.stopConnection();
    	} 
}
