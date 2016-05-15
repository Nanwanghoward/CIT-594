package hw5;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
//import javax.jws.soap.SOAPBinding.Style;
import javax.swing.*;

/**
 * @author Hany
 * ref: http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextSamplerDemoProject/src/components/TextSamplerDemo.java
 * This is class View, which is part of MVC
 * 
 */
public class View implements Observer{
  private Model model;
  private JTextArea order;
  private JTextPane sum;
  private JPanel panel1;
  private JPanel panel2;
  private JScrollPane scrollOrder;
  private String[] initString;
  private String[] initStyles;
  private Map<String, Dish> orderMap; 
//  int count;
  private NumberFormat nf = NumberFormat.getInstance();
  
  /**
   * This is method used to initialize string format to show in summary board.
   */
  private void initStr() {

    String[] initString1 = { "Subtotal:\t\t","0.00\n", "Discount:\t\t","0.00\n", "Tax Total:\t\t","0.00\n", "\t0.00" };
    String[] initStyles1 = { "regular","italic", "regular", "italic","regular", "italic", "large"};
    initString = initString1;
    initStyles = initStyles1;
    
  }
  
  /**
   * This is constructor of View class
   * @param model Pass model instance into View class, visualize the model.
   */
  public View(Model model) {
//    count = 1;
    initStr();
    this.model = model;
    order = new JTextArea();
//    sum = new JTextArea();
    sum = new JTextPane();
    createTextPane(sum);
    order.setLineWrap(true);
//    sum.setLineWrap(true);
    scrollOrder = new JScrollPane(order);
    order.setSize(560,300);
    
    sum.setSize(180,200);
    
    
    orderMap = new HashMap<String, Dish>();
    //left upper
    panel1 = new JPanel();
    //right upper
    panel2 = new JPanel();
    
    getPanel1().add(scrollOrder);
    order.setEditable(false);
    getPanel2().add(sum);
    sum.setEditable(false);
    
    scrollOrder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollOrder.setPreferredSize(new Dimension(560, 300));
    
    
    sum.setPreferredSize(new Dimension(220, 200));
    StyledDocument doc = sum.getStyledDocument();
    
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    
    
    order.setText(formatStr("Dish Name:")+ "\t\t" +"Quan" + "\t" + "Subtotal\n");
//    scrollOrder.setPreferredSize(560, 300);
  }
  
  
  
  
  
  /**
   * This is method used to format the summary board, which contains multiple formats
   * @param textPane This is summary board containner
   */
  private void createTextPane(JTextPane textPane) {
//    JTextPane textPane = new JTextPane();
    StyledDocument doc = textPane.getStyledDocument();
    addStylesToDocument(doc);

    try {
        for (int i=0; i < initString.length; i++) {
            doc.insertString(doc.getLength(), initString[i],
                             doc.getStyle(initStyles[i]));
        }
    } catch (BadLocationException ble) {
        System.err.println("Couldn't insert initial text into text pane.");
    }
//    return textPane;
}

/**
 * This is method used to create format
 * @param doc The format variable.
 */
protected void addStylesToDocument(StyledDocument doc) {
    //Initialize some styles.
    Style def = StyleContext.getDefaultStyleContext().
                    getStyle(StyleContext.DEFAULT_STYLE);

    Style regular = doc.addStyle("regular", def);
    StyleConstants.setFontFamily(def, "SansSerif");

    Style s = doc.addStyle("italic", regular);
    StyleConstants.setItalic(s, true);

    s = doc.addStyle("small", regular);
    StyleConstants.setFontSize(s, 10);

    s = doc.addStyle("large", regular);
    StyleConstants.setFontSize(s, 16);
    StyleConstants.setBold(s, true);

    
//    s = doc.addStyle("icon", regular);
//    StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
//    ImageIcon pigIcon = createImageIcon("images/Pig.gif",
//                                        "a cute pig");
//    if (pigIcon != null) {
//        StyleConstants.setIcon(s, pigIcon);
//    }

//    s = doc.addStyle("button", regular);
//    StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
//    ImageIcon soundIcon = createImageIcon("images/sound.gif",
//                                          "sound icon");
//    JButton button = new JButton();
//    if (soundIcon != null) {
//        button.setIcon(soundIcon);
//    } else {
//        button.setText("BEEP");
//    }
//    button.setCursor(Cursor.getDefaultCursor());
//    button.setMargin(new Insets(0,0,0,0));
//    button.setActionCommand(buttonString);
//    button.addActionListener(this);
//    StyleConstants.setComponent(s, button);
}
  
  
  
  
  
  
  /**
   * This is method used to format the menu, to control the length of dish
   * @param str the original string
   * @return substring of original one.
   */
  private String formatStr(String str) {
    if(str.length() > 29) {
      str = str.substring(0,29);
    } else {
      while(str.length()!=29) {
        str += " ";
      }
    }
//   
    return str;
  }
  
  
  
  /**
   * This is the method used to add item into order board
   * @param dish The dish we want to add.
   */
  private void addItem(Dish dish) {
    
//    for(String str1: model.menu.keySet()){
//      System.out.println(formatStr(str1) + ":" + model.menu.get(str1));
//    }
//    
    
    orderMap.put(dish.getName(), dish);
    sum.setText(null);
    
    //need to add is true, else false.
    boolean flag = true;
    int index = 1;
    String[] str = order.getText().split("\n");
    String newStr = "";
//    newStr += 
    for(String string: str) {
      if(string.split("\t\t")[0].equals(formatStr(dish.getName()))) {
        index = dish.getQuan() - Integer.parseInt(string.split("\t\t")[1].split("\t")[0]);
        if(dish.getQuan() != 0) {
          newStr += (string.split("\t\t")[0] + "\t\t" + dish.getQuan() + "\t" + nf.format(dish.subTotal())+"\n");
        }       
        flag = false;
      } else {
        newStr += (string + "\n");
      }
    }
    if(flag) order.append(formatStr(dish.getName()) + "\t\t" + dish.getQuan() + "\t" + nf.format(dish.subTotal())+"\n");
    else order.setText(newStr);     
    //update subTotal view
    initString[1] = nf.format(Double.parseDouble(initString[1]) + index  * dish.getPrice()) + "\n";
    initString[3] = nf.format(Double.parseDouble(initString[3]) + index * (1-dish.getDiscount())*dish.getPrice()) + "\n";
    initString[5] = nf.format(Double.parseDouble(initString[5]) + index * (dish.getTax()-1)*dish.getPrice()) + "\n";
    initString[6] = "\t"+nf.format(Double.parseDouble(initString[6]) + index * dish.getPricePerItem());   
    
    createTextPane(sum);
    
  }
  
  //when model receive click and make some changes to notify, it will change as well.
  @Override
  public void update(Observable o, Object arg) {
    if(arg == null) {
      
    } else if(arg instanceof Dish){
      Dish dish = (Dish)arg;
      addItem(dish);
    } else if(arg instanceof String) {
      String response = (String)arg;
      if(response.equals("clear")) {
        sum.setText(null);
        order.setText(null);
        initStr();
        createTextPane(sum);
      }
      if(response.equals("place")) {
        sum.setText(null);
        order.setText(null);
        initStr();
        createTextPane(sum);
        JOptionPane.showMessageDialog(this.getPanel1(),
            "Your order #"+ model.getCount() + " has been placed. Please wait for your number to be called.");
//        count++;
      }
      if(response.equals("placeNoOrder")) {
        JOptionPane.showMessageDialog(this.getPanel1(), "Your order contains no items.");
      }
    }
   // field.setEditable(false);
  }

  /**
   * @return the panel1
   */
  public JPanel getPanel1() {
    return panel1;
  }


  /**
   * @return the panel2
   */
  public JPanel getPanel2() {
    return panel2;
  }


}

