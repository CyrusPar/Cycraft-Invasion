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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


import javafx.scene.image.Image;

public class Blaze extends Sprite {         //Subclass of the sprite
	private ArrayList<Item> powerups;
	public static final double MAX_BLAZE_SPEED = 1.5;
	public static final double MIN_BLAZE_SPEED = 0.5;
	public final static Image BLAZE_IMAGE = new Image("sprite/blaze.gif",Blaze.BLAZE_WIDTH,Blaze.BLAZE_WIDTH,true,true);   //Blaze image

	public final static int BLAZE_WIDTH = 120;
	private final static int ITEM_FREQUENCY = 30;         //Frequency fot the powerup
	private boolean alive;
	
	private static final String BH_SOUND = "src/soundeff/blazehit.mp3";
	private MediaPlayer blazehitMediaPlayer;
	private static final String BLAZE_SOUND = "src/soundeff/blaze.m4a";
	private MediaPlayer blazeMediaPlayer;
	private MediaPlayer steveMediaPlayer;


	private boolean moveRight;       
	private double speed;
	private int damage;
	private final static int INITIAL_DAMAGE = 30;
	
	Blaze(int x, int y,ArrayList<Item> powerups){
		super(x,y,BLAZE_IMAGE);
		this.alive = true;
		Random r = new Random();
		this.speed = r.nextDouble()*(MAX_BLAZE_SPEED-MIN_BLAZE_SPEED) + MIN_BLAZE_SPEED;   //Randomize the speed between the minimum and maximium
		this.moveRight = true;
		this.damage = r.nextInt(11)+INITIAL_DAMAGE;               //Set the damage of the blaze
        Media blazeSound = new Media(new File(BLAZE_SOUND).toURI().toString());
        blazeMediaPlayer = new MediaPlayer(blazeSound);
		this.powerups = powerups;                          //Powerups for the blazes
		
        Media blazehSound = new Media(new File(BH_SOUND).toURI().toString());
        blazehitMediaPlayer = new MediaPlayer(blazehSound);                          //Sound effects for the blaze and the player
        
        Media steveSound = new Media(new File(Steve.STEVE_SOUND).toURI().toString());   
        steveMediaPlayer = new MediaPlayer(steveSound);
	}
	
	void move(){                              //Method for moving the blaze left and right accross the game scene
		blazeMediaPlayer.setVolume(0.1);
		blazeMediaPlayer.play();
		if (this.moveRight){
			if (this.xPos < MainGameStage.GWINDOW_WIDTH-50){
				this.xPos += this.speed;
			}
			else{
				this.moveRight = false;
			}
		}
		if (!this.moveRight){
			if (this.xPos > 0){
				this.xPos -= this.speed;
			}
			else{
				this.moveRight = true;
			}
		}
	}
	
	void checkCollision(Steve steve){            
		for	(int i = 0; i < steve.getArrows().size(); i++){           //Loop through the arrows of steve and chekc if it collides with the blaze
			if (this.collidesWith(steve.getArrows().get(i))){ 
				this.getHit(steve.getArrows().get(i).getDamage());
				blazehitMediaPlayer.setVolume(0.5);
				blazehitMediaPlayer.play();
				steve.getArrows().get(i).vanish();
			}
		}
 
		if (this.collidesWith(steve)) {        //Check if it collides with steve via collision physically
			blazeMediaPlayer.setVolume(0.1);
			blazeMediaPlayer.play();
			if (steve.getShield() == false) {            //No immortality or shield
				steve.addHealth(-this.damage);
				steveMediaPlayer.setVolume(0.5);
				steveMediaPlayer.play();
				System.out.println(steve.getName() + " Current Health: "  + steve.getHealth());
			} else{
				System.out.println(steve.getName() + " hits a blaze but did not receive damage due to shield."); 
				steve.downgradearrowType();
				steve.loadImage(Steve.STEVE_IMAGE);                //steve has shields but it will be reverted to normal upon collison
				steve.setShield(false);
				System.out.println(steve.getName() + " Current Health: "  + steve.getHealth());
			}
			this.vanish();
			if(steve.getHealth() <= 0){
				steve.die(); 
				System.out.println(steve.getName() + " died.");         //If the player's health is 0, steve died
			}
		}
	}
	
	private void getHit(int damage){      //Method for checking if the blaze got hit
		blazeMediaPlayer.setVolume(0.1);
		blazeMediaPlayer.play();
		this.die();
	}
	
	public boolean isAlive() {  //Method for cheking if it is alive
		return this.alive;
	}
	
	public void die(){                 //method for powerup drops and setting the blaze to vanish
		blazeMediaPlayer.setVolume(0.1);
		blazeMediaPlayer.play();
		int type;
		Item newPowerups;
		Random r = new Random();

		type = r.nextInt(Blaze.ITEM_FREQUENCY);
		switch(type){
			case 10: newPowerups = new Heart (this.xPos,this.yPos); break;
			case 20: newPowerups = new Shield(this.xPos,this.yPos); break;
			default: newPowerups = new Emerald(this.xPos,this.yPos); break;			
		} 		
		this.powerups.add(newPowerups);
		this.alive = false;
		this.vanish();
		
    }
	
	void setDamage(int newDamage){  //Set the damage of the blaze upon collision
		this.damage = newDamage;
	}
	
}
