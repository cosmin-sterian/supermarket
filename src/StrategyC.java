import java.util.*;

public class StrategyC implements Strategy {

	@Override
	public Item execute(WishList wishlist) {
		Integer itemID = wishlist.getLastAddedItem();
		ListIterator<Item> itemIterator = wishlist.listIterator();
		Item item = null;
		while(itemIterator.hasNext())
		{
			item = itemIterator.next();
			if(item.getID().equals(itemID))
			{
				break;
			}
		}
		return item;
	}

}
