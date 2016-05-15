package recommender;

import java.util.*;

/**
 * This is a class we used to organize and acesss data.
 * @author Hany
 *
 */
public class RatingMatrix {
  Map<String, Map<String, String>> mp;
  Map<String, Map<String, String>> mp2;
  
  /**
   * This is default constructor of RatingMatrix. 
   */
  public RatingMatrix() {
    mp = new HashMap<String, Map<String, String>>();
    mp2 = new HashMap<String, Map<String, String>>();
  }
  
  /**
   * This is a getter method used to get a set of ALL user.
   * @return It returns a set of users
   */
  public Set<String> getUserSet() {
    return mp.keySet();
  }
  
  /**
   * This is a getter method used to get a set of ALL items.
   * @return It returns a set of items
   */
  public Set<String> getItemSet() {
    return mp2.keySet();
  }
  
  /**
   * This is a initial method called when creating Recommender objects, which is used to insert entries into data structure.
   * @param user This is user's name we want to insert
   * @param item This is item's name we want to insert
   * @param rating This is rating's name we want to insert
   */
  public void insert(String user, String item, String rating) {
    if(mp.containsKey(user)) {
     //may be duplicate item/rating pair
      mp.get(user).put(item, rating);
    } else {
      Map<String, String> map = new HashMap<String, String>();
      map.put(item, rating);
      mp.put(user, map);
    }
    if(mp2.containsKey(item)) {
      mp2.get(item).put(user, rating);
    } else {
      Map<String, String> map = new HashMap<String, String>();
      map.put(user, rating);
      mp2.put(item, map);
    }
  }
  
  /**
   * This is a getter method, which returns a map, key as item'sname, value as rating
   * @param user It is a user's name whom we want to calculate
   * @return It returns a map
   */
  public Map<String, String> getUserRating(String user) {
    return mp.get(user);
  }
  
  /**
   * This is a getter method, which returns a map, key as item'user, value as rating
   * @param item It is a item's name which we want to calculate
   * @return It returns a map
   */
  public Map<String, String> getItemRating(String item) {
    return mp2.get(item); 
  }
  
  /**
   * This is a method we call to get average score of user
   * @param user The string of user name, whom we want to calculate 
   * @return average score of this user.
   */
  public double getUserAverageRating(String user) {
    Map<String, String> temp = getUserRating(user);
    double total = 0;
    int count = 0;
    for(String it: temp.keySet()) {
      total += getNumericRating(temp.get(it));
      count ++;
    }
    if(count == 0) {
      return 0;
    }
    return total/count;
  }
  
//  public double getItemAverageRating(String item) {
//    double total = 0;
//    int count = 0;
//    Map<String, String> temp = getItemRating(item);
//    for (String i : temp.keySet()) {
//      count ++;
//      total += getNumericRating(temp.get(i));
//    }
//    if(count == 0) {
//      return 0;
//    }
//    return total/count;
//  }

  /**
   * This is method we want to convert string rating to double type.
   * @param rating The string we want to convert
   * @return the double value we want to get.
   */
  private double getNumericRating(String rating) {
    NumericConverter fiveStarConverter = new FiveStarConverter();
    return fiveStarConverter.getNumericRating(rating);
  }
  
  
}
