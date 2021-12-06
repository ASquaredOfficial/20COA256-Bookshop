import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblBooks;
	private static DefaultTableModel dtmBooks;
	private static ArrayList<Book> listOfBooks;
	private static ArrayList<User> listOfUsers;
	private static ArrayList<Book> basket;
	private static ArrayList<String> listOfISBNs;
	private static ArrayList<AudioBook> listOfAudiobooks;
	private static ArrayList<Book> listOfNonAudioBooks;
	private static String[] userArray;
	private static ArrayList<String> basketISBNs;
	@SuppressWarnings("rawtypes") private JComboBox cbAddBookType;
	@SuppressWarnings("rawtypes") private JComboBox cbAddGenre;
	@SuppressWarnings("rawtypes") private JComboBox cbAddLanguage;
	@SuppressWarnings("rawtypes") private JComboBox cbAddInfo2ab;
	@SuppressWarnings("rawtypes") private JComboBox cbAddInfo2eb;
	@SuppressWarnings("rawtypes") private JComboBox cbAddInfo2pb;
	@SuppressWarnings("rawtypes") private JComboBox cbUserEntry;
	private JTextField tfAddISBN;
	private JTextField tfAddTitle;
	private JTextField tfAddDay;
	private JTextField tfAddPrice;
	private JTextField tfAddStock;
	private JTextField tfAddInfo1ab;
	private JTextField tfAddInfo1eb;
	private JTextField tfAddInfo1pb;
	private JTextField tfAddMonth;
	private JTextField tfAddYear;	
	private JTextField tfSearchBook;
	private JTextField tfBuyISBN;
	private JTextField tfBuyQuantity;
	private JTextField tfBuyEmail;
	private JTextField tfBuyCardNo;
	private JTextField tfBuyCSC;
	private JTextField tfBuyConfirmEmail;
	private JTable tblSearchBook;
	DefaultTableModel dtmSearchBook;
	private float basketTotal;
	private JTable tblbasket;
	private DefaultTableModel dtmBasket;
	private DecimalFormat df;
    private static File inputBooks;

	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 */
    
    
    //Fuction to rewrite books into arrayLists
	static void resetBooks() throws FileNotFoundException {
		//Read Stock File
		
		inputBooks = new File("Stock.txt");								//initiate file "StockData" to read -> inputBooks
		listOfBooks.clear();
		listOfISBNs.clear();
		Scanner fileScanner1 = new Scanner(inputBooks);	
		Book book = null;															//initiate book to use later
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		while (fileScanner1.hasNextLine()) {
			String[] bookDet = fileScanner1.nextLine().split(",");
			if (bookDet[1].trim().equals("paperback")) {
				book = new Paperback(bookDet[0].trim(), bookDet[2].trim(), 
						Language.valueOf(bookDet[3].trim()), Genre.valueOf(bookDet[4].trim().replaceAll(" ", "")),
						LocalDate.parse(bookDet[5].trim(), formatter),	Float.parseFloat(bookDet[6].trim()), 
						Integer.parseInt(bookDet[7].trim()), Integer.parseInt(bookDet[8].trim()), Condition.valueOf(bookDet[9].trim()+"Book")); 
			} else if (bookDet[1].trim().equals("audiobook")) {
				book = new AudioBook(bookDet[0].trim(), bookDet[2].trim(), 
						Language.valueOf(bookDet[3].trim()), Genre.valueOf(bookDet[4].trim().replaceAll(" ", "")),
						LocalDate.parse(bookDet[5].trim(), formatter),	Float.parseFloat(bookDet[6].trim()), 
						Integer.parseInt(bookDet[7].trim()), Float.parseFloat(bookDet[8].trim()), AudioFormat.valueOf(bookDet[9].trim())); 
			} else if (bookDet[1].trim().equals("ebook")) {
				book = new eBook(bookDet[0].trim(), bookDet[2].trim(), 
						Language.valueOf(bookDet[3].trim()), Genre.valueOf(bookDet[4].trim().replaceAll(" ", "")),
						LocalDate.parse(bookDet[5].trim(), formatter),	Float.parseFloat(bookDet[6].trim()),
						Integer.parseInt(bookDet[7].trim()), Integer.parseInt(bookDet[8].trim()), eBookFormat.valueOf(bookDet[9].trim())); 
			} 
			
			listOfBooks.add(book);
			listOfISBNs.add(bookDet[0]);
		}
		fileScanner1.close();
		
		//Sort in ascending order of prices
		PriceCompare bkPriceCompare = new PriceCompare();
		Collections.sort(listOfBooks, bkPriceCompare);
		//System.out.println("List of Books:" + listOfBooks + "\n");
	}
	public boolean isStringInt(String s){
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public static void main(String[] args) throws FileNotFoundException, NullPointerException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Make arrayList for all the books, and ISBNs in the Database 
		listOfBooks = new ArrayList<Book>();
		listOfISBNs = new ArrayList<String>();
		listOfUsers = new ArrayList<User>();
		basket = new ArrayList<Book>();
		basketISBNs = new ArrayList<String>();
		
		resetBooks(); //read file
		
		//Read User Files
		inputBooks = new File("UserAccounts.txt");								//initiate file "StockData" to read -> inputBooks
		try (Scanner fileScanner2 = new Scanner(inputBooks)) {
			User user = null;
			while (fileScanner2.hasNextLine()) {
				String[] userDet = fileScanner2.nextLine().split(",");
				if (userDet[6].trim().equals("admin")) {
					user = new Admin(Integer.parseInt(userDet[0].trim()), userDet[1].trim(), userDet[2].trim(), 
							new Address(Integer.parseInt(userDet[3].trim()), userDet[4].trim(), userDet[5].trim())); 
				} else if (userDet[6].trim().equals("customer")) {
					user = new Customer(Integer.parseInt(userDet[0].trim()), userDet[1].trim(), userDet[2].trim(), 
							new Address(Integer.parseInt(userDet[3].trim()), userDet[4].trim(), userDet[5].trim())); 
				} 
			listOfUsers.add(user);
			}
			userArray = new String[listOfUsers.size()]; 
			for (int i=0; i<listOfUsers.size(); i++) {
				userArray[i] = listOfUsers.get(i).getUsername();
				//System.out.println(userArray[i]);
			}
			
		}
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainFrame() throws IOException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 864, 239);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Login", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Admin", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome to Adrian's Book Store Website");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblWelcome.setBounds(139, 11, 654, 60);
		panel_1.add(lblWelcome);
		
		cbUserEntry = new JComboBox(userArray);
		cbUserEntry.setFont(cbUserEntry.getFont().deriveFont(cbUserEntry.getFont().getSize() + 25f));
		cbUserEntry.setBounds(271, 73, 339, 74);
		panel_1.add(cbUserEntry);
		
		tfAddISBN = new JTextField();
		tfAddISBN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddISBN.setBounds(140, 40, 230, 20);
		panel_2.add(tfAddISBN);
		tfAddISBN.setColumns(10);
		
		JLabel lblAddISBN = new JLabel("Enter ISBN:");
		lblAddISBN.setBounds(10, 43, 100, 14);
		panel_2.add(lblAddISBN);
		
		JLabel lblAddTitle = new JLabel("Enter Title:");
		lblAddTitle.setBounds(10, 15, 78, 14);
		panel_2.add(lblAddTitle);
		
		JLabel lblAddLanguage = new JLabel("Choose Language:");
		lblAddLanguage.setBounds(10, 71, 132, 14);
		panel_2.add(lblAddLanguage);
		
		tfAddTitle = new JTextField();
		tfAddTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddTitle.setBounds(140, 12, 692, 20);
		panel_2.add(tfAddTitle);
		tfAddTitle.setColumns(10);
		
		JLabel lblAddGenre = new JLabel("Choose Genre:");
		lblAddGenre.setBounds(10, 102, 100, 14);
		panel_2.add(lblAddGenre);
		
		JLabel lblAddDate = new JLabel("Enter Release Date (dd-mm-yyyy):");
		lblAddDate.setBounds(408, 43, 204, 14);
		panel_2.add(lblAddDate);
		
		JLabel lblAddPrice = new JLabel("Enter Price:");
		lblAddPrice.setBounds(408, 74, 138, 14);
		panel_2.add(lblAddPrice);
		
		JLabel lblAddStock = new JLabel("Enter Stock Quantity:");
		lblAddStock.setBounds(408, 105, 146, 14);
		panel_2.add(lblAddStock);
		
		tfAddDay = new JTextField();
		tfAddDay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddDay.setBounds(614, 40, 56, 20);
		panel_2.add(tfAddDay);
		tfAddDay.setColumns(10);
		
		tfAddPrice = new JTextField();
		tfAddPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddPrice.setBounds(614, 71, 218, 20);
		panel_2.add(tfAddPrice);
		tfAddPrice.setColumns(10);
		
		tfAddStock = new JTextField();
		tfAddStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddStock.setBounds(614, 102, 218, 20);
		panel_2.add(tfAddStock);
		tfAddStock.setColumns(10);
		
		JLabel lblAddInfo1 = new JLabel("Additional Info 1:");
		lblAddInfo1.setBounds(10, 145, 146, 14);
		panel_2.add(lblAddInfo1);
		
		JLabel lblAddInfo2 = new JLabel("Additional Info 2:");
		lblAddInfo2.setBounds(10, 174, 100, 14);
		panel_2.add(lblAddInfo2);
		
		tfAddInfo1ab = new JTextField();
		tfAddInfo1ab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddInfo1ab.setBounds(140, 145, 66, 20);
		panel_2.add(tfAddInfo1ab);
		tfAddInfo1ab.setColumns(10);
		
		tfAddInfo1eb = new JTextField();
		tfAddInfo1eb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddInfo1eb.setColumns(10);
		tfAddInfo1eb.setBounds(216, 145, 66, 20);
		panel_2.add(tfAddInfo1eb);
		
		tfAddInfo1pb = new JTextField();
		tfAddInfo1pb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddInfo1pb.setColumns(10);
		tfAddInfo1pb.setBounds(292, 145, 78, 20);
		panel_2.add(tfAddInfo1pb);
		
		JLabel lblAddAudioBook = new JLabel("AudioBook");
		lblAddAudioBook.setBounds(145, 127, 61, 14);
		panel_2.add(lblAddAudioBook);
		
		JLabel lblAddeBook = new JLabel("eBook");
		lblAddeBook.setBounds(226, 127, 46, 14);
		panel_2.add(lblAddeBook);
		
		JLabel lblAddPaperback = new JLabel("Paperback");
		lblAddPaperback.setBounds(299, 127, 71, 14);
		panel_2.add(lblAddPaperback);
		
		JLabel lblChooseType = new JLabel("Choose Book Type:");
		lblChooseType.setBounds(408, 133, 110, 14);
		panel_2.add(lblChooseType);
		
		//Administration Page Drop Down Section
		cbAddGenre = new JComboBox(Genre.values());
		cbAddGenre.setBounds(140, 98, 230, 22);
		panel_2.add(cbAddGenre);
		
		cbAddLanguage = new JComboBox(Language.values());
		cbAddLanguage.setBounds(140, 67, 230, 22);
		panel_2.add(cbAddLanguage);
		
		cbAddBookType = new JComboBox(BookType.values());
		cbAddBookType.setBounds(614, 133, 100, 20);
		panel_2.add(cbAddBookType);
		
		cbAddInfo2ab = new JComboBox(AudioFormat.values());
		cbAddInfo2ab.setBounds(140, 170, 66, 22);
		panel_2.add(cbAddInfo2ab);
		
		cbAddInfo2eb = new JComboBox(eBookFormat.values());
		cbAddInfo2eb.setBounds(216, 170, 66, 22);
		panel_2.add(cbAddInfo2eb);
		
		cbAddInfo2pb = new JComboBox(Condition.values());
		cbAddInfo2pb.setBounds(292, 170, 78, 22);
		panel_2.add(cbAddInfo2pb);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Customer", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblSearchBook = new JLabel("Search For Book by Title:");
		lblSearchBook.setBounds(20, 11, 156, 14);
		panel_3.add(lblSearchBook);
		
		tfSearchBook = new JTextField();
		tfSearchBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfSearchBook.setBounds(179, 8, 292, 20);
		panel_3.add(tfSearchBook);
		tfSearchBook.setColumns(10);
		
		JLabel lblAddBasket = new JLabel("Add to Basket");
		lblAddBasket.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddBasket.setBounds(20, 33, 108, 14);
		panel_3.add(lblAddBasket);
		
		JLabel lblBuyISBN = new JLabel("Enter ISBN for Book:");
		lblBuyISBN.setBounds(20, 50, 121, 14);
		panel_3.add(lblBuyISBN);
		
		tfBuyISBN = new JTextField();
		tfBuyISBN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyISBN.setBounds(151, 45, 213, 20);
		panel_3.add(tfBuyISBN);
		tfBuyISBN.setColumns(10);
		
		JLabel lblBuyQuantity = new JLabel("Enter Quantity:");
		lblBuyQuantity.setBounds(20, 75, 89, 14);
		panel_3.add(lblBuyQuantity);
		
		tfBuyQuantity = new JTextField();
		tfBuyQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyQuantity.setBounds(151, 70, 86, 20);
		panel_3.add(tfBuyQuantity);
		tfBuyQuantity.setColumns(10);
				
		JLabel lblNewLabel = new JLabel("Payment");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(20, 99, 89, 14);
		panel_3.add(lblNewLabel);
		
		JLabel lblPaypal = new JLabel("PayPal");
		lblPaypal.setFont(new Font("Verdana Pro", Font.BOLD | Font.ITALIC, 12));
		lblPaypal.setBounds(20, 117, 75, 14);
		panel_3.add(lblPaypal);
		
		JLabel lblEnterEmail = new JLabel("Email:");
		lblEnterEmail.setBounds(20, 140, 46, 14);
		panel_3.add(lblEnterEmail);
		
		tfBuyEmail = new JTextField();
		tfBuyEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyEmail.setBounds(104, 135, 138, 20);
		panel_3.add(tfBuyEmail);
		tfBuyEmail.setColumns(10);
		
		JLabel lblCreditCard = new JLabel("Credit Card");
		lblCreditCard.setFont(new Font("Verdana Pro", Font.BOLD | Font.ITALIC, 12));
		lblCreditCard.setBounds(252, 117, 108, 14);
		panel_3.add(lblCreditCard);
		
		JLabel lblNewLabel_4 = new JLabel("Card No:");
		lblNewLabel_4.setBounds(252, 140, 75, 14);
		panel_3.add(lblNewLabel_4);
		
		tfBuyCardNo = new JTextField();
		tfBuyCardNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyCardNo.setBounds(337, 135, 137, 20);
		panel_3.add(tfBuyCardNo);
		tfBuyCardNo.setColumns(10);
		
		JLabel lblCSC = new JLabel("CSC:");
		lblCSC.setBounds(252, 162, 46, 14);
		panel_3.add(lblCSC);
		
		tfBuyCSC = new JTextField();
		tfBuyCSC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyCSC.setBounds(337, 159, 137, 20);
		panel_3.add(tfBuyCSC);
		tfBuyCSC.setColumns(10);
		
		JCheckBox chckbxPaypal = new JCheckBox("Pay by PayPal");
		chckbxPaypal.setBounds(104, 115, 111, 20);
		panel_3.add(chckbxPaypal);
		
		JButton btnViewBooksCustomer = new JButton("View Books");
		btnViewBooksCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnViewBooksCustomer.setBounds(620, 7, 108, 23);
		panel_3.add(btnViewBooksCustomer);
		
		JLabel lblConfirmEmail_1 = new JLabel("Confirm Email:");
		lblConfirmEmail_1.setBounds(20, 162, 89, 14);
		panel_3.add(lblConfirmEmail_1);
		
		tfBuyConfirmEmail = new JTextField();
		tfBuyConfirmEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuyConfirmEmail.setColumns(10);
		tfBuyConfirmEmail.setBounds(104, 162, 138, 20);
		panel_3.add(tfBuyConfirmEmail);
		
		JCheckBox chckbxPayByCredit = new JCheckBox("Pay by Credit Card");
		chckbxPayByCredit.setBounds(338, 115, 133, 20);
		panel_3.add(chckbxPayByCredit);

		JTextPane tpCustomerResponse = new JTextPane();
		tpCustomerResponse.setEditable(false);
		tpCustomerResponse.setBounds(330, 185, 519, 20);
		panel_3.add(tpCustomerResponse);
		
		JLabel lblBasketTotal = new JLabel("Basket Total");
		lblBasketTotal.setBounds(392, 49, 76, 14);
		panel_3.add(lblBasketTotal);
		
		JLabel lblNewLabel_2 = new JLabel("----------------------");
		lblNewLabel_2.setBounds(382, 59, 89, 14);
		panel_3.add(lblNewLabel_2);
		
		JTextPane tpBasketTotal = new JTextPane();
		tpBasketTotal.setEditable(false);
		tpBasketTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tpBasketTotal.setBounds(383, 70, 86, 20);
		panel_3.add(tpBasketTotal);
		
		JButton btnSignOut_1 = new JButton("Sign Out");
		btnSignOut_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnSignOut_1.setBounds(738, 7, 111, 23);
		panel_3.add(btnSignOut_1);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("View Books", null, panel_4, null);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 749, 189);
		panel_4.add(scrollPane);
		
		JTextPane lblTextAdmin = new JTextPane();
		lblTextAdmin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTextAdmin.setEditable(false);
		lblTextAdmin.setBounds(408, 153, 186, 39);
		panel_2.add(lblTextAdmin);

		JButton btnNewButton_1 = new JButton("Add to Basket");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int decrementStock = Integer.parseInt(tfBuyQuantity.getText().trim());
				int quantity = decrementStock;
						
				if (listOfISBNs.contains(tfBuyISBN.getText().trim())) {
					//if ISBNs in Stock
					for (int i=0; i<listOfBooks.size(); i++) {
						Book bsktBook = listOfBooks.get(i);
						int StockAmount = bsktBook.getStock();
						
						if (bsktBook.getISBN().equals(tfBuyISBN.getText().trim()) && quantity<=StockAmount && quantity > 0){
							//If valid quantity, then Decrease Stock in arrayList
							Book bk = null;	
							bsktBook.decreaseStock(decrementStock);
							tpCustomerResponse.setText(decrementStock + " copies of '"+bsktBook.getTitle()+"' has been added to basket!");
							
							
							if (basketISBNs.contains(tfBuyISBN.getText())) {
								//if book already in basket, increase basket quantity
								for (int j=0; j<basket.size(); j++) {
									bk = basket.get(j);
									if (tfBuyISBN.getText().equals(bk.getISBN())){
										bk.increaseStock(quantity);
										tpCustomerResponse.setText("Quantity of '"+bk.getTitle()+"' has increased by " + quantity);
									}
								}	
							} 
							else {
								//If not already in basket, add to Basket arrayList and in the stock text file
								if (bsktBook instanceof eBook){
									bk = new eBook(bsktBook.getISBN(), bsktBook.getTitle(), bsktBook.getLanguage(), 
											bsktBook.getGenre(), bsktBook.getDate(), bsktBook.getRetailPrice(), 
											quantity, ((eBook) bsktBook).getNoOfPages(), ((eBook)bsktBook).getFormat());
								} 
								else if (bsktBook instanceof AudioBook){
									bk = new AudioBook(bsktBook.getISBN(), bsktBook.getTitle(), bsktBook.getLanguage(), 
											bsktBook.getGenre(), bsktBook.getDate(), bsktBook.getRetailPrice(), 
											quantity, ((AudioBook)bsktBook).getListeningLength(), ((AudioBook)bsktBook).getFormat());
								} 
								else if (bsktBook instanceof Paperback){
									bk = new Paperback(bsktBook.getISBN(), bsktBook.getTitle(), bsktBook.getLanguage(), 
											bsktBook.getGenre(), bsktBook.getDate(), bsktBook.getRetailPrice(), 
											quantity, ((Paperback) bsktBook).getNoOfPages(), ((Paperback)bsktBook).getCondition());
								} 
								basket.add(bk); //append book to basket ArrayList
								basketISBNs.add(bsktBook.getISBN()); //append book ISBN to basketISBN ArrayList
								tpCustomerResponse.setText("Added '"+bk.getTitle()+"' to basket!!");
							}
							
							basketTotal = basketTotal + (quantity* bsktBook.getRetailPrice());
							df = new DecimalFormat();
							df.setMaximumFractionDigits(2);
							tpBasketTotal.setText("£"+df.format(basketTotal));
							
							//Update Basket Table
							if (dtmBasket.getRowCount() > 0) {
							    for (int k = dtmBasket.getRowCount() - 1; k > -1; k--) {
							        dtmBasket.removeRow(k);
							    }
							}
							Object[] basketData = null;
							for (Book novel : basket) {
								basketData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getStock(), novel.getRetailPrice()}; 
								dtmBasket.addRow(basketData);
							}
						} else if(StockAmount ==0 ) {
							tpCustomerResponse.setText("Invalid Amount of Books");
						}
					}
				} else {
					tpCustomerResponse.setText("Invalid ISBN");
				}
				
			}
		});
		btnNewButton_1.setBounds(247, 72, 117, 20);
		panel_3.add(btnNewButton_1);
		
		//Search Panel
		JPanel panel = new JPanel();
		tabbedPane.addTab("Search", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 31, 724, 169);
		panel.add(scrollPane_1);
		
		tblSearchBook = new JTable();
		scrollPane_1.setViewportView(tblSearchBook);
		dtmSearchBook = new DefaultTableModel();
		dtmSearchBook.setColumnIdentifiers(new Object[] {"ISBN", "Title", "Language", "Genre", "Release Date", "Retail Price", "Stock", "AddInfo1", "AddInfo2"});
		tblSearchBook.setModel(dtmSearchBook);
		
		JButton btnSearchReturn = new JButton("Return");
		btnSearchReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnSearchReturn.setBounds(744, 177, 105, 23);
		panel.add(btnSearchReturn);
		
		JTextPane tpMatches = new JTextPane();
		tpMatches.setEditable(false);
		tpMatches.setBounds(744, 125, 105, 20);
		panel.add(tpMatches);
		
		JButton btnAddBookReturn = new JButton("Add");
		btnAddBookReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = tblSearchBook.getSelectedRow();
				String value = tblSearchBook.getModel().getValueAt(row, column).toString();
				tabbedPane.setSelectedIndex(2);
				tfBuyISBN.setText(value);
				
			}
		});
		btnAddBookReturn.setBounds(744, 149, 105, 23);
		panel.add(btnAddBookReturn);
		
		JLabel lblNewLabel_1 = new JLabel("Select Book and Press add to auto add to form");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 6, 393, 14);
		panel.add(lblNewLabel_1);
		
		JButton btnGenreFilter = new JButton("Genre Filter");
		btnGenreFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenreCompare bkGenreCompare = new GenreCompare();
				Collections.sort(listOfBooks, bkGenreCompare);
				
				//Delete Rows
				if (dtmSearchBook.getRowCount() > 0) {
				    for (int i = dtmSearchBook.getRowCount() - 1; i > -1; i--) {
				    	dtmSearchBook.removeRow(i);
				    }
				}
				//Add Rows
				Object[] rowQuery = null;
				for (Book novel : listOfBooks) {
					if (novel.getTitle().toLowerCase().contains(tfSearchBook.getText().toLowerCase())) {
						if (novel instanceof eBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
									((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
						}else if (novel instanceof AudioBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
						} else if (novel instanceof Paperback) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
						}
						dtmSearchBook.addRow(rowQuery);
					}
					
				}
			}
		});
		btnGenreFilter.setBounds(744, 28, 105, 23);
		panel.add(btnGenreFilter);
		
		JButton btnAudio = new JButton("Audio Filter");
		btnAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sort By AudioBook (sorted as: audio, eBook, paperback)
				Book ifAudio = null;
				Book ifNonAudio = null;
				listOfAudiobooks = new ArrayList<AudioBook>();
				listOfNonAudioBooks = new ArrayList<Book>();
				BookTypeCompare bkTypeCompare = new BookTypeCompare();	//Sort By Book Type First
				Collections.sort(listOfBooks, bkTypeCompare);
				
				for (Book book : listOfBooks) {
					if (book instanceof AudioBook) {
						ifAudio = new AudioBook(book.getISBN(), book.getTitle(), book.getLanguage(),
								book.getGenre(), book.getDate(), book.getRetailPrice(), book.getStock(),
								((AudioBook) book).getListeningLength(), ((AudioBook) book).getFormat());
						listOfAudiobooks.add((AudioBook) ifAudio);
					} else if (book instanceof eBook){
						ifNonAudio = new eBook(book.getISBN(), book.getTitle(), book.getLanguage(),
								book.getGenre(), book.getDate(), book.getRetailPrice(), book.getStock(),
								((eBook) book).getNoOfPages(), ((eBook) book).getFormat());
						listOfNonAudioBooks.add(ifNonAudio);
					} else if (book instanceof Paperback){
						ifNonAudio = new Paperback(book.getISBN(), book.getTitle(), book.getLanguage(), 
								book.getGenre(), book.getDate(), book.getRetailPrice(), 
								book.getStock(), ((Paperback) book).getNoOfPages(), ((Paperback)book).getCondition());
						listOfNonAudioBooks.add(ifNonAudio);
					} 
				}
				AudioCompare bkAudioCompare = new AudioCompare();		//Sort By Listening Length
				Collections.sort(listOfAudiobooks, bkAudioCompare);
				listOfBooks.clear();
				listOfBooks.addAll(listOfAudiobooks); listOfAudiobooks.clear();
				listOfBooks.addAll(listOfNonAudioBooks); listOfNonAudioBooks.clear();
				
				//Delete Rows
				if (dtmSearchBook.getRowCount() > 0) {
				    for (int i = dtmSearchBook.getRowCount() - 1; i > -1; i--) {
				    	dtmSearchBook.removeRow(i);
				    }
				}
				//Add Rows
				Object[] rowQuery = null;
				for (Book novel : listOfBooks) {
					if (novel.getTitle().toLowerCase().contains(tfSearchBook.getText().toLowerCase())) {
						if (novel instanceof eBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
									((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
						}else if (novel instanceof AudioBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
						} else if (novel instanceof Paperback) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
						}
						dtmSearchBook.addRow(rowQuery);
					}
					
				}
			}
		});
		btnAudio.setBounds(744, 60, 105, 23);
		panel.add(btnAudio);
		
		JButton btnDoubleFilter = new JButton("Double Filter");
		btnDoubleFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Genre Sort First
				GenreCompare bkGenreCompare = new GenreCompare();
				Collections.sort(listOfBooks, bkGenreCompare);
				
				//AudioBook Sort second
				Book ifAudio = null;
				Book ifNonAudio = null;
				listOfAudiobooks = new ArrayList<AudioBook>();
				listOfNonAudioBooks = new ArrayList<Book>();
				BookTypeCompare bkTypeCompare = new BookTypeCompare();	//Sort By Book Type First
				Collections.sort(listOfBooks, bkTypeCompare);
				
				for (Book book : listOfBooks) {
					if (book instanceof AudioBook) {
						ifAudio = new AudioBook(book.getISBN(), book.getTitle(), book.getLanguage(),
								book.getGenre(), book.getDate(), book.getRetailPrice(), book.getStock(),
								((AudioBook) book).getListeningLength(), ((AudioBook) book).getFormat());
						listOfAudiobooks.add((AudioBook) ifAudio);
					} else if (book instanceof eBook){
						ifNonAudio = new eBook(book.getISBN(), book.getTitle(), book.getLanguage(),
								book.getGenre(), book.getDate(), book.getRetailPrice(), book.getStock(),
								((eBook) book).getNoOfPages(), ((eBook) book).getFormat());
						listOfNonAudioBooks.add(ifNonAudio);
					} else if (book instanceof Paperback){
						ifNonAudio = new Paperback(book.getISBN(), book.getTitle(), book.getLanguage(), 
								book.getGenre(), book.getDate(), book.getRetailPrice(), 
								book.getStock(), ((Paperback) book).getNoOfPages(), ((Paperback)book).getCondition());
						listOfNonAudioBooks.add(ifNonAudio);
					} 
				}
				AudioCompare bkAudioCompare = new AudioCompare();		//Sort By Listening Length
				Collections.sort(listOfAudiobooks, bkAudioCompare);
				listOfBooks.clear();
				listOfBooks.addAll(listOfAudiobooks); listOfAudiobooks.clear();
				listOfBooks.addAll(listOfNonAudioBooks); listOfNonAudioBooks.clear();
				
				//Delete Rows
				if (dtmSearchBook.getRowCount() > 0) {
				    for (int i = dtmSearchBook.getRowCount() - 1; i > -1; i--) {
				    	dtmSearchBook.removeRow(i);
				    }
				}
				//Add Rows
				Object[] rowQuery = null;
				for (Book novel : listOfBooks) {
					if (novel.getTitle().toLowerCase().contains(tfSearchBook.getText().toLowerCase())) {
						if (novel instanceof eBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
									((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
						}else if (novel instanceof AudioBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
						} else if (novel instanceof Paperback) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
						}
						dtmSearchBook.addRow(rowQuery);
					}
					
				}
				
			}
		});
		btnDoubleFilter.setBounds(744, 94, 105, 23);
		panel.add(btnDoubleFilter);
		
		JButton btnCancelBasket = new JButton("Cancel Basket");
		btnCancelBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Write Cancelled transaction into Activity Log
				for (int i=0; i<listOfUsers.size(); i++) {
					if((cbUserEntry.getSelectedItem().equals(listOfUsers.get(i).getUsername()))) {
						User user = listOfUsers.get(i);
						BufferedWriter bw = null;
						
						for (Book novel : basket) {
							Transaction tAction = new Cancelled(user.getUserId(), user.getUserAddr().getPostcode(), novel.getISBN(), novel.getRetailPrice(), novel.getStock());
							try {
								bw = new BufferedWriter(new FileWriter("ActivityLog.txt", true)); //add false to overwrite, add true to append
								bw.write("\n"+tAction.toString());
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							
						}
					}
				}
								
				//Delete all rows on the basket table
				basket.clear();
				basketISBNs.clear();
				basketTotal = 0;
				tpBasketTotal.setText("£"+df.format(basketTotal));
				tpCustomerResponse.setText("Basket has been cleared");				
				if (dtmBasket.getRowCount() > 0) {
				    for (int k = dtmBasket.getRowCount() - 1; k > -1; k--) {
				        dtmBasket.removeRow(k);
				    }
				}
				try {
					resetBooks();
					//Delete Rows
					if (dtmSearchBook.getRowCount() > 0) {
					    for (int i = dtmSearchBook.getRowCount() - 1; i > -1; i--) {
					    	dtmSearchBook.removeRow(i);
					    }
					}
					//Add Rows
					Object[] rowQuery = null;
					for (Book novel : listOfBooks) {
						if (novel.getTitle().toLowerCase().contains(tfSearchBook.getText().toLowerCase())) {
							if (novel instanceof eBook) {
								rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
										novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
										((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
							}else if (novel instanceof AudioBook) {
								rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
										novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
										((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
							} else if (novel instanceof Paperback) {
								rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
										novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
										((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
							}
							dtmSearchBook.addRow(rowQuery);
						}
						
					}
					tpMatches.setText("");
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		btnCancelBasket.setBounds(20, 185, 145, 20);
		panel_3.add(btnCancelBasket);
		panel_1.setLayout(null);
		
		//Pay for Items
		JButton btnPayNow = new JButton("Pay Now");
		btnPayNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedWriter bw = null;
				if (chckbxPayByCredit.isSelected() && chckbxPaypal.isSelected()){
					//If Both are selected, print error message
					tpCustomerResponse.setText("Please Select One Payment Type");
				} else if (!(chckbxPayByCredit.isSelected() || chckbxPaypal.isSelected())) {
					//If Neither are selected, print error message
					tpCustomerResponse.setText("Please Check if all details are correct");
				}
				
				else {
					//Update ActivityLog
					for (int i=0; i<listOfUsers.size(); i++) {
						if((cbUserEntry.getSelectedItem().equals(listOfUsers.get(i).getUsername()))) {
							User user = listOfUsers.get(i);
							PaymentType type = null;
							boolean successfullCheckout = true;
							
							for (Book novel : basket) {
								//Choose Payment Type
								
								if (chckbxPayByCredit.isSelected())
									//Validate Credit Card Details
									if (isStringInt(tfBuyCardNo.getText()) && isStringInt(tfBuyCSC.getText()) && (tfBuyCardNo.getText().trim().length() == 6) && (tfBuyCSC.getText().trim().length() == 3)) {
										type = PaymentType.CreditCard;
										Transaction tAction = new Purchased(user.getUserId(), user.getUserAddr().getPostcode(), novel.getISBN(), novel.getRetailPrice(), novel.getStock(), type);
										try {
											bw = new BufferedWriter(new FileWriter("ActivityLog.txt", true)); //add false to overwrite, add true to append
											bw.write("\n"+tAction.toString());
											bw.close();
											successfullCheckout = true;
											tpCustomerResponse.setText("Successfull Checkout");
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									} else {
										tpCustomerResponse.setText("Ensure Card No and CSC are valid");
										successfullCheckout = false;
									}
								else if (chckbxPaypal.isSelected()) 
									//Validate PayPal Details
									if ((tfBuyEmail.getText().equals(tfBuyConfirmEmail.getText()) && !tfBuyEmail.getText().equals(""))) {
										type = PaymentType.PayPal;
										Transaction tAction = new Purchased(user.getUserId(), user.getUserAddr().getPostcode(), novel.getISBN(), novel.getRetailPrice(), novel.getStock(), type);
										try {
											bw = new BufferedWriter(new FileWriter("ActivityLog.txt", true)); //add false to overwrite, add true to append
											bw.write("\n"+tAction.toString());
											bw.close();
											successfullCheckout = true;
											tpCustomerResponse.setText("Successfull Checkout");
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									} else {
										tpCustomerResponse.setText("Please ensure the Emails are matching");
										successfullCheckout = false;
									}
								
								if (successfullCheckout) {
									tpCustomerResponse.setText("Checkout Successfull");
								}
							}
						}
					}
					//Update StockData
					try {
						bw = new BufferedWriter(new FileWriter("Stock.txt", false)); //add false to overwrite, add true to append
						for (int k=0; k<listOfBooks.size(); k++) {
							//System.out.println(listOfBooks.get(k).toString());
							bw.write(listOfBooks.get(k).toString()+"\n");
						}
						bw.close();
						basket.clear();
						basketISBNs.clear();
						basketTotal = 0;
						tpBasketTotal.setText("£"+df.format(basketTotal));			
						if (dtmBasket.getRowCount() > 0) {
						    for (int k = dtmBasket.getRowCount() - 1; k > -1; k--) {
						        dtmBasket.removeRow(k);
						    }
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally {
						
					}
				
				}
				
			}
		});
		btnPayNow.setBounds(175, 185, 145, 20);
		panel_3.add(btnPayNow);
		
		//Sign In
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	//Switch to tab3		
				for (int i=0; i<listOfUsers.size(); i++) {
					if((cbUserEntry.getSelectedItem().equals(listOfUsers.get(i).getUsername()))) {
						
						if (listOfUsers.get(i) instanceof Admin) {
							//If User is Admin, go to admin page
							tabbedPane.setSelectedIndex(1);
							lblTextAdmin.setText("Welcome back " + listOfUsers.get(i).getUsername());
							
						}
						else if (listOfUsers.get(i) instanceof Customer) {
							//If User is customer, go to customer page
							tabbedPane.setSelectedIndex(2);
							tpCustomerResponse.setText("Welcome back " + listOfUsers.get(i).getUsername());
						}						
					}
				}
			}
		});
		btnSignIn.setBounds(316, 158, 253, 42);
		panel_1.add(btnSignIn);
		
		//Update the Stock Data
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedWriter bw = null;
				
				//If valid ISBN
				if (tfAddISBN.getText().trim().matches("-?\\d+(\\.\\d+)?") && tfAddISBN.getText().trim().length() == 8) {
					//If book already exists increase quantity
					if (listOfISBNs.contains(tfAddISBN.getText().trim())) {	
						//System.out.println("Element '" + tfAddISBN.getText().trim() + "' in list"); //to print in console if book is in list
						for (int i=0; i<listOfBooks.size(); i++) {
							Book rndBook = listOfBooks.get(i);
							int incrementStock = Integer.parseInt(tfAddStock.getText().trim());
							
							if (rndBook.getISBN().toString().equals(tfAddISBN.getText().trim())){
								rndBook.increaseStock(incrementStock);
								lblTextAdmin.setText("Increased stock of '"+rndBook.getTitle()+"' by "+ incrementStock);
							}
						}
					} 
					
					//If the book doesn't Exist already
					else {						
						try {
							bw = new BufferedWriter(new FileWriter("Stock.txt", true)); //add false to overwrite, remove false to append
							System.out.println("Attempt to write into File");
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							Book book = null;
							//add book to the database, via Arraylist and in the stock text file
							if (cbAddBookType.getSelectedItem() == BookType.eBook){
								book = new eBook(tfAddISBN.getText(), tfAddTitle.getText(), (Language)cbAddLanguage.getSelectedItem(), 
										(Genre)cbAddGenre.getSelectedItem(), LocalDate.parse(tfAddDay.getText()+"-"+tfAddMonth.getText()+"-"+tfAddYear.getText(), formatter), 
										Float.parseFloat(tfAddPrice.getText()), Integer.parseInt(tfAddStock.getText()), Integer.parseInt(tfAddInfo1eb.getText()), 
										(eBookFormat)cbAddInfo2eb.getSelectedItem());	
								
							} 
							else if (cbAddBookType.getSelectedItem() == BookType.AudioBook){
								book = new AudioBook(tfAddISBN.getText(), tfAddTitle.getText(), (Language)cbAddLanguage.getSelectedItem(), 
										(Genre)cbAddGenre.getSelectedItem(), LocalDate.parse(tfAddDay.getText()+"-"+tfAddMonth.getText()+"-"+tfAddYear.getText(), formatter), 
										Float.parseFloat(tfAddPrice.getText()), Integer.parseInt(tfAddStock.getText()), Float.parseFloat(tfAddInfo1ab.getText()), 
										(AudioFormat)cbAddInfo2ab.getSelectedItem());
							} 
							else if (cbAddBookType.getSelectedItem() == BookType.Paperback){
								book = new Paperback(tfAddISBN.getText(), tfAddTitle.getText(), (Language)cbAddLanguage.getSelectedItem(), 
										(Genre)cbAddGenre.getSelectedItem(), LocalDate.parse(tfAddDay.getText()+"-"+tfAddMonth.getText()+"-"+tfAddYear.getText(), formatter), 
										Float.parseFloat(tfAddPrice.getText()), Integer.parseInt(tfAddStock.getText()), Integer.parseInt(tfAddInfo1pb.getText()), 
										(Condition)cbAddInfo2pb.getSelectedItem());	
							} 
							bw.write("\n"+book.toString());
							System.out.println(book.toString());
							
							listOfBooks.add(book);
							listOfISBNs.add(tfAddISBN.getText());
							PriceCompare bkPriceCompare = new PriceCompare();
							Collections.sort(listOfBooks, bkPriceCompare);
							lblTextAdmin.setText("Successfully Added added '"+book.getTitle()+"'");
							bw.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}				
					} 
				} else {
					lblTextAdmin.setText("An error has occurred!!!");
				}
													
			}
		});
		btnAddBook.setBounds(724, 133, 108, 20);
		panel_2.add(btnAddBook);

		tfAddMonth = new JTextField();
		tfAddMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddMonth.setColumns(10);
		tfAddMonth.setBounds(690, 40, 56, 20);
		panel_2.add(tfAddMonth);
		
		tfAddYear = new JTextField();
		tfAddYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfAddYear.setColumns(10);
		tfAddYear.setBounds(766, 40, 66, 20);
		panel_2.add(tfAddYear);
		
		JLabel dateDash1 = new JLabel("-");
		dateDash1.setFont(new Font("Tahoma", Font.BOLD, 14));
		dateDash1.setBounds(675, 40, 20, 14);
		panel_2.add(dateDash1);
		
		JLabel dateDash2 = new JLabel("-");
		dateDash2.setFont(new Font("Tahoma", Font.BOLD, 14));
		dateDash2.setBounds(755, 40, 20, 14);
		panel_2.add(dateDash2);
		
		JButton btnViewBooksAdmin = new JButton("View Books");
		btnViewBooksAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnViewBooksAdmin.setBounds(724, 164, 108, 28);
		panel_2.add(btnViewBooksAdmin);
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnSignOut.setBounds(614, 164, 100, 28);
		panel_2.add(btnSignOut);
		
		
		//Creating Viewing table
		tblBooks = new JTable();
		scrollPane.setViewportView(tblBooks);
		dtmBooks = new DefaultTableModel();
		dtmBooks.setColumnIdentifiers(new Object[] {"ISBN", "Title", "Language", "Genre", "Release Date", "Retail Price", "Stock", "AddInfo1", "AddInfo2"});
		tblBooks.setModel(dtmBooks);
		
		Object[] rowData = null;
		for (Book novel : listOfBooks) {
			if (novel instanceof eBook) {
				rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
						((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
			} else if (novel instanceof AudioBook) {
				rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
						((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
			} else if (novel instanceof Paperback) {
				rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
						((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
			}
			dtmBooks.addRow(rowData);
		}
		
		//Refresh View
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(769, 11, 80, 23);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Refresh button
				if (dtmBooks.getRowCount() > 0) {
				    for (int i = dtmBooks.getRowCount() - 1; i > -1; i--) {
				        dtmBooks.removeRow(i);
				    }
				}
				Object[] rowData = null;
				for (Book novel : listOfBooks) {
					if (novel instanceof eBook) {
						rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
								novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
								((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
					} else if (novel instanceof AudioBook) {
						rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
								novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
								((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
					} else if (novel instanceof Paperback) {
						rowData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
								novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
								((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
					}
					dtmBooks.addRow(rowData);
				}
			}
		});
		
		panel_4.add(btnRefresh);
		JButton btnUserReturn = new JButton("Return");
		btnUserReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<listOfUsers.size(); i++) {
					if((cbUserEntry.getSelectedItem().equals(listOfUsers.get(i).getUsername()))) {
						
						if (listOfUsers.get(i) instanceof Admin) {
							//If User is Admin, go to admin page
							tabbedPane.setSelectedIndex(1);
						}
						else if (listOfUsers.get(i) instanceof Customer) {
							//If User is customer, go to customer page
							tabbedPane.setSelectedIndex(2);
						}						
					}
				}
			}
		});
		btnUserReturn.setBounds(769, 177, 80, 23);
		panel_4.add(btnUserReturn);


		JButton btnNewButton = new JButton("Search for Book");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Delete Rows
				if (dtmSearchBook.getRowCount() > 0) {
				    for (int i = dtmSearchBook.getRowCount() - 1; i > -1; i--) {
				    	dtmSearchBook.removeRow(i);
				    }
				}
				//Add Rows
				Object[] rowQuery = null;
				for (Book novel : listOfBooks) {
					if (novel.getTitle().toLowerCase().contains(tfSearchBook.getText().toLowerCase())) {
						if (novel instanceof eBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
									((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
						}else if (novel instanceof AudioBook) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
						} else if (novel instanceof Paperback) {
							rowQuery = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
									novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
									((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
						}
						dtmSearchBook.addRow(rowQuery);
					}
					
				}
				tpMatches.setText(dtmSearchBook.getRowCount() + " Found!!");
				tabbedPane.setSelectedIndex(4);
			}
		});
		btnNewButton.setBounds(481, 7, 129, 23);
		panel_3.add(btnNewButton);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(478, 50, 371, 128);
		panel_3.add(scrollPane_2);
		
		tblbasket = new JTable();
		scrollPane_2.setViewportView(tblbasket);
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"ISBN", "Title", "Quantity", "Retail Price"});
		tblbasket.setModel(dtmBasket);
		
		Object[] basketData = null;
		for (Book novel : basket) {
			if (novel instanceof eBook) {
				basketData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(), 
						((eBook) novel).getNoOfPages(), ((eBook) novel).getFormat()}; 
			} else if (novel instanceof AudioBook) {
				basketData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
						((AudioBook) novel).getListeningLength(), ((AudioBook) novel).getFormat()}; 
			} else if (novel instanceof Paperback) {
				basketData = new Object[] {novel.getISBN(), novel.getTitle(), novel.getLanguage(), 
						novel.getGenre(), novel.getDate(), novel.getRetailPrice(), novel.getStock(),
						((Paperback) novel).getNoOfPages(), ((Paperback) novel).getCondition()}; 
			}
			dtmBasket.addRow(basketData);
		}
	}
}
