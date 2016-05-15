package recommender;

/**
 * This is an interface used for others to implement class.
 * @author Hany
 *
 */
public interface Similarity {
  /**
   * This is method we want to calculate the similarity between two user.
   * @param user1 name of one user
   * @param user2 name of another user
   * @param ratingMatrix This is ratingMatrix variables from main
   * @return It returns similarity index of two users.
   */
  public double getSimilarity(String user1, String user2, RatingMatrix ratingMatrix);

}
