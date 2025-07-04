/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.scene.image.Image;

public class Shield extends Item {               //Powerup
	
	private final static int SHIELD_SPEED = 2;
	private final static Image SHIELD_IMAGE = new Image("images/totem.png",POWERUP_WIDTH,POWERUP_WIDTH,true,true);
	private final static int GAIN = 1;

	Shield(double x, double y){
		super(x,y,Shield.SHIELD_IMAGE);
		this.speed = Shield.SHIELD_SPEED;
		
	}

	@Override
	void checkCollision(Steve steve){        //When receive, steve's gain immortality and upgraded arrows(untiil no collision happens among projectile and mobs)
		if(this.collidesWith(steve)){
			System.out.println(steve.getName() + " has collected an totem!");
			System.out.println(steve.getName() + " is immortal, avoid collision or getting damage");
			this.vanish();
			steve.gainScore(Shield.GAIN);
			steve.setShield(true);                             //Immortality
			steve.loadImage(Steve.UPGRADE_STEVE_IMAGE); //Change the image of steve
			steve.upgradearrowType();                    //Upgrades the arrow
		}
	}
	
	
	
}
