
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class FibonacciExample {
 
  public static long fiboAt(int position) {
    /*
     * Approach -> Top-Down (Recursive)
     * Time Complexity -> O(2^N)
     * Space Complexity -> O(N) for the recursion stack. 
     * Observation -> Multiple recursive calls with same arguements for some postions.
     */	  
    validatePosition(position);
    if (position == 0 || position == 1) return position; // Base case.
    return fiboAt(position - 1) + fiboAt(position - 2);
  }	

  public static long fiboAt(int position, long[] memory /* Defines the Fibonaccci number at different positions (/ indices). */) {
    /*
     * Approach -> Memorization, Top-Down (Recursive) 
     * Time Complexity -> O(N) 
     * Space Complexity -> O(N) - ON(N) for recrsion stack at worst case + O(N) for memory.
     * Observation -> Improved Time Complexity.
     */

    validatePosition(position);
   
    if (position == 0 || position == 1) return position; // Base case.

    if (memory == null) {
	memory = new long[position + 1]; // As we are using '0' based indexing, the memory must be of size (position + 1).
        Arrays.fill(memory, Long.MIN_VALUE);
        memory[0] = 0; // Not required. Just for theoratical correctness.
	memory[1] = 1; // Not required. just for theoratical correctness.
    }
        
    if (memory[position] == Long.MIN_VALUE) {
      memory[position] = fiboAt(position - 1, memory) + fiboAt(position - 2, memory);
    }

    return memory[position];
  }

  public static long fiboDP(int position) { 
    /*
     * Approach -> Dynamic Programming (Tabulation), Bottom-Up (Iterative) 
     * Time Complexity -> O(N) 
     * Space Complexity -> O(N) - O(N) for DP state memory.
     * Observation -> Both Time and Space Complexity is the same as of Memorization but,
                      the use of recursion stack has been eliminated as stack has very little memory
		      thus, avoiding the stack overflow.
     */

    // DP State: dp[i] -> Fibonacci number at 'ith' position.
    // As array uses '0' based indexing, fibonacci number for Nth position will be at Nth index and the size
    // of DP state array will be (N + 1). 	  

    validatePosition(position);
	  
    if (position == 0 || position == 1) return position; // Base case.	  

    long[] dp = new long[position + 1];  
 
    dp[0] = 0;  
    dp[1] = 1; 

    for (int i = 2; i <= position; i++) {
      dp[i] = dp[i - 1] + dp[i - 2]; 
    }
 
    return dp[position]; 
  }

  public static long fiboDPSO(int position) { 
    /*
     * Approach -> Dynamic Programming (Space optimized), Bottom-Up (Iterative) 
     * Time Complexity -> O(N) 
     * Space Complexity -> O(1)
     * Observation -> Improved Space Complexity as compared to Tabulation.
     */

    // DP State: dp[i] -> Fibonacci number at 'ith' position.
    // As array uses '0' based indexing, fibonacci number for Nth position will be at Nth index and the size
    // of DP state array will be (N + 1). 	  

    validatePosition(position);
	  
    if (position == 0 || position == 1) return position; // Base case	  
    
    long previousToPrevious = 0; 
    long previous = 1; 
    long current = 1;

    for (int i = 2; i <= position; i++) {
      current = previous + previousToPrevious;
      previousToPrevious = previous;
      previous = current;      
    }
 
    return current; 
  }


  /* Input Validation Function: Throwing error if the position of the Fibonacci number is negative... */
  public static void validatePosition(int position) {
    if (position < 0) throw new IllegalArgumentException("Position of a number in the Fibonacci series cannot be negative!");
  }
  
  /* Utility Fnction: Decoding the error message if any error caused during runtime... */
  public static String decodeException(CompletionException exception) {
    return exception.getMessage();
  }

  /* Main Fnction */
  public static void main (String[] args) {

   System.out.println("\n--- Finding the 'Nth' Fibonacci Number ---\n");	  
   
   System.out.print("Enter the position of the fibonaci number: "); 
   java.util.Scanner inputScanner = new java.util.Scanner(System.in); // Opening the input...
   int position = inputScanner.nextInt(); // Position of the fibonacci number / Nth fibonacci number. (Taking input...)
   inputScanner.close(); // Closing the input...  

   // Output for Recursive Algorithm...
   try { 
    System.out.print("OUTPUT (Recursive Algorithm) -> ");
    System.out.print(
      CompletableFuture.supplyAsync(() -> fiboAt(position)) // Function Call...
      .orTimeout(10, TimeUnit.SECONDS)
      .join() 
      + "\n"
    ); 
   } 
   catch (CompletionException exception) { System.out.print(decodeException(exception) + "\n"); }
   
   // Output for Memorization Algorithm...
   try { 
    System.out.print("OUTPUT (Memorization Algorithm) -> ");
    System.out.print(
      CompletableFuture.supplyAsync(() -> fiboAt(position, null)) // Function Call...
      .orTimeout(10, TimeUnit.SECONDS)
      .join() 
      + "\n"
    ); 
   } 
   catch (CompletionException exception) { System.out.print(decodeException(exception) + "\n"); }

   // Output for Tabulation Algorithm...
   try { 
    System.out.print("OUTPUT (Tabulation Algorithm) -> ");
    System.out.print(
      CompletableFuture.supplyAsync(() -> fiboDP(position)) // Function Call...
      .orTimeout(10, TimeUnit.SECONDS)
      .join() 
      + "\n"
    ); 
   } 
   catch (CompletionException exception) { System.out.print(decodeException(exception) + "\n"); }

   // Output for Space Optimized Algorithm...
   try { 
    System.out.print("OUTPUT (Space Optimized Algorithm) -> ");
    System.out.print(
      CompletableFuture.supplyAsync(() -> fiboDPSO(position)) // Function Call...
      .orTimeout(10, TimeUnit.SECONDS)
      .join() 
      + "\n"
    ); 
   } 
   catch (CompletionException exception) { System.out.print(decodeException(exception) + "\n"); }

  }
}
