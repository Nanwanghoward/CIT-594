package palindrome;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

public class ClassTest {
  MyStack s;
  PalindromeChecker pc;
  MyGenericLinkedList<Integer> ll;
  @Before
  public void setUp() throws Exception {
    s = new MyStack<Integer>();
    pc = new PalindromeChecker();
    ll = new  MyGenericLinkedList<Integer>();
  }

  @Test (expected = EmptyStackException.class)
  public void testPopEx() {
    s.pop();
  }
  
  @Test (expected = EmptyStackException.class)
  public void testPopEx2() {
    s.push(1);
    s.pop();
    s.pop();
  }
  
  @Test
  public void testLLAddLast() {
    ll.addLast(1);
    assertEquals((Integer)1,ll.getHead().value);
    ll.addLast(2);
    assertEquals((Integer)2,ll.getHead().next.value);
    ll.addLast(3);
    assertEquals((Integer)3,ll.getHead().next.next.value);
  }
  
  @Test
  public void testLLAddFirst() {
    ll.addFirst(6);
    assertEquals((Integer)6,ll.getHead().value);
    ll.addFirst(5);
    assertEquals((Integer)5,ll.getHead().value);
    ll.addFirst(4);
    assertEquals((Integer)4,ll.getHead().value);
  }
  
  @Test
  public void testLLSize() {
    ll.addFirst(9);
    assertEquals(1, ll.getSize());
    ll.addFirst(8);
    assertEquals(2, ll.getSize());
    ll.addFirst(7);
    assertEquals(3, ll.getSize());
  }
  
  @Test
  public void testPushBySize() {
    s.push(1);
    assertEquals(1,s.size());
    s.push(2);
    assertEquals(2,s.size());
    s.push(3);
    assertEquals(3,s.size());
  }
  
  @Test
  public void testPushByReturn() {
    
    assertEquals(1,s.push(1));
    assertEquals(2,s.push(2));
    assertEquals(3,s.push(3));
  }

  @Test
  public void testPopByReturn() {
    s.push(1);
    s.push(3);
    assertEquals(3,s.pop());
    assertEquals(1,s.pop());
  }
  
  @Test
  public void testPopBySize() {
    s.push(5);
    s.push(3);
    assertEquals(2,s.size());
    s.pop();
    assertEquals(1,s.size());
    s.pop();
    assertEquals(0,s.size());
  }
  
  @Test
  public void testSize() {
    s.push(2);
    s.push(4);
    assertEquals(2,s.size());
    s.pop();
    assertEquals(1,s.size());
  }
  
  @Test
  public void testIsEmpty() {
    s.push(2);
    s.push(4);
    s.pop();
    assertFalse(s.isEmpty());
    s.pop();
    assertTrue(s.isEmpty());  
  }
  
  @Test
  public void testIsNumChar() {
     assertTrue(pc.isChar('0'));
     assertTrue(pc.isChar('1'));
     assertTrue(pc.isChar('8'));
     assertTrue(pc.isChar('9'));
  }
  
  @Test
  public void testIsLetterChar() {
     assertTrue(pc.isChar('a'));
     assertTrue(pc.isChar('A'));
     assertTrue(pc.isChar('Z'));
     assertTrue(pc.isChar('z'));
  }
  
  @Test
  public void testIsNotChar() {
     assertFalse(pc.isChar('?'));
     assertFalse(pc.isChar('@'));
     assertFalse(pc.isChar('#'));
     assertFalse(pc.isChar('%'));
     assertFalse(pc.isChar('*'));
     assertFalse(pc.isChar('\t'));
     assertFalse(pc.isChar('\n'));    
  }
  
  @Test
  public void testSpecialIsNotChar() {
    assertFalse(pc.isChar('å'));
    assertFalse(pc.isChar('©'));
    assertFalse(pc.isChar('ø'));
    assertFalse(pc.isChar('†'));
  }
  
  @Test
  public void testCheckSpec() {
    assertTrue(pc.check(""));
    assertTrue(pc.check("<>"));
    assertFalse(pc.check("?'90"));
    assertTrue(pc.check("\\s"));
  }
  
  @Test
  public void testCheckNum() {
    assertTrue(pc.check("898"));
    assertFalse(pc.check("8 8 9"));
    assertTrue(pc.check("0. , , ,. 0"));
    assertTrue(pc.check("0,2,23,220"));
  }
  
  @Test
  public void testCheckLetter() {
    assertTrue(pc.check("aba"));
    assertFalse(pc.check("racebcar"));
    assertTrue(pc.check("A man, A plan, A canal, Panama"));
    assertTrue(pc.check("Eva, can I stab bats in a cave?"));
  }
  
  @Test
  public void testCheckMixed() {
    assertTrue(pc.check("a7b7a"));
    assertFalse(pc.check("race,,˚∆˙˚∆∫bcar"));
    assertTrue(pc.check("A man, A plan, A ©∆˙©˚∆˙˚∆  ÷≤÷≥≤÷≥  canal, Panama"));
    assertTrue(pc.check("Eva, can I stab 89,./,/.()*(*^*&%&^˙©∆ƒ∆∫∆8bats in a cave?"));
  }
}
