package com;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener{
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotations = (int) Math.round(e.getPreciseWheelRotation());
		rotations %= 8;
		Main.user.selectedInv += rotations;
		if(Main.user.selectedInv < 0) {
			Main.user.selectedInv += 8;
		}else if(Main.user.selectedInv > 7) {
			Main.user.selectedInv -= 8;
		}
	}

	
}
