package objects;
import objects.objs.*;

public enum ObjectTiles {
	Air(new Air()), Tree(new Tree());
	
	private Object object;
	private ObjectTiles(Object object) {
		this.object = object;
	}
	
	public void function() { //See Object.java
		this.object.function();
	}
	
	public void passiveFunction() { //See Object.java
		this.object.passiveFunction();
	}
	 
	public Object getObjectById(int id) {
		return getById(id).object;
	}
	
	public int getXOffset() {
		return this.object.xOffset;
	}
	
	public int getYOffset() {
		return this.object.yOffset;
	}
	
	public int getWidth() {
		return this.object.width;
	}
	
	public int getHeight() {
		return this.object.height;
	}
	
	public boolean getSolid() {
		return this.object.solid;
	}
	
	public ObjectTiles getById(int id) {
		return ObjectTiles.values()[id];
	}
}

