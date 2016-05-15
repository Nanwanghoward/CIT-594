package recommender;

import java.io.Serializable;
import java.util.Map;

/**
 * This is an implementation class of prediction
 * @author Hany
 *
 */
public class WeightedPrediction implements Prediction{
  Similarity similarity;
  
  /**
   * This is a constructor of WeightedPrediction.
   */
  public WeightedPrediction() {
    similarity = new PearsonSimilarity();
  }
  

  public double getPrediction(User user, String item, RatingMatrix ratingMatrix) {
    double ret = user.getAverageRating();
    double denominator = 0;
    double numerator = 0;
    Map<User, Double> map = user.chooseNeighbor(item, ratingMatrix);
     for(Map.Entry<User, Double> iter: map.entrySet()) {
       double rating = iter.getKey().getRating(item);
//       System.out.println(rating);
       double sim = iter.getValue();
//       System.out.println(sim);
       double avg = iter.getKey().getAverageRating();
//       System.out.println(avg);
       numerator += sim*(rating - avg);
       denominator += Math.abs(sim);
     }
     if (denominator == 0) return ret;
//     if(Double.isNaN(ret + numerator/denominator)) {
//       System.out.println("numerator: "+numerator);
//       System.out.println("denominator: "+denominator);
//       System.out.println("Average: "+ret);
//     }
     return ret + numerator/denominator;
  }
}
