package palindrome;

/**
 * Our implementation of LinkedList using MyNode.
 * @author Hanyu Yang
 *
 */
public class MyGenericLinkedList<T> {

  private MyGenericNode<T> head;
  private int size;

  /**
   * The constructor - initializes an empty linked list.
   */
  public MyGenericLinkedList() {
    head = null;
    size = 0;
  }

  /**
   * This method will create a new node.
   * It will add it as the first node of the linked list.
   * @param value the value to be added
   */
  public void addFirst(T value) {

    MyGenericNode<T> node = new MyGenericNode<T>(value);
    node.next = head;
    head = node;
    size++;
  }

  /**
   * This method will create a new node.
   * It will add it as the last node of the linked list.
   * @param value the value to be added
   */
  public void addLast(T value) {

    if (getSize() == 0) {
      addFirst(value);
    } else {
      MyGenericNode<T> node = new MyGenericNode<T>(value);

      MyGenericNode<T> i = head;

      while (i.next != null) {
        i = i.next;
      }

      i.next = node;
      size++;
    }
  }

//  /**
//   * This methods displays the contents of the linked list.
//   */
//  public void display() {
//
//    if (getSize() != 0) {
//
//
//      MyGenericNode<T> i = head;
//
//      System.out.println("Printing out the contents of the linked list");
//
//      while (i.next != null) {
//        System.out.print(i.value + " ---> ");
//        i = i.next;
//      }
//            
//      System.out.print(i.value);
//    }
//  }

  /**
   * This method is used to tell the size of ll.
   * @return It returns the size of Linkedlist.
   */
  public int getSize() {
    return size;
  }
  
  /**
   * This method is used to give the head node of linkedlist.
   * @return It returns the head node of linkedlist.
   */
  public MyGenericNode<T> getHead() {
    return head;
  }
}