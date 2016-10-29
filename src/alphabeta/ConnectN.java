package alphabeta;
public class ConnectN {

	int size;

	char board[][];
	char lastPlayed = 'R';
	int depth = 0;

	/** Creates empty board **/
	public ConnectN (int size) {
		this.size=size;
		board = new char[(2*(size-1))][(2*size-1)];
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				board[r][c] = ' ';

		lastPlayed = 'R';


	}


	/** Takes in a current board state **/
	public ConnectN(char board[][])
	{
		this.size = board.length;
		board = new char[(2*(size-1))][(2*size-1)];
		int numB = 0, numR = 0;

		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++) {
				this.board[r][c] = board[r][c];
				if (board[r][c] == 'B') numB++;
				if (board[r][c] == 'R') numR++;
			}

		this.depth = numB + numR;

		if (numB == numR) this.lastPlayed = 'R';
		else if ((numB - numR) == 1) this.lastPlayed = 'B';
		else throw new RuntimeException("Error - invalid board state");
	}

}
