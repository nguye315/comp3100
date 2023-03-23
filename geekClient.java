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
	
	public void receiveMessage(String check) {
		try {
			String msg = din.readLine();
			System.out.println(msg);

		} catch (IOException e) {
    			System.out.println(e);
    		}  
	}
	
	public void handShake() {
		this.sendMessage("HELO");
		this.receiveMessage("OK");
		this.sendMessage("AUTH nguy315");
		this.receiveMessage("OK");
		this.sendMessage("REDY");
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
		client.stopConnection();
    	} 
}
