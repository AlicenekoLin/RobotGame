# README #  

# File list  
***
- Game.java  
- Robot.java  
- GameDialog.java  
- compass.png [Source](https://www.iconfinder.com/icons/249846/compass_east_location_navigate_navigation_north_icon)    
- R2D2_N.png [Source](https://www.iconfinder.com/search/?q=r2d2)    
- R2D2_E.png  
- R2D2_S.png  
- R2D2_W.png  

# Class design  
***
### Game.java    
The 2D robot game is launched by this class with a game board, a information panel and a result panel. After receving the starting row/column of block and the facing direction to start the game, users can use keyboard key M/m to move the robot forward, R/r to turn right, L/l to turn left, E/e to end the game and show result on the information panel, and Q/q to quit/close the program. If users perform any action after operating E/e, the location and the direction of the robot will be reset according to the last attempt.    
    
### Robot.java    
The parameters of the robot are defined in this class, including the robot image icons, the starting location, the facing direction, the moving action, and turning left/right action.    

### GameDialog.java    
This class initializes a dialog for taking user input, such as the starting row/column of block and the facing direction.    
    
# Run the program in the terminal  
***
1. CD to the RobotGamesVer2 document, for example, cd /Users/Alice/Documents/workspace/RobotGamesVer2    
2. java -jar StartRobotGame.jar    

# Contact  
***
Chien-Han, Lin  
chl590@eng.ucsd.edu

