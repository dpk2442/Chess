package main;

import java.util.*;
import java.awt.*;
import main.ChessBoard.BoardSpace;

public class GameController {
	
	Hashtable<String,ChessPiece> boardPieces;
	
	public GameController() {
		boardPieces = new Hashtable<String,ChessPiece>();
		
		boardPieces.put("A8", new ChessPiece(ChessPiece.ROOK, ChessPiece.BLACK));
		boardPieces.put("H8", new ChessPiece(ChessPiece.ROOK, ChessPiece.BLACK));
		boardPieces.put("B8", new ChessPiece(ChessPiece.KNIGHT, ChessPiece.BLACK));
		boardPieces.put("G8", new ChessPiece(ChessPiece.KNIGHT, ChessPiece.BLACK));
		boardPieces.put("C8", new ChessPiece(ChessPiece.BISHOP, ChessPiece.BLACK));
		boardPieces.put("F8", new ChessPiece(ChessPiece.BISHOP, ChessPiece.BLACK));
		boardPieces.put("D8", new ChessPiece(ChessPiece.QUEEN, ChessPiece.BLACK));
		boardPieces.put("E8", new ChessPiece(ChessPiece.KING, ChessPiece.BLACK));
		
		boardPieces.put("A7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("B7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("C7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("D7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("E7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("F7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("G7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));
		boardPieces.put("H7", new ChessPiece(ChessPiece.PAWN, ChessPiece.BLACK));

		boardPieces.put("A1", new ChessPiece(ChessPiece.ROOK, ChessPiece.WHITE));
		boardPieces.put("H1", new ChessPiece(ChessPiece.ROOK, ChessPiece.WHITE));
		boardPieces.put("B1", new ChessPiece(ChessPiece.KNIGHT, ChessPiece.WHITE));
		boardPieces.put("G1", new ChessPiece(ChessPiece.KNIGHT, ChessPiece.WHITE));
		boardPieces.put("C1", new ChessPiece(ChessPiece.BISHOP, ChessPiece.WHITE));
		boardPieces.put("F1", new ChessPiece(ChessPiece.BISHOP, ChessPiece.WHITE));
		boardPieces.put("D1", new ChessPiece(ChessPiece.QUEEN, ChessPiece.WHITE));
		boardPieces.put("E1", new ChessPiece(ChessPiece.KING, ChessPiece.WHITE));
		
		boardPieces.put("A2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("B2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("C2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("D2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("E2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("F2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("G2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
		boardPieces.put("H2", new ChessPiece(ChessPiece.PAWN, ChessPiece.WHITE));
	}
	
	public void movePiece(String piece, String moveto) {
		if (boardPieces.get(piece) instanceof ChessPiece && !piece.equals(moveto)) {
			ChessPiece moveme = boardPieces.get(piece);
			ChessPiece moveon = boardPieces.get(moveto);
			boolean sameColor = false;
			if (moveon instanceof ChessPiece) {
				if (moveon.getColor().equals(moveme.getColor())) {
					sameColor = true;
				}
			}
			if (!sameColor) {
				if (boardPieces.get(piece).isLegalMove(piece, moveto, boardPieces)) {
					if (boardPieces.get(moveto) instanceof ChessPiece)
						boardPieces.remove(moveto);
					boardPieces.remove(piece);
					boardPieces.put(moveto, moveme);
				}
			}

		}
	}
	
	public boolean isPieceOnSpace(String space) {
		if (boardPieces.get(space) instanceof ChessPiece) return true;
		return false;
	}
	
	public void setSelectedPiece(String piece, boolean selected) {
		boardPieces.get(piece).setSelected(selected);
	}
	
	public void drawPieces(Graphics g, Hashtable<String,BoardSpace> boardSpaces) {
		drawColumn("A", g, boardSpaces);
		drawColumn("B", g, boardSpaces);
		drawColumn("C", g, boardSpaces);
		drawColumn("D", g, boardSpaces);
		drawColumn("E", g, boardSpaces);
		drawColumn("F", g, boardSpaces);
		drawColumn("G", g, boardSpaces);
		drawColumn("H", g, boardSpaces);
	}
	
	private void drawColumn(String column, Graphics g, Hashtable<String,BoardSpace> boardSpaces) {
		
		for (int i = 0; i < 8; i++) {
			int row = i + 1;
			if (boardPieces.get(column+row) instanceof ChessPiece)
				boardPieces.get(column+row).draw(g, column+row, boardSpaces);
		}
		
	}

}
