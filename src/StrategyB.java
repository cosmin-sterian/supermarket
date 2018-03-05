
public class StrategyB implements Strategy {

	@Override
	public Item execute(WishList wishlist) {
		Item item = wishlist.listIterator().next();
		return item;
	}

}
