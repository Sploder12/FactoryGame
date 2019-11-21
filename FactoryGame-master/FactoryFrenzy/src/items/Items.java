package items;
import items.itms.*;

public enum Items {
	None(new None()), Wood(new Wood());

	private Item item;
	  
	private Items(Item item) {
		this.item = item;
	}
	
	public void function() {
		this.item.function();
	}
	
	public Items getById(int id) {
		return Items.values()[id];
	}
	
	public Item getItemById(int id) {
		return getById(id).item;
	}
}
