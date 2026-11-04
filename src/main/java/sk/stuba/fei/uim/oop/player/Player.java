package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.deck.Deck;

import java.util.ArrayList;

public class Player {
    private int id;
    private int lives;
    private ArrayList<Card> tablePersonals;
    private ArrayList<Card> handPersonals;
    private Deck deck;
    public Player(int id, Deck deck) {
        this.id = id;
        this.lives = 4;
        this.tablePersonals = new ArrayList<>();
        this.handPersonals = new ArrayList<>();
        this.deck = deck;
        for (int i = 0; i < 4; i++) {
            this.deck.deal(this);
        }
    }
    public int getId() {
        return id;
    }
    public int getLives() {
        return lives;
    }
    public ArrayList<Card> getTablePersonals() {
        return tablePersonals;
    }
    public ArrayList<Card> getHandPersonals() {
        return handPersonals;
    }

    public void printStatus(boolean viewedByFriendly, int isDebugMode) {
        String sep = viewedByFriendly ? "#".repeat(115) : "+".repeat(115);
        System.out.println(sep);
        String hpOrDeath = isAlive() ? (lives + " HP") : "DEAD";

        System.out.print("Player " + id + " (" + hpOrDeath + "), Table (");
        for (Card card : tablePersonals)
            System.out.print(card.getName() + " ");
        System.out.println(")");
        System.out.print("      ");
        for (int i = 0; i < handPersonals.size(); i++)
            System.out.printf("%10d ", i);
        System.out.println();
        System.out.print("Hand: ");
        for (Card card : handPersonals)
            if(!viewedByFriendly && isDebugMode==0)
                System.out.print("*".repeat(10) + " ");
            else
                System.out.printf("%10s ", card.getName());
        System.out.println();

        System.out.println(sep);
    }
    public boolean isAlive(){
        return lives > 0;
    }
    public void heal(){
        lives++;
    }
    public void takeDmg(int dmgAmount){
        lives -= dmgAmount;
        if(!this.isAlive()) {
            deck.reuptakeAll(tablePersonals);
            deck.reuptakeAll(handPersonals);
        }
    }
    public void addTablePersonal(Card card){
        tablePersonals.add(card);
    }
    public void removeTablePersonal(Card card){
        tablePersonals.remove(card);
    }
    public void addHandPersonal(Card card){
        handPersonals.add(card);
    }
    public void removeHandPersonal(Card card){
        handPersonals.remove(card);
    }
}
