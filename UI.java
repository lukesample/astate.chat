package astate.chat;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;


/*
 * 
 */
class UI extends JFrame implements ActionListener{
	
	
     //
	Facade fcd=new Facade();

	//
	JFrame jf;
	JPanel jp;
	JLabel jl1,jl2,jl3,jl4;
	static JTextField jtf1,jtf2,jtf3,jtf4;
	JButton jb1,jb2;
	
	public UI()
	{
		//
		jf=new JFrame();
		jp=new JPanel();
		jl1=new JLabel("Input User Name");
		jtf1=new JTextField(20);
		jtf1.setToolTipText("User name has to be 3-16 chars");
		jl2=new JLabel("Input Password");
		jtf2=new JTextField(20);
		jtf2.setToolTipText("Password has to be 6-16 chars");
		jl3=new JLabel("Input Name");
		jtf3=new JTextField(20);
		jtf3.setToolTipText("Name has to be 1-16 chars");
		jl4=new JLabel("Input Id");
		jtf4=new JTextField(20);
		jtf4.setToolTipText("ID number has to be 8 digits");
		
		jb1=new JButton("back");
		jb1.setToolTipText("back to the login page");
		jb2=new JButton("register");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		jp.setLayout(new GridLayout(5,2));
		
		jp.add(jl1);
		jp.add(jtf1);
		
		jp.add(jl2);
		jp.add(jtf2);
		
		jp.add(jl3);
		jp.add(jtf3);
		
		jp.add(jl4);
		jp.add(jtf4);
		
		jp.add(jb1);
		jp.add(jb2);
		
		this.add(jp);
		this.setTitle("register page");
		this.setBounds(200, 100, 250, 150);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setResizable(false);
		
	}
	

	public void actionPerformed(ActionEvent e) {
	
		if(e.getActionCommand()=="back")
		{
			this.dispose();
			new UserRegister();
//			System.out.println("-------");
			
		}else if(e.getActionCommand()=="register")
		{
				//
			this.zhuce();
			
		}
		
	}
   public void zhuce()
   {
	   String regex1="\\w{3,16}"; //it has to be 3-16 chars
		boolean flag1=jtf1.getText().matches(regex1);
		
		String regex2="\\w{6,16}"; //it has to be 6-16 chars
		boolean flag2=jtf2.getText().matches(regex2);
		
		String regex3="\\w{1,16}.*"; //it's supposed to be 1-16 chars
		boolean flag3=jtf3.getText().matches(regex3);
		
		String regex4="\\d{8}";  //it's supposed to be 8 digits
		boolean flag4=jtf4.getText().matches(regex4);
		
//		if(jtf1.getText()==null||jtf2.getText()==null||jtf3.getText()==null||jtf4.getText()==null)
		if(flag1==false)
		{
			JOptionPane.showMessageDialog(null, "wrong user name format, it has to be 3-16 chars", "notification", JOptionPane.WARNING_MESSAGE);
			jtf1.setText("");
		}else if(flag2==false)
		{
			JOptionPane.showMessageDialog(null, "wrong password combination, it has to be 6-16 chars", "notification", JOptionPane.WARNING_MESSAGE);
			jtf2.setText("");
		}else if(flag3==false)
		{
			JOptionPane.showMessageDialog(null, "wrong name format, it's supposed to be 1-16 chars", "notification", JOptionPane.WARNING_MESSAGE);
			jtf3.setText("");
		}else if(flag4==false)
		{
			JOptionPane.showMessageDialog(null, "wrong id number format,it's supposed to be 8 digits", "notification", JOptionPane.WARNING_MESSAGE);
			jtf4.setText("");
		}else
		{			
			//
//			 SQLserver ss=new SQLserver();
//	    	 ss.ConnectSQL();
//	    	 ss.ZhuceVerify(jtf1.getText());
			
//			SQLserver ss=SQLserver.getInstance();
//	    	 ss.ConnectSQL();
//	    	 ss.ZhuceVerify(jtf1.getText());
			
			//
			fcd.ConnectSQL();
			fcd.zhuceverify(jtf1.getText());
	    	 
//			ss.UserRegis(jtf1.getText(),jtf2.getText(),jtf3.getText(), jtf4.getText());
		    this.jtf1.setText("");
		    this.jtf2.setText("");
		    this.jtf3.setText("");
		    this.jtf4.setText("");
		    
		}
   }
}