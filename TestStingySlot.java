package dw714A2;

import org.junit.*;
import static org.junit.Assert.*;

public class TestStingySlot {
	
	// public instance of the StingySlot class used for testing
	public MyStingySlot slot = new MyStingySlot();
	
	/*
	 * NOTE:
	 * The following three test functions are to test the doSpin and setSpin functions.
	 * These do not check the payoff, and only check to see if the spin is set properly.
	 */
	
	// Test a single spin to see if all the numbers are in the correct range
	@Test public void rangeTest(){
		int[] testMe = slot.doSpin();
		assertTrue("Range Check", ((testMe[0] >= 1 && testMe[0] <= 60) && (testMe[1] >= 1 && testMe[1] <= 60)
				&& (testMe[2] >= 1 && testMe[2] <= 60) && (testMe[3] >= 1 && testMe[3] <= 60) 
				&& (testMe[4] >= 1 && testMe[4] <= 60)));
	}
	
	
	// Test 100 spins to see if all the numbers are in the correct range
	@Test public void iterativeRangeTest(){
		for (int i=0; i < 100; i++){
			int[] testMe = slot.doSpin();
			assertTrue("Range Check", ((testMe[0] >= 1 && testMe[0] <= 60) && (testMe[1] >= 1 && testMe[1] <= 60)
					&& (testMe[2] >= 1 && testMe[2] <= 60) && (testMe[3] >= 1 && testMe[3] <= 60) 
					&& (testMe[4] >= 1 && testMe[4] <= 60)));
		}
	}
	
	// Test setSpin method, ensure that it is correctly setting the spin numbers 
	@Test public void setSpinTest(){
		int[] test = {1, 2, 3, 4, 5};
		slot.setSpin(test);
		for (int i = 0; i < 5; i++){
			assertTrue("setSpin Test", slot.getSpin()[i] == i+1 );
		}
	}
	
	
	/*
	 * NOTE:
	 * The following test function is a test of the expected value for the slot. The
	 * expected value is determined by getting the payoff of a random spin 1,000,000
	 * times, and then the function will assertTrue if this collective payoff is less 
	 * than the $2,000,000 it would have cost to play 1,000,000 times.
	 * THIS WILL TAKE A LONG TIME. On average, my tests would take about 40 seconds.
	 */
	@Test public void expectedValue(){
		double totalPayoff = 0.0;
		long averagePayoff = 2000000;
		long numberOfPlays = 1000000;
		
		for (int i = 0; i < numberOfPlays; i++){
			int[] temp = slot.doSpin(); // Only used to spin the slot, temp is not used
			totalPayoff += slot.payoff();
		}
		
		assertTrue("ExpectedValue Test", totalPayoff < averagePayoff);
	}
	
	
	
	/*
	 * NOTE: 
	 * Below are a series of small functions testing different combinations of possible
	 * payoffs for the StingySlot Class. The purpose of separating them into many smaller functions
	 * rather than one larger one for each exclusive payout was in order to find more specific
	 * bugs that may be present only for certain implementations of the payoff() function.
	 */
	
	// Test for correct payoff when all five numbers are the same, and nothing else
	@Test public void allSamePayoff(){
		int[] test = {2,2,2,2,2};
		slot.setSpin(test);
		assertTrue("Same Payoff", slot.payoff() == 1000000.0);
	}
	
	// Test for correct payoff with four of the same numbers and nothing else
	@Test public void fourAndOnePayoff(){
		int[] test = {11,12,12,12,12};
		slot.setSpin(test);
		assertTrue("Four and One Payoff", slot.payoff() == 10000.0);
	}
	
	// Test for correct payoff with a full house and nothing else
	@Test public void fullHousePayoff(){
		int[] test = {11,11,12,12,12};
		slot.setSpin(test);
		assertTrue("Full House Payoff", slot.payoff() == 500.0);
	}
	
	// Test for correct payoff with three of a kind and nothing else
	@Test public void threeOfAKindPayoff(){
		int[] test = {11, 11, 11, 2, 3};
		slot.setSpin(test);
		assertTrue("Three of a kind Payoff", slot.payoff() == 10.0);
	}
	// Test for correct payoff with one pair and nothing else
	@Test public void onePairPayoff(){
		int[] test = {11, 10, 11, 2, 3};
		slot.setSpin(test);
		assertTrue("One pair payoff", slot.payoff() == 2.0);
	}
	
	// Test for correct payoff with two pairs and nothing else
	@Test public void twoPairPayoff(){
		int[] test = {11, 10, 11, 2, 2};
		slot.setSpin(test);
		assertTrue("Two pair payoff", slot.payoff() == 4.0);
	}
	
	// Test for one perfect square and nothing else
	@Test public void onePerfectSquarePayoff(){
		int[] test = {16, 10, 11, 2, 3};
		slot.setSpin(test);
		assertTrue("One perfect square Payoff", slot.payoff() == 0.10);
	}
	
	// Test for one 42 and nothing else
	@Test public void oneFourtyTwoPayoff(){
		int[] test = {42, 10, 11, 2, 3};
		slot.setSpin(test);
		assertTrue("One 42 payoff", slot.payoff() == 0.35);
	}
	
	// Test for one number divisible by 17 and nothing else
	@Test public void oneDivis17Payoff(){
		int[] test = {34, 10, 11, 2, 3};
		slot.setSpin(test);
		assertTrue("One 17 payoff", slot.payoff() == 0.17);
	}
	
	// Test all five the same + perfect square
	@Test public void allFiveAndSquare(){
		int[] test = {16, 16, 16, 16, 16};
		slot.setSpin(test);
		assertTrue("All Five Same + Square Payoff", slot.payoff() == 1000000.50);
	}
	
	// Test all five the same + 42
	@Test public void allFiveAnd42(){
		int[] test = {42, 42, 42, 42, 42};
		slot.setSpin(test);
		assertTrue("All Five Same + 42 Payoff", slot.payoff() == 1000001.75);
	}
	
	// Test all five the same + divisible by 17
	@Test public void allFiveAndDivis17(){
		int[] test = {17, 17, 17, 17, 17};
		slot.setSpin(test);
		assertTrue("All Five Same + 17 Payoff", slot.payoff() == 1000000.85);
		int[] test2 = {34, 34, 34, 34, 34};
		slot.setSpin(test2);
		assertTrue("All Five Same + 34 Payoff", slot.payoff() == 1000000.85);
	}
	
	// Test four of a kind + perfect square + 42
	@Test public void fourOfAKindAndSquareAnd42(){
		int[] test = {42, 42, 42, 42, 16};
		slot.setSpin(test);
		assertTrue("Four Same + 42 + Square Payoff", slot.payoff() == 10001.50);
	}
	
	// Test four of a kind + 42 + divisible by 17
	@Test public void fourOfAKindAnd42And17(){
		int[] test = {42, 42, 42, 42, 17};
		slot.setSpin(test);
		assertTrue("Four Same + 42 + 17 Payoff", slot.payoff() == 10001.57);
	}
	
	// Test full house + perfect square + divisible by 17
	@Test public void fullHouseSquareAnd17(){
		int[] test = {17, 17, 16, 16, 17};
		slot.setSpin(test);
		assertTrue("Full House + Square + 17 Payoff", slot.payoff() == 500.71);
	}
	
	// Test full house + divisible by 17 + 42
	@Test public void fullHouse17And42(){
		int[] test = {42, 42, 42, 17, 17};
		slot.setSpin(test);
		assertTrue("Full House + 42 + 17 Payoff", slot.payoff() == 501.39);
	}
	
	// Test three of a kind + perfect square + divisible by 17 + 42
	@Test public void threeOfAKindSquare17And42(){
		int[] test = {42, 42, 42, 16, 17};
		slot.setSpin(test);
		assertTrue("Three Same + Square + 42 + 17 Payoff", slot.payoff() == 11.32);
	}
	
	// Test one pair + perfect square + divisible by 17 + 42
	@Test public void pairSquare17And42(){
		int[] test = {42, 42, 49, 10, 17};
		slot.setSpin(test);
		assertTrue("Pair + Square + 42 + 17 Payoff", slot.payoff() == 2.97);
	}
	
	// Test two pair + perfect square + divisible by 17 + 42
	@Test public void twoPairSquare17And42(){
		int[] test = {42, 42, 49, 49, 17};
		slot.setSpin(test);
		assertTrue("Two Pair + Square + 42 + 17 Payoff", slot.payoff() == 5.07);
	}
	
}
