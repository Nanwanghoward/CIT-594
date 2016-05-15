package recommender;

import java.util.*;

/**
 * This is class of ietm
 * @author Hany
 *
 */
public class Item {
  private String name;
  private Map<String, String> ratingVector;
  private NumericConverter converter;
  
  /**
   * This is contructor of certain item
   * @param item It takes item's name to create a item
   */
  public Item(String item) {
    name = item;
    converter = new FiveStarConverter();
  }
  
  /**
   * This is contructor of certain item
   * @param item It takes item's name to create a item
   * @param rating It also takes a map to store info.
   */
  public Item(String item, Map<String, String> rating) {
    this(item);
    ratingVector = rating;
  }
  
  /**
   * This is getter method to get name
   * @return name
   */
  public String getName() {
    return name;
  }
  
  /**
   * This is method to get ratingVector of certain user
   * @return
   */
  public Map<String, String> getRating() {
//    for(String user: ratingVector.keySet()) {
//      ret.put(user, converter.getNumericRating(ratingVector.get(user)));
//    }
    return ratingVector;
  }
  
  /**
   * This is method used to get double score with string user's name provided
   * @param user the string of user's name
   * @return It returns double score of instance
   */
  public double getRating(String user) {
    return converter.getNumericRating(ratingVector.get(user));
  }
}
