package application;

import java.net.*;
import java.io.*;

public class ChatClient {
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
		String message;
		String client = "";
		
		// Setting the clients nickname
		System.out.println(ANSI_GREEN + "\nConnection established on IP address " + ip + "\n" + ANSI_RESET);
		System.out.print("Enter a Client Nickname: ");
		client = input.readLine();
		
		// Sending the clients nickname to the server
		dataOut.println(client);
		
		// Clear the terminal screen
		System.out.print("\033[H\033[2J");  
	    System.out.flush();  
		
	    // Loop until the client enters "quit" and the server responds "Terminated"
		while (true) {
			// Print the display name of the client
			System.out.print(ANSI_PURPLE + client + ": " + ANSI_RESET);
			
			// Read the line entered by the client
			message = input.readLine();
			
			// Send the line to the output stream (server)
			dataOut.println(message);
			
			// Print the server-is-responding text "Server: ..."
			System.out.print(ANSI_CYAN + "Server: " + ANSI_RESET + "...");
			
			// Read the line from the server
			message = dataIn.readLine();
			
			// Delete the server-is-responding text and print the line from the server
			System.out.print(ANSI_CYAN + "\b\b\b\b\b\b\b\b\b\b\bServer: " + ANSI_RESET + message + "\n");
			
			// If the client has initiated a "quit", terminate
			if (message.equalsIgnoreCase("TERMINATED")) {
				System.out.println(ANSI_RED + "\n*** Connection terminated ***\n" + ANSI_RESET);
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