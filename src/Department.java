import java.util.*;

public abstract class Department implements Subject {
	private String name;
	private ArrayList<Item> items;
	private ArrayList<Customer> customers;
	private ArrayList<Customer> observers;
	private Integer departmentID;
	
	public Department(String name, Integer departmentID) {
		this.name = name;
		this.departmentID = departmentID;
		items = new ArrayList<Item>();
		customers = new ArrayList<Customer>();
		observers = new ArrayList<Customer>();
	}
	
	public void enter(Customer customer) {
		customers.add(customer);
	}
	
	public void exit(Customer customer) {
		customers.remove(customer);
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	public ArrayList<Customer> getObservers() {
		return observers;
	}
	
	public Integer getId() {
		return departmentID;
	}
	
	public void addItem(Item i) {
		items.add(i);
		Notification notification = new Notification(Notification.NotificationType.ADD, this.getId(), i.getID());
		this.notifyAllObservers(notification);
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void addObserver(Customer customer) {
		/*boolean contains = false;
		for(Customer c : observers)
		{
			if(c.getName().equals(customer.getName()))
				contains = true;
		}
		if(contains == false)*/
		if(!observers.contains(customer))
			observers.add(customer);
	}
	
	public void removeObserver(Customer customer) {
		observers.remove(customer);
	}
	
	public void notifyAllObservers(Notification notification) {
		ArrayList<Customer> clone = new ArrayList<Customer>();
		Iterator<Customer> iterator = observers.iterator();
		Customer c = null;
		
		while(iterator.hasNext())
		{
			clone.add(iterator.next()); //Fac o clona, pentru ca daca este scos un observator
										//nu o sa il pot verifica pe urmatorul care trece in
										//locul lui
		}
		
		iterator = clone.iterator();
		
		while(iterator.hasNext())
		{
				c = iterator.next();
			c.addNotification(notification);
		}
	}
	
	public abstract void accept(ShoppingCart cart);
	
	public void removeItem(Integer itemID) {
		for(Item item : items)
		{
			if(item.getID().equals(itemID))
			{
				Notification notification = new Notification(Notification.NotificationType.REMOVE,this.getId(),itemID);
				this.notifyAllObservers(notification);
				items.remove(item);
				return;
			}
		}
	}
	
	public void modifyItem(Integer itemID, Double price) {
		for(Item item : items)
		{
			if(item.getID().equals(itemID))
			{
				item.setPrice(price);
				Notification notification = new Notification(Notification.NotificationType.MODIFY, this.getId(), itemID);
				this.notifyAllObservers(notification);
				return;
			}
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
