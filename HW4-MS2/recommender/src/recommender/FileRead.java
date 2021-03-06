package recommender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileRead {
  private RatingMatrix ret;
  
  /**
   * Constructor of FileRead
   */
  public FileRead () {
    ret =  new RatingMatrix();
  }
  
  /**
   * openFileGui is method called by FileRead instance. It will launch a GUI to choose a file from the system
   * After that, RatingMatrix ret will be updated. 
   * @return updated or inserted RatingMatrix
   */
  public RatingMatrix openFileGUI(){
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "dat Files","dat");
      chooser.setFileFilter(filter);
      int response = chooser.showOpenDialog(chooser);
      if(JFileChooser.APPROVE_OPTION == response ) {
         File f = chooser.getSelectedFile();
         System.out.println(f.getName()+" is opened.");
         return readTextFile(f);
      }
      return ret;
  }
  
  /**
   * readTextFile method is called by openFileGUI method, and it will call parse() method to extract information 
   * from file line by line.
   * @param f File object passed by openFileGUI method
   * @return inserted RatingMatrix
   */
  public RatingMatrix readTextFile(File f){
      if (f == null || !f.isFile() || !f.canRead())
          return null;
      try {
          BufferedReader reader = new BufferedReader(new FileReader(f));
          String line;
          while ( (line = reader.readLine())!=null){
            parse(line);
          }
          reader.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      finally{
      }
      return ret;
  }
  
  /**
   * This method is called by readTextFile to insert entries into RatingMatrix
   * @param str this parameter is passed by readTextFile to extract info from
   */
  public void parse(String str) {
    String[] str1 = str.split("::");
    if(str1.length == 4) {
      ret.insert(str1[0], str1[1], str1[2]); 
    }
    
  }

}
