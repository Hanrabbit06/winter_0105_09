package game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import game.vo.Energy;
import game.vo.Meteor;

public class GameView extends JFrame {
	
	
	public static final int FRAME_HEIGHT = 600;
	public static final int FRAME_WIDTH = 830;
//	20개의 우박 객체 참조값이 저장되는 배열
	Meteor[] meteor = new Meteor[16]; 
	JLabel[] lblMeteor = new JLabel[meteor.length];
	Energy[] energy = new Energy[meteor.length / 2];
	JLabel[] lblEnergy = new JLabel[energy.length];
	JLabel charLbl = new JLabel(new ImageIcon("images/astronaut.png"));
	JLabel scoreLbl = new JLabel("점수: -----점");
	String[] comboCharStr = {"1번", "2번"};
	JComboBox<String> comboChar = new JComboBox<String>(comboCharStr);
	String[] comboMeteorStr = {"장에물1", "장에물2", "장에물3", "장에물4","장애물5"};
	JComboBox<String> comboMeteor = new JComboBox<String>(comboMeteorStr);
	String[] comboEnergyStr = {"에너지1", "에너지2", "에너지3", "에너지4","에너지5"};
	JComboBox<String> comboEnergy = new JComboBox<String>(comboEnergyStr);
	int score = 500;
	MeteorThread mThread = null;
	EnergyThread eThread = null;
	Random random = new Random();
	int hailX = 70, level = 1;
	JLabel lblLevel = new JLabel("Level 1");
	
	public GameView(GameStart startFrame) {
		startFrame.dispose();
		setLayout(null);
		getContentPane().setBackground(Color.black);
		Random random = new Random();
		MeteorThread mThread = null;
		EnergyThread eThread = null;
		scoreLbl.setBounds(1100, 20, 100, 30);
		comboChar.setBounds(1030, 20, 60, 30);
		comboChar.addItemListener(comboL);
		comboMeteor.setBounds(940, 20, 80, 30);
		comboMeteor.addItemListener(comboL);
		comboEnergy.setBounds(940, 60, 80, 30);
		comboEnergy.addItemListener(comboL);
		scoreLbl.setOpaque(true); 
		scoreLbl.setBackground(Color.white);
		
		add(scoreLbl);
		add(comboChar);
		add(comboMeteor);
		add(comboEnergy);
		changeMeteorCount();
		
		charLbl.setBounds(550, 450, 60, 70);
		add(charLbl);
		addKeyListener(keyL);
		
//		20개의 우박 객체를 생성해서 배열에 저장
		for (int i = 0; i < meteor.length; i++) {
			meteor[i] = new Meteor();
			meteor[i].setX(i * 50);
			meteor[i].setY(i * random.nextInt(50));
			meteor[i].setW(60);
			meteor[i].setH(60);
			meteor[i].setImgName("images/meteor.png");
			meteor[i].setPoint(10);
			lblMeteor[i] = new JLabel(new ImageIcon(meteor[i].getImgName()));
			lblMeteor[i].setBounds(meteor[i].getX(), meteor[i].getY(), meteor[i].getW(), meteor[i].getH());
			add(lblMeteor[i]);
			mThread = new MeteorThread(lblMeteor[i], meteor[i]);
			mThread.start();
		}
		
//		10개의 다이아몬드 객체를 생성해서 배열에 저장
		
		for (int i = 0; i < energy.length; i++) {
			energy[i] = new Energy();
			energy[i].setX(i * 50 + random.nextInt(500));
			energy[i].setY(i * random.nextInt(30));
			energy[i].setW(60);
			energy[i].setH(60);
			energy[i].setImgName("images/energy.png");
			energy[i].setPoint(20);
			lblEnergy[i] = new JLabel(new ImageIcon(energy[i].getImgName()));
			lblEnergy[i].setBounds(energy[i].getX(), energy[i].getY(), energy[i].getW(), energy[i].getH());
			add(lblEnergy[i]);
			eThread = new EnergyThread(lblEnergy[i], energy[i]);
			eThread.start();
		}
		
		charLbl.setBounds(470, 500, 50, 50);
		add(charLbl);
		addKeyListener(keyL);
		
		setTitle("**운석 피하기**");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(10, 10, 1260, 600);
		setVisible(true);
//		setResizable(false);
		setFocusable(true);
		requestFocus();
	}
	
	public void changeScore() {
		for (int i = 0; i < lblMeteor.length; i++) {				
			if(charLbl.getX() >= lblMeteor[i].getX() && charLbl.getX() <= lblMeteor[i].getX()+lblMeteor[i].getWidth()) {
				if(charLbl.getY() >= lblMeteor[i].getY() && charLbl.getY() <= lblMeteor[i].getY()+lblMeteor[i].getHeight()) {
					if(score > meteor[i].getPoint()) {
						score -= meteor[i].getPoint();
						scoreLbl.setText("점수: "+score+"점");
					}
				}
			}
		}
		
		for (int i = 0; i < lblEnergy.length; i++) {				
			if(charLbl.getX() >= lblEnergy[i].getX() && charLbl.getX() <= lblEnergy[i].getX()+lblEnergy[i].getWidth()) {
				if(charLbl.getY() >= lblEnergy[i].getY() && charLbl.getY() <= lblEnergy[i].getY()+lblEnergy[i].getHeight()) {
					if(score <= 980) {
						score += energy[i].getPoint();
						scoreLbl.setText("점수: "+score+"점");
					}
				}
			}
		}
		if(score >= 1000 && score < 1300) {
			if(level != 2) {
				int meteorCount = 20;
				hailX = 60;
				level = 2;
				JOptionPane.showMessageDialog(this, "Level "+level+"가 시작됩니다.");
				score = 500;
				scoreLbl.setText("점수: "+score+"점");
				changeMeteorCount();
			}	
		}else if(score >= 1300) {
			JOptionPane.showMessageDialog(this, "~~ You Win ~~");
			dispose();
		}
		
		scoreLbl.setText("점수: "+score+"점");
	}
	
	private void changeMeteorCount() {
		// TODO Auto-generated method stub
		
	}

	ItemListener comboL = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			ImageIcon icon = null;
			String imgName = null;
			if(e.getSource() == comboChar) {
			switch (comboChar.getSelectedIndex()) {
			case 0:
				imgName = "astronaut";
				break;
			case 1:
				imgName = "character1";
				break;
			}
			icon = new ImageIcon("images/" + imgName + ".png");
			charLbl.setIcon(icon);
			
		}else if(e.getSource() == comboMeteor) {
			switch (comboMeteor.getSelectedIndex()) {
			case 0:
				imgName = "meteor1.png";
				break;
			case 1:
				imgName = "meteor2.png";
				break;
			case 2:
				imgName = "meteor3.png";
				break;
			case 3:
				imgName = "meteor4.png";
				break;
			case 4:
				imgName = "meteor.png";
				break;
			}
			icon = new ImageIcon("images/" + imgName);
			for (int j = 0; j < lblMeteor.length; j++) {
				lblMeteor[j].setIcon(icon);
			}
			
		}else if(e.getSource() == comboEnergy) {
			switch (comboEnergy.getSelectedIndex()) {
			case 0:
				imgName = "energy.png";
				break;
			case 1:
				imgName = "energy1.gif";
				break;
			case 2:
				imgName = "energy2.gif";
				break;
			case 3:
				imgName = "energy3.gif";
				break;
			case 4:
				imgName = "energy4.gif";
				break;
			}
			icon = new ImageIcon("images/" + imgName);
			for (int j = 0; j < lblEnergy.length; j++) {
				lblEnergy[j].setIcon(icon);
			}
			
		}
		GameView.this.setFocusable(true);
		GameView.this.requestFocus();
		}
	};
	
	KeyAdapter keyL = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: 
				if(charLbl.getY() > 0)
					charLbl.setLocation(charLbl.getX(), charLbl.getY()-20);
				break;
			case KeyEvent.VK_DOWN: 
				if(charLbl.getY() < FRAME_HEIGHT-charLbl.getHeight()*2)
					charLbl.setLocation(charLbl.getX(), charLbl.getY()+20);
				break;
			case KeyEvent.VK_LEFT: 
				if(charLbl.getX() > 0)
					charLbl.setLocation(charLbl.getX()-20, charLbl.getY());
				break;
			case KeyEvent.VK_RIGHT: 
				if(charLbl.getX() < FRAME_WIDTH-charLbl.getWidth())
					charLbl.setLocation(charLbl.getX()+20, charLbl.getY());
				break;
			}			
			changeScore();
		}
	};
	
	public class MeteorThread extends Thread{
		JLabel meteorLbl;
		Meteor meteor;
		
		public MeteorThread(JLabel meteorLbl, Meteor meteor) {
			this.meteorLbl = meteorLbl;
			this.meteor = meteor;
		}
		
		
		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if( meteorLbl.getY() <= 600)
					meteorLbl.setLocation(meteorLbl.getX(), meteorLbl.getY() + 10);
				else {
					meteorLbl.setLocation(meteorLbl.getX(), random.nextInt(70));
				}
					
				
				try {
					sleep(20 * random.nextInt(50));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	public class EnergyThread extends Thread{
		JLabel energydLbl;
		Energy energy;
		
		public EnergyThread(JLabel energydLbl, Energy energy) {
			this.energydLbl = energydLbl;
			this.energy = energy;
		}
		
		
		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if( energydLbl.getY() <= 600)
					energydLbl.setLocation(energydLbl.getX(), energydLbl.getY() + 15);
				else {
					energydLbl.setLocation(energydLbl.getX(), random.nextInt(30));
				}
					
				
				try {
					sleep(10 * random.nextInt(40));
				} catch (InterruptedException e) {
	
				}
			}

		}
	}
		
	
}