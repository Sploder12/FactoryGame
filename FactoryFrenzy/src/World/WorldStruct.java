package World;

import building.*;
public class WorldStruct {
	public FloorTiles[][] worldScape = new FloorTiles[255][255]; //The look of the ground
	public ObjectTiles[][] worldObjects = new ObjectTiles[255][255]; //Walls-Coneyers-2nd Layer Stuff
	public boolean[][] covered = new boolean[255][255]; //roofs and tree cover etc
}
