package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0;
	
	int standCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH){
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize / 2);
		screenY = gp.screenHeight/2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void getPlayerImage() {
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
	}
	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResource("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void setDefaultValues(){
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void Update() {
		if(keyH.upPressed == true ||
		   keyH.downPressed == true ||
		   keyH.leftPressed == true ||
		   keyH.rightPressed == true) {
			if(keyH.upPressed == true){
				direction = "up";
			}
			if(keyH.downPressed == true){
				direction = "down";
			}
			if(keyH.leftPressed == true){
				direction = "left";
			}
			if(keyH.rightPressed == true){
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objectIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objectIndex);
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			spriteCounter++;
			
			if(spriteCounter > 12){
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			standCounter++;
			
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch (objectName) {
			case "Key":
				gp.playSoundEffect(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSoundEffect(3);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("You opened the door!");
				} else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSoundEffect(4);
				break;
			case "Boots":
				gp.playSoundEffect(2);
				speed += 1;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			}
		}
	}
	
	public void Draw(Graphics2D g2) {
		BufferedImage image = null;
		switch (direction) {
		case "up":
			switch(spriteNum) {
			case 1:
				image = up1;
				break;
			case 2:
				image = up2;
				break;
			}
			break;
		case "down":
			switch(spriteNum) {
			case 1:
				image = down1;
				break;
			case 2:
				image = down2;
				break;
			}
			break;
		case "left":
			switch(spriteNum) {
			case 1:
				image = left1;
				break;
			case 2:
				image = left2;
				break;
			}
			break;
		case "right":
			switch(spriteNum) {
			case 1:
				image = right1;
				break;
			case 2:
				image = right2;
				break;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}