package home; 
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial") // Warnning 무시
public class menu extends Frame implements ActionListener {
	
	Button start=new Button("start");
	Button close=new Button("close");
	
	
	public menu(){
		super("start");
		setLayout(new BorderLayout());
		Label t1 = new Label("시작");
		Panel p1 = new Panel();
		p1.add(start);
		p1.add(close);
	
		
		start.addActionListener(this);
		close.addActionListener(this);
		add(p1,BorderLayout.NORTH);
		setSize(400,200);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(obj==start)
		{
			game_Frame fms = new game_Frame();
		}
		else if(obj==close){
			System.exit(0);
		}
	}
	
	}