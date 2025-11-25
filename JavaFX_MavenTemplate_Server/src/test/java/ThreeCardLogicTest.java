import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

class ThreeCardLogicTest {
	
	private Card c(String suit, String rank) {
		return new Card(suit, rank);
	}
	
	@Test
	void EvaluatePair() {
		List<Card> hand = List.of(c("h", "5"), c("d", "5"), c("s", "9"));
		
		assertEquals(PokerEngine.HandRank.PAIR, PokerEngine.evaluate(hand));
	}
	
	@Test
	void EvaluateFlush() {
		List<Card> hand = List.of(c("h", "3"), c("h", "10"), c("h", "q"));
		
		assertEquals(PokerEngine.HandRank.FLUSH, PokerEngine.evaluate(hand));
	}
	
	@Test
	void EvaluateStraight() {
		List<Card> hand = List.of(c("s", "4"), c("d", "5"), c("c", "6"));
		
		assertEquals(PokerEngine.HandRank.STRAIGHT, PokerEngine.evaluate(hand));
	}
	
	@Test
	void EvaluateStraightFlush() {
		List<Card> hand = List.of(c("c", "5"), c("c", "6"), c("c", "7"));
		
		assertEquals(PokerEngine.HandRank.STRAIGHT_FLUSH, PokerEngine.evaluate(hand));
	}
	
	@Test
	void Evaluate_AceLowStraight() {
		List<Card> hand = List.of(c("h", "a"), c("d", "3"), c("s", "2"));
		
		assertEquals(PokerEngine.HandRank.STRAIGHT, PokerEngine.evaluate(hand));
	}
	
	@Test
	void Evaluate_Trips() {
		List<Card> hand = List.of(c("h", "7"), c("d", "7"), c("s", "7"));
		
		assertEquals(PokerEngine.HandRank.THREE_OF_A_KIND, PokerEngine.evaluate(hand));
	}
	
	@Test 
	void PairPlus_Pair() {
		List<Card> hand = List.of(c("h", "9"), c("d", "9"), c("s", "2"));
		
		assertEquals(5, PokerEngine.pairPlusPayout(5, hand));
	}
	
	@Test
	void PairPlus_Flush() {
		List<Card> hand = List.of(c("s", "4"), c("s", "7"), c("s", "10"));
		
		assertEquals(15, PokerEngine.pairPlusPayout(5, hand));
	}
	
	@Test
	void PairPlus_Trips() {
		List<Card> hand = List.of(c("h", "6"), c("d", "6"), c("s", "6"));
		
		assertEquals(150, PokerEngine.pairPlusPayout(5, hand));
	}
	
	@Test 
	void PairPlus_StraightFlush() {
		List<Card> hand = List.of(c("c", "9"), c("c", "10"), c("c", "j"));
		
		assertEquals(200, PokerEngine.pairPlusPayout(5, hand));
	}
	
	@Test
	void PairPlus_NoWin() {
		List<Card> hand = List.of(c("h", "2"), c("d", "7"), c("s", "j"));
		
		assertEquals(0, PokerEngine.pairPlusPayout(5, hand));
	}
	
	
	@Test
	void CompareHands_PlayerWins() {
		List<Card> dealer = List.of(c("h", "2"), c("d", "8"), c("s", "9"));
		List<Card> player = List.of(c("c", "k"), c("d", "k"), c("s", "5"));
		
		assertTrue(PokerEngine.compareHands(player, dealer) > 0);

	}
	
	@Test
	void CompareHands_DealerWins() {
		List<Card> dealer = List.of(c("h", "q"), c("d", "q"), c("s", "q"));
		List<Card> player = List.of(c("c", "9"), c("d", "j"), c("s", "4"));
		
		assertTrue(PokerEngine.compareHands(player, dealer) < 0);

	}
	
	@Test
	void CompareHands_Tie() {
		List<Card> dealer = List.of(c("h", "a"), c("d", "a"), c("s", "a"));
		List<Card> player = List.of(c("c", "a"), c("s", "a"), c("d", "a"));
		
		assertEquals(0, PokerEngine.compareHands(player, dealer));

	}
	
	
	@Test
	void TestDealerQualifies_Yes() {
		List<Card> hand = List.of(c("h", "q"), c("d", "3"), c("s", "8"));
		assertTrue(PokerEngine.dealerQualifies(hand));
	}
	
	@Test
	void TestDealerQualifies_No() {
		List<Card> hand = List.of(c("h", "j"), c("d", "3"), c("s", "8"));
		assertFalse(PokerEngine.dealerQualifies(hand));
	}
	
	
	
	
	
}
