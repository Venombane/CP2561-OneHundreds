package cardgame;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Simple player class. Stores name and hand.
 */
public class Player implements Serializable {

    private final String name;
    private LinkedList<Card> hand;
    private int roundsWon = 0;

    /**
     * Create the Player with a name
     * @param name
     */
    public Player(String name) {
        this.name = name;
        hand = new LinkedList<>();
    }

    /**
     * Get the player's name
     * @return players name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the player's rounds won
     * @return rounds won
     */
    public int getRoundsWon() { return roundsWon; }

    /**
     *  Adds 1 to rounds won
     */
    public void addRoundWon() {
        roundsWon += 1;
    }

    /**
     * Get the players hand
     * @return
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * Delete the hand
     */
    public void deleteHand(){
        this.hand = new LinkedList<>();
    }

    /**
     * Add a card to a hand
     * @param card
     */
    public void addCard(Card card){
        this.hand.add(card);
    }

    /**
     * Draw a card
     * @return Card
     */
    public Card drawCard(){
        return this.hand.removeFirst();
    }

    /**
     * Set the players hand
     * @param hand
     */
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Print the players hand to a printStream
     * @param printStream
     */
    public void printHand(PrintStream printStream){
        this.hand.forEach((c)->printStream.print(c.toString() + " "));
    }
}