package cardgame;

import java.util.LinkedList;

/**
 * Automated Game of 100s for demonstration
 *
 */
public class RobotOneHundreds {

    public static void main(String[] args) {
        System.out.println("Beginning the Robot game \n Shuffled deck:");

        Carddeck cardDeck = new Carddeck();
        cardDeck.shuffle();
        cardDeck.print(System.out);

        //setup the game with 3 robots
        LinkedList<Player> playerList = new LinkedList<>();
        playerList.add(new Player("Josh"));
        playerList.add(new Player("Chris Rock"));
        playerList.add(new Player("Jimmy"));
        cardDeck.deal(playerList);

        //Print out their hands
        System.out.println("\nPlayer hands");
        for (Player p: playerList) {
            System.out.printf("\n%s hand: \n", p.getName());
            p.printHand(System.out);
        }

        //Play the game
        System.out.println("\nGame Loop\n");
        int numRounds = playerList.get(0).getHand().size();
        for (int i = 0; i < numRounds; i++) {
            roundWinner(playerList, i);
        }

        //TODO Implement the score

    }

    /**
     * Determine the winner of a round. Round number will determine the card played.
     * @param playerList Players
     * @param roundNumber Round number
     */
    public static void roundWinner(LinkedList<Player> playerList, int roundNumber){
        Player winner = playerList.get(0);
        for (int i = 0; i < playerList.size() - 1; i++) {
            //Look into each players hand and get the card from the current round and do a comparison!
            //If the comparison (compareTo method) is >0 adjust the winner
            if(playerList.get(i).getHand().get(roundNumber).compareTo(playerList.get(i+1).getHand().get(roundNumber))>0){
                winner = playerList.get(i+1);
            }
            System.out.printf("%s plays %s \t \t \t  ", playerList.get(i).getName(), playerList.get(i).getHand().get(roundNumber).toString());

            if(i == playerList.size() - 2){
                System.out.printf("%s plays %s ", playerList.get(i+1).getName(), playerList.get(i+1).getHand().get(roundNumber).toString());
            }
        }
        System.out.printf("\t \t \t - %s Wins! \n", winner.getName(), winner.getHand().get(roundNumber).toString());
    }

}
