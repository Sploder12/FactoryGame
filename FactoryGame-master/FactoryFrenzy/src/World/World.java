package World;

import building.*;
import objects.*;

public class World {
	public FloorTiles[][] worldScape = new FloorTiles[5000][5000]; //The look of the ground
	public ObjectTiles[][] worldObjects = new ObjectTiles[5000][5000]; //Walls-Coneyers-2nd Layer Stuff
	public boolean[][] covered = new boolean[5000][5000]; //roofs and tree cover etc
	public byte[][] lightLevel = new byte[5000][5000]; //The Light Level of each area, 0-17
	
	public static int time = 0;
	public static byte timeLight(){
		return (byte) (Math.abs(time-720)/-60+15); //Absolute Value function for time
		
	}
}
