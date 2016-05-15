package hw5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;


/**
 * @author Hany
 * This is a abstract class to read file
 */
public abstract class FileRead {
  protected Map<String, Double> menu;
  protected String lastDish;
  
  /**
   * Constructor of FileRead
   */
  public FileRead () {
    menu =  new HashMap<String, Double>();
    lastDish = null;
  }
  
  /**
   * openFileGui is method called by FileRead instance. It will launch a GUI to choose a file from the system
   * After that, menu Map will be updated. 
   * @return menu Map
   */
  public Map<String, Double> openFileGUI(){
      JFileChooser chooser = new JFileChooser(".");
      int response = chooser.showOpenDialog(chooser);
      if(JFileChooser.APPROVE_OPTION == response ) {
         File f = chooser.getSelectedFile();
         System.out.println(f.getName()+" is opened.");
         return readTextFile(f);
      }
      return menu;
  }
  
  /**
   * readTextFile method is called by openFileGUI method, and it will call parse() method to extract information 
   * from file line by line.
   * @param f File object passed by openFileGUI method
   * @return menu Map
   */
  public Map<String, Double> readTextFile(File f){
      if (f == null || !f.isFile() || !f.canRead())
          return null;
      try {
          BufferedReader reader = new BufferedReader(new FileReader(f));
          String line;
          while ( (line = reader.readLine())!=null){
            parse(line.trim());
          }
          reader.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      finally{
      }
      return menu;
  }
  
  /**
   * This is method used to parse the info in a line
   * @param str the string in a line
   */
  public abstract void parse(String str);
}