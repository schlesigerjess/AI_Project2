package alphabeta;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ConnectN implements State {

	int size;
	int rowSize, colSize;
	char board[][];
	char lastPlayed = 'R';
	int depth = 0;
	int track = 0;

	/** Creates empty board **/
	public ConnectN (int size) {
		this.size=size;
		rowSize=2*(size-1);
		colSize=2*size-1;
		board = new char[rowSize][colSize];
		for (int r = 0; r < (2*(size-1)); r++) // Rows
			for (int c = 0; c < (2*size-1); c++) // Column
				board[r][c] = ' ';
		lastPlayed = 'R';
	}

	/**
	 * @author shanjones CHANGED
	 */
	public ConnectN ( ConnectN T)
	{
	
		this.size = T.size;
		this.board = new char[T.rowSize][T.colSize];
		for (int r = 0; r < T.rowSize; r++)
			for (int c = 0; c < T.colSize; c++)
				this.board[r][c] = T.board[r][c];

		this.lastPlayed = T.lastPlayed;
		this.depth = T.depth;
		this.rowSize = T.rowSize;
		this.colSize = T.colSize;
	}
	
	

	/** Takes in a current board state **/
	public ConnectN(char board[][])
	{
		this.size = (board.length/2)+1;
		rowSize=2*(size-1);
		colSize=2*size-1;
		this.board = new char[rowSize][colSize];
		int numB = 0, numR = 0;

		for (int r = 0; r < rowSize; r++) // Rows
			for (int c = 0; c < colSize; c++) { // Column
				this.board[r][c] = board[r][c];
				if (board[r][c] == 'B') numB++;
				if (board[r][c] == 'R') numR++;
			}

		this.depth = numB + numR;
		if (numB == numR) this.lastPlayed = 'R';
		else if ((numB - numR) == 1) this.lastPlayed = 'B';
		else throw new RuntimeException("Error - invalid board state, B="+numB+", R="+numR);
	}

	/**
	 * Reads in the column the player wants to place a piece into
	 * @author Jessica Schlesiger
	 * @return the colum the player will play in
	 */
	public int readMove() {
		int num=-1;
		boolean retry=false;
		Scanner sca = new Scanner(System.in);
		do {
			retry=false;
			System.out.println("Enter Column for move:");
			num = sca.nextInt();
			if (num >= colSize || num < 0) {
				if (num == -1)
				{
					System.exit(1); // -1 terminates the program
				}
				System.out.println("That number is invaild. Try again.");
				retry=true;
			}
		} while(retry);
		return num;

	}

	/**
	 * Prints out a readable board
	 * @author Jessica Schlesiger
	 */
	public String toString() {
		String printBoard="";

		for (int r=0; r<rowSize+1; r++) {
			if (r != 0)
				printBoard=printBoard+(r-1)+" | "; // Sidebar numbers
			for (int c=0;c<colSize;c++) {
				if (r == 0) { 					// Top bar numbers
					if (c==0) {

						printBoard=printBoard+"  | 0 | ";
					} else
						printBoard=printBoard+(c)+" | ";
				}
				else
					printBoard = printBoard + board[r-1][c] +" | ";
			}
			printBoard= printBoard+"\n";

		}
		return printBoard;
	}




	/**
	 * Checks for a win in a diagonal
	 * @return 0 = Nothing, -1 = red wins, 1 = black wins
	 * @Author Jessica Schlesiger **/
	public boolean checkDiagonalWin(int x, int y) {


		int count=0, row, col;
		if (x > y) {
			row=x-y;
			col=0;
		} else {
			row=0;
			col=y-x;
		}

		// *** CHECKS \ DIRECTION *** //
		while (row<rowSize && col<colSize) {

			if (board[row][col] == lastPlayed) {
				count++;
			} else
				count=0;
			if (count >= size)
				return true;

			row++;
			col++;
		}
		count=0;

		if (x+y >= colSize) {
			col=colSize-1;
		}else
			col=x+y;
		if (x+y >= rowSize) {
			row=Math.abs(rowSize-(x+y));
		}
		else
			row=0;


		// *** CHECKS / DIRECTION *** //
		while(row<rowSize && col>-1) {
			if (board[row][col] == lastPlayed) {
				count++;
			} else
				count=0;
			if (count >= size)
				return true;

			row++;
			col--;
		}
		return false;
	}
	/**
	 * Finds which row is the top in the designated col
	 * @param colToCheck
	 * @return row
	 */
	public int getTopMostColPiece (int colToCheck) {
		for (int r = 0; r < rowSize; r++) {
			if (board[r][colToCheck] != ' ')
				return r;
		}
		return -1;
	}
	/** Uses the user's input to update the board.
	 * @author Jessica Schlesiger
	 */
	public void updateBoard() {
		int col = readMove();
		int row = getTopMostColPiece(col)-1;
		System.out.println("Row: "+row+"Col: "+col);
		if (lastPlayed == 'R') {
		board[row][col] = 'B';
		lastPlayed = 'B';
		} else {
			board[row][col] = 'R';
			lastPlayed = 'R';
		}
	}

	/** Checks for win/loss win most recent move
	 * @return 0 = Nothing, -1 = red wins, 1 = black wins
	 * @Author Jessica Schlesiger **/
	public boolean checkWinOrLoss(int col) {

		if (col > colSize)
			throw new RuntimeException("Error - col coordinate too large");

		int row = getTopMostColPiece(col);

		// Check if a col is a win
		if ((rowSize-row) >= 0) {
			if (checkColumnWin(row, col))
				return true;
		}

		// Check if a row is a win
		if (checkRowWin(row))
			return true;

		// Check if diagonal win
		if (checkDiagonalWin(row , col))
			return true;


		return false; // If no wins, return 0
	}

	public int getColSize()
	{
		return colSize;
	}

	public int getRowSize()
	{
		return rowSize;
	}
	
	public int getSize()
	{
		return size;
	}

	/**
	 * Checks for a win in a column
	 * @return true/false
	 * * @Author Jessica Schlesiger **/
	public boolean checkColumnWin(int row, int col) {
		int colSet=0;
		for (int r = row; r < rowSize; r++) // Rows
		{
			if (board[r][col] == lastPlayed) {
				colSet++;
			} else
				return false;
			if (colSet >= size) // if red wins
				return true;
		}
		return false;
	}

	/** Checks for wins in the rows
	 * @return 0 = Nothing, -1 = red wins, 1 = black wins
	 * @Author Jessica Schlesiger **/
	public boolean checkRowWin(int row) {
		int rowSet=0;
		for (int c = 0; c < colSize; c++) { // Column
			if (board[row][c] == lastPlayed) {
				rowSet++;
			} else
				rowSet=0;
			if (rowSet >= size)  // if red wins
				return true;

		}
		return false;
	}

	/**
	 * @author shanjones & Dr.Briggs
	 * @return
	 */
	public boolean isDraw( )
	{
		for (int r = 0; r < rowSize; r++)
		{
			for (int c = 0; c < colSize; c++)
			{
				if (board[r][c] == ' ') return false;
				if (checkWinOrLoss(c)) return false;
				if (checkWinOrLoss(c)) return false;
			}
		}
		return true;
	}


	/**
	 * @author shanjones & Dr. briggs
	 * @return
	 */
	public LinkedList<State> next( )
	{
		LinkedList<State> next = new LinkedList<State>( );
		for (int r = 0; r < rowSize; r++)
		{
			
			for (int c = 0; c < colSize; c++)
			{
				
				if (board[r][c] == ' ') 
				{
					
					ConnectN t = new ConnectN(this);
					
						t.board[r][c] = (lastPlayed == 'R') ? 'B' : 'R';
						t.lastPlayed = (lastPlayed == 'R') ? 'B' : 'R';
						t.depth++;
						next.add((State) t );
					
				}
			}
		}

		return next;
	}

	/**
	 * @author shanjones & Dr.Briggs
	 * @return
	 */
	public boolean isTerminal( )
	{
		//
		for(int i =0 ; i < colSize; i++)
		{
			if (checkWinOrLoss(i)) return true;
		}

		//!!!! depth
		if (this.depth > 9) return true;

		LinkedList<State> children = next( );
		for (State S : children) {

			ConnectN T = (ConnectN) S;

			for(int i =0 ; i < colSize; i++)
			{
				if (T.checkWinOrLoss(i) || T.isDraw())
					return true;
			}

		}

		return false;
	}

	/**
	 * @author shanjones & Dr. Briggs
	 * @return
	 */
	public int utility( )
	{
		for(int i = 0; i < colSize; i++)
		{
			if((lastPlayed == 'R') && (checkWinOrLoss(i)))
			{
				return 1000;
			}

			if((lastPlayed == 'B') && (checkWinOrLoss(i)))
			{
				return -1000;
			}

			if (isDraw()) return 0;
		}


		if (isTerminal()) {
			int sum = 0;
			LinkedList<State> children = next( );
			for (State S : children) {

				ConnectN T = (ConnectN) S;
				sum += T.evaluate();
			}

			return sum;
		}

		else {
			return evaluate();
		}
	}


	/**
	 * @author shanjones & Dr.Briggs
	 * @return
	 */
	public int evaluate( )
	{
		for(int i = 0; i < colSize; i++)
		{
			if((lastPlayed == 'R') && (checkWinOrLoss(i)))
			{
				return 1000;
			}

			if((lastPlayed == 'B') && (checkWinOrLoss(i)))
			{
				return -1000;
			}

			if (isDraw()) return 0;
		}

		int total = 0;

		for (int r = 0; r < rowSize; r++)
		{

			int numR  = 0;
			int numB = 0;
			int numSp = 0;

			for (int c = 0; c < colSize; c++)
			{
				if (board[r][c] == 'R') numR++;
				if (board[r][c] == 'B') numB++;
				if (board[r][c] == ' ') numSp++;
			}

			if ((numR > 0) && (numB == 0) && (numSp > 0)) total += (numR * size);
			if ((numB > 0) && (numR == 0) && (numSp > 0)) total -= (numB * size);
		}

		for (int c = 0; c < colSize; c++)
		{

			int numR  = 0;
			int numB = 0;
			int numSp = 0;

			for (int r = 0; r < rowSize; r++)
			{
				if (board[r][c] == 'X') numR++;
				if (board[r][c] == 'O') numB++;
				if (board[r][c] == ' ') numSp++;
			}

			if ((numR > 0) && (numB == 0)) total += (numR * size);
			if ((numB > 0) && (numR == 0)) total -= (numB * size);
		}



		return total;

	}

	@Override
	public int getDepth() {
		return this.depth;
	}

	@Override
	public char getPlayer( )
	{
		if (this.lastPlayed == 'R')
			return 'B';
		else
			return 'R';
	}

	public static void main(String args[])
	{


		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', 'R', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', 'B', 'B', ' ', ' ', ' ' },
				{ 'R', ' ', 'B', 'B', ' ', ' ', ' ' },
		};

		ConnectN T = new ConnectN(board);

		/*System.out.println(T.getColSize());
		System.out.println(T.getRowSize());
		System.out.println(T.getDepth());
		System.out.println(T.getPlayer());
		System.out.println(T.getSize());*/
		
		
		boolean use_ab = true;

		if (! use_ab) {
			Minimax M = new Minimax();

			State S = M.minimax_decision(T);
			System.out.println(S);
		}
		else {
			AlphaBeta ab = new AlphaBeta('R', 10);
			State move = ab.getMove(T, false, 9);
			System.out.println(move);
		}

	}

	@Override
	public LinkedList<State> getNext() {
		// TODO Auto-generated method stub
		return null;
	}
}


