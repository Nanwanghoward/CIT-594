package recommender;

import java.util.*;

public class RecommendSys {
  
  private Recommender recommender;
  private FileRead fileRead;
  
  /**
   * This is constructor of RecommendSys
   */
  public RecommendSys() {
    
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
    RecommendSys sys = new RecommendSys();
    
    while(flag) {
      int rev = 1;
      while(rev == 1) {
        int choose = 1;
        System.out.println("Please choose a recommender engine: 1.Collaborative filtering 2.Baseline Prediction");
        try{
          choose = Integer.parseInt(scan.nextLine());
          rev = 0;
        } catch (Exception e) {
          rev = 1;
        }
        if(rev == 1) continue;
        if(choose == 1) {
          rev = 1;
          while (rev == 1) {
            System.out.println("Please choose a similarity engine: 1.Pearson 2.Cosine");
            int similarity = 1;
            try{
              similarity = Integer.parseInt(scan.nextLine());
              rev = 0;
            } catch (Exception e) {
              rev = 1;
            }
            if (rev == 1) continue;
            if (similarity == 1 || similarity == 2) {
              sys.recommender.setEngine(similarity);
            } else {
              rev = 1;
              continue;
            }
          }
        } else if (choose == 2) {
          sys.recommender.setEngine(3);
        } else {
          rev = 1;
          continue;
        }      
      }
      
      if(sys.recommender.getEngine() == 1 || sys.recommender.getEngine() == 2) {
        rev = 1;
        while(rev == 1) {
  //        System.out.println("Item size is "+sys.recommender.getMatrix().getItemSet().size());
          System.out.println("Please enter the neighborNum used in similarity calculation.");
          try {
            //if neighbor num is negative will treat as one
            sys.recommender.setNeighborNum(Integer.parseInt(scan.nextLine()));
            rev = 0;
          } catch (NumberFormatException e) {
            rev = 1;
          }    
        }
      }
      rev = 1;
      while(rev == 1) {
        System.out.println("Please selcet the user number you want to serve.");
        rev = sys.recommender.setCurrentUser(scan.nextLine());
        System.out.println("Neighbor Set is calculating...");
        if(rev == 1) {
          System.out.println("User name not found!");
          continue;
        }
      }
      
      rev = 1;
      while(rev == 1) {
        System.out.println("Please enter the item number you want to predict.");
        String item = scan.nextLine();
        if(sys.recommender.getItem().contains(item)) rev = 0;
        if(rev == 1) {
          System.out.println("Item name not found!");
          continue;
        }
        
//        
 //       sys.recommender.getCurrentUser().chooseNeighbor(item, sys.recommender.getMatrix());
        double result = sys.recommender.clacPrediction(item);
        System.out.println("The prediction of this item is " + result);   
      }
      
      rev = 1;
      while(rev == 1) {
        System.out.println("Please enter number N to get N items for recommendation:");
        String num = scan.nextLine();
        List<String> rank = null;
        try {
          rank = sys.recommender.getPreference((Integer.parseInt(num)));
          rev = 0;
        } catch (NumberFormatException e) {
          rev = 1;
          continue;
        }
        
        int count = 0;
        for(String iter: rank) {
          count ++ ;
          System.out.println("No."+count+": "+iter);
        }       
      }
      
      System.out.println("Do you want to launch an other search? (Enter 'Y' to continue)");
      if(scan.nextLine().equals("Y")) {
        flag = true;
      } else {
        flag = false;
      }
    }    
  }
}
