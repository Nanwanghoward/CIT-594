package recommender;

import java.util.List;
import java.util.Map;

/**
 * This is method we use to get N preferred list
 * @author Hany
 *
 */
public interface Preference {
  /**
   * This is method to get N preferred items.
   * @param num number of items we want to recommend
   * @param user user's name
   * @param ratingMatrix ratingMatrix variable。
   * @return It returns a list of map entries in descending order.
   */
  public List<Map.Entry<String, Double>> getPreference(int num, User user, RatingMatrix ratingMatrix);
    
}
