/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Random;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


import java.util.Iterator;

public class GameTimer extends AnimationTimer {
	
	private GraphicsContext gc;
	private Steve steve;
	private Scene scene;
	private Guard ghast;
	private Portal portal;
	private Boss edragon;

	private long startBlazeSpawn;
	private long startEnderManSpawn;
	private long Time;
	
	
	private boolean successfulBossKill;
	private boolean entersportal;
	private boolean successfulGuardKill;
	private int enemiesKilled;
	
	private int spawnsBlazeCounter;
	private int spawnsEnderManCounter;
	private int gCounter;
	private int bossdelay;

	
	private long BossShootTimer;
	private long GuardShootTimer;
	
	public final static int MIN_MONSTERS = 3;
	public final static int MAX_MONSTERS = 5;
	public final static double SPAWN_DELAY = 5;
	private final static int SPAWN_GUARD_DELAY = 5;
	
	
	private ArrayList<Blaze> blazes;
	private ArrayList <EnderMan> enderman;   //Array of monster for both the munions of every realm
	private ArrayList<Item> powerups;
	
	private double backgroundX;
	private Image tryc = new Image("images/entrywarning.png",1100,100,true,true);
	private Image background1 = new Image("images/normalworld.png");
	private Image background = new Image( "images/netherrealm.jpg" );                       //Background images that will be used in the game
	private Image background2 = new Image( "images/endrealm.jpg" );
	
	private Image win = new Image("images/win.png");                   //Gameover images for winning and losing
	private Image lose = new Image("images/lost.png");
	
	public final static int BACKGROUND_SPEED = 2;
	
	private final static int INITIAL_GUARD_XPOS = MainGameStage.GWINDOW_WIDTH-150;        //Initial position of the Guard
	private final static int INITIAL_GUARD_YPOS = MainGameStage.GWINDOW_HEIGHT-300;
	

	private final static int INITIAL_BOSS_XPOS = MainGameStage.GWINDOW_WIDTH;             //Initial position of the boss
	private final static int INITIAL_BOSS_YPOS = MainGameStage.GWINDOW_HEIGHT-100;
	
	
	

	
	GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;                         //Branch nodes by creating a group for the scene
		this.scene = theScene;
		this.steve = new Steve("Steve");       //Instantiate the main character 
		this.startBlazeSpawn = this.Time = System.nanoTime();
		
		this.blazes = new ArrayList<Blaze>();
		this.enderman = new ArrayList<EnderMan>(); //Array list for both the enderman,blaze and powerups
		this.powerups = new ArrayList<Item>();
		this.enemiesKilled = 0;           //To check the number of enemies steve killed
		this.spawnsBlazeCounter = 0;
		this.spawnsEnderManCounter = 0;       //Counter for both the enderman and blaze, since there is a limit on how many monsters can pop up on the screen for easier gameplay
		this.bossdelay = 0;
		
		this.successfulBossKill = false;         //Initialize to false, inficating that the gaurd or boss is still alive, will be used in certain conditions
		this.successfulGuardKill = false;
		this.entersportal = false;
		
		this.ghast = new Guard(INITIAL_GUARD_XPOS,INITIAL_GUARD_YPOS,false);
		this.edragon = new Boss(INITIAL_BOSS_XPOS,INITIAL_BOSS_YPOS,false);            //Initializes both the boss and the guard and the portal from the start of the game
		this.portal = new Portal(INITIAL_GUARD_XPOS,INITIAL_GUARD_YPOS,false);
		
		this.handleKeyPressEvent();   //Handles key press events
	}
	
		public void handle(long currentNanoTime) {
			this.gc.clearRect(0, 0, MainGameStage.GWINDOW_WIDTH,MainGameStage.GWINDOW_HEIGHT);  //Clears the canvas when the game starts
			this.gc.drawImage( background1,this.backgroundX,0);             //Draw the background
			this.gc.drawImage( tryc,50,30);
			this.steve.move(); 
			this.steve.render(this.gc);                                 //Render and move steve and its arrows at the start of the game so thje player can check the movements
			this.renderArrows();		
         	
			if(this.entersportal== false) {		  //Check if it collides with the portalon the screen to indicate that the game is about to begin
			if(this.portal.getInitY() == false) {
				this.portal.setY(MainGameStage.GWINDOW_HEIGHT-500);    //Sets the position of the game at the screen
				this.portal.setX(600);
				this.portal.setInitY(true); 
				this.portal.setInitX(true);
				this.portal.setAlive(true);                  //Set alive similar to other mobs
			} 
			if (this.portal.isAlive()) {
				this.portal.render(this.gc);  //Renders or draw the portal on the screen for visibility
			} 
				this.Portalentry();                                         //Method for chekcing if it enters the portal
			if ((this.portal.isAlive() == false) && this.entersportal == false) { //If it does set alive to false and change the portal entry to true
					this.entersportal = true;
			}
			
			}else {
			Timer(currentNanoTime);            //Get a timer for the game counter

		
			
			if(this.successfulGuardKill == false) {        //Check if the guard is still alive
				this.redrawBackgroundImage(background);       //Redraw the background again for the nether realm
				this.drawScore();                             //Draws the score of the player on the upper left of the screen
				this.spawnBlaze(currentNanoTime);
				this.moveBlaze();
				this.renderBlaze();                             //Spawns and render the blazes and move it alongside with its powerup upon death
				this.renderPowerUps();
				this.movePowerups();
			}
			else if(this.successfulGuardKill == true) {  //If the guard is not alive, render another background for the nether realm
				this.redrawBackgroundImage(background2);
				this.drawScore();                       //Draws the score of the player on the upper left of the screen
				this.spawnEnderMan(currentNanoTime);
				this.moveEnderMan();
				this.renderEnderMan();
				this.renderPowerUps();                      //Spawns and render the enderman for the end realm and move it alongside with its powerup upon death
				this.movePowerups();
			}
			this.steve.render(this.gc);                  //Since both required clearing the canvas for changing backgrounds, it is important to draw or render the player or the arrow again for visibility
		    this.renderArrows();
			
		    if(gCounter == SPAWN_GUARD_DELAY) {                    //The delay before the guard spawns in the game
				Random r = new Random();
				if(this.ghast.getInitY() == false) {
					this.ghast.setY(r.nextInt(MainGameStage.GWINDOW_HEIGHT-100));
					this.ghast.setInitY(true);
					this.ghast.setAlive(true);
				}
			}
			
			if (gCounter >= SPAWN_GUARD_DELAY){                  //If the game counter is greater than that of the delay, the boss will appear and render in the screen
				if (this.ghast.isAlive()) {
					this.ghast.render(this.gc);
					this.ghastfire(currentNanoTime);                  //Let the ghast fire

				this.moveGuard();                                //Calls the method for moving the ghast
				}
				if ((this.ghast.isAlive() == false) && this.successfulGuardKill == false) {    //Check if the ghast is still alive 
					this.successfulGuardKill = true;
					this.enemiesKilled++;                              //Change the ghast killed to true, add enemies killed
					this.bossdelay = gCounter + 7;                         //Add a 7 second to the game counter for the boss spawn after travelling to the end
					System.out.println("Successfully killed the ghast. Enemies killed: " + this.enemiesKilled);    //Prints the guard killed prompt
					steve.addHealth(100);
					steve.setShield(true);
					steve.loadImage(Steve.UPGRADE_STEVE_IMAGE);              //Upgrade the steve as a bonus before entering the end realm
					steve.upgradearrowType();
					System.out.println("Bonus 100 Health and shield as a reward");                 //Bonus 100 additional health for the death of the guard
					steve.gainScore(70);                                                           //70 points bonus
					System.out.println("Approaching the end realm......Be ready");
									
				}
			}
			
			if(gCounter == bossdelay) {                         //If the counter is similar to the boss delay time
				Random r = new Random();
				if(this.edragon.getInitY() == false) {
					this.edragon.setY(r.nextInt(MainGameStage.GWINDOW_HEIGHT-100));           //Sets the position of the ender dragon on the end of the screen
					this.edragon.setInitY(true);
					this.edragon.setAlive(true);                                      //Sets the position and alive to true for the ender dragon
				}
			}
			
			if (gCounter >= bossdelay && this.successfulGuardKill == true){           //If it is greater than the game timer and the guard is truly kill
				if (this.edragon.isAlive()) {
					this.edragon.render(this.gc);                            //Render the ender dragon
					this.edragonfire(currentNanoTime);                        //Shoot

				this.moveBoss();                                  //Move the ender man
				}
				if ((this.edragon.isAlive() == false) && this.successfulBossKill == false) {
					this.successfulBossKill = true;              //Set the boss kiled to true
					this.enemiesKilled++;                    //Add health kills
					System.out.println("Successfully killed the Ender Dragon . Enemies killed: " + this.enemiesKilled);
					steve.gainScore(500);			                                                                        //Gain a bonus 500 score
				}
			}
			
			
			
			
			
			
			

	        if(!this.steve.isAlive()) { // checks if steve is still alive 
	        	this.stop();
	        	this.flashGameOver(lose);       //steve dies without finishing the game
	        }
	        
	        if(this.steve.isAlive() && this.successfulBossKill == true) { //Steve is allived and finished the game
	        	this.stop();
	        	this.flashGameOver(win);
	        }
			}
		}
		
		private void flashGameOver(Image background) {
		    this.gc.clearRect(0, 0, MainGameStage.GWINDOW_WIDTH, MainGameStage.GWINDOW_HEIGHT);  		    // Clear the canvas
		    this.gc.drawImage(background, 0, 0);
		    this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));       //Draws the enemies killed on the screen
		    this.gc.setFill(Color.WHITE);
		    this.gc.fillText("ENEMIES KILLED: "+enemiesKilled, MainGameStage.GWINDOW_WIDTH / 2 - 150 , MainGameStage.GWINDOW_HEIGHT / 2 + 25);
		    this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));                  //Draws the player's score
		    this.gc.fillText("SCORE: " + steve.getScore(), MainGameStage.GWINDOW_WIDTH / 2 - 75 , MainGameStage.GWINDOW_HEIGHT / 2 + 75);
		}
		
	    void redrawBackgroundImage(Image background) {
			// clear the canvas
	        this.gc.clearRect(0, 0, MainGameStage.GWINDOW_WIDTH,MainGameStage.GWINDOW_HEIGHT);

	        // redraw background image (moving effect)
	        this.backgroundX += GameTimer.BACKGROUND_SPEED;

	        this.gc.drawImage( background, this.backgroundX-this.background.getWidth(),0);
	        this.gc.drawImage( background, this.backgroundX,0);
	        
	        if(this.backgroundX>=MainGameStage.GWINDOW_WIDTH) 
	        	this.backgroundX = MainGameStage.GWINDOW_WIDTH-this.background.getWidth();
	    }
	    
	    private void Timer(long currentNanotime) {                   //Method for getting to know the seconds the timer is running
	    	if(((currentNanotime - this.Time)/1000000000.0)>= 1){;        //convert nanoseconds to seconds
	    	this.gCounter++;                                   //Counter for every second elapsed
	    	this.Time = System.nanoTime();
	    	}
	    }
	    
		private void handleKeyPressEvent() {     //Method for Handle key press even and set on released
			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
	            	KeyCode move = e.getCode();
	                moveSteve(move);            //Calls the method for moving steve
				}

			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			    public void handle(KeyEvent e){
			        KeyCode stop = e.getCode();
			        stopSteve(stop);                  //Calls the method for stopping steve
			    }
			});
	    }
		
		private void moveSteve(KeyCode ke) {           //Method for moving the character up and down and left and right
			if(ke==KeyCode.UP)                             
				this.steve.setDY(-steve.getSpeed()); 

			if(ke==KeyCode.LEFT) 
				this.steve.setDX(-steve.getSpeed());
                                                                  //Traverse or let the player move the character/sprite using arrow keys
			if(ke==KeyCode.DOWN)                                  //Decrease the DX and DY position depending on where the player want to go
				this.steve.setDY(steve.getSpeed());

			if(ke==KeyCode.RIGHT)       
				this.steve.setDX(steve.getSpeed());

			if(ke==KeyCode.SPACE) 
				this.steve.shoot();
			
			
	   	}

		private void stopSteve(KeyCode ke) {  //Method for stopping the movement of the player
	        if (ke == KeyCode.UP) 
	        	steve.setDY(0);

	        if (ke == KeyCode.LEFT)  
	        	steve.setDX(0);                           //If the player has release the keys for movement, it is important that the character stops and not move on his own

	       if (ke == KeyCode.DOWN) 
	    	   steve.setDY(0);

	        if (ke == KeyCode.RIGHT) 
	        	steve.setDX(0);
	    }
		
		private void spawnBlaze(long currentNanoTime){
			double spawnElapsedTime = (currentNanoTime - this.startBlazeSpawn) / 1000000000.0; // convert nanoseconds to seconds
			if (this.spawnsBlazeCounter == 0){ // checks the counter for minimum blazes allowed
			   	this.generateBlazes(MAX_MONSTERS); // generate blazes
			   	this.spawnsBlazeCounter++;                       // increments counter of spawned blaze
			} else if (spawnElapsedTime > GameTimer.SPAWN_DELAY) { // checks for spawn delay, it is important to take note that it is not advisable to spawn every second
		        this.generateBlazes(MIN_MONSTERS); 
		        this.startBlazeSpawn = System.nanoTime(); // resets blaze spawn timer to its nanoTime (0 to compare again until 5)
		        this.spawnsBlazeCounter++;                
		    }
		}
		
		private void spawnEnderMan(long currentNanoTime){          //same code explanation from the blaze spawn
			double spawnElapsedTime = (currentNanoTime - this.startEnderManSpawn) / 1000000000.0; 
			if (this.spawnsEnderManCounter == 0){ 
			   	this.generateEnderMan(MAX_MONSTERS); 
			   	this.spawnsEnderManCounter++; 
			} else if (spawnElapsedTime > GameTimer.SPAWN_DELAY) { 
		        this.generateEnderMan(MIN_MONSTERS); 
		        this.startEnderManSpawn = System.nanoTime(); 
		        this.spawnsEnderManCounter++;
		    }
		}
		
		
		private void generateBlazes(int numberOfBlaze){ // Method for Instantiates minimum slimes from different locations on the scene
			Random r = new Random(); // randomize number for speed in y
			for (int i=0; i < numberOfBlaze;i++) { // loops through each instance of blaze
				int x = MainGameStage.GWINDOW_WIDTH-50;
				int y = r.nextInt(MainGameStage.GWINDOW_HEIGHT-70);
				this.blazes.add(new Blaze(x, 10+y,this.powerups)); // adds an instance of blaze in the array list of blaze
			}
		}
		
		private void generateEnderMan(int numberOfEnderMan){  //Same explanation for generate blaze
			Random r = new Random(); 
			for (int i=0; i < numberOfEnderMan;i++) { 
				int x = MainGameStage.GWINDOW_WIDTH-50;
				int y = r.nextInt(MainGameStage.GWINDOW_HEIGHT-70);
				this.enderman.add(new EnderMan(x,10+y,this.powerups)); 
			}
		}
		
		private void moveBlaze(){                           //Method for calling to move each blazes from the array
			for(int i = 0; i < this.blazes.size(); i++){
				Blaze a = this.blazes.get(i);
				if (a.isVisible()){ // conditional statement to check if the blaze collides with steve
					a.move();
					a.checkCollision(this.steve); 
				}
				else {

					this.blazes.remove(i);      //remove the blaze from the array 
					this.enemiesKilled++;              //Increments the enemies killed
					System.out.println(steve.getName() + " destroyed a Blaze. Enemies Killed: " + this.enemiesKilled);
					steve.gainScore(5);          //Gain 5 score for every blaze killed
				}
			}
		}
		
		private void moveEnderMan(){                            //Same method for moving the enderman and the blaze
			for(int i = 0; i < this.enderman.size(); i++){
				EnderMan e = this.enderman.get(i);
				if (e.isVisible()){ 
					e.move();
					e.checkCollision(this.steve); 
				}
				else {
					this.enderman.remove(i);
					this.enemiesKilled++;
					System.out.println(steve.getName() + " destroyed an EnderMan. Enemies Killed: " + this.enemiesKilled);
					steve.gainScore(8);        //Gain 8 score for every enderman killed
				}
			}
		}
		
		private void renderBlaze() {           //Method for Rendering the sprite blaze
			for (Blaze blaze : this.blazes){
				blaze.render(this.gc);
			}
			moveBlaze();
		}
		
		private void renderEnderMan() {          //Method for Rendering the sprite ender man
			for (EnderMan enderman : this.enderman){
				enderman.render(this.gc);
			}
			moveEnderMan();
		}
		
		private void renderArrows() {    //Method for rendering the arrows or projectiles from sprites that shoots
			for (Arrow g : this.steve.getArrows())
		       	g.render( this.gc );
			for ( GuardProjectile gg : this.ghast.getGuardProjectile())       
		       	gg.render( this.gc );
			for ( BossBlast ee : this.edragon.getBossBlast())
		       	ee.render( this.gc );
			moveArrows();

		}
		
		private void moveArrows(){                 
			ArrayList<Arrow> AList = this.steve.getArrows(); // arrow list array from steve's array list
			for(int i = 0; i < AList.size(); i++){           //Loop through every arrow and check visibility and move
				Arrow a = AList.get(i);
				if (a.isVisible())
					a.move(); // moves left to right
				else AList.remove(i); // removes arrow from array list of steve arrows (not being able to render)
			}
			
			ArrayList<GuardProjectile> GList = this.ghast.getGuardProjectile();   // guard projectile list for the guard
			for(int i = 0; i < GList.size(); i++){                            //Loop through every projectile and check visibility and move
				GuardProjectile gg = GList.get(i);
				if (gg.isVisible()) gg.move();
				else GList.remove(i);
			}
			
			ArrayList<BossBlast> EList = this.edragon.getBossBlast(); // blast projectile for the boss
			for(int i = 0; i < EList.size(); i++){        //Loop through every blast and check visibility and move
				BossBlast ee = EList.get(i);
				if (ee.isVisible()) 
					ee.move();
				else 
					EList.remove(i);
			}

		}
		
		private void ghastfire(long currentNanoTime){  //Method for shooting the projectile for the guard as considering the game timer(seconds)
			if(((currentNanoTime-this.GuardShootTimer) / 1000000000.0) >= 1.5) {
				this.ghast.shoot();
				this.GuardShootTimer = System.nanoTime();
			}

		}
		
		private void edragonfire(long currentNanoTime){   //Method for shooting the projectile for the boss as considering the game timer(seconds)
			if(((currentNanoTime-this.BossShootTimer) / 1000000000.0) >= 1.5) {
				this.edragon.shoot();
				this.BossShootTimer = System.nanoTime();
			}

		}
		
		private void moveGuard(){           //Method for checking visibility and collision as well as moving the guard
			Guard ghast = this.ghast;
			if (ghast.isVisible()){
				ghast.move();
				ghast.checkCollision(this.steve);
			}
		}
		
		private void moveBoss(){             //Method for checking visibility and collision as well as moving the boss
			Boss edragon = this.edragon;
			if (edragon.isVisible()){
				edragon.move();
				edragon.checkCollision(this.steve);
			}
		}
		
		private void Portalentry(){            //Checks collision for the portal to start the game
			Portal portal = this.portal;
			if (portal.isVisible()){
				portal.checkCollision(this.steve);
			}
		}
		
		private void renderPowerUps() {            //Render all powerups from the array
			for (Item p : this.powerups)
			    p.render( this.gc );
		}
		
		private void movePowerups(){
			for(int i = 0; i < this.powerups.size(); i++){
				Item c = this.powerups.get(i);
				if(c.isVisible()){                       //Move th epower up from left to right upon dying, check for collision and remove it from the array if it does
					c.move();
					c.checkCollision(this.steve);
				}
				else powerups.remove(i);
			}
		}
		  
		private void drawScore(){                                     //Method for drawing the Score of the player on the screeen
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.YELLOW);
			this.gc.fillText("Score:", 20, 30);                            //For the score
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText(steve.getScore()+"", 100, 30);           //Position of the text
			
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.YELLOW);
			this.gc.fillText("Health:", 20, 60);                              //For the health
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText(steve.getHealth()+"", 110, 60);
			
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.YELLOW);
			this.gc.fillText("Enemies Killed:", 20, 90);                              //For the current enemies killed
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			this.gc.setFill(Color.WHITE);
			this.gc.fillText(enemiesKilled+"", 200, 90);
		}
		
	    
	
	
	
	
}

