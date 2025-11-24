import java.util.*;

public class Deck {
	private List<Card> deckCards;
	
	public Deck() {
		deckCards = new ArrayList<>();
		
		String[] suits = {"s" , "h" , "c" , "d"};
		String[] ranks = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k"};
		
		for (String suit : suits) {
			for (String rank : ranks) {
				deckCards.add(new Card(suit, rank));
			}
		}
	}
	
	public Card draw() {
		if (deckCards.isEmpty()) {
			return null;
		}
		
		return deckCards.remove(0);
	}
	
	public void shuffle() {
		Collections.shuffle(deckCards);
	}
	
	
	public void printDeck() {
		for (Card card : deckCards) {
			System.out.println(card);
		}
	}
	
}
