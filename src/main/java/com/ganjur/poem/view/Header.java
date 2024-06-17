package com.ganjur.poem.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.ComponentOrientation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Header extends JPanel{
	
	private final MainPage parent;

	private JButton falNavBtn;
	private JButton searchNavBtn;
	private JButton poetNavBtn;

	private JLabel pageLabel;

	public Header(MainPage mp){
		this.parent=mp;
		init();
	}

	private void init(){
		initBtns();
		initLabel();
		this.setMinimumSize(new Dimension(MainPage.X,MainPage.Y/4));
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setBorder(new EmptyBorder(10,10,10,10));
	}

	private void initLabel(){
		this.pageLabel=new JLabel();
		this.pageLabel.setAlignmentX(RIGHT_ALIGNMENT);
		this.pageLabel.setFont(new Font("Serif",Font.BOLD,20));
		this.add(pageLabel);
	}

	private void initBtns(){
		
		this.falNavBtn=new JButton("فال حافظ");
		falNavBtn.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e){
				parent.gotoFalPage();
				pageLabel.setText("فال حافظ            ");
			}
		});
		this.add(falNavBtn);

		this.searchNavBtn=new JButton("جستجوی شعر");
		searchNavBtn.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e){
				parent.gotoSearchPage();
				pageLabel.setText("جستجوی شعر            ");
			}
		});
		this.add(searchNavBtn);

		this.poetNavBtn=new JButton("اطلاعات شعرا");
		poetNavBtn.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e){
				parent.gotoPoetPage();
				pageLabel.setText("اطلاعات شعرا            ");
			}
		});
		this.add(poetNavBtn);
	}

}
