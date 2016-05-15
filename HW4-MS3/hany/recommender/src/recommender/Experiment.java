package recommender;



  import java.util.Scanner;

  public class Experiment {
    
    private Recommender recommender;
    private FileRead fileRead;
    
    /**
     * This is constructor of RecommendSys
     */
    public Experiment() {
      
      //using factory method for MS3 1/C/i, more flexible for supporting other file types
      fileRead = new DefaultFileRead();
      recommender = new Recommender(fileRead.openFileGUI());
    }
    
    /**
     * This is setter method for field variable of neighborNum.
     * @param num variable used for set neighborNum
     */
    public void setNeighborNum(int num) {
      recommender.setNeighborNum(num);
    }
    

    public static void main(String[] args) {
      boolean flag = true;
      Scanner scan = new Scanner(System.in);
      System.out.println("Welcome to recommender Sys!");
      System.out.println("Please selcet the source dat file.");
      Experiment ex = new Experiment();
      String user = null;
      String item = null;
      
      int rev = 1;
      while(rev == 1) {
//        System.out.println("Item size is "+sys.recommender.getMatrix().getItemSet().size());
        System.out.println("Please enter the neighborNum used in similarity calculation.");
        try {
          //if neighbor num is negative will treat as one
          ex.recommender.setNeighborNum(Integer.parseInt(scan.nextLine()));
          rev = 0;
        } catch (NumberFormatException e) {
          rev = 1;
        }    
      }
    rev = 1;
    while(rev == 1) {
      System.out.println("Please selcet the user number you want to serve.");
      user = scan.nextLine();
//      rev = ex.recommender.setCurrentUser(user);
      rev = (ex.recommender.getMatrix().mp.containsKey(user)) ? 0 : 1;
//      System.out.println("Neighbor Set is calculating...");
      if(rev == 1) {
        System.out.println("User name not found!");
        continue;
      }
    }
    
    rev = 1;
    while(rev == 1) {
      System.out.println("Please enter the item number you want to predict.");
      item = scan.nextLine();
      if(ex.recommender.getItem().contains(item)) rev = 0;
      if(rev == 1) {
        System.out.println("Item name not found!");
        continue;
      }
      
//      
      ex.recommender.setEngine(1);
      ex.recommender.setCurrentUser(user);
      double result = ex.recommender.clacPrediction(item);
      System.out.println("The Pearson prediction of this item is " + result);   
      
      ex.recommender.setEngine(2);
      ex.recommender.setCurrentUser(user);
      result = ex.recommender.clacPrediction(item);
      System.out.println("The Cosine prediction of this item is " + result); 
      
      ex.recommender.setEngine(3);
      ex.recommender.setCurrentUser(user);
      result = ex.recommender.clacPrediction(item);
      System.out.println("The Baseline prediction of this item is " + result); 
      
      
    }
      

    }
}
