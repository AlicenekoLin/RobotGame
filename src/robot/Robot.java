package robot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * File: Robot.java
 * Author: Chien-Han, Lin
 * 
 * This file constructs a robot with robot parameters, including image icon, 
 * location x, location y and facing direction which will be implemented in the 
 * Game class.
 * 
 * Source: R2D2 icon image comes from https://www.iconfinder.com/search/?q=r2d2
 * Source: compass icon image comes from https://www.iconfinder.com/icons/249846
 * 			/compass_east_location_navigate_navigation_north_icon
 * 
 */

/**
 * A class defined a robot.
 * 
 */
public class Robot extends JPanel {
	private static final long serialVersionUID = 1L;
	//Image for the robot
	private Image robotSelected;
	private List<BufferedImage> robotImages = new ArrayList<>(4);;
	private ImageIcon robotIcon;
	//Image for the compass
	private Image compass;
	private ImageIcon compassIcon;
	//Size of each game board block 
	private static final int BLOCKSIZE = 80;
	//Number of row/column in the game board
	private final static int BLOCK = 8;
	//Location x of the robot
	private int x; 
	//Location y of the robot
	private int y;
	//Facing direction of the robot
	private char facing;
	//Panel width for the robot
	private final int PANELWIDTH = 640;
	//Panel height for the robot 
	private final int PANELHEIGHT = 640;

	/**
	 * Initialize the Robot.
	 */
	public Robot(){}

	/**
	 * Initializes the Robot.
	 * 
	 * @param x location x of the robot
	 * @param y location y of the robot
	 * @param facing facing direction
	 * 
	 */
	public Robot(int x, int y,  char facing){
		//Set background transparent
		this.setOpaque(false);
		//Convert the index of block row/column to the location 
		this.x = (x-1)*BLOCKSIZE;
		this.y = (BLOCK-y)*BLOCKSIZE;
		this.facing = facing;
		//Load robot image
		loadImageIcon(this.facing);
	}

	/**
	 * {@inheritdoc}
	 * 
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		robotIcon.paintIcon(this, g, x, y);
		compassIcon.paintIcon(this, g, 560, 560);
	}

	/**
	 * This method loads the robot and the compass image, and set them to 
	 * the robotIcon and the compassIcon. 
	 * 
	 * @param dir the facing direction
	 * 
	 */
	public void loadImageIcon(char dir){
		try{
			robotImages.add(ImageIO.read(new File("R2D2_N.png")));
			robotImages.add(ImageIO.read(new File("R2D2_E.png")));
			robotImages.add(ImageIO.read(new File("R2D2_S.png")));
			robotImages.add(ImageIO.read(new File("R2D2_W.png")));

			switch(dir){
			case 'N':
				robotSelected = robotImages.get(0);
				break;
			case 'E':
				robotSelected = robotImages.get(1);
				break;
			case 'S':
				robotSelected = robotImages.get(2);
				break;
			case 'W':
				robotSelected = robotImages.get(3);
				break;
			default:
				break;
			}

			robotIcon = new ImageIcon(robotSelected.getScaledInstance(
					BLOCKSIZE, BLOCKSIZE,Image.SCALE_DEFAULT));
		}catch(IOException e){
			e.printStackTrace();
		}

		try{
			compass = ImageIO.read(new File("compass.png"));
			compassIcon = new ImageIcon(compass.getScaledInstance(
					BLOCKSIZE, BLOCKSIZE,Image.SCALE_DEFAULT));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the current facing direction.
	 * 
	 * @return facing direction
	 */
	public char getFacingDir(){
		return this.facing;
	}

	/**
	 * This method sets the facing direction according to the action it 
	 * is performed and its previous facing dierction.
	 * 
	 * @param turn an operated action
	 * @return facing direction
	 * 
	 */
	public void setFacingDir(char turn){
		char current = this.getFacingDir();
		if(turn == 'L'){
			switch(current){
			case 'N':
				this.facing = 'W';
				break;
			case 'E':
				this.facing = 'N';
				break;
			case 'S':
				this.facing = 'E';
				break;
			case 'W':
				this.facing = 'S';
				break;
			default:
				break;
			}
		}else if(turn == 'R'){
			switch(current){
			case 'N':
				this.facing = 'E';
				break;
			case 'E':
				this.facing = 'S';
				break;
			case 'S':
				this.facing = 'W';
				break;
			case 'W':
				this.facing = 'N';
				break;
			default:
				break;
			}
		}
		loadImageIcon(this.facing);
		validate();
		repaint();
	}

	/**
	 * This method returns the current location x.
	 * 
	 * @return location x
	 * 
	 */
	public int getRobotX(){
		return this.x;
	}

	/**
	 * This method returns the current location y.
	 * 
	 * @return location y
	 * 
	 */
	public int getRobotY(){
		return this.y;
	}

	/**
	 * This method sets the location x to the input argument.
	 * 
	 * @param x location x
	 * 
	 */
	public void setRobotX(int x){
		this.x = x;
	}

	/**
	 * This method sets location y to the input argument.
	 * 
	 * @param y location y
	 * 
	 */
	public void setRobotY(int y){
		this.y = y;
	}

	/**
	 * This method checks whether the robot can move forward or not. If it is 
	 * true, moves the robot forward one block; if it is false, do not move the 
	 * robot.
	 * 
	 * @return true if the robot can move forward; return false if not.
	 * 
	 */
	public boolean moveRobot(){
		char facing = getFacingDir();
		boolean result = false;
		switch(facing){
		case 'E':
			if(this.x < PANELWIDTH - BLOCKSIZE){
				this.x += BLOCKSIZE;
				result = true;
			}
			break;
		case 'W':
			if(this.x >= BLOCKSIZE){
				this.x -= BLOCKSIZE;
				result = true;
			}
			break;
		case 'S':
			if(this.y < PANELHEIGHT - BLOCKSIZE){
				this.y += BLOCKSIZE;
				result = true;
			}
			break;
		case 'N':
			if(this.y >= BLOCKSIZE){
				this.y -= BLOCKSIZE;
				result = true;
			}
			break;
		default:
			break;
		}
		loadImageIcon(this.facing);
		validate();
		repaint();
		return result;
	}
}
