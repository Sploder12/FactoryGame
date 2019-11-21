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
				pressingW = true; //see Main for what this does
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
			case 32:
				Main.user.inventory[Main.user.selectedInv][3].function(); //See items.Items & items.Item for what this does
				break;
			case '1':
				Main.user.selectedInv = 0;
				break;
			case '2':
				Main.user.selectedInv = 1;
				break;	
			case '3':
				Main.user.selectedInv = 2;
				break;
			case '4':
				Main.user.selectedInv = 3;
				break;
			case '5':
				Main.user.selectedInv = 4;
				break;
			case '6':
				Main.user.selectedInv = 5;
				break;
			case '7':
				Main.user.selectedInv = 6;
				break;
			case '8':
				Main.user.selectedInv = 7;
				break;
			}
			if(e.isShiftDown()){
				sprinting = true; //see Main for what this does
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
