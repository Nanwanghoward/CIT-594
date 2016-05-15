package recommender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DefaultFileRead extends FileRead{
  /**
   * This method is called by readTextFile to insert entries into RatingMatrix
   * @param str this parameter is passed by readTextFile to extract info from
   */
  public void parse(String str) {
    String[] str1 = str.split("::|;");
    if(str1.length == 1){
      str1 = str.split(",");
    }
    if(str1[0].equals("\"User-ID")) return;
    if(str1[0].equals("userId")) return;
    if(str1.length >= 3) {
      ret.insert(str1[0].replaceAll("\"|,", ""), str1[1].replaceAll("\"|,", ""), str1[2].replaceAll("\"|,", "")); 
//      if(str1[0].replaceAll("\"|,", "").equals("100")){
//        System.out.println(str1[0].replaceAll("\"|,", "")+"::"+str1[1].replaceAll("\"|,", "")+"::"+str1[2].replaceAll("\"|,", ""));
//      } 
    }
    
  }

}
