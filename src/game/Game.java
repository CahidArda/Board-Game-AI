package game;
import java.util.TreeMap;
import java.util.Vector;

import board.GameBoard;
import board.GamePiece;

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
	abstract Vector<GamePiece> setInitialPieces();
	abstract String[] setTags();
	abstract public int[] getScores();	
	
}
