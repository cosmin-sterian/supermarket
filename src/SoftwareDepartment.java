
public class SoftwareDepartment extends Department {
	
	public SoftwareDepartment(String name, Integer ID) {
		super(name, ID);
	}
	
	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}
}
