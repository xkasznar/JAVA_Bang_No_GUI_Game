package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Missed extends Card {
    private static final String CARD_NAME = "Missed";
    
    public Missed(Deck deck){
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        return "This card is unplayable manually, only automatically via other's Bang.\n";
    }
}
