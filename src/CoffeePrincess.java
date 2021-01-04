import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CoffeePrincess{
	public static void main(String[] args) {
		POS obj = new POS();
	}
}

class POS extends JFrame implements ActionListener{
	//Declare global variables
	static List<ArrayList> userOrder = new ArrayList<>();							//Contains orders in format (Name,Cost,Quantity)
	static int currentType1 = 0;
	static int currentType2 = 0;
	static int currentType3 = 0;
	static JRadioButton payment1, payment2;
	static JButton type1B0, type1B1, type1B2, type1B3, type1B4, type1B5, type1B6;
	static JButton type2B0, type2B1, type2B2, type2B3, type2B4, type2B5, type2B6;	
	static JButton type3B0, type3B1, type3B2, type3B3, type3B4, type3B5, type3B6;
	static JButton[] type1Buttons = { type1B0, type1B1, type1B2, type1B3, type1B4, type1B5, type1B6 }; 
	static JButton[] type2Buttons = { type2B0, type2B1, type2B2, type2B3, type2B4, type2B5, type2B6 }; 
	static JButton[] type3Buttons = { type3B0, type3B1, type3B2, type2B3, type3B4, type3B5, type3B6 }; 
	static JPanel menuTop;
	static JPanel menuBottom;
	static JPanel subMenu;
	static JPanel orderViewer;
	static JCheckBox selectAll;
	
	//Declare global orderViewer components
	static JPanel orderPanel1, orderPanel2, orderPanel3, orderPanel4, orderPanel5, orderPanel6, orderPanel7, orderPanel8, orderPanel9, orderPanel10;
	static JPanel[] orderPanels = {orderPanel1, orderPanel2, orderPanel3, orderPanel4, orderPanel5, orderPanel6, orderPanel7, orderPanel8, orderPanel9, orderPanel10};
	static JButton orderButton1, orderButton2, orderButton3, orderButton4, orderButton5, orderButton6, orderButton7, orderButton8, orderButton9, orderButton10;
	static JButton[] orderButtons = {orderButton1, orderButton2, orderButton3, orderButton4, orderButton5, orderButton6, orderButton7, orderButton8, orderButton9, orderButton10};
	static JTextField orderField1, orderField2, orderField3, orderField4, orderField5, orderField6, orderField7, orderField8, orderField9, orderField10;
	static JTextField[] orderTextFields = {orderField1, orderField2, orderField3, orderField4, orderField5, orderField6, orderField7, orderField8, orderField9, orderField10};
	
	
	static Boolean[] selections = {false, false, false, false, false,
			false, false, false, false, false};
	static JCheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;
	static JCheckBox[] checkBoxes = {cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10};

	/*	Initialize food and cost dictionaries
	 	Type1							> 				Type2						> 		Type 3
	 	Food,Dessert,Drink etc...		>		Coffee, Softdrink, etc...			>  		Latte, Mocha, etc. */
	static String[][][] foodDictionary = {
		{
			{"Drink"},
			{"Coffee","Latte","Cappucino","Caramel Macchiato","Mocha"},
			{"Juice","Mixed Fruit Juice","Apple Juice","Orange Juice"},
			{"Soft Drink","Pepsi","Coca-Cola","Fanta","Sprite", "7 Up", "Miranda","Dr.Pepper"},
		},
		{
			{"Meal"},
			{"Asian","Bibimbap","Kimchi", "Korean Barbeque Chicken", "Cha Gio","Takoyaki","Ramen","Nasi Lemak"},
			{"American","Beef Burger","Chicken Burger","French Fries", "Wings","Onion Rings","Bagels"},
			{"Western","Beans with bread","Sausage","Bacon sandwitch","Shepherd's pie"},
			{"Pizza","Pepperoni Pizza","Island Tuna Pizza","Barbeque Pineapple Pizza","Nasi Lemak Pizza"},
		},
		{
			{"Dessert"},
			{"Savory","Fish and Chips","Scotch Egg","dessert1"},
			{"Sweet","Syllabub","Trifle","Queen of Puddings"},
			{"Vegetarian","Peanut Butter Cookie Bars","Vegan Ice-cream","Cinnamon rolls","Fruit Salad"},
			{"Pies","Apple Pie","Mixed Fruit Pie","Blueberry Coconut Pudding Pie","Pumpkin Pie"},
		}
		};
	static double[][][] costDictionary = {
			{
				{7.8, 7, 10.50, 11.30},
				{3,1,1},
				{5, 5, 5, 5, 5, 5, 5},
			},
			{
				{6, 7, 8, 9, 10, 11 ,12},
				{10,11,12, 13,14,15},
				{21, 22 ,23, 24},
				{31,32,33,34},
			},
			{
				{101,102,103},
				{121,122,123},
				{131,132,133,134},
				{141,142,143,144},
			}
			};
	// Initialize order methods
	public static boolean itemOrdered(int type1, int type2, int type3){
		boolean isExist = false;
		type2++;
		type3++;
		for(int i = 0; i < userOrder.size();i++) {
			if(userOrder.get(i).get(0).equals(foodDictionary[type1][type2][type3])) {
				isExist = true;
			}
			
		}
		return isExist;
	}
	public static void addOrderItem(int type1, int type2, int type3, int quantity){
		Double cost = 0.0;
		cost = costDictionary[type1][type2][type3];
		
		
		type2++;
		type3++;
		
		ArrayList temp = new ArrayList();
		temp.add(foodDictionary[type1][type2][type3]);		
		temp.add(cost);
		temp.add(quantity);

		userOrder.add(new ArrayList<>(temp));
		temp.clear();
		System.out.println("Order = " + userOrder + ", ");
	}
	public static void incrementItemQuantity(int type1, int type2, int type3){
		type2++;
		type3++;
		for(int i = 0; i < userOrder.size();i++) {
			if(userOrder.get(i).get(0).equals(foodDictionary[type1][type2][type3])) {
				int quantity = Integer.valueOf(String.valueOf(userOrder.get(i).get(2)));
				userOrder.get(i).set(2, ++quantity);
			}
			
		}
	}
	public static void updateOrder(int type1, int type2, int type3, int newQuantity){
		type2++;
		type3++;
		
		for(int i = 0; i < userOrder.size();i++) {
			if(userOrder.get(i).get(0).equals(foodDictionary[type1][type2][type3])) {
				userOrder.get(i).set(2, newQuantity);
			}
			
		}
		System.out.println("UPDATED Order = " + userOrder + ", ");
	}

	POS(){
		//Set Initial Parameters
		currentType1 = 0;
		currentType2 = 0;
		
		//Initialize variables
		JPanel header = new JPanel();
		JPanel container = new JPanel();
		JPanel menu = new JPanel();
		menuTop = new JPanel();
		menuBottom = new JPanel();
		subMenu = new JPanel();
		JPanel order = new JPanel();
		
		JPanel headerIcon = new JPanel();
		JPanel headerTitle = new JPanel();			
		JPanel menuType1 = new JPanel();
		JPanel menuType2 = new JPanel();
		JPanel menuType3 = new JPanel();
		
	
		//Create design
		setLayout(new BorderLayout());
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		menu.setLayout(new BorderLayout());
		menuTop.setLayout(new GridLayout(1,0));
		menuBottom.setLayout(new GridLayout(10,1));
		subMenu.setLayout(new GridLayout(13,1));
		order.setLayout(new BorderLayout());
		menu.add(menuTop, BorderLayout.NORTH);
		menu.add(menuBottom);
		container.setLayout(new GridLayout(1,0));
		container.add(menu);
		container.add(subMenu);
		container.add(order);
		add(header, BorderLayout.NORTH);
		add(container);
		
		
			//Header
			headerIcon.setLayout(new BoxLayout(headerIcon, BoxLayout.X_AXIS));
			headerIcon.add(Box.createHorizontalGlue());
			headerIcon.add(new JLabel(new ImageIcon("src\\tiara.png")));
			headerIcon.add(Box.createHorizontalGlue());
			headerTitle.setLayout(new BoxLayout(headerTitle, BoxLayout.X_AXIS));
			headerTitle.add(Box.createHorizontalGlue());
			JLabel Title = new JLabel("Coffee Princess");
			Font bigFont = new Font("Serif", Font.BOLD, 25);
			Title.setFont(bigFont);
			headerTitle.add(Title);
			headerTitle.add(Box.createHorizontalGlue());
			header.add(Box.createRigidArea(new Dimension(0, 20)));
			header.add(headerIcon);
			header.add(headerTitle);
		
			//Menu
			drawType1();
			drawType2();
			drawType3();
			
			//Order			
			JLabel orderText = new JLabel("Check Out", SwingConstants.CENTER);
			Font bigFont2 = new Font("Serif", Font.BOLD, 25);
			orderText.setFont(bigFont2);
			orderText.setForeground(Color.WHITE);
			orderViewer = new JPanel(new GridLayout(10,1));
			JPanel orderContainer = new JPanel(new BorderLayout());
			JPanel orderPayment = new JPanel(new GridLayout(0,2));
			JButton calculate = new JButton("Check out (Calculate)");
			calculate.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					double total = 0;
					List<ArrayList> selectedOrders = new ArrayList<>();
					
					for(int i=0; i<userOrder.size();i++) {
						if(checkBoxes[i].isSelected()) {
							selectedOrders.add(userOrder.get(i));
						}
					}
					System.out.println(userOrder);
					System.out.println(selectedOrders);
					
					for(int i=0; i<selectedOrders.size();i++) {
						String num1 = String.valueOf(selectedOrders.get(i).get(1));
						String num2 = String.valueOf(selectedOrders.get(i).get(2));
						
						total += Double.valueOf(num1) * Double.valueOf(num2);
					}
					
					if(payment1.isSelected()) {
						total = total - (total*0.10);
					}
					
					total*=1.06;
					
					
					JOptionPane.showMessageDialog(POS.this, "The total for your " + selectedOrders.size() + " items is : \nRM" + total, "Check Out", JOptionPane.PLAIN_MESSAGE);
					userOrder.clear();
					orderViewer.removeAll();
					drawOrder();
					orderViewer.repaint();
					orderViewer.revalidate();
				}
				
			});
			order.add(orderText, BorderLayout.NORTH);
			orderContainer.add(orderViewer);
			orderContainer.add(orderPayment, BorderLayout.SOUTH);
			order.add(orderContainer);
			orderViewer.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
//			orderViewer.setBorder(blackline); 
			orderPayment.setBorder(new TitledBorder(new EtchedBorder(), "Payment Method")); 
			payment1 = new JRadioButton("Pay with Boost");
			payment2 = new JRadioButton("Pay with Cash");
			ButtonGroup gp = new ButtonGroup();
			gp.add(payment1);
			gp.add(payment2);
			orderPayment.add(payment1);
			orderPayment.add(payment2);
			order.add(calculate, BorderLayout.SOUTH);

			JPanel info = new JPanel(new GridLayout(1,5));
			info.add(new JLabel("Selected"));
			info.add(new JLabel("Item"));
			info.add(new JLabel("Cost"));
			info.add(new JLabel("Quantity"));
			info.add(new JLabel("[Delete]"));
			orderViewer.add(info);
			
			
		//Coloring
		header.setBackground(Color.decode("#68ed8c"));
		menu.setBackground(Color.decode("#49ab64"));
		subMenu.setBackground(Color.decode("#6fde8d"));
		order.setBackground(Color.decode("#2c7a41"));
		headerIcon.setBackground(Color.decode("#68ed8c"));
		headerTitle.setBackground(Color.decode("#6fded7"));
		menuTop.setBackground(Color.RED);
		menuBottom.setBackground(Color.decode("#49ab64"));
		
		orderViewer.setBackground(Color.WHITE);
		orderPayment.setBackground(Color.YELLOW);
		
		ArrayList type1 = getType1();
		ArrayList type2 = getType2(currentType1);
		ArrayList type3 = getType3(currentType1,currentType2);

		
		for(int i = 0; i < type1.size(); i++) {
			type1Buttons[i].setBackground(Color.decode("#6fde8d"));
		}
		type1Buttons[0].setBackground(Color.decode("#49ab64"));
		
		//Set initial conditions
		payment1.setSelected(true);
		
		
		//Add action listeners
		for(int i = 0; i < type1.size(); i++) {
			type1Buttons[i].addActionListener(this);
		}
		for(int i = 0; i < type2.size(); i++) {
			type2Buttons[i].addActionListener(this);
		}
		for(int i = 0; i < type3.size(); i++) {
			type3Buttons[i].addActionListener(this);
		}
		// Page Configurations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Princess Coffee | Make an Order!");
        setVisible(true);
        setSize(850,600);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Update Menu
		ArrayList type1 = getType1();
		ArrayList type2 = getType2(currentType1);
		ArrayList type3 = getType3(currentType1, currentType2);

		for(int i = 0; i < type1.size(); i++) {
			if(arg0.getSource() == type1Buttons[i]){
				currentType1 = i;
			}
		}
		for(int i=0; i<type2.size(); i++) {
			if(arg0.getSource() == type2Buttons[i]) {
				currentType2 = i;
			}
		}
		for(int i=0;i<type3.size();i++) {
			if(arg0.getSource() == type3Buttons[i]) {
				currentType3 = i;			
				
				if(itemOrdered(currentType1, currentType2, currentType3) ){
					incrementItemQuantity(currentType1,currentType2,currentType3);
				} else {
					addOrderItem(currentType1,currentType2,currentType3,1);
				}
			}
		}
		
		//Handle Order
		orderViewer.removeAll();
		drawOrder();
		orderViewer.repaint();
		orderViewer.revalidate();

		//Redraw Menu
		for(int i = 0; i < type1.size(); i++) {
			type1Buttons[i].setBackground(Color.decode("#6fde8d"));
			if(i == currentType1) {
				type1Buttons[i].setBackground(Color.decode("#49ab64"));

				//Change MenuType2
				menuBottom.removeAll();	
				type2 =  getType2(currentType1);
				if(type2.size() -1 < currentType2)
					currentType2 = 0;
				drawType2();		
				menuBottom.repaint();
				menuBottom.validate();
				for(int m = 0; m < type2.size(); m++) {
					type2Buttons[m].addActionListener(this);
				}
				}
			}
			for(int i = 0; i < type2.size(); i++)
			{
				type2Buttons[i].setBackground(Color.decode("#6fde8d"));
				if(i == currentType1) {
					//Change MenuType3
					subMenu.removeAll();
					type3 =  getType3(currentType1, currentType2);
					if(type3.size()  <= currentType3)
						currentType3 = 0;
					drawType3();				
					subMenu.repaint();
					subMenu.validate();
					type3 =  getType3(currentType1, currentType2);
					for(int m = 0; m < type3.size(); m++) {
						type3Buttons[m].addActionListener(this);
					}
				} else {}
			}
			type2Buttons[currentType2].setBackground(Color.decode("#6fded7"));
			for(int i = 0; i < type3.size(); i++)
				type3Buttons[i].setBackground(Color.decode("#6fded7"));
			type3Buttons[currentType3].setBackground(Color.decode("#49918d"));
	}
	public static void drawOrder() {
		//Create Panels
		JPanel info = new JPanel(new GridLayout(1,5));
		info.add(new JLabel("Selected"));		
		info.add(new JLabel("Item"));
		info.add(new JLabel("Cost"));
		info.add(new JLabel("Quantity"));
		info.add(new JLabel("[Delete]"));

		orderViewer.add(info);
		for(int i = 0; i < userOrder.size(); i++) {			//TODO change
			orderPanels[i] = new JPanel(new GridLayout(1,4));
			orderViewer.add(orderPanels[i]);
			orderPanels[i].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
		}
		for(int i = 0; i < userOrder.size(); i++) {
			orderPanels[i].setBackground(Color.RED);
		}
		//Overight new values with old values
		//Add radiobutton
				for(int i = 0; i < userOrder.size(); i++) {	
					Boolean oldValue = true;
					if(checkBoxes[i] != null) {
						oldValue = checkBoxes[i].isSelected();
					}
					checkBoxes[i] = new JCheckBox();
					checkBoxes[i].setSelected(oldValue);
					orderPanels[i].add(checkBoxes[i]);
					orderPanels[i].add(new JLabel(userOrder.get(i).get(0) + ""));
					orderPanels[i].add(new JLabel("RM " + userOrder.get(i).get(1)));
					
					checkBoxes[i].addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent arg0) {
							for(int i=0; i<userOrder.size();i++) {
								if(!checkBoxes[i].isSelected()) {
									userOrder.get(i).set(2, 0);
								} else {
									int Quantity = Integer.valueOf(String.valueOf(userOrder.get(i).get(2)));
									if(Quantity==0) {
										userOrder.get(i).set(2, 1);
									}
								}
								orderViewer.removeAll();
								drawOrder();
								orderViewer.repaint();
								orderViewer.revalidate();
							}
						}
					});
				}	
								
		//Add textfield to panels
		for(int i = 0; i < userOrder.size(); i++) {			//TODO change
			orderTextFields[i] = new JTextField(userOrder.get(i).get(2) + "");
			orderPanels[i].add(orderTextFields[i]);
			orderTextFields[i].setBackground(Color.decode("#6fde89"));
		}
		
		for(int m = 0; m < userOrder.size(); m++) {
			orderTextFields[m].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					for(int i=0; i<userOrder.size();i++) {
						if(orderTextFields[i] == e.getSource()) {
							int val = Integer.valueOf(orderTextFields[i].getText());
							System.err.println("QUANTITY CHANGED for item " + i);
							
							if(Integer.valueOf(String.valueOf(userOrder.get(i).get(2))) == 0) {
								checkBoxes[i].setSelected(true);
							}
							
							userOrder.get(i).set(2, val);
							System.out.println("Order222 = " + userOrder);
							
							orderViewer.removeAll();
							drawOrder();
							orderViewer.repaint();
							orderViewer.revalidate();

						}
					}
				}
				
			});
		}
		
		//Add buttons to panels
		for(int i = 0; i < userOrder.size(); i++) {			//TODO change
			orderButtons[i] = new JButton("X");
			orderPanels[i].add(orderButtons[i]);
		}
		//Add ActionListeners to buttons
		for(int i=0; i<userOrder.size();i++) {
				orderButtons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						for(int s=0; s<userOrder.size();s++) {
							if(orderButtons[s] == e.getSource()) {
								System.out.print("Answer = " + s);
								userOrder.remove(s);
							}
						}
						orderViewer.removeAll();
						drawOrder();
						orderViewer.repaint();
						orderViewer.revalidate();
					}
				});
		}
		
		for(int i = 0; i < userOrder.size(); i++) {
			orderPanels[i].setBackground(Color.decode("#6fde8d"));
		}
	}
	public static void drawType1() {
		ArrayList type1 = getType1();
		for(int i = 0; i < type1.size(); i++) {			//TODO change
			type1Buttons[i] = new JButton("" + type1.get(i));
			menuTop.add(type1Buttons[i]);
		}
	}
	public static void drawType2() {
		ArrayList type2 = getType2(currentType1);
		for(int i = 0; i < type2.size(); i++) {			//TODO change
			type2Buttons[i] = new JButton("" + type2.get(i));
			menuBottom.add(type2Buttons[i]);
		}
		for(int i = 0; i < type2.size(); i++) {
			type2Buttons[i].setBackground(Color.decode("#6fde8d"));
		}
		type2Buttons[0].setBackground(Color.decode("#6fded7"));
	}
	public static void drawType3() {
		ArrayList type3 = getType3(currentType1,currentType2);
		for(int i = 0; i < type3.size(); i++) {			//TODO change
			type3Buttons[i] = new JButton("" + type3.get(i));
			subMenu.add(type3Buttons[i]);
		}
		for(int i = 0; i < type3.size(); i++) {
			type3Buttons[i].setBackground(Color.decode("#6fded7"));
		}
		type3Buttons[currentType3].setBackground(Color.decode("#49918d"));
	}
	public static ArrayList getType1(){
		ArrayList<String> type1 = new ArrayList<>();
		for(int i = 0; i < foodDictionary.length; i++) {
			type1.add(foodDictionary[i][0][0]);
		}
		return type1;
	}
	public static ArrayList getType2(int type1){					// 0 1 2
		ArrayList<String> type2 = new ArrayList<>();
		int numberOfType2 = foodDictionary[type1].length - 1;
		for(int i = 1 ; i <= numberOfType2 ; i++) {
			type2.add(foodDictionary[type1][i][0]);
		}
		return type2;
	}
	public static ArrayList getType3(int type1, int type2) {		//
		type2++;
		ArrayList<String> type3 = new ArrayList<>();
		int numberOfType3 = foodDictionary[type1][type2].length;
		for(int i = 1 ; i < numberOfType3 ; i++) {
			type3.add(foodDictionary[type1][type2][i]);
		}

		return type3;
	}
}