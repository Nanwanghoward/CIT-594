import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The Computer class - It implements the player interface as a computer player not a user player.
 * It will has methods from Player interface
 * @author Hany
 *
 */
public class Computer implements Player{
	private char[][] board;
	private int[][] track;
	boolean hit;
	boolean sunk;
	private Random rand;
//	List<String> temp = new ArrayList<String>(Arrays.asList("1", "12"));
	private Location prev;
	private Location prevHit;
	private boolean flag =false;
	
	/**
	 * This construct creates a computer player instance
	 */
	public Computer(){
		board = new char[10][10];
		track = new int[10][10];
		hit = false;
		sunk = false;
		rand = new Random();
		for(char[] row: board){
    		Arrays.fill(row, '~');
    	}
		for(int[] row: track){
    		Arrays.fill(row, 0);
    	}
		
		prev=null;
		prevHit=null;
	}
	

	/**
	 * This method calls a series of method to get the location of ship we want to put.
	 * This method will place a ship on the grid.
	 * This method should guarantee correctness of location (no overlaps, no ships over the edge of the board, etc.)
	 * @param size the size of the ship to place
	 * @param retry if an earlier call to this method returned an invalid position, this method will be called again with retry set to true.
	 * @return The Location of the ship
	 */
	@Override
	public Location placeShip(int size, boolean retry) {
		//strategy
		
		Location lo = null;
		int i=0,j=0;
		boolean k =false;
		while(!check(size, i, j, k)){
			retry = true;
			k = rand.nextBoolean();
			if(k){
				j = rand.nextInt(11-size)+1;
				i = rand.nextInt(10)+1;
			}
			else{
				i = rand.nextInt(11-size)+1;
				j = rand.nextInt(10)+1;	
			}
		}
		lo = new Coordinate(i,j,k);
		placeToBoard(lo,size);
		retry = false;
		return lo;
	}
	
	/**
	 * This method will check the correctness of location we want to fire.
	 * @param size the size of the ship to place.
	 * @param x the x coordinate of ship location.
	 * @param y the x coordinate of ship location.
	 * @param isHorizontal the horizon status of ship.
	 * @return return true if the location is Ok to put the ship.
	 */
	private boolean check(int size, int x, int y, boolean isHorizontal){
		boolean flag = false;		
		//to check if it is in board
		if((x<=0 || x>10) || (y<=0 || y>10)) return false;		
		//to check is there any overlap
		for(int i = x-1, j = y-1; (i < x-1+size) && (j < y-1+size);){
			flag = flag||board[i][j] == '#';
			if(flag){
				return false;
			}
			if(isHorizontal){			
				j++;
			}
			else{
				i++;
				}
			}	
	    return true;
	}
	
	/**
	 * This method will place ship on board based on the location and size.
	 * @param lo The location the ship we want to place on board.
	 * @param size the size of ship we want to put on board.
	 */
	private void placeToBoard(Location lo, int size){
		for(int i = lo.getX()-1, j = lo.getY()-1; (i < lo.getX()-1+size) && (j < lo.getY()-1+size);){
			if(lo.isShipHorizontal()){				
				board[i][j] = '#';
				j++;
			}
			else{				
				board[i][j] = '#';
				i++;
				}
		}	
	}

	/**
	 * This method will get a new target.
	 * The Player can choose whichever location it wants to aim for next and return that location. 
	 * @return The Location of the target.
	 */
	@Override
	public Location getTarget() {
		int round;
		int x,y;
		Location lo;
		if(prevHit!=null){
			lo = findLo();
			
			//no error then return.
			if(lo!=null) {
				prev = lo;
				return lo;
			}
		}
		if(prev == null) {
			lo = new Coordinate(rand.nextInt(10) + 1, rand.nextInt(10) + 1);
			prev = lo;
			return lo;
		}
		
		if(prev.getX()+4>10){
			if(prev.getY()!=10) y=prev.getY()+1;
			else {y=1; x=4;}
			x=(prev.getX()+4)%10+1;
		}
		else{
			x=prev.getX()+4;
			y=prev.getY();
		}
		round = 0;
		while(track[x-1][y-1]!=0){
			if(round>12) x+=2;
			if(round>24) x+=(rand.nextInt(3)+1);
			if(round<50){
				if(x+4>10){
					if(y!=10) y=y+1;
					else {y=1; x=4;}
					x=(x+4)%10+1;
				}
				else{
					x=x+4;
				}
			}
			else{
				x=rand.nextInt(10)+1;
				y=rand.nextInt(10)+1;
			}
			round++;
		}
		lo = new Coordinate(x,y);
		//set this location has been fired
		prev = lo;
		return lo;
	}

	/**
	 * This method will find the location of target we want to fire based on the previous fire location we hit the ship.
	 * @return The location of target we want to fire.
	 */
	private Location findLo() {
		int x, y;
		int i;
		x=prevHit.getX();
		y=prevHit.getY();
		ArrayList<Location> list = new ArrayList<Location>();
		list.add(new Coordinate(x+1,y));
		list.add(new Coordinate(x+2,y));
		list.add(new Coordinate(x+3,y));
		list.add(new Coordinate(x-1,y));
		list.add(new Coordinate(x-2,y));
		list.add(new Coordinate(x-3,y));
		list.add(new Coordinate(x,y+1));
		list.add(new Coordinate(x,y+2));
		list.add(new Coordinate(x,y+3));
		list.add(new Coordinate(x,y-1));
		list.add(new Coordinate(x,y-2));
		list.add(new Coordinate(x,y-3));
		i=0;
        while(validate(list.get(i).getX(),list.get(i).getY())!=0&&i<12){
        	i++;
        }
        if(validate(list.get(i).getX(),list.get(i).getY())!=0) return null;
        return list.get(i);
	}


	/**
	 * This method checks status of the other player's board in the location of coordinate x and y.
	 * @param x The x coordinate of target we want to fire.
	 * @param y The y coordinate of target we want to fire.
	 * @return return -2 if the location of target out of board, -1 if already hit, 0 if not fired, 1 if hit, 2 if sunk.
	 */
	private int validate(int x, int y) {
		if((x<=0 || x>10) || (y<=0 || y>10)) return -2;
		//has been hit or sunk or fire
		return track[x-1][y-1];
	}


	/**
	 * This method will notify the Player of the result of the previous shot.
	 * @param hit true, if it was a hit; false otherwise.
	 * @param sunk true, if a ship is sunk; false otherwise.
	 */
	@Override
	public void setResult(boolean hit, boolean sunk) {
		if(hit==true&&sunk==false){
			track[prev.getX()-1][prev.getY()-1] = 1;
			if(prevHit==null) prevHit = prev;
			
		}
		if(sunk){
			track[prev.getX()-1][prev.getY()-1] = 2;
			prevHit = null;
		}
		else track[prev.getX()-1][prev.getY()-1] = -1;
	}
}
