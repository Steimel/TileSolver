package projectCore;

/*
 * public class Solution
 */

import java.util.ArrayList;

public class Solution 
{
	private ArrayList<Position> positions;
	private ArrayList<Piece> pieces;
	
	public Solution()
	{
		positions = new ArrayList<Position>();
		pieces = new ArrayList<Piece>();
	}
	
	/*
	 * Copy Constructor
	 */
	public Solution(Solution s)
	{
		positions = new ArrayList<Position>();
		positions.addAll(s.positions);
		pieces = new ArrayList<Piece>();
		pieces.addAll(s.pieces);
	}
	
	public void pushPart(Position pos, Piece p)
	{
		positions.add(pos);
		pieces.add(p);
	}
	
	public void popPart()
	{
		positions.remove(positions.size()-1);
		pieces.remove(pieces.size()-1);
	}
	
	public ArrayList<Position> getPositions()
	{
		return positions;
	}
	
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
}
