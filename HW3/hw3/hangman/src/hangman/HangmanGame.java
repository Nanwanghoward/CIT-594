package hangman;

import java.util.Scanner;

/**
 * This is Hangman Running class. It creates a Hangman class running in main function.
 * @author Hany 
 *
 */
public class HangmanGame {
  /**
   * The main method which takes a Hangman instance to run the game.
   * @param args
   */
  public static void main(String[] args) {
    Hangman hangman = new Hangman();
    boolean play = true;
    boolean win = false;
    String winner;
    Scanner scan = new Scanner(System.in);

    System.out.println("This is Evil Hangman. Do you want to play?(y/n):");
    String temp = scan.nextLine();
    if (temp.toLowerCase().equals("y")) {
      play = true;
    } else {
      play = false;
    }
    
    //set the guess length
    while (play) {
      String finals = "";
      String result = "";
      win = false;
      boolean flag = true;
      int length = 0;
      int guessTimes = 0;
      while (flag) {
        //set the difficulty of game (easy is normal mode)
        System.out.println("Please choose the difficulty: 1.easy 2.hard:");
        String input = scan.nextLine();
        int diff = 0;
        try {
          diff = Integer.parseInt(input);
          if (diff != 1 && diff != 2) {
            flag = true;
            continue;
          }
        } catch (NumberFormatException e) {
          flag = true;
          continue;
        }
        hangman.setDiff(diff);
        
        System.out.println("Please choose a word length:");
        input = scan.nextLine();
        try {
          length = Integer.parseInt(input);
          if (hangman.gamePrep(length)) {
            flag = false;
          } else {
            flag = true;
            System.out.println("The word length not allowed!");
          }
        } catch (NumberFormatException e) {
          flag = true;
          System.out.println("The word length not allowed!");
        }
      }
      hangman.setLength(length);
      result = hangman.getResult();
      //set the guess times
      flag = true;
      while (flag) {
        System.out.println("Please choose a guess times:");
        try {
          guessTimes = Integer.parseInt(scan.nextLine());
          if (guessTimes >= 26 || guessTimes <= 0) {
            System.out.println("Guess times not allowed!");
            flag = true;
            continue;
          } else {
            flag = false;
          }
        } catch (Exception e) {
          flag = true;
          System.out.println("Guess times not allowed!");
          continue;
        }
      }
      
      flag = true;
      while (flag && guessTimes > 0) {
        char ch = ' ';    
        System.out.println("For now, you have " + guessTimes + " guess chances");

        System.out.println("For now, you have picked:");
        if (hangman.getPicked().isEmpty()) {
          System.out.println("None");
        } else {
          for (char c: hangman.getPicked()) {
            System.out.print(c);
            System.out.print(' ');
          }
          System.out.println("");
        }  
        System.out.println("Which character you would choose? (Enter \"debug\" for output rest word number)");
        String str = scan.nextLine();
        if (str.length() != 1) {
          if (str.equals("debug")) {
            System.out.format("The rest word number is %d\n", hangman.getWordlsNum());
          }
          flag = true;
          continue;
        } else {
          ch = Character.toLowerCase(str.charAt(0));
          if (ch < 'a' || ch > 'z') {
            flag = true;
            continue;
          } else {
            if (hangman.getPicked().contains(ch)) {
              System.out.println("You've already picked this char!");
              flag = true;
              continue;
            }
            flag = false;
          }     
        }
        flag = !hangman.gameRound(ch);
        if (flag) {
          guessTimes--;
        } else {
          result = hangman.getResult();
        }
        System.out.println(result); 
        
        //test the 
       
        if (result.contains("_")) {
          flag = true;
          continue;
        }
        finals = result;
        break;      
      }
      if (guessTimes > 0) {
        win = true;
      } else {
        win = false;
      }     
      winner = win ? "You" : "Computer";
      if (finals.equals("")) {
        finals = hangman.getAWord();
      }
      System.out.format("The final mystery word is %s\n", finals);
      System.out.println(winner + " won.\n Do you want to play again?(y/n):");
      temp = scan.nextLine();
      if (temp.toLowerCase().equals("y")) {
        play = true;
        hangman.reset();
      } else {
        play = false;
      }  
    
    }
    System.out.println("Bye!");    
  }
}
