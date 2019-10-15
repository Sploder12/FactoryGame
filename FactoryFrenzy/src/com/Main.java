package com;

import java.io.FileInputStream;
import java.io.InputStream;

import player.*;
import building.*;
import npc.*;

import sploder12.json.JSON;

public class Main implements Runnable{
	public static Thread main;
	public static boolean running = true;
	public static float taxrate = 0.03f;
	
	public static Player0 user = new Player0(0,0);
	
	public Main(){
		
		start();
	}
	
	private float speedmult = 1;
	public synchronized void run() {
		user.LocalSaveData();
		
		
		while(running){
			
			
			
			
			//put code here
			
			if(Render.state == 'S'){
				speedmult = (Keyboard.sprinting)? 2:1;
				if(Keyboard.pressingW){
					user.moveY(Math.round(-1 * speedmult));
				}else if(Keyboard.pressingS){
					user.moveY(Math.round(1 * speedmult));
				}
					
				if(Keyboard.pressingA){
					user.moveX(Math.round(-1 * speedmult));	
				}else if(Keyboard.pressingD){
					user.moveX(Math.round(1 * speedmult));
				}
				
				
				if(Keyboard.sprinting){
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
	
	
	
	public static void main(String[] args) {
		Config.loadConfig(); //gotta load that config
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
