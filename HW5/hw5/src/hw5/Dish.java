package hw5;

/**
 * @author Hany
 * This is a class to store info on certain dish
 */
public class Dish {
  private String name;
  private double price;
  private double discount;
  private int quan;
  private double tax;
  
  /**
   * This is constructor of Dish
   * @param name the name of dish
   * @param price the price of dish
   */
  public Dish(String name, double price) {
    this.name = name;
    this.price = price;
    quan = 1;
    discount = 1;
    tax = 1.08;
  }
  
  /**
   * This is method used to increment order number of this dish
   */
  public void order() {
    quan ++;
  }
  
  /**
   * 
   * @return It returns the tax, for instance, 1.08 means 8% tax.
   */
  public double getTax() {
    return tax;
  }
  
  /**
   * @return It returns discount, for instance, 0.8 means 20% off.
   */
  public double getDiscount() {
    return discount;
  }
  
  /**
   * @return It returns the number of this dish have been ordered
   */
  public int getQuan() {
    return quan;
  }
  
  /**
   * @return It returns the price per dish
   */
  public double getPrice() {
    return price;
  }
  
  /**
   * @return It returns the name of dish
   */
  public String getName() {
    return name;
  }
  
  /**
   * @return It gives the total price per item, including tax and discount
   */
  public double getPricePerItem() {
    return price*discount*tax;
  }
  
  /**
   * @return It returns the cost of this dish
   */
  public double subTotal(){
    return quan*price*discount*tax;
  }

  /**
   * This is method to decrement the quan
   */
  public void reverseOrder() {
    if(quan >= 1) {
      quan --;
    }   
  }
  
  
}
