import java.util.*;

public class WishList extends ItemList {
	
	private Strategy strategy;
	private Stack<Integer> itemIDHistory = new Stack<Integer>();
	
	public Item executeStrategy() {
		Item item = strategy.execute(this);
		this.remove(item, true);
		
		Store store = Store.getInstance();
		for(Customer customer : store.getCustomers())
		{
			if(customer.getWishList() == this)
			{
				customer.getShoppingCart().add(item);
				break;
			}
		}
		
		return item;
	}
	
	public Integer getLastAddedItem() {
		return itemIDHistory.peek();
	}
	
	public boolean add(Item item) {
		super.add(item);
		itemIDHistory.push(item.getID());
		return true;
	}
	
	public boolean remove(Item item) {
		boolean result = super.remove(item);
		Integer aux = null;
		Stack<Integer> temp = new Stack<Integer>();
		if(!itemIDHistory.empty())
		{
			aux = itemIDHistory.pop();
			while(!itemIDHistory.empty() && !aux.equals(item.getID()))
			{
				temp.push(aux);
				aux = itemIDHistory.pop();
			}
			while(!temp.empty())
			{
				aux = temp.pop();
				itemIDHistory.push(aux);
			}
		}
		
		return result;
	}
	
	public boolean remove(Item item, boolean check) {
		boolean result = super.remove(item);
		Integer aux = null;
		Stack<Integer> temp = new Stack<Integer>();
		if(!itemIDHistory.empty())
		{
			aux = itemIDHistory.pop();
			while(!itemIDHistory.empty() && !aux.equals(item.getID()))
			{
				temp.push(aux);
				aux = itemIDHistory.pop();
			}
			while(!temp.empty())
			{
				aux = temp.pop();
				itemIDHistory.push(aux);
			}
		}
		
		//------------------------Check if customer remains observer------------
		if(check == true)
		{
			Store store = Store.getInstance();
			Department dep = null;
			Customer customer = null;
			boolean contains = false;
			for(Customer c : store.getCustomers())
			{
				if(c.getWishList() == this)
				{
					customer = c;
					break;
				}
			}
			for(Department department : store.getDepartments())
			{
				if(department.getObservers().contains(customer))
				{
					for(Item items : department.getItems())
					{
						if(items.equals(item))
						{
							dep = department;
							break;
						}
					}
					if(dep != null)
					{
						break;
					}
				}
			}
			for(Item items : dep.getItems())
			{
				if(customer.getWishList().contains(items))
				{
					contains = true;
					break;
				}
			}
			if(contains == false)
			{
				dep.removeObserver(customer);
			}
		}
		
		return result;
	}
	
	public Item remove(int index) {
		Item item = this.getItem(index);
		this.remove(item);
		return item;
	}
	
	public WishList() {
		this.comparator = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				return i1.getName().compareTo(i2.getName());
			}
		};
	}
	
	public void setStrategy(String s) {
		switch(s)
		{
		case "A":
			strategy = new StrategyA();
			break;
		case "B":
			strategy = new StrategyB();
			break;
		case "C":
			strategy = new StrategyC();
			break;
		default:
			break;
		}
	}
}
