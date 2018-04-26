package astate.chat;



import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UserRegister extends JFrame implements ActionListener{
	
		Facade fcd=new Facade();
		JButton jb1,jb2,jb3=null;
		String appName = "Astate.Chat";
		JRadioButton jrb1,jrb2=null;
		JPanel jp1,jp2,jp3, jp4=null;
	    JTextField jtf=null;
		JLabel jlb1,jlb2=null;
		JPasswordField jpf=null;
	    static String  username;
	    
	    Container ct;
	    BackgroundPanel bgp;
	    
	    class BackgroundPanel extends JPanel  
	    {  
	        Image im;  
	        public BackgroundPanel(Image im)  
	        {  
	            this.im=im;  
	            this.setOpaque(true);  
	        }  
	        //Draw the back ground.  
	        public void paintComponent(Graphics g)  
	        {  
	            super.paintComponents(g);  
	            g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  
	              
	        }  
	    }
	    		
	
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		UserRegister ur=new UserRegister();	
		
	}
	
	public UserRegister()
	{
        ct=this.getContentPane();  
        this.setLayout(null);  
          
        bgp=new BackgroundPanel((new ImageIcon("/Users/Luke/eclipse-workspace/astate.chat/src/astate/chat/1.jpg")).getImage());  
        ct.add(bgp);  
        
        this.setVisible(true);  

		//this.getContentPane().setBackground(Color.red);
		
		jb1=new JButton("login");
		jb1.setForeground(Color.red);
		jb2=new JButton("register");
		jb3=new JButton("exit");
		jb3.setForeground(Color.DARK_GRAY);

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		
		JLabel lb0 = new JLabel("Arkansas State University");
		jlb1=new JLabel("UserName");
		jlb2=new JLabel("Password");
		 lb0.setForeground(Color.RED);  
	     lb0.setFont(new Font("Verdana", Font.BOLD, 16));
	     jlb1.setFont(new Font("Verdana", Font.BOLD, 12));
	     jlb2.setFont(new Font("Verdana", Font.BOLD, 12));
		
		jtf=new JTextField(10);
		jpf=new JPasswordField(10);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		
		jp4.add(lb0);
		
		jp1.add(jlb1);
		jp1.add(jtf);
		
		jp2.add(jlb2);
		jp2.add(jpf);
		
		jp3.add(jb1);
		jp3.add(jb2);
		jp3.add(jb3);
		
		this.add(jp4);
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);

		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Astate.Chat");
		this.setLayout(new GridLayout(5,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(350, 250, 300, 400);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand()=="exit")
		{
			System.exit(0);
		}else if(e.getActionCommand()=="login")
		{
			if(jtf.getText().isEmpty()||jpf.getText().isEmpty())
				JOptionPane.showMessageDialog(null, "please import right combination", "Warning", JOptionPane.WARNING_MESSAGE);
			else
				
				try {
					username = jtf.getText();
					this.login();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "please run server", "Warning", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "please run server", "Warning", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
		}else if(e.getActionCommand()=="register")
		{
			this.Regis();
		}
		
	}
	
     public void Regis() {
    	 
    	 this.dispose();
    	 new UI(); 
    	
	}

	public void login() throws UnknownHostException, IOException {		
		//fcd.ConnectSQL();
		//fcd.SQLverify(jtf.getText(), jpf.getText());
		username = this.jtf.getText();
		this.jtf.setText("");
		this.jpf.setText("");
		
		
		/* authentication disabled for testing purposes
		if(fcd.LoginVerify()==0) { //user did not successfully login
			new UserRegister(); //try again
		}
		else { //user successfully logged in, create a new client and start running the client
			Client c = new Client(username);
			c.start();
		}
		*/
		Client c = new Client(username);
		c.start();
		this.setVisible(false); //close the login screen
	}
}