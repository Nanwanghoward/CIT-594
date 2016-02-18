
import java.util.Arrays;
import java.util.Scanner;


/**
 * The User class - It implements the player interface as a user player not a computer player.
 * It will has methods from Player interface
 * @author Hany
 *
 */
public class User implements Player{
	private boolean hit;
	private boolean sunk;
	private boolean isHorizontal;
	private boolean retry;
	private int x,y;
	private Scanner scanner;
	private char[][] board;
	
	/**
	 * This constructor creates a Human player instance
	 */
	public User(){
		hit = false;
		sunk = false;
		isHorizontal = false;
		retry = false;
		x=0;
		y=0;
		scanner = new Scanner(System.in);
		board = new char[10][10];
		for(char[] row: board){
    		Arrays.fill(row, '~');
    	}
	}

	
    	
	/**
	 * This method will place a ship on the grid.
	 * This method should guarantee correctness of location (no overlaps, no ships over the edge of the board, etc.).
	 * @param size the size of the ship to place.
	 * @param retry if an earlier call to this method returned an invalid position, this method will be called again with retry set to true.
	 * @return The Location of the ship.
	 */
	@Override
	public Location placeShip(int size, boolean retry) {
		Location lo;		
		x = 0;
		y = 0;
		while(!check(size, x, y, isHorizontal)){
			retry = true;
			System.out.println("Please input the two digits between 1 and 10 for top-left coordinates of the ship with size " + size +" seperated by comma:");
			String s1;
			String[] s2;		
			try{
				s1 = scanner.nextLine();
				s2 = s1.replaceAll("\\s+","").split(",");
				if(s2.length<2){
					x=0;
					y=0;
					continue;
				}
				x = Integer.parseInt(s2[0]);
				y = Integer.parseInt(s2[1]);
			}
			//Reset the parameter to go into next loop
			catch(Exception e){
				x = 0;
				y = 0;
				continue;
			}
			System.out.println("How to put your ship? Horizontally?(y/n):");
			try{
				s1 = scanner.nextLine();
				s1.replaceAll("\\s+","");
				if(s1.charAt(0)=='y') isHorizontal = true;
				else if(s1.charAt(0)=='n') isHorizontal = false;
				
				//Reset the parameter to go into another loop,
				else{
					System.out.println("The input is not recognized, default value will be used.");
					isHorizontal = true;
				}
			}catch(Exception e){
				System.out.println("The input is not recognized, default value will be used.");
				isHorizontal = true;
			}
	    }		
		// reset the retry to false, to notify next time this method is called successfully
		retry = false;		
		//set location's x and y fields, and size, isHorizontal
		lo = new Coordinate(x, y, isHorizontal);
		placeToBoard(lo,size);
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
		//to check if it is in board.
		if(isHorizontal){
			if((x<=0 || x>10) || (y<=0 || y-1+size>10)) return false;
		}
		else{
			if((x<=0 || x-1+size>10) || (y<=0 || y>10)) return false;
		}		
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
	 * @return The Location of the target
	 */
	@Override
	public Location getTarget() {
		Location lo;
		x = 0;
		y = 0;
		String s1;
		String[] s2;
		while((x<=0 || x>10) || (y<=0 || y>10)){
			System.out.println("Please input the two digits between 1 and 10 for coordinates of TARGET, seperated by comma:");
			try{
				s1 = scanner.nextLine();
				s2 = s1.replaceAll("\\s+","").split(",");
				if(s2.length<2){
					x=0;
					y=0;
					continue;
				}
				x = Integer.parseInt(s2[0]);
				y = Integer.parseInt(s2[1]);
			}
			//Reset the parameter to go into next loop
			catch(Exception e){
				x = 0;
				y = 0;
				continue;
			}	
	    }			
		lo = new Coordinate(x, y);
		return lo;
	}


	/**
	 * This method will notify the Player of the result of the previous shot.
	 * @param hit true, if it was a hit; false otherwise.
	 * @param sunk true, if a ship is sunk; false otherwise.
	 */
	@Override
	public void setResult(boolean hit, boolean sunk) {
		this.hit = hit;
		this.sunk = sunk;
	}

}
