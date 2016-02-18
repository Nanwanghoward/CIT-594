package palindrome;

import java.util.EmptyStackException;

/**
 * Our implementation of Stack.
 * Uses a String for the value. Could use int, double, other objects or generics.
 * @author Hanyu Yang
 *
 * @param <T> Generic type
 */
public class MyStack<T> extends MyGenericLinkedList<T> {
  
  private MyGenericNode<T> head;
  private int size;
  
  /**
   * Constructor of Mystack -- It initializes an empty stack.
   * 
   */
  public MyStack() {
    head = null;
    size = 0;
  }
  
  /**
   * This method will take an generic element to push it into stack.
   * @param value Generic type parameter value is pushed into stack.
   * @return It returns the element pushed into stack.
   */
  public T push(T value) {
    MyGenericNode<T> node = new MyGenericNode<T>(value);
    node.next = head;
    head = node;
    size++;
    return value;
  }
  
  /**
   * This method will pop out the first element of stack.
   * @return It returns the element which is popped out.
   */
  public T pop() {
    T value;
    if (size == 0) {
      throw new EmptyStackException();
    }
    value = head.value;
    head = head.next;
    size--;
    return value;
  }
  
  /**
   * This method will give the current size of stack.
   * @return It returns the size of stack.
   */
  public int size() {
    return size;
  }
  
  /**
   * This method will tell us if this stack is empty or not.
   * @return It returns true if empty, false if not.
   */
  public boolean isEmpty() {
    return (size == 0);
  }  
}
