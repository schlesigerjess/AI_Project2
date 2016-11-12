package alphabeta;
import static org.junit.Assert.*;

import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;
/** This tests our ConnectN class **/
public class testConnectN {

	/**
	 * Tests toString
	 * @author Jessica Schlesiger
	 */
	@Test
	public void testToString() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', 'R', 'R', ' ', ' ', ' ' },
				{ ' ', 'R', 'B', 'B', ' ', ' ', ' ' },
				{ 'R', 'B', 'B', 'B', ' ', ' ', ' ' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | \n0 |   |   |   |   |   |   |   | \n1 |   |   |   |   |   |   |   "
				+ "| \n2 |   |   |   | R |   |   |   | \n3 |   |   | R | R |   |   |   | \n4 |   | R | B | B |   |   |  "
				+ " | \n5 | R | B | B | B |   |   |   | \n", c.toString());
	}

	/** Tests if it can find  the top most piece
	 * @author Jessica Schlesiger **/
	@Test
	public void testGeTopMostColPiece() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', 'R', 'R', ' ', ' ', ' ' },
				{ ' ', 'R', 'B', 'B', ' ', ' ', ' ' },
				{ 'R', 'B', 'B', 'B', ' ', ' ', ' ' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals(2, c.getTopMostColPiece(3));
	}

	/**
	 * Tests for wins
	 * @Author Jessica Schlesiger
	 */
	@Test
	public void testWinDetector() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'R', 'B' },
				{ ' ', 'R', ' ', 'R', 'R', 'B', 'B' },
				{ ' ', 'B', 'B', 'R', 'B', 'R', 'R' },
				{ ' ', 'B', 'R', 'R', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkWinOrLoss(3)); // red win
		char board2[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'R', ' ' },
				{ ' ', 'R', 'R', 'R', 'R', 'B', 'B' },
				{ ' ', 'B', 'B', 'B', 'B', 'R', 'R' },
				{ ' ', 'B', 'R', 'R', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		c = new ConnectN(board2);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkWinOrLoss(2)); // red win
		char board3[][] = {
				{ ' ', ' ', ' ', ' ', ' ', 'R', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'B', 'B' },
				{ 'R', 'R', 'R', 'R', 'R', 'B', 'B' },
				{ 'R', 'B', 'B', 'B', 'B', 'R', 'R' },
				{ 'R', 'B', 'R', 'B', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		c = new ConnectN(board3);
		assertEquals('B', c.lastPlayed);
		assertEquals(true, c.checkWinOrLoss(6)); // black win
		char board4[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'R', 'B' },
				{ ' ', 'R', 'R', 'R', 'R', 'B', 'B' },
				{ ' ', 'B', 'B', 'B', 'B', 'R', 'R' },
				{ ' ', 'B', 'R', 'B', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		c = new ConnectN(board3);
		assertEquals('B', c.lastPlayed);
		assertEquals(false, c.checkWinOrLoss(0)); // no  win

	}

	/**
	 * Tests for complicated wins in the Diagonals
	 * @Author Jessica Schlesiger
	 */
	@Test
	public void testComplicatedDiaWin() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'R' },
				{ ' ', ' ', ' ', ' ', ' ', 'R', 'B' },
				{ ' ', 'R', ' ', ' ', 'R', 'B', 'B' },
				{ ' ', 'B', 'B', 'R', 'B', 'R', 'R' },
				{ ' ', 'B', 'R', 'R', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(0, 6)); // red win
		char board2[][] = {
				{ ' ', ' ', ' ', 'B', ' ', ' ', 'R' },
				{ ' ', ' ', ' ', 'R', 'B', 'R', 'B' },
				{ ' ', 'R', 'R', 'R', 'R', 'B', 'B' },
				{ ' ', 'B', 'B', 'R', 'B', 'R', 'B' },
				{ ' ', 'B', 'R', 'R', 'R', 'B', 'B' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', 'B' },
		};
		c = new ConnectN(board2);
		assertEquals('B', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(1, 4)); // Black win
	}
	/**
	 * Tests for wins in the Diagonals
	 * @Author Jessica Schlesiger
	 */
	@Test
	public void testDiaWin() {
		char board3[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'R', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', 'B', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', 'R', ' ', ' ', ' ' },
				{ 'B', 'B', 'B', 'R', 'R', ' ', ' ' },
		};
		ConnectN c = new ConnectN(board3);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(2, 1)); // red win
		char board4[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'R', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', 'R', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', 'B', 'R', ' ', ' ', ' ', ' ' },
				{ 'B', 'B', 'B', 'R', 'R', 'R', ' ' },
		};
		c = new ConnectN(board4);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(2, 0)); // red win
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', 'R', 'R', ' ', ' ', ' ' },
				{ ' ', 'R', 'B', 'B', ' ', ' ', ' ' },
				{ 'R', 'B', 'B', 'B', ' ', ' ', ' ' },
		};
		c = new ConnectN(board);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(5, 0)); // Red win
		char board2[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'B', ' ', ' ', ' ' },
				{ ' ', ' ', 'B', 'B', ' ', ' ', ' ' },
				{ ' ', 'B', 'R', 'R', ' ', ' ', ' ' },
				{ 'B', 'R', 'R', 'R', 'B', ' ', ' ' },
		};
		c = new ConnectN(board2);
		assertEquals('B', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(2, 3)); // Black win



		char board5[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'B' },
				{ ' ', ' ', ' ', 'B', 'R', 'B', 'B' },
				{ ' ', ' ', ' ', 'B', 'B', 'R', 'R' },
				{ ' ', ' ', ' ', 'B', 'R', 'B', 'R' },
				{ ' ', ' ', ' ', 'R', 'R', 'B', 'R' },
		};
		c = new ConnectN(board5);
		assertEquals('B', c.lastPlayed);
		assertEquals(true, c.checkDiagonalWin(1, 6)); // Black win



	}



	/**
	 * Tests for wins in the Column
	 * @Author Jessica Schlesiger
	 */
	@Test
	public void testColWin() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ 'B', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ 'B', 'B', ' ', 'R', 'B', ' ', ' ' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals('R', c.lastPlayed);
		assertEquals(true, c.checkColumnWin(2, 3)); // Red win
		char board2[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ 'B', ' ', ' ', 'R', ' ', ' ', ' ' },
				{ 'B', ' ', ' ', 'R', ' ', ' ', ' ' },
		};
		c = new ConnectN(board2);
		c.lastPlayed='B';
		assertEquals(true, c.checkColumnWin(2, 0)); // Black win

	}

	/**
	 * Tests for wins in the rows
	 * @Author Jessica Schlesiger
	 */
	@Test
	public void testRowWin() {
		char board[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', 'B', ' ', ' ', ' ', ' ', ' ' },
				{ 'R', 'R', 'R', 'R', 'B', 'B', ' ' },
		};
		ConnectN c = new ConnectN(board);
		assertEquals(true, c.checkRowWin(5)); // Red win
		char board2[][] = {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ 'B', 'B', 'B', 'B', 'B', ' ', ' ' },
				{ 'R', 'R', 'R', 'B', 'R', 'R', ' ' },
		};
		c = new ConnectN(board2);
		c.lastPlayed='B';
		assertEquals(true, c.checkRowWin(4)); // Black win
	}

	/**
	 * Tests passing in an already created board
	 * @Author Jessica Schlesiger
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
			final String msg = "Error - invalid board state, B=8, R=6";
			assertEquals(msg, e.getMessage());

		}

	}

	/**
	 * Creates an empty board of N size
	 * @Author Jessica Schlesiger
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
