package networking;

import cardgame.Carddeck;
import cardgame.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Game server which will handle the client connections and create threads to handle the game
 *
 * Built to handle a dynamic number of players before starting the game. ( The number of players will be dynamic
 * but we will make it static for simplicity - i.e. we will force 3 players for example.)
 */
public class GameServer {

    public static final int NUMBER_PLAYERS = 3;

    /**
     * Main server loop
     * @param args args
     */
    public static void main(String[] args) {

        int portNumber = 4401; //Integer.parseInt(args[0]);
        boolean listening = true;

        //Track the connections
        LinkedList<Socket> clientSocketList = new LinkedList<>();

        //Wait until we have all the connections we need
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening & clientSocketList.size() < NUMBER_PLAYERS) {
                clientSocketList.add(serverSocket.accept());
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }

        //Debug - be sure you got the connections you wanted
        System.out.printf("Connections: %d\n", clientSocketList.size());

        //Start the game

        LinkedList<Player> playerList = new LinkedList<Player>();
        for (int i = 1; i <= NUMBER_PLAYERS; i++) {
            playerList.add(new Player("Player " + i));
        }

        Carddeck cardDeck = new Carddeck();
        cardDeck.shuffle();
        GameProtocol gameProtocol = new GameProtocol(cardDeck, playerList);

        //Create the threads
        LinkedList<GameServerThread> threadList = new LinkedList<>();
        for (int i = 0; i < clientSocketList.size(); i++) {
            threadList.add(new GameServerThread(gameProtocol, clientSocketList.get(i), playerList.get(i)));
        }
        threadList.forEach(thread ->  thread.start());
    }
}