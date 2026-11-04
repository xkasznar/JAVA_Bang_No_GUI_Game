package sk.stuba.fei.uim.oop.cards.brown;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CatBalou extends Card{
    private static final String CARD_NAME = "CatBalou";
    
    public CatBalou(Deck deck) {
        super(CARD_NAME, deck);
    }

    @Override
    public String play(Player currentPlayer, ArrayList<Player> players){
        int choiceId = ZKlavesnice.readInt("CatBalou whom?");

        if(choiceId <= -1 || choiceId >= players.size())
            return "OOB choice.\n";
        Player victimPlayer = players.get(choiceId);
        if(!victimPlayer.isAlive())
            return "Corpse choice.\n";
        if(victimPlayer.equals(currentPlayer))
            return "Can't choose self.\n";

        ArrayList<Card> tp = victimPlayer.getTablePersonals();
        ArrayList<Card> hp = victimPlayer.getHandPersonals();
        if(tp.isEmpty() && hp.isEmpty())
            return "Nothing to sabotage them.\n";

        deck.reuptake(this, currentPlayer.getHandPersonals());
        int sabotageChoice = -1;
        String chainMsg = "";
        if(!tp.isEmpty() && hp.isEmpty()) {
            sabotageChoice = 0;
            chainMsg += "Auto chose table personals, as sole sabotageable.\n";
        }
        if(tp.isEmpty() && !hp.isEmpty()) {
            sabotageChoice = 1;
            chainMsg += "Auto chose hand personals, as sole sabotageable.\n";
        }
        if(!tp.isEmpty() && !hp.isEmpty()) {
            while (true) {
                sabotageChoice = ZKlavesnice.readInt("0 sabotage table personal | 1 sabotage hand personal?");
                if(sabotageChoice == 0 || sabotageChoice == 1)
                    break;
                System.out.println("Bad subchoice.");
            }
        }

        if(sabotageChoice == 0) {
            int position = ThreadLocalRandom.current().nextInt(tp.size());
            Card card = tp.get(position);
            deck.reuptake(card, tp);
            return chainMsg+"CatBalou was played and discarded "+position+"th card ("+card.getName()+") of player "+victimPlayer.getId()+"'s table personals.\n";
        }
        int position = ThreadLocalRandom.current().nextInt(hp.size());
        Card card = hp.get(position);
        deck.reuptake(card, hp);
        return chainMsg+"CatBalou was played and discarded "+position+"th card ("+card.getName()+") of player "+victimPlayer.getId()+"'s hand personals.\n";
    }
}
