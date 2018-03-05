
public class MusicDepartment extends Department {
	
	public MusicDepartment(String name, Integer ID) {
		super(name, ID);
	}
	
	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}
}
