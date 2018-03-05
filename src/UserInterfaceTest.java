import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UserInterfaceTest extends JFrame {

	private JPanel startPane;
	private JButton btnStart;
	private JPanel itemsListPane;
	private JTable itemsTable;
	private ArrayList<Item> items;
	private Comparator<Item> alfabetic, pretCrescator, pretDescrescator, dupaPret, currentComparator;
	private DefaultTableModel tableModel;
	private final ButtonGroup grupSortare = new ButtonGroup();
	private JButton btnTogglePret;
	private JRadioButton rbPret;
	private JButton btnAdaugare;
	private static UserInterfaceTest instance = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterfaceTest frame = UserInterfaceTest.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public static UserInterfaceTest getInstance() {
		if(instance == null)
			instance = new UserInterfaceTest();
		return instance;
	}
	
	protected UserInterfaceTest() {
		items = new ArrayList<Item>();
		setResizable(false);
		setTitle("Store Startpage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(650, 400));
		setLocationRelativeTo(null);
		startPane = new JPanel();
		startPane.setBackground(Color.WHITE);
		startPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(startPane);
		startPane.setLayout(null);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(208, 250, 167, 45);
		startPane.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readStoreAndCustomers();
				btnStart.setEnabled(false);
			}
		});
		
		JTextPane startInfoText = new JTextPane();
		startInfoText.setBounds(242, 208, 101, 20);
		startPane.add(startInfoText);
		startInfoText.setBackground(Color.WHITE);
		startInfoText.setForeground(Color.BLACK);
		startInfoText.setEditable(false);
		startInfoText.setText("Intra in magazin");
		
		itemsListPane = new JPanel();
		itemsListPane.setBounds(0, 0, 594, 371);
		startPane.add(itemsListPane);
		itemsListPane.setBackground(Color.WHITE);
		itemsListPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		itemsListPane.setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.WHITE);
		
		JLabel lblSortareDupa = new JLabel("Sortare dupa:");
		lblSortareDupa.setBackground(Color.WHITE);
		
		JRadioButton rbNumeProdus = new JRadioButton("Nume produs");
		rbNumeProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentComparator = alfabetic;
				btnTogglePret.setVisible(false);
				sortTable(currentComparator);
			}
		});
		rbNumeProdus.setBackground(Color.WHITE);
		grupSortare.add(rbNumeProdus);
		rbNumeProdus.setSelected(true);
		
		rbPret = new JRadioButton("Pret crescator");
		rbPret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentComparator = dupaPret;
				btnTogglePret.setVisible(true);
				sortTable(currentComparator);
			}
		});
		rbPret.setBackground(Color.WHITE);
		grupSortare.add(rbPret);
		
		btnTogglePret = new JButton("Descrescator");
		btnTogglePret.setVisible(false);
		btnTogglePret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dupaPret == pretCrescator)
				{
					dupaPret = pretDescrescator;
					btnTogglePret.setText("Crescator");
					rbPret.setText("Pret descrescator");
				}
				else
				{
					dupaPret = pretCrescator;
					btnTogglePret.setText("Descrescator");
					rbPret.setText("Pret crescator");
				}
				currentComparator = dupaPret;
				sortTable(currentComparator);
			}
		});
		
		btnAdaugare = new JButton("Adaugare produs");
		btnAdaugare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAddItemWindow();
			}
		});
		GroupLayout gl_itemsListPane = new GroupLayout(itemsListPane);
		gl_itemsListPane.setHorizontalGroup(
			gl_itemsListPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_itemsListPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_itemsListPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSortareDupa)
						.addComponent(rbNumeProdus)
						.addComponent(rbPret, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
						.addComponent(btnTogglePret)
						.addComponent(btnAdaugare))
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		gl_itemsListPane.setVerticalGroup(
			gl_itemsListPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_itemsListPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_itemsListPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_itemsListPane.createSequentialGroup()
							.addComponent(lblSortareDupa)
							.addGap(3)
							.addComponent(rbNumeProdus)
							.addGap(4)
							.addComponent(rbPret)
							.addGap(1)
							.addComponent(btnTogglePret)
							.addPreferredGap(ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
							.addComponent(btnAdaugare)
							.addGap(52))))
		);
		
		itemsTable = new JTable();
		tableModel = new DefaultTableModel(new String[]{"Nume produs", "ID", "Pret"}, 0){
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		itemsTable.setModel(tableModel);
		/*ItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nume produs", "ID", "Pret"
			}
		));*/
		scrollPane.setViewportView(itemsTable);
		itemsListPane.setLayout(gl_itemsListPane);
		
		alfabetic = new Comparator<Item>() {

			@Override
			public int compare(Item arg0, Item arg1) {
				return arg0.getName().compareToIgnoreCase(arg1.getName());
			}
			
		};
		pretCrescator = new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				if(o1.getPrice().doubleValue() > o2.getPrice().doubleValue())
					return 1;
				else {
					if(o1.getPrice().doubleValue() == o2.getPrice().doubleValue())
						return o1.getName().compareTo(o2.getName());
					else
						return -1;
				}
			}
			
		};
		pretDescrescator = new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				return pretCrescator.compare(o2, o1);
			}
			
		};
		dupaPret = pretCrescator;
		currentComparator = alfabetic;
		
	}
	
	public boolean contains(Item item) {
		for(Item i : items)
		{
			if(i.equals(item))
				return true;
		}
		return false;
	}
	
	public void showItemsList() {
		setContentPane(itemsListPane);
		itemsListPane.setVisible(true);
		
		revalidate();
	}
	
	public void createAddItemWindow() {
		AddItemWindow frame = new AddItemWindow();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(420, 240));
		frame.setResizable(false);
		frame.setLocationRelativeTo(this);
		
		frame.show();
		frame.pack();
	}
	
	public void addItem(Item item, Integer depID) {
		Store store = Store.getInstance();
		Department department = store.getDepartment(depID);
		department.addItem(item);
		addItemToTable(item);
	}
	
	public void addItemToTable(Item item) {
		items.add(item);
		sortTable(currentComparator);
	}
	
	public void sortTable(Comparator<Item> comparator) {
		Collections.sort(items, comparator);
		updateTable();
	}
	
	public void updateTable() {
		int n = tableModel.getRowCount();
		Item aux = null;
		while(n > 0)
		{
			tableModel.removeRow(n-1);
			n--;
		}
		n = items.size();
		for(int i = 0; i < n; i++)
		{
			aux = items.get(i);
			tableModel.addRow(new Object[]{aux.getName(), aux.getID(), aux.getPrice()});
		}
	}
	
	public void readStoreAndCustomers() {
		Store store = Store.getInstance();
		
		FileReader stream = null;
		BufferedReader br = null;
		try {
			stream = new FileReader("store.txt");
			br = new BufferedReader(stream);
			String name = br.readLine();
			store.setName(name);
			setTitle(name);
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
					addItemToTable(item);
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
		
		showItemsList();
	}
}
