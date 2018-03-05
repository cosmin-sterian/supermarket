
public class VideoDepartment extends Department {
	
	public VideoDepartment(String name, Integer ID) {
		super(name, ID);
	}
	
	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}
}
