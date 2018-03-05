import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Test {
	
	public static void printGetItem(Item item) {
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		String result = item.getName() + ";" + item.getID() + ";" + decimalformat.format(item.getPrice()) + "\n";
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("result.txt", true);
			writer.write(result);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printGetNotifications(String name) {
		Store store = Store.getInstance();
		Customer customer = null;
		String result = "[";
		for(Customer c : store.getCustomers())
		{
			if(c.getName().equals(name))
			{
				customer = c;
				break;
			}
		}
		for(Notification notification : customer.getNotifications())
		{
			if(result.equals("["))
			{
				result = result + notification.getNotificationType() + ";" + notification.getProductID() + ";" + notification.getDepartmentID();
			} else {
				result = result + ", " + notification.getNotificationType() + ";" + notification.getProductID() + ";" + notification.getDepartmentID();
			}
		}
		result = result + "]\n";
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("result.txt", true);
			writer.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printGetObservers(Integer depID) {
		Store store = Store.getInstance();
		Department department = store.getDepartment(depID);
		String result = "[";
		for(Customer observer : department.getObservers())
		{
			if(result.equals("["))
				result = result + observer.getName();
			else
				result = result + ", " + observer.getName();
		}
		result = result + "]\n";
		//System.out.println(result);
		FileWriter writer = null;
		try {
			writer = new FileWriter("result.txt", true);
			writer.write(result);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printGetTotal(String theList, String name) {
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		Store store = Store.getInstance();
		Customer target = null;
		String result = null;
		for(Customer customer : store.getCustomers())
		{
			if(customer.getName().equals(name))
			{
				target = customer;
				break;
			}
		}
		if(theList.equals("ShoppingCart"))
		{
			//System.out.println(target.getShoppingCart().getTotalPrice());
			
			result = decimalformat.format(target.getShoppingCart().getTotalPrice()) + "\n";
		} else {
			//System.out.println(target.getWishList().getTotalPrice());
			result = decimalformat.format(target.getWishList().getTotalPrice()) + "\n";
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("result.txt", true);
			writer.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printGetItems(String theList, String name) {
		Store store = Store.getInstance();
		Customer target = null;
		String result = null;
		for(Customer customer : store.getCustomers())
		{
			if(customer.getName().equals(name))
			{
				target = customer;
				break;
			}
		}
		if(theList.equals("ShoppingCart"))
		{
			//System.out.println(target.getShoppingCart().toString());
			result = target.getShoppingCart().toString() + "\n";
		} else {
			//System.out.println(target.getWishList().toString());
			result = target.getWishList().toString() + "\n";
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter("result.txt", true);
			writer.write(result);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void debugPrintItems(Store store) {
		ArrayList<Department> departments = store.getDepartments();
		for(Department department : departments)
		{
			System.out.println(department.getName());
			ArrayList<Item> items = department.getItems();
			for(Item item : items)
			{
				System.out.println(item.getName()+";"+item.getID()+";"+item.getPrice());
			}
		}
	}
	
	public static void debugPrintCustomers(Store store) {
		ArrayList<Customer> customers = store.getCustomers();
		for(Customer customer : customers)
		{
			System.out.println(customer.getName() + "|" + customer.getBudget());
		}
	}

	public static void main(String[] args) {
		Store store = Store.getInstance();
		
		try {
			new PrintWriter("result.txt").close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		FileReader stream = null;
		BufferedReader br = null;
		try {
			stream = new FileReader("store.txt");
			br = new BufferedReader(stream);
			store.setName(br.readLine());
			String line = null;
			StringTokenizer st = null;
			Department department;
			while((line = br.readLine()) != null)
			{
				department = null;
				st = new StringTokenizer(line,";");
				String departmentName = st.nextToken();
				Integer departmentID = Integer.parseInt(st.nextToken());
				switch(departmentName)
				{
				case "BookDepartment":
					department = new BookDepartment(departmentName, departmentID);
					break;
				case "MusicDepartment":
					department = new MusicDepartment(departmentName, departmentID);
					break;
				case "SoftwareDepartment":
					department = new SoftwareDepartment(departmentName, departmentID);
					break;
				case "VideoDepartment":
					department = new VideoDepartment(departmentName, departmentID);
					break;
				}
				Integer n = Integer.parseInt(br.readLine());
				for(Integer i = 1; i <= n; i++)
				{
					line = br.readLine();
					st = new StringTokenizer(line,";");
					String itemName = st.nextToken();
					Integer itemID = Integer.parseInt(st.nextToken());
					Double itemPrice = Double.parseDouble(st.nextToken());
					Item item = new Item(itemName, itemID, itemPrice);
					department.addItem(item);
				}
				store.addDepartment(department);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stream != null)
					stream.close();
				if(br != null)
					br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		stream = null;
		br = null;
		try {
			stream = new FileReader("customers.txt");
			br = new BufferedReader(stream);
			String line = null;
			StringTokenizer st = null;
			String name = null;
			Double budget = null;
			String strategy = null;
			Customer customer;
			Integer n = Integer.parseInt(br.readLine());
			for(Integer i = 1; i <= n; i++)
			{
				line = br.readLine();
				st = new StringTokenizer(line,";");
				name = st.nextToken();
				budget = Double.parseDouble(st.nextToken());
				strategy = st.nextToken();
				customer = new Customer(name, strategy);
				customer.newShoppingCart(store.getShoppingCart(budget));
				store.enter(customer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stream != null)
					stream.close();
				if(br != null)
					br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		stream = null;
		br = null;
		try {
			stream = new FileReader("events.txt");
			br = new BufferedReader(stream);
			String line = null;
			StringTokenizer st = null;
			String event = null;
			Integer itemID = null, departmentID = null;
			String theList = null;
			Double price = null;
			String name = null;
			Integer n = Integer.parseInt(br.readLine());
			for(Integer i = 1; i <= n; i++)
			{
				line = br.readLine();
				st = new StringTokenizer(line,";");
				event = st.nextToken();
				Item item;
				switch(event)
				{
				case "addItem":
					itemID = Integer.parseInt(st.nextToken());
					theList = st.nextToken();
					name = st.nextToken();
					
					//System.out.println("Going to add item " + itemID + " to " + name + "'s " +  theList); //-----------------DEBUG
					item = null;
					for(Department department : store.getDepartments())
					{
						for(Item tempItem : department.getItems())
						{
							if(tempItem.getID().equals(itemID))
							{
								item = tempItem.clone();
								break;
							}
						}
						if(item != null)
							break;
					}
					for(Customer customer : store.getCustomers())
					{
						if(customer.getName().equals(name))
						{
							if(theList.equals("ShoppingCart"))
							{
								customer.getShoppingCart().add(item);
								if(customer.getWishList().contains(item))
								{
									customer.getWishList().remove(item, true);
								}
							}
							else
							{
								customer.getWishList().add(item);
								for(Department department : store.getDepartments())
								{
									for(Item tempItem : department.getItems())
									{
										if(tempItem.equals(item))
										{
											department.addObserver(customer);
											break;
										}
									}
								}
							}
							break;
						}
					}
					item = null;
					break;
				case "delItem":
					itemID = Integer.parseInt(st.nextToken());
					theList = st.nextToken();
					name = st.nextToken();
					//System.out.println("Going to remove Item " + itemID + " from " + name + "'s " + theList); //------------DEBUG
					item = null;
					for(Department department : store.getDepartments())
					{
						for(Item tempItem : department.getItems())
						{
							if(tempItem.getID().equals(itemID))
							{
								item = tempItem.clone();
								break;
							}
							if(item != null)
								break;
						}
					}
					for(Customer customer : store.getCustomers())
					{
						if(customer.getName().equals(name))
						{
							if(theList.equals("ShoppingCart"))
								customer.getShoppingCart().remove(item);
							else
								customer.getWishList().remove(item, true);
							break;
						}
					}
					item = null;
					break;
				case "addProduct":
					departmentID = Integer.parseInt(st.nextToken());
					itemID = Integer.parseInt(st.nextToken());
					price = Double.parseDouble(st.nextToken());
					name = st.nextToken();
					//System.out.println("Going to add product " + name + ", ID: " + itemID + " price: " + price + " to department id: " + departmentID); //-------------DEBUG
					store.getDepartment(departmentID).addItem(new Item(name, itemID, price));
					break;
				case "modifyProduct":
					departmentID = Integer.parseInt(st.nextToken());
					itemID = Integer.parseInt(st.nextToken());
					price = Double.parseDouble(st.nextToken());
					//System.out.println("Going to modify product " + itemID + " from department " + departmentID + " to price " + price); //-------------------DEBUG
					store.getDepartment(departmentID).modifyItem(itemID, price);
					break;
				case "delProduct":
					itemID = Integer.parseInt(st.nextToken());
					//System.out.println("Going to remove product " + itemID); //--------------------------------------------------------------------------------DEBUG
					item = null;
					for(Department department : store.getDepartments())
					{
						for(Item tempItem : department.getItems())
						{
							if(tempItem.getID().equals(itemID))
							{
								item = tempItem;
								department.removeItem(itemID);
								break;
							}
						}
						if(item != null)
							break;
					}
					for(Customer customer : store.getCustomers())
					{
						if(customer.getShoppingCart().contains(item))
							customer.getShoppingCart().remove(item);
						if(customer.getWishList().contains(item))
							customer.getWishList().remove(item, true);
					}
					item = null;
					break;
				case "getItem":
					name = st.nextToken();
					for(Customer customer : store.getCustomers())
					{
						if(customer.getName().equals(name))
						{
							item = customer.getWishList().executeStrategy();
							printGetItem(item);
							break;
						}
					}
					break;//-------------------------------PENTRU BONUS----------------
				case "getItems":
					theList = st.nextToken();
					name = st.nextToken();
					//System.out.println("Going to print " + name + "'s " + theList); //-------------------------------DEBUG
					printGetItems(theList, name);
					break;
				case "getTotal":
					theList = st.nextToken();
					name = st.nextToken();
					printGetTotal(theList, name);
					break;
				case "accept":
					departmentID = Integer.parseInt(st.nextToken());
					name = st.nextToken();
					//System.out.println("Going to accept from depID " + departmentID + " " + name); //--------------------DEBUG
					for(Customer customer : store.getCustomers())
					{
						if(customer.getName().equals(name))
						{
							Department department = store.getDepartment(departmentID);
							department.accept(customer.getShoppingCart());
							break;
						}
					}
					break;
				case "getObservers":
					departmentID = Integer.parseInt(st.nextToken());
					//System.out.println("Going to print observers of departmentID " + departmentID); //---------------------DEBUG
					printGetObservers(departmentID);
					break;
				case "getNotifications":
					name = st.nextToken();
					//System.out.println("Going to print " + name + "'s notifications"); //-----------------------DEBUG
					printGetNotifications(name);
					break;
				default:
					break;
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stream != null)
					stream.close();
				if(br != null)
					br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		//debugPrintItems(store);
		//debugPrintCustomers(store);
	}
}
