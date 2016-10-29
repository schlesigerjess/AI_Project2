package alphabeta;
import static org.junit.Assert.*;

import org.junit.Test;
/** This tests our ConnectN class **/
public class testConnectN {

	/**
	 * @Author Jessica Schlesiger
	 * Tests passing in an already created board
	 */
	@Test
	public void testPassCurrentBoard() {

		char board[][] = {							// Black's turn
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
				{ 'B', 'R', 'R', ' ', ' ', ' ', 'B' },
				{ 'B', 'B', 'R', 'B', ' ', 'R', 'B' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals('B', c.lastPlayed );
		char board2[][] = { 						// Red's turn
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'R', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', ' ', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', ' ', ' ', ' ', 'B' },
				{ 'R', 'B', 'R', 'B', ' ', 'R', 'B' },
		};
		c = new ConnectN(board2);
		assertEquals('R', c.lastPlayed );

		try {
	    char board3[][] = { 						// Invalid State
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'R', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', ' ', ' ', 'R', ' ' },
				{ ' ', 'B', 'R', ' ', ' ', 'B', 'B' },
				{ 'R', 'B', 'R', 'B', ' ', 'B', 'B' },
		};
		c = new ConnectN(board3);
		fail("Should have failed");
		} catch (final Exception e) {
			final String msg = "Error - invalid board state";
			assertEquals(msg, e.getMessage());

		}

	}


	/**
	 * @Author Jessica Schlesiger
	 * Creates an empty board of N size
	 */
	@Test
	public void testCreateBlankBoard() {
		for (int size=4;size<20;size++) {
		ConnectN c = new ConnectN(size);
		assertEquals(2*size-1, c.board[0].length); // Checks column
		assertEquals(2*(size-1), c.board.length); // Checks row
		}
	}

}
