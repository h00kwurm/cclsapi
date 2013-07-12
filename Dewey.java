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
	{}
	
	public String getResult()
	{
		return searchResult;
	}
	
	public ArrayList<Item> getItemResults()
	{
		if( itemResults == null || itemResults.size() == 0 )
		{
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
		
		temp.setTitle("A Title");
		temp.setAuthor("Adi Natraj");
		temp.setRating((float)4.2);
		temp.setType("EBOOK");
		
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
				if(classBearer.childNodeSize() > 0)
				{
					tempItem.setType(classBearer.getElementsByTag("a").get(0).attr("alt"));
				}
				else
				{
					tempItem.setType(classBearer.text());
				}		
			}
			else if(classLabel.equals("briefcitTitle"))
			{
				Element link = classBearer.getElementsByAttribute("a").get(0);
				tempItem.setItemURL(link.attr("href"));				
				String titleJumble = link.text();
				tempItem.setTitle(titleJumble.split("[ ]*\\[|[ ]*[:][ ]*|[ ]*[/][ ]*")[0]);
	
			}
			else if(classLabel.equals("briefcitRequest"))
			{
				Element link = classBearer.getElementsByAttribute("a").get(0);
				tempItem.setHoldURL(link.attr("href"));
			}
			if(classLabel.equals("bibItemsEntry"))
			{
				Availability tempAvail = new Availability();
				int counter = 1;
				for(Element line : classBearer.getElementsByAttribute("td"))
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
		tempItem.setAuthor("???");
		return tempItem;
	}
	
	public void traverseHTML()
	{
		Elements rows = webpage.getElementsByClass("briefCitRow");
		Log.d("DWY/tH", String.valueOf(rows.size()));
		
		for( Element row : rows)
		{
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
			searchResult = webpage.text();
			
		}catch (Exception e)
		{
			Log.d("Couldnt load data from page", e.toString());
		}
	
		return true;
	}
}
