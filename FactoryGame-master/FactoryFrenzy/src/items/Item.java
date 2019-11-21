package items;

public abstract class Item {
	//All items are obtained by using Machine Objects or Purchase (except certain raw resources ex. Wood)
	
	public void function() {/*Default Nothing*/}; //When player presses the SPACE key
	public long id = 0; //item id (used for storing and sprite)
	public String name = "Placeholder";
	public byte maxStack = 16;
	public float stockPrice = 1f; //The default price at ingame stores
		
}
