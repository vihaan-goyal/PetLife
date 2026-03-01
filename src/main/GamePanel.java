package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	// SCREEN SETTINGS
	final int originalTileSizes = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSizes * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	// FPS LIMIT
	int fps = 60;
	
	// SYSTEM
	TileManager tileManager = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		assetSetter.setObject();
		
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/fps;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null){
			long currentTime = System.nanoTime();
			
			timer += (nextDrawTime - currentTime);
			
			Update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0){
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			drawCount++;
			if(timer >= 1000000000){
				Main.window.setTitle("2D Game Tutorial" + " | FPS: " + drawCount);
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void Update(){
		player.Update();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// DEBUG
		long drawStart = 0;
		if(keyH.checkDrawDebugTime) {
			drawStart = System.nanoTime();
		}
		
		// TILES
		tileManager.draw(g2);
		
		// OBJECTS
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// PLAYER
		player.Draw(g2);
		
		// UI
		ui.draw(g2);
		
		//DEBUG
		if(keyH.checkDrawDebugTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			
			g2.setColor(Color.WHITE);
			g2.drawString("Draw time: " + passed, 10, 400);
			System.out.println("Draw time: " + passed);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}
}