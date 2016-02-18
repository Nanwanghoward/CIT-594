import java.util.Random;

/**
 * The coodinate class -- It implements the Location interface.
 * @author Hany
 *
 */
public class Coordinate implements Location{
	private int x, y;
	private boolean isHorizontal;
	private static Random rand;
	
	
	/**
	 * The constructor of the Coordinate -- It creates a coordinate instance, based on preset parameters.
	 */
	public Coordinate(){
		this(0, 0, rand.nextBoolean());	
	}
	
	/**
	 * The constructor of the Coordinate -- It creates a coordinate instance.
	 * @param x x coordinates we want to pass in Location instance.
	 * @param y y coordinates we want to pass in Location instance.
	 */
	public Coordinate(int x, int y){
		this(x, y, rand.nextBoolean());
	}
	
	/**
	 * The constructor of the Coordinate -- It creates a coordinate instance.
	 * @param x x coordinates we want to pass in Location instance.
	 * @param y y coordinates we want to pass in Location instance.
	 * @param isHorizontal Horizontal status of the location 
	 */
	public Coordinate(int x, int y, boolean isHorizontal){
		rand = new Random();
		this.x = x;
		this.y = y;
		this.isHorizontal = isHorizontal;
	}

	/**
	 * Gets the x coordinate.
	 * It should return a number between 0 and 9. 
	 * The top-left position will be 0, 0.
	 * @return the x coordinate
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate.
	 * It should return a number between 0 and 9. 
	 * The top-left position will be 0, 0.
	 * @return the y coordinate
	 */
	@Override
	public int getY() {
		
		return y;
	}

	/**
	 * This method will indicate whether the ship is horizontal or vertical.
	 * Can return an arbitrary value if the location is used to indicate a shot (and not a ship)
	 * @return true if ship is horizontal, false otherwise
	 */
	@Override
	public boolean isShipHorizontal() {	
		return isHorizontal;
	}

}
