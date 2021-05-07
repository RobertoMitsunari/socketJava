package keyboard;

import java.net.*;
import java.util.Base64;
import java.io.*;  

public class KeyboardClient {
	public static void main(String args[])throws Exception{  
		Socket socket = new Socket("localhost",9002);  
		
		DataInputStream socket_in 
					= new DataInputStream(socket.getInputStream());  
		DataOutputStream socket_out 
					= new DataOutputStream(socket.getOutputStream());  
		
		BufferedReader keyboard_input 
				  	= new BufferedReader(new InputStreamReader(System.in));
		
		String[] msg = {"Usuario1","1234","3*2","fim"};
		int cont = 0;
		String str="",str2="";  
		while(!str2.equals("fim")){  
			str = msg[cont];
			socket_out.writeUTF(criptografiaBase64Encoder(str));  
			socket_out.flush();
			str2 = socket_in.readUTF();  
			System.out.println("O servidor falou: " + str2);
			cont++;
		}
		
		boolean valid = socket_in.readBoolean();
		System.out.println("O servidor falou: " + valid);
		if(valid) {
			String token = socket_in.readUTF();
			System.out.println("O servidor falou: " + token);
		}
		
		int resul = socket_in.readInt();
		if(resul == 6) {
			System.out.println("É valido");
		}

		
		  
		socket_out.close();  
		socket.close();  
	}
	
	// Criptografando 
	public static String criptografiaBase64Encoder(String pValor) {
	    return new String(Base64.getEncoder().encode(pValor.getBytes()));
	}
}
