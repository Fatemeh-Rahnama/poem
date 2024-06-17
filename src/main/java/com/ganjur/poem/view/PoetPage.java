package com.ganjur.poem.view;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ganjur.poem.model.HttpReq;
import com.ganjur.poem.model.Poet;
import com.ganjur.poem.App;

import java.util.List;
import java.util.ArrayList;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.ComponentOrientation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class PoetPage extends JPanel{

	private static final String POETS_LIST_PATH="poets.json";
	
	private JScrollPane outP;
		private JTextArea outTA; // for output

	private JPanel inputP;
		private JLabel inputL;
		private JScrollPane inputListScroll;
			private JList inputList; // for input
	
	public PoetPage(){
		super();
		this.init();
	}

	private void init(){
		initInput();
		initOutput();

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(outP);
		this.add(inputP);
		this.setBorder(new EmptyBorder(10,10,10,10));
	}

	private void initOutput(){
		this.outTA=new JTextArea();
		this.outTA.setEditable(false);
		this.outTA.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.outTA.setFont(new Font("Serif",Font.BOLD,24));
		this.outTA.setMargin(new Insets(10, 10, 10, 10));
		this.outTA.setLineWrap(true);

		this.outP=new JScrollPane();
		this.outP.setViewportView(this.outTA);
		this.outP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.outP.setPreferredSize(new Dimension(MainPage.X/2,MainPage.Y-200));
		this.outP.setBorder(new EmptyBorder(10,10,10,10));
	}

	private void initInput(){
		
		this.inputL=new JLabel("نام شاعر");
		this.inputL.setFont(new Font("Serif",Font.BOLD,32));
		this.inputL.setAlignmentX(RIGHT_ALIGNMENT);
		this.inputL.setBorder(new EmptyBorder(10,10,10,10));

		initInputList();

		this.inputP=new JPanel(); 
		this.inputP.setLayout(new BoxLayout(inputP,BoxLayout.Y_AXIS));
		this.inputP.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		this.inputP.add(inputL);
		this.inputP.add(inputListScroll);
		this.inputP.setBorder(new EmptyBorder(10,10,10,10));
	}

	private void initInputList(){
		DefaultListModel<Poet> model=new DefaultListModel<Poet>();
		List<Poet> data=getAllPoets();
		model.addAll(data);
		this.inputList=new JList(model);
		
		initInputListBehavior();
		initInputListUI();
	}

	private void initInputListBehavior(){
		this.inputList.setSelectionMode(
			ListSelectionModel.SINGLE_SELECTION); // select one @ a time

		// init select listener
		this.inputList.addListSelectionListener(new ListSelectionListener() {
			@Override 
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					requestPoet();
				}
    	}
		});
	}

	private void initInputListUI(){

		// Poets List
		this.inputList.setComponentOrientation(
			ComponentOrientation.RIGHT_TO_LEFT);
		this.inputList.setFont(new Font("Serif",Font.BOLD,24));
		
		// input ScrollPane
		this.inputListScroll=new JScrollPane();
		this.inputListScroll.setViewportView(inputList);
		this.inputListScroll.setComponentOrientation(
			ComponentOrientation.RIGHT_TO_LEFT);
		this.inputListScroll.setHorizontalScrollBarPolicy(
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.inputListScroll.setPreferredSize(new Dimension(MainPage.X/4,MainPage.Y/2));
	}
	
	/** used to get all poets list only once the program is at poets page*/
	private List<Poet> getAllPoets(){
		List<Poet> ps=new ArrayList<Poet>(); // return empty list (placeholder)
		String res=null;
		if((res=readPoetsFile())==null)
			res=HttpReq.httpGet(App.API_ALL_POETS);
		else ps=Poet.listFromJson(res);
		return ps;
	}

	private String readPoetsFile(){
		
		File f=new File(POETS_LIST_PATH);
		if(!f.exists() || f.isDirectory()){  // check if the file is valid
			System.out.println(
				"POETS_LIST_PATH not found, fetching resource over the web");
			return null;
		}
		
		// read file's content
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(f));
			StringBuffer sb=new StringBuffer();
			String line;
			while((line=br.readLine())!=null)
				sb.append(line);
			return sb.toString();	
		}catch(IOException ioe){
			ioe.printStackTrace();
			return null;
		}
	}

	private void requestPoet(){
		Poet selected=(Poet) inputList.getSelectedValue();
		String url=App.API_SINGLE_POET+"?url="+selected.getFullUrl();
		System.out.println("fetching "+url);
		Poet result=Poet.fromJson(HttpReq.httpGet(url));
		this.outTA.setText(result.toFullString());
		this.outTA.setCaretPosition(0);
		/**/
	}

}
