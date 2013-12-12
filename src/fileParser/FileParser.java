package fileParser;
/*
 * public class FileParser
 * 
 * class used to open the input file,
 * parse it, create a puzzle, and give
 * that puzzle to the solver
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import projectCore.*;

public class FileParser 
{
	private final String[] zeroStrings = {" ", "."};
	private ArrayList<ArrayList<Square>> inputBoard;
	private ArrayList<String> colorList;
	private Square[][] arrayBoard;
	private boolean[][] searched;
	/*
	 * public Puzzle parseFile
	 * 
	 * opens the given file and parses it into a puzzle
	 * 
	 * @param path - the file path of the input
	 * 
	 * @return the Puzzle described by the input file
	 * @return null if the input is invalid
	 */
	public Puzzle parseFile(String path)
	{
		// Create a new board
		inputBoard = new ArrayList<ArrayList<Square>>();
		
		// Open File
		FileReader file = getFileReader(path);
		if(file == null) return null;
		
		// Read File and Create Board
		Scanner fileScanner = new Scanner(file);
		colorList = new ArrayList<String>();
		while(fileScanner.hasNextLine())
		{
			String line = fileScanner.nextLine();
			ArrayList<Square> row = createRow(line);
			inputBoard.add(row);
		}
		
		// Parse Board to Create Pieces
		arrayBoard = createArrayBoard();
		int height = arrayBoard.length;
		int width = arrayBoard[0].length;
		searched = new boolean[height][width];
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		while(!finishedSearching())
		{
			boolean found = false;
			Piece newPiece = null;
			for(int x = 0; x < height && !found; x++)
			{
				for(int y = 0; y < width && !found; y++)
				{
					if(!searched[x][y])
					{
						searched[x][y] = true;
						if(arrayBoard[x][y].getColor()!=0)
						{
							found = true;
							newPiece = getPiece(x,y);
						}
					}
				}
			}
			if(newPiece != null) pieces.add(newPiece);
		}
		
		// Make sure pieces exist
		if(pieces.size() == 0)
		{
			System.out.println("Error thrown in FileParser.java:" +
					" no pieces found.");
			return null;
		}
		
		// Find the largest piece
		Piece boardPiece = pieces.remove(getBoardIndex(pieces));
		
		// Check for validity
		if(!isValid(pieces, boardPiece))
		{
			System.out.println("Error thrown in FileParser.java: "
					+ "invalid number of squares");
			return null;
		}
		
		// return the Puzzle
		return new Puzzle(pieces, boardPiece);
	}
	
	/*
	 * private FileReader getFileReader
	 * 
	 * gets the FileReader for the given path
	 * 
	 * @param path - the file path
	 * @return the FileReader for the given path
	 * @return null if the file cannot be found
	 */
	private FileReader getFileReader(String path)
	{
		FileReader file;
		try
		{
			file = new FileReader(path);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error thrown in FileParser.java: " + 
					e.getMessage());
			return null;
		}
		return file;
	}
	
	/*
	 * private ArrayList<Square> createRow
	 * 
	 * creates an ArrayList of Squares representing
	 * a row of the inputBoard
	 * 
	 * @param line - the current row string
	 * @return the ArrayList<Square> representing the row
	 */
	private ArrayList<Square> createRow(String line)
	{
		// Initialize the row
		ArrayList<Square> row = new ArrayList<Square>();
		for(int x = 0; x < line.length(); x++)
		{
			// For each character in the string, create a Square
			String s = line.substring(x,x+1);
			Square newSquare = new Square(0);
			boolean squareSet = false;
			
			// check if it's a zero string
			for(int y = 0; y < zeroStrings.length; y++)
			{
				if(s.equals(zeroStrings[y]))
				{
					squareSet = true;
				}
			}
			// check if it's an existing color
			if(!squareSet)
			{
				for(int y = 0; y < colorList.size(); y++)
				{
					if(s.equals(colorList.get(y)))
					{
						newSquare.setColor(y+1);
						squareSet = true;
					}
				}
			}
			// else add it as a new color
			if(!squareSet)
			{
				colorList.add(s);
				newSquare.setColor(colorList.size());
			}
			// add the Square to the row
			row.add(newSquare);
		}
		return row;
	}
	
	/*
	 * private Square[][] createArrayBoard
	 * 
	 * puts the inputBoard into a 2-d array to 
	 * make it rectangular and easier to search for
	 * pieces
	 * 
	 * @return the 2-d array of Squares representing the board
	 */
	private Square[][] createArrayBoard()
	{
		int height, width;
		height = inputBoard.size();
		width = 0;
		for(int x = 0; x < height; x++)
		{
			if(width < inputBoard.get(x).size())
			{
				width = inputBoard.get(x).size();
			}
		}
		Square[][] board = new Square[height][width];
		for(int x = 0; x < height; x++)
		{
			for(int y = 0; y < width; y++)
			{
				if(y < inputBoard.get(x).size())
				{
					board[x][y] = inputBoard.get(x).get(y);
				}
				else
				{
					board[x][y] = new Square(0);
				}
			}
		}
		return board;
	}
	
	/*
	 * private boolean finishedSearching
	 * 
	 * determines whether an entire 2-d array of booleans
	 * is true; used to determine if we're done searching
	 * the board for pieces
	 * 
	 * @return whether all booleans are true
	 */
	private boolean finishedSearching()
	{
		for(int x = 0; x < searched.length; x++)
		{
			for(int y = 0; y < searched[0].length; y++)
			{
				if(!searched[x][y]) return false;
			}
		}
		return true;
	}
	
	/*
	 * private Piece getPiece
	 * 
	 * finds the piece from the inputBoard which
	 * contains the given Square
	 * 
	 * @param int x - the x coordinate of the Square
	 * @param int y - the y coordinate of the Square
	 * 
	 * @return the Piece found
	 */
	private Piece getPiece(int x, int y)
	{
		int maxX = x, maxY = y, minX = x, minY = y;
		boolean improved = true;
		while(improved)
		{
			improved = false;
			// Check for north expansion
			for(int i = minY; i <= maxY && minX > 0; i++)
			{
				if(!arrayBoard[minX-1][i].isNull())
				{
					if(!arrayBoard[minX][i].isNull())
					{
						minX--;
						improved = true;
					}
				}
			}
			// Check for east expansion
			for(int i = minX; i <= maxX && minY > 0; i++)
			{
				if(!arrayBoard[i][minY-1].isNull())
				{
					if(!arrayBoard[i][minY].isNull())
					{
						minY--;
						improved = true;
					}
				}
			}
			// Check for south expansion
			for(int i = minY; i <= maxY && maxX < arrayBoard.length-1; i++)
			{
				if(!arrayBoard[maxX+1][i].isNull())
				{
					if(!arrayBoard[maxX][i].isNull())
					{
						maxX++;
						improved = true;
					}
				}
			}
			// Check for west expansion
			for(int i = minX; i <= maxX && maxY < arrayBoard[0].length-1; i++)
			{
				if(!arrayBoard[i][maxY+1].isNull())
				{
					if(!arrayBoard[i][maxY].isNull())
					{
						maxY++;
						improved = true;
					}
				}
			}
		}
		Square[][] newPiece = new Square[maxX-minX+1][maxY-minY+1];
		for(int i = minX; i <= maxX; i++)
		{
			for(int j = minY; j <= maxY; j++)
			{
				searched[i][j] = true;
				newPiece[i-minX][j-minY] = arrayBoard[i][j];
			}
		}
		return new Piece(newPiece);
	}
	
	/*
	 * private int getBoardIndex
	 * 
	 * gets the index of the board by
	 * finding the largest piece
	 * 
	 * @param pieces - the ArrayList of Pieces
	 * 
	 * @return the index of the largest piece (the board)
	 */
	private int getBoardIndex(ArrayList<Piece> pieces)
	{
		int maxIndex = 0;
		int maxVal = pieces.get(0).getNumSquares();
		for(int x = 0; x < pieces.size(); x++)
		{
			if(pieces.get(x).getNumSquares() > maxVal)
			{
				maxIndex = x;
				maxVal = pieces.get(x).getNumSquares();
			}
		}
		return maxIndex;
	}
	
	/*
	 * private boolean isValid
	 * 
	 * checks the board and the pieces to make
	 * sure there is the correct number of
	 * each color
	 * 
	 * @param pieces - the ArrayList of non-board pieces
	 * @param board - the Piece representing the board
	 * 
	 * @return whether the puzzle is valid
	 */
	private boolean isValid(ArrayList<Piece> pieces, Piece board)
	{
		for(int x = 1; x <= colorList.size(); x++)
		{
			int total = 0;
			for(int y = 0; y < pieces.size(); y++)
			{
				total += pieces.get(y).getNumSquares(x);
			}
			if(total != board.getNumSquares(x))
			{
				return false;
			}
		}
		return true;
	}
}
