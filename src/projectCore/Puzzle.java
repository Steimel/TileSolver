package projectCore;

/*
 * public class Puzzle
 * 
 * A Puzzle is made up of a set of Pieces
 * and a large Piece which represents the board.
 */

import java.util.ArrayList;

public class Puzzle 
{
	private ArrayList<Piece> pieces;
	private Piece board;
	
	/*
	 * public Puzzle
	 * 
	 * constructs a Puzzle with the given set
	 * of Pieces and the given board
	 * 
	 * @param p - the ArrayList of Pieces
	 * @param b - the Piece representing the board
	 */
	public Puzzle(ArrayList<Piece> p, Piece b)
	{
		pieces = p;
		board = b;
	}
	
	/*
	 * public ArrayList<Piece> getPieces
	 * 
	 * standard getter for the set of pieces
	 * 
	 * @return the ArrayList of pieces
	 */
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
	
	/*
	 * public Piece getBoard
	 * 
	 * standard getter for the board
	 * 
	 * @return the Piece representing the board
	 */
	public Piece getBoard()
	{
		return board;
	}
}
