import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class MainPage extends JFrame{
	
	public static final int X=1024;
	public static final int Y=600;

	
	public MainPage(){
		super();
		init();
	}
	
	private void init(){
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(X,Y));
		this.setLocationRelativeTo(null); // appear at centre
		this.setVisible(true);
	}
}
