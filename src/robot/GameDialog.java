package robot;

import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
/**
 * File: GameDialog.java
 * Author: Chien-Han, Lin
 * 
 * This file builds a dialog that can take user input, for example,
 * starting points [x, y] and the facing direction, for playing the robot game.
 * 
 */

/**
 * A class to take user input containing information for playing 
 * the robot game.
 * 
 */
public class GameDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	//Textfield for the starting block column x
	JFormattedTextField startAtX;
	//Textfield for the starting block row y
	JFormattedTextField startAtY;
	//Textfield for starting the facing direction
	JFormattedTextField direction;
	//User input x
	public int xBlock;
	//User input y
	public int yBlock;
	//User input facing direction
	public char faceDir;
	//Components for the dialog
	JComponent[] inputs;

	/**
	 * Initialize the GameDialog.
	 * 
	 */
	public GameDialog(){
		dialogGUI();
	}

	/**
	 * The method construct the GUI for the dialog.
	 * 
	 */
	private void dialogGUI(){
		//Set three textfields for requesting user input 
		startAtX = new JFormattedTextField();
		startAtY = new JFormattedTextField();
		direction = new JFormattedTextField();

		startAtX.setValue(new Integer(1));
		startAtX.setColumns(1);

		startAtY.setValue(new Integer(1));
		startAtY.setColumns(1);

		direction.setValue(new String("N"));
		direction.setColumns(1);

		inputs = new JComponent[]{
				new JLabel("Provide starting position [x,y]" + "\n"), 
				new JLabel("Starting at x, (0 < x < 9): \n"), startAtX, 
				new JLabel("Starting at y, (0 < x < 9): \n"), startAtY,
				new JLabel("Provide facing direction (N, E, S, W) \n"), direction};

		int result = JOptionPane.showConfirmDialog(null, inputs, 
				"Start Robot Games", JOptionPane.OK_CANCEL_OPTION);

		//Get user input after the OK button is pressed
		//Leave and close the program if the CANCEL button is pressed
		if (result == JOptionPane.OK_OPTION) {
			//Get user input
			int inputX = Integer.parseInt(startAtX.getText());
			this.xBlock = inputX;

			int inputY = Integer.parseInt(startAtY.getText());
			this.yBlock = inputY;

			char inputDir = direction.getText().charAt(0);
			this.faceDir = inputDir;	
		} else if(result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Get user input for the starting block column x.
	 * 
	 * @return starting block column x
	 * 
	 */
	public int getInputX(){
		return this.xBlock;
	}

	/**
	 * Get user input for the starting block row y.
	 * 
	 * @return starting block column y
	 * 
	 */
	public int getInputY(){
		return this.yBlock;
	}

	/**
	 * Get user input for the facing direction.
	 * 
	 * @return facing direction
	 */
	public int getInputDir(){
		return this.faceDir;
	}
}