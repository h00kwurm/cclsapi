package com.example.cclsapi;

public class LibraryCard 
{
	private String name;
	private char[] cardNumber;
	private char[] pin;
	
	public LibraryCard()
	{
		name = "";
		cardNumber = new char[16];
		pin = new char[4];
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char[] getPin() {
		return pin;
	}
	public void setPin(char[] pin) {
		this.pin = pin;
	}
	public char[] getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(char[] cardNumber) {
		this.cardNumber = cardNumber;
	}

}
