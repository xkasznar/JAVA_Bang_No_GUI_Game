package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Barrel;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";
    
    public Bang(Deck deck){
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        int choiceId = ZKlavesnice.readInt("Bang whom?");

        if(choiceId <= -1 || choiceId >= players.size())
            return "OOB choice.\n";
        Player victimPlayer = players.get(choiceId);
        if(!victimPlayer.isAlive())
            return "Corpse choice.\n";
        if(victimPlayer.equals(currentPlayer))
            return "Can't choose self.\n";

        deck.reuptake(this, currentPlayer.getHandPersonals());
        String chainMsg = "";
        for(Card card : victimPlayer.getTablePersonals()){
            if(card instanceof Barrel) {
                chainMsg = card.check(victimPlayer, players);
                if(chainMsg.contains("Barrel protected"))
                    return chainMsg+"So bang was played, but dodged.\n";
                break;
            }
        }
        for(Card card : victimPlayer.getHandPersonals()){
            if(card instanceof Missed) {
                deck.reuptake(card, victimPlayer.getHandPersonals());
                chainMsg += "Missed protected id "+victimPlayer.getId()+" and got reuptaken.\n";
                return chainMsg+"So bang was played, but dodged.\n";
            }
        }
        victimPlayer.takeDmg(1);
        return chainMsg+"Bang was played on id "+victimPlayer.getId()+" and deducted one life.\n";
    }
}
