/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. El Yirdaw
*/

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;


public class Card {


  private int identity; //clubs(1-13), diamonds(14-26), hearts(27-39), spades(40-52) identity%13 is the value

	// Default constructor with no argument
	// NOTE: You can overload the constructor but you must
	// have this default constructor and it must generate a valid card
    public Card() {
    	this.identity=1; //creates an ace of clubs
    }

    public Card(int identity){
      this.identity=identity; //creates a card based on the number in the parameter. (as defined on the comment next to private int identity)
    }

    // Override the method equals which is inherited from class Object
	  // (similar to what we did in class Employee), and make it return true if the
	  // two cards have the same suit and rank
	  // Important: Use the @Override annotation
    @Override
    public boolean equals(Object B){
      return (B instanceof Card) && (this.identity==((Card)B).identity); //checks if B is a card and then casts it into a card and checks if they have the same suit and rank
    }

	// Public static method that takes in an empty deck and constructs a
	// randomly shuffled standard 52-card deck. A standard 52-card deck
	// is comprised of 13 ranks in each of the four French suits:
	// clubs, diamonds, hearts, and spades. Each suit includes an Ace, a King,
	// a Queen, and a Jack with the numeric cards from two to ten.
	// Important: Two calls to the build deck should likely return 2 different shuffles.
    public static void buildDeck(ArrayList<Card> deck) {
      ArrayList<Integer> nums= new ArrayList<Integer>(); //creates an array of numbers to be assigned to the deck
      Random randomGenerator = new Random(); //creates the random number generator

      while(nums.size()<52){ //ensures the nums array has 52 numbers at the end
        int random=randomGenerator.nextInt(52); //picks a number between 0(inclusive)-52 (exclusive)
        if(!nums.contains(random+1)) //if the random card is already in the array, it's not added again. the +1 makes it a number between 1 (inclusive)-53 (exclusive)
        nums.add(random+1); //the card is added (if it's not already in there, per the previous line)
      }

      for (int num: nums){ //this goes through the random 1-52 arraylist we created before
        deck.add(new Card(num)); //this creates a card using the random number and adds it to the deck
      }
    }

	// Method that takes a non-empty deck and deals 2 cards to the player's hand
	// and deals 2 cards to the dealer's hand. The deck at the end of
	// this method should have 4 less cards than when it started.
    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        playerHand.add(deck.get(0)); //adds the top card from the deck to the playerHand
        deck.remove(0); //remove that card from the deck
        playerHand.add(deck.get(0)); //adds the top card from the deck to the playerHand
        deck.remove(0); //remove that card from the deck
        dealerHand.add(deck.get(0)); //adds the top card from the deck to the playerHand
        deck.remove(0); //remove that card from the deck
        dealerHand.add(deck.get(0)); //adds the top card from the deck to the playerHand
        deck.remove(0); //remove that card from the deck
    }


	// Method that takes a non-empty deck and deals 1 card to the hand.
	// The deck at the end of this method should have
	// 1 less card than when it started.
    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand) {
        hand.add(deck.get(0)); //adds the top card from the deck to the hand
        deck.remove(0); //remove that card from the deck
    }

    // Method that gets the total value of the hand. The Jack, Queen, and
    // King cards take on the value 10, while an Ace can be 1 or 11.
	// Thus, if the hand contains a 10 of Spades and a Jack of Hearts
	// it will return a 20. If the hand contains a 5 of Clubs and a
	// Queen of Spades, this method should return a 15.
    public static int getHandValue(ArrayList<Card> hand){
      int countA=0; //this counts the number of aces
      int valuewithoutA=0; //this counts the value at hand WITHOUT aces

      for(Card card:hand){ //this goes through every card at hand
        if (card.identity%13==1){ //checks if the card is an ace and counts it into the countA
        countA++;}
        else if(card.identity%13>=10||card.identity%13==0){ //checks if the card is 10,J,Q,K
          valuewithoutA=valuewithoutA+10; //adds 10 to the value if the card is 10,J,Q,K
        }
        else{
          valuewithoutA=valuewithoutA+card.identity%13; //this just adds the number on the card to the valuewithoutA
        }
      }

      if (countA==0||valuewithoutA>10) //this checks if there is no A or if the valuewithout A is more than 10 (meaning player doesn't want A to be 11)
      return valuewithoutA+countA; //this adds the number of A to the valuewithoutA and return it

      return valuewithoutA+11+countA-1; //this only happens if using A as 11 is beneficial so it adds it to the value as 11 and takes 1 away from the number of A


    }

	// Method that checks whether the given hand's value exceeds 21.
    public static boolean checkBust(ArrayList<Card> hand){
    	return getHandValue(hand)>21; //checks if the handvalue of hand is bigger than 21
    }

    // Method that conduct the dealer's turn. The return value should be
    // true if the dealer busts and false otherwise. For the dealer's turn,
    // your code should continue to hit (or get a card) if the hand is less than 17
    // otherwise stand if the hand is greater than or equal to 17.
    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
      while(getHandValue(hand)<17){ //only occurs while the hand value is less than 17
      dealOne(deck, hand); //card is dealt from the deck to hand
      }
      return checkBust(hand); //returns true if the hand busted and false if not
    }

    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
      if (checkBust(playerHand)) //if player busts...
      return 2; //dealer wins
      if (checkBust(dealerHand)) //if dealer busts (but player didn't, since we checked that earlier)
      return 1; //player wins
      if (getHandValue(playerHand)>getHandValue(dealerHand)) //if the playerhand is bigger
      return 1; //player wins
      return 2; //dealer wins (since none of them bust and the player doesn't have a bigger value)
    }

    // Method that describes the card (value and suit) located at index 1
    // in the hand. This is used to show one of the cards that the dealer has.
    public static String displayCard(ArrayList<Card> hand){
      return readCard(hand.get(1)); //this gets the top card at hand and read it using the method I created below
    }

    // Method that describes the cards (values and suits) in the hand.
    public static String displayHand(ArrayList<Card> hand){
      String result=""; //creates the string that'd be returned later
      for (Card card:hand){ //goes through each card at hand
        result+=readCard(card)+", "; //adds the card's reading to the result and add a comma after
      }
      if (result.endsWith(", ")){ //if there is a comma at the end
        return result.substring(0, result.length() - 2); //the result is returned without the comma
      }
      return result; //otherwise the result is returned by itself
    }

    //this just describes one card
    public static String readCard (Card card){
      String type=""; //this is used to store the type
      if (card.identity<14){ //identity less than 14 (1-13) is a club
        type = "Clubs";
      } else if (card.identity<27){ //identity less than 27 but above 13 (14-26) is a club
        type = "Diamonds";
      } else if (card.identity<40) { //identity less than 40 but above 26 (27-39) is a heart
        type = "Hearts";
      } else{
        type = "Spades"; //if it's not a club, diamond, or heart, it's spade
      }

      if (card.identity%13==0) //if it's divisible by 13...
      return "King of "+type; //it's a king
      else if (card.identity%13==12) //if it's remainder when divided by 13 is 12...
      return "Queen of "+type; //it's queen
      else if (card.identity%13==11) //if it's remainder when divided by 13 is 11...
      return "Jack of "+type; //it's a jack
      else if (card.identity%13==1) //if it's remainder when divided by 13 is 1...
      return "Ace of "+type; //it's ace
      else //otherwise
      return ""+card.identity%13+" of "+type; //it is a number so we can just return the number with its type
    }

    // Method that clears both the player hand and dealer hand.
    // There should be no cards in either hand after this method is called.
    public static void clearHands(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
    	playerHand.clear(); //this clears the playerhand
      dealerHand.clear(); //this clears the dealerhand
    }



    // Do not change anything after this!
    public static void main(String[] args) {

		int playerChoice, winner;
		ArrayList<Card> deck = new ArrayList<Card> ();

		playerChoice = JOptionPane.showConfirmDialog(null, "Ready to Play Blackjack?", "Blackjack", JOptionPane.OK_CANCEL_OPTION);

		if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
		    System.exit(0);


		Object[] options = {"Hit", "Stand"};
		boolean isBusted, dealerBusted;
		boolean isPlayerTurn;
		ArrayList<Card> playerHand = new ArrayList<>();
		ArrayList<Card> dealerHand = new ArrayList<>();

		do{ // Game loop
			// Clear the hands
			clearHands(playerHand, dealerHand);
			// Clear the deck and build if not enough cards left to play a new game
			if (deck.size() <= 12) {
				deck.clear();
				buildDeck(deck);
			}

		    initialDeal(deck, playerHand, dealerHand);
		    isPlayerTurn=true;
		    isBusted=false;
		    dealerBusted=false;

		    while (isPlayerTurn){
		    	System.out.println("Player hand value is:" + String.valueOf(getHandValue(playerHand)));

				// Shows the hand and prompts player to hit or stand
				playerChoice = JOptionPane.showOptionDialog(null, "Dealer shows " + displayCard(dealerHand) + "\nYour hand is: " + displayHand(playerHand) + "\nWhat do you want to do?","Hit or Stand",
									   JOptionPane.YES_NO_OPTION,
									   JOptionPane.QUESTION_MESSAGE,
									   null,
									   options,
									   options[0]);

				// Player chooses to close the game
				if (playerChoice == JOptionPane.CLOSED_OPTION)
				    System.exit(0);

				// Player chooses to Hit
				else if(playerChoice == JOptionPane.YES_OPTION){
				    dealOne(deck, playerHand);
				    System.out.println("Player hand value after hitting is:" + String.valueOf(getHandValue(playerHand)));
				    isBusted = checkBust(playerHand);
				    if(isBusted){
						// Case: Player Busts
						playerChoice = JOptionPane.showConfirmDialog(null,"Your hand: " +  displayHand(playerHand) + "\nPlayer has busted!", "You lose", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);

						isPlayerTurn = false;
				    }
				}
			    // Player chooses to Stand
				else{
				    isPlayerTurn = false;
				}
		    }
		    System.out.println("Dealer hand value is:" + String.valueOf(getHandValue(dealerHand)));

		    if (!isBusted) { // Continues if player hasn't busted
				dealerBusted = dealerTurn(deck, dealerHand);
				System.out.println("Dealer hand value after turn is:" + String.valueOf(getHandValue(dealerHand)));

				if(dealerBusted){ // Case: Dealer Busts
				    playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " + displayHand(dealerHand) + "\n \nYour hand: " + displayHand(playerHand) + "\nThe dealer busted.\nCongratulations!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

					if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						System.exit(0);
				}


				else{ //The Dealer did not bust.  The winner must be determined
				    winner = whoWins(playerHand, dealerHand);

				    if (winner == 1){ //Player Wins
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " + displayHand(dealerHand) + "\n \nYour hand: " + displayHand(playerHand) + "\nCongratulations!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }

				    else{ //Player Loses
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \nYour hand: " + displayHand(playerHand) + "\nBetter luck next time!", "You lose!!!", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }
				}
		    }
		} while(true);
    }
}
