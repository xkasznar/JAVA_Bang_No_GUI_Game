package sk.stuba.fei.uim.oop.cards.blue;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Dynamite extends Card {
    private static final String CARD_NAME = "Dynamite";
    
    public Dynamite(Deck deck){
        super(CARD_NAME, deck);
    }

    private Player getPrevAlivePlayer(Player currentPlayer, ArrayList<Player> players){
        ArrayList<Player> playersNavigator = new ArrayList<>(players);
        Collections.reverse(playersNavigator);
        while(playersNavigator.indexOf(currentPlayer) != 0)
            Collections.rotate(playersNavigator, 1);
        playersNavigator.remove(0);

        for(Player viewedPlayer : playersNavigator)
            if (viewedPlayer.isAlive()) {
                boolean hasDyn = false;
                for (Card card : viewedPlayer.getTablePersonals()) {
                    if (card instanceof Dynamite) {
                        hasDyn = true;
                        break;
                    }
                }
                if (!hasDyn)
                    return viewedPlayer;
            }
        return currentPlayer;
    }
    @Override
    public String check(Player currentPlayer, ArrayList<Player> players){
        int odds = 8;
        int roll = ThreadLocalRandom.current().nextInt(odds);
        String reason = " ("+roll+"/"+(odds-1)+")";

        if(roll == 0){
            deck.reuptake(this, currentPlayer.getTablePersonals());
            currentPlayer.takeDmg(3);
            return "Dynamite exploded on you "+reason+".\n";
        }
        Player prevAlivePlayer = getPrevAlivePlayer(currentPlayer, players);
        currentPlayer.removeTablePersonal(this);
        prevAlivePlayer.addTablePersonal(this);
        return "Dynamite passed over from you to id "+prevAlivePlayer.getId()+reason+".\n";
    }
    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        for(Card card : currentPlayer.getTablePersonals())
            if (card instanceof Dynamite)
                return "You already have dynamite in front.\n";

        currentPlayer.removeHandPersonal(this);
        currentPlayer.addTablePersonal(this);
        return "Dynamite was played.\n";
    }
}
