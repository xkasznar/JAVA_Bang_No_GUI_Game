package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blue.Dynamite;
import sk.stuba.fei.uim.oop.cards.blue.Prison;
import sk.stuba.fei.uim.oop.deck.Deck;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private Deck deck;
    private ArrayList<Player> players;
    private Scanner scanner;
    private int isDebugMode;
    public Game(){
        int playerCount;
        while(true) {
            playerCount = ZKlavesnice.readInt("Enter valid player count (2-4)?");
            if(playerCount >= 2 && playerCount <= 4)
                break;
            System.out.println("Bad player count.");
        }
        while(true) {
            this.isDebugMode = ZKlavesnice.readInt("0 player mode | 1 debug mode");
            if(isDebugMode == 0 || isDebugMode == 1)
                break;
            System.out.println("Choose mode.");
        }
        this.deck = new Deck();
        this.players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++)
            players.add(new Player(i, deck));
        this.scanner = new Scanner(System.in);

        gameLoop();
        scanner.close();
    }
    private void gameLoop(){
        while (!isExactlyOneAlive()){
            for (Player currentPlayer : players) {
                if (currentPlayer.isAlive()) {
                    if(!mayMove(currentPlayer)) {
                        if(isExactlyOneAlive())
                            break;
                        askToContinue("Forced move skip [ enter ]");
                        continue;
                    }
                    firstStage(currentPlayer);
                    if(!secondStage(currentPlayer))
                        break;
                    thirdStage(currentPlayer);
                    askToContinue("You passed three stages [ enter ]");
                }
            }
        }
        declareAliveWinner();
    }
    private boolean mayMove(Player currentPlayer){
        ArrayList<Card> tableNavigator = new ArrayList<>(currentPlayer.getTablePersonals());
        String chainMsg = "";
        for (Card card : tableNavigator)
            if (card instanceof Dynamite) {
                chainMsg += card.check(currentPlayer, players);
                if (!currentPlayer.isAlive()) {
                    printFullStatus(currentPlayer, chainMsg, isDebugMode);
                    return false;
                }
            }
        tableNavigator = new ArrayList<>(currentPlayer.getTablePersonals());
        for (Card card : tableNavigator)
            if (card instanceof Prison) {
                chainMsg += card.check(currentPlayer, players);
                if (chainMsg.contains("failed to break")) {
                    printFullStatus(currentPlayer, chainMsg, isDebugMode);
                    return false;
                }
            }
        if(!chainMsg.isEmpty()) {
            printFullStatus(currentPlayer, chainMsg, isDebugMode);
            askToContinue("You evaded move skip condition(s) [ enter ]");
        }
        return true;
    }
    private void firstStage(Player currentPlayer){
        String chainMsg = "1st stage dealt you: ";
        for (int i = 0; i < 2; i++)
            chainMsg += deck.deal(currentPlayer) + ", ";
        chainMsg += "\n";
        printFullStatus(currentPlayer, chainMsg, isDebugMode);
    }
    private boolean secondStage(Player currentPlayer) {
        System.out.println("2nd stage was entered.");
        while (true) {
            int cardChoiceId = ZKlavesnice.readInt("Play valid index (-1 for forfeit)?");
            if(cardChoiceId >= 0 && cardChoiceId <= currentPlayer.getHandPersonals().size()-1) {
                Card card = currentPlayer.getHandPersonals().get(cardChoiceId);
                String msg = card.play(currentPlayer, players);
                if (msg.contains("was played"))
                    printFullStatus(currentPlayer, msg, isDebugMode);
                else
                    System.out.print(msg);
                if(isExactlyOneAlive())
                    return false;
            }
            else if(cardChoiceId == -1)
                return true;
            else
                System.out.println("Bad card choice id.");
        }
    }
    private void thirdStage(Player currentPlayer){
        int toTruncateCount = currentPlayer.getHandPersonals().size() - currentPlayer.getLives();
        String chainMsg = "3rd stage truncations (if hand exceeded lives): ";
        for (int truncatedCount = 0; truncatedCount < toTruncateCount; truncatedCount++) {
            int position = ThreadLocalRandom.current().nextInt( currentPlayer.getHandPersonals().size() ) ;
            Card card = currentPlayer.getHandPersonals().get(position);
            deck.reuptake(card, currentPlayer.getHandPersonals());
            chainMsg += card.getName() + "(@"+position+"), ";
        }
        chainMsg += "\n";
        printFullStatus(currentPlayer, chainMsg, isDebugMode);
    }
    private boolean isExactlyOneAlive(){
        int aliveCount = 0;
        for(Player player : players) {
            if(player.isAlive())
                aliveCount++;
        }
        return aliveCount == 1;
    }
    private void declareAliveWinner() {
        for (Player player : players) {
            if (player.isAlive())
                System.out.println("Winner is player " + player.getId() + ".");
        }
    }
    private void printFullStatus(Player currentPlayer, String msg, int isDebugMode){
        System.out.print("\n".repeat(7));
        deck.printPiles(isDebugMode);
        for(Player viewedPlayer : players) {
            viewedPlayer.printStatus(viewedPlayer.equals(currentPlayer), isDebugMode);
        }
        System.out.print(msg);
    }
    private void askToContinue(String msg){
        System.out.print(msg);
        scanner.nextLine();
    }
}
