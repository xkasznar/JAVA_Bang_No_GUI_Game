package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Barrel extends Card {
    private static final String CARD_NAME = "Barrel";
    
    public Barrel(Deck deck) {
        super(CARD_NAME, deck);
    }

    @Override
    public String check(Player victimPlayer, ArrayList<Player> players){
        int odds = 4;
        int roll = ThreadLocalRandom.current().nextInt(odds);
        String reason = " ("+roll+"/"+(odds-1)+")";

        if(roll == 0)
            return "Barrel protected id "+victimPlayer.getId()+reason+".\n";
        return "Barrel failed to protect id "+victimPlayer.getId()+reason+".\n";
    }
    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        for(Card card : currentPlayer.getTablePersonals())
            if (card instanceof Barrel)
                return "You already have barrel in front.\n";

        currentPlayer.removeHandPersonal(this);
        currentPlayer.addTablePersonal(this);
        return "Barrel was played.\n";
    }
}
