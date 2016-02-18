package palindrome;

import java.util.Scanner;

/**
 * This class implements a tester to take user's input and check if it satisfies the rule.
 * @author Hany
 *
 */
public class PalindromeTester {
  
  /**
   * The main method does IN/OUT.
   * @param args Trivial parameter
   */
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    PalindromeChecker pc = new PalindromeChecker();
    
    while (true) {
      System.out.println("Please input your sentence to check if it is a palindrome:");
      String str = scan.nextLine();
      
      boolean bl = pc.check(str);
      if (bl) {
        System.out.println("It is a palindrome.");
      } else {
        System.out.println("It is not a palindrome.");
      }
    }  
    
  }
  

}
