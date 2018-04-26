package astate.chat;




/*
 * 
 */
public class Facade {
	
	SQLserver s=SQLserver.getInstance();
	
	//
	public void ConnectSQL(){
		
		s.ConnectSQL();
	}
	
	//
	public void SQLverify(String a,String b){
		s.SQLverify(a, b);
		
	}

	//
	public void zhuceverify(String a){
		s.ZhuceVerify(a);
		
	}
	public int LoginVerify() {
		return s.LoginVerify();
		
	}
	
}