
public class Item {
	private String name;
	private Integer ID;
	private Double price;
	
	public Item(String name, Integer ID, Double price) {
		this.name = name;
		this.price = price;
		this.ID = ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public boolean equals(Item i) {
		if(this.ID.equals(i.ID))
			return true;
		else
			return false;
	}
	
	public Item clone() {
		return new Item(this.name, this.ID, this.price);
	}
	
	/*public boolean contains(ArrayList<Item> array, Item i) {
		for(Item a : array)
		{
			if(a == i || i!=null && i.equals(a))
				return true;
		}
		return false;
	}*/
	
}
