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

public class EnderMan extends Sprite {
	private ArrayList<Item> powerups;                         //Powerups for the ender man
	public static final double MAX_ENDERM_SPEED = 1.5;
	public static final double MIN_ENDERM_SPEED = 0.5;
	public final static Image ENDERM_IMAGE = new Image("sprite/enderman.gif",EnderMan.ENDERM_WIDTH,EnderMan.ENDERM_WIDTH,true,true);  //Image
	public final static int ENDERM_WIDTH = 150;
	private final static int ITEM_FREQUENCY = 30;
	private boolean alive;
	
	private static final String BH_SOUND = "src/soundeff/enderman.mp3";
	private MediaPlayer endermanhitMediaPlayer;
	private static final String ENDERM_SOUND = "src/soundeff/endermanhit.mp3";  //Enderman Sounds
	private MediaPlayer endermanMediaPlayer;
	private MediaPlayer steveMediaPlayer;
	

	private boolean moveRight;      
	private double speed;
	private int damage;
	private final static int INITIAL_DAMAGE = 40;
	
	EnderMan(int x, int y,ArrayList<Item> powerups){  //Constructor
		super(x,y,ENDERM_IMAGE);
		this.alive = true;
		Random r = new Random();
		this.speed = r.nextDouble()*(MAX_ENDERM_SPEED-MIN_ENDERM_SPEED) + MIN_ENDERM_SPEED;
		this.moveRight = true;
		this.damage = r.nextInt(20)+INITIAL_DAMAGE;
		this.powerups = powerups;
		
        Media endermanSound = new Media(new File(ENDERM_SOUND).toURI().toString());
        endermanMediaPlayer = new MediaPlayer(endermanSound);
        Media endermanhSound = new Media(new File(BH_SOUND).toURI().toString());
        endermanhitMediaPlayer = new MediaPlayer(endermanhSound);
        Media steveSound = new Media(new File(Steve.STEVE_SOUND).toURI().toString());
        steveMediaPlayer = new MediaPlayer(steveSound);
		
	}
	
	void move(){           //Method for moving the enderman
		endermanMediaPlayer.setVolume(0.1);
		endermanMediaPlayer.play();
		if (this.moveRight){
			if (this.xPos < MainGameStage.GWINDOW_WIDTH-50){
				this.xPos += this.speed;
			}
			else{
				this.moveRight = false;
			}
		}
		if (!this.moveRight){
			if (this.xPos > 0){                      //Similar movements for blaze
				this.xPos -= this.speed;
			}
			else{
				this.moveRight = true;
			}
		}
	}
	 
	void checkCollision(Steve steve){             //Check for collision
		for	(int i = 0; i < steve.getArrows().size(); i++){  
			if (this.collidesWith(steve.getArrows().get(i))){ 
				this.getHit(steve.getArrows().get(i).getDamage());
				endermanhitMediaPlayer.setVolume(0.5);
				endermanhitMediaPlayer.play();
				steve.getArrows().get(i).vanish();
			}
		}

		if (this.collidesWith(steve)) {        //Collsiion with steve physically
			endermanMediaPlayer.setVolume(0.1);
			endermanMediaPlayer.play();
			if (steve.getShield() == false) {     //No immortality
				steve.addHealth(-this.damage);
				steveMediaPlayer.setVolume(0.5);
				steveMediaPlayer.play();
				System.out.println(steve.getName() + " Current Health: "  + steve.getHealth());
			} else{
				System.out.println(steve.getName() + " hits a enderman but did not receive damage due to immortality.");
				steve.downgradearrowType();
				steve.loadImage(Steve.STEVE_IMAGE);               //Revert steve to normal
				steve.setShield(false);
				System.out.println(steve.getName() + " Current Health: "  + steve.getHealth());
			}
			this.vanish();
			if(steve.getHealth() <= 0){
				steve.die();
				System.out.println(steve.getName() + " died.");     //Player will died if health is zero
			}
		}
	}
	
	private void getHit(int damage){         //If the enderman died
		endermanMediaPlayer.setVolume(0.1);
		endermanMediaPlayer.play();
		this.die();
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void die(){                   //Method for calling that the enderman has died
		endermanMediaPlayer.setVolume(0.1);
		endermanMediaPlayer.play();
		int type;
		Item newPowerups;
		Random r = new Random();

		type = r.nextInt(EnderMan.ITEM_FREQUENCY);
		switch(type){
			case 10: newPowerups = new Heart (this.xPos,this.yPos); break;
			case 20: newPowerups = new Shield(this.xPos,this.yPos); break;           //Powerups possibility upon death
			default: newPowerups = new Emerald(this.xPos,this.yPos); break;			
		} 		
		this.powerups.add(newPowerups);
		this.alive = false;
		this.vanish();      //Set the vanish 
    }
	
	void setDamage(int newDamage){    //Getting the damage
		this.damage = newDamage;
	}
	
}
