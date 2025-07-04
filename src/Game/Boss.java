/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import java.util.ArrayList;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;

public class Boss extends Sprite{
	private static final double MAX_BOSS_SPEED = 1;
	private static final double MIN_BOSS_SPEED = 0.8;
	public final static Image BOSS_IMAGE = new Image("sprite/edragon.gif",Boss.BOSS_WIDTH,Boss.BOSS_WIDTH,true,true);  //Boss image
	private final static int BOSS_WIDTH = 300;
	
	private boolean alive;
	private int health;
	private double speed;
	private int damage;
	private boolean initY;
	private final static int INITIAL_DAMAGE = 50;
	private boolean collidedSteve;
	private ArrayList<BossBlast> blast;  //Array for the boss blast
	private int arrowtype;
	private String name;
	private boolean moveUp;
	private boolean moveRight;
	private MediaPlayer steveMediaPlayer;
	
	
	Boss(int x, int y, boolean alive){   //constructor
		super(x,y,BOSS_IMAGE);
		Random r = new Random();
		this.damage = r.nextInt(25)+INITIAL_DAMAGE;
		this.speed = r.nextDouble()*(MAX_BOSS_SPEED-MIN_BOSS_SPEED) + MIN_BOSS_SPEED;
		this.health = 2000;
		this.moveUp = true;
		this.moveRight = true;           //Movement 
		this.initY = false;
		this.arrowtype = 0;
		this.alive = alive;
		
		this.name = "Ender Dragon";
		this.blast = new ArrayList<BossBlast>();
		
        Media steveSound = new Media(new File(Steve.STEVE_SOUND).toURI().toString());
        steveMediaPlayer = new MediaPlayer(steveSound);
	}
	
	void move(){      //Method for moving the boss
		if (this.moveUp){
			if (this.yPos < MainGameStage.GWINDOW_HEIGHT-50){
				this.yPos += this.speed;
			}
			else{
				this.moveUp = false;
			}
		} 
		if (!this.moveUp){                            //For this case the boss moves similar to the player, however only half of the screen it can traverse due to its damage
			if (this.yPos > 0){
				this.yPos -= this.speed;
			}
			else{
				this.moveUp = true;
			}
		}
		
		if (this.moveRight){
			if (this.xPos <= MainGameStage.GWINDOW_WIDTH-50){ 
				this.xPos += this.speed; 
			}else {
				this.moveRight = false; 
			}
		}
		if (!this.moveRight){
			if (this.xPos > 400){ 
				this.xPos -= this.speed; 
			}else { 
				this.moveRight = true; 
			}
		}
	}
	
	void checkCollision(Steve steve){   //Method for chekcing for collision
		for	(int i = 0; i < steve.getArrows().size(); i++)	{      //Loop through the arrow list of the player and check for collision

			if (this.collidesWith(steve.getArrows().get(i))){
				this.getHit(steve.getArrows().get(i).getDamage());
				steve.getArrows().get(i).vanish();
				System.out.println(steve.getName() + "'s arrow hit the Ender Dragon. Current boss health: " + this.getHealth());
			}
			if (this.getHealth() <= 0) { 
				this.die(); 
			}
		}
			
		for	(int i = 0; i < this.getBossBlast().size(); i++)	{

			if (steve.collidesWith(this.getBossBlast().get(i))){
				steve.addHealth(-(this.getBossBlast().get(i).getDamage()));;
				this.getBossBlast().get(i).vanish();
				steveMediaPlayer.setVolume(0.5);
				steveMediaPlayer.play();
				steve.downgradearrowType();
				steve.loadImage(Steve.STEVE_IMAGE);
				steve.setShield(false);
				System.out.println(this.name + "'s Blast hit the player. Current player Health: " + steve.getHealth());
			}
			if (steve.getHealth() <= 0) { steve.die(); }
		}
			
		if (this.collidesWith(steve)){    //If it collided with steve physically

			if (steve.getShield() == false){  //steve has no shield
				if (this.collidedSteve == false){
					steve.addHealth(-damage);
					steveMediaPlayer.setVolume(0.5);
					steveMediaPlayer.play();
					System.out.println(steve.getName() + " hits the Ender Dragon and took damage. Current player Health: " + steve.getHealth());
					this.collidedSteve = true;
					if(steve.getHealth() <= 0) steve.die();
				}

			} else {
				System.out.println(steve.getName() + " hits the Ender Dragon but did not receive damage due to shield.");
				steve.downgradearrowType();
				steve.loadImage(Steve.STEVE_IMAGE);
				steve.setShield(false);
			}
		}
		if (this.collidesWith(steve) == false) { this.collidedSteve = false; }

		}
	
	void shoot(){ 
	    this.blast.add(new BossBlast(this.arrowtype,this.xPos-27,this.yPos+180));
	   
	}
	
	private void getHit(int damage){ 
		this.setHealth(this.getHealth() - damage); // Decreases health of the enderdragon
	}
	
	ArrayList<BossBlast> getBossBlast(){ // Gets the array list of enderdragon blast
		return this.blast;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setHealth(int health) { // Sets health of the enderdragon
		this.health = health;
	}
	
	void setAlive(boolean alive){ // Sets the alive status
		this.alive = alive;
	}
	
	void die(){ // Sets the enderdragon's status to deceased
		this.alive = false;
		this.vanish();
	}
	
	public int getHealth() { // Gets the health of the enderdragon
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
