package World;

import building.*;

public class World {
	public static WorldStruct[][] fullWorld = new WorldStruct[100][100]; //This is technically a 5D array
	//[WorldX][WorldY].Layer[X][Y] Layer could be treated as an array of 3
	//[100][100][3][255][255] Atleast 19,507,500  Bytes of mem or 19MB
	
}
