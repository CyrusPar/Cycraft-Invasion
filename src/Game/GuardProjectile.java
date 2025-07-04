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

public class GuardProjectile extends Arrow {
	
	
	private final static int GUARD_PROJECTILE_HEIGHT = 40;
	private final static int GUARD_PROJECTILE_WIDTH = 40;
	private final static double GUARD_PROJECTILE_SPEED = 1.5;
	private final static Image GUARD_PROJECTILE_IMAGE = new Image("sprite/fireball.png",GUARD_PROJECTILE_WIDTH,GUARD_PROJECTILE_HEIGHT,false,false);
	private final static int GUARD_PROJECTILE_DAMAGE = 30;
	private static final String GUARD_SOUND = "src/soundeff/ghastfire.mp3";
	private MediaPlayer ghastMediaPlayer;
	private int damage;
	
	
	// Constructor 
	GuardProjectile(int type, double x, double y) {
		super(type,x,y); 
		this.loadImage(GUARD_PROJECTILE_IMAGE);
		this.damage = GUARD_PROJECTILE_DAMAGE;
        Media ghastSound = new Media(new File(GUARD_SOUND).toURI().toString());
        ghastMediaPlayer = new MediaPlayer(ghastSound);
	}
	

	// The GuardProjectile moves towards the left side of the scene. If it goes beyond the left boundary, it becomes invisible.
	void move(){
		ghastMediaPlayer.setVolume(0.5);
		ghastMediaPlayer.play();
		this.xPos -= GuardProjectile.GUARD_PROJECTILE_SPEED;
		if (this.xPos <= 0) {  // if this arrow passes through the left of the scene, set visible to false
			this.vanish();
		}
	}
	
    int getDamage(){               //Get the damage of the projectile
    	return this.damage;
    }
}
