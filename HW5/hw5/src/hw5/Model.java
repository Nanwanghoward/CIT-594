package hw5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is Model class part of MVC
 * @author Hany
 *
 */
public class Model extends Observable{
  private Map<String, Double> menu;
  private Map<String, Dish> order;
  private File file;
  private FileWriter writer;
  private double total;
  private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
  private Calendar cal;
  private int count;
//  System.out.println(dateFormat.format(cal.getTime()));
  private NumberFormat nf = NumberFormat.getInstance();

  
  /**
   * This is constructor of Model
   * @param menu This is menu variable used to create Model
   */
  public Model(Map<String, Double> menu){
    count = 1;
    cal = Calendar.getInstance();
    String fileName = dateFormat.format(cal.getTime());
    file = new File("./"+fileName+".txt");
//    file = new File("./abc.txt");
    if(!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.out.println("file created failed");
        System.out.println("All files in current directory are listed as follows:");
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        for (File file : filesList) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }    
      }   
    } else {
      FileReader f = null;
      try {
        f = new FileReader(file);
      } catch (FileNotFoundException e) {
        System.out.println("Please manually add log file");
      }
      
      BufferedReader br = new BufferedReader(f); 
      try {
        String[] lines = br.readLine().split(" ");
        count = Integer.parseInt(lines[1]);
      } catch (Exception e) {
        System.out.print("Log file format error!");
      }
    }
    
    this.menu = menu;
    order = new HashMap<String, Dish>();
//    nameList = new ArrayList<String>();
    total = 0;
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setRoundingMode(RoundingMode.HALF_UP);
  }

  /**
   * This is method used to add item into model
   * @param item the name of dish
   */
  public void addItem(String item) {
    Dish dish;
    if(order.keySet().contains(item)) {
      dish = order.get(item);
      dish.order();
    } else {
      dish = new Dish(item, menu.get(item));
      order.put(item, dish);
    }    
    total += menu.get(item);
    setChanged();
    notifyObservers(dish); 
  }
  
  /**
   * This is method used to proceed place order, waiting for controller part to call
   */
  public void placeOrder() {
    BufferedReader bf = null;
    String whole;
    try {
      bf = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e1) {
    }
    whole = "";
    String line = "";
    try {     
      line = bf.readLine();
      count = Integer.parseInt(line.split(" ")[1]);
    } catch (Exception e) {
//      RandomAccessFile raf = null;
//      try {
//        raf = new RandomAccessFile(file, "rw");
//        raf.seek(0); // to the beginning
//        raf.write("Total: 1\n".getBytes());
//        raf.close();
//      } catch (Exception e1) {     
//        System.out.println("Please manually add log file");
//      }
      count = 0;
    }
    count ++;
    whole += ("Total: "+count+"\n");
    try {
      line = bf.readLine();
      while(line != null) {
        whole += (line+"\n");
        line = bf.readLine(); 
      }
      String path = file.getAbsolutePath();
      file.delete();
      file = new File(path);
      if(!file.exists()) {
        try {
          file.createNewFile();
        } catch(Exception e2) {          
        }
      } 
    }catch (Exception e1) {
      
    }
    try {
        writer = new FileWriter(file, true);
        PrintWriter printer = new PrintWriter(writer);
        printer.append(whole);
        printer.append(orderString(order));
        printer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    order.clear();
    total = 0;
    setChanged();
    notifyObservers("place"); 
    
  }
  
  /**
   * This is a method used to create a string, which will be written in receipt file
   * @param orderMap This is all information of order.
   * @return It returns the string written in the file
   */
  private String orderString(Map<String, Dish> orderMap) {
    String ret = "####################################receipt:NO."+count+"#################################\n";
    String time = dateFormat2.format(cal.getTime());
    ret += (time+"\n");
    ret += "item\t\t\t\t\tquan\t\t\t\tprice\n";
    for(String str : order.keySet()) {
      Dish dish = order.get(str);
      if(str.length() > 31) {
        str = str.substring(0,31);
      } else {
        while(str.length()!=31) {
          str += " ";
        }
      }
      ret += (str+ "\t" +dish.getQuan()+"\t\t\t\t"+nf.format(dish.getPrice())+"\n");
    }
    ret += ("\t\t\t\t\t\t\t\t\ttotal:"+nf.format(getTotal())+"\n");
    ret += "################################################################################\n";
//    count ++;
    return ret;
  }

  /**
   * This is method clear the current order
   */
  public void clear() {
    order.clear();
    total = 0;
    setChanged();
    notifyObservers("clear");
  }
  
  /**
   * This is method used to get total price of current order
   * @return It returns total
   */
  public double getTotal() {
    return total;
  }

  /**
   * This is method used to remove certain item
   * @param text The item name
   */
  public void removeItem(String text) {
    Dish dish;
    if(order.keySet().contains(text)) {
      dish = order.get(text);
      dish.reverseOrder();
      if(dish.getQuan() == 0) {
        order.remove(text);
      }
      total -= menu.get(text);
      setChanged();
      notifyObservers(dish);
    } else {
    }       
  }
  
  /**
   * @return It returns the order info
   */
  public Map<String, Dish> getOrder() {
    return order;
  }

  /**
   * This is method called when no items in order, but order is placed.
   */
  public void placeNoOrder() {
    setChanged();
    notifyObservers("placeNoOrder");
    
  }

  /**
   * @return It returns number of order today.
   */
  public int getCount() {
    // TODO Auto-generated method stub
    return count;
  }
  
}
