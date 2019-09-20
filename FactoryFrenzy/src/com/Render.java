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

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = -9013264526583867430L;

	public static float xScale, yScale;
	public static Thread render;
	private Mouse mouse;
	private Keyboard keyboard;
	public boolean rendering = false; //lots of important variables
	public static Graphics2D g;
	public static Font newFont, currentFont;
	
	public volatile static char state = 'M';
	public volatile static String subState = "main";
	public static int[] fpsgraph = new int[30];
	public static final String Version = "V0.0.2";
	
	public static final byte bodies = 1, shirts = 0, hairs = 0, hats = 0;
	public static final Color[] skins = {new Color(255,255,255)};
	
	public static BufferedImage[] BBmenus;
	public Image[] menus;
	
	public static BufferedImage[] Bbodi = new BufferedImage[bodies];
	public Image[] body = new Image[bodies];
	
	public static BufferedImage[] Bshirt = new BufferedImage[shirts];
	public Image[] shirt = new Image[bodies];
	
	public static BufferedImage[] Bhair = new BufferedImage[shirts];
	public Image[] hair = new Image[bodies];
	
	public static BufferedImage[] Bhats = new BufferedImage[shirts];
	public Image[] hat = new Image[bodies];
	
	
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
			
			for(int i = 0; i < 1; i++){
				BBmenus[i] = ImageIO.read(new File("Resources\\Menus\\"+ i + ".png")); //loads tilesets
				menus[i] = BBmenus[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			
			for(byte i = 0; i < bodies; i++){
				Bbodi[i] = ImageIO.read(new File("Resources\\Player\\body"+ i + ".png")); //loads tilesets
				body[i] = Bbodi[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			for(byte i = 0; i < shirts; i++){
				Bshirt[i] = ImageIO.read(new File("Resources\\Player\\shirt"+ i + ".png")); //loads tilesets
				shirt[i] = Bshirt[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			for(byte i = 0; i < hairs; i++){
				Bhair[i] = ImageIO.read(new File("Resources\\Player\\hair"+ i + ".png")); //loads tilesets
				hair[i] = Bhair[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			for(byte i = 0; i < hats; i++){
				Bhats[i] = ImageIO.read(new File("Resources\\Player\\hat"+ i + ".png")); //loads tilesets
				hat[i] = Bhats[i].getScaledInstance((int)(800*xScale), -1, Image.SCALE_SMOOTH);
			}
			
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
				//System.out.println("(Graphic)FPS: " + frames);  
				//System.out.println(fpslimit);
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