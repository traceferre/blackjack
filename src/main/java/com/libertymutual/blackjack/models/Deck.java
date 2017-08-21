package com.libertymutual.blackjack.models;

import java.util.Stack;
import java.util.Collections;

public class Deck 
{
	Stack<Card> deckOfCards;
	String[] faceCards;
	
	public Deck()
	{
		deckOfCards = new Stack<Card>();
		faceCards = new String[] {"Hearts", "Clubs", "Spades", "Diamonds"};
	}
	
	public Card dealCard()
	{
		return deckOfCards.pop();
	}
	
	public void createMyDeck()
	{
		for (String faceCard : this.faceCards)
		{
			for (Integer i = 2; i < 11; i++)
			{
				deckOfCards.push(new Card(faceCard, i.toString()));
			}
			deckOfCards.push(new Card(faceCard, "Ace"));
			deckOfCards.push(new Card(faceCard, "Jack"));
			deckOfCards.push(new Card(faceCard, "Queen"));
			deckOfCards.push(new Card(faceCard, "King"));
		}
	}

	public void shuffleMyDeck() 
	{
		Collections.shuffle(deckOfCards);
	}
}
