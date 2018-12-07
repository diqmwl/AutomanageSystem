package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import java.awt.TextField;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.TextArea;
public class screen extends JFrame {
	public static int check = 1; //학교 1 공장 2 ...
	private JPanel contentPane;
	private final JPanel panel_1 = new JPanel();
	Image icon = new ImageIcon(Ceodb.class.getResource("/images/hansung.jpg")).getImage();
	Image icon2 = new ImageIcon(Ceodb.class.getResource("/images/logo.jpg")).getImage();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_1_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	DBmanage db = new DBmanage();
	public screen() {
		setTitle("자동 관리 시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel() {
			public void paint(Graphics g) {
		        g.drawImage(icon, 0, 0, this);
			}	
		};
		panel.setBounds(0, 0, 700, 581);
		contentPane.add(panel);
		
		panel_1.setBounds(700, 0, 284, 581);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton auto = new JButton("자동 조절");
		auto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ceodb ceo = new Ceodb();
				ceo.auto();
			}
		});
		auto.setBounds(102, 200, 97, 23);
		panel_1.add(auto);
		panel_1.setBackground(Color.WHITE);
		
		JButton control = new JButton("수동 조절");
		control.setBounds(102, 263, 97, 23);
		panel_1.add(control);
		
		
		JButton exit = new JButton("끝내기");
		exit.setBounds(102, 400, 97, 23);
		exit.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(exit);
		
		JPanel panel_2 = new JPanel() {
			public void paint(Graphics g) {
		        g.drawImage(icon2, 0, 0, this);
			}	
		};
		panel_2.setBounds(39, 441, 208, 111);
		panel_1.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(0, 0, 984, 581);
		contentPane.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setVisible(false);
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(324, 15, 305, 170);
		panel_3.add(panel_4);

		JPanel panel_5 = new JPanel() {
			public void paint(Graphics g) {
		        g.drawImage(icon2, 0, 0, this);
			}	
		};
		panel_5.setBounds(60, 50, 208, 111);
		panel_3.add(panel_5);
		
		Choice choice = new Choice();
		choice.add("학교");
		choice.add("공장");
		choice.add("빌딩");
		
		choice.setBounds(102, 140, 97, 21);
		choice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(choice.getSelectedItem()=="학교") {
					check = 1;
				}else if(choice.getSelectedItem()=="공장") {
					check = 2;
				}else if(choice.getSelectedItem()=="빌딩") {
					check = 3;
				}
			}
		});
		panel_1.add(choice);
		
		JTextPane textPane = new JTextPane();
		textPane.setForeground(Color.DARK_GRAY);
		textPane.setEditable(false);
		textPane.setToolTipText("");
		textPane.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		textPane.setText("\uC790\uB3D9\uAD00\uB9AC \uC2DC\uC2A4\uD15C \uC785\uB2C8\uB2E4.\r\n\r\n1. \uC7A5\uC18C\uB97C \uC120\uD0DD\uD569\uB2C8\uB2E4.\r\n2 \uC790\uB3D9\uC870\uC808\uC744 \uD074\uB9AD\uD569\uB2C8\uB2E4");
		textPane.setBounds(59, 27, 174, 85);
		panel_1.add(textPane);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(163, 191, 653, 390);
		panel_3.add(textArea);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(95, 42, 165, 21);
		panel_4.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(95, 84, 165, 21);
		panel_4.add(textField_2);
		
		JTextPane textField_3 = new JTextPane();
		textField_3.setEditable(false);
		textField_3.setText("\uC7A5\uC18C");
		textField_3.setBounds(48, 39, 31, 21);
		panel_4.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setText("IP");
		textField_4.setColumns(10);
		textField_4.setBounds(59, 84, 20, 21);
		panel_4.add(textField_4);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setToolTipText("");
		textPane_1.setText("\uAD00\uB9AC \uBC0F \uCD94\uAC00 \uC11C\uBE44\uC2A4 \uC785\uB2C8\uB2E4.\r\n\r\n\uD655\uC778 \uBC84\uD2BC\uC744 \uB204\uB97C\uACBD\uC6B0 \uD604\uC7AC \uB4F1\uB85D\uB41C \uC7A5\uC18C\uB97C \uD655\uC778\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\uC7A5\uC18C\uC640 IP\uB97C \uC785\uB825\uD55C\uD6C4 \uB4F1\uB85D\uBC84\uD2BC\uC744 \uB204\uB974\uBA74 \uC0C8\uB85C\uC6B4 \uC7A5\uC18C\uB97C \uB4F1\uB85D \uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.");
		textPane_1.setForeground(Color.DARK_GRAY);
		textPane_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		textPane_1.setEditable(false);
		textPane_1.setBounds(655, 15, 317, 146);
		panel_3.add(textPane_1);
		
		JButton back = new JButton("\uC774\uC804\uC73C\uB85C");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					panel.setVisible(true);
					panel_1.setVisible(true);
					panel_3.setVisible(false);
					panel_4.setVisible(false);
					back.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		back.setBounds(655, 162, 97, 23);
		back.setVisible(false);
		panel_3.add(back);
		
		JButton list = new JButton("목록 보기");
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					panel.setVisible(false);
					panel_1.setVisible(false);
					panel_3.setVisible(true);
					panel_4.setVisible(true);
					back.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		list.setBounds(102, 330, 97, 23);
		panel_1.add(list);
		
		
		
		JButton search = new JButton("\uAC80\uC0C9");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					db.search(textArea);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		search.setBounds(95, 115, 72, 23);
		panel_4.add(search);
		
		JButton insert = new JButton("\uB4F1\uB85D");
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					if((textField_1.getText()).equals("") || (textField_2.getText()).equals("")) {
						JOptionPane.showMessageDialog(null, "장소 비밀번호를 모두 입력하세요.", "경고 메시지", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(db.insert(textField_1.getText(), textField_2.getText())==1) {
							textArea.append("성공\n");
						}
						else {
							textArea.append("에러, IP중복을 확인하세요\n");
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		insert.setBounds(186, 115, 72, 23);
		panel_4.add(insert);
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 0, 984, 581);
		panel_6.setVisible(false);
		panel_6.setBackground(Color.WHITE);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
				
		TextArea textArea1 = new TextArea();
		textArea1.setBounds(69, 155, 480, 426);
		panel_6.add(textArea1);
				
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setBounds(609, 20, 363, 561);
		panel_6.add(panel_7);
		panel_7.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(145, 26, 116, 21);
		panel_7.add(textField);
		textField.setColumns(10);
		
		JTextPane textPane2 = new JTextPane();
		textPane2.setEditable(false);
		textPane2.setText("\uC7A5\uC18C :");
		textPane2.setBounds(100, 22, 49, 21);
		panel_7.add(textPane2);
		
		JButton button_1 = new JButton("전등 켜기");
		button_1.setBounds(40, 100, 132, 23);
		panel_7.add(button_1);
		
		JButton button_8 = new JButton("전등 75% 밝기");
		button_8.setBounds(210, 100, 132, 23);
		panel_7.add(button_8);
		
		JButton button_9 = new JButton("전등 100% 밝기");
		button_9.setBounds(40, 170, 132, 23);
		panel_7.add(button_9);
		
		JButton button_2 = new JButton("에어컨 중풍");
		button_2.setBounds(210, 170, 132, 23);
		panel_7.add(button_2);
		
		JButton button_3 = new JButton("에어컨 강풍");
		button_3.setBounds(40, 240, 132, 23);
		panel_7.add(button_3);
	
		JButton button_4 = new JButton("공기청정기 켜기");
		button_4.setBounds(210, 240, 132, 23);
		panel_7.add(button_4);
		
		JButton button_5 = new JButton("제습기능 켜기");
		button_5.setBounds(40, 310, 132, 23);
		panel_7.add(button_5);
		
		JButton button_7 = new JButton("모두 끄기");
		button_7.setBounds(210, 310, 132, 23);
		panel_7.add(button_7);
		
		
		JButton button_6 = new JButton("이전으로");
		button_6.setBounds(90, 380, 208, 23);
		panel_7.add(button_6);
		
		JPanel panel_8 = new JPanel() {
			public void paint(Graphics g) {
		        g.drawImage(icon2, 0, 0, this);
			}	
		};
		panel_8.setBackground(Color.WHITE);
		panel_8.setBounds(90, 420, 208, 111);
		panel_7.add(panel_8);
		
		JTextPane textPane1 = new JTextPane();
		textPane1.setEditable(false);
		textPane1.setFont(new Font("굴림", Font.PLAIN, 14));
		textPane1.setText("   \uC218\uB3D9 \uC870\uC808 \uC2DC\uC2A4\uD15C \uC785\uB2C8\uB2E4\r\n\r\n\uC7A5\uC18C\uB97C \uC785\uB825 \uD6C4 \uBC84\uD2BC\uC744 \uD074\uB9AD\uC2DC \r\n\uC218\uB3D9\uC73C\uB85C \uC870\uC808\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4");
		textPane1.setBounds(202, 36, 199, 91);
		panel_6.add(textPane1);
		
		control.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					panel_6.setVisible(true);
					panel.setVisible(false);
					panel_1.setVisible(false);
					panel_3.setVisible(false);
					panel_4.setVisible(false);
					back.setVisible(false);
					db.search(textArea1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					panel_6.setVisible(false);
					panel.setVisible(true);
					panel_1.setVisible(true);
					panel_3.setVisible(false);
					panel_4.setVisible(false);
					back.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.ipsearch(textField.getText(), 0, 700, 0);
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.ipsearch(textField.getText(), 0, 25, 2);
			}
		});
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.ipsearch(textField.getText(), 100, 100, 2);
			}
		});
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.ipsearch(textField.getText(), 1000, 0, 1);
			}
		});
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.ipsearch(textField.getText(), 0, 0, 3);
			}
		});
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					db.turnoff(textField.getText());
			}
		});
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.ipsearch(textField.getText(), 0, 900, 0);
			}
		});
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.ipsearch(textField.getText(), 0, 1500, 0);
			}
		});
	}
	
}
