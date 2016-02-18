package palindrome;

/**
 * This is a class used to check if the string satisfies the rule of palindrome.
 * @author Hanyu Yang
 *
 */
public class PalindromeChecker {
  
  private MyStack<Character> s1;
  private MyStack<Character> s2;
  
  /**
   * The constructor of palindrome -- It initializes a PalindromeChecker class.
   */
  public PalindromeChecker() {
    s1 = new MyStack<Character>();
    s2 = new MyStack<Character>();
  }
  
  /**
   * This method will check the string is palindrome or not
   * @param str The string is checked.
   * @return If the string satisfies the rule, returns true, else returns not.
   */
  public boolean check(String str) {
    int size = 0;
    s1 = new MyStack<Character>();
    s2 = new MyStack<Character>();
    for (int i = 0;i < str.length();i++) {
      if (isChar(str.charAt(i))) {
        s1.push(str.charAt(i));
      }
    }
    size = s1.size();
    if (size == 0) {
      return true;
    }
    for (int i = size; i > (size / 2); i--) {
      s2.push(s1.pop());
    }
    if (s2.size() > size / 2) {
      s2.pop();
    }
    while (!s1.isEmpty()) {
      if (Character.toLowerCase(s1.pop()) != Character.toLowerCase(s2.pop())) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * This method is used to tell the character is valid or not.
   * @param ch The character we want to check.
   * @return It returns true if the character is valid, false if not.
   */
  public boolean isChar(char ch) {
    return (ch >= 48 && ch <= 57 || ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122);
  }
}
