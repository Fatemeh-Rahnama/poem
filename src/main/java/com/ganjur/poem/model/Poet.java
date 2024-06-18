package com.ganjur.poem.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

public class Poet{

	public static List<Poet> listFromJson(String jsonStr){
		JSONArray ja=new JSONArray(jsonStr);
		List<Poet> ps=new ArrayList<Poet>();
		JSONObject jo;
		for(int i=0;i<ja.length();i++){
			jo=ja.getJSONObject(i);
			ps.add(jsonObjectToPoet(jo));
		}
		return ps;
	}

	public static Poet fromJson(String jsonStr){
		JSONObject jo = new JSONObject(jsonStr).getJSONObject("poet");
		return jsonObjectToPoet(jo);
	}

	private static Poet jsonObjectToPoet(JSONObject jo){
		String desc="";
		String name="";
		String fullUrl="";

		try{ 
			desc=jo.getString("description");
		}catch(JSONException je){
			// je.printStackTrace(); 
			desc="404 Not Found"; // just in case...
		}

		return new Poet(
			jo.getString("name"),
			jo.getString("fullUrl"),
			desc
		);	
	}

	//--------------------------------------NON-STATIC------------->

	private String name;
	private String fullUrl;
	private String desc;
	
	public Poet(String name,String fullUrl,String desc){
		this.name=name;
		this.fullUrl=fullUrl;
		this.desc=desc;
	}
	
	/** shown only in selection list */
	@Override
	public String toString(){
		return this.name;
	}
	
	/** shown in textarea where a full description is needed */
	public String toFullString(){
		return "(("+this.name+"))\n\n"+this.desc+"\n----------\n";
	}

	/** used when trying to call one specific poet's details from server */
	public String getFullUrl(){return this.fullUrl;}
	public String getDesc(){return this.desc;}
	public String getName(){return this.name;}
}
