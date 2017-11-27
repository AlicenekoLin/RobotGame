package robot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * File: Game.java
 * Author: Chien-Han, Lin
 * 
 * This file builds a 2D robot game with a game board panel, a result panel and 
 * a movable robot icon. Actions of the robot are controlled by keyboard keys 'M'
 * , 'L' and 'R'. Once the robot reaches the end of the board, it cannot move 
 * beyond the board boundaries. The alert dialog will pop out,and suggest users
 * to turn left/right.  
 * 
 */

/**
 * A class to take user input and perform the game.
 * 
 */
public class Game extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1L;
	//Dimension for the game board
	private Dimension BOARDSIZE = new Dimension(650, 650);
	//Dimension for the frame
	private Dimension FRAMESIZE = new Dimension(1000, 680);
	//Dimension for the info panel
	private Dimension INFOSIZE = new Dimension(300, 650);
	//Number of row/column in the game board
	private final static int BLOCK = 8;
	//Size of block in the game board
	private final int BLOCKSIZE = 80;
	//Gap for JPanel border
	private final int GAP = 10;
	//Starting location x
	private static int startX;
	//Starting location y
	private static int startY;
	//Starting facing direction
	private static char startDir;
	//Number of Action
	private int numOfAction = 0;
	//List for documenting all valid actiona
	List<Character> actionList = new ArrayList<Character>();
	//Layer for game panel
	JLayeredPane layer;
	//Label for showing starting location
	JLabel dirInfo;
	//Label for showing starting facing direction
	JLabel locationInfo;
	//Game baord 
	Block board;
	//Robot for the game
	Robot R2D2;
	//Textfield for showing results
	JTextPane text;
	//Scrollable panel for showing results
	JScrollPane textPanel;
	StringBuilder stringbuilder;

	/**
	 * Initialize the Game.
	 * 
	 */
	public Game(){
		displayGUI();
	}

	/**
	 * Construct the GUI for the game.
	 * 
	 */
	private void displayGUI(){
		//Establish the properities for the game frame
		this.setPreferredSize(FRAMESIZE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);

		//Use the contentPanel panel to construct the game frame layout
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		contentPanel.setBackground(Color.WHITE);

		//Use the layer to overlap the game board and the robot
		layer = new JLayeredPane();
		layer.setBounds(GAP,GAP,BOARDSIZE.width, BOARDSIZE.height);
		layer.setPreferredSize(BOARDSIZE);

		//Construct the game board 
		board = new Block();
		board.setBounds(GAP, GAP, BOARDSIZE.width, BOARDSIZE.height);
		board.setOpaque(true);

		//Construct the robot
		R2D2 = new Robot(startX, startY, startDir);
		R2D2.setBounds(GAP, GAP, BOARDSIZE.width, BOARDSIZE.height);
		R2D2.setOpaque(false);

		//Put the game board and the robot into the layer
		layer.add(board, new Integer(0), 0);
		layer.add(R2D2, new Integer(1), 0);

		//Use the container panel to display information
		JPanel container = new JPanel();
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(INFOSIZE);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		container.setLayout(new BorderLayout(15,15));

		//Construct the keybord control information panel
		JPanel control = new JPanel();
		control.setLayout(new GridLayout(7, 1, 5, 5));
		control.setOpaque(true);
		control.setBackground(Color.WHITE);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		control.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createCompoundBorder(raisedbevel, loweredbevel),
				" [ Game Infomation ] ", TitledBorder.CENTER, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 20), Color.DARK_GRAY));

		//Move label
		JLabel M = new JLabel(" Move a square forward");
		M.setFont(new Font("Tahoma", Font.PLAIN, 13));
		M.setBorder(BorderFactory.createTitledBorder(
				null, " [M/m] ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//Left label
		JLabel L = new JLabel(" Turn left");
		L.setFont(new Font("Tahoma", Font.PLAIN, 13));
		L.setBorder(BorderFactory.createTitledBorder(
				null, " [L/l] ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//Right label
		JLabel R = new JLabel(" Turn right");
		R.setFont(new Font("Tahoma", Font.PLAIN, 13));
		R.setBorder(BorderFactory.createTitledBorder(
				null, " [R/r] ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//End of the game label
		JLabel E = new JLabel(" End of the game");
		E.setFont(new Font("Tahoma", Font.PLAIN, 13));
		E.setBorder(BorderFactory.createTitledBorder(
				null, " [E/e] ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//Restart the game label
		JLabel Q = new JLabel(" Quit the game");
		Q.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Q.setBorder(BorderFactory.createTitledBorder(
				null, " [Q/q] ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//Starting location information info
		locationInfo = new JLabel("  Loction: [" + startX + "," + startY + "]");
		locationInfo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		locationInfo.setBorder(BorderFactory.createTitledBorder(
				null, " Starting Location ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		//Starting location information info
		dirInfo = new JLabel("  Facing direction: [" + startDir + "]");
		dirInfo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dirInfo.setBorder(BorderFactory.createTitledBorder(
				null, " Starting faing direction ", TitledBorder.LEFT, TitledBorder.TOP, 
				new Font("Allegro", Font.BOLD, 14), Color.DARK_GRAY));

		// Add labels into control panel
		control.add(M);  
		control.add(L);  
		control.add(R);
		control.add(E);
		control.add(Q);
		control.add(locationInfo);
		control.add(dirInfo);

		//Scollable textfield for displaying results
		JPanel tmp = new JPanel(new BorderLayout());
		text = new JTextPane();
		text.setEditable(false);
		text.setContentType("text/plain");
		textPanel = new JScrollPane(text);
		tmp.add(textPanel, BorderLayout.CENTER);

		//Add all panels into the container panel
		contentPanel.add(layer);
		container.add(control, BorderLayout.PAGE_START);
		container.add(tmp,BorderLayout.CENTER);
		contentPanel.add(container);

		//Add the contentPane panel to the frame
		this.add(contentPanel, BorderLayout.NORTH);
		this.addKeyListener(this);
	}

	/**
	 * This method takes two int targuments and returns the number of 
	 * corresponding block indexs.
	 * 
	 * @param x the location x
	 * @param y the location y
	 * @return an array with computed block indexs 
	 * 
	 */
	private int[] printBlock(int x, int y){
		int resultX = (x/BLOCKSIZE) + 1;
		int resultY = BLOCK - (y/BLOCKSIZE);
		int[] result = {resultX, resultY};
		return result;
	}

	/**
	 * This method takes two int and one char arguments and print out 
	 * information at the text panel.
	 * 
	 * @param x the location x
	 * @param y the location y
	 * @param action action operated
	 * 
	 */
	private void printInfo(int x, int y, char action){
		stringbuilder = new StringBuilder();
		stringbuilder.append(text.getText());
		stringbuilder.append("#Action " + numOfAction+ ": " + action + "\n");
		stringbuilder.append("-Direction faced: "+ R2D2.getFacingDir() + "\n");
		stringbuilder.append("-Location: [" + Integer.toString(printBlock(x,y)[0]) 
		+ "," + Integer.toString(printBlock(x,y)[1]) + "]" + "\n");
		
		text.setText(null);
		text.setText(stringbuilder.toString());
		text.setFont(new Font("Allegro", Font.PLAIN, 13));
		text.setForeground(Color.DARK_GRAY);
	}

	/**
	 * This method takes two int arguments and print out final information at 
	 * the text panel.
	 * 
	 * @param x the location x
	 * @param y the location y
	 * 
	 */
	private void printFinalInfo(int x, int y){
		stringbuilder = new StringBuilder();
		stringbuilder.append(" ***************** End of the game ***************** \n");
		stringbuilder.append(" *Starting Location: ["+ startX + "," + startY + "]" + "\n");
		stringbuilder.append(" *Direction faced: "+ startDir + "\n");
		stringbuilder.append(" *Action: ");
		for(char c: actionList){
			stringbuilder.append(c + ", ");
		}
		stringbuilder.append("\n");
		stringbuilder.append(" *Output Location: [" + Integer.toString(printBlock(x,y)[0]) 
		+ "," + Integer.toString(printBlock(x,y)[1]) + "]" + "\n");
		stringbuilder.append(" *Direction faced: "+ R2D2.getFacingDir() + "\n");
		stringbuilder.append(" ***************** End of the game ***************** \n");

		text.setText(null);
		text.setText(stringbuilder.toString());
		text.setFont(new Font("Allegro", Font.PLAIN, 13));
		text.setForeground(Color.DARK_GRAY);
	}

	/**
	 * {@inheritdoc}
	 * 
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * {@inheritdoc}
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		char action = ' ';
		//Operate an action to the corresponding key
		if(c == 'M' || c == 'm'){
			//Check the robot can move forward or not
			boolean canMove = R2D2.moveRobot();
			//If not, show the alert dialog
			if(!canMove){
				JOptionPane.showMessageDialog(this, "Cannot Move Forward. \n" + 
						"Please turn left (L/l) or turn right (R/r).",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}else{
				action = 'M';
				numOfAction++;
				actionList.add(action);
			}
		}else if(c == 'L' || c == 'l'){
			action = 'L';
			numOfAction++;
			//Update the facing direction of the robot
			R2D2.setFacingDir('L');
			actionList.add(action);
		}else if(c == 'R' || c == 'r'){
			action = 'R';
			numOfAction++;
			//Update the facing direction of the robot
			R2D2.setFacingDir('R');
			actionList.add(action);
		}else if(c == 'E' || c == 'e'){
			action = 'E';
			//Print the final information of the game
			printFinalInfo(R2D2.getRobotX(), R2D2.getRobotY());
			reset();
		}else if(c == 'Q' || c == 'q'){
			action = 'Q';
			//Exit the program
			System.exit(0);
		}else{
			JOptionPane.showMessageDialog(this, "Invalid Keyboard Input.\n" + 
					"Please use keys M(m), L(l), R(r), E(e) or Q(q). \n" + 
					"(Uppercase are typed with CAPS lock ON) \n",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}

		//Print the action result on the panel
		if(c != 'E' && c != 'e' && c != 'Q' && c != 'q'){
			printInfo(R2D2.getRobotX(), R2D2.getRobotY(), action);
		}
	}

	/**
	 * {@inheritdoc}
	 * 
	 */
	@Override
	public void keyReleased(KeyEvent e) {}

	/**
	 * This method resets the starting location and the direction to the 
	 * current location and direction. 
	 * 
	 */
	public void reset(){
		numOfAction = 0;
		int[] blockXY = printBlock(R2D2.getRobotX(),R2D2.getRobotY());
		Game.startX = blockXY[0];
		Game.startY = blockXY[1];
		Game.startDir = R2D2.getFacingDir();
		//Update the starting location information
		locationInfo.setText("  Loction: [" + startX + "," + startY + "]");
		dirInfo.setText("  Facing direction: [" + startDir + "]");
	}


	/**
	 * This method takes two int and one char arguments for validating 
	 * user inputs received from the GameDialog. 
	 * 
	 * @param x the location x
	 * @param y the location y
	 * @param dir the facing direction
	 * @return true if user input are valid; if not, show alert dialog and 
	 * 				retunn false
	 * 
	 */
	private static boolean checkStartPoint(int x, int y, char dir){
		boolean result = false;
		int constraint = BLOCK+1;
		if(x <= 0 || x >= constraint){
			JOptionPane.showMessageDialog(null, "Invalid x, (0 < x < 9). \n",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}

		if(y <= 0 || y >= constraint){
			JOptionPane.showMessageDialog(null, "Invalid y, (0 < y < 9). \n",
					"Warning", JOptionPane.WARNING_MESSAGE);			
		}

		if(x > 0 && x < constraint){
			if(y > 0 && y < constraint){
				result = true;
				startX = x;
				startY = y;
			}
		}
		return result & checkDirection(dir); 
	}

	/**
	 * This method takes one char argument for validating user inputs received 
	 * from the GameDialog. 
	 * 
	 * @param dir the facing direction
	 * @return true if user input are valid; if not, show alert dialog and 
	 * 				retunn false
	 * 
	 */
	private static boolean checkDirection(char dir){
		boolean result = false;
		switch(dir){
		case 'N':
		case 'E':
		case 'S':
		case 'W':
		case 'n':
		case 'e':
		case 's':
		case 'w':
			result = true;
			startDir = Character.toUpperCase(dir);
			break;
		default:
			JOptionPane.showMessageDialog(null, "Invalid Diecrtion, (N, E, S, W)."
					, "Warning", JOptionPane.WARNING_MESSAGE);
			break;
		}
		return result;
	}

	/**
	 * A class to take user input containing information for playing 
	 * the robot game.
	 * 
	 */
	public class Block extends JPanel{
		private static final long serialVersionUID = 1L;
		
		/**
		 * {@inheritdoc}
		 * 
		 */
		@Override
		public void paint(Graphics g){
			for (int i = 0; i < BLOCK; i++) {
				for (int j = 0; j < BLOCK; j++) {
					Color blockColor = (i+j) % 2 == 0 ?
							Color.WHITE:Color.LIGHT_GRAY;
					g.setColor(blockColor);
					g.fillRect(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
				}
			}
		}
	}


	/**
	 * Main method to run the robot game.
	 *
	 * @param args 
	 * 
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				try{
					//Check user input
					boolean checkInput = false;
					do{
						GameDialog inputDialog = new GameDialog();
						checkInput = checkStartPoint(inputDialog.xBlock, 
								inputDialog.yBlock, inputDialog.faceDir);
					}while(!checkInput);

					Game game = new Game();
					game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					game.pack();
					game.setFocusable(true);
					game.requestFocusInWindow();
					game.setVisible(true);
					game.setLocationRelativeTo(null);
				}catch(NullPointerException e){
					e.printStackTrace();
				}
			}
		});
	}
}

