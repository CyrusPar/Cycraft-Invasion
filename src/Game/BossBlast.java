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
import java.io.File;


public class BossBlast extends Arrow {
	
	
	private final static int BOSS_PROJECTILE_HEIGHT = 40;   //Attributes and descripts
	private final static int BOSS_PROJECTILE_WIDTH = 40;
	private final static double BOSS_PROJECTILE_SPEED = 1.8;
	private final static Image BOSS_PROJECTILE_IMAGE = new Image("sprite/enderproj.png",BOSS_PROJECTILE_WIDTH,BOSS_PROJECTILE_HEIGHT,false,false);  //Image
	private final static int BOSS_PROJECTILE_DAMAGE = 50;
	private int Gdamage;
	
	private static final String BOSS_SOUND = "src/soundeff/enderdragon.mp3";
	private MediaPlayer enderdragonMediaPlayer;
	

	
	
	BossBlast(int type, double x, double y) {   //boss blast constructor
		super(type,x,y); // method overloading
		this.Gdamage = BOSS_PROJECTILE_DAMAGE;
		this.loadImage(BOSS_PROJECTILE_IMAGE);
		
        Media enderdragonSound = new Media(new File(BOSS_SOUND).toURI().toString());
        enderdragonMediaPlayer = new MediaPlayer(enderdragonSound);
	}
	
   
	
	void move(){   //Method for moving the boss blast to the game scene
		this.xPos -= BossBlast.BOSS_PROJECTILE_SPEED;
		if (this.xPos <= 0) {  // if this arrow passes through the left of the scene, set visible to false
			enderdragonMediaPlayer.setVolume(0.5);
			enderdragonMediaPlayer.play();
			this.vanish();
		}
	}
	
    int getDamage(){   //Get the damage of the projectile
    	return this.Gdamage;
    }
}
