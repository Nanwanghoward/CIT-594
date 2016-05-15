package recommender;

public class FiveStarConverter implements NumericConverter{
  
  /**
   * Default constructor
   */
  public FiveStarConverter() {
    
  }
  
  /* (non-Javadoc)
   * @see recommender.NumericConverter#getNumericRating(java.lang.String)
   */
  public double getNumericRating (String rating) {
    double ret = 0;
//    try{
      ret = Double.parseDouble(rating);
//    } catch (Exception e) {
//      System.out.println("Warning:"+rating);
//      return 0;
//    }
    return ret;
  }
}
