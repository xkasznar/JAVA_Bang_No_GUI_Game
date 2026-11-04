package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public abstract class Card {
    private String name;
    protected Deck deck;

    public Card(String name, Deck deck){
        this.name = name;
        this.deck = deck;
    }
    public String getName(){
        return name;
    }
    public String getAcronym(){
        if(name.length() <= 2)
            return name;
        return "" + name.charAt(0) + name.charAt(name.length()-1);
    }

    public String check(Player currentPlayer, ArrayList<Player> players){
        return "Card isn't odds checkable.\n";
    }
    public abstract String play(Player currentPlayer, ArrayList<Player> players);
}
