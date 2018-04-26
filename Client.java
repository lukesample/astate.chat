package astate.chat;

import java.io.*;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener
{
	DataInputStream dis = null;
    DataOutputStream dos = null;
    final static int ServerPort = 12345;
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatBox;
    JButton     logoutButton;
    JButton		activeUsersButton;
    JFrame      newFrame    = new JFrame("Astate.Chat");
    String username;
    Socket s = null;
    
    
    public Client(String user)
    {
    	
    	this.username = user;
    }
    
    public void start() throws UnknownHostException, IOException 
    {
    	newFrame.setTitle("Astate.chat - " + username);
    	
        InetAddress ip = InetAddress.getByName("localhost");
        
        //establish the connection to the server
        s = new Socket(ip, ServerPort);
        
        //obtaining input and out streams
        dis = new DataInputStream(s.getInputStream());
        dos =  new DataOutputStream(s.getOutputStream());
         
        dos.writeUTF(username);

        //readMessage thread
        Thread readMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {
                while (dos != null && dis != null) {
                    try 
                    {
                        //read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                        chatBox.append(msg);
                    } //end try
                    catch (IOException e) 
                    {
 
                        e.printStackTrace();
                    }//end catch
                }//end while
            }//end run
        });
        display();
        readMessage.start();
    }


    //not sure what this needs to do? just to satisfy an error
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 Object obj = e.getSource();
	       if(obj == sendMessage){ 
	    	 	String msg = messageBox.getText();
                try {
					dos.writeUTF(msg);
				 	chatBox.append(username + ": " + msg + "\n"); //place what the user sent in the text box
				 	messageBox.setText(""); 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	       } 
	       else{}
	}
		
	
	public void display() throws IOException 
	{
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.RED);
        southPanel.setLayout(new GridBagLayout());
        
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.RED);
        northPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        //sendMessage.addActionListener(new sendMessageButtonListener());
        sendMessage.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
        sendMessage.addActionListener(this);
        
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new logoutButtonListener());
        
        activeUsersButton = new JButton("Active Users");
        activeUsersButton.addActionListener(new activeUsersButtonListner());
        
        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        
        northPanel.add(activeUsersButton, left);
        northPanel.add(logoutButton, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.add(BorderLayout.NORTH, northPanel);
        
        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
	}
       

	class sendMessageButtonListener implements ActionListener 
    {
		
        public void actionPerformed(ActionEvent event) 
        {
            if (messageBox.getText().length() < 1) 
            {
                // nothing to do, empty box
            } 
            
            else if (messageBox.getText().equals(".clear")) 
            {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } 
            
            else 
            {
            	if (dos != null) //stream is open still
            	{
            		try 
            		{
						//write on the output stream
                    	String msg = messageBox.getText();
                        dos.writeUTF(msg);
                        if(msg.equals(".logout"))
                        {
                        	dos.close(); //close output streams and set them 
                        	dis.close(); //to null to stop attempting to read/write
                        	dos = null;
                        	dis = null;
                        	newFrame.setVisible(false); //close the chat box
                        	s.close(); //close the socket
                        }//end if
                        
                        else
                        {
                        	chatBox.append(username + ": " + msg + "\n"); //place what the user sent in the text box
                        	messageBox.setText(""); 
                        }//end else
                        
                    } //end try
            		catch (IOException e) 
            		{
                        e.printStackTrace();
                    }//end catch
            		
            	}//end if
            	
            }//end outer else
            messageBox.requestFocusInWindow();
        }//end function
    }//end sendMessageButtonListener class
	
	class logoutButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) 
        {
			try 
    		{
				dos.writeUTF(".logout");
				dos.close(); //close output streams and set them 
            	dis.close(); //to null to stop attempting to read/write
            	dos = null;
            	dis = null;
            	newFrame.setVisible(false); //close the chat box
            	s.close();
    		}
			catch (IOException e) 
    		{
                e.printStackTrace();
            }
        }
	}//end of logoutButtonListener
	
	
	class activeUsersButtonListner implements ActionListener{
		public void actionPerformed(ActionEvent event) 
        {
			try 
    		{
				dos.writeUTF(".activeUsers");
    		}
			catch (IOException e) 
    		{
                e.printStackTrace();
            }
        }
	}//end of activeUsersButtonListener
	
	
}//end Client class

