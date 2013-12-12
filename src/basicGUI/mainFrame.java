package basicGUI;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import projectCore.Piece;
import projectCore.Square;

/*
 * public class mainFrame
 * 
 * The GUI class that handles all UI related tasks
 * for the main window
 */


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("serial")
public class mainFrame extends javax.swing.JFrame {
	private JButton loadButton;
	private JButton solveButton;
	private JButton leftButton;
	private JLabel solDisplayLabel;
	private JButton rightButton;
	private JLabel solutionNumLabel;
	private JCheckBox rotatePieceFlag;
	private JPanel jPanel1;
	private JProgressBar progressBar;
	private JLabel fileLabel;
	private Grid drawCanvas;
	
	// The controller to handle all non-UI related operations
	// that eventually get displayed by the GUI
	private mainFrameController theApp;
	private int currSol;
	private JButton cancelButton;
	private JCheckBox flipPieceFlag;
	private int totalSol;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame inst = new mainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public void setSolutionFlag(boolean flag){
		if(flag){
			drawCanvas.drawSolution(theApp.getSolution(0));
			currSol = 1; 
		}		
	}
	
	public void setSolutionNumLabel(int solutions){
		solutionNumLabel.setText(solutions + " solutions found");
		totalSol  = solutions;
		solDisplayLabel.setText(currSol + "/" + totalSol);
		if(solutions > 1) rightButton.setEnabled(true);
	}
	
	public void resetGUI(){
		currSol = 0;
		totalSol = 0;
		setSolutionNumLabel(0);
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);
	}
	
	public void setSolutionButton(boolean flag){
		solveButton.setEnabled(flag);
	}
	
	public void setFlipPieceFlag(boolean flag){
		flipPieceFlag.setEnabled(flag);
	}
	
	public boolean getFlipPieceFlag(){
		return flipPieceFlag.isSelected();
	}
	
	public void setRotatePieceFlag(boolean flag){
		rotatePieceFlag.setEnabled(flag);
	}
	
	public boolean getRotatePieceFlag(){
		return rotatePieceFlag.isSelected();
	}
	
	public void setLoadButton(boolean flag){
		loadButton.setEnabled(flag);
	}
	
	public mainFrame() {
		super();
		this.theApp = new mainFrameController();
		theApp.setMainFrame(this);
		
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setTitle("Tile Puzzle Solver");

			pack();
			this.setSize(894, 442);
			this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				{
					progressBar = new JProgressBar(0, 100000);
					progressBar.setValue(0);
					progressBar.setStringPainted(true);
					theApp.setProgressBar(progressBar);
				}
				{
					rotatePieceFlag = new JCheckBox();
					rotatePieceFlag.setText("Rotate Pieces?");
				}
				{
					solutionNumLabel = new JLabel();
					solutionNumLabel.setText("0 solutions found");
				}
				{
					cancelButton = new JButton();
					cancelButton.setText("Forget It!");
					cancelButton.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							theApp.killEverything();
							progressBar.setString("Cancelled");
						}
						@Override
						public void mouseEntered(MouseEvent arg0) {
						}
						@Override
						public void mouseExited(MouseEvent arg0) {
						}
						@Override
						public void mousePressed(MouseEvent arg0) {
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {						
						}
					});
				}
				{
					solveButton = new JButton();
					solveButton.setText("Solve");
					solveButton.setBounds(23, 45, 86, 23);
					solveButton.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							// Solve the puzzle and display
							drawCanvas.redrawGrid(600, 600, theApp.getBoard(), drawCanvas.getGraphics());
							//drawCanvas.drawSolution(theApp.solve(theApp.getPuzzle())); 
							
							theApp.solve(theApp.getPuzzle());
							
							//Piece p = theApp.solve(theApp.getPuzzle()).get(0).getPieces().get(0);
							//Position pos = theApp.solve(theApp.getPuzzle()).get(0).getPositions().get(0);				
							//drawCanvas.outlinePiece(0, 0, 0, 0, p, drawCanvas.getGraphics());
							
						}
						
						@Override
						public void mouseEntered(MouseEvent arg0) {						
						}
						@Override
						public void mouseExited(MouseEvent arg0) {						
						}
						@Override
						public void mousePressed(MouseEvent arg0) {						
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {						
						}
					});
				}
				{
					leftButton = new JButton();
					leftButton.setText("<-");
					leftButton.setFont(new java.awt.Font("Segoe UI",0,12));
					leftButton.setEnabled(false);
					
					leftButton.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							if(currSol == 1) leftButton.setEnabled(false); 
							
							if(currSol > 1){
								rightButton.setEnabled(true);
								currSol--;
								drawCanvas.drawSolution(theApp.getSolution(currSol - 1));
								solDisplayLabel.setText(currSol + "/" + totalSol);
							}
							
						}
						
						@Override
						public void mouseEntered(MouseEvent arg0) {						
						}
						@Override
						public void mouseExited(MouseEvent arg0) {						
						}
						@Override
						public void mousePressed(MouseEvent arg0) {						
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {						
						}
					});
				}
				{
					solDisplayLabel = new JLabel();
					solDisplayLabel.setText("0/0");
				}
				{
					rightButton = new JButton();
					rightButton.setText("->");
					rightButton.setFont(new java.awt.Font("Segoe UI",0,12));
					rightButton.setEnabled(false);
					
					rightButton.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							if(currSol == totalSol) rightButton.setEnabled(false); 
							
							if(currSol < totalSol){
								currSol++;
								drawCanvas.drawSolution(theApp.getSolution(currSol - 1));
								solDisplayLabel.setText(currSol + "/" + totalSol);
								leftButton.setEnabled(true);
							}
							
						}
						
						@Override
						public void mouseEntered(MouseEvent arg0) {						
						}
						@Override
						public void mouseExited(MouseEvent arg0) {						
						}
						@Override
						public void mousePressed(MouseEvent arg0) {						
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {						
						}
					});
				}
				{
					fileLabel = new JLabel();
					fileLabel.setText("No File...");
				}
				{
					flipPieceFlag = new JCheckBox();
					flipPieceFlag.setText("Reflect Pieces?");
				}
				{
					loadButton = new JButton();
					loadButton.setText("Load File");
					loadButton.setBounds(23, 17, 86, 23);
					loadButton.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							fileSelectFrame fsFrame = new fileSelectFrame(theApp);
							if(fsFrame.getFileName().equals("")) {
								fileLabel.setText("No File...");
							}else{
								fileLabel.setText(fsFrame.getFileName());
							}
						}
						@Override
						public void mouseEntered(MouseEvent arg0) {
						}
						@Override
						public void mouseExited(MouseEvent arg0) {
						}
						@Override
						public void mousePressed(MouseEvent arg0) {
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {						
						}
					});
				}
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					        .addGap(32))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(solveButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					        .addGap(32))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					        .addGap(32))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(flipPieceFlag, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					        .addGap(13))
					    .addComponent(progressBar, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(rotatePieceFlag, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					        .addGap(13)))
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(leftButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					        .addComponent(solDisplayLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					        .addComponent(rightButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 52, Short.MAX_VALUE))
					    .addGroup(jPanel1Layout.createSequentialGroup()
					        .addPreferredGap(leftButton, solutionNumLabel, LayoutStyle.ComponentPlacement.INDENT)
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(solutionNumLabel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					                .addGap(0, 34, Short.MAX_VALUE))
					            .addComponent(fileLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)))));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(loadButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fileLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
					.addComponent(flipPieceFlag, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(rotatePieceFlag, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, Short.MAX_VALUE)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(leftButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(solDisplayLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(rightButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(solveButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(progressBar, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(solutionNumLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(48, 48));
			}
			{				
				drawCanvas = new Grid(600, 600, new Piece(new Square[1][1]));  // Create new Grid and draw			
			}
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(thisLayout.createParallelGroup()
					    .addGroup(thisLayout.createSequentialGroup()
					        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 0, Short.MAX_VALUE))
					    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
					        .addGap(50)
					        .addComponent(drawCanvas, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 161, Short.MAX_VALUE)))
					.addContainerGap(165, 165));
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
					.addGap(131)
					.addComponent(drawCanvas, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(293, Short.MAX_VALUE));
			this.validate();                // Make sure layout is ok
			
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
