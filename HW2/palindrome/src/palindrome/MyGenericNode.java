package palindrome;

/**
 * Our implementation of node.
 * Uses a String for the value. Could use int, double, other objects or generics.
 * @author Hanyu Yang
 *
 */
public class MyGenericNode<T> {
   
  public T value;
  public MyGenericNode<T> next;
    
  /**
   * The constructor for node.
   * @param val the value of the node.
   */
  public MyGenericNode(T val) {
    value = val;
    next = null;
  }
}