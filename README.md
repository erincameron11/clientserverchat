# Client-Server Chat Application

*Erin Cameron, Algoma University COSC4436 Computer Networks*

---

## Introduction
**Purpose**: communication of information across distributed machines

**Approach**: implemented using client-server socket programming with Transmission Control Protocol (TCP)

**Communication Process**:   
* Client – types message and sends data through output channel.  
* Server – receives message displayed on screen, types response message, and sends the response through output channel.  
* Client – receives the response and is displayed on the screen.  


## Application Features
### Chat Initialization
#### Server Connection:
First we must compile and run the program in two Terminal windows. Open a Terminal window for the Server, and follow the instructions below:
1. Ensure that Java is installed on your machine. To do so enter `java --version` into your Terminal. If it is not installed, visit `www.java.com` to install.
2. Navigate to the project directory that houses the Client and Server files
3. Type `javac ChatServer.java` and press Enter. It will take 2-5 seconds to process.
4. Type `java ChatServer.java` and press Enter. Your server is now running and waiting for clients. The message “Connection pending…” will display on the screen.
*Important Note: the server must be running first before attempting to connect the client to the server.*

#### Client Connection:
Open a second Terminal window for the Client, and follow the instructions below:
1. Navigate to the project directory that houses the Client and Server file. 
2. Type `javac ChatClient.java` and press Enter. It will take 2-5 seconds to process.
3. Type `java ChatClient.java` and press Enter.
4. The client is now prompted to enter an IP address. If running both the Server and Client locally, use `0.0.0.0` or `127.0.0.1`. If running on two separate machines, use the IP address for the machine that the Server is running on. The messages `Connection pending on IP: <ip_address>` and `Connection established on IP address <ip_address>` will display on the screen if the connection is successful. The connection is now formed between Client and Server.
5. The Client must then enter a Nickname they would like to be called for the duration of the chat. The Client is now connected to the chat with the Server.


### Chat Operation
After Chat Initialization, all chat operations take place inside the two Terminal windows. Starting with the Client, users can now message each other alternating on each Terminal window between Client and Server.

### Chat Termination
#### Server Disconnection:
The Server is automatically disconnected when the Client enters a specified phrase into the chat application Terminal.

#### Client Disconnection:
To terminate the chat, the Client must type and enter a specified phrase into the chat to end the connection. In this application, the phrase to terminate the chat is “quit”.

## Tool and Techniques
The entirety of the chat application is programmed in the Java programming language. In order to write, edit, and compile the application, the Eclipse Integrated Development Environment (IDE) was used.

Two Java packages were imported to write the code:
* `java.io.*`  – this package was utilized for input and output within the application. The BufferedReader, PrintStream and InputStreamReader classes all require this package to function.
* `java.net.*`  – this package was utilized for creating and connecting to sockets within the application. The Socket and ServerSocket classes require this package to function.

Some additional techniques were used within the application to help with the design and overall look and feel of the interface:
* American National Standards Institute (ANSI) colour codes have been utilized to colour the output in the Terminal. Note that this feature may not appear correctly for all operating systems.
  * `ANSI_YELLOW` – used for pending connections
  * `ANSI_GREEN` – used for established connections
  * `ANSI_CYAN` – used for the Server’s name
  * `ANSI_PURPLE` – used for the Client’s name
  * `ANSI_RED` – used for the termination of connections
* bash commands have also been utilized within this application to change the titles of the Terminal windows, and to clear the command line interface (CLI) when starting a new chat.
  * In order to title the Terminal windows, bash commands were output to the CLI to change the window titles to `CLIENT` and `SERVER`.
  * To clear the Terminal window prior to a chat taking place, a bash command was used to flush the Terminal window, and clear the current visible output to allow for the chat to take place in a clear window.

## Implementation Details
* Sockets: The creation of a Server Socket is done by instantiating a ServerSocket class object with the Port specified as a parameter. The ServerSocket class uses the server’s machine IP address to create the socket on. The creation of a Client Socket is done by instantiating a Socket class object with the IP address and Port specified as parameters.
* Input/Output: Both Client and Server code include BufferedReader, InputStreamReader, and PrintStream for input and output operations.
* Client Message Handling: When a Client sends a message, the following actions occur. The display name of the Client is entered into the chat, and the next line of text that the Client types is read into the String variable, “message”. The message is sent to the output stream and sent to the Server. The `Server: …` server-is-responding text is displayed in the Client’s terminal window while the Server takes the time to respond to the Client’s message. When the Server has responded, the response is read into the message variable, and displayed as output in the terminal window. The loop continues indefinitely until the Client enters the predetermined phrase “quit” to terminate the chat. When the user enters the phrase “quit” into the chat, the loop executes as normal and waits for a reply from the Server. The Server replies with “Terminated”, and this is what triggers the termination of the connection.
* Server Message Handling: When a Server sends a message, the following actions occur. The message entered by the Client is read into a “message” variable. If the text sent by the Client is “quit”, then the message is displayed, and the connection is terminated. Otherwise, if the message is not “quit”, the client-is-responding text is removed from the terminal window, and the message from the Client is displayed. The display name of the Server is entered into the chat, and the next line of text that the Server types is read into the String variable, “message”. The message is sent to the output stream and sent to the Client. The `<ClientName>: …` client-is-responding text is displayed in the Server’s terminal window while the Client takes the time to send a message. The loop continues indefinitely, until the Client terminates the chat with the phrase “quit”.
* Utility: `ChatServer.java`’s findBackspaces function is used to locate and erase the correct number of characters from the command line interface, based on the length of the String used for the Nickname of the Client. This is used when altering the output in the Terminal from `<ClientName>: …` to the client’s actual message.
* Connection Termination: To terminate each connection, the code first breaks out of the indefinite while loop. Then, each open socket, input and output sources are closed using .close(). For the `ChatServer.java` source code, it is important to note that there is also a line of code included that closes the ServerSocket: `ss.close();`.
