package solver;

/*
 * public class Solver
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;

import projectCore.*;
import basicGUI.mainFrameController;

public class Solver 
{
	private static Puzzle puzzle;
	private static Piece placementGrid;
	private static ArrayList<Solution> solutions;
	private static Solution current;
	private static long startTime;
	private static ArrayList<ArrayList<Piece>> p;
	private static Stack<ArrayList<ArrayList<Position>>> posStack;
	private static Stack<ArrayList<ArrayList<Piece>>> pieceStack;
	private static int boardSize;
	private static mainFrameController mfc;
	private static int cur2;
	private static int tot2;
	private static int cur3;
	private static int tot3;
	
	/*
	 * public ArrayList<Solution> solve
	 * 
	 * Solves a Puzzle and returns an ArrayList of
	 * all non-isomorphic solutions
	 * 
	 * @param p - the Puzzle to be solved
	 * 
	 * @return the ArrayList of non-isomorphic solutions
	 * @return null upon error
	 */
	public static ArrayList<Solution> solve(Puzzle p1, mainFrameController m, boolean pieceReflection, boolean pieceRotation)
	{
		// set up
		puzzle = p1;
		mfc = m;
		placementGrid = getPlacementGrid();
		boardSize = puzzle.getBoard().getNumSquares();
		solutions = new ArrayList<Solution>();
		// sort pieces by size
		//sortPieces();
		// start placing pieces like a madman
		startTime = System.currentTimeMillis();
		setupPieceOrientations(pieceReflection, pieceRotation);
		removeIsomorphicPieceOrientations();
		posStack = new Stack<ArrayList<ArrayList<Position>>>();
		pieceStack = new Stack<ArrayList<ArrayList<Piece>>>();
		pieceStack.add(p);
		current = new Solution();
		getSolutions();
		//findSolution(0);
		// check solutions for isomorphism
		// return the solutions
		return solutions;
	}
	
	/*
	 * private static Piece getPlacementGrid
	 * 
	 * creates the Piece representing the placement grid
	 * 
	 * @return the Piece representing the placement grid
	 */
	private static Piece getPlacementGrid()
	{
		Piece b = puzzle.getBoard();
		Square[][] grid = new Square[b.getHeight()][b.getWidth()];
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				grid[x][y] = new Square(0);
			}
		}
		return new Piece(grid);
	}
	
	/*
	 * private static void sortPieces
	 * 
	 * sorts the Pieces from largest to smallest
	 */
	/*
	private static void sortPieces()
	{
		Collections.sort(puzzle.getPieces());
		Collections.reverse(puzzle.getPieces());
	}
	*/
	
	//old
	private static void getSolutions()
	{
		if(placementGrid.getNumSquares() == boardSize)
		{
			solutions.add(current);
			if(mfc != null)
			{
				mfc.addSolution(current);
			}
			
			if(solutions.size() < 3)
			{
				System.out.println("Pieces:");
				System.out.println(current.getPieces());
				System.out.println("Positions:");
				System.out.println(current.getPositions());
			}
			
			current = new Solution(current);
			
			posStack.pop();
			
			System.out.println("Solution found!!! (" + solutions.size() + ") " + new Date().toString());
			return;
		}
		ArrayList<ArrayList<Position>> pos;
		if(posStack.isEmpty())
		{
			//System.out.println("empty finding first pos");
			pos = getGoodPositions(pieceStack.peek());
			for(int x = 0; x < pos.size(); x++)
			{
				//System.out.println(pos.get(x).size());
			}
		}
		else
		{
			//System.out.println("modifying the positions");
			//System.out.println(placementGrid);
			//System.out.println(posStack.size());
			pos = getGoodPositions(pieceStack.peek(), posStack.pop());
		}
		posStack.push(pos);
		int minIndex = 0;
		int minVal = pos.get(0).size();
		for(int x = 1; x < pos.size(); x++)
		{
			int cur = pos.get(x).size();
			if(cur < minVal)
			{
				minVal = cur;
				minIndex = x;
			}
		}
		if(minVal == 0)
		{
			posStack.pop();	
			return;
		}
		
		//System.out.println("shit is actually going to be placed");
		
		ArrayList<Piece> curPieceList = pieceStack.peek().get(minIndex);
		ArrayList<ArrayList<Piece>> newPieceList = new ArrayList<ArrayList<Piece>>();
		newPieceList.addAll(pieceStack.peek());
		newPieceList.remove(minIndex);
		pieceStack.push(newPieceList);
		for(int x = 0; x < pos.get(minIndex).size(); x++)
		{
			
			if (pieceStack.size() <= 4)
			{
				if(pieceStack.size() == 2)
				{
					cur2 = x;
					tot2 = pos.get(minIndex).size();
				}
				if(pieceStack.size() == 3)
				{
					cur3 = x;
					tot3 = pos.get(minIndex).size();
				}
				if(pieceStack.size() == 4)
				{
					int var = pos.get(minIndex).size();
					int totalPoints = var * tot3 * tot2;
					int progress = var * tot3 * cur2 + var * cur3 + x;
					double percentDone = (100.0*progress)/(1.0*totalPoints);
					System.out.println(""+percentDone + "%");
					if(mfc != null)
					{
						mfc.setPercentage(percentDone);
					}
				}
				//System.out.print("x=" + x);
				//System.out.print(" ps=" + pieceStack.size());
				//System.out.print(" time=" + new Date().toString());
				//System.out.println(" test:" + pos.get(minIndex).size());
			}
			
			ArrayList<ArrayList<Position>> newPosList = new ArrayList<ArrayList<Position>>();
			newPosList.addAll(posStack.peek());
			newPosList.remove(minIndex);
			posStack.push(newPosList);
			
			Position curPos = pos.get(minIndex).get(x);
			placePiece(curPieceList, curPos);
			current.pushPart(curPos, curPieceList.get(0));
			getSolutions();
			
			current.popPart();
			removePiece(curPieceList, curPos);
		}
		pieceStack.pop();
		posStack.pop();
	}

	private static ArrayList<Solution> findSolutions(
			ArrayList<ArrayList<Piece>> pieces,
			ArrayList<ArrayList<Position>> positions,
			Piece board,
			Piece grid)
	{
		// You can have more pieces than necessary
		// You may be solving just a piece of the total solution
		
		// place a piece
		
		// split board
		ArrayList<ArrayList<Piece>> newBoards = splitBoard(board, grid);
		// findSolutions on each section
		ArrayList<ArrayList<Solution>> sols = new ArrayList<ArrayList<Solution>>();
		for(ArrayList<Piece> s : newBoards)
		{
			sols.add(findSolutions(pieces,positions,s.get(0),s.get(1)));
		}
		// combine solutions
		
		return null;
	}
	
	private static ArrayList<ArrayList<Piece>> splitBoard(Piece board, Piece grid)
	{
		ArrayList<ArrayList<Piece>> boards = new ArrayList<ArrayList<Piece>>();
		// find all cuts
		
		// put cuts as pieces of grid and board into the 2d arraylist
		return boards;
	}
	
	private static ArrayList<ArrayList<Position>> getGoodPositions(ArrayList<ArrayList<Piece>> pieces)
	{
		ArrayList<ArrayList<Position>> goodPositions = new ArrayList<ArrayList<Position>>();
		for(int x = 0; x < pieces.size(); x++)
		{
			goodPositions.add(getPossiblePositions(x, pieces));
			//System.out.println(goodPositions.get(x).size());
		}
		return goodPositions;
	}
	
	private static ArrayList<ArrayList<Position>> getGoodPositions(ArrayList<ArrayList<Piece>> pieces, ArrayList<ArrayList<Position>> pos)
	{
		ArrayList<ArrayList<Position>> goodPositions = new ArrayList<ArrayList<Position>>();
		//System.out.println("(" + pieces.size() + " , " + pos.size() + ")");
		for(int x = 0; x < pos.size(); x++)
		{
			ArrayList<Position> work = new ArrayList<Position>();
			for(int y = 0; y < pos.get(x).size(); y++)
			{
				if(canPut(pieces.get(x), pos.get(x).get(y)))
				{
					work.add(pos.get(x).get(y));
				}
			}
			goodPositions.add(work);
		}
		return goodPositions;
	}
	
	private static ArrayList<Position> getPossiblePositions(int index, ArrayList<ArrayList<Piece>> pieces)
	{
		ArrayList<Position> pos = new ArrayList<Position>();
		for(int i = 0; i < 8; i++)
		{
			Piece cPiece = pieces.get(index).get(i);
			if(cPiece != null)
			{
				for(int x = 0; x <= placementGrid.getHeight()-cPiece.getHeight();x++)
				{
					for(int y = 0; y <= placementGrid.getWidth()-cPiece.getWidth(); y++)
					{
						if(canPut(cPiece, x, y))
						{
							pos.add(new Position(x,y,i));
						}
					}
				}
			}
		}
		return pos;
	}
	
	// old and slow
	/*
	private static void findSolution(int index)
	{
		if(index == puzzle.getPieces().size())
		{
			// you have found a solution
			// so do something!!!
			solutions.add(current);
			current = new Solution(current);
			System.out.println("Found a Solution! (" + solutions.size() + ")");
			System.out.println("It's been " + (System.currentTimeMillis() - startTime) + " ms");
			return;
		}
		for(int i = 0; i < 8; i++)
		{
			Piece cPiece = p.get(index).get(i);
			for(int x = 0; x <= placementGrid.getHeight()-cPiece.getHeight();x++)
			{
				for(int y = 0; y <= placementGrid.getWidth()-cPiece.getWidth(); y++)
				{
					if(canPut(cPiece, x, y))
					{
						//System.out.println("Shit being placed");
						placePiece(cPiece, x, y);
						//current.pushPart(x, y, i);
						findSolution(index+1);
						removePiece(cPiece, x, y);
						current.popPart();
					}
				}
			}
		}
	}
	
	*/
	
	
	private static boolean canPut(Piece p, int x, int y)
	{
		for(int i = 0; i < p.getHeight(); i++)
		{
			for(int j = 0; j < p.getWidth(); j++)
			{
				if(!(p.getSquare(i, j).equals(puzzle.getBoard().getSquare(i+x, j+y))))
				{
					if(!p.getSquare(i, j).isNull()) return false;
				}
				if(!(p.getSquare(i,j).isNull()) && !(placementGrid.getSquare(i+x, j+y).isNull()))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean canPut(ArrayList<Piece> p, Position pos)
	{
		return canPut(p.get(pos.getOrientation()), pos.getX(), pos.getY());
	}
	
	private static void placePiece(Piece p, int x, int y)
	{
		for(int i = 0; i < p.getHeight(); i++)
		{
			for(int j = 0; j < p.getWidth(); j++)
			{
				if(!(p.getSquare(i, j).isNull())) placementGrid.setSquare(i+x, j+y, p.getSquare(i, j).getColor());
			}
		}
	}
	
	private static void placePiece(ArrayList<Piece> p, Position pos)
	{
		placePiece(p.get(pos.getOrientation()), pos.getX(), pos.getY());
	}
	
	private static void removePiece(Piece p, int x, int y)
	{
		for(int i = 0; i < p.getHeight(); i++)
		{
			for(int j = 0; j < p.getWidth(); j++)
			{
				if(!(p.getSquare(i, j).isNull())) placementGrid.setSquare(i+x, j+y, 0);
			}
		}
	}
	
	private static void removePiece(ArrayList<Piece> p, Position pos)
	{
		removePiece(p.get(pos.getOrientation()), pos.getX(), pos.getY());
	}
	
	private static void setupPieceOrientations(boolean refl, boolean rot)
	{
		p = new ArrayList<ArrayList<Piece>>();
		for(int x = 0; x < puzzle.getPieces().size(); x++)
		{
			ArrayList<Piece> l = new ArrayList<Piece>();
			l.add(puzzle.getPieces().get(x));
			for(int i = 0; i < 7; i++) l.add(null);
			if(refl && rot)
			{
				l.set(1, l.get(0).get180Rotation());
				l.set(2, l.get(0).getHAxisReflection());
				l.set(3, l.get(0).getVAxisReflection());
				l.set(4, l.get(0).get90Rotation());
				l.set(5, l.get(4).get180Rotation());
				l.set(6, l.get(4).getHAxisReflection());
				l.set(7, l.get(4).getVAxisReflection());
			}
			else if(refl)
			{
				l.set(2, l.get(0).getHAxisReflection());
				l.set(3, l.get(0).getVAxisReflection());
			}
			else if(rot)
			{
				l.set(1, l.get(0).get180Rotation());
				l.set(4, l.get(0).get90Rotation());
				l.set(5, l.get(4).get180Rotation());
			}
			p.add(l);
		}
	}
	
	private static void removeIsomorphicPieceOrientations()
	{
		for(int x = 0; x < p.size(); x++)
		{
			for(int y = 0; y < p.get(x).size(); y++)
			{
				if(p.get(x).get(y) != null)
				{
					for(int z = y+1; z < p.get(x).size(); z++)
					{
						if(p.get(x).get(z) != null)
						{
							if(p.get(x).get(y).equals(p.get(x).get(z)))
							{
								p.get(x).set(z, null);
							}
						}
					}
				}
			}
		}
	}
	
	
	
}
