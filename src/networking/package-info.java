package networking;

/**
 * The networking component of the project. This package will provide a game server that enables multiple clients
 * to connect to play the card game noted in the card game package (100s)
 *
 * Proposed classes:
 *      GameServer - Handles the all the client connections (similar to KKMultiServer from class notes)
*           This server will spin off the multi threads
 *      GameClient - Handles the client connection to the server
 *      GameServerThread - Handles each client connection
 *      GameProtocol - synchronizes the game between all the clients
 *
 * //TODO Read these!
 * IDEAS for Progress
 *  - Create players, add them to the Game Protocol and set up their hands.
 *  - Debug this by printing it all out to the screen. This will make sure that all the hands are set up correctly
 *  - Pass the hands to the client and have the client dump the hands out. If they match then you are almost there
 *  - Then build a game loop. Keep it simple. You can make it wait until there is a reply from the server like in the KK Game
 *  - So inside the game you are waiting on all the cards. The client is waiting on a reply. Send the reply which is the
 *      result when you have all the cards
 *  - Once you have that working it's just polish and score
 *
 * @author Josh
 */