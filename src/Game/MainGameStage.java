/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*******************************************************/
package Game;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.nio.file.Paths;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.StackPane;

public class MainGameStage {
	private Stage stage;
	private Group root;
	private Scene scene;
	private Scene aboutscene;
	private Scene howtoplay;
	private MediaPlayer mediaPlayer;
	private Scene splashscene;
	private Canvas canvas;
	private MediaPlayer videoPlayer;
	
	public final static int WINDOW_WIDTH = 800;
	public final static int WINDOW_HEIGHT = 736;
	
	public final static int GWINDOW_WIDTH = 1200;
	public final static int GWINDOW_HEIGHT = 599;
	
	static final String BGM_MENU = "src/fightmusic.m4a"; //Music used in the game
	//static final String BGM_MENU = "src/Minecraft.mp3";
	
	private static final String CLICK_SOUND = "src/soundeff/click.mp3";
	private MediaPlayer clickMediaPlayer;
	
	private static final Image ABOUT = new Image("images/dev.gif",MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT,true,true);
	private final static String VIDEO_FILE = "src/images/howtovid.mp4";
	
	public MainGameStage() {
		this.root = new Group();
		this.scene = new Scene(root, MainGameStage.GWINDOW_WIDTH,MainGameStage.GWINDOW_HEIGHT);
		this.canvas = new Canvas(MainGameStage.GWINDOW_WIDTH,MainGameStage.GWINDOW_HEIGHT);
		
        Media clickSound = new Media(new File(CLICK_SOUND).toURI().toString());
        clickMediaPlayer = new MediaPlayer(clickSound);
        
        Media videoMedia = new Media(new File(VIDEO_FILE).toURI().toString());
        videoPlayer = new MediaPlayer(videoMedia);
	}
	
	
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle( "INVASION" );
		this.root.getChildren().addAll(this.createCanvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT),canvas);
		this.initSplash(stage);
		this.initHowtoplay(stage);
		this.initAbout(stage);
		this.stage.setScene(this.splashscene);
		this.stage.setResizable(false);
		music(BGM_MENU);
		this.stage.show();
		
		
	}
	
	void music( String music) {        //Method for playing the music
		
		Media s = new Media(Paths.get(music).toUri().toString());
	    mediaPlayer = new MediaPlayer(s);
	    mediaPlayer.setVolume(0.40);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
        mediaPlayer.play();
		
	}
	
	void setGame(Stage stage) {   //starts the game
			
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        GameTimer GTimer = new GameTimer(gc, scene);
        GTimer.start();
        stage.setScene(this.scene);
	}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        root.getChildren().addAll(this.createCanvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT),this.createVBox());
        this.splashscene = new Scene(root,MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
	}
	
	private void setMenu(Stage stage) {
        stage.setScene(splashscene);
	}
	
	private void setHowtoplay(Stage stage) {
        stage.setScene(howtoplay);
	}
	
	private void initHowtoplay(Stage stage) {
		ScrollPane root = new ScrollPane();
        this.howtoplay = new Scene(root,MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
        root.setContent(this.createHowtoplayPane());
	}
	
	private Pane createHowtoplayPane() {
		Pane howtoplay = new Pane();
        MediaView mediaView = new MediaView(videoPlayer);  //My instruction is a scroll pane, however there is a limit on the size og GIF you can use
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(2944);
        DropShadow shadow = new DropShadow();
        shadow.setColor(javafx.scene.paint.Color.BLACK);
		
		Image image = new Image("images/return.png", 150, 25, false, false); //Image for the retunr button
        ImageView imageView = new ImageView(image);
		
		Button b1 = new Button();
		b1.setGraphic(imageView);                                                                    //Button for returning to the main menu
        b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b1, imageView, shadow);
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.relocate(10, 10);
        b1.setOnAction(new EventHandler<ActionEvent>() {         //Check if the use clicks the return button
            @Override 
            public void handle(ActionEvent e) {
            	clickMediaPlayer.stop();
        		clickMediaPlayer.play();
                setMenu(stage);		// changes the scene into the game scene
            }
        });
		howtoplay.getChildren().addAll(createCanvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT), mediaView,b1);
		videoPlayer.play();
		videoPlayer.setOnEndOfMedia(() -> videoPlayer.seek(Duration.ZERO));
		return howtoplay;
		
		
		
	}
	
	private void setAbout(Stage stage) {
        stage.setScene(aboutscene);
	}
	
	private void initAbout(Stage stage) {
	    StackPane root = new StackPane(); 
	    this.aboutscene = new Scene(root,MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
	    root.getChildren().add(this.createAboutPane());
	}

	private Pane createAboutPane() {   //About the developer
		Pane about = new Pane();
		ImageView img1 = new ImageView(ABOUT);   //A gif image
        DropShadow shadow = new DropShadow();
        shadow.setColor(javafx.scene.paint.Color.BLACK);
		
		Image image = new Image("images/return.png", 200, 35, false, false);  //Return button
        ImageView imageView = new ImageView(image);
		
		Button b1 = new Button();
		b1.setGraphic(imageView);   //Create a button for returning to the splash scene
        b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b1, imageView, shadow);
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.relocate(500, 500);
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	clickMediaPlayer.stop();              //This calls are for clicking the sounds of each button
        		clickMediaPlayer.play();
                setMenu(stage);		// changes the scene into the game scene
            }
        });
		about.getChildren().addAll(createCanvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT), img1,b1);
		return about;
		
		
		
	}
	private VBox createVBox() {   //The vbox is the branch nodes that will handle all the data or images on the splash scene
		
    	VBox vbox = new VBox();
    	vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setAlignment(Pos.CENTER);                 //Images will be placxed in the center
        vbox.setSpacing(8);
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(javafx.scene.paint.Color.BLACK);
        
      
        Image title = new Image("images/title.gif", 800, 500, true, true);
        ImageView titleView = new ImageView(title);
        
        Image ng = new Image("images/newgame.png", 200, 35, false, false);
        ImageView newgame = new ImageView(ng);
        
        Image ht = new Image("images/howto.png", 200, 35, false, false);            //Assign an image thatw will replace the button for every button created
        ImageView howto = new ImageView(ht);
        
        Image optns = new Image("images/mute.png", 200, 35, false, false);
        ImageView options = new ImageView(optns);
        
        Image abt = new Image("images/about.png", 200, 35, false, false);
        ImageView about = new ImageView(abt);
        
        Image ext = new Image("images/exit.png", 200, 35, false, false);
        ImageView exit = new ImageView(ext);
        
        Button b1 = new Button();                       
        Button b2 = new Button();
        Button b3 = new Button();                       //Create 5 buttons
        Button b4 = new Button();
        Button b5 = new Button();
        
        b1.setGraphic(newgame); //set the graphics or image of the button
        b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b1, newgame, shadow);
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {             //If the user decided to start the game
            	clickMediaPlayer.stop();
        		clickMediaPlayer.play();
                setGame(stage);		// changes the scene into the game scene
            }
        });

        
        b2.setGraphic(howto);
        b2.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b2, howto, shadow);
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	clickMediaPlayer.stop();
        		clickMediaPlayer.play();
                setHowtoplay(stage);		// changes the scene into the howtoplay scene or the instructions scene
            }
        });
        
        b3.setGraphic(options);
        b3.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b3, options, shadow);
        b3.setOnMouseClicked(event -> mediaPlayer.pause());
        b3.setOnMouseReleased(event -> mediaPlayer.play());
        
        b4.setGraphic(about);
        b4.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b4, about, shadow);
        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	clickMediaPlayer.stop();
        		clickMediaPlayer.play();
                setAbout(stage);		// changes the scene into the about scene, where it shows the developer of the game
            }
        });
        
        b5.setGraphic(exit);
        b5.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        addHoverEffect(b5, exit, shadow);
        b5.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	clickMediaPlayer.stop();
        		clickMediaPlayer.play();
        		System.exit(0);		//Exit the game
            }
        });


       
        vbox.getChildren().addAll(titleView,b1,b2,b3,b4,b5);   //get all children of the branch node vbox
        
    	return vbox;
    	
	}
	
	
	private Canvas createCanvas(int width, int height){           //Creates the canvas
		Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image bg = new Image("images/backg.png");
        gc.drawImage(bg, 0, 0);                 //Draws the background at the canvas 
        return canvas;
	}
	
    private void addHoverEffect(Button button, ImageView imageView, DropShadow shadow) {     //Method for checking if the mouse cursor is on the button, which puts a border or glow effect on the button
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(shadow);                //Set the effect everytime the mouse came contact with the button
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        });
    }
    

	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
