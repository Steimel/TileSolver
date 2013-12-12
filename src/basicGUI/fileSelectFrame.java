package basicGUI;
import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.WindowConstants;

/*
 * public class fileSelectFrame
 * 
 * The GUI class that handles file browsing 
 * and selection
 * 
 */

@SuppressWarnings("serial")
public class fileSelectFrame extends javax.swing.JFrame {
	private JFileChooser fileChooser;
	private mainFrameController theApp;
	private String fileName;
	
	public fileSelectFrame(mainFrameController app) {
		super();
		theApp = app;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Choose File");
			{
				fileChooser = new JFileChooser();
				
				fileChooser.setDialogTitle("Choose File");				
			    int returnVal = fileChooser.showOpenDialog(getParent());
			    
			    
			    switch( returnVal )
		        {
		            case JFileChooser.CANCEL_OPTION:
		                System.out.println( "Cancel chosen" );
		                break;
		            case JFileChooser.APPROVE_OPTION:
		            	String file = fileChooser.getSelectedFile().getName();
		            	String path = fileChooser.getCurrentDirectory().toString();
		            	
		            	// Load the file at the chosen location
		            	theApp.loadFile(path + "\\" +file);
		            	fileName = file;
		                break;
		        }
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       
			    }
				getContentPane().add(fileChooser, BorderLayout.CENTER);
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public String getFileName(){
		return fileName;
	}

}
