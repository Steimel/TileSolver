package projectCore;

/*
 * public class Square
 * 
 * A Square represents a single unit building
 * block of a Piece.
 * Squares can be null (empty space) or colored.
 */

public class Square 
{
	private int color;
	
	/*
	 * public Square
	 * 
	 * constructs a Square of the specified color
	 * 
	 * @param newColor - the color of the Square
	 */
	public Square(int newColor)
	{
		color = newColor;
	}
	
	/*
	 * public int getColor
	 * 
	 * standard getter for the color
	 * 
	 * @return the color of the Square
	 */
	public int getColor()
	{
		return color;
	}
	
	/*
	 * public void setColor
	 * 
	 * standard setter for the color
	 * 
	 * @param newColor - the Square's new color
	 */
	public void setColor(int newColor)
	{
		color = newColor;
	}
	
	/*
	 * public boolean isNull
	 * 
	 * determines whether the given Square is a null
	 * Square (which is represented by a color of zero)
	 * 
	 * @return true if the Square is null, false otherwise
	 */
	public boolean isNull()
	{
		return color == 0;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Square))
		{
			return false;
		}
		Square s = (Square)o;
		return this.color == s.color;
	}
	
	/*
	 * public String toString
	 * 
	 * gives a String representation of the Square
	 * using its color
	 * 
	 * @return a String containing the Square's color
	 */
	public String toString()
	{
		return ""+color;
	}
}
