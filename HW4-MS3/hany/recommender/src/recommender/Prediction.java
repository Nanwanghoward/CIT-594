package recommender;

/**
 * This is interface of Prediction
 * @author Hany
 *
 */
public interface Prediction {
  /**
   * This is a method used to get prediction of given user,item pairs
   * @param user the string of certain user's name
   * @param item the string of certain item's name
   * @param ratingMatrix The rating matrix as scource data
   * @return It returns score of prediction
   */
  public double getPrediction(User user, String item, RatingMatrix ratingMatrix);
}
