package networking;

import cardgame.Card;
import cardgame.Player;

import java.io.*;
import java.net.Socket;

/**
 * The Game Server thread. Handles the Card playing by the player and echos the score to the player.
 *
 * @author Nathan Atkins
 */
public class GameServerThread extends Thread {

    GameProtocol gameProtocol;
    Socket playerSocket;
    Player player;
    int round = 0;
    int maxRounds;

    /**
     * Sets a set of objects for each player.
     * @param gameProtocol game protocol
     * @param playerSocket player socket
     * @param player player
     */
    public GameServerThread(GameProtocol gameProtocol, Socket playerSocket, Player player) {
        super(player.getName() + " - game thread");
        this.gameProtocol = gameProtocol;
        this.playerSocket = playerSocket;
        this.player = player;
    }

    @Override
    public void run() {
        super.run();
        ObjectOutputStream toClient = null;
        ObjectInputStream fromClient = null;

        try {
            toClient = new ObjectOutputStream(this.playerSocket.getOutputStream());
            fromClient = new ObjectInputStream(this.playerSocket.getInputStream());
            toClient.writeObject(this.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxRounds = player.getHand().size();
        this.player.deleteHand();

        while (!gameProtocol.isGameOver()) {
            Card card = null;
            try {
                assert fromClient != null;
                card = (Card) fromClient.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            this.player.addCard(card);

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Gets the round winner and passes back a winner string.
            gameProtocol.setRound(++round);
            String winner = gameProtocol.roundWinner();

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Deletes the player's hand.
            player.deleteHand();

            //Sends winner string to client.
            try {
                toClient.writeObject(winner);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Ends the game
            if (round == maxRounds) {
                gameProtocol.setGameStatus(GameProtocol.Status.GAME_OVER);
            }
        }

        //Sends the overall winner to the clients.
        String overallWinner = gameProtocol.overallWinner();
        try {
            assert toClient != null;
            toClient.writeObject(overallWinner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
