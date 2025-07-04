/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.scene.image.Image;

public class Emerald extends Item {    //Powerup
	
	private final static int EMERALD_SPEED = 2;               //Speed
	private final static Image EMERALD_IMAGE = new Image("images/emerald.gif",POWERUP_WIDTH,POWERUP_WIDTH,true,true);  //Image for the Emerald
	private final static int GAIN = 2;

	Emerald(double x, double y){
		super(x,y,Emerald.EMERALD_IMAGE);
		this.speed = Emerald.EMERALD_SPEED;
	}

	@Override
	void checkCollision(Steve steve){          //Method for checking if it collided with the player
		if(this.collidesWith(steve)){
			System.out.println(steve.getName() + " has collected an Emerald!");
			this.vanish(); 
			steve.gainScore(Emerald.GAIN);           //Gain score from collecting emeralds
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
