/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Guard extends Sprite {           //A subclass of sprite
	
	private static final double MAX_GUARD_SPEED = 1.0;
	private static final double MIN_GUARD_SPEED = 2.0;
	public final static Image GUARD_IMAGE = new Image("sprite/ghast.gif",Guard.GUARD_WIDTH,Guard.GUARD_WIDTH,true,true);    //Image
	private final static int GUARD_WIDTH = 200;
	private MediaPlayer steveMediaPlayer;

	
	private boolean alive;
	private int health;
	private double speed;                  //Attributes of the ghast or guard
	private int damage;
	private boolean initY;
	private final static int INITIAL_DAMAGE = 50;         //Damage
	private boolean collidedSteve;
	
	private ArrayList<GuardProjectile> projectile;                //Array of projectile since the guard fires to the player
	private int arrowtype;
	private String name;
	private boolean moveUp;

	
	Guard(int x, int y, boolean alive){       //Constructor
		super(x,y,GUARD_IMAGE);
		Random r = new Random();
		this.damage = r.nextInt(25)+INITIAL_DAMAGE;
		this.speed = r.nextDouble()*(MAX_GUARD_SPEED-MIN_GUARD_SPEED) + MIN_GUARD_SPEED;          //Randomizes the speed and damage of the guard as a handicap
		this.health = 700;                         //Initiate the health
		this.moveUp = true;

		this.initY = false;
		this.arrowtype = 0;
		this.alive = alive;
		
		this.name = "Ghast";             //The name of the mob oR GUARD
		this.projectile = new ArrayList<GuardProjectile>();
		
        Media steveSound = new Media(new File(Steve.STEVE_SOUND).toURI().toString());       //Initialize the steve sound similar to other sprites
        steveMediaPlayer = new MediaPlayer(steveSound);
	}
	
	void move(){                            //Method for moving the ghast
		if (this.moveUp){
			if (this.yPos < MainGameStage.GWINDOW_HEIGHT-50){      //Move the ghast up and if it is in the boundary set the move up to false so that it could go down
				this.yPos += this.speed;
			}
			else{
				this.moveUp = false;
			}
		}
		if (!this.moveUp){                          //Move the ghast down if it reaches the upper boundary
			if (this.yPos > 0){
				this.yPos -= this.speed;                             //Moves the ghast up as it reaches the bottom boundary by setting the move up to true
			}
			else{
				this.moveUp = true;   
			}
		}
                                                         //The ghast main movement is repeatedly on the y axis or vertically on the end of the screen
	}
	
	void checkCollision(Steve steve){
		for	(int i = 0; i < steve.getArrows().size(); i++)	{                        //Check if one of the arrays of arrow of the player might have collided with the ghast
			if (this.collidesWith(steve.getArrows().get(i))){
				this.getHit(steve.getArrows().get(i).getDamage());                        //geth the damage of the arrow
				steve.getArrows().get(i).vanish();                                        //Set the arrow to vanish once it collides
				System.out.println(steve.getName() + "'s arrow hit the ghast. Current boss health: " + this.getHealth());   //Prints if it hits and show the boss health for awareness
			}
			if (this.getHealth() <= 0) { this.die(); }                        //Check if the ghast health is at 0 so we prompt it to die
		}
			
		for	(int i = 0; i < this.getGuardProjectile().size(); i++)	{                        //Check if one of the p[rojectiles of the ghast has hit the player
			if (steve.collidesWith(this.getGuardProjectile().get(i))){
				steve.addHealth(-(this.getGuardProjectile().get(i).getDamage()));;                 //Two case scenario in which steve's is immortal and not
				this.getGuardProjectile().get(i).vanish();
				steveMediaPlayer.setVolume(0.5);
				steveMediaPlayer.play();
				steve.downgradearrowType();                                                                        //If the projectile collides with the player,return the stats to normal for steve
				steve.loadImage(Steve.STEVE_IMAGE);
				steve.setShield(false);
				System.out.println(this.name + "'s projectile hit the player. Current player Health: " + steve.getHealth()); //prompt on the terminal that it hits the player
			}
			if (steve.getHealth() <= 0) { steve.die(); }
		}
			
		if (this.collidesWith(steve)){                //To check if the player has collided with the boundary or rectangle collision of the ghast or guard
			if (steve.getShield() == false){                        //Check for immortality
				if (this.collidedSteve == false){
					steve.addHealth(-damage);                           //Reduce the health of steve based on the damage
					steveMediaPlayer.setVolume(0.5);
					steveMediaPlayer.play();
					System.out.println(steve.getName() + " hits the ghast and took damage. Current player strength: " + steve.getHealth());
					this.collidedSteve = true;
					if(steve.getHealth() <= 0) steve.die();
				}

			} else {
				System.out.println(steve.getName() + " hits the ghast but did not receive damage due to immortality."); //Steve is immortal upon collision, no damage will be taken
				steve.downgradearrowType();                                          
				steve.loadImage(Steve.STEVE_IMAGE);     // reduce the powerup by returning to normal
				steve.setShield(false);
			}
		}
		if (this.collidesWith(steve) == false) { this.collidedSteve = false; }

		}
	
	void shoot(){ 
	    this.projectile.add(new GuardProjectile(this.arrowtype,this.xPos-27,this.yPos+35));        //Method for shooting of the guard
	    
	}
	
	private void getHit(int damage){ 
		this.setHealth(this.getHealth() - damage); // Reduce the health of the ghast everytime it gets hit by the arrow of the player
	}
	
	ArrayList<GuardProjectile> getGuardProjectile(){ // Gets the array list of ghast projectile
		return this.projectile;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setHealth(int health) { // Sets health of the ghast
		this.health = health;
	}
	
	void setAlive(boolean alive){ // Sets the alive status
		this.alive = alive;
	}
	
	void die(){ // Sets the ghast's status to deceased
		this.alive = false;
		this.vanish();
	}
	
	public int getHealth() { // Gets the health of the ghast
		return health;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
