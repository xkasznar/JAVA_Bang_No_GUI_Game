package sk.stuba.fei.uim.oop.deck;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.*;
import sk.stuba.fei.uim.oop.cards.brown.*;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> drawPile;
    private ArrayList<Card> discardPile;

    public Deck(){
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        for(int i = 0; i < 2; i++)
            drawPile.add(new Barrel(this));
        for(int i = 0; i < 1; i++)
            drawPile.add(new Dynamite(this));
        for(int i = 0; i < 3; i++)
            drawPile.add(new Prison(this));

        for(int i = 0; i < 30; i++)
            drawPile.add(new Bang(this));
        for(int i = 0; i < 15; i++)
            drawPile.add(new Missed(this));
        for(int i = 0; i < 8; i++)
            drawPile.add(new Beer(this));
        for(int i = 0; i < 6; i++)
            drawPile.add(new CatBalou(this));
        for(int i = 0; i < 4; i++)
            drawPile.add(new Stagecoach(this));
        for(int i = 0; i < 2; i++)
            drawPile.add(new Indians(this));
        Collections.shuffle(drawPile);
    }

    public void printPiles(int isDebugMode){
        System.out.print("Draw pile (" + drawPile.size() + "): ");
        for(Card card : drawPile){
            System.out.print(isDebugMode == 1 ? card.getAcronym() + " " : "** ");
        }
        System.out.println();
        System.out.print("Discard pile (" + discardPile.size() + "): ");
        for(Card card : discardPile){
            System.out.print(isDebugMode == 1 ? card.getAcronym() + " " : "** ");
        }
        System.out.println();
    }
    public boolean arePilesGeqTwo(){
        return drawPile.size() + discardPile.size() >= 2;
    }
    public String deal(Player currentPlayer) {
        if (drawPile.isEmpty()) {
            if (discardPile.isEmpty()) {
                return "-";
            }
            drawPile.addAll(discardPile);
            discardPile.clear();
            Collections.shuffle(drawPile);
        }
        Card cardToDeal = drawPile.remove(0);
        currentPlayer.addHandPersonal(cardToDeal);
        return cardToDeal.getName();
    }
    public void reuptake(Card card, ArrayList<Card> personals){
        discardPile.add(card);
        personals.remove(card);
    }
    public void reuptakeAll(ArrayList<Card> personals){
        discardPile.addAll(personals);
        personals.clear();
    }
}
