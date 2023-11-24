package application;

/* TO RUN: 
* 			1. Open two separate Terminal windows and navigate to this directory 
* 			2. In the first window, type "javac MyServer.java" followed by "java MyServer.java" 
* 			3. In the second window, type "javac MyClient.java" followed by "java MyClient.java" 
* 			You can now message back an forth on each Terminal window */

import java.net.*;
import java.io.*;

public class ChatClient {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RED = "\u001B[31m";

	public static void main(String[] args) throws Exception {
		System.out.print("\"\033]0;CLIENT\007\"");
		// Create input from the client
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		// Prompt user for IP address of server
		System.out.println("\nEnter the IP address of the server (format: 0.0.0.0): ");
		String ip = input.readLine();
		
		// Create a socket
		int port = 2000;
		Socket sk = new Socket(ip, port);
		System.out.println(ANSI_YELLOW + "\nConnection pending on IP: " + ip + "...\n" + ANSI_RESET);
		
		// Create input and output streams
		BufferedReader dataIn = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream dataOut = new PrintStream(sk.getOutputStream());
		
		// Declare and initialize variables
		String s;
		String client = "";
		
		// Setting the clients nickname
		System.out.println(ANSI_GREEN + "\nConnection established on IP address " + ip + "\n" + ANSI_RESET);
		System.out.print("Enter a Client Nickname: ");
		client = input.readLine();
		dataOut.println(client);
		
		// Clear the terminal screen
		System.out.print("\033[H\033[2J");  
	    System.out.flush();  
		
	    // Loop until the client enters "end" and the server responds "bye"
		while (true) {
			System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET); // Print the display name of the client
			s = input.readLine(); // Read the line entered by the client
			dataOut.println(s); // Send the line to the output stream (server)
			System.out.print(ANSI_CYAN + "Server: " + ANSI_RESET + "..."); // Print the server-is-responding text
			s = dataIn.readLine(); // Read the line from the server
			System.out.print(ANSI_CYAN + "\b\b\b\b\b\b\b\b\b\b\bServer: " + ANSI_RESET + s + "\n"); // Delete the server-is-responding text and print the line from the server
			
			// If the client has initiated an "end", terminate
			if (s.equalsIgnoreCase("BYE")) {
				System.out.println(ANSI_RED + "\n*** Connection terminated ***\n" + ANSI_RESET); // error for some reason
				break;
			}
		}
		
		// Close the socket
		sk.close();
		
		// Close the input/output streams
		dataIn.close();
		dataOut.close();
		
		// Close the user input
		input.close();
	}
}










//// Create input
////Scanner input = new Scanner(System.in);
//		
//// Create a socket on a port
//Socket socket = new Socket("localhost", 2000);
//
//// Create input and output streams, and buffered reader
////DataInputStream dataInput = new DataInputStream(socket.getInputStream());
////DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
//PrintStream dataOutput = new PrintStream(socket.getOutputStream());
//BufferedReader dataInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
//
//// Define new variables
//String str = "str";
//String client = "";
//
//// Setting the clients nickname
//System.out.print("Enter a Client Nickname: ");
//client = userInput.readLine();
////dataOutput.println(client);
//
//while(true) {
//	System.out.print(client + ": ");
//	str = dataInput.readLine(); // str = stdin.readLine();
//	dataOutput.println(str); // sout.println(str);
//	dataOutput.flush();
//	str = dataInput.readLine(); // str = sin.readLine();
//	System.out.print("Server: "+ str +"\n");
//	if (str.equalsIgnoreCase("end"))
//		break;
//}
//
////String str = "", str2 = "";
////while(!str.equals("stop")) {
////	str = br.readLine();
////	dataOutput.writeUTF(str);
////	dataOutput.flush();
////	str2 = dataInput.readUTF();
////	System.out.println("Server says: " + str2);
////}
//
//dataInput.close(); // close the data input stream
//socket.close(); // close the socket
//userInput.close(); // close the input
