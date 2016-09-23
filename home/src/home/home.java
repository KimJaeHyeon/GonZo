package home;
import java.awt.*;

public class home extends Frame {

	public home() {
		// TODO Auto-generated method stub
      super();
      setLayout(new BorderLayout());
      Label t1 = new Label("     ID        ");
      Label t2 = new Label("Password");
      TextField id = new TextField(10);
      TextField pwd = new TextField(10);
      Button btn1 = new Button("로 그 인");
      Button btn2 = new Button("회원가입");
      
      Panel p1 = new Panel();
      p1.add(t1);
      p1.add(id);
      p1.add(btn1);
      
      Panel p2 = new Panel();
      p2.add(t2);
      p2.add(pwd);
      p2.add(btn2);
      
      add(p1,BorderLayout.NORTH);
      add(p2,BorderLayout.SOUTH);
      
      setSize(300,100);
      setVisible(true);
	}
	
	public static void main(String []args)
	{
		home app = new home();
	}

}
