package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Stagecoach extends Card {
    private static final String CARD_NAME = "Stagecoach";
    
    public Stagecoach(Deck deck){
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        if(!deck.arePilesGeqTwo())
            return "Piles can't deal two cards now.\n";

        deck.reuptake(this, currentPlayer.getHandPersonals());
        String chainMsg = "";
        for (int i = 0; i < 2; i++) {
            chainMsg += deck.deal(currentPlayer) + ", ";
        }
        return "Stagecoach was played and dealt you: "+chainMsg+"\n";
    }
}
