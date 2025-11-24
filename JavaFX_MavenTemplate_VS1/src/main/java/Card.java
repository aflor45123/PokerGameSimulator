
public class Card {
	
	private String suit;
	private String rank;
	
	public Card(String s, String r) {
		this.suit = s;
		this.rank = r;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return rank + "" + suit;
	}
}
