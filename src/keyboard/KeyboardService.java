package keyboard;

import java.net.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.io.*;  
class KeyboardService{  
	public static void main(String args[])throws Exception{  
		ServerSocket socket_server=new ServerSocket(9002);  
		Socket socket = socket_server.accept();  
		
		DataInputStream socket_in     
				= new DataInputStream(socket.getInputStream());  
		DataOutputStream socket_out   
				= new DataOutputStream(socket.getOutputStream());  
		
		BufferedReader keyboard_in    
				= new BufferedReader(new InputStreamReader(System.in));
		
		ArrayList<String> lista = new ArrayList<String>();
		String str="", str2="";  
		while(!str.equals("fim")){  
			str = descriptografiaBase64Decode(socket_in.readUTF());
			System.out.println("O cliente falou: "+str);
			lista.add(str);
			socket_out.writeUTF(str);
			socket_out.flush();

		}
		if(lista.get(0).equals("Usuario1") && lista.get(1).equals("1234")) {
			socket_out.writeBoolean(true);
			socket_out.flush();
			socket_out.writeUTF(UUID.randomUUID().toString());
			socket_out.flush();
		}
		String equa = lista.get(2);
		int resul = Integer.parseInt(equa.substring(0,1)) * Integer.parseInt(equa.substring(2,3));
		socket_out.writeInt(resul);
		socket_out.flush();
		
		
		socket_in.close();  
		socket.close();  
		socket_server.close();  
	}
	// Descriptografando 
	public static String descriptografiaBase64Decode(String pValor) {
	    return new String(Base64.getDecoder().decode(pValor.getBytes()));
	}
}  