package recommender;

public class BaseLinePrediction implements Prediction{
  NumericConverter converter;
  
  public BaseLinePrediction() {
    converter = new FiveStarConverter();
  }
  @Override
  public double getPrediction(User user, String item, RatingMatrix ratingMatrix) {
    double ret = 0;
    double bu = 0;
    double bi = 0;
    double avg = ratingMatrix.getAvg();
    
    bu = ratingMatrix.getUserAverageRating(user.getName()) - avg;
    int count = 0;
    for (String iterU : ratingMatrix.getItemRating(item).keySet()) {
      bi = bi + converter.getNumericRating(ratingMatrix.getUserRating(iterU).get(item)) - ratingMatrix.getUserAverageRating(iterU); 
      count ++;
    }
    bi /= count;
    ret = avg + bu + bi;
    return ret;
  }

}
