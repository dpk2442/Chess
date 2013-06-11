package main;

import java.awt.*;
import java.util.*;
import main.ChessBoard.BoardSpace;

public class ChessPiece {

	final static int PAWN = 1;
	final static int ROOK = 2;
	final static int KNIGHT = 3;
	final static int BISHOP = 4;
	final static int QUEEN = 5;
	final static int KING = 6;

	final static int WHITE = 7;
	final static int BLACK = 8;

	private int pieceType;
	private int pieceColor;
	private boolean pieceSelected;

	public ChessPiece(int pieceType, int pieceColor) {
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
		this.pieceSelected = false;
	}

	public void draw(Graphics g, String location, Hashtable<String,BoardSpace> boardSpaces) {

		Point topLeft = boardSpaces.get(location).getPoint();
		int size = boardSpaces.get(location).getSize();

		//Border
		if (pieceSelected) g.setColor(Color.green);
		else if (pieceColor == ChessPiece.BLACK) g.setColor(Color.white);
		else  g.setColor(Color.black);
		g.drawRect(topLeft.x+5, topLeft.y+5, size-10, size-10);
		//Fill
		if (pieceColor == ChessPiece.BLACK) g.setColor(Color.black);
		else  g.setColor(Color.white);
		g.fillRect(topLeft.x+6, topLeft.y+6, size-12, size-12);
		//Text
		if (pieceColor == ChessPiece.BLACK) g.setColor(Color.white);
		else  g.setColor(Color.black);
		String letter = "";
		if (pieceType == ChessPiece.PAWN) letter = "P";
		else if (pieceType == ChessPiece.ROOK) letter = "R";
		else if (pieceType == ChessPiece.KNIGHT) letter = "K";
		else if (pieceType == ChessPiece.BISHOP) letter = "B";
		else if (pieceType == ChessPiece.QUEEN) letter = "Q";
		else if (pieceType == ChessPiece.KING) letter = "Ki";
		g.drawString(letter, topLeft.x + 8, topLeft.y + 28);

	}

	public boolean isLegalMove(String myLocation, String moveToSpace, Hashtable<String,ChessPiece> boardPieces) {

		int myCol = getColNumFromLetter(myLocation.substring(0, 1));
		int moveCol = getColNumFromLetter(moveToSpace.substring(0, 1));
		int myRow = Integer.parseInt(myLocation.substring(1));
		int moveRow = Integer.parseInt(moveToSpace.substring(1));
		int rowDiff = myRow - moveRow;
		
		String firstPieceFound = "";
		boolean firstPieceFoundYet = false;
		
		if (this.pieceColor == ChessPiece.BLACK) { // Black pieces
			
			if (this.pieceType == ChessPiece.PAWN) {
				
				if (myCol == moveCol) {
					if (rowDiff >= 0) {
						if (!(boardPieces.get(moveToSpace) instanceof ChessPiece)) {
							if (myRow == 7 && rowDiff <= 2) {
								return true;
							} else if ((myRow - moveRow) <= 1) {
								return true;
							}
						}
					}
				} else if (myCol == moveCol+1 || myCol == moveCol-1) {
					if (myRow == moveRow+1) {
						if (boardPieces.get(moveToSpace) instanceof ChessPiece) {
							return true;
						}
					}
				}
			} else if (this.pieceType == ChessPiece.ROOK) {
				if (myCol == moveCol) {
					if (myRow > moveRow) { // Moving Down
						for (int i = myRow; i > moveRow; i--) {
							int checkRow = i - 1;
							if (boardPieces.get(getColLetterFromNum(myCol) + checkRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(myCol) + checkRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					} else { // Moving Up
						for (int i = myRow; i < moveRow; i++) {
							int checkRow = i + 1;
							if (boardPieces.get(getColLetterFromNum(myCol) + checkRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(myCol) + checkRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					}
					return true;
				} else if (myRow == moveRow) {
					if (myCol > moveCol) { // Moving left
						for (int i = myCol; i > moveCol; i--) {
							int checkCol = i - 1;
							if (boardPieces.get(getColLetterFromNum(checkCol) + myRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(checkCol) + myRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					} else { // Moving right
						for (int i = myCol; i < moveCol; i++) {
							int checkCol = i + 1;
							if (boardPieces.get(getColLetterFromNum(checkCol) + myRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(checkCol) + myRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					}
					
					return true;
				}
			} else if (this.pieceType == ChessPiece.BISHOP) {
				
				if (myCol != moveCol && myRow != moveRow) {
					int y = myRow - moveRow;
					int x = myCol - moveCol;
					int absSlope = Math.abs(y)/Math.abs(x);
					if (absSlope == 1) {
						if (x > 0 && y < 0) { // Up left
							for (int row = myRow + 1, counter = 1; row < moveRow + 1; row++, counter++) {
								int col = myCol - counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x > 0 && y > 0) { // Down left
							for (int row = myRow - 1, counter = 1; row > moveRow - 1; row--, counter++) {
								int col = myCol - counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x < 0 && y < 0) { // Up right
							for (int row = myRow + 1, counter = 1; row < moveRow + 1; row++, counter++) {
								int col = myCol + counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x < 0 && y > 0) { // Down right
							for (int row = myRow - 1, counter = 1; row > moveRow - 1; row--, counter++) {
								int col = myCol + counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						}
						return true;
					}
				}
				
			}
			
		} else if (this.pieceColor == ChessPiece.WHITE) { // White pieces
			
			if (this.pieceType == ChessPiece.PAWN) {
				
				if (myCol == moveCol) {
					rowDiff = Math.abs(rowDiff);
					if (rowDiff >= 0) {
						if (!(boardPieces.get(moveToSpace) instanceof ChessPiece)) {
							if (myRow == 2 && rowDiff <= 2) {
								return true;
							} else if ((moveRow - myRow) <= 1) {
								return true;
							}
						}
					}
				} else if (myCol == moveCol+1 || myCol == moveCol-1) {
					if (myRow == moveRow-1) {
						if (boardPieces.get(moveToSpace) instanceof ChessPiece) {
							return true;
						}
					}
				}
			} else if (this.pieceType == ChessPiece.ROOK) {
				if (myCol == moveCol) {
					if (myRow > moveRow) { // Moving Down
						for (int i = myRow; i > moveRow; i--) {
							int checkRow = i - 1;
							if (boardPieces.get(getColLetterFromNum(myCol) + checkRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(myCol) + checkRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					} else { // Moving Up
						for (int i = myRow; i < moveRow; i++) {
							int checkRow = i + 1;
							if (boardPieces.get(getColLetterFromNum(myCol) + checkRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(myCol) + checkRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					}
					return true;
				} else if (myRow == moveRow) {
					if (myCol > moveCol) { // Moving left
						for (int i = myCol; i > moveCol; i--) {
							int checkCol = i - 1;
							if (boardPieces.get(getColLetterFromNum(checkCol) + myRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(checkCol) + myRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					} else { // Moving right
						for (int i = myCol; i < moveCol; i++) {
							int checkCol = i + 1;
							if (boardPieces.get(getColLetterFromNum(checkCol) + myRow) instanceof ChessPiece) {
								if (!firstPieceFoundYet) {
									firstPieceFound = getColLetterFromNum(checkCol) + myRow;
								}
								if (!firstPieceFound.equals(moveToSpace))
									return false;
							}
						}
					}
					
					return true;
				}
			} else if (this.pieceType == ChessPiece.BISHOP) {
				
				if (myCol != moveCol && myRow != moveRow) {
					int y = myRow - moveRow;
					int x = myCol - moveCol;
					int absSlope = Math.abs(y)/Math.abs(x);
					if (absSlope == 1) {
						if (x > 0 && y < 0) { // Up left
							for (int row = myRow + 1, counter = 1; row < moveRow + 1; row++, counter++) {
								int col = myCol - counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x > 0 && y > 0) { // Down left
							for (int row = myRow - 1, counter = 1; row > moveRow - 1; row--, counter++) {
								int col = myCol - counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x < 0 && y < 0) { // Up right
							for (int row = myRow + 1, counter = 1; row < moveRow + 1; row++, counter++) {
								int col = myCol + counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						} else if (x < 0 && y > 0) { // Down right
							for (int row = myRow - 1, counter = 1; row > moveRow - 1; row--, counter++) {
								int col = myCol + counter;
								if (boardPieces.get(getColLetterFromNum(col) + row) instanceof ChessPiece) {
									if (!firstPieceFoundYet) {
										firstPieceFound = getColLetterFromNum(col) + row;
									}
									if (!firstPieceFound.endsWith(moveToSpace))
										return false;
								}
							}
						}
						return true;
					}
				}
				
			}
			
		} // End black pieces

		return false;
	}
	
	public int getColNumFromLetter(String letter) {
		if (letter.equals("A")) {
			return 1;
		} else if (letter.equals("B")) {
			return 2;
		} else if (letter.equals("C")) {
			return 3;
		} else if (letter.equals("D")) {
			return 4;
		} else if (letter.equals("E")) {
			return 5;
		} else if (letter.equals("F")) {
			return 6;
		} else if (letter.equals("G")) {
			return 7;
		} else if (letter.equals("H")) {
			return 8;
		}
		return 0;
	}
	public String getColLetterFromNum(int col) {
		switch (col) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";
		case 5:
			return "E";
		case 6:
			return "F";
		case 7:
			return "G";
		case 8:
			return "H";
			
		}
		return null;
	}

	public void setSelected(boolean selected) {
		this.pieceSelected = selected;
	}

	public String getPiece() {
		String piece = "";
		if (pieceType == ChessPiece.PAWN) piece = "pawn";
		if (pieceType == ChessPiece.ROOK) piece = "rook";
		if (pieceType == ChessPiece.KNIGHT) piece = "knight";
		if (pieceType == ChessPiece.BISHOP) piece = "bishop";
		if (pieceType == ChessPiece.QUEEN) piece = "queen";
		if (pieceType == ChessPiece.KING) piece = "king";
		return piece;
	}

	public String getColor() {
		String color = "";
		if (pieceColor == ChessPiece.WHITE) color = "white";
		if (pieceColor == ChessPiece.BLACK) color = "black";
		return color;
	}

	public void print() {
		System.out.println("A " + getColor() + " " + getPiece() + ".");
	}

}
