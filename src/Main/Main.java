/*******************************************************
* Cyrus Gello M. Par
* CMSC 22 -YZ4L
* 2022 - 12741
*
*GAME PROJECT IN CMSC 22
*
*
*References:
*"CMSC 22 A.Y. 2023-2024 Assets. (2023). Everwing-FX [Source Code]
“CMSC 22 2023-2024 ASSETS. (2023) AR1L_Tandang9_miniproject [Source Code]
Images References:
Wiki, C. T. M. (n.d.). Ender Dragon. Minecraft Wiki. https://minecraft.fandom.com/wiki/Ender_Dragon

Wiki, C. T. M. (n.d.). Ghast. Minecraft Wiki. https://minecraft.fandom.com/wiki/Ghast

Blaze. (n.d.). Minecraft Wiki. https://minecraft.wiki/w/Blaze

r/PixelArt - Steve 16x16. (n.d.). Reddit. https://www.reddit.com/r/PixelArt/comments/gk8oea/steve_16x16/

r/Minecraft - A pixel art I made for the latest snapshot ! (n.d.). Reddit. https://www.reddit.com/r/Minecraft/comments/g3t5bu/a_pixel_art_i_made_for_the_latest_snapshot/
Nether was. pixel art. Full credits to u/ yokcos700. Pixel art background, Pixel art landscape, HD phone wallpaper | Peakpx. (n.d.). https://www.peakpx.com/en/hd-wallpaper-desktop-euabx

Wiki, C. T. M. (n.d.). Enderman. Minecraft Wiki. https://minecraft.fandom.com/wiki/Enderman

Music References:

Bardify. (2022, September 1). Epic Fight | D&D/TTRPG Battle/Combat/Fight Music | 1 Hour [Video]. YouTube. https://www.youtube.com/watch?v=H8n7K3jABhI

C418 - Topic. (2019, October 3). Minecraft [Video]. YouTube. https://www.youtube.com/watch?v=XuZDeT8zI5c
*******************************************************/
package Main;

import Game.MainGameStage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MainGameStage newgame = new MainGameStage();
		newgame.setStage(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
