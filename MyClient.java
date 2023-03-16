    import java.io.*;  
    import java.net.*;  
    public class MyClient {  
    public static void main(String[] args) {  
    try{      
    	Socket s=new Socket("localhost",6666);
    	DataInputStream din=new DataInputStream(s.getInputStream());  
    	DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
    	
    	dout.writeUTF("Hello");  
    	dout.flush();  
    	
    	String  str=(String)din.readUTF();
    	System.out.println(str);
   	dout.writeUTF("Bye"); 
	dout.flush();
	
	str = (String)din.readUTF();
	System.out.println(str);
	
	dout.flush();
    	dout.close();  
    	s.close();  
    }catch(Exception e){System.out.println(e);}  
    }  
}  
