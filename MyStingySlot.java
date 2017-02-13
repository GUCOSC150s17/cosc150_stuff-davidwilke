package dw714A2;

import java.util.Random;


// This is a StingySlot class intentionally created to be buggy in different ways in order to 
// test the JUnit testing capacities

public class MyStingySlot {
	
	private int[] theSpin;
	protected static final int maxSpinNumber = 60;
	
	// Constructor, only thing to be done is initialize theSpin
	public MyStingySlot(){
		theSpin = new int[5]; 
	}
	
	// doSpin function, generates a new random int array combination and returns it.
	// The spin is also used to compute the payoff by storing the result in theSpin
	public int[] doSpin(){
		int[] result = new int[5];
		
		Random rand = new Random();
		
		for (int i=0; i<theSpin.length; i++){
			int a = rand.nextInt(maxSpinNumber) + 1; // This method found on StackOverflow
			// http://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
			result[i] = a;
			
			theSpin[i] = result[i];
		}
		return result;
	}
	
	// setSpin function, sets the spin numbers to a specific series of numbers given an array of ints
	// as input. This will largely be used in testing the rest of the functions against specific
	// combinations.
	public void setSpin(int[] arr){
		for (int i=0; i<theSpin.length; i++){
			theSpin[i] = arr[i];
		}
	}
	
	// getSpin function, returns an theSpin
	public int[] getSpin(){
		return theSpin;
	}
	
	// payoff function, returns the payoff for a spin due to the user. Cases with a payout
	// are outlined in the project description. This will be buggy!!! GIVEN from Professor Koster
	public double payoff(){
		double winnings=0; // This is what you win.  Starts at 0.
    	
        Partish p = new Partish(theSpin);
        
        if ( p.getPartSize(0) == 5 ) { winnings += 1000000; }
        
        if ( p.getPartSize(0) == 4 ) { winnings += 500; }
        
        if ( p.getPartSize(0) == 3 && p.getPartSize(1) == 2 ) { winnings += 50; }
        
        if ( p.getPartSize(0) == 3 && p.getPartSize(1) == 1 ) { winnings += 10; }
        
        //if ( p.getPartSize(0) == 2 && p.getPartSize(1) == 2 ) { winnings += 4; } 
        
        if ( p.getPartSize(0) == 2 && p.getPartSize(1) == 1 ) { winnings += 2; }
        
        // reward perfect squares
        double perfectReward = 0.1;     
        double pf = (  p.getCount( 1)
        		     + p.getCount( 4)
 		             + p.getCount( 9)
 		             + p.getCount(16)
 		             //+ p.getCount(25)
 		             + p.getCount(36)
 		             + p.getCount(49)
 		            ) * perfectReward;
        winnings += pf;
       
        // reward occurrences of 42
        double reward42 = 0.35;
        winnings += p.getCount(42) * reward42;
        
        // reward multiples of 17
        double reward17 = 0.17;
        winnings += ( p.getCount(17) + p.getCount(34) /*+ p.getCount(51)*/ ) * reward17; 
    	
    	return winnings;

	}
}
