package com.libertymutual.blackjack.models;

import java.util.ArrayList;

public class Computations 
{	
	public int handTotal(Hand hand)
	{
		int cardTotal = 0;
		ArrayList<String> handInWords = hand.getHandInWords();
		for (String card : handInWords)
		{
			for (Integer i = 2; i < 11; i++)
			{
				if (card.contains(i.toString())) cardTotal += i;
			}
			if (card.contains("Jack")) cardTotal += 10;
			else if (card.contains("Queen")) cardTotal += 10;
			else if (card.contains("King")) cardTotal += 10;
			else if (card.contains("Ace")) cardTotal += 11;
		}
		for (String card : handInWords)
		{
			if (cardTotal > 21 && card.contains("Ace"))
			{
					cardTotal -= 10;
			}				
		}		
		return cardTotal;
	}
	
	public boolean checkForPlayerWin(Hand pHand, Hand dHand)
	{
		int pTotal = 0;
		int dTotal = 0;
		Computations comp = new Computations();
		pTotal = comp.handTotal(pHand);
		dTotal = comp.handTotal(dHand);
		if (pTotal > dTotal) return true;
		else return false;		
	}
	
	public boolean checkForDraw(Hand pHand, Hand dHand)
	{
		int pTotal = 0;
		int dTotal = 0;
		Computations comp = new Computations();
		pTotal = comp.handTotal(pHand);
		dTotal = comp.handTotal(dHand);
		if (pTotal == dTotal) return true;
		else return false;		
	}
	
	

}
