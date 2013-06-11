package main;

import java.awt.Toolkit;

import javax.swing.*;

public class ChessWindow extends JFrame {

	public ChessWindow(String title) {
		super(title);
		
		ChessBoard board = new ChessBoard();
		
		add(board);
		board.repaint();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 250, Toolkit.getDefaultToolkit().getScreenSize().height/2 - 250);
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new ChessWindow("Chess");
	}
}