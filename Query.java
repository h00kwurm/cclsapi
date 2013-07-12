package com.example.cclsapi;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Query {
	
	private String query;
	private String encodedQuery;
	private String location;
	private Map<String,String> locations;
	private Map<String,String> item_types;
	private Map<String,String> languages;
	private Map<String,String> sortStyles;
	private Map<String,String> searchTypes;
	private Map<String,String> arguments;
	
	public Query(String query)
	{
		this.query = query;
		initialize();
	}
	
	public String getQuery()
	{
		return query;
	}
	
	public String compute()
	{
		return query.replaceAll("[ ]", "+");
	}
	
	public String testCompute(String location, String material, String language, String style, String type)
	{
		return "&b=" + locations.get(location) + "&m=" + item_types.get(material) + "&l=" + languages.get(language);
	}
	
	public String toString()
	{
		Log.d("QRY/item:", query);
		return query;
	}
	
	public void initialize()
	{
		Log.d("QRY/INT:", "start initialize");
		// fill locations
		locations = new HashMap<String, String>();
		locations.put("*","");
		locations.put("Atglen","at");
		locations.put("Avon Grove","ag");
		locations.put("Bayard Taylor","bt");
		locations.put("Chester County","cc");
		locations.put("Chester Springs","cs");
		locations.put("Coatesville","co");
		locations.put("Downingtown","do");
		locations.put("Easttown","ea");
		locations.put("Health Department","hd");
		locations.put("Henrietta Hankin","hh");
		locations.put("Honey Brook","hb");
		locations.put("Malvern","ma");
		locations.put("Oxford","ox");
		locations.put("Paoli","pa");
		
		//fill item_types
		item_types = new HashMap<String, String>();
		item_types.put("*","");
		item_types.put("BOOK","a");
		item_types.put("LARGETYPE","b");
		item_types.put("AUDIO","h");
		item_types.put("E-AUDIOBOOK","k");
		item_types.put("VIDEO/DVD","g");
		item_types.put("MAGAZINE","y");
		item_types.put("SOFTWARE","m");
		item_types.put("VIDEOGAME","v");
		item_types.put("PAMPHLET","p");
		item_types.put("PRINTEDMUSIC","c");
		item_types.put("INTERNETSITE","z");
		item_types.put("STORYTIMEKIT","s");
		item_types.put("EQUIPMENT","q");
		item_types.put("E-BOOK","e");
		item_types.put("NEWSPAPER","n");
		item_types.put("MAP","j");
		item_types.put("AUDIOBK/PLAYER","w");
		item_types.put("VIDEO/PLAYER","x");
		item_types.put("E-READER","r");
		
		// fill languages
		languages = new HashMap<String, String>();
		languages.put("english","eng");
		languages.put("chinese","chi");
		languages.put("spanish","spa");
		languages.put("hindi","hin");
		languages.put("french","fre");
		languages.put("german","ger");
		languages.put("italian","ita");
		
		//fill searchTypes
		searchTypes = new HashMap<String,String>();
		searchTypes.put("author","a");
		searchTypes.put("title","t");
		searchTypes.put("subject","note");
		searchTypes.put("journal title","j");
		Log.d("QRY/INT:", "end filling");
	}
}
