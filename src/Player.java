import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

class Player extends JPanel {
	int player_num;  //  �÷��̾� ������ȣ
	Color player_color; //  �÷��̾� ��������
	int money;  //  ������ �ִ� ����
	int property_value;  //  �ε��� ��ġ
	JPanel colorPanel;
	JLabel nameLabel, valueLabel, moneyLabel;
	int total_dice=0;
	int songwon=0;  //  �ۿ����� ����� �� 3�� �ǰ�, 1�� ����
	int subway_ticket=0;
	ArrayList<NormalSite> property;
	
	
	public Player() {  //  ����Ʈ������
		this.setSize(200,100);
		this.setLayout(null);
		
		this.money = 5000; //  �⺻ ���۱�
		this.property_value = 0;
		
		this.setVisible(true);
	}
	
	public Player(int i, Color c) {
		this.setSize(150,100);
		this.setLayout(null);
		
		player_num = i;
		player_color = c;
		this.money = 5000; //  1000���� �⺻ ���۱�
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
		
		this.valueLabel = new JLabel("�ε��� ��ġ: " + this.property_value);
		valueLabel.setBounds(60, 10, 140, 30);
		valueLabel.setVisible(true);
		this.add(valueLabel);
		
		this.moneyLabel = new JLabel("����: " + this.money);
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
		
		this.moneyLabel.setText("����: " + this.money);
		this.valueLabel.setText("�ε��� ��ġ:"+this.property_value);
	}
}
