import java.awt.*;
import javax.swing.*;
import java.util.*;

class Site extends JPanel{
	String name;
	int site_num;  //  Site ���� ������ȣ
	
	ArrayList<Boolean> player_OK; //  �÷��̾ ���� �� ���� �ִ� �� ����
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSite_num() {
		return site_num;
	}

	public void setSite_num(int site_num) {
		this.site_num = site_num;
	}
	
	public Site() {
		this.name = "";
		this.site_num = 0;
		
		player_OK = new ArrayList<Boolean>();
		player_OK.add(false);
		player_OK.add(false);
		player_OK.add(false);
		player_OK.add(false);
	}
	
	public Site(String name, int site_num) {
		this.name = name;
		this.site_num = site_num;
		
		player_OK.add(false);
		player_OK.add(false);
		player_OK.add(false);
		player_OK.add(false);
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(120, 0, 150, 0);//����
	    g.drawLine(150, 0, 150, 100);//����
	    g.drawLine(120, 100, 150, 100);//����
	    g.drawLine(120, 0, 120, 100);//����
	    
	    if(player_OK.get(0)) {  //  plyaer1�� ���� ���� ������ �ش� �÷��̾��� ���� �����ͼ� �� ���� ���� �׷��ִ´�.
	    	g.setColor(Color.pink);
	    	g.fillOval(125,4,20,20);
	    }
	    if(player_OK.get(1)) {  //  plyaer1�� ���� ���� ������ �ش� �÷��̾��� ���� �����ͼ� �� ���� ���� �׷��ִ´�.
	    	g.setColor(Color.green);
	    	g.fillOval(125,28,20,20);
	    }
	    if(player_OK.get(2)) {  //  plyaer1�� ���� ���� ������ �ش� �÷��̾��� ���� �����ͼ� �� ���� ���� �׷��ִ´�.
	    	g.setColor(Color.blue);
	    	g.fillOval(125,52,20,20);
	    }
	    if(player_OK.get(3)) {  //  plyaer1�� ���� ���� ������ �ش� �÷��̾��� ���� �����ͼ� �� ���� ���� �׷��ִ´�.
	    	g.setColor(Color.cyan);
	    	g.fillOval(125,76,20,20);
	    }
	}
	public void init(Player p1, Player p2, Player p3, Player p4,JPanel gamePage) {
		//  �÷��̾��� total_dice�� �����ϴ� site�� �ش� �÷��̾ �����Ѵٰ� ǥ��
		try{
			if(p1.total_dice==site_num) {
				player_OK.set(0, true);
			}
			if(p2.total_dice==site_num) {
				player_OK.set(1, true);
			}
			if(p3.total_dice==site_num) {
				player_OK.set(2, true);
			}
			if(p4.total_dice==site_num) {
				player_OK.set(3, true);
			}
		}
		catch(NullPointerException e) {
			e.getStackTrace();
		}
		gamePage.repaint();
	}

}

class NormalSite extends Site{
   /*ttang=1 house=2...*/
   Player owner;  //  "1", "2", "3", "4"
   Color owner_color;
   int ttang;
   int house;
   int villa;
   int hotel;
   int landmark;
   
   int ttangp;
   int housep;
   int villap;
   int hotelp;
   int landmarkp;
   
   int takeover=(ttangp+housep+villap+hotelp)*2;
   int sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
   int toll;											
   
   int festival=0;
   
   boolean ttang_OK;
   boolean house_OK;
   boolean villa_OK;
   boolean hotel_OK;
   boolean landmark_OK;
   
   int subway;  // 0, 1, 2, 3
   
   Image img;
   
   public Player getOwner() {
	   return owner;
   }
   
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getSite_num() {
      return site_num;
   }

   public void setSite_num(int site_num) {
      this.site_num = site_num;
   }

   public int getTtang() {
      return ttang;
   }

   public void setTtang(int ttang) {
      this.ttang = ttang;
   }

   public int getHouse() {
      return house;
   }

   public void setHouse(int house) {
      this.house = house;
   }

   public int getVilla() {
      return villa;
   }

   public void setVilla(int villa) {
      this.villa = villa;
   }

   public int getHotel() {
      return hotel;
   }

   public void setHotel(int hotel) {
      this.hotel = hotel;
   }

   public int getToll() {
      return toll;
   }

   public void purchase(int i) {  //  �����ϴ� �޼ҵ�
      if(i==1) {
         this.ttang_OK=true;
      }
      else if(i==2) {
         this.house_OK=true;
      }
      else if(i==3) {
         this.villa_OK=true;
      }
      else if(i==4) {
         this.hotel_OK=true;
      }
      else {
         this.landmark_OK=true;
      }
   }
   
   public void setToll() {  //  �� �޼ҵ带 �۵����Ѿ� ����ᰡ �����
      if(landmark_OK) {  //  ���帶ũ ������ ��
         if(site_num <= 7) {
            this.toll = 2*(ttangp + housep + villap + hotelp) * 3;
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
         } 
         else if(site_num <= 12 ) {
            this.toll = (int)(2*(ttangp + housep + villap + hotelp) * (3.5));
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
         } 
         else if(site_num <= 20) {
            this.toll = 2*(ttangp + housep + villap + hotelp) * 4;
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         } 
         else {
            this.toll = (int)(2*(ttangp + housep + villap + hotelp) * (4.5));
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         }
      } 
      else {  //  ���帶ũ �� ������ ��
         if(site_num <= 7) {
            this.toll = (ttangp + housep + villap + hotelp) * 3;
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         } 
         else if(site_num <= 12 ) {
            this.toll = (int)((ttangp + housep + villap + hotelp) * (3.5));
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         } 
         else if(site_num <= 20) {
            this.toll = (ttangp + housep + villap + hotelp) * 4;
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         } 
         else {
        	
            this.toll = (int)((ttangp + housep + villap + hotelp) * (4.5));
            sell=(int)((ttangp+housep+villap+hotelp+landmarkp)*0.8);  //  �ε��갡ġ, ���ÿ� �Ű� �ݾ�
            
         }
      }
   }

   public void setToll(int n) {  //  �� �޼ҵ带 �۵����Ѿ� ����ᰡ �����
	      if(landmark_OK) {  //  ���帶ũ ������ ��
	         if(site_num <= 7) {
	            this.toll = 2*(ttangp + housep + villap + hotelp) * 3*n;
	            
	         } 
	         else if(site_num <= 12 ) {
	            this.toll = (int)(2*(ttangp + housep + villap + hotelp) * (3.5))*n;
	            
	         } 
	         else if(site_num <= 20) {
	            this.toll = 2*(ttangp + housep + villap + hotelp) * 4*n;
	         } 
	         else {
	            this.toll = (int)(2*(ttangp + housep + villap + hotelp) * (4.5))*n;
	         }
	      } 
	      else {  //  ���帶ũ �� ������ ��
	         if(site_num <= 7) {
	            this.toll = (ttangp + housep + villap + hotelp) * 3*n;
	         } 
	         else if(site_num <= 12 ) {
	            this.toll = (int)((ttangp + housep + villap + hotelp) * (3.5))*n;
	         } 
	         else if(site_num <= 20) {
	            this.toll = (ttangp + housep + villap + hotelp) * 4*n;
	         } 
	         else {
	        	
	            this.toll = (int)((ttangp + housep + villap + hotelp) * (4.5))*n;
	         }
	      }
	   }

   public int getLand() {
      return landmark;
   }

   public void setLand(int land) {
      this.landmark = land;
   }

   
   public NormalSite(int x, int y, String name,int ttang,int house,int villa,int hotel,int land,int site_num, int subway, Image img) {
      this.setBounds(x, y, 150, 100);
      this.setLayout(null);
      this.setVisible(true);
      this.name = name;
      this.owner = null;
      
      this.ttang=ttang;
      this.house=house;
      this.villa=villa;
      this.hotel=hotel;
      this.landmark=land;
      
      this.ttangp=0;
      this.housep=0;
      this.villap=0;
      this.hotelp=0;
      this.landmarkp=0;
      
      this.site_num=site_num;
      
      this.ttang_OK=false;
      this.house_OK=false;
      this.villa_OK=false;
      this.hotel_OK=false;
      this.landmark_OK=false;
      
      this.subway = subway;
      
      this.img=img;
      
      this.setBackground(Color.WHITE);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      g.setColor(Color.black);
      g.drawString(name, 30, 22);
      g.drawLine(27, 0, 27, 40);
      
      g.setColor(Color.black);
      g.drawString("�����: " + toll, 10, 87);
      
      if(subway==1) {
    	  g.setColor(Color.RED);
    	  g.setFont(new Font("Monospaced", Font.BOLD, 20));
    	  g.drawString("1", 7, 24);
      }
      
      if(subway==2) {
    	  g.setColor(Color.GREEN);
    	  g.setFont(new Font("Monospaced", Font.BOLD, 20));
    	  g.drawString("2", 7, 24);
      }
      
      if(subway==3) {
    	  g.setColor(Color.ORANGE);
    	  g.setFont(new Font("Monospaced", Font.BOLD, 20));
    	  g.drawString("3", 7, 24);
      }
      
      if(house_OK==false) {
    	  g.setColor(Color.black);
    	  g.drawRect(0, 40, 40, 30);         //������ ������ �ǹ� ����
      }
      if(house_OK==true) {
    	  g.setColor(owner_color);
    	  g.fillRect(0, 40, 40, 30);
      }
      if(villa_OK==false) {
    	  g.setColor(Color.black);
    	  g.drawRect(40, 40, 40, 30);         //������ ������ �ǹ� ����
      }
      if(villa_OK==true) {
    	  g.setColor(owner_color);
    	  g.fillRect(40, 40, 40, 30);
      }
      if(hotel_OK==false) {
    	  g.setColor(Color.black);
    	  g.drawRect(80, 40, 40, 30);         //������ ������ �ǹ� ����
      }
      if(hotel_OK==true) {
    	  g.setColor(owner_color);
    	  g.fillRect(80, 40, 40, 30);
      }
      if(landmark_OK==true) {
    	  g.setColor(Color.RED);
    	  g.setFont(new Font("Monospaced", Font.BOLD, 20));
    	  g.drawString("LANDMARK", 14, 64);
    	  
      }
      
   }
   
}