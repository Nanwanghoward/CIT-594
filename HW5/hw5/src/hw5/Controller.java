package hw5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

/**
 * @author Hany
 * ref: http://docs.oracle.com/javase/6/docs/api/java/awt/GridBagLayout.html
 * This is controller part for MVC
 */
public class Controller extends JFrame{
  private Model model;
  private View view;
  private FileRead fr;
  private Map<String, Double> menu;
  private JPanel menuPanel;
  private JPanel orderPanel;
  private JPanel cmdPanel;
  private JPanel upPanel;
  private JPanel summary;
  private JScrollPane menuPane;
  private JButton[] buttons;
  private JButton placeOrder;
  private JButton clear;
  
  /**
   * This is constructor of Controller
   */
  public Controller() {
    
    fr = new FileReadSub();
    menu = fr.openFileGUI();
    model = new Model(menu);
    view = new View(model);
    
    
    menuPanel = new JPanel();
    menuPane = new JScrollPane(menuPanel);
//    menuPanel.setPreferredSize(new Dimension(800,270));
    
    menuPane.setPreferredSize(new Dimension(800,270));
    menuPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    cmdPanel = new JPanel();
    cmdPanel.setPreferredSize(new Dimension(220,100));
    upPanel = new JPanel();
    upPanel.setPreferredSize(new Dimension(800,300));
    summary = new JPanel();
    summary.setPreferredSize(new Dimension(240,300));
    
    buttons = new JButton[menu.size()];
    int count = 0;
    for(String dish:menu.keySet()) {
      buttons[count++] = new JButton("<html>" + dish+ "<br>" +"<th>"+ menu.get(dish) +"</th>"+ "</html>");
    }
    placeOrder = new JButton("Place Order");
    clear = new JButton("Clear Order");
    
    model.addObserver(view);    
  }
  
  /**
   * This is method used to add listener to all components
   */
  private void attachListenersToComponents() {
    
    for(JButton button: buttons) {
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {   
            model.addItem(button.getText().substring(6).split("<br>")[0]);
        }
    });
    }
    
    placeOrder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
          @Override
          protected Void doInBackground() {
              //any slow process
            if(model.getOrder().isEmpty()) {
              model.placeNoOrder();
            } else {
              model.placeOrder();
            }          
            return null;
          } 
        };      
        worker.execute();        
      }
  });
    
    clear.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {   
          model.clear();
      }
  });
    
    
  }
 
  /**
   * This is method to lay out all frames and components
   */
  private void layOutComponents() {
//    setSize(800,600);
//    setLayout(new BorderLayout());
//    upPanel.setLayout(new BorderLayout());
//    menuPanel.setLayout(new GridLayout(0,3));
//    summary.setLayout(new GridLayout(2,1));
//    
//    this.add(BorderLayout.NORTH, upPanel);
//    //scrollPane always has problem.
////    this.add(BorderLayout.SOUTH, menuPane);
//    this.add(BorderLayout.SOUTH, menuPanel);
//    
//    view.panel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Your Orders"), BorderFactory.createEmptyBorder(5,5,5,5)));
//    upPanel.add(BorderLayout.WEST, view.panel1);
//    summary.add(view.panel2);
//    summary.add(cmdPanel);
//    upPanel.add(BorderLayout.EAST, summary);
////    upPanel.add(BorderLayout.NORTH, view.panel2);
//    
//    menuPanel.setBorder(
//        BorderFactory.createCompoundBorder(
//            BorderFactory.createCompoundBorder(
//                            BorderFactory.createTitledBorder("Menu"),
//                            BorderFactory.createEmptyBorder(5,5,5,5)),
//            menuPanel.getBorder()));
//    for(int i = 0; i < buttons.length; i++){
////      System.out.println("add: "+i);
//      menuPanel.add(buttons[i]);
//    }    
////    cmdPanel.add(menuPanel);
//    menuPane.add(menuPanel);
//    menuPane.setPreferredSize(new Dimension(800,300));
    
    setSize(800,600);
    setLayout(new BorderLayout());
    upPanel.setLayout(new BorderLayout());
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    menuPanel.setLayout(gridbag);
    summary.setLayout(new GridLayout(2,1));
    cmdPanel.setLayout(new GridLayout(2,1));
    
    this.add(BorderLayout.NORTH, upPanel);
    this.add(BorderLayout.SOUTH, menuPane);
    
    upPanel.add(BorderLayout.WEST, view.getPanel1());
    summary.add(view.getPanel2());
    summary.add(cmdPanel);
    upPanel.add(BorderLayout.EAST, summary);
 
    
//    menuPanel.setPreferredSize(new Dimension(800,270));
//    menuPane.setPreferredSize(new Dimension(800,270));
    for(int i = 0; i < buttons.length; i++){
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 4*(i%3);
      c.gridy = i/3;
      c.gridwidth = 3;
      
      JButton minus = new JButton("-");
      menuPanel.add(buttons[i], c);
      c.gridx = 4*(i%3)+3;
      c.gridy = i/3;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridwidth = 1;
      menuPanel.add(minus, c);
      final String context= buttons[i].getText().substring(6).split("<br>")[0];
      minus.addActionListener(new ActionListener(){
        @Override        
        public void actionPerformed(ActionEvent event) {   
            model.removeItem(context);
        }
      });
    }
 
    cmdPanel.add(placeOrder);
    cmdPanel.add(clear);
//    menuPane.add(menuPanel);
  }
  
  /**
   * This is method to do display
   */
  private void display() {
    layOutComponents();
    attachListenersToComponents();
//    pack();
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  
  
 
  
  public static void main(String[] args) {
    Controller c = new Controller();
    c.display();
  }
  
  /**
   * @return It returns menu
   */
  public Map<String, Double> getMenu() {
    return menu;
  }
}
