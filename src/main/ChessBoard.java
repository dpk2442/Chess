package main;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class ChessBoard extends Canvas implements MouseListener {
	
	Hashtable<String,BoardSpace> boardSpaces;
	
	int boardX;
	int boardY;
	int boardSize;
	
	GameController game;

	public ChessBoard() {
		super();
		
		boardSpaces = new Hashtable<String,BoardSpace>();

		boardX = 30;
		boardY = 50;
		boardSize = 50;
		
		game = new GameController();
		
		addMouseListener(this);
		
		setSize(200, 200);
		setBackground(Color.gray);
	}

	public void paint(Graphics g) {
		
		Graphics offgc;
		Image offscreen = null;
		Dimension d = getSize();

		// create the offscreen buffer and associated Graphics
		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();
		
		offgc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		offgc.setColor(Color.white);
		offgc.drawString("This is Chess", 5, 22);
		
		int stringX = boardX + 5;
		int stringY = boardY + 15;
		
		offgc.drawString("A", stringX, boardY - 5);
		offgc.drawString("B", stringX + 50, boardY - 5);
		offgc.drawString("C", stringX + 50*2, boardY - 5);
		offgc.drawString("D", stringX + 50*3, boardY - 5);
		offgc.drawString("E", stringX + 50*4, boardY - 5);
		offgc.drawString("F", stringX + 50*5, boardY - 5);
		offgc.drawString("G", stringX + 50*6, boardY - 5);
		offgc.drawString("H", stringX + 50*7, boardY - 5);
		
		offgc.drawString("8", stringX - 25, stringY);
		offgc.drawString("7", stringX - 25, stringY + 50*1);
		offgc.drawString("6", stringX - 25, stringY + 50*2);
		offgc.drawString("5", stringX - 25, stringY + 50*3);
		offgc.drawString("4", stringX - 25, stringY + 50*4);
		offgc.drawString("3", stringX - 25, stringY + 50*5);
		offgc.drawString("2", stringX - 25, stringY + 50*6);
		offgc.drawString("1", stringX - 25, stringY + 50*7);
		
		drawBoard(offgc, boardX, boardY, boardSize);
		
		// transfer offscreen to window
		g.drawImage(offscreen, 0, 0, this);

	}
	
	private void drawBoard(Graphics g, int topCornerX, int topCornerY, int size) {
		
		int[] rows = new int[] {topCornerX, topCornerX+(1 * size), topCornerX+(2 * size), topCornerX+(3 * size), topCornerX+(4 * size), topCornerX+(5 * size), topCornerX+(6 * size), topCornerX+(7 * size)};
		int[] colsOneOver = new int[] {topCornerY+(1 * size), topCornerY+(3 * size), topCornerY+(5 * size), topCornerY+(7 * size)};
		int[] colsNormal = new int[] {topCornerY, topCornerY+(2 * size), topCornerY+(4 * size), topCornerY+(6 * size)};
		
		drawSquares(Color.white, rows, colsNormal, colsOneOver, boardSpaces, g);
		spacesRecorded = false;
		drawSquares(Color.black, rows, colsOneOver, colsNormal, boardSpaces, g);
		
		game.drawPieces(g, boardSpaces);
	}


	boolean spacesRecorded = false;

	private void drawSquares(Color color, int[] rowCoords, int[] colEvenCoords, int[] colOddCoords, Hashtable<String,BoardSpace> spaces, Graphics g) {

		g.setColor(color);
		
		int[] rows = rowCoords;
		int[] colsEven = colEvenCoords;
		int[] colsOdd = colOddCoords;
		int spaceSize = boardSize;

		for (int x = 0; x < rows.length; x++) {
			if (Math.IEEEremainder(x, 2) == 0)
				for (int y = 0; y < colsEven.length; y++) {
					g.fillRect(rows[x], colsEven[y], spaceSize, spaceSize);
					if (!spacesRecorded) {
						String column = getColumn(x);
						String row = getRow(colsEven[y]);
						spaces.put(column + row, new BoardSpace(new Point(rows[x], colsEven[y]), spaceSize));
					}
				}
			else
				for (int y = 0; y < colsOdd.length; y++) {
					g.fillRect(rows[x], colsOdd[y], spaceSize, spaceSize);
					if (!spacesRecorded) {
						String column = getColumn(x);
						String row = getRow(colsOdd[y]);
						spaces.put(column + row, new BoardSpace(new Point(rows[x], colsOdd[y]), spaceSize));
					}
				}
		}
		
		spacesRecorded = true;
		
	}
	
	public String getColumn(int x) {
		String row = "";
		switch (x) {
		case 0:
			row = "A";
			break;
		case 1:
			row = "B";
			break;
		case 2:
			row = "C";
			break;
		case 3:
			row = "D";
			break;
		case 4:
			row = "E";
			break;
		case 5:
			row = "F";
			break;
		case 6:
			row = "G";
			break;
		case 7:
			row = "H";
			break;
		}
		return row;
	}
	
	public String getRow(int yCoord) {
		int colCalc = (yCoord - boardX)/boardSize + 1;
		return ""+Math.abs(colCalc - 9);
	}
	
	class BoardSpace {
		
		private Point tl;
		private int size;
		
		public BoardSpace(Point topLeft, int size) {
			tl = topLeft;
			this.size = size;
		}
		
		public Point getPoint() {
			return tl;
		}
		
		public int getX() {
			return tl.x;
		}
		public int getY() {
			return tl.y;
		}
		public int getSize() {
			return size;
		}
		
		public void print() {
			System.out.println("TL: ("+getX()+", "+getY()+") size: "+getSize());
		}
	}

	int selCounter = 0;
	String spaceSelected = "";
	
	public void mouseClicked(MouseEvent arg0) {
		
		String spaceClicked = "No Space";
		
		Enumeration<String> e = boardSpaces.keys();
		while(e.hasMoreElements()) {
			String key = e.nextElement();
			BoardSpace current = boardSpaces.get(key);
			int x = current.getX();
			int y = current.getY();
			int size = current.getSize();
			
			int clickX = arg0.getX();
			int clickY = arg0.getY();
			
			if (clickX >= x && clickX <= x + size) {
				if (clickY >= y && clickY <= y + size) {
					spaceClicked = key;
					break;
				}
			}
		}
		
//		System.out.println("You clicked: " + spaceClicked);
		
		if (selCounter == 0) {
			if (!spaceClicked.equals("No Space")) {
				if (game.isPieceOnSpace(spaceClicked)) {
					spaceSelected = spaceClicked;
					game.setSelectedPiece(spaceClicked, true);
					repaint();
					selCounter++;
				}
			}
		} else if (selCounter == 1) {
			if (!spaceClicked.equals("No Space")) {
				game.setSelectedPiece(spaceSelected, false);
				game.movePiece(spaceSelected, spaceClicked);
				repaint();
				selCounter = 0;
				spaceSelected = "";
			}
		}
		
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
