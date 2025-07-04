/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Arrow extends Sprite {
	protected int type;
	private int damage;
	
	private final static double ARROW_SPEED = 8;      //Speed of the arrow
	private final static int NORMAL_ARROW_DAMAGE = 10;             //Damage for both of the arrow
	private final static int UPGRADED_ARROW_DAMAGE = 25;
	
	private final static Image NORMAL_ARROW_IMAGE = new Image("sprite/arrow.png",40,20,false,false);            //Normal arrow
	private final static Image UPGRADED_ARROW_IMAGE = new Image("sprite/upgarrow.gif",40,20,false,false);           //Upgraded arrow
	 
	public final static int NORMAL_ARROW = 0;
	public final static int UPGRADED_ARROW = 1;
	
	private static final String BOW_SOUND = "src/soundeff/bowshot.mp3";
	private MediaPlayer bowMediaPlayer;
	
	Arrow(int type, double x, double y){
		super(x,y,type==Arrow.NORMAL_ARROW ?Arrow.NORMAL_ARROW_IMAGE: Arrow.UPGRADED_ARROW_IMAGE);   //Assign the position and the image going to be used
		this.type = type;
		this.damage = this.type==Arrow.NORMAL_ARROW ?Arrow.NORMAL_ARROW_DAMAGE:Arrow.UPGRADED_ARROW_DAMAGE;       //Assign the damage depending on the type of the arrow
		
        Media bowSound = new Media(new File(BOW_SOUND).toURI().toString());
        bowMediaPlayer = new MediaPlayer(bowSound);              //Bow sound for every fire
	}
	
    int getDamage(){
    	return this.damage;
    }
    
	void move(){                //Moves the arrow from left to right starting from the player sprite
		bowMediaPlayer.setVolume(0.5);
		bowMediaPlayer.play();
		this.xPos += Arrow.ARROW_SPEED;
		if(this.xPos >= 1200){				
			this.vanish();           //Vanish it if it passess the screen
		}
	}
}
