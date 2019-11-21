package objects;

public abstract class Object {
	public void function() {} //When player interacts with object
	public void passiveFunction() {} //When player is near the object
	
	public long id = 0; //item id (used for storing and sprite)
	public String name = "Placeholder";
	public byte maxStack = 2;
	public float stockPrice = 5f; //The default price at ingame stores
	public short width = 32;
	public short height = 32;
	public short xOffset = 0;
	public short yOffset = 0;
	
	public boolean solid = true;
	
}
