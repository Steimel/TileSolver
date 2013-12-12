package basicGUI;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import projectCore.Piece;
import projectCore.Position;
import projectCore.Solution;


/*
 * public class Grid extends Canvas
 * 
 * The GUI component class to handle the 
 * puzzle grid and piece rendering
 * 
 */

@SuppressWarnings("serial")
class Grid extends Canvas {
	int width, height, widthInc, heightInc, rows, columns;
	
	private Piece board;
	ArrayList<Line> lines = new ArrayList<Line>();
	
	Grid(int w, int h, Piece p) {
		setSize(width = w, height = h);
		board = p; 
		rows = p.getHeight();
		columns = p.getWidth();
		
		heightInc = height / rows;			// Divide to get the height increment
		height = heightInc * rows;				// Set the height to an even multiple
		widthInc = width / columns;		
		width = widthInc * columns;
		
	}

	/*
	 * public void paint(Graphics g)
	 * 
	 * Paint the canvas. Called when canvas is first initialized
	 * and any time "update()" or "repaint()" is called.
	 * 
	 * @param the Graphics context to be rendered
	 */
	public void paint(Graphics g) {
				
		// Clear the board to prevent strange results when 
		// displaying a smaller board after a larger board
		g.clearRect(0, 0, 600, 600);		
		
		// TODO - Add 15 pixel buffer around entire canvas so bold lines aren't cut off
		
		// Ensure that pieces will be drawn square, even if the board is not square
		if (heightInc > widthInc) {
			heightInc = widthInc;
		}else{
			widthInc = heightInc;
		}
		
		// Ensure that the side of each piece will be an exact integer (for graphics precision) 
		int drawWidth = widthInc * columns;
		int drawHeight = heightInc * rows;		
		
		// Draw the outside boarder rectangle 
		g.drawRect(0, 0, drawWidth - 1, drawHeight - 1); 
		
		// Redraw the board with all the pieces
		try{
			System.out.println("Redrawing Board");
			board.getSquare(0, 0);				// Check if our board is initialized 
			this.drawPiece(0,0,0, board);		// There is probably a better way of doing this...
		}catch(NullPointerException e){
			System.out.println("Skip board draw this round, board not yet initialized");
		}
		
		// Outline pieces based on lines in the ArrayList
		for(Line l : lines){
			System.out.println(l.toString());
			drawBoldLine(l);
		}
		
		
	}
	
	/*
	 * public void fillSquare(int r, int c, Color color, Graphics g)
	 * 
	 * Fill the given square (r, c) indexed from 1 with the
	 * given color. 
	 * 
	 * @param r - row of square indexed from one
	 * @param c - column of square indexed from one
	 * @param color - color to fill the square
	 * @param g - Graphics context to be rendered 
	 */
	public void fillSquare(int r, int c, Color color, Graphics g){
		if (r > rows || c > columns || r <= 0 || c <= 0) return;	// Only accept valid input
				
		g.setColor(color);									// Set context to new color
		g.fillRect((c - 1) * widthInc, (r - 1) * heightInc, widthInc, heightInc);   	// fillRect(x-coord, y-coord, width, height)
		
		/*
		// TODO: Complete this portion of the method
		// Add 3D effect to pieces 
		Color newColor = new Color(color.getRGB() - 600);
		g.setColor(newColor);
		g.fillRect((int)(r + (double)heightInc*(1.0/5.0)), (int)(c + (double)widthInc*(1.0/5.0)), (int)((double)widthInc*(3.0/5.0)), (int)((double)heightInc*(3.0/5.0)));
		*/
		
		g.setColor(Color.black); 							// Return to default black
	}
	
	
	
	/*
	 * public void outlineSquare(int r, int c, Graphics g)
	 * 
	 * Outline the given square (r, c) indexed from 1 
	 * 
	 * @param row of square indexed from one
	 * @param column of square indexed from one
	 * @param Graphics context to be rendered 
	 */
	public void outlineSquare(int r, int c, Graphics g){
		if (r > rows || c > columns || r <= 0 || c <= 0) return;
		
		g.setColor(Color.red);
		g.drawRect((c - 1) * widthInc, (r - 1) * heightInc, widthInc, heightInc);
		g.drawRect((c - 1) * widthInc - 1, (r - 1) * heightInc - 1, widthInc + 1, heightInc + 1);
		g.setColor(Color.black);							// Return to default black
		
	}
	
	
	/*
	 * public void outlinePiece(int startR, int startC, int rotation, Piece p, Graphics g)
	 * 
	 * Outline the given piece on the board starting at startR and startC 
	 * indexed from 0. 
	 * 
	 * @param startR - row of square indexed from one
	 * @param startC - column of square indexed from one
	 * @param rotation - rotation of the piece
	 * @param reflection - reflection of piece. 0 = none, 1 = horizontal, 2 = vertical
	 * @param p - the piece to be outlined
	 * @param g - Graphics context to be rendered 
	 */
	public void outlinePiece(int startR, int startC, int rotation, int reflection, Piece p, Graphics g){
		
		// TODO - determine where the error for this switch is coming from
		// This has to do with the change from coordinate system between matrix and graphics context
		int temp = startR;
		startR = startC;
		startC = temp;
		// -------------------------------------------------------
		
		if (startR > rows || startC > columns || startR < 0 || startC < 0) return;
		
		
		
		// Adjust the piece for the given rotation and reflection
		p = this.adjustPiece(rotation, reflection, p);
		
		//p = this.adjustPiece(7, p);
		
		System.out.println("Start outline - piece dimensions: " + p.getHeight() + ", " + p.getWidth());
		
		// Create ArrayList to store all piece outlines 
		//ArrayList<Line> lines = new ArrayList<Line>();

		for (int r = 0; r < p.getHeight(); r++){
			for(int c = 0; c < p.getWidth(); c++){
				if(!p.getSquare(r, c).isNull()){
					
					//System.out.print("Non null square at " + r + ", " + c);
					
					int x = c + startC;
					int y  = r + startR;
					
					// Get Left
					if (c - 1 >= 0){ 
						if(p.getSquare(r, c - 1).isNull()){
							// Add line (r, c, r + 1, c) to ArrayList
							Line newL = new Line(x, y, x, y + 1);
							if(!lines.contains(newL)) lines.add(newL);
							//System.out.print("\t\tLeft is null");
						}
					}else{
						Line newL = new Line(x, y, x, y + 1);
						if(!lines.contains(newL)) lines.add(newL);
						//System.out.print("\t\tLeft is boarder");
					}
							
					// Get Right
					if (c + 1 < p.getWidth()){ 
						if(p.getSquare(r, c + 1).isNull()){
							// Add line (r, c + 1, r + 1, c + 1) to ArrayList
							Line newL = new Line(x + 1, y, x + 1, y + 1);
							if(!lines.contains(newL)) lines.add(newL);
							//System.out.print("\t\tRight is null");
						}
					}else{
						Line newL = new Line(x + 1, y, x + 1, y + 1);
						if(!lines.contains(newL)) lines.add(newL);
						//System.out.print("\t\tRight is boarder");
					}
					
					
					// Get Up
					if (r - 1 >= 0){ 
						if(p.getSquare(r - 1, c).isNull()){
							// Add line (r, c, r, c + 1) to ArrayList
							Line newL = new Line(x, y, x + 1, y);
							if(!lines.contains(newL)) lines.add(newL);
							//System.out.print("\t\tUp is null");
						}
					}else{
						Line newL = new Line(x, y, x + 1, y);
						if(!lines.contains(newL)) lines.add(newL);
						//System.out.print("\t\tUp is boarder");
					}
					
					
					// Get Down
					if (r + 1 < p.getHeight()){ 
						if(p.getSquare(r + 1, c).isNull()){
							// Add line (r + 1, c, r + 1, c + 1) to ArrayList
							Line newL = new Line(x, y + 1, x + 1, y + 1);
							if(!lines.contains(newL)) lines.add(newL);
							//System.out.print("\t\tDown is null");
						}
					}else{
						Line newL = new Line(x, y + 1, x + 1, y + 1);
						if(!lines.contains(newL)) lines.add(newL);
						//System.out.print("\t\tDown is boarder");
					}	
					//System.out.println();
				}
			}
			
			
			// Outline pieces based on lines in the ArrayList
			for(Line l : lines){
				//System.out.println(l.toString());
				drawBoldLine(l);
			}
			
		}	
		
	}
	
	public void drawSolution(ArrayList<Solution> solutions){
			
		lines.clear();		
		Solution currSolution = solutions.get(0);  // This will eventually be removed
				
		int i = 0;
		for (Piece p : currSolution.getPieces()){
			System.out.println("Printing piece " + i);
			Position pos = currSolution.getPositions().get(i);
			p = this.adjustPiece(pos.getOrientation(), p);
			this.outlinePiece(pos.getY(), pos.getX(), 0, 0, p, this.getGraphics());
			i++;
		}
	}
	
	public void drawSolution(Solution currSolution){
		
		lines.clear();
		paint(this.getGraphics());
				
		int i = 0;
		for (Piece p : currSolution.getPieces()){
			System.out.println("Printing piece " + i);
			Position pos = currSolution.getPositions().get(i);
			p = this.adjustPiece(pos.getOrientation(), p);
			this.outlinePiece(pos.getY(), pos.getX(), 0, 0, p, this.getGraphics());
			i++;
		}
	}
	
	private Piece adjustPiece(int rotation, int reflection, Piece p){
			
		Piece newP = p;
		
		switch(rotation){
		case 1:
			newP = newP.get90Rotation();
			break;
		case 2:
			newP = newP.get180Rotation();
			break;
		case 3:
			newP = newP.get180Rotation();
			newP = newP.get90Rotation();
			break;
		default:
			break;
		}
		
		switch(reflection){
		case 1:
			newP = newP.getHAxisReflection();
			break;
		case 2:
			newP = newP.getVAxisReflection();
			break;
		default:
			break;
		}
		
		return newP;
		
	}
	
	private Piece adjustPiece(int orientation, Piece p){
		
		Piece newP = p;
		
		switch(orientation){
		case 1:
			newP = p.get180Rotation();
			break;
		case 2:
			newP = p.getHAxisReflection();
			break;
		case 3:
			newP = p.getVAxisReflection();
			break;
		case 4:
			newP = p.get90Rotation();
			break;
		case 5:
			newP = p.get180Rotation();
			newP = newP.get90Rotation();
			break;
		case 6:
			newP = p.get90Rotation();
			newP = newP.getHAxisReflection();
			break;
		case 7:
			newP = p.get90Rotation();
			newP = newP.getVAxisReflection();
			break;			
		default:
			break;
		}
		
		return newP;
		
	}
	
	
	public void drawBoldLine(Line l){
		Graphics g = this.getGraphics();
		
		g.setColor(Color.black);
		
		for (int i = -10; i < 5; i++){
			// Check if line is vertical or horizontal 
			if(l.getStartX() == l.getEndX()){
				// Horizontal
				g.drawLine(l.getStartX() * widthInc + i, l.getStartY() * heightInc , l.getEndX() * widthInc + i, l.getEndY() * heightInc );
			}else{
				// Vertical
				g.drawLine(l.getStartX() * widthInc , l.getStartY() * heightInc + i, l.getEndX() * widthInc , l.getEndY() * heightInc + i);
			}
		}	
		
		g.setColor(Color.black);							// Return to default black
	}
	
	
	/*
	 * public void redrawGrid(int w, int h, int r, int c, Graphics g)
	 * 
	 * Redraw the grid to fit the given specifications. This clears 
	 * all data in the grid. 
	 * 
	 * @param width of grid in pixels
	 * @param height of grid in pixels
	 * @param number of rows of grid
	 * @param number of columns of grid
	 * @param Graphics context to be rendered 
	 */
	public void redrawGrid(int w, int h, Piece p, Graphics g){
		setSize(width = w, height = h);
		board = p; 
		rows = p.getHeight();
		columns = p.getWidth();
		
		heightInc = height / rows;			// Divide to get the height increment
		height = heightInc * rows;				// Set the height to an even multiple
		widthInc = width / columns;		
		width = widthInc * columns;
		
		lines.clear();
		paint(g);
		
	}
	
	
	/*
	 * public void drawPeice(int startR, int startC, int rotation, Piece p)
	 * 
	 * Draw the piece on the grid
	 * 
	 * @param the row of the grid square to start the piece on (indexed from 1)
	 * @param the column of the grid square to start the piece on (indexed from 1)
	 * @param the rotation amount of the piece (0, 1, 2, 3) corresponds to (0, 90, 180, 270) 
	 * @param the piece to be drawn
	 */
	public void drawPiece(int startR, int startC, int rotation, Piece p){
				
		System.out.println("Drawing Piece");
		
		int xLength = p.getHeight();
		int yLength = p.getWidth();
					
		for(int x = 0; x < xLength; x++)
		{
			for(int y = 0; y < yLength; y++)
			{
				//board.setSquare(x, y, p.getSquare(x,y).getColor());  // Update the board
								
				if(!p.getSquare(x, y).isNull())		// Don't color null squares
				{
					int colorVal = p.getSquare(x, y).getColor();
					this.fillSquare(startR + x + 1, startC + y + 1, getColor(colorVal), this.getGraphics());
					this.outlineSquare(startR + x + 1, startC + y + 1, this.getGraphics());
				}
			}
		}
		
		//this.outlinePiece(startR, startC, rotation, 0,  p, this.getGraphics());
	}
	
	/*
	 * public Color getColor(int id)
	 * 
	 * Get the color to be used to fill squares based on the 
	 * index given
	 * 
	 * @param id of color to switch on
	 * @return the color that matches the given index
	 */
	private Color getColor(int id){
		switch(id){
			case 1:  return Color.blue;
			case 2:  return Color.yellow;
			case 3:  return Color.green;
			case 4:  return Color.orange;
			case 5:  return Color.black;
			case 6:  return Color.white;
			case 7:  return Color.CYAN;
			default: return Color.magenta;
		}
	}
	
}


