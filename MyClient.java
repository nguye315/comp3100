    import java.io.*;  
    import java.net.*;  
    public class MyClient { 
    
    public static void main(String[] args) {  
 
    try{      
    	Socket s = new Socket("localhost",50000);
    	BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
    	DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
    	
    	//initial hello for connection
    	dout.write(("HELO\n").getBytes());  
    	dout.flush(); 
    	
    	//confirm OK
    	//if OK return username AUTH
    	String str = din.readLine();
    	System.out.println(str);
   
   	dout.write(("AUTH nguy315\n").getBytes());
	dout.flush();

    	//confirm OK
    	//receive ds-system.xml info
    	//send Ready
    	//get ready to start receving jobs
    	str = din.readLine();
    	System.out.println(str);
   	dout.write(("REDY\n").getBytes());
	dout.flush();
	
	//Gets message to request servers
	str = din.readLine();
	System.out.println(str);
	
	String[] job = str.split(" ", 10); 
	
	dout.write(("GETS All\n").getBytes());
	dout.flush();
	
	//get Data of each server. 
	str = din.readLine();
	System.out.println(str);
	
	int nRecs = Integer.parseInt(String.valueOf(str.charAt(5)));
	System.out.println(nRecs);
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	//variables to store largest server core and largetst server DATA
	int j = 0;
	String[] large = new String[10];
	
	for(int i = 0; i < nRecs; i++) {
		str = din.readLine();
		System.out.println(str);
		
		String[] arr = str.split(" ", 9);
		int coreNum = Integer.parseInt(String.valueOf(arr[4]));
		
		if (j < coreNum) {
			j = coreNum;
			large = arr;
		} 
	}	
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	dout.write(("SCHD " + job[2] + " " + large[0] + " " + large[1] + "\n").getBytes());
	dout.flush();
	
	dout.write(("OK\n").getBytes());
	dout.flush();
	
	//Quit message to cease processing gracefully
	dout.write(("QUIT\n").getBytes());
	dout.flush();
    	dout.close();  
    	s.close();  
    	
    } catch (Exception e)
    	{System.out.println(e);
    	}  
    }  
}  
