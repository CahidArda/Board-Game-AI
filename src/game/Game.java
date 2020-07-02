package game;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import board.GameBoard;
import board.GamePiece;
import board.Move;

public abstract class Game {
	public GameBoard gameBoard;
	public int size;
	private Random r;
	
	Game(int size) {
		r = new Random(0);
		this.size = size;
		gameBoard = new GameBoard(size, this, setInitialPieces());
		GamePiece.pieceMovements = setPieceMovements();	
		GamePiece.tags = setTags();
	}
	
	
	abstract TreeMap<Integer, Vector<int[]>> setPieceMovements();
	abstract Vector<int[]> setInitialPieces();
	abstract String[] setTags();
	
	//abstract int[] getScores();
	
	private final Vector<Move> getAllMoves() {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: gameBoard.getAllPieces() ) {
			allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}
	
	private final Vector<Move> getAllMoves(int playerId) {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: gameBoard.getAllPieces() ) {
			if (gp.getPlayerId()==playerId)
				allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}

	public final void makeRandomMove(int playerId) {
		Vector<Move> allMoves = getAllMoves(playerId);
		int a = r.nextInt() % allMoves.size();
		Move m = allMoves.elementAt(a>0? a: -a);
		gameBoard.updateWithMove(m);
	}
	
	public GamePiece getPieceIn(int x, int y) {
		return gameBoard.getPieceIn(x, y);
	}
	
	
}
