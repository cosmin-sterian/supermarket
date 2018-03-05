import java.util.*;

public class Customer implements Observer {
	private String name;
	private ShoppingCart cart;
	private WishList wishlist;
	private ArrayList<Notification> notifications = new ArrayList<Notification>();
	
	public Customer(String name, String strategy) {
		this.name = name;
		wishlist = new WishList();
		wishlist.setStrategy(strategy);
	}
	
	public ShoppingCart getShoppingCart() {
		return cart;
	}
	
	public WishList getWishList() {
		return wishlist;
	}
	
	public void newShoppingCart(ShoppingCart cart) {
		this.cart = cart;
	}
	
	public Double getBudget() {
		if(cart != null)
			return cart.getBudget();
		else
			return 0.0;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addNotification(Notification notification) {
		notifications.add(notification);
		this.update(notification);
	}
	
	public ArrayList<Notification> getNotifications() {
		return notifications;
	}
	
	public void update(Notification notification) {
		Integer productID = notification.getProductID();
		Integer departmentID = notification.getDepartmentID();
		Department department = Store.getInstance().getDepartment(departmentID);
		ArrayList<Item> items = department.getItems();
		switch(notification.getNotificationType())
		{
		case ADD:
			break;
		case REMOVE:
			for(Item item : items)
			{
				if(item.getID().equals(productID))
				{
					if(wishlist.contains(item))
						wishlist.remove(item, true);
					if(cart.contains(item))
					{
						cart.setBudget(cart.getBudget() + item.getPrice());
						cart.remove(item);
					}
					break;
				}
			}
			break;
		case MODIFY:
			for(Item item : items)
			{
				if(item.getID().equals(productID))
				{
					Item oldItem = null;
					if(wishlist.contains(item))
					{
						oldItem = wishlist.getItem(wishlist.indexOf(item));
						wishlist.remove(oldItem, false);
						wishlist.add(item);
					}
					if(cart.contains(item))
					{
						oldItem = cart.getItem(cart.indexOf(item));
						cart.setBudget(cart.getBudget() + oldItem.getPrice());
						cart.remove(oldItem);
						cart.add(item);
					}
					break;
				}
			}
			break;
		}
	}
	
}
