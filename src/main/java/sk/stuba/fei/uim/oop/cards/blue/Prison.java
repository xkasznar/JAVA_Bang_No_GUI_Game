package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Prison extends Card {
    private static final String CARD_NAME = "Prison";
    
    public Prison(Deck deck){
        super(CARD_NAME, deck);
    }

    @Override
    public String check(Player currentPlayer, ArrayList<Player> players){
        deck.reuptake(this, currentPlayer.getTablePersonals());
        int odds = 4;
        int roll = ThreadLocalRandom.current().nextInt(odds);
        String reason = " ("+roll+"/"+(odds-1)+")";

        if(roll == 0)
            return "You broke out of prison"+reason+".\n";
        return "You failed to break out of prison"+reason+".\n";
    }
    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        int choiceId = ZKlavesnice.readInt("Imprison whom?");

        if(choiceId <= -1 || choiceId >= players.size())
            return "OOB choice.\n";
        Player victimPlayer = players.get(choiceId);
        if(!victimPlayer.isAlive())
            return "Corpse choice.\n";
        if(victimPlayer.equals(currentPlayer))
            return "Can't choose self.\n";
        for(Card card : victimPlayer.getTablePersonals()){
            if(card instanceof Prison){
                return "They already are imprisoned.\n";
            }
        }

        currentPlayer.removeHandPersonal(this);
        victimPlayer.addTablePersonal(this);
        return "Prison was played on id "+victimPlayer.getId()+".\n";
    }
}
