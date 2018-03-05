import java.text.DecimalFormat;
import java.util.*;

public abstract class ItemList {
	private static class Node<T> {
		private T data;
		private Node<T> next;
		private Node<T> prev;
		
		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
		
		public boolean equals(Node<T> node) {
			if(this.data.equals(node.data) && this.next == node.next && this.prev == node.prev)
				return true;
			else
				return false;
		}
		
	}
	
	private class NodeIterator implements ListIterator<Node<Item>> {
		private Node<Item> current = null;
		private int index = -1;
		
		@Override
		public boolean hasNext() {
			if(isEmpty())
				return false;
			else if(current == null)
			{
				return true;
			} else if(current == last) {
				return false;
			} else
				return true;
		}

		@Override
		public void add(Node<Item> e) {
			Node<Item> temp = e;
			if(isEmpty()) {
				first = temp;
			} else if(current == null) {
				temp.next = first;
				first = temp;
			} else if(current.next == null) {
				current.next = temp;
				temp.prev = current;
				last = temp;
			} else {
				current.next = temp;
				temp.next = current.next.next;
				temp.prev = current;
			}
		}

		@Override
		public boolean hasPrevious() {
			if(isEmpty())
				return false;
			else if(current == null) {
				return false;
			} else if(current == first) {
				return false;
			} else
				return true;
		}

		@Override
		public Node<Item> next() {
			if(current == null)
			{
				current = first;
				index++;
				return current;
			}
			if(current.next == null)
				throw new NoSuchElementException();
			current = current.next;
			index++;
			return current;
		}

		@Override
		public int nextIndex() {
			return index+1;
		}

		@Override
		public Node<Item> previous() {
			if(current == null)
			{
				throw new NoSuchElementException();
			}
			if(current.prev == null)
			{
				throw new NoSuchElementException();
			}
			current = current.prev;
			index--;
			return current;
		}

		@Override
		public int previousIndex() {
			return index-1;
		}

		@Override
		public void remove() {
			if(current == first)
			{
				if(current == last)
				{
					first = null;
					last = null;
				} else {
					first = current.next;
					current.next.prev = null;
					current = first;
				}
			} else if(current == last) {
				last = current.prev;
				last.next = null;
				current = last;
			} else {
				current.prev.next = current.next;
				current.next.prev = current.prev;
				current = current.next;
			}
		}

		@Override
		public void set(Node<Item> e) {
			current = e;
		}
		
	}
	
	private class ItemIterator implements ListIterator<Item> {
		private Node<Item> current = null;
		private int index = -1;
		
		@Override
		public boolean hasNext() {
			if(isEmpty())
				return false;
			else if(current == null)
			{
				return true;
			} else if(current == last) {
				return false;
			} else
				return true;
		}

		@Override
		public void add(Item e) {
			Node<Item> temp = new Node<>(e);
			if(isEmpty()) {
				first = temp;
			} else if(current == null) {
				temp.next = first;
				first = temp;
			} else if(current.next == null) {
				current.next = temp;
				temp.prev = current;
				last = temp;
			} else {
				current.next = temp;
				temp.next = current.next.next;
				temp.prev = current;
			}
		}

		@Override
		public boolean hasPrevious() {
			if(isEmpty())
				return false;
			else if(current == null) {
				return false;
			} else if(current == first) {
				return false;
			} else
				return true;
		}

		@Override
		public Item next() {
			if(current == null)
			{
				current = first;
				index++;
				return current.data;
			}
			if(current.next == null)
				throw new NoSuchElementException();
			current = current.next;
			index++;
			return current.data;
		}

		@Override
		public int nextIndex() {
			return index+1;
		}

		@Override
		public Item previous() {
			if(current == null)
			{
				throw new NoSuchElementException();
			}
			if(current.prev == null)
			{
				throw new NoSuchElementException();
			}
			current = current.prev;
			index--;
			return current.data;
		}

		@Override
		public int previousIndex() {
			return index-1;
		}

		@Override
		public void remove() {
			if(current == first)
			{
				if(current == last)
				{
					first = null;
					last = null;
				} else {
					first = current.next;
					current.next.prev = null;
					current = first;
				}
			} else if(current == last) {
				last = current.prev;
				last.next = null;
				current = last;
			} else {
				current.prev.next = current.next;
				current.next.prev = current.prev;
				current = current.next;
			}
		}

		@Override
		public void set(Item e) {
			current.data = e;
		}
		
	}
	
	public ListIterator<Node<Item>> nodeIterator() {
		return new NodeIterator();
	}
	
	public ListIterator<Item> listIterator() {
		return new ItemIterator();
	}
	
	Comparator<Item> comparator = null; //Ramane sa il declar in fiecare clasa ca o clasa abstracta
	
	private Node<Item> first = null;
	private Node<Item> last = null;
	
	private int getSize() {
		ListIterator<Item> itemIterator = this.listIterator();
		int result = 0;
		while(itemIterator.hasNext())
		{
			itemIterator.next();
			result++;
		}
		return result;
	}
	
	public void sort() {
		if(first == null || first == last)
			return;
		boolean flag = true;
		Item aux = null;
		Node<Item> current = null;
		int size = this.getSize();
		while(flag)
		{
			flag = false;
			current = first;
			for(int i = 0; i < size-1; i++)
			{
				if(comparator.compare(current.data, current.next.data) > 0) {
					aux = current.data;
					current.data = current.next.data;
					current.next.data = aux;
					flag = true;
				}
				current = current.next;
			}
		}
	}
	
	public boolean add(Item element) {
		Node<Item> current = new Node<>(element);
		if(first == null)
		{
			first = current;
			last = current;
		}
		else
		{
			if(comparator.compare(element, first.data) <= 0)
			{
				current.next = first;
				//last = first;
				first.prev = current;
				first = current;
			} else if(comparator.compare(element, last.data) >= 0)
			{
				last.next = current;
				current.prev = last;
				last = current;
			} else 
			{
				Node<Item> next = first.next;
				Node<Item> prev = first;
				while(comparator.compare(element, next.data) > 0) {
					prev = next;
					next = next.next;
				}
				prev.next = current;
				current.prev = prev;
				current.next = next;
				next.prev = current;
			}
		}
		return true;
	}
	
	//public abstract boolean addAll(Collection<? extends Item> c);
	public boolean addAll(Collection<? extends Item> c) {
		ArrayList<Item> arraylist = new ArrayList<Item>();
		arraylist.addAll(c);
		Iterator<Item> iterator = arraylist.iterator();
		while(iterator.hasNext())
		{
			this.add(iterator.next());
		}
		return true;
	}
	
	public Item getItem(int index) {
		ListIterator<Item> itemIterator = this.listIterator();
		Item aux = null;
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(itemIterator.nextIndex()-1 == index)
				return aux;
		}
		return null;
	}
	
	public Node<Item> getNode(int index) {
		ListIterator<Node<Item>> nodeIterator = this.nodeIterator();
		Node<Item> aux = null;
		while(nodeIterator.hasNext())
		{
			aux = nodeIterator.next();
			if(nodeIterator.nextIndex()-1 == index)
				return aux;
		}
		return null;
	}
	
	public int indexOf(Item item) {
		ListIterator<Item> itemIterator = this.listIterator();
		Item aux = null;
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(aux.equals(item))
				return itemIterator.nextIndex()-1;
		}
		return -1;
	}
	
	public int indexOf(Node<Item> node) {
		ListIterator<Node<Item>> nodeIterator = this.nodeIterator();
		Node<Item> aux = null;
		while(nodeIterator.hasNext())
		{
			aux = nodeIterator.next();
			if(aux.equals(node))
				return nodeIterator.nextIndex()-1;
		}
		return -1;
	}
	
	public boolean contains(Node<Item> node) {
		ListIterator<Node<Item>> nodeIterator = this.nodeIterator();
		Node<Item> aux = null;
		while(nodeIterator.hasNext())
		{
			aux = nodeIterator.next();
			if(aux.equals(node))
				return true;
		}
		return false;
	}
	
	public boolean contains(Item item) {
		ListIterator<Item> itemIterator = this.listIterator();
		Item aux = null;
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(aux.equals(item))
				return true;
		}
		return false;
	}
	
	public Item remove(int index) {
		ListIterator<Item> itemIterator = this.listIterator();
		Item aux = null;
		Item result = null;
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(itemIterator.nextIndex()-1 == index)
			{
				result = aux;
				itemIterator.remove();
				return result;
			}
		}
		return result;
	}
	
	public boolean remove(Item item) {
		ListIterator<Item> itemIterator = this.listIterator();
		Item aux = null;
		boolean result = false;
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(aux.equals(item))
			{
				result = true;
				itemIterator.remove();
			}
		}
		return result;
	}
	
	public boolean reomveAll(Collection<? extends Item> collection) {
		ArrayList<Item> arraylist = new ArrayList<Item>();
		arraylist.addAll(collection);
		ListIterator<Item> iterator = arraylist.listIterator();
		boolean result = false;
		while(iterator.hasNext())
		{
			result = result | this.remove(iterator.next());
		}
		return result;
	}
	
	public boolean isEmpty() {
		if(first==null)
			return true;
		else
			return false;
	}
	
	public ListIterator<Item> listIterator(int index) {
		ListIterator<Item> result = this.listIterator();
		while(result.hasNext())
		{
			result.next();
			if(result.nextIndex()-1 == index)
				return result;
		}
		return this.listIterator();
	}
	
	public Double getTotalPrice() {
		ListIterator<Item> itemIterator = this.listIterator();
		Double sum = new Double(0);
		while(itemIterator.hasNext())
		{
			sum = sum + itemIterator.next().getPrice();
		}
		return sum;
	}
	
	public String toString() {
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		ListIterator<Item> itemIterator = this.listIterator();
		String result = "[";
		Item item = null;
		while(itemIterator.hasNext())
		{
			item = itemIterator.next();
			result = result + item.getName() + ";" + item.getID() + ";" + decimalformat.format(item.getPrice());
			if(itemIterator.hasNext())
				result = result + ", ";
		}
		result = result + "]";
		return result;
	}
	
}
