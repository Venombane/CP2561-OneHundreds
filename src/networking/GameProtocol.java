package networking;

import cardgame.Carddeck;
import cardgame.Player;

import java.util.LinkedList;

/**
 * This is the object that manages the game between all the threads.
 *
 * @author Nathan Atkins
 */
public class GameProtocol {

    private Status gameStatus;
    private Carddeck cardDeck;
    private LinkedList<Player> playerList;
    private int round = 0;

    public enum Status{
        NOT_STARTED, PLAY_IN_PROGRESS, GAME_OVER
    }

    /**
     * Sets the default
     * @param cardDeck The card deck.
     * @param playerList List of players.
     */
    public GameProtocol(Carddeck cardDeck, LinkedList<Player> playerList) {
        this.cardDeck = cardDeck;
        this.playerList = playerList;
        this.gameStatus = Status.NOT_STARTED;
        this.cardDeck.deal(this.playerList);
    }

    /**
     * Sets the round of the game.
     * @param newRound new round.
     */
    public void setRound(int newRound) {
        round = newRound;
    }

    /**
     * Get the Game Status
     * @return current game status
     */
    public Status getGameStatus() {
        return gameStatus;
    }

    /**
     * Sets the games status
     * @param gameStatus gameStatus
     */
    public void setGameStatus(Status gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Determines if the game is over
     * @return true or false
     */
    public boolean isGameOver(){
        return this.gameStatus == Status.GAME_OVER;
    }

    /**
     * Determine the winner of a round.
     * @return winnerString
     */
    public String roundWinner(){

        //TODO write into loop and wait for player cards
        Player winner = playerList.get(0);
        for (int i = 0; i < playerList.size() - 1; i++) {
            if(playerList.get(i).getHand().get(0).compareTo(playerList.get(i+1).getHand().get(0))>0)
            {
                winner = playerList.get(i+1);
                playerList.get(i+1).addRoundWon();
            }
        }
        String winnerString = "Round" + round + "\t";
        for (Player player : playerList) {
            winnerString += player.getName() + " plays " + player.getHand().get(0) + "\t";
        }
        winnerString = winnerString + "\t" + winner.getName() + " Wins!";
        return winnerString;
    }

    /**
     * Method to determine the overall winner of the game and return string with their name.
     * @return String with overall winner
     */
    public String overallWinner() {
        int scoreIndex = 0;
        for (int i = 1; i < playerList.size(); i++) {
            if(playerList.get(i).getRoundsWon() > playerList.get(scoreIndex).getRoundsWon()) {
                scoreIndex = i;
            }
        }
        return String.format("%s wins the game.", playerList.get(scoreIndex).getName());
    }
}
