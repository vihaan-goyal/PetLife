package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	GamePanel gp;

	Font arial_20, arial_40, arial_80b;
	BufferedImage keyImage;

	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;

	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#.##");

	public UI(GamePanel gp) {

		this.gp = gp;

		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_20 = new Font("Segoe UI", Font.PLAIN, 20);
		arial_80b = new Font("Arial", Font.BOLD, 80);

		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {

		// TITLE SCREEN
		if(gp.gameState == gp.TITLE_STATE){

			// background
			g2.setColor(new Color(30,30,30));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

			g2.setColor(Color.white);

			// title
			g2.setFont(arial_80b);

			String title = "Tung Tung Pet";
			int titleX = gp.screenWidth/2 - g2.getFontMetrics().stringWidth(title)/2;
			g2.drawString(title, titleX, 120);

			// subtitle
			g2.setFont(arial_40);

			String choose = "Choose Your Pet";
			int chooseX = gp.screenWidth/2 - g2.getFontMetrics().stringWidth(choose)/2;
			g2.drawString(choose, chooseX, 220);

			// options
			g2.drawString("1 - Dog (needs lots of love)", chooseX-50, 320);
			g2.drawString("2 - Cat (moody, hungry often)", chooseX-50, 370);
			g2.drawString("3 - Koala (sleepy, rests often)", chooseX-50, 420);

			g2.setFont(arial_20);
			g2.drawString("Press 1, 2, or 3 to start", 280, 500);

			g2.setFont(arial_20);

			g2.drawString("Name your pet:", 280, 480);
			g2.drawRect(280, 500, 200, 30);
			g2.drawString(gp.petNameInput, 290, 523);

			return;
		}

		// GAMEPLAY UI
		else {

			g2.setColor(Color.WHITE);
			g2.setFont(arial_20);

			// time display
			int displayHour = gp.hour % 12;
			if(displayHour == 0) displayHour = 12;

			String period = (gp.hour < 12) ? "AM" : "PM";
			String formattedTime = String.format("%02d:%02d %s", displayHour, gp.minute, period);

			g2.drawString("Day: " + gp.currentDay + " / " + gp.MAX_DAYS, gp.screenWidth-150, 40);
			g2.drawString("Time: " + formattedTime, gp.screenWidth-150, 70);

			// money
			g2.drawString("Money: $" + gp.money, 20, 40);

			// pet stats (only if pet exists)
			if(gp.pet != null){

				g2.drawString("Hunger: " + gp.pet.hunger, 20, 70);
				g2.drawString("Happiness: " + gp.pet.happiness, 20, 100);
				g2.drawString("Energy: " + gp.pet.energy, 20, 130);
			}

			// controls
			g2.drawString("F = Feed ($5)", 20, 200);
			g2.drawString("P = Play ($10)", 20, 230);
			g2.drawString("R = Rest (Free)", 20, 260);
		}
	}
}