package recommender;

import java.util.*;

/**
 * This is implementation class implements 
 * @author Hany
 *
 */
public class PearsonSimilarity implements Similarity{
  NumericConverter converter;
  RatingMatrix ratingMatrix;
  public PearsonSimilarity() {
    converter = new FiveStarConverter();
  }
  
  public double getSimilarity(String user1, String user2, RatingMatrix ratingMatrix) {
    double ret = 0;
    this.ratingMatrix = ratingMatrix;
    Set<String> common = new HashSet<String>();
    for(String item:ratingMatrix.getUserRating(user1).keySet()) {
      if(ratingMatrix.getUserRating(user2).keySet().contains(item)) {
        common.add(item);
      }
    }
    double denominatorPart1 = 0;
    double denominatorPart2 = 0;
    double numerator = 0;
    double average1 = getAverage(user1);
    double average2 = getAverage(user2);
    for(String item:common) {
      numerator += (converter.getNumericRating(ratingMatrix.getUserRating(user1).get(item))-average1)*(converter.getNumericRating(ratingMatrix.getUserRating(user2).get(item))-average2);
      denominatorPart1 += Math.pow((converter.getNumericRating(ratingMatrix.getUserRating(user1).get(item))-average1),2);
      denominatorPart2 += Math.pow((converter.getNumericRating(ratingMatrix.getUserRating(user2).get(item))-average2),2); 
//      System.out.println("numerator:"+numerator); 
//      System.out.println("denomiator1:"+denominatorPart1); 
//      System.out.println("denomiator2:"+denominatorPart2); 
    }
    if(common.size()==0 || denominatorPart1*denominatorPart2==0 ) ret = 0;
    else {
      
      ret = numerator/(Math.sqrt(denominatorPart1)*Math.sqrt(denominatorPart2));
      if(Double.isNaN(ret)) {
        System.out.println("numerator:"+numerator); 
        System.out.println("denomiator1:"+denominatorPart1); 
        System.out.println("denomiator2:"+denominatorPart2); 
      }
    }
    return ret;
  }
  
  /**
   * This is getAverage method 
   * @param user This is argument we want to use to clac
   * @return It returns double of average.
   */
  public double getAverage(String user) {
    int count = 0;
    double total = 0;
    Map<String, String> map = ratingMatrix.getUserRating(user);
    for(String item: map.keySet()) {
      count ++;
      total += converter.getNumericRating(map.get(item));
    }
    if(count == 0) return 0;
    return total/count;
  }
}
