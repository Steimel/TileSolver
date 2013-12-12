package basicGUI;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import projectCore.Piece;
import projectCore.Puzzle;
import projectCore.Solution;
import solver.Solver;
import fileParser.FileParser;

/*
 * public class mainFrameController
 * 
 * The controller class to handle all 
 * non-UI related computation that will eventually
 * require the GUI display
 */

public class mainFrameController {
	private Puzzle puzzle;
	private JProgressBar bar;
	private ArrayList<Solution> solutions = new ArrayList<Solution>();
	private mainFrame gui;
	private Thread solveThread;
	
	public mainFrameController(){
		
	}
	
	public Puzzle getPuzzle(){
		return puzzle;
	}
	
	public void setMainFrame(mainFrame frame){
		gui = frame;
	}
	
	public ArrayList<Solution> solve(final Puzzle p1){
		
		final mainFrameController m = this;
		
		Runnable solverRunnable = new Runnable(){

			@Override
			public void run() {
				boolean reflect = gui.getFlipPieceFlag();
				boolean rotate = gui.getRotatePieceFlag();
				
				gui.resetGUI();
				solutions.clear();
				Solver.solve(p1, m, reflect,rotate);
				m.setPercentage(100);
				bar.setString("You're Welcome");
				gui.setSolutionButton(true);
				gui.setLoadButton(true);
				gui.setFlipPieceFlag(true);
				gui.setRotatePieceFlag(true);
			}
		};
		
		solveThread = new Thread(solverRunnable);
		solveThread.start();
		gui.setSolutionButton(false);
		gui.setLoadButton(false);
		gui.setFlipPieceFlag(false);
		gui.setRotatePieceFlag(false);
		
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void killEverything(){
		solveThread.stop();
		gui.setSolutionButton(true);
		gui.setLoadButton(true);
		gui.setFlipPieceFlag(true);
		gui.setRotatePieceFlag(true);
	}
	
	public void setProgressBar(JProgressBar bar){
		this.bar = bar;
	}
	
	public void setPercentage(double percent){
		bar.setValue((int)(percent*1000));
		DecimalFormat format = new DecimalFormat("0.00000");
		bar.setString(format.format(percent) + "%");
	}
	
	public synchronized void addSolution(Solution s){
		solutions.add(s);
		if (solutions.size() == 1) gui.setSolutionFlag(true);
		gui.setSolutionNumLabel(solutions.size());
	}
	
	public Solution getSolution(int x){
		if (solutions.size() > x) return solutions.get(x);
		return null;
	}
	
	/*
	 * public void loadFile(String path)
	 * 
	 * Parse the given file and set puzzle
	 * to the returned puzzle
	 * 
	 * @param the String for the file path
	 */
	public void loadFile(String path){
		FileParser parser = new FileParser();
    	puzzle = parser.parseFile(path);
    	System.out.println("File loaded");
	}
	
	
	/*
	 * public int getPuzzleHeight()
	 * 
	 * Gets the height of the puzzle board
	 * 
	 * @return the height of the puzzle board, or 
	 * -1 if the puzzle is not found/doesn't have
	 * a board
	 */
	public int getPuzzleHeight(){
		try{
			return puzzle.getBoard().getHeight();
		}catch(NullPointerException e){
			return -1;
		}
	}
	
	/*
	 * public int getPuzzleWidth()
	 * 
	 * Gets the width of the puzzle board
	 * 
	 * @return the width of the puzzle board, or 
	 * -1 if the puzzle is not found/doesn't have
	 * a board
	 */
	public int getPuzzleWidth(){
		try{
			return puzzle.getBoard().getWidth();
		}catch(NullPointerException e){
			return -1;
		}
	}
	
	/*
	 * public Piece getBoard()
	 * 
	 * Gets the board
	 * 
	 * @return the board for the puzzle
	 */
	public Piece getBoard(){
		return puzzle.getBoard();
	}
	
}
