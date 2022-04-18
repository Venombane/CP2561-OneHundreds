package testclasses;

import cardgame.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Card tester class
 */
public class CardTester {

    public static void main(String[] args) {
        System.out.println("Card Tester!");

        Card c1 = new Card(12);
        Card c2 = new Card(13);
        System.out.println("\nCard 2 should win");
        printComparison(c1, c2);

        c1.setWild(true);
        System.out.println("\nCard 1 should win");
        printComparison(c1, c2);

        c2.setWild(true);
        System.out.println("\nCard 1 should win");
        printComparison(c1, c2);

        c1.setWild(false);
        System.out.println("\nCard 2 should win");
        printComparison(c1, c2);

        System.out.println("Let's test the compare To method");
        List<Card> cardList = generateRandomList();
        cardList.forEach(c-> System.out.print(c.toString() + " "));
        cardList.sort( (o1, o2) -> o1.compareTo(o2));
        System.out.println("\nPrint sorted list");
        cardList.forEach(c-> System.out.print(c.toString() + " "));
    }

    public static void printComparison(Card c1, Card c2){
        System.out.printf("Card 1: %s, Card 2: %s, Compare %d, Winner: %s\n", c1.toString(), c2.toString(),
                Card.compare(c1,c2), Card.compare(c1,c2) < 0 ? "Card 1" : "Card 2");
    }

    /**
     * Generarte a Random List of cards - like will be used in the game
     * @return
     */
    public static List<Card> generateRandomList(){
        LinkedList<Card> cardList = new LinkedList<>();
        for (int i = 1; i <= 100; i++) {
            cardList.add(new Card(i));
        }

        //4 Random Wilds
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(100); //Draws 0 to 99 which is the index

        for (int i = 0; i < 4; i++) {
            int j = randomSupplier.get();
            if(!cardList.get(j).isWild()){
                cardList.get(j).setWild(true);
            } else {
                i--; //try again
            }
        }
        return cardList;
    }

}
