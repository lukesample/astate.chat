package astate.chat;



import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;
/*
 * 
 */

class SQLserver {

	Connection ct;
	PreparedStatement ps;
	ResultSet rs;
	String user,pwd;
	public static int i;
	
	/*
	 * 
	 */
	private SQLserver()
	{
		
	}
	private static final SQLserver ss=new SQLserver();
	
	public static SQLserver getInstance()
	{
		return ss;
		
	}
	
	//
	public void ConnectSQL()
	{
		try {
			  String url = "jdbc:postgresql://147.97.156.245/yuchen.zhou"; 
		      //String     url = "jdbc:postgresql://127.0.0.1/suh"; 
		      //Connection con = DriverManager.getConnection(url, "postgres", ""); 
		      Properties props = new Properties();
		      props.setProperty("user","yuchen.zhou");
		      props.setProperty("password","50440669");
		      props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		      props.setProperty("ssl","true");   //for ssl
		      ct = DriverManager.getConnection(url, props);
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //
			
			//ct=DriverManager.getConnection("jdbc:odbc:ywq"); //
		     // Statement  stm = ct.createStatement(); 

		     // stm.setQueryTimeout(10); 
		     // ResultSet  rs  = stm.executeQuery("select * from users"); 
		 
		    //  rs.next(); 
		 
		     // System.out.println(rs.getString(1)); 
			
			System.out.println("successfully connect PSQL DataBase...");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception1!"); 
		    System.out.println(e.toString()); 
		}
	}
	
	//
	public void UserRegis(String a,String b,String c,String d)
	{
		//
		try {
			ps=ct.prepareStatement("insert into users values(?,?,?,?)");
			ps.setString(1,a);
			ps.setString(2,b);
			ps.setString(3,c);
			ps.setString(4,d);
			
			//
			int i=ps.executeUpdate();
			if(i==1)
			{
				JOptionPane.showMessageDialog(null, "register success","notification",JOptionPane.WARNING_MESSAGE);
			    
			}else
			{
				JOptionPane.showMessageDialog(null, "register fail","notification",JOptionPane.ERROR_MESSAGE);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	
	public void SQLverify(String a,String b)
	{
		try {
			ps=ct.prepareStatement("select * from users where username=? and password=? ");
			ps.setString(1, a);
			ps.setString(2, b);
			
			// ResultSet
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				user = rs.getString(1);
				pwd = rs.getString(2);
				JOptionPane.showMessageDialog(null, "login successful", "notification", JOptionPane.WARNING_MESSAGE);
				System.out.println("successfully verify the user name and password from DataBase");
				System.out.println(user + "\t" + "\t" + pwd + "\t");
				i =1;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "user name or password incorrectly (please input again)", "notification", JOptionPane.ERROR_MESSAGE);
			    i = 0;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	//
	public void ZhuceVerify(String a)
	{
		try {
			ps=ct.prepareStatement("select * from users where username=?");
//			System.out.println(ps);
			ps.setString(1, a);
			
			rs=ps.executeQuery();
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null, "this user name has been used", "notification", JOptionPane.WARNING_MESSAGE);
			}else
			{
//				
//				UI ui=new UI();
				this.UserRegis(UI.jtf1.getText(),UI.jtf2.getText(),UI.jtf3.getText(),UI.jtf4.getText());
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public int LoginVerify() {
		return i;
	}
}