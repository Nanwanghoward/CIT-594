package recommender;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is an implementation class
 * @author Hany
 *
 */
public class DefaultPreference implements Preference{
  Prediction prediction;
  public DefaultPreference() {
    prediction = new WeightedPrediction();
  }
  
  @Override
  public List<Map.Entry<String, Double>> getPreference(int num, User user, RatingMatrix ratingMatrix) {
    Map<String, Double> mapItem = new HashMap<String, Double>();
    Set<String> list = ratingMatrix.getItemSet();
    for (String item:list) {
      if(user.getRating().containsKey(item)) continue;
      //prediction.getPrediction(user, item, ratingMatrix)) may cause delay
      mapItem.put(item, prediction.getPrediction(user, item, ratingMatrix));
//      System.out.println("Item name: "+item +", prediction: "+mapItem.get(item));
    }
    
    //nlgn complexity
    List<Map.Entry<?, ?>> ret = Recommender.sortMap(mapItem);
    List<Map.Entry<String, Double>> result = new ArrayList<>();
    int count = 0;
    for(Map.Entry<?, ?> iter : ret) {
      count ++;
      if(count > num) break;
      Map.Entry<String,Double> entry = new AbstractMap.SimpleEntry<String, Double>((String)(iter.getKey()), (Double)(iter.getValue()));
      result.add(entry);
    }
    
    return result;
  }
  
  
}
