 import java.io.*;  
 import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
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
}

//compares jobs by their CPU core numbers in descending order (largest to smalelst)
class coreComparator implements Comparator<Job> {
	
	// override the compare() method
	public int compare(Job j1, Job j2) {
		if (j1.core() == j2.core()) {
				return 0;
			}
			else if (j1.core() < j2.core()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}

class runClient {
	public static void main(String[] args) throws IOException {  
		geekClient client = new geekClient();
		client.startConnection("localhost",50000);
		client.handShake();
		
		String str = client.receiveMessage();
		
		while(str != "NONE") {
			client.receiveMessage();
			client.sendMessage("GETS All");
			
			//retrieve number of records from Data mesage
			String data = client.receiveMessage();
			int nRecs = client.dataExtract(data);
			
			ArrayList<Job> list = new ArrayList<Job>();
			
			//loop and add all servers into list of Jobs
			for(int i = 0; i < nRecs; i++) {
				String input = client.receiveMessage();
				list.add(new Job(input));
			}

			//sorts in descending order
			Collections.sort(list, new coreComparator());

		}
		
		client.stopConnection();
	}
}
