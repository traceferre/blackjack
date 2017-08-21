package com.libertymutual.blackjack.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.blackjack.models.Computations;
import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Round;
import com.libertymutual.blackjack.models.Wallet;

@Controller
@RequestMapping("/game")
public class GameController 
{
	private Deck runningDeck;
	private Round currentRound;	
	private Wallet wallet;
	private Computations comp;
	
	//handles start-game get sent by new game button
	@GetMapping("/start-game")
	public String sendToGame()
	{
		return "/game/start-game";
	}
	
	@PostMapping("/place-bet")
	public String sendToBet(Model model)
	{
		model.addAttribute("wallet", wallet.getWalletTotal());
		if (wallet.getWalletTotal() < 5) return "/game/game-over";
		else return "/game/place-bet";
	}
	
	//handles clicks of the Double Down button, doubling down
	//doubles player's bet and deals one more card, button only visible
	//when player has adequate funds in wallet
	@GetMapping("/game-in-progress/double")
	public String doubleDown(Model model)
	{
		int bet = wallet.getBet();
		wallet.placeBet(-bet);
		wallet.placeBet(2 * bet);
		try {
			currentRound.dealCardToPlayer();
		} catch (Exception e) {
			return "game/game-over";
		}
		
		return "redirect:/game/game-in-progress/stay";
	}
	
	//handles clicks of the Stay button during current round
	//player takes no additions cards and dealer's page is loaded
	@GetMapping("/game-in-progress/stay")
	public String stay(Model model)
	{	
		try {
			currentRound.dealerAutoHit();
		} catch (Exception e) {
			model.addAttribute("wallet", wallet.getWalletTotal());
			return "game/game-over";
		}		
		
		model.addAttribute("bet", wallet.getBet());
		model.addAttribute("wallet", wallet.getWalletTotal());
		model.addAttribute("dealerHand", currentRound.getDealersHandInWords());
		model.addAttribute("playerHand", currentRound.getPlayersHandInWords());
		
		comp = new Computations();
		model.addAttribute("dealerTotal", comp.handTotal(currentRound.dealerHand));
		model.addAttribute("playerTotal", comp.handTotal(currentRound.playerHand));
		
		if (currentRound.didDealerBust()) return "/game/dealer-bust";
		else if (currentRound.didDrawOccur()) return "/game/draw";
		else if (currentRound.didPlayerWin() 
				&& !currentRound.didDealerBust()) return "/game/player-wins";
		else return "/game/dealer-wins";
	}
	
	//handles clicks of the hit button during current round
	//hit pops card from the deck, adds to player's hand
	@PostMapping("/game-in-progress/hit")
	public String hit(Model model)
	{
		ArrayList<String> dealersHand = new ArrayList<String>();
		dealersHand = currentRound.getDealersHandInWords();
		
		model.addAttribute("bet", wallet.getBet());
		model.addAttribute("wallet", wallet.getWalletTotal());
		model.addAttribute("dealerHidden", dealersHand.get(0));
		model.addAttribute("dealerDisplay", dealersHand.get(1));
				
		try {
			currentRound.dealCardToPlayer();
		} catch (Exception e) {
			return "game/game-over";
		}	

		comp = new Computations();
		model.addAttribute("dealerTotal", comp.handTotal(currentRound.dealerHand));
		model.addAttribute("playerTotal", comp.handTotal(currentRound.playerHand));
		
		if (currentRound.didPlayerBust())
		{
			model.addAttribute("dealerHand", currentRound.getDealersHandInWords());
			model.addAttribute("playerHand", currentRound.getPlayersHandInWords());
			return "/game/player-bust";
		}
		model.addAttribute("playerHand", currentRound.getPlayersHandInWords());
		return "/game/game-in-progress";
	}
	
	@PostMapping("/game-in-progress/deal-new-hand")
	public String dealNewHand(int bet, Model model)
	{
		wallet.placeBet(bet);
		Round round = new Round(runningDeck, wallet);
		
		try {
			round.dealHand();
		} catch (Exception e) {
			model.addAttribute("wallet", wallet.getWalletTotal());
			return "game/game-over";
		}	
		currentRound = round;		

		ArrayList<String> dealersHand = new ArrayList<String>();
		dealersHand = round.getDealersHandInWords();
		
		model.addAttribute("bet", wallet.getBet());
		model.addAttribute("wallet", wallet.getWalletTotal());
		model.addAttribute("dealerHidden", dealersHand.get(0));
		model.addAttribute("dealerDisplay", dealersHand.get(1));
		model.addAttribute("playerHand", round.getPlayersHandInWords());
		
		comp = new Computations();
		model.addAttribute("dealerTotal", comp.handTotal(currentRound.dealerHand));
		model.addAttribute("playerTotal", comp.handTotal(currentRound.playerHand));
		
		if (round.didPlayerBlackjack())
		{
			model.addAttribute("dealerHand", currentRound.getDealersHandInWords());
			model.addAttribute("playerHand", currentRound.getPlayersHandInWords());
			return "/game/player-blackjack";
		}
		if (wallet.getWalletTotal() < wallet.getBet()) return "/game/game-in-progress";
		else return "/game/game-in-progress-double";		
	}
	
	//handles click of the deal button, kicks off a new blackjack game,
	//creates new deck, shuffles deck, deals player and dealer two cards
	@PostMapping("/start-game/deal")
	public String kickOffNewGame(int bet, Model model)
	{
		Deck deck = new Deck();
		deck.createMyDeck();
		deck.shuffleMyDeck();
		
		wallet = new Wallet(bet);
		Round round = new Round(deck, wallet);
		round.dealHand();
		
		currentRound = round;
		runningDeck = deck;	
		
		ArrayList<String> dealersHand = new ArrayList<String>();
		dealersHand = round.getDealersHandInWords();
		
		model.addAttribute("bet", wallet.getBet());
		model.addAttribute("wallet", wallet.getWalletTotal());
		model.addAttribute("dealerHidden", "Do not show..");
		model.addAttribute("dealerDisplay", dealersHand.get(1));
		model.addAttribute("playerHand", round.getPlayersHandInWords());
		
		comp = new Computations();
		model.addAttribute("dealerTotal", comp.handTotal(round.dealerHand));
		model.addAttribute("playerTotal", comp.handTotal(round.playerHand));
		
		if (round.didPlayerBlackjack())
		{
			model.addAttribute("dealerHand", currentRound.getDealersHandInWords());
			model.addAttribute("playerHand", currentRound.getPlayersHandInWords());
			return "/game/player-blackjack";
		}
		if (wallet.getWalletTotal() < wallet.getBet()) return "/game/game-in-progress";
		else return "/game/game-in-progress-double";
	}
}

