import java.io.*;  
import java.net.*;  
    
public class MyServer {  
public static void main(String[] args){  
    
	try	{  
    		ServerSocket ss = new ServerSocket(6666);  
    		Socket s=ss.accept();//establishes connection 
    		DataInputStream din = new DataInputStream(s.getInputStream());    
    		DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
    		
    		String str=(String)din.readUTF();
    		System.out.println(str);
		dout.writeUTF("G'day"); 
		dout.flush();

		str = (String)din.readUTF();
		System.out.println(str);
		dout.writeUTF("Bye"); 
		dout.flush();
		
    		din.close();
    		s.close();
    		ss.close();  
    		} catch(Exception e){System.out.println(e);}  
    	}  
}  
