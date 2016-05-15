package recommender;

import java.util.*;

/**
 * This is an implementation class
 * @author Hany
 *
 */
public class DefaultPreference implements Preference{
  Prediction prediction;
  /**
   * This is a default constructor.
   */
  public DefaultPreference() {
    prediction = new WeightedPrediction();
  }
  
  /**
   * another constructor passing prediction instance into that.
   * @param prediction This is argument passing into constructor.
   */
  public DefaultPreference(Prediction prediction) {
    this.prediction = prediction;
  }
  
  @Override
  public List<Map.Entry<String, Double>> getPreference(int num, User user, RatingMatrix ratingMatrix) {
//    Map<String, Double> mapItem = new HashMap<String, Double>();
    Set<String> list = ratingMatrix.getItemSet();
    Queue<Map.Entry<String,Double>> sorted = new PriorityQueue(num , new Comparator<Map.Entry<String, Double>>()
    {
      public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
      {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });
    for (String item:list) {
      if(user.getRating().containsKey(item)) continue;
      //prediction.getPrediction(user, item, ratingMatrix)) may cause delay
      double predict = prediction.getPrediction(user, item, ratingMatrix);
//      mapItem.put(item, predict);
//      System.out.println("item:"+item+" prediction:"+predict );
      Map.Entry<String,Double> entry = new AbstractMap.SimpleEntry<String, Double>(item, predict);
      if(sorted.size() >= num) {
        sorted.poll();
      }
      sorted.add(entry);
//      System.out.println("Item name: "+item +", prediction: "+mapItem.get(item));
    }
    
    
    //nlgn complexity
//    List<Map.Entry<?, ?>> ret = Recommender.sortMap(mapItem);
//    List<Map.Entry<String, Double>> result = new ArrayList<>();
//    int count = 0;
//    for(Map.Entry<?, ?> iter : sorted) {
//      Map.Entry<String,Double> entry = new AbstractMap.SimpleEntry<String, Double>((String)(iter.getKey()), (Double)(iter.getValue()));
//      result.add(entry);
//    }
    List<Map.Entry<String,Double>> ret = new ArrayList<>();
    while(sorted.size() > 0) {
      ret.add(sorted.poll());
    }
    Collections.reverse(ret);
    return ret;
  }
 
}
