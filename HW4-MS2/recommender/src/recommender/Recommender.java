package recommender;

import java.util.*;

public class Recommender {
  private int neighborNum;
  private RatingMatrix ratingMatrix;
  private User currentUser;
  private Similarity similarity;
  private Prediction prediction;
  private Preference preference;
  
  /**
   * Default constructor
   */
  public Recommender() {
    neighborNum = 20;
  }
  
  /**
   * This is constructor takes ratingMatrix as an argument to set field variable with same name
   * @param ratingMatrix It is used to set field variable.
   */
  public Recommender(RatingMatrix ratingMatrix) {
    this();
    this.ratingMatrix = ratingMatrix;
    similarity = new PearsonSimilarity();
    prediction = new WeightedPrediction();
    preference = new DefaultPreference();
  }
  
  /**
   * This is setter method used to set up field variable currentUser using given string.
   * @param user It is used to create a User instance.
   * @return It returns 0 if all normal, returns 1 if given user name not found.
   */
  public int setCurrentUser(String user) {
    for(String usr : ratingMatrix.getUserSet()) {
      if(usr.equals(user)) {
        currentUser = new User(user, ratingMatrix.getUserRating(user));
        currentUser.setNeighborNum(neighborNum);
        calcNeighborList();
        return 0;
      }
    }
    return 1;
  }
  
  /**
   * This is getter method 
   * @return It returns currentUser.
   */
  public User getCurrentUser() {
    return currentUser;
  }
  
  /**
   * This is a setter method, which takes integer as argument to set neighborNum to that
   * @param num It is argument used to set field variable to that value.
   * @return It returns 0 if all normal, returns 1 if num out of range.
   */
  public int setNeighborNum(int num) {
    if(num >= 1) {
    neighborNum = num;
    return 0;
    } else {
      return 1;
    }
  }
  
  /**
   * This is getter method to get field variable neighborNum.
   * @return It returns neighborNum.
   */
  public int getNeighborNum() {
    return neighborNum;
  }
  
  
  /**
   * This is method used to calculate and set Neighbor of current user in a descending order of similarity
   */
  public void calcNeighborList() {
    Map<String, Double> ret = new HashMap<String, Double>();
    for(String iter: ratingMatrix.getUserSet()) {
      if(iter.equals(currentUser.getName())) continue;
      ret.put(iter, similarity.getSimilarity(currentUser.getName(), iter, ratingMatrix));
    }
    List<Map.Entry<?, ?>> temp = sortMap(ret);
    currentUser.setNeighbor(temp);
  }
  
  /**
   * This is method used to sort a map's entries in a descending order of its value
   * @param sort The map we want to sort
   * @return It returns the list of sorted map entries.
   */
  public static List<Map.Entry<?, ?>> sortMap(Map<?,?> sort) {
    ArrayList<Map.Entry<?, ?>> temp = new ArrayList<Map.Entry<?, ?>>(sort.entrySet());   
    Collections.sort(temp, Collections.reverseOrder(new Comparator<Map.Entry<?, ?>>()
    {
      public int compare( Map.Entry<?, ?> o1, Map.Entry<?, ?> o2 )
      {
        return ((Double) (o1.getValue())).compareTo((Double)(o2.getValue()));
      }
    }));
    return temp;
  }
  
//  public static List<Map.Entry<?, ?>> sortMap(Map<?,?> sort, int num) {
//    ArrayList<Map.Entry<?, ?>> temp = new ArrayList<Map.Entry<?, ?>>(sort.entrySet());   
//    Collections.sort(temp, Collections.reverseOrder(new Comparator<Map.Entry<?, ?>>()
//    {
//      public int compare( Map.Entry<?, ?> o1, Map.Entry<?, ?> o2 )
//      {
//        return ((Double) (o1.getValue())).compareTo((Double)(o2.getValue()));
//      }
//    }));
//    return temp;
//  }
  
  /**
   * This is method used to calculate the certain item's prediction
   * @param item This is argument we want to used to predict its score
   * @return It returns the predicted score of given item
   */
  public double clacPrediction(String item){
    return prediction.getPrediction(currentUser, item, ratingMatrix);
  }
  
  /**
   * This is method we used to get num highest preferred unrated item for certain user.
   * @param num This is number of items we want to recommend
   * @return It returns list of item's name we want to recommend
   */
  public List<String> getPreference(int num) {
    List<String> ret = new ArrayList<>();
    List<Map.Entry<String, Double>> result = preference.getPreference(num, currentUser, ratingMatrix); 
    for(Map.Entry<String, Double> iter : result) {
      ret.add(iter.getKey());
    }
    return ret;
  }
  
  /**
   * This is a getter method to get ratingMatrix
   * @return It returns the field variable ratingMatrix
   */
  public RatingMatrix getMatrix() {
    return ratingMatrix;
  }
  
  /**
   * This is getter method used to get the set of all items
   * @return It returns the set of all items
   */
  public Set<String> getItem() {
    return ratingMatrix.getItemSet();
  }
  

}
