package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	public BufferedImage image;
	public String name;
	public String description;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public boolean flipX = false;
	public boolean flipY = false;

	UtilityTool uTool = new UtilityTool(); 
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			int width = gp.tileSize;
			int height = gp.tileSize;

			int drawX = screenX;
			int drawY = screenY;

			if(flipX){
				drawX = screenX + gp.tileSize;
				width = -gp.tileSize;
			}

			if(flipY){
				drawY = screenY + gp.tileSize;
				height = -gp.tileSize;
			}

			g2.drawImage(image, drawX, drawY, width, height, null);
		}

	}
}