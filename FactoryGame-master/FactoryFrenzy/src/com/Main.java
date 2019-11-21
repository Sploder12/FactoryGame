package com;

import java.io.FileInputStream;
import java.io.InputStream;

import World.World;
import player.*;
import building.*;
import sploder12.json.JSON;
import objects.*;

public class Main implements Runnable{
	public static Thread main;
	public static boolean running = true;
	public static float taxrate = 0.03f;
	public static World world = new World();
	public static Player0 user = new Player0(0,0);
	
	public Main(){
		
		start();
	}
	
	private float speedmult = 1;
	
	public boolean checkCollision(int moveX, int moveY) {
		for(byte x = -1; x <= 1; x++) {
			for(byte y = -1; y <= 1; y++) {
				int xThing = Main.user.tileX + x;
				int yThing = Main.user.tileY + y;
				try {
					ObjectTiles object = world.worldObjects[xThing][yThing];
					if(object.getSolid()) {
						int x0 = object.getXOffset() + xThing*32;
						int y0 = object.getYOffset() + yThing*32;
						if((Main.user.x+5+moveX >= x0 && Main.user.x+5+moveX <= x0 + object.getWidth()) || (Main.user.x+30+moveX >= x0 && Main.user.x+30+moveX <= x0 + object.getWidth())) {
							if((Main.user.y+moveY >= y0 && Main.user.y+moveY <= y0 + object.getHeight()) || (Main.user.y+11+moveY >= y0 && Main.user.y+11+moveY <= y0 + object.getHeight())) return true;
						}
					}
				}catch(Exception e) {
					
				}
			}
		}
		return false;
	}

	public synchronized void run() {
		user.LocalSaveData();
		boolean sprint = Keyboard.sprinting; //Preventing from being changed during Keyboard
		boolean pressS = Keyboard.pressingS;
		boolean pressA = Keyboard.pressingA;
		boolean pressW = Keyboard.pressingW;
		boolean pressD = Keyboard.pressingD;
		long timer = System.currentTimeMillis();
		while(running){
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				if(World.time <= 1440){
					World.time ++;
				}else{
					World.time = 0;
				}
			}
			
			//put code here
			
			if(Render.state == 'S'){
				sprint = Keyboard.sprinting; //Preventing from being changed during Keyboard
				pressS = Keyboard.pressingS;
				pressA = Keyboard.pressingA;
				pressW = Keyboard.pressingW;
				pressD = Keyboard.pressingD;
				speedmult = (sprint)? 4:2; //sprinting
				speedmult = ((pressS && (pressA || pressD))||(pressW && (pressA || pressD)))? speedmult/2:speedmult; //diagonal movement
				if(pressW && !checkCollision(0, (int) (-1*speedmult))){
					user.moveY(Math.round(-1 * speedmult));
					
				}else if(pressS && !checkCollision(0, (int) (1*speedmult))){
					user.moveY(Math.round(1 * speedmult));
				}
					
				if(pressA && !checkCollision((int) (-1*speedmult), 0)){
					user.moveX(Math.round(-1 * speedmult));	
					
				}else if(pressD && !checkCollision((int) (1*speedmult), 0)){
					user.moveX(Math.round(1 * speedmult));
				}
				
				if(user.x < 0) {
					user.x = 0;
				}else if(user.x > 160000) {
					user.x = 160000;
				}
				
				if(user.y < 0) {
					user.y = 0;
				}else if(user.y > 160000) {
					user.y = 160000;
				}
				
				user.tileY = user.y/32;
				user.tileX = user.x/32;
				
				
				if(sprint){
					//user.energy -= 0.000001;
				}
				
				
			}
			
			
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
	
	public synchronized void start(){
		main = new Thread(this);
		main.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			main.join();   
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
public static void generateLandscape(long seed){
		World world = new World();
		for(short X = 0; X < 5000; X++){
			for(short Y = 0; Y < 5000; Y++){
				world.worldScape[X][Y] = FloorTiles.Grass;
				world.lightLevel[X][Y] = 17;
				world.covered[X][Y] = false;
				world.worldObjects[X][Y] = ObjectTiles.Air;
			}
		}
		world.worldScape[0][0] = FloorTiles.DeepWater;
		world.worldScape[0][4999] = FloorTiles.DeepWater;
		world.worldScape[4999][0] = FloorTiles.DeepWater;
		world.worldScape[4999][4999] = FloorTiles.DeepWater;
		world.worldObjects[2][2] = ObjectTiles.Tree;
		Main.world = world;
	}
	
	public static void main(String[] args) {
		Config.loadConfig(); //gotta load that config
		generateLandscape(100);
		new Main();
		try{
			Config.WIDTH = Integer.parseInt(args[0]); //takes in args for height and width of window
			Config.HEIGHT = Integer.parseInt(args[1]);
		}catch(Exception e){
			System.out.println("No Width or Heigh Args Given, Starting 800x600 or config");
		}
		
		Render.xScale = Config.WIDTH/800F; 
		Render.yScale = Config.HEIGHT/600F;
		
		new Render();
	}


}
