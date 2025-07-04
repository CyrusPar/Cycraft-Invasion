/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;
import javafx.scene.image.Image;

public class Portal extends Sprite {         //The portal in the start of the game
	private boolean alive;
	private boolean initY;
	private boolean initX;
	private boolean collidedSteve;
	public final static int PORTAL_WIDTH = 400;
	public final static Image PORTAL_IMAGE = new Image("sprite/netherportal.gif",Portal.PORTAL_WIDTH,Portal.PORTAL_WIDTH,true,true);
		
	Portal(int x, int y, boolean alive){
		super(x,y,PORTAL_IMAGE);
		this.alive = alive;
		this.initY = false;
	}
	
	
	void checkCollision(Steve steve) {
		if(this.collidesWith(steve)) {
			if (this.collidedSteve == false){
				this.collidedSteve = true;
				this.die();
				steve.entersPortal();
				System.out.println(steve.getName()+ "Entered the Nether realm to start the adventure");
			}
		}
		if (this.collidesWith(steve) == false) { this.collidedSteve = false; }
		//System.out.println(steve.getName()+ "Entered the Nether realm to start the adventure");
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	void die(){ 
		this.alive = false;
		this.vanish();
	}
	
	void setAlive(boolean alive){ // Sets the alive status
		this.alive = alive;
	}
	
	boolean getInitY(){ // Gets the initial Y attribute
		return this.initY;
	}
	
	void setInitY(boolean y){ // Sets the initial Y attribute
		this.initY = y;
	}
	
	void setY(int y){ // Sets the y-coordinate
		this.yPos = y;
	}
	
	boolean getInitX(){ // Gets the initial Y attribute
		return this.initX;
	}
	
	void setInitX(boolean x){ // Sets the initial Y attribute
		this.initX = x;
	}
	
	void setX(int x){ // Sets the y-coordinate
		this.xPos = x;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
}
