package recommender;

/**
 * This is a interface used to convert rating 
 * @author Hany
 *
 */
public interface NumericConverter {
  /**
   * This is a method to convert rating to double
   * @param rating input string
   * @return It returns double score.
   */
  public double getNumericRating(String rating);
}
