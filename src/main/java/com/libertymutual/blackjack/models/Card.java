package com.libertymutual.blackjack.models;

public class Card 
{
	private String suit;
	private String faceValue;
	
	public Card(String suit, String faceValue)
	{
		this.suit = suit;
		this.faceValue = faceValue;
	}
	
	public String getCardSuit()
	{
		return suit;
	}
	
	public String getCardFaceValue()
	{
		return faceValue;
	}
}
