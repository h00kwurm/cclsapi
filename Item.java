package com.example.cclsapi;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Item {
	private String type;
	private String title;
	private String author;
	private String callNo;
	private float rating;
	private String coverURL;
	private String itemURL;
	private String holdURL;
	private ArrayList<Availability> availabilities;
	
	public Item()
	{
		availabilities = new ArrayList<Availability>();
		type = "?M";
		title = "?T";
		author = "?A";
		rating = (float)4.2;
	}
	
	public void setType(String t)
	{
		type = t;
	}
	public String getType()
	{
		return type;
	}
	
	public void setTitle(String t)
	{
		title = t;
	}
	public String getTitle()
	{
		return title;
	}
	
	public void setAuthor(String t)
	{
		author = t;
	}
	public String getAuthor()
	{
		return author;
	}
	
	public void setRating(float t)
	{
		rating = t;
	}
	public float getRating()
	{
		return rating;
	}
	
	public String toString()
	{
		return getTitle() + "\t --- \t" + getAuthor() + "\n" + getType() + "\t" + getRating() + "\n";
	}


	public String getCallNo() {
		return callNo;
	}


	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public String getItemURL() {
		return itemURL;
	}

	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}

	public String getHoldURL() {
		return holdURL;
	}

	public void setHoldURL(String holdURL) {
		this.holdURL = holdURL;
	}

	public void addAvailability(Availability tempAvail) {
		availabilities.add(tempAvail);
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

}
