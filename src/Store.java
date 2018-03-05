import java.util.*;

public class Store {
	private String name;
	private ArrayList<Department> departments;
	private ArrayList<Customer> customers;
	private static Store instance = null;
	
	protected Store() {
		departments = new ArrayList<Department>();
		customers = new ArrayList<Customer>();
	}
	
	public static Store getInstance() {
		if(instance == null)
			instance = new Store();
		return instance;
	}
	
	public void enter(Customer customer) {
		customers.add(customer);
	}
	
	public void exit(Customer customer) {
		customers.remove(customer);
	}
	
	public ShoppingCart getShoppingCart(Double budget) {
		return new ShoppingCart(budget);
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	public ArrayList<Department> getDepartments() {
		return departments;
	}
	
	public void addDepartment(Department department) {
		departments.add(department);
	}
	
	public Department getDepartment(Integer ID) {
		for(Department d : departments)
		{
			if(d.getId().equals(ID))
				return d;
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
