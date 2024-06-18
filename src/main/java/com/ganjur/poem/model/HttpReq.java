package com.ganjur.poem.model;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;
import java.net.HttpURLConnection;

import com.ganjur.poem.App;

/**
	simplified Http Request Sender.
	if send() is not called, getX methods are returning null.
	only 'GET' http method supported as of current version.
*/
public class HttpReq{

	public static String httpGet(String url){
		HttpReq req;
		try{
			req=new HttpReq(url);
			req.send();
			if(req.hasError()) 
				throw new IOException(
					"[HttpReq Failure] "+req.getError()); // is caught by catch block
			return req.getReponse(); // everything is fine
		}catch(IOException ioe){
			ioe.printStackTrace();
			App.showErrMsg(
				"Fetching '"
				+url
				+"' failed (check out the console output for details)"
			);
		}
		return null; // in case of failure
	}

	//--------------------------------------NON-STATIC------------->

	private String url;
	private String response;
	private String error;

	public HttpReq(String url) throws IOException{
		this.url=url;
		this.response=null;
		this.error=null;
	}
	
	/** send an http request to the url, forward the response afterwards.  */
	public String send() throws IOException{
		URL u=new URL(this.url);
		HttpURLConnection conn=(HttpURLConnection) u.openConnection();
		
		// handle errors
		this.error=this.getError(conn); // if no errors, nothing bad happens
		
		// otherwise read the response and fwd it
		this.response=this.readResponse(conn);
		return this.response;
	}
	
	// WARNING : null-checking of all variables is the user code's job
	public String getUrl(){return this.url;}
	public String getReponse(){return this.response;} 
	public String getError(){return this.error;}
	public boolean hasError(){ return this.error!=null; }
	
	// -----------------------------------------PVT.METHODS---------------------->

	private String getError(HttpURLConnection c) throws IOException{
		int status=c.getResponseCode();
		// System.out.println("status:"+status);
		return (status >=400)?readError(c):null;
	}

	private String readError(HttpURLConnection c) throws IOException{
		return readInputStream(c.getErrorStream());
	}

	private String readResponse(HttpURLConnection c) throws IOException{
		return readInputStream(c.getInputStream());
	}

	private String readInputStream(InputStream is) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		StringBuffer sb=new StringBuffer(); // better to use StringBuffer for building a String (than string+="something" )
		String text; // entire text
		String line; // one line at a time
		while((line=br.readLine())!=null) sb.append(line+"\n");
		text=sb.toString();
		br.close(); // internally callse close of InputStreamReader as well
		return text;
	}
	
}
