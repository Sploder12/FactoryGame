package objects.objs;
import objects.Object;

public class Air extends Object{
	public Air() {
		id = 0;
		name = "Air";
		stockPrice = 0f;
		maxStack = 0;
		solid = false;
	}
	//air doesn't redeclare function and passive because it has no function and serves as a placeholder.
}
