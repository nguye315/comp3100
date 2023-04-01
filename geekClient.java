 import java.io.*;  
 import java.net.*;
 import java.util.*;
 
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
	
	public static void main(String[] args) throws IOException {  
		geekClient client = new geekClient();
		client.startConnection("localhost",50000);
		client.handShake();
		
		String check = client.receiveMessage();
		
		//initalize values that need to be outside of the while loop
		//they're used only once throughout the while loop and need ot be the same
		ArrayList<String> serverList = new ArrayList<>();
		int index = 0;
		int largestCore = 0;
		String largestSeverType = "";
		
		while(check != "NONE\n") {
			client.sendMessage("REDY");
			String message = client.receiveMessage();
							
			//retrieve number of records from Data mesage
			String data = client.receiveMessage();
			int nRecs = client.dataExtract(data);
				
			client.sendMessage("OK");
		
			//loop once and adds all servers into list of Jobs. Run only once to populate all servers
			if(serverList.isEmpty()) {
				client.sendMessage("GETS All");
				for(int i = 0; i < nRecs; i++) {
					String input = client.receiveMessage();		
					serverList.add(input);
					String curServer = serverList.get(i);
					
					String[] arr = curServer.split(" ", 7);
					int coreNum = Integer.parseInt(String.valueOf(arr[4]));

					//if the list is empty or meets the conditions of being larger, add to list

					if (largestCore < coreNum) {
						largestCore = coreNum;
						largestSeverType = arr[0];
						System.out.println("first largest type of server is " + largestSeverType);
					} 
				}
				
				System.out.println("List of largest servers");
				//Iterates through the server list and removes any that aren't the largest
				//and first of the largest serverTypes

				Iterator<String> itr = serverList.iterator();
				while(itr.hasNext()) {
					String server = itr.next();
					String[] arr = server.split(" ", 7);
					int coreNum = Integer.parseInt(String.valueOf(arr[4]));
					
					if (largestCore >= coreNum && largestSeverType.equals(arr[0])) {
						itr.remove();
					}
				}

				while(itr.hasNext()) {
					System.out.println(itr.next());
				}
			}
			
			client.sendMessage("OK");
			client.receiveMessage();
			
			//retreives servertype and id from the server at the current index
			String[] server = serverList.get(index).split(" ", 7);
			String sType = server[0];
			String sID = server[1];

			//retrieves job id fom job scheduled 
			String[] checkMessage = message.split(" ", 7);
			
			System.out.println(checkMessage[0]);		
			System.out.println(checkMessage[0].equals("JOBN"));
			if(checkMessage[0].equals("JOBN")) {
				System.out.println("job has been received");
				String[] job = message.split(" ", 7);
				String jobID = job[2];
				
				client.schedule(jobID, sType, sID);
				System.out.println("job has been scheduled");
				
				if(index == serverList.size()-1) {
					index = 0;
				} else {
					index += 1; 
				}
			}
			check = client.receiveMessage();
		}
		client.stopConnection();
	}
}




