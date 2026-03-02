package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Pet {

    GamePanel gp;

    public String name;
    public String type;

    public int hunger = 100;
    public int happiness = 100;
    public int health = 100;
    public int energy = 100;

    public int totalExpenses = 0;

    private BufferedImage happySprite;
    private BufferedImage sadSprite;
    private BufferedImage sickSprite;

    private BufferedImage currentSprite;

    public final int screenX;
    public final int screenY;

    public Pet(GamePanel gp, String name, String type) {

        this.gp = gp;
        this.name = name;
        this.type = type;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 + 60;

        loadImages();
        updateMoodSprite();
    }

    private void loadImages() {
        try {
            happySprite = ImageIO.read(new File("res/pet/happy.png"));
            sadSprite = ImageIO.read(new File("res/pet/sad.png"));
            sickSprite = ImageIO.read(new File("res/pet/sick.png"));
        } catch (Exception e) {
            System.out.println("Error loading pet images.");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (currentSprite != null) {
            g2.drawImage(currentSprite, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void updateMoodSprite() {

        if (health < 40) {
            currentSprite = sickSprite;
        }
        else if (happiness < 40) {
            currentSprite = sadSprite;
        }
        else {
            currentSprite = happySprite;
        }
    }

    public void checkHealth() {

        if (hunger <= 20) {
            health -= 10;
        }

        if (happiness <= 20) {
            health -= 5;
        }

        if (energy <= 10) {
            health -= 5;
        }

        if (health < 0) health = 0;

        updateMoodSprite();
    }

    public void feed() {
        hunger += 20;
        happiness += 5;
        totalExpenses += 5;
        clampStats();
        updateMoodSprite();
    }

    public void play() {
        happiness += 15;
        energy -= 10;
        totalExpenses += 10;
        clampStats();
        updateMoodSprite();
    }

    public void rest() {
        energy += 20;
        clampStats();
        updateMoodSprite();
    }

    private void clampStats() {
        hunger = Math.max(0, Math.min(100, hunger));
        happiness = Math.max(0, Math.min(100, happiness));
        health = Math.max(0, Math.min(100, health));
        energy = Math.max(0, Math.min(100, energy));
    }
}