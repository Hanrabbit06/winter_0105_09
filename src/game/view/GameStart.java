package game.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStart extends JFrame {
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 300;
	public GameStart() {
		JButton btnStart = new JButton("게임시작");
		add(btnStart);
		btnStart.addActionListener(btnL);
		setTitle("**운석 피하기**");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(10, 10, FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
	}
	
	ActionListener btnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new GameView(GameStart.this);
		}
	};
}