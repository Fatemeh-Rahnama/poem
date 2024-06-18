package com.ganjur.poem.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;

import com.ganjur.poem.model.HttpReq;
import com.ganjur.poem.model.Poem;

import java.io.IOException;

import com.ganjur.poem.App;

import java.awt.Dimension;
import java.awt.ComponentOrientation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class FalPage extends JPanel{

	private JPanel submitP;
		private JButton submitBtn;
	
	private JScrollPane resP;
		private JTextArea resTA;
	
	public FalPage(){
		init();
	}

	private void init(){
		initTextArea();
		initSubmitButton();

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(resP);
		this.add(submitP);
		this.setBorder(new EmptyBorder(10,10,10,10));
	}

	private void initTextArea(){
		resTA=new JTextArea();
		resTA.setFont(new Font("Serif",Font.BOLD,30));
		resTA.setEditable(false);
		resTA.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		resP=new JScrollPane();
		resP.setViewportView(resTA);
		resP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}
	
	private void initSubmitButton(){	
		this.submitBtn=new JButton("فال تصادفی حافظ");

		// assigining button , a functionality
		this.submitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				requestFal();
			}
		});

		this.submitP=new JPanel();
		this.submitP.setAlignmentX(CENTER_ALIGNMENT);
		this.submitP.add(submitBtn);
		this.submitP.setMaximumSize(new Dimension(MainPage.X,MainPage.Y/5));
	}

	private void requestFal(){
		HttpReq req;
		try{
			req=new HttpReq(App.API_FAL_HAFEZ);
			req.send();
			if(req.hasError())
				App.showErrMsg(
					"Fetching '"
					+App.API_FAL_HAFEZ
					+"' failed (check out the console output for details)"
				);
			else initData(req);
		}catch(IOException ioe){
			ioe.printStackTrace();
			App.showErrMsg(
				"Fetching '"
				+App.API_FAL_HAFEZ
				+"' failed (check out the console output for details)"
			);
		}

	}

	private void initData(HttpReq req){
		Poem p=Poem.fromJson(req.getReponse()); // Poem object for easier json parsing
		this.resTA.setText(p.toString());
		this.resTA.setCaretPosition(0);
	}
}
