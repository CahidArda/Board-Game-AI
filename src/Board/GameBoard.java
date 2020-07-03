package board;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import game.Game;

public class GameBoard {
	private GamePiece[][] gamePieces;
	private Vector<GamePiece> allPieces;
	private Stack<Move> pastMoves;
	private Map<Move, GamePiece> removedPieces;
	private int size;
	private int nofMovesPlayedOnTheBoard;
	
	private Random r;
	
	public GameBoard(int size, Game game, Vector<GamePiece> initialPieces) {
		this.size = size;
		gamePieces = new GamePiece[size][size];
		allPieces = new Vector<GamePiece>();
		pastMoves = new Stack<Move>();
		removedPieces = new HashMap<Move, GamePiece>();
		for (GamePiece gp: initialPieces) {
			setPieceTo(gp.getPositionIndexes(), gp);
			gp.gameBoard = this;
		}
		r = new Random(0);
		nofMovesPlayedOnTheBoard = 0;
	}
	
	public void updateWithMove(Move move) {
		if(!move.pieceToMove.getPossibleMovesForPiece().contains(move)) {
	        throw new IllegalArgumentException("Given move is not possible");
	    }
		pastMoves.add(move);
		if (getPieceIn(move.to)!=null) {
			allPieces.remove(getPieceIn(move.to));
			removedPieces.put(move, getPieceIn(move.to));
		}
		gamePieces[move.from[1]][move.from[0]] = null;
		gamePieces[move.to[1]][move.to[0]] = move.pieceToMove;
		move.pieceToMove.setPositionIndexes(move.to);
		nofMovesPlayedOnTheBoard++;
	}
	
	public void reverseMove() {
		Move move = pastMoves.pop();
		if (removedPieces.keySet().contains(move)) {
			setPieceTo(move.to, removedPieces.get(move));
		} else {
			gamePieces[move.to[1]][move.to[0]] = null;
		}
		
		gamePieces[move.from[1]][move.from[0]] = move.pieceToMove;
		move.pieceToMove.setPositionIndexes(move.from);
		nofMovesPlayedOnTheBoard--;
	}
	
	private void setPieceTo(int[] indexes, GamePiece gp) {
		if (!allPieces.contains(gp)) {
			allPieces.add(gp);
		}
		gamePieces[indexes[1]][indexes[0]] = gp;
	}
	
	public Vector<Move> getAllMoves() {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: getAllPieces() ) {
			allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}
	
	public final Vector<Move> getAllMoves(int playerId) {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: getAllPieces() ) {
			if (gp.getPlayerId()==playerId)
				allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}
	
	public final Move getRandomMove(int playerId) {
		Vector<Move> allMoves = getAllMoves(playerId);
		int a = r.nextInt() % allMoves.size();
		return allMoves.elementAt(a>0? a: -a);
	}
	
	public final void makeRandomMove(int playerId) {
		updateWithMove(getRandomMove(playerId));
	}
	
	public void printGameBoardToConsole() {
		System.out.print("   ");
		for (int i=0; i<size; i++) {
			System.out.print("  " + i);
		}
		System.out.println();
		for (int y=0; y<size; y++) {
			System.out.print(y + "   ");
			for (int x=0; x<size; x++) {
				if (gamePieces[y][x]==null) {
					System.out.print(".. ");
				} else {
					System.out.print(String.format("%s%d ", (getPieceIn(x, y).getPlayerId()==0?"A":"B"), getPieceIn(x, y).getTagId()));
				}
			}
			System.out.println();
		}
	}
	
	public boolean indexesAreWithinGameBoard(int[] indexes) {
		return (0<=indexes[0] && indexes[0]<size && 0<=indexes[1] && indexes[1]<size);
	}
	
	public boolean indexesAreWithinGameBoard(int x, int y) {
		return (0<=x && x<size && 0<=y && y<size);
	}
	
	//TODO fix assertion, change with exception
	public GamePiece getPieceIn(int[] indexes) {
		assert (indexesAreWithinGameBoard(indexes)) : String.format("index out of range: %d, %d", indexes[0], indexes[1]);
		return gamePieces[indexes[1]][indexes[0]];
	}
	
	//TODO fix assertion, change with exception
	public GamePiece getPieceIn(int x, int y) {
		assert (indexesAreWithinGameBoard(x,y)) : String.format("index out of range: %d, %d", x, y);
		return gamePieces[y][x];
	}
	
	public int getNumberOfPiecesOnTheBoard() {
		return allPieces.size();
	}
	
	public Vector<GamePiece> getAllPieces() {
		return allPieces;
	}
	
	public int getNofMovesPlayedOnTheBoard() {
		return nofMovesPlayedOnTheBoard;
	}
}
