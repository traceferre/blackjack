package com.libertymutual.blackjack.models;

public class Wallet 
{
	int money = 100;
	int bet = 0;
	
	public Wallet(int bet)
	{
		this.bet = bet;
		if (!checkForSufficientFunds()) this.bet = money;
		money -= this.bet;
	}
	
	public boolean checkForSufficientFunds()
	{
		if (bet <= money) return true;
		else return false;
	}
	
	public int getBet()
	{
		return bet;
	}
	
	public void placeBet(int bet)
	{
		this.bet = bet;
		if (!checkForSufficientFunds()) this.bet = money;
		money -= this.bet;
	}
	
	public int getWalletTotal()
	{
		return money;
	}
	
	public void threeTotwo()
	{
		money += 1.5 * bet;
	}

	public void breakEven() 
	{
		money += bet;
	}

	public void twoToOne() 
	{
		money += 2 * bet;
	}

}
