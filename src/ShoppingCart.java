import java.util.*;

public class ShoppingCart extends ItemList implements Visitor {
	private Double budget = null;
	
	public ShoppingCart() {
		budget = new Double(0);
		this.comparator = new Comparator<Item>() {
			
			@Override
			public int compare(Item i1, Item i2) {
				if(i1.getPrice() > i2.getPrice())
				{
					return 1;
				} else if(i1.getPrice().equals(i2.getPrice()))
				{
					return i1.getName().compareTo(i2.getName());
				} else
				{
					return -1;
				}
			}
		};
	}
	
	public ShoppingCart(Double budget) {
		this.budget = new Double(budget);
		this.comparator = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				if(i1.getPrice() > i2.getPrice())
				{
					return 1;
				} else if(i1.getPrice().equals(i2.getPrice()))
				{
					return i1.getName().compareTo(i2.getName());
				} else
				{
					return -1;
				}
			}
		};
	}
	
	public Double getBudget() {
		return budget;
	}
	
	public void setBudget(Double budget) {
		this.budget = budget;
	}

	@Override
	public void visit(BookDepartment bookDepartment) {
		ListIterator<Item> iterator = this.listIterator();
		Item aux = null;
		boolean changed = false;
		while(iterator.hasNext())
		{
			aux = iterator.next();
			for(Item item : bookDepartment.getItems())
				if(item.equals(aux))
				{
					aux.setPrice(aux.getPrice()*(1-(double)10/100));
					changed = true;
					break;
				}
		}
		if(changed == true)
			this.sort();
	}

	@Override
	public void visit(MusicDepartment musicDepartment) {
		Double price = new Double(0);
		ListIterator<Item> iterator = this.listIterator();
		Item aux = null;
		boolean changed = false;
		while(iterator.hasNext())
		{
			aux = iterator.next();
			for(Item item : musicDepartment.getItems())
				if(item.equals(aux))
				{
					price = price + aux.getPrice();
					break;
				}
		}
		if(price.compareTo(0.0) > 0)
			changed = true;
		this.setBudget(this.getBudget() + price*(double)10/100);
		if(changed == true)
			this.sort();
	}

	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		ListIterator<Item> iterator = this.listIterator();
		Item aux = null;
		ArrayList<Item> items = new ArrayList<Item>();
		items.addAll(softwareDepartment.getItems());
		Collections.sort(items, comparator);
		boolean contains = false;
		boolean changed = false;
		while(iterator.hasNext())
		{
			aux = iterator.next();
			for(Item item : items)
				if(item.equals(aux))
				{
					contains = true;
					break;
				}
		}
		if(contains == true && items.get(0).getPrice()>this.getBudget())
		{
			aux.setPrice(aux.getPrice()*(1-(double)20/100));
			while(iterator.hasNext())
			{
				aux = iterator.next();
				for(Item item : items)
					if(item.equals(aux))
					{
						aux.setPrice(aux.getPrice()*(1-(double)20/100));
						changed = true;
						break;
					}
			}
		}
		if(changed == true)
			this.sort();
	}

	@Override
	public void visit(VideoDepartment videoDepartment) {
		ListIterator<Item> iterator = this.listIterator();
		Item aux = null;
		Double price = new Double(0);
		Double sum = new Double(0);
		ArrayList<Item> items = videoDepartment.getItems();
		ArrayList<Item> clone = new ArrayList<Item>();
		clone.addAll(items);
		boolean changed = false;
		while(iterator.hasNext())
		{
			aux = iterator.next();
			for(Item item : items)
				if(item.equals(aux))
				{
					price = price + aux.getPrice()*(double)5/100;
					sum = sum + aux.getPrice();
				}
		}
		if(price.compareTo(0.0) > 0)
			changed = true;
		this.setBudget(this.getBudget() + price);
		
		Collections.sort(clone, comparator);
		
		if(sum > clone.get(clone.size() - 1).getPrice())
		{
			iterator = this.listIterator();
			while(iterator.hasNext())
			{
				aux = iterator.next();
				for(Item item : clone)
					if(item.equals(aux))
					{
						aux.setPrice(aux.getPrice()*(1-(double)15/100));
						//iterator.remove();
						//this.add(aux);
						changed = true;
					}
			}
		}
		if(changed == true)
			this.sort();
	}
	
	public boolean add(Item element) {
		//System.out.println(element.getPrice() + " " + this.getBudget()); //---------------DEBUG
		if(element.getPrice() <= this.getBudget())
		{
			super.add(element);
			this.setBudget(this.getBudget() - element.getPrice());
			return true;
		}
		else
			return false;
	}
	
	public boolean addAll(Collection<? extends Item> c) {
		ArrayList<Item> arraylist = new ArrayList<Item>();
		arraylist.addAll(c);
		Iterator<Item> iterator = arraylist.iterator();
		boolean result = false;
		while(iterator.hasNext())
		{
			result = result & this.add(iterator.next());
		}
		return result;
	}
	
}
