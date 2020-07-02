package game;
import java.util.TreeMap;
import java.util.Vector;

import Board.GameBoard;
import Board.GamePiece;

public abstract class Game {
	public GameBoard gameBoard;
	public int size;
	
	Game(int size) {
		this.size = size;
		gameBoard = new GameBoard(size, this, setInitialPieces());
		GamePiece.pieceMovements = setPieceMovements();	
		GamePiece.tags = setTags();
	}
	
	
	abstract TreeMap<Integer, Vector<int[]>> setPieceMovements();
	abstract Vector<int[]> setInitialPieces();
	abstract String[] setTags();
	
	//abstract int[] getScores();
	
	GamePiece getPieceIn(int[] indexes) {
		return gameBoard.getPieceIn(indexes);
	}
	
	public GamePiece getPieceIn(int x, int y) {
		return gameBoard.getPieceIn(x, y);
	} 
	
	
}
