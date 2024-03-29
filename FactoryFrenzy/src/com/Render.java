package com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import building.*;
import player.Player0;
import World.*;

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = -9013264526583867430L;

	public static float xScale, yScale;
	public static Thread render;
	private Mouse mouse;
	private Keyboard keyboard;
	public boolean rendering = false; //lots of important variables
	public static Graphics2D g;
	public static Font newFont, currentFont;
	
	public volatile static char state = 'S';
	public volatile static String subState = "main";
	public static int[] fpsgraph = new int[30];
	public static final String Version = "V0.0.2";
	
	public static final byte bodies = 1, shirts = 2, hairs = 2, hats = 1, faces = 5, items = 1;
	public static final byte worldFloorTiles = 5;
	public static final Color[] skins = {new Color(255,255,255), new Color(255,255,230), new Color(255,255,200),
			new Color(255,255,170), new Color(255,255,140), new Color(241, 194, 125),  new Color(224, 172, 105),
			new Color(198, 134, 66),new Color(141, 85, 36),new Color(90,30,6),new Color(40, 0, 0),new Color(20,0,0)};
	
	public static BufferedImage[] BBmenus;
	public Image[] menus;
	
	public static BufferedImage[] Bfloors = new BufferedImage[worldFloorTiles];
	public Image[] floor = new Image[worldFloorTiles];
	
	public static BufferedImage[] Bbodi = new BufferedImage[bodies];
	public Image[] body = new Image[bodies];
	
	public static BufferedImage[] Bshirt = new BufferedImage[shirts];
	public Image[] shirt = new Image[shirts];
	
	public static BufferedImage[] Bhair = new BufferedImage[hairs];
	public Image[] hair = new Image[hairs];
	
	public static BufferedImage[] Bhats = new BufferedImage[hats];
	public Image[] hat = new Image[hats];
	
	public static BufferedImage[] Bfaces = new BufferedImage[faces];
	public Image[] face = new Image[faces];
	
	public static BufferedImage[] BItems = new BufferedImage[items];
	public Image[] item = new Image[items]; 
	
	public static BufferedImage Bhead;
	
	public static int curplayer = 0; //for solo purposes
	
	public Render(){

		mouse = new Mouse();
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
		keyboard = new Keyboard();	//starting mouse and keyboard listeners
		this.addKeyListener(keyboard);
		
		try{
			BBmenus = new BufferedImage[1];
			menus = new Image[1];
			
			Bhead = ImageIO.read(new File("Resources\\Player\\Head.png"));
			
			for(int i = 0; i < 1; i++){
				BBmenus[i] = ImageIO.read(new File("Resources\\Menus\\"+ i + ".png")); //loads tilesets
				menus[i] = BBmenus[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			for(int i =0; i <  worldFloorTiles; i++){
				Bfloors[i] = ImageIO.read(new File("Resources\\World\\floor\\f"+ i + ".png"));
				floor[i] = Bfloors[i].getScaledInstance((int)(32*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			for(int i =0; i <  items; i++){
				BItems[i] = ImageIO.read(new File("Resources\\Items\\item"+ i + ".png"));
				item[i] = BItems[i].getScaledInstance((int)(65*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			for(byte i = 0; i < bodies; i++){
				Bbodi[i] = ImageIO.read(new File("Resources\\Player\\body"+ i + ".png")); //loads tilesets
				body[i] = Bbodi[i].getScaledInstance((int)(48*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			
			for(byte i = 0; i < shirts; i++){
				for(byte j = 0; j < bodies; j++){
					Bshirt[i] = ImageIO.read(new File("Resources\\Player\\"+ j +"shirt"+ i + ".png")); //loads tilesets
					shirt[i] = Bshirt[i].getScaledInstance((int)(48*xScale), -1, Image.SCALE_SMOOTH);
				}
			}
			
			for(byte i = 0; i < hairs; i++){
				Bhair[i] = ImageIO.read(new File("Resources\\Player\\hair"+ i + ".png")); //loads tilesets
				hair[i] = Bhair[i].getScaledInstance((int)(48*xScale), -1, Image.SCALE_SMOOTH);
			}
			for(byte i = 0; i < hats; i++){
				Bhats[i] = ImageIO.read(new File("Resources\\Player\\hat"+ i + ".png")); //loads tilesets
				hat[i] = Bhats[i].getScaledInstance((int)(48*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			for(byte i = 0; i < faces; i++){
				Bfaces[i] = ImageIO.read(new File("Resources\\Player\\face"+ i + ".png")); //loads tilesets
				face[i] = Bfaces[i].getScaledInstance((int)(48*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			Main.user.LoadData("Saves\\null.pdat");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		new Window(Config.WIDTH, Config.HEIGHT, "Factory Time", this);
	}
	
	
	public synchronized void start(){
		render = new Thread(this);
		render.start();
		rendering = true;
	}
	
	public synchronized void stop(){
		try{
			render.join();
			rendering = false;     
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static int tempframes;
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(rendering){
			long now = System.nanoTime();		//loops the rendering
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				delta--;                               
			}
			if(rendering){
				render();	
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("(Graphic)FPS: " + frames);  
				System.out.println(Config.fpslimit);
				System.out.println(Config.wantedfps);
				if(frames > Config.wantedfps+2 && Config.fpslimit > 2){
					Config.fpslimit -= 2;
				}else if(frames < Config.wantedfps-2 && Config.fpslimit < 254){ //framerate stablizer
					Config.fpslimit += 2;
				}
				tempframes = frames;
				int[] tempgraph = new int[30];
				tempgraph = fpsgraph;
				for(byte shift = 0; shift < fpsgraph.length; shift++){
					if(shift >= 29){
						fpsgraph[shift] = tempframes;
					}else{
						fpsgraph[shift] = tempgraph[shift+1];
					}
				}
				
				frames = 0;
			}
		}
		stop();
	}


final Color transBlack = new Color(0,0,0, 150);
final Color transRed = new Color(200,0,0,175);
final Color transWhite = new Color(255,255,255,150);
final Color transGreen = new Color(0,200,0,175);
final Color transBlue = new Color(0,0,200,175);
private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);          //Makes the FPS not 31mil also prevents flashing
			return;
		}
		g =  (Graphics2D) bs.getDrawGraphics(); 
		 
		currentFont = g.getFont();
		newFont = currentFont.deriveFont(xScale*(currentFont.getSize() * 0.9F)); 
		g.setFont(newFont);
		g.setColor(Color.black);
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			  
		switch(state){
		case 'M':
			if(subState == "main"){
				g.drawImage(menus[0], 0, 0, this);
			}else{
				//@TODO display options
				g.drawImage(menus[1], 0, 0, this);
			}
		break;
		case 'G':
			
			

			g.drawImage(Main.user.bodyimg, (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 48*xScale), this); //draws the body 
			g.drawImage(shirt[Main.user.shirt + (Main.user.body * shirts)], (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 48*xScale), this); //draws the shirt ontop
			g.drawImage(Main.user.headimg,(int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this);
			g.drawImage(Main.user.faceimg, (int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this); //draws the head
			//@TODO colorize skin and hair
			g.drawImage(Main.user.hairimg, (int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this); //draws the hair on head
			g.drawImage(hat[Main.user.hat], (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 64*xScale), this); //draws the hat on head
			
		break;
		case 'S':
			
			
			for(int x  = 0; x < 29; x++){ //Player can only see 25x18 area around them
				for(int y = 0; y < 22; y++){ //29x22 is used to prevent stuttering when moving
					try{
						//System.out.println(Main.user.x/32);
						g.drawImage(floor[Main.world.worldScape[x+Main.user.x/32][y+Main.user.y/32].ordinal()],(x-2)*32-(Main.user.x%32), (y-2)*32-(Main.user.y%32),this);
					}catch (Exception e){	
						g.drawImage(floor[4], (x-2)*32-(Main.user.x%32), (y-2)*32-(Main.user.y%32),this);
					}
				}
			}
			
			//@TODO add objects and roof rendering
			
			
			g.drawImage(Main.user.bodyimg, (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 48*xScale), this); //draws the body 
			g.drawImage(shirt[Main.user.shirt + (Main.user.body * shirts)], (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 48*xScale), this); //draws the shirt ontop
			g.drawImage(Main.user.headimg,(int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this);
			g.drawImage(Main.user.faceimg, (int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this); //draws the head
			//@TODO colorize skin and hair
			g.drawImage(Main.user.hairimg, (int)(Config.WIDTH/2 - 22*xScale), (int)(Config.HEIGHT/2 - 73*xScale), this); //draws the hair on head
			g.drawImage(hat[Main.user.hat], (int)(Config.WIDTH/2 - 24*xScale), (int)(Config.HEIGHT/2 - 64*xScale), this); //draws the hat on head
			
			for(int x  = 0; x < 29; x++){ //This is for shading, it is done last
				for(int y = 0; y < 22; y++){ //29x22 is used to prevent uneven shading when moving
					try{
						//Color shading = new Color(0,0,0,255-Main.world.lightLevel[x][y]*15);
						Color shading = new Color(0,0,0,255-World.timeLight()*15);
						g.setColor(shading);
						g.fillRect((x-2)*32-(Main.user.x%64), (y-2)*32-(Main.user.y%64), 32, 32);
					}catch(Exception e){
						//e.printStackTrace(); Usually A NullPointer Exception
					}
					
				}
			}
			

			currentFont = g.getFont();
			newFont = currentFont.deriveFont(xScale*(currentFont.getSize() * 1.7F));
			g.setFont(newFont);
			
			
			
			g.setColor(transBlack);
			g.fillRect(0,Math.round(450*yScale),Config.WIDTH, Config.HEIGHT);
			for(int inv = 0; inv < 8; inv++){
				g.setColor(transBlack);
				g.fillRect(Math.round((inv*95+30)*xScale), Math.round(495*yScale), Math.round(65*xScale), Math.round(65*yScale));
				g.drawImage(item[Main.user.inventory[inv][3]], Math.round((inv*95+30)*xScale), Math.round(495*yScale),this);
				g.setColor(transWhite);
				g.drawString(Main.user.invenCount[inv][3]+"",Math.round((inv*95+82)*xScale), Math.round(560*yScale));
				
			}
			
			
			
			g.drawString(Main.user.name,(int)(Config.WIDTH/2 - (Main.user.name.length()*4)*xScale), (int)(Config.HEIGHT/2 - 65*xScale)); //name
			
			
			
			g.fillRect(Math.round(40*xScale), Math.round(465*yScale),Math.round(200*xScale), Math.round(20*yScale)); //hpbar
			g.fillRect(Math.round(295*xScale), Math.round(465*yScale),Math.round(200*xScale), Math.round(20*yScale)); //Stambar
			g.fillRect(Math.round(550*xScale), Math.round(465*yScale),Math.round(200*xScale), Math.round(20*yScale)); //Hungbar
			
			g.setColor(transRed);
			g.fillRect(Math.round(40*xScale), Math.round(465*yScale),Math.round(200*(Main.user.health/100)*xScale), Math.round(20*yScale));
			
			g.setColor(transGreen);
			g.fillRect(Math.round(295*xScale), Math.round(465*yScale),Math.round(200*(Main.user.energy/100)*xScale), Math.round(20*yScale));
			
			g.setColor(transBlue);
			g.fillRect(Math.round(550*xScale), Math.round(465*yScale),Math.round(200*(Main.user.hunger/100)*xScale), Math.round(20*yScale));
			
		
			
		break;
		}
		  
		  
		
		g.dispose(); 
	    bs.show();
	    	
	    try {
	    	if(Config.fpslimit > 0 && Config.fpslimit < 254){
	    		Thread.sleep(1000/(Config.fpslimit));			//frame limiter
	    	}else{
	    		Thread.sleep(17);
	    	}
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}
	}
	
}