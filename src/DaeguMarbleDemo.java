import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

/*
 *  필드를 만들어준다. 땅필드랑 주택 빌라 호텔
 *    통행료를 정해주어요
 *  site number필드 구현
 *  팔공산 랜드마크 사진 추가해야 함
 */
public class DaeguMarbleDemo extends JFrame implements ActionListener {

	JPanel startPage, gamePage;
	private JLabel s_title, g_title;
	ShowTurn showturn;
	private JButton start_Button, rulebook_Button, g_dice;
	private static EtchedBorder eborder;
	private ArrayList<Player> playerList; // 게임에 참가중인 player.
	private ArrayList<Site> siteList; // Site;.
	private Player p1, p2, p3, p4;
	NormalSite s1, s_14, s_15, s_16, s5, s_18, s_19, s_20, s9, s_22, s_23, s_25, s13, s_1, s_3, s16, s_5, s_6, s_7, s20;
	NormalSite s_9, s_10, s_11, s_12, s25, s26, s27, s28, s29, s30, s31, s32;
	Subway tr;
	Start s;
	Tax tax;
	Image dice1, dice2, dice3, dice4, dice5, dice6;
	ArrayList<Site> subway1, subway2, subway3; // 각 호선별 지하철역 보음
	ArrayList<String> GoldCard;
	int xpot, ypot; // site의 좌표지정을 위한 변수.
	int turn = 0; // 혹시 오류뜰수도 있을듯.
	int ch_checked = 0; // 치킨을 밟았는지 확인.
	int rn; // 황금카드 결정짓는 랜덤숫자.
	Player nowplayer; // 쓰레기
	Toolkit t = Toolkit.getDefaultToolkit();

	// int player_number = 0;
	public DaeguMarbleDemo() { // 디폴트생성자
		dice1 = t.getImage("src\\dice1.jpg");
		dice2 = t.getImage("src\\dice2.jpg");
		dice3 = t.getImage("src\\dice3.jpg");
		dice4 = t.getImage("src\\dice4.jpg");
		dice5 = t.getImage("src\\dice5.jpg");
		dice6 = t.getImage("src\\dice6.jpg");

		this.setSize(1570, 850); // 창이 생성되는 위치까지 정하고 싶다면 setBounds(x좌표, y좌표, 창 가로크기, 창 세로크기)
		// 전체화면으로 하고 싶으면 -> this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("대구마블");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // 화면 정중앙에 표시

		startPage = new JPanel(); // 시작화면 패널 생성
		startPage.setSize(1570, 850);
		startPage.setLayout(null); // AbsouluteLayout을 사용하기 위해 null로 지정. 중요!!!!!!!!!!!!!!!!!!!!!!!!
		s_title = new JLabel("대구마블");
		s_title.setFont(new Font("Dialog", Font.BOLD, 80));
		s_title.setBounds(600, 320, 350, 150); // 레이블의 위치 지정
		startPage.add(s_title);

		start_Button = new JButton("게임 시작");
		start_Button.setFont(new Font("Monospaced", Font.PLAIN, 30));
		start_Button.setBounds(670, 485, 200, 80);

		start_Button.setBackground(Color.WHITE);
		start_Button.addActionListener(this);
		startPage.add(start_Button); // 시작화면에 추가

		rulebook_Button = new JButton("Rule Book");
		rulebook_Button.setFont(new Font("Monospaced", Font.PLAIN, 20));
		rulebook_Button.setBounds(1300, 700, 200, 80);
		rulebook_Button.setVisible(true);
		rulebook_Button.addActionListener(this);
		startPage.add(rulebook_Button); // 시작화면에 추가

		// 게임화면 만들어야함
		gamePage = new JPanel(); // 게임화면 패널 생성
		gamePage.setSize(1570, 850);
		gamePage.setLayout(null);

		g_title = new JLabel("대구마블");
		g_title.setFont(new Font("Dialog", Font.BOLD, 80));
		g_title.setBounds(600, 250, 350, 150);
		gamePage.add(g_title);

		g_dice = new JButton("굴리기");
		g_dice.setBounds(720, 530, 100, 40);
		g_dice.addActionListener(this);
		gamePage.add(g_dice);

		xpot = 120;
		ypot = 100;
		eborder = new EtchedBorder();

		ChickenFestival cf = new ChickenFestival(xpot, ypot); // 치맥축제
		cf.setBorder(eborder);
		gamePage.add(cf);

		Image img = t.getImage("src\\라이온즈파크.jpg");
		xpot += 150;
		s_14 = new NormalSite(xpot, ypot, "라이온즈 파크", 59, 25, 75, 125, 125, 14, 2, img);
		s_14.setBorder(eborder);
		gamePage.add(s_14);

		img = t.getImage("src\\대구역.jpg");
		xpot += 150;
		s_15 = new NormalSite(xpot, ypot, "대구역", 62, 25, 75, 125, 125, 15, 1, img);
		s_15.setBorder(eborder);
		gamePage.add(s_15);

		img = t.getImage("src\\대구공항.jpg");
		xpot += 150;
		s_16 = new NormalSite(xpot, ypot, "대구공항", 70, 30, 90, 150, 150, 16, 0, img);
		s_16.setBorder(eborder);
		gamePage.add(s_16);

		xpot += 150;
		Goldkey k1 = new Goldkey(xpot, ypot, 17);// 17
		k1.setBorder(eborder);
		gamePage.add(k1);

		img = t.getImage("src\\서문시장.jpg");
		xpot += 150;
		s_18 = new NormalSite(xpot, ypot, "서문시장", 73, 30, 90, 150, 150, 18, 3, img);
		s_18.setBorder(eborder);
		gamePage.add(s_18);

		img = t.getImage("src\\콘서트하우스.jpg");
		xpot += 150;
		s_19 = new NormalSite(xpot, ypot, "콘서트 하우스", 75, 32, 92, 152, 152, 19, 1, img); // 원래이름은 대구 콘서트 하우스. 이름이 길어서
																						// '대구'뺐음.
		s_19.setBorder(eborder);
		gamePage.add(s_19);

		img = t.getImage("src\\야당.jpg");
		xpot += 150;
		s_20 = new NormalSite(xpot, ypot, "야외 음악당", 82, 35, 105, 175, 175, 20, 2, img);
		s_20.setBorder(eborder);
		gamePage.add(s_20);

		xpot += 150;
		tr = new Subway(xpot, ypot);// 21
		tr.setBorder(eborder);
		gamePage.add(tr);

		img = t.getImage("src\\3지구.jpg");
		ypot += 100;
		s_22 = new NormalSite(xpot, ypot, "칠곡 3지구", 85, 35, 105, 175, 175, 22, 3, img);
		s_22.setBorder(eborder);
		gamePage.add(s_22);

		img = t.getImage("src\\수성못.jpg");
		ypot += 100;
		s_23 = new NormalSite(xpot, ypot, "수성못", 96, 40, 120, 200, 200, 23, 3, img);
		s_23.setBorder(eborder);
		gamePage.add(s_23);

		ypot += 100;
		tax = new Tax(xpot, ypot); // 24
		tax.setBorder(eborder);
		gamePage.add(tax);

		img = t.getImage("src\\동성로.jpg");
		ypot += 100;
		s_25 = new NormalSite(xpot, ypot, "동성로", 101, 45, 125, 220, 220, 25, 2, img);
		s_25.setBorder(eborder);
		gamePage.add(s_25);

		ypot += 100;
		s = new Start(xpot, ypot); // 시작점
		s.setBorder(eborder);
		gamePage.add(s);

		img = t.getImage("src\\북성로.jpg");
		xpot -= 150;
		s_1 = new NormalSite(xpot, ypot, "북성로", 10, 5, 15, 25, 25, 1, 1, img);
		s_1.setBorder(eborder);
		gamePage.add(s_1);

		xpot -= 150;
		Bonus b = new Bonus(xpot, ypot); // 2
		b.setBorder(eborder);
		gamePage.add(b);

		img = t.getImage("src\\김광석 거리.jpg");
		xpot -= 150;
		s_3 = new NormalSite(xpot, ypot, "김광석 거리", 13, 5, 15, 25, 25, 3, 2, img);
		s_3.setBorder(eborder);
		gamePage.add(s_3);

		xpot -= 150;
		Goldkey k2 = new Goldkey(xpot, ypot, 4); // 4
		k2.setBorder(eborder);
		gamePage.add(k2);

		img = t.getImage("src\\스포츠타운.jpg");
		xpot -= 150;
		s_5 = new NormalSite(xpot, ypot, "복합 스포츠센터", 24, 10, 30, 50, 50, 5, 3, img);
		s_5.setBorder(eborder);
		gamePage.add(s_5);

		img = t.getImage("src\\동대구역.jpg");
		xpot -= 150;
		s_6 = new NormalSite(xpot, ypot, "동대구역", 27, 10, 30, 50, 50, 6, 1, img);
		s_6.setBorder(eborder);
		gamePage.add(s_6);

		img = t.getImage("src\\경북대학교.jpg");
		xpot -= 150;
		s_7 = new NormalSite(xpot, ypot, "경북대학교", 27, 10, 30, 50, 50, 7, 0, img);
		s_7.setBorder(eborder);
		gamePage.add(s_7);

		xpot -= 150;
		SongWon sw = new SongWon(xpot, ypot);
		sw.setBorder(eborder);
		gamePage.add(sw);

		img = t.getImage("src\\달성공원.jpg");
		ypot -= 100;
		s_9 = new NormalSite(xpot, ypot, "달성공원", 36, 15, 45, 75, 75, 9, 3, img);
		s_9.setBorder(eborder);
		gamePage.add(s_9);

		img = t.getImage("src\\안지랑.jpg");
		ypot -= 100;
		s_10 = new NormalSite(xpot, ypot, "안지랑 곱창골목", 36, 15, 45, 75, 75, 10, 1, img);
		s_10.setBorder(eborder);
		gamePage.add(s_10);

		img = t.getImage("src\\팔공산.jpg");// 이미지 아직 없음
		ypot -= 100;
		s_11 = new NormalSite(xpot, ypot, "팔공산", 47, 20, 60, 100, 100, 11, 0, img);
		s_11.setBorder(eborder);
		gamePage.add(s_11);

		img = t.getImage("src\\E월드.jpg");
		ypot -= 100;
		s_12 = new NormalSite(xpot, ypot, "E월드", 50, 20, 60, 100, 100, 12, 2, img);
		s_12.setBorder(eborder);
		gamePage.add(s_12);

		siteList = new ArrayList<Site>();
		siteList.add(s);
		siteList.add(s_1);
		siteList.add(b);
		siteList.add(s_3);
		siteList.add(k2);
		siteList.add(s_5);
		siteList.add(s_6);
		siteList.add(s_7);
		siteList.add(sw);
		siteList.add(s_9);
		siteList.add(s_10);
		siteList.add(s_11);
		siteList.add(s_12);
		siteList.add(cf);
		siteList.add(s_14);
		siteList.add(s_15);
		siteList.add(s_16);
		siteList.add(k1);
		siteList.add(s_18);
		siteList.add(s_19);
		siteList.add(s_20);
		siteList.add(tr);
		siteList.add(s_22);
		siteList.add(s_23);
		siteList.add(tax);
		siteList.add(s_25);

		subway1 = new ArrayList<Site>();
		subway1.add(s_1);
		subway1.add(s_6);
		subway1.add(s_10);
		subway1.add(s_15);
		subway1.add(s_19);

		subway2 = new ArrayList<Site>();
		subway2.add(s_3);
		subway2.add(s_12);
		subway2.add(s_14);
		subway2.add(s_20);
		subway2.add(s_25);

		subway3 = new ArrayList<Site>();
		subway3.add(s_5);
		subway3.add(s_9);
		subway3.add(s_18);
		subway3.add(s_22);
		subway3.add(s_23);

		GoldCard = new ArrayList<String>();

		GoldCard.add("지진 카드");
		GoldCard.add("땅 매각 카드");
		GoldCard.add("환승 카드");
		GoldCard.add("이월드 별빛축제 카드");
		GoldCard.add("경북대 축제 카드");
		GoldCard.add("세금 지원 카드");
		// =================================================
		// BonusFrame bf=new BonusFrame();
		// bf.setVisible(true);

		startPage.setBackground(Color.WHITE);
		gamePage.setBackground(Color.WHITE);
		this.add(startPage, BorderLayout.CENTER);
		this.add(gamePage, BorderLayout.CENTER);
		this.setVisible(true);
		gamePage.setVisible(false);
	}

	public void dice() {
		Random rand = new Random();
		int now_dice = rand.nextInt(6) + 1; // now_dice에 주사위 결과를 저장한다.
		ArrayList<String> property_name = null;
		int start = 0;
		nowplayer = playerList.get(turn);
		siteList.get(nowplayer.total_dice).player_OK.set(nowplayer.player_num - 1, false); // 주사위 던지고 이동하기 전 site를
																							// false로

		nowplayer.total_dice += now_dice; // nowplayer의 total_dice에 주사위 결과를 더해준다.

		if (nowplayer.total_dice == 26) {
			nowplayer.money += 800; // 월급 3000
			start = 1;
		}
		if (nowplayer.total_dice > 26) {
			nowplayer.money += 500; // 월급 2000
		}
		nowplayer.total_dice = nowplayer.total_dice % 26;
		if (nowplayer.total_dice == 24) {
			TaxFrame tf=new TaxFrame();
		}
		if (nowplayer.total_dice == 13) { // 치맥축제
			property_name = new ArrayList<String>();
			for (int i = 0; i < nowplayer.property.size(); i++) {
				property_name.add(nowplayer.property.get(i).name);
			}
			ch_checked = 1;
		}
		if (nowplayer.total_dice == 8) { // 송원
			nowplayer.songwon = 3;
			SongwonFrame sF = new SongwonFrame();
		}
		if (nowplayer.total_dice == 2) { // Bonus
			BonusFrame BF = new BonusFrame();
		}
		if (nowplayer.total_dice == 21) {
			SubwayFrame TF = new SubwayFrame();
		}
		if ((nowplayer.total_dice == 4) || (nowplayer.total_dice == 17)) { // 황금열쇠
			Random goldrandom = new Random();
			rn = goldrandom.nextInt(4) + 2;
			GoldCardFrame gcf = new GoldCardFrame(rn);
		}
		
		siteList.get(nowplayer.total_dice).player_OK.set((nowplayer.player_num) - 1, true); // 주사위 던지고 이동한 후 site를 true로
		
		if (start == 1) {
			turn += 1;
			turn %= playerList.size();
			nowplayer = playerList.get(turn);
			if (s_7.festival > 0) {
				s_7.festival--;
				s_7.setToll(2);
			}
			if (s_7.festival == 0) {
				s_7.setToll();
			}

			if (s_12.festival > 0) {
				s_12.festival--;
				s_12.setToll(2);
			}
			if (s_12.festival == 0) {
				s_12.setToll();
			}
			gamePage.repaint();
			start = 0;
		}
		if (ch_checked == 1) {
			gamePage.repaint();
			ChickenFestivalFrame cff = new ChickenFestivalFrame(property_name);
			ch_checked = 0;
		}
		gamePage.repaint();

	}
	
	// 내부 클래스//시작점
	private class Start extends Site {

		public Start(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);
			JLabel st = new JLabel("START");
			st.setBounds(32, 43, 50, 10);
			this.add(st);
			// this.site_num=0;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void first_setting(Setting setting) {
			if (setting.c1_state == true) {
				this.player_OK.set(0, true);
			}
			if (setting.c2_state == true) {
				this.player_OK.set(1, true);
			}
			if (setting.c3_state == true) {
				this.player_OK.set(2, true);
			}
			if (setting.c4_state == true) {
				this.player_OK.set(3, true);
			}
		}

	}

	// 내부 클래스//송원 학원
	private class SongWon extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\songwon.jpg");

		public SongWon(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);

			this.site_num = 8;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);
		}

	}

	// 내부클래스 //보너스
	private class Bonus extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\Bonus.jpg");

		public Bonus(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);

			this.site_num = 2;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);
		}
	}

	private class BonusFrame extends JFrame implements ActionListener {

		JButton odd, even, confirm, onemore, end;
		JPanel p1, p2;
		JLabel str1, str2, str3, str4, str5, str6;
		int savemoney = 0;
		int challenge_money;
		int tmp;

		public BonusFrame() {
			this.setSize(500, 500);
			this.setTitle("Bonus Game");
			this.setLocationRelativeTo(null);
			this.setLayout(null);

			challenge_money = 1000;

			JLabel bg = new JLabel("Bonus Game");
			bg.setBounds(190, 10, 150, 50);
			bg.setFont(new Font("Monospaced", Font.BOLD, 20));
			this.add(bg);

			odd = new JButton("홀");
			even = new JButton("짝");
			odd.setFont(new Font("Monospaced", Font.BOLD, 20));
			even.setFont(new Font("Monospaced", Font.BOLD, 20));
			odd.setBounds(0, 300, 250, 200);
			even.setBounds(250, 300, 250, 200);
			odd.addActionListener(this);
			even.addActionListener(this);

			confirm = new JButton("확인");
			onemore = new JButton("한번 더!");
			end = new JButton("STOP");

			confirm.setBounds(190, 380, 120, 60);
			onemore.setBounds(0, 352, 250, 100);
			end.setBounds(250, 352, 250, 100);

			confirm.setFont(new Font("Monospaced", Font.BOLD, 30));
			onemore.setFont(new Font("Monospaced", Font.BOLD, 30));
			end.setFont(new Font("Monospaced", Font.BOLD, 30));

			confirm.addActionListener(this);
			onemore.addActionListener(this);
			end.addActionListener(this);

			str3 = new JLabel("현재 도전금액 ");
			str3.setBounds(50, 80, 400, 80);
			str3.setFont(new Font("Monospaced", Font.BOLD, 40));

			str4 = new JLabel(challenge_money + "!!");
			str4.setBounds(50, 190, 400, 80);
			str4.setFont(new Font("Monospaced", Font.BOLD, 40));

			p1 = new JPanel();
			p2 = new JPanel();
			p1.setLayout(null);
			p2.setLayout(null);
			p1.setBounds(0, 0, 500, 500);
			p2.setBounds(0, 0, 500, 500);
			this.add(p1);
			this.add(p2);

			p1.add(odd);
			p1.add(even);
			p1.setVisible(true);
			p1.add(str3);
			p1.add(str4);
			str1 = new JLabel("축! 당첨!!");
			str2 = new JLabel("ㅠㅠ실패ㅠㅠ");

			str1.setBounds(150, 260, 400, 100);
			str2.setBounds(150, 260, 400, 100);

			str1.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 30));
			str2.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 30));

			p2.add(confirm);
			p2.add(onemore);
			p2.add(end);
			p2.add(str1);
			p2.add(str2);

			p1.setBackground(Color.WHITE);
			p2.setBackground(Color.WHITE);

			this.setVisible(true);
		}

		public void paint(Graphics g) {
			super.paint(g);

			g.drawLine(0, 90, 500, 90);
			int xpot = 185;
			int ypot = 130;
			switch (tmp) {
			case 1:
				g.drawImage(dice1, xpot, ypot, this);
				break;
			case 2:
				g.drawImage(dice2, xpot, ypot, this);
				break;
			case 3:
				g.drawImage(dice3, xpot, ypot, this);
				break;
			case 4:
				g.drawImage(dice4, xpot, ypot, this);
				break;
			case 5:
				g.drawImage(dice5, xpot, ypot, this);
				break;
			case 6:
				g.drawImage(dice6, xpot, ypot, this);
				break;
			}
			str4.setText(challenge_money + "!!");
		}

		public void actionPerformed(ActionEvent e) {
			String actionCmd = e.getActionCommand();
			// 첫 보상 1000만원 그다음은 *2;
			if (actionCmd.equals("홀")) {
				p1.setVisible(false);
				p2.setVisible(true);

				Random rand = new Random();
				tmp = rand.nextInt(6) + 1;

				if (tmp % 2 == 1) {// 당첨
					str1.setVisible(true);
					str2.setVisible(false);
					onemore.setVisible(true);
					end.setVisible(true);
					confirm.setVisible(false);

					savemoney = challenge_money;
					challenge_money *= 2;
					this.repaint();

				} else {// 실패
					str2.setVisible(true);
					str1.setVisible(false);
					onemore.setVisible(false);
					end.setVisible(false);
					confirm.setVisible(true);

					this.repaint();
				}
			}
			if (actionCmd.equals("짝")) {// 짝
				Random rand = new Random();
				tmp = rand.nextInt(6) + 1;

				p1.setVisible(false);
				p2.setVisible(true);
				if (tmp % 2 == 0) {// 당첨
					str1.setVisible(true);
					str2.setVisible(false);
					onemore.setVisible(true);
					end.setVisible(true);
					confirm.setVisible(false);

					savemoney = challenge_money;
					challenge_money *= 2;
					this.repaint();

				} else {// 실패
					str2.setVisible(true);
					str1.setVisible(false);
					onemore.setVisible(false);
					end.setVisible(false);
					confirm.setVisible(true);

					this.repaint();
				}

			}
			if (actionCmd.equals("확인")) {
				savemoney = 0;
				challenge_money = 1000;

				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				gamePage.repaint();
				dispose();// 모아놓은돈 0;
			}
			if (actionCmd.equals("한번 더!")) {
				p1.setVisible(true);
				p2.setVisible(false);

				p1.repaint();
			}
			if (actionCmd.equals("STOP")) {
				nowplayer.money += savemoney;
				challenge_money = 1000;
				savemoney = 0;

				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				gamePage.repaint();
				dispose();
			}
		}

	}

	// 내부클래스 //국세청
	private class Tax extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\tax.jpg");

		int tax_money;

		public Tax(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);
			tax_money = 0;

			this.site_num = 24;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);
		}

	}
	public class TaxFrame extends JFrame implements ActionListener{
		JLabel str1,str2;
		JButton con;
		
		TaxFrame(){
			
			this.setSize(500,380);
			this.setTitle("세금");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			str1=new JLabel("세금으로 300만원이 빠져나갑니다.");
			str1.setBounds(90,50,500,50);
			str1.setFont(new Font("Monospaced",Font.BOLD,20));
			

			str2=new JLabel("국세청 잔액: "+tax.tax_money);
			str2.setBounds(150,150,300,50);
			str2.setFont(new Font("Monospaced",Font.BOLD,20));
			
			con=new JButton("확인");
			con.setBounds(190,230,100,50);
			con.setFont(new Font("Monospaced",Font.BOLD,20));
			con.addActionListener(this);
			
			
			this.add(str1);
			this.add(str2);
			this.add(con);
			this.getContentPane().setBackground(Color.white);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd=e.getActionCommand();
			
			if(actionCmd.equals("확인")) {
				dispose();

				nowplayer.money -= 200; // tax 200 빠짐
				tax.tax_money += 200; // tax_money 200 증가
				
				turn+=1;
				turn%=playerList.size();
				nowplayer=playerList.get(turn);
			}
		}
		
		
	}
	// 내부 클래스//황금 열쇠
	private class Goldkey extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\key.jpg");

		public Goldkey(int x, int y, int site_num) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);

			this.site_num = site_num; // 4, 17 둘 중 하나

			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);

		}

	}

	// 내부 클래스//지하철
	private class Subway extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\train.jpg");

		public Subway(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);

			this.site_num = 21;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);
		}

	}

	public class SubwayFrame extends JFrame implements ActionListener {

		JPanel p1, p2, p3, p4; // p4는 subway_ticket 가지고 있을 때 발동
		JButton btn, btn2, btn3, btn4, btn5, btn6;
		JLabel str, str2, str3, str4, str5, str6;
		int subway_line;
		JComboBox subway1_jcb, subway2_jcb, subway3_jcb;
		String[] subway1_s, subway2_s, subway3_s;

		SubwayFrame() {
			subway1_s = new String[subway1.size()];
			for (int i = 0; i < subway1.size(); i++) {

				subway1_s[i] = subway1.get(i).name;
			}
			subway2_s = new String[subway2.size()];
			for (int i = 0; i < subway2.size(); i++) {

				subway2_s[i] = subway2.get(i).name;
			}
			subway3_s = new String[subway3.size()];
			for (int i = 0; i < subway3.size(); i++) {

				subway3_s[i] = subway3.get(i).name;
			}

			subway1_jcb = new JComboBox(subway1_s);
			subway2_jcb = new JComboBox(subway2_s);
			subway3_jcb = new JComboBox(subway3_s);

			subway1_jcb.setBounds(150, 170, 300, 50);
			subway2_jcb.setBounds(150, 170, 300, 50);
			subway3_jcb.setBounds(150, 170, 300, 50);

			this.setSize(600, 400);
			this.setTitle("지하철");
			this.setLayout(null);
			this.setLocationRelativeTo(null);

			p1 = new JPanel();
			p1.setLayout(null);
			p1.setBounds(0, 0, 600, 400);
			p1.setVisible(true);

			p2 = new JPanel();
			p2.setLayout(null);
			p2.setBounds(0, 0, 600, 400);
			p2.setVisible(false);

			p3 = new JPanel();
			p3.setLayout(null);
			p3.setBounds(0, 0, 600, 400);
			p3.setVisible(false);

			p4 = new JPanel();
			p4.setLayout(null);
			p4.setBounds(0, 0, 600, 400);
			p4.setVisible(false);

			str5 = new JLabel("환승 티켓을 소유중이군요!!?");
			str5.setBounds(120, 30, 400, 80);
			str5.setFont(new Font("Monospaced", Font.BOLD, 25));
			p4.add(str5);

			str6 = new JLabel("원하는 호선을 선택해주세요.");
			str6.setBounds(120, 150, 400, 80);
			str6.setFont(new Font("Monospaced", Font.BOLD, 25));
			p4.add(str6);

			btn4 = new JButton("1호선");
			btn4.setBounds(20, 250, 150, 80);
			btn4.setFont(new Font("Monospaced", Font.BOLD, 20));
			p4.add(btn4);
			btn4.addActionListener(this);

			btn5 = new JButton("2호선");
			btn5.setBounds(220, 250, 150, 80);
			btn5.setFont(new Font("Monospaced", Font.BOLD, 20));
			p4.add(btn5);
			btn5.addActionListener(this);

			btn6 = new JButton("3호선");
			btn6.setBounds(420, 250, 150, 80);
			btn6.setFont(new Font("Monospaced", Font.BOLD, 20));
			p4.add(btn6);
			btn6.addActionListener(this);

			str = new JLabel("랜덤으로 지하철 호선이 결정됩니다.");
			str.setBounds(80, 100, 500, 60);
			str.setFont(new Font("Monospaced", Font.BOLD, 25));
			p1.add(str);

			str2 = new JLabel(subway_line + "호선 으로 배정되었습니다.");
			str2.setBounds(150, 210, 500, 60);
			str2.setFont(new Font("Monospaced", Font.BOLD, 25));
			p2.add(str2);

			btn = new JButton("굴리기");
			btn.setBounds(220, 200, 150, 50);
			btn.setFont(new Font("Monospaced", Font.BOLD, 25));
			btn.addActionListener(this);
			p1.add(btn);

			btn2 = new JButton("확인");
			btn2.setBounds(250, 280, 100, 50);
			
			btn2.setFont(new Font("Monospaced", Font.BOLD, 25));
			btn2.addActionListener(this);
			p2.add(btn2);

			btn3 = new JButton("이동");
			btn3.setBounds(250, 280, 100, 50);
			btn3.setFont(new Font("Monospaced", Font.BOLD, 25));
			btn3.addActionListener(this);
			p3.add(btn3);

			str3 = new JLabel("이동할 지역을 선택해주세요.");
			str3.setBounds(130, 80, 500, 60);
			str3.setFont(new Font("Monospaced", Font.BOLD, 25));
			p3.add(str3);

			str4 = new JLabel("<" + subway_line + "호선>");
			str4.setBounds(250, 10, 100, 40);
			str4.setFont(new Font("Monospaced", Font.BOLD, 25));
			p3.add(str4);

			p3.add(subway1_jcb);
			p3.add(subway2_jcb);
			p3.add(subway3_jcb);

			p1.setBackground(Color.white);
			p2.setBackground(Color.white);
			p3.setBackground(Color.white);

			this.add(p1);
			this.add(p2);
			this.add(p3);
			this.add(p4);

			p4.setBackground(Color.white);
			if (nowplayer.subway_ticket >= 1) {
				p4.setVisible(true);
				p1.setVisible(false);
				p2.setVisible(false);
				p3.setVisible(false);
			}

			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();
			int index;

			NormalSite current = null;
			if (actionCmd.equals("굴리기")) {

				Random rd = new Random();
				subway_line = rd.nextInt(3) + 1;
				// subway_line = 1;

				p1.setVisible(false);
				p2.setVisible(true);

				this.repaint();
			}
			if (actionCmd.equals("확인")) {

				switch (subway_line) {
				case 1:
					subway1_jcb.setVisible(true);
					subway2_jcb.setVisible(false);
					subway3_jcb.setVisible(false);
					break;
				case 2:
					subway2_jcb.setVisible(true);
					subway1_jcb.setVisible(false);
					subway3_jcb.setVisible(false);
					break;
				case 3:
					subway3_jcb.setVisible(true);
					subway2_jcb.setVisible(false);
					subway1_jcb.setVisible(false);
					break;
				}

				p2.setVisible(false);
				p3.setVisible(true);
				this.repaint();
			}
			if (actionCmd.equals("이동")) {
				switch (subway_line) {
				case 1:
					index = subway1_jcb.getSelectedIndex();
					current = (NormalSite) subway1.get(index);
					current.player_OK.set(nowplayer.player_num - 1, true);
					tr.player_OK.set(nowplayer.player_num - 1, false);
					if (current.site_num < tr.site_num && 0 <= current.site_num) {
						nowplayer.money += 2000;
					}
					nowplayer.total_dice = current.site_num;

					gamePage.repaint();

					break;
				case 2:
					index = subway2_jcb.getSelectedIndex();
					current = (NormalSite) subway2.get(index);
					current.player_OK.set(nowplayer.player_num - 1, true);
					tr.player_OK.set(nowplayer.player_num - 1, false);
					if (current.site_num < tr.site_num && 0 <= current.site_num) {
						nowplayer.money += 2000;
					}
					nowplayer.total_dice = current.site_num;

					gamePage.repaint();
					break;
				case 3:
					index = subway3_jcb.getSelectedIndex();
					current = (NormalSite) subway3.get(index);
					current.player_OK.set(nowplayer.player_num - 1, true);
					tr.player_OK.set(nowplayer.player_num - 1, false);
					if (current.site_num < tr.site_num && 0 <= current.site_num) {
						nowplayer.money += 2000;
					}
					nowplayer.total_dice = current.site_num;

					gamePage.repaint();
					break;

				}
				dispose();

				if (current.owner == nowplayer) { // 내 땅일 때
					if (!current.landmark_OK) { // 랜드마크 건설이 되지 않은 땅일 때
						if (current.house_OK && current.villa_OK && current.hotel_OK) { // 세 개 다 지은 경우
							LandMarkBuild lmblmb = new LandMarkBuild(current);
						} else { // 세 개 중 하나라도 안 지은 게 있을 경우
							Purchase ppp = new Purchase(current);
						}
					}
				} else if (current.owner == null) { // 주인없는 땅일 때
					Purchase pppp = new Purchase(current);

				} else { // 남의 땅일 때
						Pay ppp = new Pay(current);
				}
			}
			if (actionCmd.contentEquals("1호선")) {
				subway_line = 1;
				subway1_jcb.setVisible(true);
				subway2_jcb.setVisible(false);
				subway3_jcb.setVisible(false);

				p4.setVisible(false);
				p3.setVisible(true);
				this.repaint();
			}
			if (actionCmd.contentEquals("2호선")) {
				subway_line = 2;
				subway2_jcb.setVisible(true);
				subway1_jcb.setVisible(false);
				subway3_jcb.setVisible(false);

				p4.setVisible(false);
				p3.setVisible(true);
				this.repaint();
			}
			if (actionCmd.contentEquals("3호선")) {
				subway_line = 3;
				subway3_jcb.setVisible(true);
				subway2_jcb.setVisible(false);
				subway1_jcb.setVisible(false);

				p4.setVisible(false);
				p3.setVisible(true);
				this.repaint();
			}
		}

		public void paint(Graphics g) {
			super.paint(g);

			int xpot = 230;
			int ypot = 80;

			if (p2.isVisible() == true) {
				switch (subway_line) {
				case 1:
					g.drawImage(dice1, xpot, ypot, this);
					break;
				case 2:
					g.drawImage(dice2, xpot, ypot, this);
					break;
				case 3:
					g.drawImage(dice3, xpot, ypot, this);
					break;
				}
			}
			if (p3.isVisible() == true) {
				g.drawLine(0, 100, 600, 100);
			}

			str2.setText(subway_line + "호선 으로 배정되었습니다.");
			str4.setText("<" + subway_line + "호선>");
		}
	}

	private class ChickenFestival extends Site {
		// EtchedBorder eborder = new EtchedBorder();
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("src\\Beer.jpg");

		public ChickenFestival(int x, int y) {
			this.setBounds(x, y, 150, 100);
			this.setLayout(null);

			this.site_num = 13;
			this.setBackground(Color.WHITE);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);
		}

	}

	// 내부클래스
	private class RuleBook extends JFrame implements ActionListener {
		private JTextArea text;
		private JButton confirm;
		String space = "\n";

		RuleBook() {
			this.setBounds(1350, 200, 500, 560);
			this.setTitle("Rule Book");
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			String s = null, total = "";
			try {
				Scanner inputStream = new Scanner(new FileInputStream("src\\rulebook.txt")); // 텍스트파일 입력
				s = inputStream.nextLine(); // 한 줄 읽어오기
				total = s;
				total += space;
				while (s != null) {
					s = inputStream.nextLine();
					total += s;
					total += space;
				}
				inputStream.close();
			} catch (IOException e) {
				e.getStackTrace();
			} catch (NoSuchElementException e) {
				e.getStackTrace();
			}

			text = new JTextArea(25, 30);
			text.setText(total); // 추가로 더 적어 넣어야 함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			JScrollPane scrolledText = new JScrollPane(text);
			scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			JPanel textPanel = new JPanel();
			textPanel.setBackground(Color.BLUE);
			textPanel.add(scrolledText);

			add(textPanel, BorderLayout.NORTH);

			JPanel btnPanel = new JPanel();
			btnPanel.setBackground(Color.GRAY);

			confirm = new JButton("확인");
			confirm.addActionListener(this);
			btnPanel.add(confirm);

			add(btnPanel, BorderLayout.SOUTH);

			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();

			if (actionCmd.equals("확인"))
				dispose(); // 현재 창 종료(전체 프로그램 종료x)
		}
	}

	// 내부클래스
	private class Setting extends JFrame implements ActionListener {
		public int nop = 0, noc = 0; // nop는 인원 수, noc는 선택된 색깔 수
		public boolean c1_state, c2_state, c3_state, c4_state;
		private JButton start_Button;
		private Checkbox c1, c2, c3, c4;
		private JComboBox cb;
		private int count; // 캐릭터가 총 몇 개가 선택되었는지 저장하는 변수
		private String[] player_num = { "2", "3", "4" };

		Setting() {
			this.setBounds(800, 300, 400, 500);
			this.setTitle("게임 설정");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			// this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // x버튼을 눌러도 꺼지지 않음(코드 완성
			// 후 요걸로 교체)
			this.setLayout(null); // AbsoluteLayout을 위해 null로 설정

			JLabel 인원 = new JLabel("인원:");
			인원.setBounds(10, 20, 70, 50);
			인원.setVisible(true);
			this.add(인원);

			// 인원 선택하는 옵션 생성해야 함
			cb = new JComboBox(player_num);
			cb.setBounds(100, 32, 180, 30);
			this.add(cb);

			JLabel 캐릭터 = new JLabel("캐릭터: ");
			캐릭터.setBounds(10, 100, 80, 50);
			캐릭터.setVisible(true);
			this.add(캐릭터);

			// 캐릭터 선택하는 옵션 생성해야 함
			c1 = new Checkbox("색깔1");
			c1.setBounds(100, 220, 100, 30);
			this.add(c1);
			c2 = new Checkbox("색깔2");
			c2.setBounds(250, 220, 100, 30);
			this.add(c2);
			c3 = new Checkbox("색깔3");
			c3.setBounds(100, 340, 100, 30);
			this.add(c3);
			c4 = new Checkbox("색깔4");
			c4.setBounds(250, 340, 100, 30);
			this.add(c4);

			start_Button = new JButton("START");
			start_Button.setBounds(150, 400, 100, 50);
			start_Button.setBackground(new Color(230, 230, 230));
			start_Button.addActionListener(this);
			this.add(start_Button);

			this.getContentPane().setBackground(Color.white);
			this.setVisible(true);
		}

		public void paint(Graphics g) {
			super.paint(g);

			g.setColor(Color.pink);
			g.fillOval(110, 180, 50, 50);

			g.setColor(Color.blue);
			g.fillOval(110, 315, 50, 50);

			g.setColor(Color.green);
			g.fillOval(260, 180, 50, 50);

			g.setColor(Color.cyan);
			g.fillOval(260, 315, 50, 50);

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String actionCmd = arg0.getActionCommand();
			int index = 0;
			ArrayList<String> property_name = new ArrayList<String>();
			if (actionCmd.equals("START")) {

				String checked = cb.getSelectedItem().toString();
				nop = Integer.parseInt(checked);

				if (c1.getState()) {
					noc++;
					c1_state = true;
				}
				if (c2.getState()) {
					noc++;
					c2_state = true;
				}
				if (c3.getState()) {
					noc++;
					c3_state = true;
				}
				if (c4.getState()) {
					noc++;
					c4_state = true;
				}

				if (nop == noc) {
					// 플레이하는 사람의 수에 맞게 Player를 생성하도록 조건문을 써서 만들어야 함
					playerList = new ArrayList<Player>();
					if (this.c1_state == true) {
						p1 = new Player(1, Color.PINK);
						p1.setBounds(0, 0, 200, 100);
						p1.setBorder(eborder);
						playerList.add(index, p1);
						index++;
						gamePage.add(p1);
					}

					if (this.c2_state == true) {
						Player p2 = new Player(2, Color.GREEN);
						p2.setBounds(1370, 0, 200, 100);
						p2.setBorder(eborder);
						playerList.add(index, p2);
						index++;
						gamePage.add(p2);
					}
					if (this.c3_state == true) {
						Player p3 = new Player(3, Color.BLUE);
						p3.setBounds(1370, 700, 200, 100);
						p3.setBorder(eborder);
						playerList.add(index, p3);
						index++;
						gamePage.add(p3);
					}
					if (this.c4_state == true) {
						Player p4 = new Player(4, Color.CYAN);
						p4.setBounds(0, 700, 200, 100);
						p4.setBorder(eborder);
						playerList.add(index, p4);
						index++;
						gamePage.add(p4);
					}
					s.first_setting(this);

					nowplayer = playerList.get(turn);

					showturn = new ShowTurn();
					showturn.setBounds(550, 420, 500, 80);
					gamePage.add(showturn);

					gamePage.repaint();
					dispose();
				} else {
					error_frame ef = new error_frame();
					noc = 0;
					nop = 0;
					c1_state = false;
					c2_state = false;
					c3_state = false;
					c4_state = false;

				}

			}

			s.init(p1, p2, p3, p4, gamePage);

		}
	}

	public class error_frame extends JFrame implements ActionListener {

		public error_frame() {
			this.setSize(500, 300);
			this.setTitle("에러");
			this.setLocationRelativeTo(null);

			JLabel st = new JLabel("인원수와 캐릭터 수가 일치하지 않습니다.");
			JLabel st2 = new JLabel("다시 선택해주세요.");
			this.setLayout(null);
			st.setBounds(130, 50, 400, 30);
			st2.setBounds(200, 110, 150, 30);
			this.add(st);
			this.add(st2);

			JButton confirm = new JButton("확인");
			confirm.addActionListener(this);
			this.add(confirm, BorderLayout.SOUTH);
			confirm.setBounds(220, 200, 70, 30);
			this.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String actionCmd = arg0.getActionCommand();

		if (actionCmd.equals("게임 시작")) {
			startPage.setVisible(false); // 시작페이지는 안 보이게
			gamePage.setVisible(true); // 게임페이지는 보이게
			Setting set = new Setting(); // Setting 객체 생성. Setting은 JFrame 상속한 내부클래스.
		} else if (actionCmd.equals("Rule Book")) {
			RuleBook rb = new RuleBook(); // RoolBook 객체 생성. RoolBook은 JFrame 상속한 내부클래스.
		} else if (actionCmd.equals("굴리기")) {
			if (nowplayer.songwon == 0) {
				this.dice();
				if (siteList.get(nowplayer.total_dice) instanceof NormalSite) {
					NormalSite n = (NormalSite) siteList.get(nowplayer.total_dice);

					if (n.getOwner() != null) {// 주인있는 땅
						if (n.getOwner() == nowplayer) { // 내 땅
							if (n.landmark_OK == false) {// 랜드마크 매입여부
								if (n.house_OK && n.villa_OK && n.hotel_OK) {  //  주택, 빌라, 호텔이 다 지어져있을 때
									LandMarkBuild lmb = new LandMarkBuild(n);// landmark안에서 nowplayer 처리해야된다고,

								} else {

									Purchase p = new Purchase((NormalSite) siteList.get(nowplayer.total_dice));
								}
							}
							else {
								turn+=1;
								turn%=playerList.size();
								nowplayer=playerList.get(turn);
							}

						} 
						else { // 남의 땅을 밟아서 통행료를 내야 할 때
							if (n.toll > nowplayer.money + nowplayer.property_value) {  //  부동산가치+현금 보다 통행료가 더 클 때
								NormalSite ns = null;

								for (int i = 0; i < nowplayer.property.size(); i++) {
									ns = nowplayer.property.get(i);

									nowplayer.money += ns.sell;

									ns.owner = null;
									ns.owner_color = null;

									ns.ttang_OK = false;
									ns.house_OK = false;
									ns.villa_OK = false;
									ns.hotel_OK = false;
									ns.landmark_OK = false;

									ns.takeover = 0;
									ns.sell = 0;
									ns.toll = 0;

									nowplayer.property_value -= (int) ns.ttangp * (0.8);
									nowplayer.property_value -= (int) ns.housep * (0.8);
									nowplayer.property_value -= (int) ns.villap * (0.8);
									nowplayer.property_value -= (int) ns.hotelp * (0.8);
									nowplayer.property_value -= (int) ns.landmarkp * (0.8);

									ns.ttangp = 0;
									ns.housep = 0;
									ns.villap = 0;
									ns.hotelp = 0;
									ns.landmarkp = 0;
								}
								nowplayer.money -= n.toll;
								n.owner.money += nowplayer.money;

								PasanIm psi = new PasanIm(nowplayer);
							} else if (n.toll > nowplayer.money) { // 내 돈보다 통행료가 더 클 때
								SellFrame sf = new SellFrame(n.owner, n.toll - nowplayer.money, n.toll);// 통행료에서 현금을
																										// 빼서줘요.,
							} else {
								Pay pay = new Pay((NormalSite) siteList.get(nowplayer.total_dice));
							}

						}
					} else { // 주인없는 땅
						Purchase p = new Purchase((NormalSite) siteList.get(nowplayer.total_dice));
					}
				}
			} else { // 송원 땅에 있을 때
				SongwonFrame sF = new SongwonFrame();
			}

		}
	}

	public class Purchase extends JFrame implements ActionListener {

		Toolkit t;
		Image house, villa, hotel;

		JLabel ttang_name, total_price, owned1, owned2, owned3;
		JButton calculate, confirm, cancel;
		Checkbox housebox, villabox, hotelbox;

		int total; // 총 가격
		int real_total=0;
		NormalSite current;

		Purchase(NormalSite site) {
			this.setSize(600, 500);
			this.setTitle("땅 구매");
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			

			current = site;

			cancel = new JButton("구매 포기");
			cancel.setBounds(390, 0, 200, 75);
			cancel.setFont(new Font("Serif", Font.BOLD, 20));
			cancel.addActionListener(this);
			this.add(cancel);

			ttang_name = new JLabel(site.getName());
			ttang_name.setBounds(50, 10, 750, 50);
			ttang_name.setFont(new Font("Serif", Font.BOLD, 40));
			this.add(ttang_name);

			housebox = new Checkbox("주택");
			housebox.setBounds(50, 300, 100, 50);
			housebox.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 35));

			villabox = new Checkbox("빌라");
			villabox.setBounds(250, 300, 100, 50);
			villabox.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 35));

			hotelbox = new Checkbox("호텔");
			hotelbox.setBounds(450, 300, 100, 50);
			hotelbox.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 35));

			t = Toolkit.getDefaultToolkit();
			house = t.getImage("src\\house.jpg");
			villa = t.getImage("src\\villa.jpg");
			hotel = t.getImage("src\\hotel.jpg");

			calculate = new JButton("계산");
			calculate.addActionListener(this);
			calculate.setFont(new Font("SanSerif", Font.BOLD, 25));
			calculate.setBounds(20, 380, 90, 50);
			this.add(calculate);

			confirm = new JButton("확인");
			confirm.addActionListener(this);
			confirm.setFont(new Font("SanSerif", Font.BOLD, 25));
			confirm.setBounds(390, 361, 200, 100);
			this.add(confirm);

			total_price = new JLabel("총 가격: " + total);
			this.add(total_price);
			total_price.setBounds(120, 380, 400, 50);
			total_price.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 25));

			owned1 = new JLabel("소유중");
			owned2 = new JLabel("소유중");
			owned3 = new JLabel("소유중");

			if (!site.house_OK) {
				this.add(housebox);
			} else {
				owned1.setBounds(50, 300, 100, 50);
				owned1.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 25));
				this.add(owned1);
			}

			if (!site.villa_OK) {
				this.add(villabox);
			} else {
				owned2.setBounds(250, 300, 100, 50);
				owned2.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 25));
				this.add(owned2);
			}

			if (!site.hotel_OK) {
				this.add(hotelbox);
			} else {
				owned3.setBounds(450, 300, 100, 50);
				owned3.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 25));
				this.add(owned3);
			}

			this.getContentPane().setBackground(Color.white);
			this.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			String actionCmd = e.getActionCommand();

			if (actionCmd.equals("구매 포기")) {
				dispose();
				turn += 1;
				turn = turn % (playerList.size());
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				gamePage.repaint();
			}
			if (actionCmd.equals("계산")) {
				total = 0;
				if ((!current.house_OK) && housebox.getState()) {
					total += current.getHouse();
				}

				if ((!current.villa_OK) && villabox.getState()) {
					total += current.getVilla();
				}

				if ((!current.hotel_OK) && hotelbox.getState()) {
					total += current.getHotel();
				}

				if (!current.ttang_OK) {
					total += current.getTtang();
				}

				total_price.setText("총 가격: " + total);
				this.repaint(); // 총 가격을 repaint하는 역할./
			}
			real_total=0;
			if ((!current.house_OK) && housebox.getState()) {
				real_total += current.getHouse();
			}

			if ((!current.villa_OK) && villabox.getState()) {
				real_total += current.getVilla();
			}

			if ((!current.hotel_OK) && hotelbox.getState()) {
				real_total += current.getHotel();
			}

			if (!current.ttang_OK) {
				real_total += current.getTtang();
			}

			
		

			if (actionCmd.equals("확인")) {
				if (real_total <= nowplayer.money) {  //  현금으로 살 수 있는 금액일 때
					if ((!current.house_OK) && housebox.getState()) { // house를 안 지었고 체크박스에 짔겠다고 했을 때
						current.house_OK = true;
						current.housep = current.house;
						nowplayer.money -= current.house;
						current.takeover += current.housep * 2;
						current.sell += (int) current.housep * (0.8);
						nowplayer.property_value += (int) current.housep * (0.8);
					}
					if ((!current.villa_OK) && villabox.getState()) {
						current.villa_OK = true;
						current.villap = current.villa;
						nowplayer.money -= current.villa;
						current.takeover += current.villap * 2;
						current.sell += (int) current.villap * (0.8);
						nowplayer.property_value += (int) current.villap * (0.8);
					}
					if ((!current.hotel_OK) && hotelbox.getState()) {
						current.hotel_OK = true;
						current.hotelp = current.hotel;
						nowplayer.money -= current.hotel;
						current.takeover += current.hotel * 2;
						current.sell += (int) current.hotel * (0.8);
						nowplayer.property_value += (int) current.hotelp * (0.8);
					}
					if (!current.ttang_OK) { // 땅을 처음 밟았을 때(땅, 주택, 빌라, 호텔 x)
						nowplayer.money -= current.ttang;
						current.ttang_OK = true;
						current.ttangp = current.ttang;

						current.owner_color = nowplayer.player_color;

						current.takeover += current.ttangp * 2;
						current.sell += (int) current.ttangp * (0.8);

						nowplayer.property_value += (int) current.ttangp * (0.8);
					}

					current.setToll();
					
					dispose();
					nowplayer.repaint();

					if (current.owner != nowplayer) { // 현재 땅을 이미 구매하지 않은 상태일 때(처음 구매할 때만)
						nowplayer.property.add(current); // Player 클래스에 current(땅) 추가
						current.owner = nowplayer;
					}

					boolean correct1 = true;
					boolean correct2 = true;
					boolean correct3 = true;
					for (int i = 0; i < subway1.size(); i++) {
						if (!nowplayer.property.contains(subway1.get(i))) {
							correct1 = false;
						}
					}
					for (int i = 0; i < subway1.size(); i++) {
						if (!nowplayer.property.contains(subway2.get(i))) {
							correct2 = false;
						}
					}
					for (int i = 0; i < subway1.size(); i++) {
						if (!nowplayer.property.contains(subway3.get(i))) {
							correct3 = false;
						}
					}
					if (correct1 == true) { // 1호선 독점
						SubwayWinner sw = new SubwayWinner(nowplayer, 1);
					}
					if (correct2 == true) { // 2호선 독점
						SubwayWinner sw = new SubwayWinner(nowplayer, 2);
					}
					if (correct3 == true) { // 3호선 독점
						SubwayWinner sw = new SubwayWinner(nowplayer, 3);
					}

					turn += 1;
					turn = turn % (playerList.size());
					nowplayer = playerList.get(turn);

					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					gamePage.repaint();

				} 
				else {  //  확인을 눌렀을 때 돈이 부족할 때
					MoneyLack ml = new MoneyLack();
					
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					gamePage.repaint();
				}

			}

		}

		public void paint(Graphics g) {
			super.paint(g);

			g.drawLine(0, 110, 600, 110);
			g.drawLine(400, 0, 400, 500);
			g.drawLine(0, 400, 600, 400);
			g.drawLine(200, 110, 200, 400);
			g.drawLine(0, 320, 600, 320);

			g.drawImage(house, 0, 111, this);
			g.drawImage(villa, 200, 111, this);
			g.drawImage(hotel, 400, 111, this);
		}

	}
	
	public class MoneyLack extends JFrame implements ActionListener {  
		
		JLabel str;
		JButton ok;
		
		MoneyLack() {
			this.setSize(500, 400);
			this.setTitle("현금 부족");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			str = new JLabel("현금이 부족합니다.");
			str.setBounds(130, 100, 400, 50);
			str.setFont(new Font("Monospaced", Font.BOLD, 25));
			this.add(str);
			
			ok = new JButton("확인");
			ok.setBounds(195, 220, 100, 50);
			ok.setFont(new Font("Monospaced", Font.BOLD, 25));
			ok.addActionListener(this);
			this.add(ok);
			
			this.getContentPane().setBackground(Color.WHITE);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();
			
			if(actionCmd.equals("확인")) {
				this.dispose();
			}
		}
	}
	public class Pay extends JFrame implements ActionListener {
		int toll;
		JButton confirm;
		JLabel str;
		// Toolkit t;
		// Image house,villa,hotel;
		NormalSite current;

		Pay(NormalSite site) {
			current = site;
			this.setSize(500, 300);
			this.setTitle("통행료 지불");
			this.setLayout(null);
			this.setLocationRelativeTo(null);

			confirm = new JButton("확인");
			confirm.addActionListener(this);
			confirm.setBounds(220, 200, 70, 30);
			this.add(confirm);

			toll = site.getToll();

			str = new JLabel("통행료는" + toll + "입니다.");
			str.setFont(new Font("SanSerif", Font.BOLD, 30));
			str.setBounds(100, 100, 300, 50);
			this.add(str);

			this.getContentPane().setBackground(Color.WHITE);

			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			String actionCmd = e.getActionCommand();
			NormalSite current = (NormalSite) (siteList.get(nowplayer.total_dice));
			if (actionCmd.equals("확인")) {
				dispose();
				
				if(nowplayer.money>=toll) {
					nowplayer.money -= toll; // 통행료 지불
					current.owner.money += toll; // 땅 주인에게 통행료 지급
				}
				else if(nowplayer.property_value+nowplayer.money>=toll) {
					SellFrame sfrs = new SellFrame(current.owner, toll - nowplayer.money, toll);
				}
				else {
					NormalSite ns = null;

					for (int i = 0; i < nowplayer.property.size(); i++) {
						ns = nowplayer.property.get(i);

						nowplayer.money += ns.sell;

						ns.owner = null;
						ns.owner_color = null;

						ns.ttang_OK = false;
						ns.house_OK = false;
						ns.villa_OK = false;
						ns.hotel_OK = false;
						ns.landmark_OK = false;

						ns.takeover = 0;
						ns.sell = 0;
						ns.toll = 0;

						nowplayer.property_value -= (int) ns.ttangp * (0.8);
						nowplayer.property_value -= (int) ns.housep * (0.8);
						nowplayer.property_value -= (int) ns.villap * (0.8);
						nowplayer.property_value -= (int) ns.hotelp * (0.8);
						nowplayer.property_value -= (int) ns.landmarkp * (0.8);

						ns.ttangp = 0;
						ns.housep = 0;
						ns.villap = 0;
						ns.hotelp = 0;
						ns.landmarkp = 0;
						
						
					}
					
					nowplayer.money -= current.toll;
					current.owner.money += nowplayer.money;

					PasanIm psi = new PasanIm(nowplayer);
				}
				

				if (current.landmark_OK == false) { // 아직 랜드마크가 안 지어졌을 때
					if (current.takeover <= nowplayer.money) { // 인수할 수 있는 돈이 있을 때
						TakeOver tk = new TakeOver((NormalSite) siteList.get(nowplayer.total_dice));
					}
				}
				else {  //  남의 땅에 랜드마크가 이미 지어져있을 때
					turn+=1;
					turn%=playerList.size();
					nowplayer=playerList.get(turn);
				}
			}
			gamePage.repaint();
		}

	}

	public class TakeOver extends JFrame implements ActionListener {
		NormalSite current;

		TakeOver(NormalSite site) {
			this.setSize(500, 300);
			this.setTitle("인수");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			current = site;

			JLabel st1 = new JLabel("인수가는 " + site.takeover + "입니다.");
			st1.setBounds(150, 40, 400, 50);
			st1.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 25));
			this.add(st1);

			JLabel st2 = new JLabel("인수하시겠습니까?");
			st2.setBounds(150, 95, 250, 50);
			st2.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 25));
			this.add(st2);

			JButton take = new JButton("인수");
			take.addActionListener(this);
			take.setBounds(0, 160, 250, 100);
			this.add(take);

			JButton giveup = new JButton("인수 포기");
			giveup.addActionListener(this);
			giveup.setBounds(250, 160, 250, 100);
			this.add(giveup);

			this.getContentPane().setBackground(Color.WHITE);

			this.setVisible(true);
		}

		public void paint(Graphics g) {
			super.paint(g);

			g.drawLine(0, 200, 500, 200);
			g.drawLine(250, 200, 250, 300);
		}

		public void actionPerformed(ActionEvent e) {
			String actionCmd = e.getActionCommand();

			if (actionCmd.equals("인수")) {
				nowplayer.money -= current.takeover;
				nowplayer.property_value += current.sell;
				current.owner.property_value -= current.sell;
				current.owner.property.remove(current); // current(땅) 객체를 ArrayList에서 제거

				current.owner = nowplayer;
				nowplayer.property.add(current); // 인수하는 측은 current(땅)을 ArrayList에 추가
				current.owner_color = nowplayer.player_color;


				if (current.house_OK && current.villa_OK && current.hotel_OK) { // 세 개 다 지은 경우
					LandMarkBuild lmblmb = new LandMarkBuild(current);
				} else { // 세 개 중 하나라도 안 지은 게 있을 경우
					Purchase ppp = new Purchase(current);
				}

				gamePage.repaint();
				dispose();

			} else if (actionCmd.equals("인수 포기")) {
				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				dispose();
				gamePage.repaint();
			}
		}
	}

	public class LandMarkBuild extends JFrame implements ActionListener {

		NormalSite current;

		LandMarkBuild(NormalSite site) {
			this.setSize(700, 300);
			this.setTitle("랜드마크 건설");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			current = site;

			JLabel str0 = new JLabel(site.name);
			str0.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 25));
			str0.setBounds(320, 10, 400, 50);
			this.add(str0);

			JLabel str = new JLabel("랜드마크를 세우시겠습니까?");
			str.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 25));
			str.setBounds(320, 70, 400, 50);
			this.add(str);

			JLabel str2 = new JLabel("비용은: " + site.landmark + "입니다.");
			str2.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 25));
			str2.setBounds(320, 110, 400, 50);
			this.add(str2);

			JButton build = new JButton("건설");
			build.setBounds(291, 172, 200, 90);
			build.addActionListener(this);
			this.add(build);

			JButton giveup = new JButton("건설 포기");
			giveup.setBounds(491, 172, 200, 90);
			giveup.addActionListener(this);
			this.add(giveup);

			this.setVisible(true);
		}

		public void paint(Graphics g) {
			super.paint(g);

			// 랜드마크 사진크기 300X300

			g.drawLine(300, 0, 300, 400);
			g.drawLine(300, 210, 700, 210);
			g.drawLine(500, 210, 500, 300);
			g.drawImage(current.img, 0, 0, this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String actionCmd = arg0.getActionCommand();

			if (actionCmd.equals("건설")) {
				current.landmark_OK = true;
				nowplayer.money -= current.landmark;
				current.landmarkp = current.landmark;
				nowplayer.property_value += (int) current.landmarkp * (0.8);
				current.setToll();
				
				// 뭐 있지싶은데;;

				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				gamePage.repaint();
				dispose();
			}
			if (actionCmd.equals("건설 포기")) {
				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				dispose();
			}
		}
	}

	public class ChickenFestivalFrame extends JFrame implements ActionListener {
		JComboBox jcb;
		JButton btn, btn2;
		String[] property_array;
		int index = 0;
		NormalSite SelectedSite;

		ChickenFestivalFrame(ArrayList<String> property_name) {
			this.setSize(500, 300);
			this.setTitle("치맥축제");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			property_array = new String[property_name.size()];

			for (int i = 0; i < property_name.size(); i++) {

				property_array[i] = property_name.get(i);
			}

			btn = new JButton("확인");
			btn.setBounds(220, 200, 80, 50);
			btn.setFont(new Font("monospaced", Font.BOLD | Font.PLAIN, 10));
			btn.addActionListener(this);

			if (property_name.size() == 0) {
				String[] no = { "개최할 땅이 없습니다." };
				jcb = new JComboBox(no); // 여기 구현해야 함!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				jcb.setBounds(100, 80, 300, 50);
				btn.setVisible(false);
				btn2 = new JButton("ㅠㅠ");
				btn2.setBounds(200, 200, 80, 50);
				btn2.setFont(new Font("monospaced", Font.BOLD | Font.PLAIN, 10));
				this.add(btn2);
				btn2.setVisible(true);
				btn2.addActionListener(this);
			} else {
				jcb = new JComboBox(property_array); // 여기 구현해야 함!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				jcb.setBounds(100, 80, 300, 50);
			}

			this.getContentPane().setBackground(Color.WHITE);

			this.add(jcb);
			this.add(btn);

			this.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			String actionCmd = e.getActionCommand();
			if (actionCmd.equals("확인")) {
				try {
					index = jcb.getSelectedIndex();
					SelectedSite = nowplayer.property.get(index);
					nowplayer.property_value += SelectedSite.sell;

					SelectedSite.housep *= 2;
					SelectedSite.villap *= 2;
					SelectedSite.hotelp *= 2;
					SelectedSite.landmarkp *= 2;
					SelectedSite.ttangp *= 2;

					SelectedSite.sell *= 2;

					SelectedSite.setToll();
					dispose();

					turn += 1;
					turn %= playerList.size();
					nowplayer = playerList.get(turn);
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					gamePage.repaint();
				} catch (ArrayIndexOutOfBoundsException ex) {
					ex.getStackTrace();
					showturn.chickenChecked = true;

					turn += 1;
					turn %= playerList.size();
					nowplayer = playerList.get(turn);
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					gamePage.repaint();
					dispose();
				}
			}
			if (actionCmd.equals("ㅠㅠ")) {
				dispose();
				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				gamePage.repaint();
			}
		}
	}

	public class ShowTurn extends JPanel {

		JLabel state;
		JLabel nofestival;
		boolean chickenChecked;

		public ShowTurn() {
			this.setLayout(null);

			state = new JLabel("현재  Player" + nowplayer.player_num + "의 차례입니다.");
			state.setBounds(0, 0, 500, 80);
			state.setFont(new Font("Monospaced", Font.PLAIN | Font.BOLD, 30));
			this.add(state);

			nofestival = new JLabel("축제를 개최할 땅이 없습니다.");
			nofestival.setBounds(0, 0, 500, 80);
			nofestival.setVisible(false);
			this.add(nofestival);

			this.setBackground(Color.WHITE);

			this.setVisible(true);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				if (chickenChecked) {
					nofestival.setVisible(true);
					state.setVisible(false);
					chickenChecked = false;
				} else {
					state.setText("현재  Player" + nowplayer.player_num + "의 차례입니다.");
					nofestival.setVisible(false);
					state.setVisible(true);
				}

			} catch (NullPointerException e) {
				e.getStackTrace();
			}
		}
	}

	public class SongwonFrame extends JFrame implements ActionListener {

		JPanel p1, p2;
		JLabel str, str2, str3, str4, str5, str6, btnLabel, btnLabel2;
		JButton btn, btn2, btn3;
		int num;
		int rnum, rnum2;

		SongwonFrame() {
			this.setSize(600, 450);
			this.setTitle("송원");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			p1 = new JPanel();
			p2 = new JPanel();
			p1.setLayout(null);
			p2.setLayout(null);
			p1.setVisible(true);
			p2.setVisible(false);

			str = new JLabel("돈을 지불하고 다음 턴에 바로 탈출한다."); // 300차감
			str2 = new JLabel("주사위를 굴린다. (더블이 발생할 시 탈출)");
			str3 = new JLabel("탈출까지 남은 횟수: " + nowplayer.songwon);
			str4 = new JLabel("탈출합니다.");
			str5 = new JLabel("더블이 나왔습니다.");
			str6 = new JLabel("더블이 아닙니다.");

			

			btn = new JButton("돈 지불");
			btn2 = new JButton("주사위 굴리기");
			btn3 = new JButton("확인");

			if (nowplayer.songwon == 0) {//
				str4.setBounds(200, 200, 300, 80);
				str4.setFont(new Font("Monospaced", Font.BOLD, 25));
				p1.add(str4);

				nowplayer.songwon = 999;
			} else {
				str.setBounds(80, 20, 500, 80);
				str2.setBounds(80, 120, 500, 80);
				str3.setBounds(80, 220, 400, 80);

				str.setFont(new Font("Monospaced", Font.BOLD, 20));
				str2.setFont(new Font("Monospaced", Font.BOLD, 20));
				str3.setFont(new Font("Monospaced", Font.BOLD, 20));

				btn.setBounds(0, 300, 300, 100);
				btn2.setBounds(300, 300, 300, 100);
				btn.addActionListener(this);
				btn2.addActionListener(this);

				str5.setBounds(165, 250, 300, 50);
				str5.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 30));

				p1.add(str);
				p1.add(str2);
				p1.add(str3);
				p1.add(btn);
				p1.add(btn2);
				p2.add(str5);
			}

			// setBounds 처리

			p1.setBounds(0, 0, 600, 420);
			p2.setBounds(0, 0, 600, 420);
			p1.setBackground(Color.WHITE);
			p2.setBackground(Color.WHITE);
			this.add(p1);
			this.add(p2);
			this.setVisible(true);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();
			if (actionCmd.contentEquals("돈 지불")) {
				if(nowplayer.money>=300) {

					nowplayer.money -= 300;
					nowplayer.songwon = 0;

					turn += 1;
					turn %= playerList.size();
					nowplayer = playerList.get(turn);
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					dispose();
				}
				else {
					SMoneyLack lmlm = new SMoneyLack();
					dispose();
					
				}
				
				gamePage.repaint();
				
			}
			if (actionCmd.contentEquals("주사위 굴리기")) {
				Random srand = new Random();
				Random srand2 = new Random();

				rnum = srand.nextInt(6) + 1;
				rnum2 = srand.nextInt(6) + 1;

				btnLabel = new JLabel("1번 주사위: " + rnum);
				btnLabel2 = new JLabel("2번 주사위: " + rnum2);

				btnLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
				btnLabel.setBounds(65, 180, 200, 50);
				btnLabel2.setFont(new Font("Monospaced", Font.BOLD, 20));
				btnLabel2.setBounds(360, 180, 200, 50);
				str6.setBounds(165, 250, 300, 50);
				str6.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 30));
				btn3.setFont(new Font("Monospaced", Font.BOLD, 20));
				btn3.setBounds(240, 320, 100, 50);
				btn3.addActionListener(this);

				if (rnum == rnum2) {

					str5.setVisible(true);
					str6.setVisible(false);
					nowplayer.songwon = 0;
				} else {

					str5.setVisible(false);
					str6.setVisible(true);
				}
				p2.add(btnLabel);
				p2.add(btnLabel2);
				p2.add(btn3);
				p2.add(str6);

				p1.setVisible(false);
				p2.setVisible(true);

				nowplayer.songwon -= 1;
				this.repaint();

			}
			if (actionCmd.equals("확인")) {
				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				if (s_7.festival > 0) {
					s_7.festival--;
					s_7.setToll(2);
				}
				if (s_7.festival == 0) {
					s_7.setToll();
				}

				if (s_12.festival > 0) {
					s_12.festival--;
					s_12.setToll(2);
				}
				if (s_12.festival == 0) {
					s_12.setToll();
				}
				gamePage.repaint();
				dispose();
			}
		}

		public void paint(Graphics g) {
			super.paint(g);

			switch (rnum) {
			case 1:
				g.drawImage(dice1, 80, 60, this);
				break;
			case 2:
				g.drawImage(dice2, 80, 60, this);
				break;
			case 3:
				g.drawImage(dice3, 80, 60, this);
				break;
			case 4:
				g.drawImage(dice4, 80, 60, this);
				break;
			case 5:
				g.drawImage(dice5, 80, 60, this);
				break;
			case 6:
				g.drawImage(dice6, 80, 60, this);
				break;
			}

			switch (rnum2) {
			case 1:
				g.drawImage(dice1, 375, 60, this);
				break;
			case 2:
				g.drawImage(dice2, 375, 60, this);
				break;
			case 3:
				g.drawImage(dice3, 375, 60, this);
				break;
			case 4:
				g.drawImage(dice4, 375, 60, this);
				break;
			case 5:
				g.drawImage(dice5, 375, 60, this);
				break;
			case 6:
				g.drawImage(dice6, 375, 60, this);
				break;
			}
		}
	}
	
	public class SMoneyLack extends JFrame implements ActionListener {  
		
		JLabel str;
		JButton ok;
		
		SMoneyLack() {
			this.setSize(500, 400);
			this.setTitle("현금 부족");
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			str = new JLabel("현금이 부족합니다.");
			str.setBounds(130, 100, 400, 50);
			str.setFont(new Font("Monospaced", Font.BOLD, 25));
			this.add(str);
			
			ok = new JButton("확인");
			ok.setBounds(195, 220, 100, 50);
			ok.setFont(new Font("Monospaced", Font.BOLD, 25));
			ok.addActionListener(this);
			this.add(ok);
			
			this.getContentPane().setBackground(Color.WHITE);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();
			
			if(actionCmd.equals("확인")) {
				SongwonFrame sdfsdg=new SongwonFrame();
				this.dispose();
			}
		}
	}
	public class SubwayWinner extends JFrame implements ActionListener {

		JLabel str, str1, str2;
		JButton end;

		SubwayWinner(Player winner, int subway_num) {
			this.setSize(700, 480);
			this.setLayout(null);
			this.setLocationRelativeTo(null);

			str = new JLabel("Winner: Player" + winner.player_num);
			str.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 50));
			str.setBounds(140, 50, 600, 100);
			str1 = new JLabel("PLayer " + winner.player_num + "이 " + subway_num + "을 독점하였습니다.");
			str1.setFont(new Font("Monospaced", Font.BOLD, 30));
			str1.setBounds(120, 170, 600, 50);
			str2 = new JLabel("!!!축하합니다!!!");
			str2.setFont(new Font("Monospaced", Font.BOLD, 30));
			str2.setBounds(220, 250, 450, 50);

			end = new JButton("종료");
			end.setBounds(285, 350, 100, 50);
			end.setFont(new Font("Monospaced", Font.BOLD, 30));
			end.addActionListener(this);

			this.add(str);
			this.add(str1);
			this.add(str2);
			this.add(end);

			this.getContentPane().setBackground(Color.WHITE);

			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();

			if (actionCmd.equals("종료")) {
				System.exit(0);
			}
		}

	}// subwayFrame;

	public class GoldCardFrame extends JFrame implements ActionListener {

		JScrollPane scrolledText;
		Image i3, i4, i5, i6;
		JPanel s, p1, p2, p3, p4, p5, p6, tapanel;
		JLabel cardname, p3_label1, p3_label2, p4_label1, p4_label2, p5_label1, p5_label2, p6_label1, p6_label2,
				p6_label3;
		JTextArea ta;
		JButton ok, explain, goback, end1, end2, end3, end4, end5, end6;
		int random;

		GoldCardFrame(int rn) {
			this.setSize(400, 600);
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			this.setTitle("황금 카드");
			random = rn;

			s = new JPanel();
			s.setLayout(null);
			s.setBounds(0, 0, 400, 600);

			tapanel = new JPanel(); // 상세 설명 버튼 누르면 이 패널로 이동(JTextArea 보여주는 패널)
			tapanel.setBounds(0, 0, 400, 600);
			tapanel.setLayout(null);
			goback = new JButton("돌아가기");
			goback.setBounds(150, 450, 100, 50);

			cardname = new JLabel("<" + GoldCard.get(rn) + ">");
			cardname.setBounds(100, 380, 380, 40);
			cardname.setFont(new Font("Monospaced", Font.BOLD, 20));

			JPanel card = new JPanel();
			card.add(cardname, BorderLayout.CENTER);
			card.setBounds(0, 380, 380, 40);
			card.setBackground(Color.white);

			i3 = t.getImage("src\\환승 카드.jpg");
			i4 = t.getImage("src\\이월드 별빛축제 카드.jpg");
			i5 = t.getImage("src\\경북대 축제 카드.jpg");
			i6 = t.getImage("src\\국세청.jpg");

			String space = "\n";
			String line = null, total = "";
			try {
				Scanner inputStream = null;

				switch (random) {
				case 2:
					inputStream = new Scanner(new FileInputStream("src\\환승 카드.txt"));
					break;
				case 3:
					inputStream = new Scanner(new FileInputStream("src\\이월드 별빛축제 카드.txt"));
					break;
				case 4:
					inputStream = new Scanner(new FileInputStream("src\\경북대 축제 카드.txt"));
					break;
				case 5:
					inputStream = new Scanner(new FileInputStream("src\\세금 지원 카드.txt"));
					break;
				}
				line = inputStream.nextLine(); // 한 줄 읽어오기
				total = line;
				total += space;
				while (line != null) {
					line = inputStream.nextLine();
					total += line;
					total += space;
				}
				inputStream.close();
			} catch (IOException e) {
				e.getStackTrace();
			} catch (NoSuchElementException e) {
				e.getStackTrace();
			}
			ta = new JTextArea(50, 5);
			ta.setText(total);
			ta.setVisible(true);
			ta.setBounds(0, 0, 400, 400);
			ta.setFont(new Font("Monospaced", Font.PLAIN, 15));
			scrolledText = new JScrollPane(ta);
			scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			tapanel.add(scrolledText);
			scrolledText.setBounds(0, 0, 400, 400);
			p1 = new JPanel();
			p2 = new JPanel();
			p3 = new JPanel();
			p4 = new JPanel();
			p5 = new JPanel();
			p6 = new JPanel();

			end3 = new JButton("닫기");
			end3.setBounds(140, 450, 100, 50);
			end3.addActionListener(this);

			p3_label1 = new JLabel("GET");
			p3_label1.setBounds(160, 205, 330, 70);
			p3_label2 = new JLabel("환승 티켓");
			p3_label2.setBounds(110, 315, 330, 70);
			p3_label1.setFont(new Font("Monospaced", Font.BOLD, 35));
			p3_label2.setFont(new Font("Monospaced", Font.BOLD, 35));

			p3.add(end3);
			p3.add(p3_label1);
			p3.add(p3_label2);
			p3.setLayout(null);
			p3.setBounds(0, 0, 400, 600);

			end4 = new JButton("닫기");
			end4.setBounds(140, 450, 100, 50);
			end4.addActionListener(this);

			p4_label1 = new JLabel("앞으로 3턴동안 E월드에");
			p4_label1.setBounds(80, 205, 330, 70);
			p4_label2 = new JLabel("축제를 개최합니다.");
			p4_label2.setBounds(100, 315, 330, 70);
			p4_label1.setFont(new Font("Monospaced", Font.BOLD, 20));
			p4_label2.setFont(new Font("Monospaced", Font.BOLD, 20));

			p4.add(end4);
			p4.add(p4_label1);
			p4.add(p4_label2);
			p4.setLayout(null);
			p4.setBounds(0, 0, 400, 600);

			end5 = new JButton("닫기");
			end5.setBounds(140, 450, 100, 50);
			end5.addActionListener(this);

			p5_label1 = new JLabel("앞으로 3턴동안 경북대학교에");
			p5_label1.setBounds(40, 205, 330, 70);
			p5_label2 = new JLabel("축제를 개최합니다.");
			p5_label2.setBounds(100, 315, 330, 70);

			p5.setLayout(null);
			p5.setBounds(0, 0, 400, 600);
			p5.add(end5);
			p5.add(p5_label1);
			p5.add(p5_label2);
			p5_label1.setFont(new Font("Monospaced", Font.BOLD, 20));
			p5_label2.setFont(new Font("Monospaced", Font.BOLD, 20));

			p6.setLayout(null);
			p6.setBounds(0, 0, 400, 600);

			p6_label1 = new JLabel("Player" + nowplayer.player_num + "에게");
			p6_label1.setFont(new Font("Monospaced", Font.BOLD, 25));
			p6_label1.setBounds(120, 90, 300, 70);
			p6.add(p6_label1);

			p6_label2 = new JLabel("국세청에서 세금");
			p6_label2.setFont(new Font("Monospaced", Font.BOLD, 25));
			p6_label2.setBounds(100, 205, 300, 70);
			p6.add(p6_label2);

			p6_label3 = new JLabel(tax.tax_money + "만원이 지원됩니다.");
			p6_label3.setFont(new Font("Monospaced", Font.BOLD, 25));
			p6_label3.setBounds(60, 315, 300, 70);
			p6.add(p6_label3);

			end6 = new JButton("닫기");
			end6.setBounds(150, 450, 100, 50);
			end6.addActionListener(this);

			p6.add(end6);

			ok = new JButton("확인");
			ok.setBounds(155, 500, 80, 30);
			ok.addActionListener(this);

			explain = new JButton("상세 설명");
			explain.setBounds(143, 440, 100, 30);
			explain.addActionListener(this);

			goback.addActionListener(this);
			tapanel.add(goback);
			tapanel.add(scrolledText);

			// s.add(cardname);
			s.add(explain);
			s.add(ok);
			s.add(card);

			this.add(s);
			this.add(p1);
			this.add(p2);
			this.add(p3);
			this.add(p4);
			this.add(p5);
			this.add(p6);

			this.add(tapanel);
			tapanel.setVisible(false);

			s.setVisible(true);
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(false);
			p4.setVisible(false);
			p5.setVisible(false);
			p6.setVisible(false);

			p1.setBackground(Color.white);
			p2.setBackground(Color.white);
			p3.setBackground(Color.white);
			p4.setBackground(Color.white);
			p5.setBackground(Color.white);
			p6.setBackground(Color.white);

			s.setBackground(Color.white);
			this.getContentPane().setBackground(Color.white);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();

			if (actionCmd.equals("상세 설명")) {
				s.setVisible(false);
				tapanel.setVisible(true);
			}
			if (actionCmd.equals("확인")) {
				switch (random) {
				case 2:
					p3.setVisible(true);
					s.setVisible(false);

					break;
				case 3:
					p4.setVisible(true);
					s.setVisible(false);

					s_12.setToll(2);
					s_12.festival += (3 * playerList.size() + 1);
					break;
				case 4:
					p5.setVisible(true);
					s.setVisible(false);

					s_7.setToll(2);
					s_7.festival += (3 * playerList.size() + 1);
					break;
				case 5:
					p6.setVisible(true);
					s.setVisible(false);

					nowplayer.money += tax.tax_money;
					tax.tax_money = 0;
					break;
				}

				this.repaint();
			}
			if (actionCmd.equals("돌아가기")) { // 상세 설명 읽은 후 s패널로 돌아가기
				s.setVisible(true);
				tapanel.setVisible(false);
				this.repaint();
			}
			if (actionCmd.equals("닫기")) { // case 별로 다 적용이 되는 버튼
				dispose();
				switch (random) {
				case 2:
					nowplayer.subway_ticket += 1;
					//System.out.println(nowplayer.subway_ticket);
					break;
				case 3:
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					dispose();
					break;
				case 4:
					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					dispose();
					break;
				case 5:

					if (s_7.festival > 0) {
						s_7.festival--;
						s_7.setToll(2);
					}
					if (s_7.festival == 0) {
						s_7.setToll();
					}

					if (s_12.festival > 0) {
						s_12.festival--;
						s_12.setToll(2);
					}
					if (s_12.festival == 0) {
						s_12.setToll();
					}
					break;
				}
				turn += 1;
				turn %= playerList.size();
				nowplayer = playerList.get(turn);
				gamePage.repaint();
			}
		}

		public void paint(Graphics g) {
			super.paint(g);

			if (s.isVisible() == true) {
				switch (random) { // 사진크기는 400x400
				case 2:
					g.drawImage(i3, 0, 0, this);
					break;
				case 3:
					g.drawImage(i4, 0, 0, this);
					break;
				case 4:
					g.drawImage(i5, 0, 0, this);
					break;
				case 5:
					g.drawImage(i6, 0, 50, this);
					break;
				}
			}

		}
	}

	public class SellFrame extends JFrame implements ActionListener {

		JComboBox jcb1, jcb2;
		ArrayList<NormalSite> have, sell;
		String[] have_string, sell_string;
		JButton add, remove, calculate, end, re;
		JLabel have_label, sell_label, money_label, needed_label;
		int sell_money;
		Player site_owner; // 땅 주인
		int needed_money;
		int total_money;

		SellFrame(Player site_owner, int needed_money, int total_money) {
			this.setSize(1000, 550);
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			this.total_money = total_money;
			this.site_owner = site_owner;
			this.needed_money = needed_money; // 줘야되는 돈에서 현재 가지고있는 현금 뺀값.,

			have = new ArrayList<NormalSite>(20);
			sell = new ArrayList<NormalSite>(20);
			have_string = new String[nowplayer.property.size()];
			sell_string = new String[nowplayer.property.size()];

			for (int i = 0; i < nowplayer.property.size(); i++) {
				have.add(nowplayer.property.get(i));
				have_string[i] = nowplayer.property.get(i).name;
			}

			jcb1 = new JComboBox(have_string);
			jcb2 = new JComboBox(sell_string);

			jcb1.setBounds(100, 170, 200, 50);
			jcb2.setBounds(700, 170, 200, 50);

			this.add(jcb1);
			this.add(jcb2);

			add = new JButton(">>>");
			add.setBounds(450, 100, 70, 50);
			add.addActionListener(this);
			remove = new JButton("<<<");
			remove.setBounds(450, 200, 70, 50);
			remove.addActionListener(this);

			calculate = new JButton("계산");
			calculate.setBounds(160, 420, 100, 50);
			calculate.addActionListener(this);

			have_label = new JLabel("<소유 땅>");
			have_label.setBounds(140, 100, 300, 50);
			have_label.setFont(new Font("Monospaced", Font.BOLD, 25));
			sell_label = new JLabel("<매각 땅>");
			sell_label.setBounds(740, 100, 300, 50);
			sell_label.setFont(new Font("Monospaced", Font.BOLD, 25));

			sell_money = 0;
			money_label = new JLabel("매각 금액: " + sell_money);
			money_label.setFont(new Font("Monospaced", Font.BOLD, 25));
			money_label.setBounds(720, 300, 200, 50);

			needed_label = new JLabel("필요 금액: " + this.needed_money);
			needed_label.setFont(new Font("Monospaced", Font.BOLD, 25));
			needed_label.setBounds(120, 300, 200, 50);

			end = new JButton("확인");
			end.setBounds(760, 420, 100, 50);
			end.addActionListener(this);

			this.getContentPane().setBackground(Color.WHITE);

			this.add(add);
			this.add(remove);
			this.add(have_label);
			this.add(sell_label);
			this.add(calculate);
			this.add(end);
			this.add(money_label);
			this.add(needed_label);

			this.setVisible(true);
		}

		public void sellSite(Player p, NormalSite ns) {// 돈받을사람,noplayer의 매각땅.
			nowplayer.money += ns.sell;

			ns.owner = null;
			ns.owner_color = null;

			ns.ttang_OK = false;
			ns.house_OK = false;
			ns.villa_OK = false;
			ns.hotel_OK = false;
			ns.landmark_OK = false;

			ns.takeover = 0;
			ns.sell = 0;
			ns.toll = 0;

			nowplayer.property_value -= (int) ns.ttangp * (0.8);
			nowplayer.property_value -= (int) ns.housep * (0.8);
			nowplayer.property_value -= (int) ns.villap * (0.8);
			nowplayer.property_value -= (int) ns.hotelp * (0.8);
			nowplayer.property_value -= (int) ns.landmarkp * (0.8);

			ns.ttangp = 0;
			ns.housep = 0;
			ns.villap = 0;
			ns.hotelp = 0;
			ns.landmarkp = 0;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String actionCmd = arg0.getActionCommand();

			if (actionCmd.equals(">>>")) {
				try {
					String s123 = jcb1.getSelectedItem().toString();
					int index = -1;

					for (int i = 0; i < have_string.length; i++) {
						if (have_string[i].equals(s123)) {
							index = i;
						}
					}

					NormalSite tmp = have.get(index); // 사용자가 선택한 NormalSite를 임시로 저장
					have.remove(tmp); // 그 NormalSite를 지움

					sell.add(tmp);
					sell_string = new String[sell.size()];
					for (int i = 0; i < sell_string.length; i++) {
						sell_string[i] = sell.get(i).name;
					}

					String[] tmpstring = new String[have_string.length - 1];
					for (int i = 0; i < index; i++) {
						tmpstring[i] = have_string[i];
					}
					for (int i = index + 1; i < have_string.length; i++) {
						tmpstring[i - 1] = have_string[i];
					}

					have_string = new String[tmpstring.length];
					for (int i = 0; i < tmpstring.length; i++) {
						have_string[i] = tmpstring[i];
					}

					jcb2.removeAllItems(); // 싹 다 지우고
					for (int i = 0; i < sell_string.length; i++) {
						jcb2.addItem(sell_string[i]);
					}

					jcb1.removeAllItems();
					for (int i = 0; i < have_string.length; i++) {
						jcb1.addItem(have_string[i]);
					}
				} catch (NullPointerException e) {
					e.getStackTrace();
				}

				this.repaint();
			}

			if (actionCmd.equals("<<<")) {
				try {
					String s123 = jcb2.getSelectedItem().toString();
					int index = -1;

					for (int i = 0; i < sell_string.length; i++) {
						if (sell_string[i].equals(s123)) {
							index = i;
						}
					}

					NormalSite tmp = sell.get(index); // 사용자가 선택한 NormalSite를 임시로 저장
					sell.remove(tmp); // 그 NormalSite를 지움

					have.add(tmp);
					have_string = new String[have.size()];
					for (int i = 0; i < have_string.length; i++) {
						have_string[i] = have.get(i).name;
					}

					String[] tmpstring = new String[sell_string.length - 1];
					for (int i = 0; i < index; i++) {
						tmpstring[i] = sell_string[i];
					}
					for (int i = index + 1; i < sell_string.length; i++) {
						tmpstring[i - 1] = sell_string[i];
					}

					sell_string = new String[tmpstring.length];
					for (int i = 0; i < tmpstring.length; i++) {
						sell_string[i] = tmpstring[i];
					}

					jcb1.removeAllItems(); // 싹 다 지우고
					for (int i = 0; i < have_string.length; i++) {
						jcb1.addItem(have_string[i]);
					}

					jcb2.removeAllItems();
					for (int i = 0; i < sell_string.length; i++) {
						jcb2.addItem(sell_string[i]);
					}
				} catch (NullPointerException e) {
					e.getStackTrace();
				}

				this.repaint();
			}
			if (actionCmd.equals("계산")) {
				sell_money = 0;

				for (int i = 0; i < sell.size(); i++) {
					sell_money += sell.get(i).sell;
				}

				this.repaint();
			}
			if (actionCmd.equals("확인")) {
				for (int i = 0; i < sell.size(); i++) {
					this.sellSite(this.site_owner, sell.get(i));
				}
				nowplayer.money -= this.total_money;
				site_owner.money += this.total_money;
				dispose();
				gamePage.repaint();
			}
		}

		public void paint(Graphics g) {
			super.paint(g);

			money_label.setText("매각 금액: " + sell_money);
		}

	}

	public class PasanIm extends JFrame implements ActionListener {

		JLabel str, str1, str2;
		JButton confirm, end;
		JPanel p1, p2;

		PasanIm(Player loser) {
			this.setTitle("파산");
			this.setSize(700, 480);
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			int loser_player_num = loser.player_num;
			for(int i = 0; i < siteList.size(); i++) {  //  모든 site에서 파산한 플레이어 안 보이게 한다.
				siteList.get(i).player_OK.set(loser_player_num - 1, false);
			}
			
			playerList.remove(loser);  //  playerList에서 파산한 플레이어 제거
			
			p1 = new JPanel();
			p1.setBounds(0, 0, 700, 480);
			p1.setLayout(null);
			p2 = new JPanel();
			p2.setBounds(0, 0, 700, 480);
			p2.setLayout(null);
			
			
			str = new JLabel("Player " + loser.player_num + "이 파산했습니다.");
			str.setFont(new Font("Monospaced", Font.BOLD | Font.PLAIN, 30));
			str.setBounds(150, 150, 600, 100);
			p1.add(str);
			
			confirm = new JButton("확인");
			confirm.setBounds(285, 350, 100, 50);
			confirm.setFont(new Font("Monospaced", Font.BOLD, 30));
			confirm.addActionListener(this);
			p1.add(confirm);
			
			end = new JButton("종료");
			end.setBounds(285, 350, 100, 50);
			end.setFont(new Font("Monospaced", Font.BOLD, 30));
			end.addActionListener(this);
			p2.add(end);
			
			p1.setVisible(true);
			p1.setBackground(Color.WHITE);
			p2.setVisible(false);
			p2.setBackground(Color.WHITE);
			
			if (playerList.size() != 1) {  //  아직 게임이 끝나지 않음
				
			} else {  //  게임이 끝남

				str1 = new JLabel("PLayer " + playerList.get(0).player_num + "이  승리하였습니다.");
				str1.setFont(new Font("Monospaced", Font.BOLD, 30));
				str1.setBounds(120, 170, 600, 50);
				str2 = new JLabel("!!!축하합니다!!!");
				str2.setFont(new Font("Monospaced", Font.BOLD, 30));
				str2.setBounds(220, 250, 450, 50);

				confirm = new JButton("종료");
				confirm.setBounds(285, 350, 100, 50);
				confirm.setFont(new Font("Monospaced", Font.BOLD, 30));
				confirm.addActionListener(this);
				
				p2.add(str1);
				p2.add(str2);
			}
			
			this.add(p1);
			this.add(p2);
			
			this.getContentPane().setBackground(Color.WHITE);

			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String actionCmd = e.getActionCommand();
			
			if(actionCmd.equals("확인")) {
				if(playerList.size() == 1) {
					p1.setVisible(false);
					p2.setVisible(true);
				}
				else {  //  아직 게임이 다 끝난게 아닐 때
					dispose();
				}
			} 
			if(actionCmd.equals("종료")) {
				System.exit(0);
			}
			
		}

	}

	public static void main(String[] args) { // 메인 메소드
		// TODO Auto-generated method stub
		DaeguMarbleDemo dm = new DaeguMarbleDemo();
	}

}