package recommender;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CosineSimilarity implements Similarity{
  NumericConverter converter;
  RatingMatrix ratingMatrix;
  public CosineSimilarity() {
    converter = new FiveStarConverter();
  }  

  @Override
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
    
    for (String item:common) {
      double userOne = converter.getNumericRating(ratingMatrix.getItemRating(item).get(user1));     
      double userTwo = converter.getNumericRating(ratingMatrix.getItemRating(item).get(user2));
      numerator += (userOne*userTwo);
    }
    for(String item:ratingMatrix.getUserRating(user1).keySet()) {
      double userOne = converter.getNumericRating(ratingMatrix.getUserRating(user1).get(item)); 
      denominatorPart1 += Math.pow(userOne, 2);
    }
    for(String item:ratingMatrix.getUserRating(user2).keySet()) {
      double userTwo = converter.getNumericRating(ratingMatrix.getUserRating(user2).get(item)); 
      denominatorPart2 += Math.pow(userTwo, 2);
    }
   
    if(denominatorPart1*denominatorPart2 == 0) {
      ret = 0;
    } else {
      ret = numerator/(Math.sqrt(denominatorPart1)*Math.sqrt(denominatorPart2));
    }
    return ret;
  }

}
