package com.example.cclsapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Dewey {
	
	final String baseURL = "http://catalog.ccls.org/search/X?search=";
	final String tailURL = "&searchscope=1&SORT=D&extended=0";
	private String searchResult;
	private Pattern patternForResult = Pattern.compile(".*Rating");
	private ArrayList<String> processedResult;
	private ArrayList<Item> itemResults;
	private Document webpage;
	
	public Dewey()
	{
		itemResults = new ArrayList<Item>();
	}
	
	public String getResult()
	{
		return searchResult;
	}
	
	public ArrayList<Item> getItemResults()
	{
		if( itemResults == null || itemResults.size() == 0 )
		{
			Log.d("DWY/GIR", "itemResults is null or has none");
			return new ArrayList<Item>();
		}
		return itemResults;
	}
	
	public boolean parseAllResults()
	{
		itemResults = new ArrayList<Item>();
		for( String result : processedResult )
		{
			Item temp = parseResultIntoItem(result);
			if( temp == null )
				return false;
			itemResults.add(temp);
		}
		return true;
	}
	
	public Item parseResultIntoItem(String textResult)
	{
		Item temp = new Item();
		
		temp.setTitle(new String("A Title"));
		temp.setAuthor(new String("Adi Natraj"));
		temp.setRating((float)4.2);
		temp.setType(new String("EBOOK"));
		
		return temp;
	}
	
	
	public Item parseRow(Element row)
	{
		Item tempItem = new Item();
		for( Element classBearer : row.getElementsByAttribute("class"))
		{
			String classLabel = classBearer.attr("class");
			if(classLabel.equals("briefcitMedia"))
			{
				Elements kids = classBearer.children();
				if(kids.isEmpty())
				{
//					Log.d("DWY/PR:MT", classBearer.text());
					tempItem.setType(classBearer.text().toLowerCase());
				}
				else
				{
//					Log.d("DWY/PR:MT", kids.get(0).attr("alt"));
					tempItem.setType(kids.get(0).attr("alt").toLowerCase());
				}		
			}
			else if(classLabel.equals("briefcitTitle"))
			{
				Elements link = classBearer.getElementsByTag("a");
				if( link.size() == 0 )
					Log.d("DWY/PR", "no link for title");
				else{
					String[] titSections;
					tempItem.setItemURL(link.get(0).attr("href"));				
					String titleJumble = classBearer.text();
					titSections = titleJumble.split("[ ]*\\[|[ ]*[:][ ]*|[ ]*[/][ ]*");
					tempItem.setTitle(titSections[0]);
//					Log.d("DWY/PR", link.get(0).attr("href"));
//					Log.d("DWY/PR", tempItem.getTitle());
//					Log.d("DWY/PR*:",titSections[titSections.length-1]);
				}
			}
			else if(classLabel.equals("briefcitRequest"))
			{
				Element link = classBearer.getElementsByTag("a").get(0);
				tempItem.setHoldURL(link.attr("href"));
				//Log.d("DWY/PR:RQ", link.attr("href"));
			}
			if(classLabel.equals("bibItemsEntry"))
			{
				Availability tempAvail = new Availability();
				int counter = 1;
				for(Element line : classBearer.getElementsByTag("td"))
				{
					switch(counter){
					case 1:
						tempAvail.setLocation(line.text());
						break;
					case 2:
						tempAvail.setCallNo(line.text());
						break;
					case 3:
						tempAvail.setStatus(line.text());
						counter = 0;
						break;
					default:
						break;
					}
					++counter;
				}
				tempItem.addAvailability(tempAvail);
			}
		}
		Elements tBookCover = row.getElementsByAttributeValue("alt", "Book Cover");
		if( tBookCover.isEmpty() )
		{
			Log.d("DWY/PR:S", "no cover?");
			tempItem.setCoverURL("???");
		}
		else
		{
			//Log.d("DWY/PR:S", tBookCover.get(0).attr("src"));
			tempItem.setCoverURL(tBookCover.get(0).attr("src"));
		}
		tempItem.setAuthor("???");
		return tempItem;
	}
	
	public void traverseHTML()
	{
		Elements rows = webpage.getElementsByClass("briefCitRow");
		Log.d("DWY/tH", String.valueOf(rows.size()));
		Item tItem;
		for( Element row : rows)
		{
			tItem = parseRow(row);
			if( tItem == null )
				Log.d("DWY/TH:", "item is null");
			else
				itemResults.add(parseRow(row));
		}
	}
	
	
	public boolean splitResults()
	{
		//processedResult = new ArrayList<String>(Arrays.asList(searchResult.split("[ ][1234567890]+[ ]")));
		//searchResult = processedResult.get(2) + "\n\n" + processedResult.get(3) + "\n\n" + processedResult.get(4);
		searchResult = "something\n";
		if( processedResult == null )
			Log.d("DWY", "null pResult");
		else
			Log.d("DWY", String.valueOf(processedResult.size()));
		return true;
	}
	
	public boolean retrieve(Query q)
	{
		String urlExtension = q.compute();
		if( urlExtension == null )
			return false;
		try
		{
			Log.d("DWY/URL", baseURL + q.compute() + tailURL);
			webpage = Jsoup.connect(baseURL + urlExtension).get();
			Log.d("DWY/URL", "fetch complete");
			
		}catch (Exception e)
		{
			Log.d("Couldnt load data from page", e.toString());
		}
		return true;
	}
}
