package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Hangman is a game class for Evil Hangman game version.
 * @author Hany
 *
 */
public class Hangman {
  private Map<Integer, ArrayList<String>> dict;
  private Map<Integer, ArrayList<String>> cur;
  private int length;
  
  private int diff;
  private List<String> wordls;
  private char[] printString;
  private Set<Character> picked;
  private Set<Character> unpicked;
  
  /**
   * This is a constructor for Hangman class. 
   */
  public Hangman() {
    dict = openFileGUI();
    length = 0;
    diff = 0;
    wordls = new ArrayList<String>();
    picked = new HashSet<Character>();
    unpicked = new HashSet<Character>(); 
    for (char ch = 'a';ch <= 'z';ch++) {
      unpicked.add(ch);
    } 
  }
  
  /**
   * This is a reset method which reset the picked char list.
   * This method is called before the game is played again.
   */
  public void reset() {
    picked = new HashSet<Character>();
    unpicked = new HashSet<Character>(); 
    for (char ch = 'a';ch <= 'z';ch++) {
      unpicked.add(ch);
    } 
  }
  
  /**
   * This is a setLength method which takes an int value, then set the length to that value.
   * So that we can set the length of word, pick the specific length of word list.
   * Reset the word computer wants to display.
   * @param length the int value we use to set length of word to that.
   */
  public void setLength(int length) {
    this.length = length;
    printString = new char[length];
    for (int i = 0; i < length; i++) {
      printString[i] = '_';
    }
  }
  
  /**
   * This is a method return the number of words left in current list (current word family).
   * @return the number words in current word family.
   */
  public int getWordlsNum() {
    return wordls.size();
  }
  
  /**
   * This is a method used to prepare the game. 
   * It takes an int to check the corresponding length of word list exists or not 
   * @param length the integer value we take as the length of word
   * @return the word family with specific length exits return true, else return false.
   */
  public boolean gamePrep(int length) {
    if (!dict.containsKey(length)) {
      return false;
    }
    wordls = new ArrayList<String>(dict.get(length));  
    return true;
  }
  
  /**
   * This method is used to return the set of picked letter.
   * @return return a set of letters we have picked.
   */
  public Set<Character> getPicked() {
    return picked;
  }
  
  /**
   * This method is used to return the set of unpicked letter.
   * @return return a set of letters we haven't picked.
   */
  public Set<Character> getUnPicked() {
    return unpicked;
  }
  
  /**
   * This method is used to set the difficulty index in game.
   * @param diff the parameter it takes to set the diff index in game.
   */
  public void setDiff(int diff) {
    this.diff = diff;
  }
  
  /**
   * This method is called to get the string which shows the all letters user have guessed out.
   * @return It returns a string shows all letters that are guessed right.
   */
  public String getResult() {
    return new String(printString);
  }
  
  
  /**
   * This is method used to create a random word so that computer can pretend to choose this word at the beginning.
   * @return It returns the random word.
   */
  public String getAWord() {
    Random rand = new Random();
    return wordls.get(rand.nextInt(wordls.size()));
  }
  
  /**
   * This method is used to create an index to choose the word family
   * @param list The list is the word family we want to calculate.
   * @param key This key stores the info that which position has specific char. 
   * @return It returns the index of diff, the higher, the more difficult to guess.
   */
  public int getIndex(ArrayList<String> list, int key) {
    char[] binary = Integer.toBinaryString(key).toCharArray();
    int count = 0;
    for (int i = 0; i < binary.length ; i++) {
      if (binary[i] == '1') {
        // count "ing" postfix to 3. Assume user get 'i' or 'f' at the last 3 char, will guess word end with"ing" or "ful"       
        if ((list.get(0).charAt(length - 3) == 'i' || list.get(0).charAt(length - 3) == 'f') && binary.length == length - 2) {
          count += 2;
        }
        //count "ed" postfix to 2. Assume user get 'e' or 'l' at the last 2 char, will guess word end with"ed" or "ly"
        if ((list.get(0).charAt(length - 2) == 'e' || list.get(0).charAt(length - 2) == 'l') && binary.length == length - 1) {
          count ++ ;
        }
        count ++;
      }
    }
    if (list.size() > 30) {
      return 30 * (length - count + 1);
    } else {
      return list.size() *  (length - count + 1);
    }
  }
  
  /**
   * This method takes the char, to decide if user guess the letter right.
   * @param ch The char user put in to check if ch is in this word.
   * @return It returns true if ch in this word, else false.
   */
  public boolean gameRound(char ch) {
    boolean result = false;
    picked.add(ch);
    unpicked.remove(ch);
    makeAGuess(ch);
    int max = 0;
    int tempKey = 0;
    for (int key: cur.keySet()) {
      int index = (diff == 1 ? cur.get(key).size() : getIndex(cur.get(key), key));
      if (index > max) {
        max = index;
        tempKey = key;
      }     
    }
    wordls = cur.get(tempKey);
    char[] binary = Integer.toBinaryString(tempKey).toCharArray();
    for (int i = 0; i < binary.length ; i++) {
      if (binary[binary.length - 1 - i] == '1') {
        printString[i] = ch;
        result = true;
      }
    }
    return result;
  }
  
  /**
   * This method takes a char to create a HashMap, key as different patterns represent word families, value as Arraylists store word families.
   * @param ch The char we use to create word families map.
   */
  public void makeAGuess(char ch) {
    HashMap<Integer, ArrayList<String>> temp = new HashMap<>();
    for (String str:wordls) {
      int key = 0;
      for (int i = 0; i < str.length() ; i++) {
        if (str.charAt(i) == ch) {
          key += Math.pow(2, i);
        }
      }
      if (temp.containsKey(key)) {
        temp.get(key).add(str);
      } else {
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add(str);
        temp.put(key, list1);
      }
    }
    cur = temp;
  }
  
  /**
   * This method is used to open a file.
   * @return It returns a HashMap, key as length of word, value as ArrayList of words with that length. 
   */
  public Map<Integer, ArrayList<String>> openFileGUI() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "txt Files","txt");
    chooser.setFileFilter(filter);
    int response = chooser.showOpenDialog(chooser);
    if (JFileChooser.APPROVE_OPTION == response ) {
      File f = chooser.getSelectedFile();
      System.out.println(f.getName() + " is opened.");
      return readTextFile(f);
    }
    return null;
  }

  /**
   * This method is used to read every line in a file.
   * @param f the File instance we want to read data from
   * @return It returns a HashMap, key as length of word, value as ArrayList of words with that length.
   */
  public Map<Integer, ArrayList<String>> readTextFile(File f) {
    Map<Integer, ArrayList<String>> hm = new HashMap<>();
    if (f == null || !f.isFile() || !f.canRead()) {
      return null;
    }       
    try {
      BufferedReader reader = new BufferedReader(new FileReader(f));
      String line;
      while ( (line = reader.readLine()) != null) {
        if (hm.containsKey(line.length())) {
          hm.get(line.length()).add(line);
        } else {
          ArrayList<String> temp = new ArrayList<String>();
          temp.add(line);
          hm.put(line.length(), temp);
        }         
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
    }
    return hm;
  }
}  
  
  


