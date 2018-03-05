
public class BookDepartment extends Department {
	
	public BookDepartment(String name, Integer ID) {
		super(name, ID);
	}
	
	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}
	
}
