package com;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	public static boolean pressingW = false;
	public static boolean pressingA = false;
	public static boolean pressingS = false;
	public static boolean pressingD = false;
	public static boolean sprinting = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		

		if(Render.state == 'G'){
			switch(key){
			case 'w':
				break;
			case 'a':
				break;
			case 's':
				break;
			case 'd':
				break;
			case 'e':
				break;
			}
		}else if(Render.state == 'S'){
			switch(key){
			case 'W':
			case 'w':
				pressingW = true;
				break;
			case 'A':
			case 'a':
				pressingA = true;
				break;
			case 'S':
			case 's':
				pressingS = true;
				break;
			case 'D':
			case 'd':
				pressingD = true;
				break;
			case 'e':
				break;
			
			}
			if(e.isShiftDown()){
				sprinting = true;
			}else{
				sprinting = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();
		if(Render.state == 'S'){
			switch(key){
			case 'w':
			case 'W':
				pressingW = false;
				break;
			case 'a':
			case 'A':
				pressingA = false;
				break;
			case 's':
			case 'S':
				pressingS = false;
				break;
			case 'd':
			case 'D':
				pressingD = false;
				break;
			
			}
			if(e.isShiftDown()){
				sprinting = true;
			}else{
				sprinting = false;
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		
		
		
	}
}
