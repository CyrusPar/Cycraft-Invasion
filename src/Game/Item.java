/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;
import javafx.scene.image.Image;

abstract class Item extends Sprite {       //Subclass of sprite
	protected String name;
	double speed;
	public final static int POWERUP_WIDTH = 40;
	private final static double POWER_SPEED = 8;
	
	Item(double x, double y, Image image){
		super(x,y,image);                 
		this.speed = Item.POWER_SPEED;		        //The speed of the item as it moves to the left
	} 
	
	String getName(){            //Gets the name of the item
		return this.name;
	}
	
	void move(){
		this.xPos -= this.speed;
		if(this.xPos >= MainGameStage.GWINDOW_WIDTH){	// if this item passes through width or on the left side of the game width, it vanishes
			this.vanish();
		}
	}
	
	abstract void checkCollision(Steve steve);   //Check if it collides with steve
}
