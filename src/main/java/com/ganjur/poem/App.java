package com.ganjur.poem;

public class App {

	public static void main(String[] args){
		MainPage mp=new MainPage();
 	}

	public static void showErrMsg(String err){
		JOptionPane.showMessageDialog(
			null,"Error",err,JOptionPane.ERROR_MESSAGE
		);
	}
}
