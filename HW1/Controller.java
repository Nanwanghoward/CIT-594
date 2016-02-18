//http://www.datagenetics.com/blog/december32011/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The controller class -- It implements the Game interface as a controller of game.
 * It has methods from Game interface.
 * @author Hany
 *
 */
public class Controller implements Game {
	private Player p1;
	private Player p2;
	private boolean turn, hit, sunk, gameOver;

	private int[] ships = { CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER };
	private int[] countP1 = { CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER };
	private int[] countP2 = { CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER };
	private String[] shipName = { "CARRIER", "BATTLESHIP", "SUBMARINE", "CRUISER", "DESTROYER" };

	private ArrayList<Location> listP1, listP2;
	private char[][] boardP1, boardP2;

	/**
	 * The constructor of Controller instance  
	 */
	public Controller() {
		p1 = new User();
		p2 = new Computer();
		boardP1 = new char[10][10];
		boardP2 = new char[10][10];	
		// p1's turn
		turn = false;
		// Initialize the board
		for (char[] row : boardP1) {
			Arrays.fill(row, '~');
		}
		for (char[] row : boardP2) {
			Arrays.fill(row, '~');
		}
		// Initial the variables
		gameOver = false;
		hit = false;
		sunk = false;
	}

	/**
	 * This method will initialize the game. At the end of this method, the board has been set up and the game can be started.
	 * @param p1 Human Player of this game.
	 * @param p2 Computer Player of this game.
	 */
	@Override
	public void initialize(Player p1, Player p2) {
		listP1 = new ArrayList<Location>();
		listP2 = new ArrayList<Location>();
		turn = false;
		for (int i = 0; i < ships.length; i++) {
			Location lo;
			lo = p1.placeShip(ships[i], false);
			placeToBoard(lo, ships[i]);
			listP1.add(lo);
		}
		// computer take turns to place ships.
		turn = true;
		for (int i = 0; i < ships.length; i++) {
			Location lo;
			lo = p2.placeShip(ships[i], false);
			placeToBoard(lo, ships[i]);
			listP2.add(lo);
		}
	}

	/**
	 * This is the start point of playing the game. The game will alternate.
	 * between the players letting them take shots at the other team.
	 * 
	 * @return Player who won.
	 */
	@Override
	public Player playGame() {
		Player temp = null;
		turn = false;
		String player;
		int[] count;
		int round = 0;
		while (!gameOver) {
			try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				e.printStackTrace();
			}
			player = turn ? "Player2" : "Player1";
			round = turn ? round : round + 1;
			System.out.println("Round " + round + ":" + player + "'s turn");
			temp = turn ? p2 : p1;
			count = turn ? countP1 : countP2;

			String s = fireTarget(temp.getTarget());
            // print the board
			printBoard();
			if (hit) {
				System.out.println(player + " Hit a ship!");
			}
			if (sunk) {
				System.out.println("The other player's " + s + " is sunk.");
			}
			temp.setResult(hit, sunk);
			if (count[0] + count[1] + count[2] + count[3] + count[4] == 0)
				gameOver = true;
			turn = !turn;
		}
		return temp;
	}

	/**
	 * This method takes the Location instance as parameter to fire that target.
	 * @param fireLo The location we want to fire.
	 * @return the string of shipname got sunk.
	 */
	private String fireTarget(Location fireLo) {
		hit = false;
		sunk = false;
		int row = ((Coordinate) fireLo).getX();
		int column = ((Coordinate) fireLo).getY();
		ArrayList<Location> temp;
		char[][] board;
		int[] count;
		temp = turn ? listP1 : listP2;
		board = turn ? boardP1 : boardP2;
		count = turn ? countP1 : countP2;

		for (int k = 0; k < temp.size(); k++) {
			if (board[row - 1][column - 1] == 'X') {
				hit = false;
				sunk = false;
				return null;
			}

			if (temp.get(k).isShipHorizontal()) {
				// to determine is in the same row and in range of ship size
				if (row == temp.get(k).getX() && column >= temp.get(k).getY()
						&& column < temp.get(k).getY() + ships[k]) {
					board[row - 1][column - 1] = 'X';
					hit = true;// assume hit a location twice, see this case as a hit
					count[k]--;
					if (count[k] == 0) {
						sunk = true;
						return shipName[k];
					} else
						return null;
				}
			}
			// ship is not horizontal
			else {
				if (column == temp.get(k).getY() && row >= temp.get(k).getX() && row < temp.get(k).getX() + ships[k]) {
					board[row - 1][column - 1] = 'X';
					hit = true;// assume hit a location twice, see this case as
					// a hit
					count[k]--;
					if (count[k] == 0) {
						sunk = true;
						return shipName[k];
					} else
						return null;
				}
			}

		}
		return null;
	}

	/**
	 * This method will print the two boards, including the user's board and computer's board.
	 */
	private void printBoard() {
		System.out.println("User's Board:");
		for (char[] row : boardP1) {
			for (char ch : row) {
				System.out.print(ch + " ");
			}
			System.out.print('\n');
		}
		System.out.println("Computer's Board:");
		for (char[] row : boardP2) {
			for (char ch : row) {
				if (ch == '#')
					System.out.print("~ ");
				else
					System.out.print(ch + " ");
			}
			System.out.print('\n');
		}
	
	}


	/**
	 * This method will place ship on board based on the location and size.
	 * @param lo The location the ship we want to place on board.
	 * @param size the size of ship we want to put on board.
	 */
	private void placeToBoard(Location lo, int size) {
		for (int i = lo.getX() - 1, j = lo.getY() - 1; (i < lo.getX() - 1 + size) && (j < lo.getY() - 1 + size);) {
			if (lo.isShipHorizontal()) {
				if (turn)
					boardP2[i][j] = '#';
				else
					boardP1[i][j] = '#';
				j++;
			} else {
				if (turn)
					boardP2[i][j] = '#';
				else
					boardP1[i][j] = '#';
				i++;
			}
		}
	}

	public static void main(String[] args) {
		String winner;
		Player p;
		Controller c = new Controller();
		c.initialize(c.p1, c.p2);
		c.printBoard();
		p = c.playGame();
		winner = (p == c.p1 ? "User" : "Computer");
		System.out.println(winner + " won!");
	}

}
