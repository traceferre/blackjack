package com.libertymutual.blackjack.models;

import java.util.ArrayList;

public class Round 
{
	public Hand playerHand;
	public Hand dealerHand;
	public Wallet wallet;
	
	public Round(Deck deck, Wallet wallet)
	{
		playerHand = new Hand(deck);
		dealerHand = new Hand(deck);
		this.wallet = wallet;
	}
	
	public void dealHand()
	{
		playerHand.addCardToHand();
		dealerHand.addCardToHand();
		playerHand.addCardToHand();
		dealerHand.addCardToHand();
	}
	
	public void dealCardToPlayer()
	{
		playerHand.addCardToHand();
	}
	
	public void dealerAutoHit()
	{
		while (dealerHand.checkDealerNeedCard(dealerHand) && !playerHand.checkForBust(playerHand))
		{
			dealerHand.addCardToHand();
		}
	}
	
	public boolean didPlayerWin()
	{
		Computations comp = new Computations();
		if (comp.checkForPlayerWin(playerHand, dealerHand))
		{
			wallet.twoToOne();
		}
		return comp.checkForPlayerWin(playerHand, dealerHand);
	}
	
	public boolean didDrawOccur()
	{
		Computations comp = new Computations();
		if (comp.checkForDraw(playerHand, dealerHand))
		{
			wallet.breakEven();
		}
		return comp.checkForDraw(playerHand, dealerHand);
	}
	
	public boolean didPlayerBust()
	{
		return playerHand.checkForBust(playerHand);
	}
	
	public boolean didPlayerBlackjack()
	{
		if (playerHand.checkForBlackjack(playerHand) && !dealerHand.checkForBlackjack(dealerHand))
		{
			wallet.threeTotwo();
		}
		else if (playerHand.checkForBlackjack(playerHand) && dealerHand.checkForBlackjack(dealerHand))
		{
			wallet.breakEven();
		}
		return playerHand.checkForBlackjack(playerHand);
	}
	
	public boolean didDealerBust()
	{
		if (dealerHand.checkForBust(dealerHand))
		{
			wallet.twoToOne();
		}
		return dealerHand.checkForBust(dealerHand);
	}
	
	public boolean didDealerBlackjack()
	{
		return dealerHand.checkForBlackjack(dealerHand);
	}
	
	public ArrayList<String> getPlayersHandInWords()
	{
		return playerHand.getHandInWords();
	}
	
	public ArrayList<String> getDealersHandInWords()
	{
		return dealerHand.getHandInWords();
	}

}
