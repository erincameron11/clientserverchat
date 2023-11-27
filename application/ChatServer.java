package application;

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
		
		// Create a port and socket on that port
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
		
		// Declare string variables
		String message;
		
		// Get the clients name from the client
		String client = dataIn.readLine();
		
		// Clear the terminal screen
		System.out.print("\033[H\033[2J");  
	    System.out.flush(); 
	    
	    // Display the client-is-responding text "<ClientName>: ..."
	    System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET + "...");
	    
		// Loop until the client enters "quit"
		while (true) {
			// Read the line entered by the client
			message = dataIn.readLine();
			
			// If the entered text from the client is "quit", terminate
			if (message.equalsIgnoreCase("quit")) {
				dataOut.println("Terminated");
				
				// Delete the client-is-responding text and print the line from the client
				System.out.print(ANSI_PURPLE + findBackspaces(client) + client + ": " + ANSI_RESET + message + "\n");
				
				// Display that the connection was terminated
				System.out.println(ANSI_RED + "\n*** Connection terminated by " + client + " ***\n" + ANSI_RESET);
				
				// Exit the loop
				break;
			}
			
			// Delete the client-is-responding text and print the line entered by client
			System.out.print(ANSI_PURPLE + findBackspaces(client) + client + ": " + ANSI_RESET + message + "\n");
			
			// Print the display name of the server
			System.out.print(ANSI_CYAN + "Server: " + ANSI_RESET);
			
			// Get the line entered by the server
			message = input.readLine();
			
			// Send the line from the server to the client
			dataOut.println(message);
			
			// Print the client-is-responding text
			System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET + "...");
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