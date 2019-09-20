package com;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		
		
		
	}
}
