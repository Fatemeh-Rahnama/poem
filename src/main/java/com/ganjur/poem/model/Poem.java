package com.ganjur.poem.model;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

public class Poem{

	public static List<Poem> listFromJson(String jsonStr){
		JSONArray ja=new JSONArray(jsonStr);
		List<Poem> ps=new ArrayList<Poem>();
		JSONObject jo;
		for(int i=0;i<ja.length();i++){
			jo=ja.getJSONObject(i);
			ps.add(jsonObjectToPoem(jo));
		}
		return ps;
	}
	public static Poem fromJson(String jsonString){
		return jsonObjectToPoem(new JSONObject(jsonString));
	}

	private static Poem jsonObjectToPoem(JSONObject jo){
		return new Poem(
			jo.getString("fullTitle"),
			jo.getString("plainText")
		);
	
	}

	//--------------------------------------NON-STATIC------------->

	private String fullTitle;
	private String plainText;

	public Poem(){}

	public Poem(String fullTitle,String plainText){
		this.fullTitle=fullTitle;
		this.plainText=plainText;
	}
	
	@Override
	public String toString(){
		return "(("+this.fullTitle+"))\n\n"+this.plainText+"\n\n------\n\n";
	}

	public String getFullTitle(){return this.fullTitle;}
	public String getPlainText(){return this.plainText;}

}
