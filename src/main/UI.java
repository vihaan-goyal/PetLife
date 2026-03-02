package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Font arial_40, arial_80b;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#.##");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80b = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	} 
	
	public void draw(Graphics2D g2) {

	int displayHour = gp.hour % 12;
	if(displayHour == 0) displayHour = 12;

	String period = (gp.hour < 12) ? "AM" : "PM";
	String formattedTime = String.format("%02d:%02d %s", displayHour, gp.minute, period);

	g2.setColor(Color.WHITE);

	g2.drawString("Day: " + gp.currentDay + " / " + gp.MAX_DAYS, 600, 40);
	g2.drawString("Time: " + formattedTime, 600, 70);

    
    g2.drawString("Money: $" + gp.money, 20, 40);

    g2.drawString("Hunger: " + gp.pet.hunger, 20, 70);
    g2.drawString("Happiness: " + gp.pet.happiness, 20, 100);
    g2.drawString("Health: " + gp.pet.health, 20, 130);
    g2.drawString("Energy: " + gp.pet.energy, 20, 160);
    g2.drawString("Total Spent: $" + gp.pet.totalExpenses, 20, 190);

    g2.drawString("F = Feed ($5)", 20, 230);
    g2.drawString("P = Play ($10)", 20, 260);
    g2.drawString("R = Rest (Free)", 20, 290);
}
}