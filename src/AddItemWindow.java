import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddItemWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfID;
	private JTextField tfPret;
	private JButton btnAdaugare;
	private JTextField tfNume;
	private JTextField tfDepID;
	private JLabel lblDepID;
	private JButton btnOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddItemWindow frame = new AddItemWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void check() {
		if(tfNume.getText().equals("") || tfID.getText().equals("") || tfPret.getText().equals("") || tfDepID.getText().equals(""))
			btnAdaugare.setEnabled(false);
		else
			btnAdaugare.setEnabled(true);
	}

	/**
	 * Create the frame.
	 */
	public AddItemWindow() {
		setBackground(Color.WHITE);
		setTitle("Adaugare produs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 240);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfNume = new JTextField();
		tfNume.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void insertUpdate(DocumentEvent e)
			{
				check();
			}
		});
		tfNume.setBounds(38, 65, 120, 20);
		contentPane.add(tfNume);
		tfNume.setColumns(10);
		
		tfID = new JTextField();
		tfID.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void insertUpdate(DocumentEvent e)
			{
				check();
			}
		});
		tfID.setBounds(168, 65, 86, 20);
		contentPane.add(tfID);
		tfID.setColumns(10);
		
		tfPret = new JTextField();
		tfPret.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void insertUpdate(DocumentEvent e)
			{
				check();
			}
		});
		tfPret.setBounds(264, 65, 86, 20);
		contentPane.add(tfPret);
		tfPret.setColumns(10);
		
		JLabel lblNumeProdus = new JLabel("Nume Produs");
		lblNumeProdus.setBounds(58, 40, 100, 14);
		contentPane.add(lblNumeProdus);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(198, 40, 33, 14);
		contentPane.add(lblId);
		
		JLabel lblPret = new JLabel("Pret");
		lblPret.setBounds(296, 40, 33, 14);
		contentPane.add(lblPret);
		
		btnAdaugare = new JButton("Adaugare produs");
		btnAdaugare.setEnabled(false);
		btnAdaugare.setBounds(122, 134, 165, 23);
		btnAdaugare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = tfNume.getText();
				Integer itemID = Integer.parseInt(tfID.getText());
				Integer depID = Integer.parseInt(tfDepID.getText());
				Double price = Double.parseDouble(tfPret.getText());
				Item item = new Item(name, itemID, price);
				UserInterfaceTest home = UserInterfaceTest.getInstance();
				if(home.contains(item))
				{
					createErrorFrame();
					dispose();
				} else {
					home.addItem(item, depID);
					//System.out.println(item.getName() + " " + item.getID() + " " + item.getPrice() + " " + depID);
					dispose();
				}
			}
		});
		contentPane.add(btnAdaugare);
		
		tfDepID = new JTextField();
		tfDepID.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				check();
			}
			
			public void insertUpdate(DocumentEvent e)
			{
				check();
			}
		});
		
		tfDepID.setBounds(168, 96, 86, 20);
		contentPane.add(tfDepID);
		tfDepID.setColumns(10);
		
		lblDepID = new JLabel("ID Departament");
		lblDepID.setBounds(58, 99, 100, 14);
		lblDepID.setBackground(Color.WHITE);
		contentPane.add(lblDepID);
		
	}
	public void createErrorFrame() {
		AddItemWindowError frame = new AddItemWindowError();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(420, 240));
		frame.setResizable(false);
		frame.setLocationRelativeTo(this);
		
		frame.show();
		frame.pack();
	}
}
