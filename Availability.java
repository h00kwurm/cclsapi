package com.example.cclsapi;

public class Availability {
	private String location;
	private String callNo;
	private String status;
	
	public Availability()
	{
		location = new String("XXX");
		callNo = new String("###");
		status = new String("SSS");
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCallNo() {
		return callNo;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
