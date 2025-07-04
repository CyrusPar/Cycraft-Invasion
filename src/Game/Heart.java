/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.scene.image.Image;

public class Heart extends Item {         //Power-up
	private final static int HEART_SPEED = 2; 
	private final static Image HEART_IMAGE = new Image("images/heart.png",POWERUP_WIDTH,POWERUP_WIDTH,true,true);  //Image
	private final static int GAIN = 6;           //Points to be gain from collecting
	private final static int ADD_HEALTH = 100;     //increase the health of the player by 100 if the player collides

	Heart(double x, double y){      //Constructor
		super(x,y,Heart.HEART_IMAGE);
		this.speed = Heart.HEART_SPEED;
	}

	@Override
	void checkCollision(Steve steve){      //method for checking if it collides
		if(this.collidesWith(steve)){
			System.out.println(steve.getName() + " has collected a Heart! Health increase by 100");
			this.vanish();          //emoves the item if it collides
			steve.gainScore(Heart.GAIN);
			steve.addHealth(Heart.ADD_HEALTH);    //Gain score and additional health
			System.out.println(steve.getName() + " Current Health: "  + steve.getHealth());  //Prints steve health in the terminal
		}
	}
	
	
	
	
	
	
	
	
	
	
}
