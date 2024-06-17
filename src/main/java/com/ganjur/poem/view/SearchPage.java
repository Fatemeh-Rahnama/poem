package com.ganjur.poem.view;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ganjur.poem.view.MainPage;
import com.ganjur.poem.model.HttpReq;
import com.ganjur.poem.model.Poem;
import com.ganjur.poem.App;

import java.util.List;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

public class SearchPage extends JPanel{

	private static final String PAGE_NUMBER="1";
	private static final String PAGE_SIZE="5";
	
	private JPanel inputP;
		private JLabel inputL;
		private JTextField inputTF;
		private JButton submitBtn;

	private JScrollPane outP;
		private JTextArea outTA; // for output

	public SearchPage(){
		super();
		this.init();
	}

	private void init(){
		initInput();
		initOutput();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(this.outP);
		this.add(this.inputP);
	}

	private void initInput(){

		this.inputL=new JLabel("لغت مورد نظر");
		this.inputL.setFont(new Font("Serif",Font.BOLD,24));
		this.inputL.setBorder(new EmptyBorder(10,10,10,10));

		this.inputTF=new JTextField();
		this.inputTF.setFont(new Font("Serif",Font.BOLD,24));
		this.inputTF.setMaximumSize(new Dimension(400,50));
		this.inputTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		this.submitBtn=new JButton("جستجو");
		this.submitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				requestPoems();
			}
		});
		this.submitBtn.setFont(new Font("Serif",Font.BOLD,24));

		this.inputP=new JPanel();
		this.inputP.setLayout(new BoxLayout(inputP,BoxLayout.LINE_AXIS));
		this.inputP.setBorder(new EmptyBorder(10,10,10,10));
		this.inputP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.inputP.setPreferredSize(new Dimension(MainPage.X/2,MainPage.Y/4));

		this.inputP.add(inputL);
		this.inputP.add(inputTF);
		this.inputP.add(submitBtn);
	}

	private void initOutput(){

		this.outTA=new JTextArea();
		this.outTA.setFont(new Font("Serif",Font.BOLD,24));
		this.outTA.setMargin(new Insets(10, 10, 10, 10));
		this.outTA.setEditable(false);

		this.outP=new JScrollPane();
		this.outP.setViewportView(this.outTA);
		this.outP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.outP.setMaximumSize(new Dimension(MainPage.X-100,MainPage.Y-100));
	}

	private void requestPoems(){
		String term=this.inputTF.getText();
		if(term.isEmpty()) return; // do nothing
		
		try{
			term=URLEncoder.encode(term, "UTF-8");
		}catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
			App.showErrMsg("خطا در انکد کردن آدرس سایت. کنسول را چک کنید");
			return;
		}

		String url=App.API_SEARCH_POEMS
			+"?term="+term
			+"&pageNumber="+PAGE_NUMBER
			+"&pageSize="+PAGE_SIZE
		;

		System.out.println("fetching "+url);

		// init data
		List<Poem> ps=Poem.listFromJson(HttpReq.httpGet(url));
		if(ps.size()==0){
			this.outTA.setText("یافت نشد!");
			return ;
		}
		System.out.println("[received_poems]");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<ps.size();i++)
			sb.append(ps.get(i).toString());
		this.outTA.setText(sb.toString());
		this.outTA.setCaretPosition(0);
	}

}
