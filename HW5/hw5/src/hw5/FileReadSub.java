package hw5;

/**
 * @author Hany
 * This is class used to read file
 */
public class FileReadSub extends FileRead{

  @Override
  public void parse(String str) {
    double d;
    try {
      d = Double.parseDouble(str);
      menu.put(lastDish, d);
      
    }
    catch (NumberFormatException ex) {
      menu.put(str, null);
      lastDish = str;
    }
  }

}
