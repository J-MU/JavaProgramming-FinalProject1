import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

class Player extends JPanel {
	int player_num;  //  플레이어 고유번호
	Color player_color; //  플레이어 고유색깔
	int money;  //  가지고 있는 현금
	int property_value;  //  부동산 가치
	JPanel colorPanel;
	JLabel nameLabel, valueLabel, moneyLabel;
	int total_dice=0;
	int songwon=0;  //  송원땅을 밟았을 때 3이 되고, 1씩 감소
	int subway_ticket=0;
	ArrayList<NormalSite> property;
	
	
	public Player() {  //  디폴트생성자
		this.setSize(200,100);
		this.setLayout(null);
		
		this.money = 5000; //  기본 시작금
		this.property_value = 0;
		
		this.setVisible(true);
	}
	
	public Player(int i, Color c) {
		this.setSize(150,100);
		this.setLayout(null);
		
		player_num = i;
		player_color = c;
		this.money = 5000; //  1000만이 기본 시작금
		this.property_value = 0;
		
		this.colorPanel = new JPanel();
		colorPanel.setBounds(0, 0, 50, 50);
		colorPanel.setBackground(c);
		colorPanel.setVisible(true);
		this.add(colorPanel);
		
		Integer num = new Integer(i);
		this.nameLabel = new JLabel(num.toString());
		nameLabel.setBounds(20, 50, 20, 50);
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		nameLabel.setVisible(true);
		this.add(nameLabel);
		
		this.valueLabel = new JLabel("부동산 가치: " + this.property_value);
		valueLabel.setBounds(60, 10, 140, 30);
		valueLabel.setVisible(true);
		this.add(valueLabel);
		
		this.moneyLabel = new JLabel("현금: " + this.money);
		moneyLabel.setBounds(60, 60, 90, 30);
		moneyLabel.setVisible(true);
		this.add(moneyLabel);
		
		property = new ArrayList<NormalSite>();
		
		this.setBackground(Color.WHITE);
		
		this.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(50, 0, 50, 100);
		g.drawLine(0, 50, 200, 50);	
		
		this.moneyLabel.setText("현금: " + this.money);
		this.valueLabel.setText("부동산 가치:"+this.property_value);
	}
}
