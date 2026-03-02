package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import entity.Pet;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSizes = 16;
    final int scale = 3;

    public final int tileSize = originalTileSizes * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	public int currentDay = 1;
	public final int MAX_DAYS = 30;
	//public int dayTimer = 0;
	//public final int FRAMES_PER_DAY = 1800; // 5 seconds at 60fps

    // FPS
    int fps = 60;

	// TIME
	public int hour = 8;
	public int minute = 0;

	private int clockTimer = 0;


    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
	public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);

    Thread gameThread;

    // GAME STATE
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
	public final int REPORT_STATE = 2;

    // ENTITY
    public Player player = new Player(this, keyH);
    public Pet pet;

    // MONEY SYSTEM
    public int money = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

	public void updateClock() {

		clockTimer++;

		if (clockTimer >= 45) {  // 30 frames = 1 in-game minute
			minute++;
			clockTimer = 0;
		}

		if (minute >= 60) {
			minute = 0;
			hour++;
		}

		if (hour >= 24) {
			hour = 0;
			nextDay();
		}	
	}

    public void setupGame() {
        pet = new Pet(this, "Buddy", "Dog");
        gameState = PLAY_STATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

   @Override
	public void run() {

		double drawInterval = 1000000000.0 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				Update();
				repaint();
				delta--;
			}
		}
	}

	public void nextDay() {

		currentDay++;

		pet.hunger -= 8;
		pet.energy -= 6;
		pet.happiness -= 4;

		money += 20;

		pet.checkHealth();

		if(currentDay > MAX_DAYS) {
			gameState = REPORT_STATE;
    }
}

    public void Update() {

        if (gameState == PLAY_STATE) {

            player.Update();

            if (keyH.feedPressed && money >= 5) {
                pet.feed();
                money -= 5;
            }

            if (keyH.playPressed && money >= 10) {
                pet.play();
                money -= 10;
            }

            if (keyH.restPressed) {
                pet.rest();
            }

			updateClock();
			player.Update();
			/*dayTimer++;

			if(dayTimer >= FRAMES_PER_DAY) {
				nextDay();
				dayTimer = 0;
			}*/
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
		player.Draw(g2);
		pet.draw(g2);
		ui.draw(g2);
				

        g2.dispose();
    }
}