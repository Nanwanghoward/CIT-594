package recommender;

import java.util.*;

/**
 * This is a class of User
 * @author Hany
 *
 */
public class User {
  private String name;
  private Map<String, String> ratingVector;
  private List<Map.Entry<?, ?>> neighborList;
  private NumericConverter converter;
  private int neighborNum;
  
  /**
   * This is a constructor of User
   * @param name It is name of user
   * @param rating It is rating matrix of user
   */
  public User(String name, Map<String, String> rating) {
    this.name = name;
    ratingVector = rating;
    converter = new FiveStarConverter();
    neighborNum = 20;
  }
  
  /**
   * This is setter method to set neighborNum of user.
   * @param num This is argument we used to assign to neighborNum 
   */
  public void setNeighborNum(int num) {
    neighborNum = num;
  }
  
  /**
   * This is setter method to set neighborList.
   * @param list This is argument we used to assign to neighborNumList 
   */
  public void setNeighbor(List<Map.Entry<?,?>> list) {
    neighborList = list;
  }
  
  /**
   * This is getter method, which returns the certain user's neighborList.
   * @return It returns the list of sorted map entries
   */ 
  public List<Map.Entry<?,?>> getNeighbor() {
    return neighborList;
  }
  
  /**
   * This is a method we use to get certain user's average rating of all items 
   * @return It returns average as double 
   */
  public double getAverageRating() {
    int count = 0;
    double total = 0;
    for(String item: ratingVector.keySet()) {
      count ++;
      total += converter.getNumericRating(ratingVector.get(item));
    }
    if(count == 0) return 0;
    return total/count;
  }
  
  /**
   * This is a getter method to get field variable
   * @return It returns ratingVector
   */
  public Map<String, String> getRating() {
    return ratingVector;
  }
  
  /**
   * This is method used to get certain item's numeric rating by its name
   * @param item The item name want to know
   * @return It returns the double of rating of certain user.s
   */
  public double getRating(String item) {
    return converter.getNumericRating(ratingVector.get(item));
  }

  /**
   * This is a getter method to get currentUser's name 
   * @return
   */
  public String getName() {
    return name;
  }
  
  /**
   * This method is used to choose certain neighbor by input string item, ratingMatrix.
   * @param item This is item name
   * @param ratingMatrix This is ratingMatrix
   * @return It returns a map, key as a user's name, second as key.
   */
  public Map<User, Double> chooseNeighbor(String item, RatingMatrix ratingMatrix) {
    Map<User,Double> result = new HashMap<User, Double>();
    int count = 0;
    Map<String,String> itemMap = ratingMatrix.getItemRating(item);
    for (Map.Entry<?, ?> entry : neighborList)
    { 
      if(itemMap.containsKey((String)(entry.getKey()))) {
        result.put(new User((String)(entry.getKey()), ratingMatrix.getUserRating((String)(entry.getKey()))), (Double)(entry.getValue()));
        count ++;
        if(count >= neighborNum) break;
      }
    }
//    for(User iter: result.keySet()) {
//      System.out.println(iter.getName());
//      System.out.println(result.get(iter));
//    }
//    setNeighborMap(result);
    return result;
  }
  
//  public void setNeighborMap(Map<User, Double> input) {
//    neighborMap = input;
//  }
  
}
