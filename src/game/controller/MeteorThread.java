package game.controller;

import java.util.Random;

import javax.swing.JLabel;

import game.view.GameView;
import game.vo.Meteor;

public class MeteorThread extends Thread{
	JLabel meteorLbl;
	Meteor meteor;
	int level;
	public MeteorThread(JLabel meteorLbl, Meteor meteor, int level) {
		this.meteorLbl = meteorLbl;
		this.meteor = meteor;
		this.level = level;
	}
	
	
	@Override
	public void run() {
		while (true) {
			if(level == 2) 
				break;
				
			Random random = new Random();
			if( meteorLbl.getY() <= GameView.FRAME_HEIGHT)
				meteorLbl.setLocation(meteorLbl.getX(), meteorLbl.getY() + 10);
			else {
				meteorLbl.setLocation(meteorLbl.getX(), random.nextInt(70));
			}
			//changeScore();
			try {
				sleep(20 * random.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
