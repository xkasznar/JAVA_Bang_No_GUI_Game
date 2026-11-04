package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Indians extends Card {
    private static final String CARD_NAME = "Indians";
    
    public Indians(Deck deck){
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        deck.reuptake(this, currentPlayer.getHandPersonals());

        String chainMsg = "";
        for(Player potentialVictimPlayer : players){
            if(!potentialVictimPlayer.isAlive() || potentialVictimPlayer.equals(currentPlayer))
                continue;

            boolean hasBang = false;
            for(Card card : potentialVictimPlayer.getHandPersonals()){
                if(card instanceof Bang){
                    deck.reuptake(card, potentialVictimPlayer.getHandPersonals());
                    hasBang = true;
                    chainMsg += "Player " + potentialVictimPlayer.getId() + " dodged Indians with Bang, this was reuptaken.\n";
                    break;
                }
            }
            if(!hasBang){
                potentialVictimPlayer.takeDmg(1);
                chainMsg += "Player " + potentialVictimPlayer.getId() + " failed to dodge Indians, had no bang.\n";
            }
        }
        return chainMsg + "Indians was played and did as noted above.\n";
    }
}
