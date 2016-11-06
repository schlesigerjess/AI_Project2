package alphabeta;
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
		else throw new RuntimeException("Error - invalid board state");
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
		//int blackWon=1, redWon=-1;
		//checkRowWin();
		int row = getTopMostColPiece(col);

		// Check if a col is a win
		if ((rowSize-row) >= 0) {
			boolean colWin = checkColumnWin(row, col);
			if (colWin)
				return colWin;
		}

		// Check if a row is a win
		boolean rowWin = checkRowWin(row , col);
		if (rowWin)
			return rowWin;

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
	public boolean checkRowWin(int row, int col) {
		int rowSet=0;
		for (int c = 0; c < colSize; c++) { // Column
			if (board[row][c] == lastPlayed) {
				rowSet++;
			} else
				return false;
			if (rowSet >= size)  // if red wins
				return true;

		}
		return false;
	}
}
