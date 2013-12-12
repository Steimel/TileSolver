package projectCore;

import java.util.ArrayList;

/*
 * public class Piece
 * 
 * Pieces are made up of a 2-d array
 * of Squares. Pieces are fit together
 * to cover a board, which is a large Piece
 */

public class Piece implements Comparable<Piece>
{
	private Square[][] piece;
	/*
	 * public Piece
	 * 
	 * contructs a piece with the given
	 * 2-d array of Squares
	 * 
	 * @param inputPiece - the 2-d array of Squares
	 */
	public Piece(Square[][] inputPiece)
	{
		piece = inputPiece;
	}
	
	/*
	 * public int getHeight
	 * 
	 * gets the height of the piece
	 * 
	 * @return the height of the piece
	 */
	public int getHeight()
	{
		return piece.length;
	}
	
	/*
	 * public int getWidth
	 * 
	 * gets the width of the piece
	 * 
	 * @return the width of the piece
	 */
	public int getWidth()
	{
		if(piece[0] == null) return 0;
		return piece[0].length;
	}
	

	/*
	 * public Square getSquare
	 * 
	 * gets the Square from the specified location
	 * of the Piece
	 * 
	 * @param down - the vertical movement from UL corner
	 * @param right - the horizontal movement from UL corner
	 * 
	 * @return the Square at the specified position
	 * @return null if the index of down and right is out of bounds
	 */
	public Square getSquare(int down, int right)
	{
		if(down < 0 || right < 0 || down >= piece.length 
				|| right >= piece[0].length)
		{
			System.out.println("Error thrown in Piece.java: getSquare"
					+ " index out of bounds");
			return null;
		}
		return piece[down][right];
	}
	
	/*
	 * public void setSquare
	 * 
	 * sets the Square from the specified location
	 * of the Piece
	 * 
	 * @param down - the vertical movement from UL corner
	 * @param right - the horizontal movement from UL corner
	 * @param color - the color of the new square
	 */
	public void setSquare(int down, int right, int color)
	{
		if(down < 0 || right < 0 || down >= piece.length 
				|| right >= piece[0].length)
		{
			System.out.println("Error thrown in Piece.java: setSquare"
					+ " index out of bounds");
			return;
		}
	}
	
	/*
	 * public int getNumSquares
	 * 
	 * calculates the total number of non-null squares
	 * in the piece
	 * 
	 * @return the number of non-null squares
	 */

	public int getNumSquares()
	{
		int total = 0;
		for(int x = 0; x < piece.length; x++)
		{
			for(int y = 0; y < piece[0].length; y++)
			{
				if(!piece[x][y].isNull())
				{
					total++;
				}
			}
		}
		return total;
	}
	
	/*
	 * public int getNumSquares
	 * 
	 * calculates the total number of squares
	 * in the piece of the specified color
	 * 
	 * @param color - the integer representing the color
	 * 
	 * @return the number of squares of the given color
	 */
	public int getNumSquares(int color)
	{
		//if(squaresSet)
		//{
		//	return numSquares.get(color);
		//}
		int total = 0;
		for(int x = 0; x < piece.length; x++)
		{
			for(int y = 0; y < piece[0].length; y++)
			{
				if(piece[x][y].getColor() == color)
				{
					total++;
				}
			}
		}
		return total;
	}
	
	/*
	 * public int compareTo
	 * 
	 * compares one piece to another
	 * 
	 * @param p - the other piece
	 * 
	 * @return the difference between Piece sizes
	 */
	
	public int compareTo(Piece p)
	{
		return this.getValue() - p.getValue();
	}
	
	/*
	 * private int getValue
	 * 
	 * returns a value for use in compareTo
	 * which represents the Piece's size
	 * 
	 * @return the value
	 */
	private int getValue()
	{
		return getHeight() + getWidth() + getNumSquares();
	}
	
	public Piece getHAxisReflection()
	{
		Square[][] newPiece = new Square[getHeight()][getWidth()];
		for(int x = 0; x < getHeight(); x++)
		{
			for(int y = 0; y < getWidth(); y++)
			{
				newPiece[x][y] = piece[getHeight()-x-1][y];
			}
		}
		return new Piece(newPiece);
	}
	
	public Piece getVAxisReflection()
	{
		Square[][] newPiece = new Square[getHeight()][getWidth()];
		for(int x = 0; x < getHeight(); x++)
		{
			for(int y = 0; y < getWidth(); y++)
			{
				newPiece[x][y] = piece[x][getWidth()-y-1];
			}
		}
		return new Piece(newPiece);
	}
	
	public Piece get180Rotation()
	{
		Square[][] newPiece = new Square[getHeight()][getWidth()];
		for(int x = 0; x < getHeight(); x++)
		{
			for(int y = 0; y < getWidth(); y++)
			{
				newPiece[x][y] = piece[getHeight()-x-1][getWidth()-y-1];
			}
		}
		return new Piece(newPiece);
	}
	
	public Piece get90Rotation()
	{
		Square[][] newPiece = new Square[getWidth()][getHeight()];
		for(int x = 0; x < getWidth(); x++)
		{
			for(int y = 0; y < getHeight(); y++)
			{
				newPiece[x][y] = piece[getHeight()-y-1][x];
			}
		}
		return new Piece(newPiece);
	}
	
	/*
	 * public String toString
	 * 
	 * gives a String representation of the Piece
	 * 
	 * @return a String representation of the Piece
	 */
	public String toString()
	{
		String s = "";
		for(int x = 0; x < this.getHeight(); x++)
		{
			for(int y = 0; y < this.getWidth(); y++)
			{
				s += piece[x][y].toString();
			}
			s += "|";
		}
		return s;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Piece)) return false;
		Piece p = (Piece)o;
		return this.toString().equals(p.toString());
	}
}
