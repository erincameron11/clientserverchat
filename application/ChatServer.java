package application;

/* TO RUN: 
* 			1. Open two separate Terminal windows and navigate to this directory 
* 			2. In the first window, type "javac MyServer.java" followed by "java MyServer.java" 
* 			3. In the second window, type "javac MyClient.java" followed by "java MyClient.java" 
* 			You can now message back an forth on each Terminal window */

import java.net.*;
import java.io.*;

public class ChatServer {
	// Define final variables for ANSI terminal colouring
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RED = "\u001B[31m";

	// Method: main method for running the program
	public static void main(String[] args) throws Exception {
		// bash code to rename the Terminal window
		System.out.print("\"\033]0;SERVER\007\"");
		
		// Create a socket
		int port = 2000;
		ServerSocket ss = new ServerSocket(port);
		
		// Establish connection between client and server
		System.out.println(ANSI_YELLOW + "\nConnection pending...\n" + ANSI_RESET);
		Socket sk = ss.accept();
		System.out.println(ANSI_GREEN + "\nConnection established on port " + port + "\n" + ANSI_RESET);
		
		// Create input and output streams
		BufferedReader dataIn = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream dataOut = new PrintStream(sk.getOutputStream());
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		// Declare string variable
		String s;
		
		// Get the clients name from the client
		String client = dataIn.readLine();
		
		// Clear the terminal screen
		System.out.print("\033[H\033[2J");  
	    System.out.flush(); 
	    System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET + "...");
	    
		// Loop until the client enters "quit"
		while (true) {
			s = dataIn.readLine(); // Read the line entered by the client
			
			// If the entered text from the client is "quit", terminate
			if (s.equalsIgnoreCase("quit")) {
				dataOut.println("Bye");
				System.out.print(ANSI_PURPLE + findBackspaces(client) + client + ": " + ANSI_RESET + s + "\n");
				System.out.println(ANSI_RED + "\n*** Connection terminated by " + client + " ***\n" + ANSI_RESET);
				break;
			}
			
			System.out.print(ANSI_PURPLE + findBackspaces(client) + client + ": " + ANSI_RESET + s + "\n"); // Delete the client-is-responding text and print the line entered by client
			System.out.print(ANSI_CYAN + "Server: " + ANSI_RESET);
			s = input.readLine(); // Get the line entered by the server
			dataOut.println(s); // Send the line from the server to the client
			System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET + "..."); // Print the client-is-responding text
		}
		
		// Close the server socket
		ss.close();
		
		// Close the socket
		sk.close();
		
		// Close the input/output streams
		dataIn.close();
		dataOut.close();
		
		// Close the user input
		input.close();
	}
	
	
	// Utility Method: used to erase the "<clientname>: ..." from the CLI
	public static String findBackspaces(String client) {
		// Find the number of backspaces required to append to the string
		String clientName = "";
		
		// Loop through and append the backspaces to the string
		for(int i = 0; i < (client.length() + 7); i++) {
			clientName = clientName + "\b";
		}
		return clientName;
	}

}