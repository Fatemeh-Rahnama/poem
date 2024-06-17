package com.ganjur.poem.view;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class MainPage extends JFrame{
	
	public static final int X=1024;
	public static final int Y=600;

	private FalPage fp;
	private PoetPage pp;
	private SearchPage sp;


	private Component currentPane; // required for navigation

	public MainPage(){
		super();
		init();
	}

	public void gotoFalPage(){
		gotoPage(this.fp);
	}

	public void gotoPoetPage(){
		gotoPage(this.pp);
	}
	
	public void gotoSearchPage(){
		gotoPage(this.sp);
	}


	private void init(){

		initPanels();

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(X,Y));
		this.setLocationRelativeTo(null); // appear at centre
		this.setVisible(true);
	}

	private void initPanels(){
		
		// set header (will not change during program)
		setLayout(new BorderLayout());
		add(new Header(this),BorderLayout.NORTH);

		// these will be changed during runtime
		this.fp=new FalPage();
		this.pp=new PoetPage();
		this.sp=new SearchPage();

		gotoFalPage();
	}


	private void gotoPage(JPanel page){
		if(page==currentPane) return ;
		if(currentPane!=null) // beware of first init
			this.remove(currentPane);
		
		// change the center of out layout
		this.add(page,BorderLayout.CENTER);
		this.currentPane=page;
		
		// necessary for changes to take place
		this.validate();
		this.repaint();

	}
}
