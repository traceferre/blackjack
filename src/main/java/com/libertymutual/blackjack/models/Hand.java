package com.libertymutual.blackjack.models;

import java.util.ArrayList;

public class Hand 
{
	Deck deck = new Deck();
	public ArrayList<Card> hand;
	
	public Hand(Deck deck)
	{
		this.deck = deck;
		hand = new ArrayList<Card>();
	}
	
	public void addCardToHand()
	{
		hand.add(deck.dealCard());
	}
	
	public boolean checkForBlackjack(Hand hand)
	{
		int cardTotal = 0;
		Computations comp = new Computations();
		cardTotal = comp.handTotal(hand);
		if (cardTotal == 21) 
		{
			return true;
		}
		else return false;
	}
	
	public boolean checkDealerNeedCard(Hand hand)
	{
		int cardTotal = 0;
		Computations comp = new Computations();
		cardTotal = comp.handTotal(hand);
		if (cardTotal <= 16) 
		{
			return true;
		}
		else return false;
	}
	
	public boolean checkForBust(Hand hand)
	{
		int cardTotal = 0;
		Computations comp = new Computations();
		cardTotal = comp.handTotal(hand);
		if (cardTotal > 21) 
		{
			return true;
		}
		else return false;
	}
	
	public ArrayList<String> getHandInWords()
	{
		ArrayList<String> handInWords = new ArrayList<String>();
		for (int i = 0; i < hand.size(); i++) 
		{
			handInWords.add(hand.get(i).getCardFaceValue() + " of " + hand.get(i).getCardSuit());
		}
		return handInWords;
	}

}
