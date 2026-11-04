package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Beer extends Card {
    private static final String CARD_NAME = "Beer";
    
    public Beer(Deck deck) {
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        deck.reuptake(this, currentPlayer.getHandPersonals());
        currentPlayer.heal();
        return "Beer was played and gave you one life.\n";
    }
}
