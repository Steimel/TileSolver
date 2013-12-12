package projectCore;

public class Position 
{
	private int x;
	private int y;
	/*
	 * About Orientations:
	 * 0 = Normal
	 * 1 = 180 Degree Rotation
	 * 2 = Reflection over Horizontal Axis
	 * 3 = Reflection over Vertical Axis
	 * 4 = 90 Degree Rotation
	 * 5 = 270 Degree Rotation
	 * 6 = 90 Degree Rotation + Reflection over Horizontal Axis
	 * 7 = 90 Degree Rotation + Reflection over Vertical Axis
	 */
	private int o;
	
	public Position()
	{
		this(0,0,0);
	}
	
	public Position(int xPos, int yPos, int orientation)
	{
		x = xPos;
		y = yPos;
		o = orientation;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getOrientation()
	{
		return o;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public void setOrientation(int newO)
	{
		o = newO;
	}
	
	public String toString()
	{
		return "{ " + x + " , " + y + " , " + o + " }";
	}
	
	public boolean equals(Position p)
	{
		return x==p.x && y==p.y && o==p.o;
	}
}
