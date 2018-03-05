import java.util.*;

public class StrategyA implements Strategy {

	@Override
	public Item execute(WishList wishlist) {
		Item item = null, aux = null;
		ListIterator<Item> itemIterator = wishlist.listIterator();
		while(itemIterator.hasNext())
		{
			aux = itemIterator.next();
			if(item == null)
			{
				item = aux;
			} else if(item.getPrice().compareTo(aux.getPrice())>0) {
				item = aux;
			}
		}
		return item;
	}
	
}
