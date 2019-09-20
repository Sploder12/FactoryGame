package com;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
	public static boolean clicked;
	public static int mx, my;
	
	public void mouseMoved(MouseEvent e){
		mx = e.getX();
		my = e.getY();
	}
	
	public void mousePressed(MouseEvent e)
	{
		
		
		clicked = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
		clicked = false;
	}
	
	public static boolean moveOver(int mx, int my,int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			return(my > y && my < y + height);
		} else return false;
	}
}
	
	
