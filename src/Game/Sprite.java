/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public abstract class Sprite {           //Parent Class of all sprited
	protected Image img;
	protected double xPos, yPos, dx, dy;   // attributes includes the image, position, and visibility (alive)
	protected boolean visible;
	protected double width;
	protected double height;

    public Sprite(double xPos, double yPos, Image image){
		this.xPos = xPos;
		this.yPos = yPos;
		this.loadImage(image);
		this.visible = true;
	}
    
	protected void loadImage(Image image){     //Loads the image of the sprite
		try{
			this.img = image;
	        this.setSize();
		} catch(Exception e)	{
			e.printStackTrace();
		}
	}
	
	private void setSize(){                //Set's the size of the sprite
		this.width = this.img.getWidth();
        this.height = this.img.getHeight();
	}
	
	public void render(GraphicsContext gc){                       //render or draw the image on the scene
        gc.drawImage( this.img, this.xPos, this.yPos );
    }
	
	protected boolean collidesWith(Sprite rect2)	{            //Check if a certain image or objects collides withe the boundaries of sprite
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	
	private Rectangle2D getBounds(){                                                 //Boundary of the sprite in terms of images, will be use for collision checking
		return new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}
	
	public Image getImage(){ //Gets the image
		return this.img;
	}
	
	public double getXPos(){  //gets the xposition on the scene
		return this.xPos;
	}

	public double getYPos(){   //Gets the yposition on the scene
		return this.yPos;
	}
	
	public double getDX(){
		return this.dx;
	}
	
	public double getDY(){
		return this.dy;
	}
	
	public void setDX(int dx){
		this.dx = dx;
	}

	public void setDY(int dy){
		this.dy = dy;
	}
	
	public void setWidth(double w){
		this.width = w;
	}

	public void setHeight(double h){
		this.height = h;
	}
	
	
	public void vanish(){                //Removes the sprite from being drawn or remove it
		this.visible = false;
	}
	
	public boolean isVisible(){            //Method for checking if the sprite is still on the scene
		if(visible) return true;
		return false;
	}
}
