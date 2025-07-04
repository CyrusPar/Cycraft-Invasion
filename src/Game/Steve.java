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
import javafx.scene.media.MediaPlayer;

public class Steve extends Sprite {
	private String name;
	private boolean shielded;
	private boolean alive;
	private boolean portal;
	private ArrayList<Arrow> arrows;//Steve uses a bow to fire, we need an array of arrows for multiple firing
	private int arrowtype;
	private int speed;
	private int score;  //Steve's attribute
	private int health;
	
	final static Image STEVE_IMAGE = new Image("sprite/steve1.gif",150,150,true,true);
	final static Image UPGRADE_STEVE_IMAGE = new Image("sprite/steve2.gif",150,150,true,true);   //Steve normal and upgraded images
	private final static double Initial_x = 60;                     
	private final static double Initial_y = 60;
	private static int steve_speed = 3;
	
	static final String STEVE_SOUND = "src/soundeff/steve.mp3";
	
	Steve(String name){
		super(Steve.Initial_x,Steve.Initial_y,Steve.STEVE_IMAGE);
		this.name = name;
		this.alive = true;
		this.portal = false;
		this.arrows = new ArrayList<Arrow>(); 
		this.arrowtype = 0;
		this.speed = steve_speed;
		this.shielded = false;
		this.health = 300;
	}
	
	String getName(){   //method for getting the name of the sprite steve
		return this.name;    
	}
	
	boolean isAlive(){          //Check if steve is alive
		if(this.alive) return true;
		return false;
	}
	
	void entersPortal() {           //Check's if steve has enter the portal
		this.portal = true;
	}

	
    void die(){                  //Method to check if steve has died from the game
    	this.alive = false;
    }
    
    
    
	int getSpeed(){                //Get the speed of steve
		return this.speed;
	}

	int getHealth(){      //Gets the health
		return this.health;
	}
	
	boolean getShield(){       //Assign steve to be invinsible 
		return this.shielded;
	}
    

	
	void setShield(boolean s){ 
		this.shielded = s;
	}
	
	void downgradearrowType(){  //Reduce the state of the arrow to normla
		this.arrowtype = 0;
	}
	
	void upgradearrowType(){    //Upgrades the arrow
		this.arrowtype = 1; 
	}
	
	void addHealth(int addedHealth){               //Add health everytime steve gets a heart or kills a guard
		this.health = this.health + addedHealth;
	}

    
	ArrayList<Arrow> getArrows() {          //Array of arrows
		return this.arrows;
	}
    
	int getScore(){             //Gets steve score from kills and mob drops
		return this.score;
	}
	
    void gainScore(int increase){              //increases score and prints it in the terminal
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }
	
    void move() {               //Method for moving steve across the screen
    	if(this.xPos+this.dx >= 0 && this.xPos+this.dx <= MainGameStage.GWINDOW_WIDTH-this.width)
			this.xPos += this.dx;
    	if(this.yPos+this.dx >= 10 && this.yPos+this.dy <= MainGameStage.GWINDOW_HEIGHT-this.height)
			this.yPos += this.dy;
	}
    
	void shoot(){        //Method for shooting arrows
        this.arrows.add(new Arrow(this.arrowtype,this.xPos+130,this.yPos+95)); 
      
	}
	
}
