package com.ganjur.poem;

public class App {

	public static final String API_RANDOM_POEM="https://api.ganjoor.net/api/ganjoor/poem/random";
	public static final String API_FAL_HAFEZ="https://api.ganjoor.net/api/ganjoor/hafez/faal";
	public static final String API_ALL_POETS="https://api.ganjoor.net/api/ganjoor/poets";
	public static final String API_SINGLE_POET="https://api.ganjoor.net/api/ganjoor/poet";
	public static final String API_SEARCH_POEMS="https://api.ganjoor.net/api/ganjoor/poems/search";

	public static void main(String[] args){
		MainPage mp=new MainPage();
 	}

	public static void showErrMsg(String err){
		JOptionPane.showMessageDialog(
			null,"Error",err,JOptionPane.ERROR_MESSAGE
		);
	}
}
