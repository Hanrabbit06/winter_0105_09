package game.controller;

import java.util.Random;

import javax.swing.JLabel;

import game.view.GameView;
import game.vo.Energy;

public class EnergyThread extends Thread{
	JLabel energyLbl;
	Energy energy;
	
	public EnergyThread(JLabel energyLbl, Energy energy) {
		this.energyLbl = energyLbl;
		this.energy = energy;
	}
	
	
	@Override
	public void run() {
		while (true) {
			Random random = new Random();
			if( energyLbl.getY() <= GameView.FRAME_HEIGHT)
				energyLbl.setLocation(energyLbl.getX(), energyLbl.getY() + 10);
			else {
				energyLbl.setLocation(energyLbl.getX(), random.nextInt(70));
			}
				
			
			try {
				sleep(30 * random.nextInt(50));
			} catch (InterruptedException e) {

			}
		}

	}
}
