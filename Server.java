package astate.chat;


import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    //vector to store active clients, static so threads share the vector of clients
    static Vector<ClientHandler> activeUsers = new Vector<>();
     
    public static void main(String[] args) throws IOException 
    {
        ServerSocket serverSocket = new ServerSocket(12345); //server is listening on port 12345, never closed
        Socket s; //declared globally so ClientHandler class can close sockets
         
        while (true) //infinite loop for getting client request
        {
            s = serverSocket.accept(); //accept the incoming request
             
            //obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            
            String username = dis.readUTF(); //username is first thing sent from the client for naming
            ClientHandler newClient = new ClientHandler(s,username , dis, dos); //create a new handler object for handling this client
            
            //notify currently connected users that a new user has joined
            newClient.notifyUsers(activeUsers, " has joined the server!");
            
            Thread t = new Thread(newClient); //create a new Thread with this object.
            activeUsers.add(newClient); //add this client to active clients list
            t.start(); //start the thread.
        }//end while
    }//end main
}//end class Server


// ClientHandler class
class ClientHandler implements Runnable 
{
    private String name;
    DataInputStream dis;
    DataOutputStream dos;
    Socket socket;
    boolean isloggedin;
    
    //send a message to all currently logged in users
    //used to notify when user joins and disconnects from the server
    public void notifyUsers(Vector<ClientHandler> activeUsers, String message) throws IOException
    {
    	for(ClientHandler client : Server.activeUsers)
    	{
    		client.dos.writeUTF("SERVER: " + this.name + message + "\n");
    	}
    }
    
    //write the active user list to the screen
    public void getActiveUsers(Vector<ClientHandler> activeUsers) throws IOException
    {
    	for(ClientHandler client : Server.activeUsers)
    	{
    		this.dos.writeUTF(client.name + " | ");
    	}
    	this.dos.writeUTF("\n");
    }
     
    //constructor for ClientHandler
    public ClientHandler(Socket socket, String name, DataInputStream dis, DataOutputStream dos) 
    {
        this.dis = dis; //data input stream
        this.dos = dos; //data output stream
        this.name = name; //client name
        this.socket = socket; //socket
        this.isloggedin=true; //handler is being created so user is logged in
    }
    
    @Override
    public void run() {
        String received;
        try {
			dos.writeUTF("To use group chat, send a message.  To use peer to peer, type a message then @username\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        while (true) 
        {
            try
            {
            	
                received = dis.readUTF(); //receive the string

                if(received.equals(".logout")){ //if user wishes to log out, close IO streams, 
                    this.isloggedin=false;	    //socket connection, and remove from activeUser list
                    this.notifyUsers(Server.activeUsers, " has left the server.");
                    this.dis.close();
                    this.dos.close();
                    this.socket.close();
                    Server.activeUsers.removeElement(this);
                    break;
                }
                
                else if(received.equals(".activeUsers")) //command to display the active users
                {
                	this.dos.writeUTF("Active user list: ");
                	this.getActiveUsers(Server.activeUsers);
                }
                
                else {
                	//break the string into message and recipient part
                	//MsgToSend is before the @ symbol, recipient is after
                	StringTokenizer st = new StringTokenizer(received, "@");
                	String MsgToSend = st.nextToken();
                	
                	//check if there is a next token, attempt to read, set recipient to null if not there
                	String recipient = st.hasMoreTokens() ? st.nextToken() : null; 
                
                	if (recipient != null) //if the delimiter was read, write to the specific client
                	{	
                		//search for the recipient in the active user list.
                		//activeUsers is the vector storing client of active users
                		//if (Server.activeUsers.contains)
                		boolean found = false;
                		for (ClientHandler client : Server.activeUsers) 
                		{
                			//if the recipient is found, write on its output stream
                			//p2p chat
                			if (client.name.equals(recipient) && client.isloggedin==true) 
                			{
                				client.dos.writeUTF(this.name+ ": " + MsgToSend + "\n");
                				found = true;
                				break;
                			} //end if
                			
                		}//end for
                		
                		if(found == false) //recipient wasn't found, notify the sender
            			{
            				this.dos.writeUTF("SERVER: " + recipient + " is not currently logged in.\n");
            			}
                
                	}//end if
                	
                	else //group chat, recipient is null, send to everyone
                	{
                		for (ClientHandler client : Server.activeUsers)
                		{
                			if (client.name != this.name) //send the message to everyone but the user who sent it (this)
                				client.dos.writeUTF(this.name + ": " + MsgToSend + "\n");
                		}//end for
                		
                	}//end else
                	
                }//end outer else
                
            }//end try
            catch (IOException e) 
            {
            	e.printStackTrace();
            }//end catch
            
        }//end while
       
    }//end run
    
}//end class ClientHandler
