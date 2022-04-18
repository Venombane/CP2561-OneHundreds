package networking;

import cardgame.Card;
import cardgame.Player;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Client which will connect to the server. After the connection is made, the client will receive the hand of cards
 * and then proceed to play the cards as the game goes on until finished.
 *
 * @author Nathan Atkins
 */
public class GameClient {

    /**
     * Main client method
     * @param args args
     */
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        LinkedList<Card> playerHand = null;

        //Game Server location
        String hostName = "localhost";
        int portNumber = 4401;

        //Connect to the game server
        try (
                Socket gameSocket = new Socket(hostName, portNumber);

                ObjectOutputStream out = new ObjectOutputStream(gameSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(gameSocket.getInputStream());
        ) {
            Player player = null;

            try {
                player = (Player) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            assert player != null;
            System.out.println("Welcome to OneHundreds " + player.getName());

            while (player.getHand().size() != 0) {
                assert player != null;
                if(player.getHand().size() > 0) {
                    Card card = player.drawCard();
                    System.out.println("You draw " + card.getValue());
                    out.writeObject(card);

                } else {
                    System.out.println("No cards left!");
                }
                String winner = (String) in.readObject();
                System.out.println(winner + "\n");
            }
            System.out.println(in.readObject());


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}