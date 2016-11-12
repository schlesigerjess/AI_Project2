package alphabeta;

import java.util.Arrays;
import java.util.Scanner;

public class ConnectN {

	int size;
	int rowSize, colSize;
	char board[][];
	char lastPlayed = 'R';
	int depth = 0;

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
		Scanner sca=new Scanner(System.in);
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
		for (int r = 0; r < rowSize; r++)
			if (board[r][colToCheck] != ' ')
				return r;
		return -1;
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
}
