package board;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import game.Game;

/**
 * Used to represent the game board.
 * @author cahid
 *
 */
public class GameBoard {
	private GamePiece[][] gamePieces;
	private  Vector<GamePiece> allPieces;
	private  Stack<Move> pastMoves;
	private  Map<Move, GamePiece> removedPieces;
	private int size;
	private int nofMovesPlayedOnTheBoard;
	private Random r;
	
	/**
	 * 
	 * @param size Size of the n*n game board
	 * @param initialPieces Initial GamePiece objects on the game board
	 */
	public GameBoard(int size, Vector<GamePiece> initialPieces) {
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
	
	/**
	 * Updates the gameBoard object with a move.
	 * Checks if the move is plausible, gives error if it isn't.
	 * @param move Move being implemented
	 */
	public void updateWithMove(Move move) {
		if(!move.pieceToMove.getPossibleMovesForPiece().contains(move)) {
			printGameBoardToConsole();
			System.out.println(move);
			System.out.println("stats:");
			System.out.println(allPieces.size());
			System.out.println(pastMoves.size());
			System.out.println(removedPieces.size());
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
	
	/**
	 * Reverses the last move played.
	 */
	public void reverseMove() {		
		Move move = pastMoves.pop();
		
		gamePieces[move.to[1]][move.to[0]] = null;
		for (Move m: removedPieces.keySet()) {
			if (m.getMoveId() == move.getMoveId()) {
				setPieceTo(m.to, removedPieces.get(m));
				removedPieces.remove(m);
				break;
			}
		}
		gamePieces[move.from[1]][move.from[0]] = move.pieceToMove;
		move.pieceToMove.setPositionIndexes(move.from);
		nofMovesPlayedOnTheBoard--;
	}
	
	/**
	 * Used to set the pieces to their positions on the gamePieces array and
	 * add the pieces to the allPieces vector at the time of initialization.
	 * @param indexes indexes of the 
	 * @param gp
	 */
	private void setPieceTo(int[] indexes, GamePiece gp) {
		if (!allPieces.contains(gp)) {
			allPieces.add(gp);
		}
		gamePieces[indexes[1]][indexes[0]] = gp;
	}
	
	/**
	 * @return All playable moves of both players
	 */
	public Vector<Move> getAllMoves() {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: getAllPieces() ) {
			allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}
	
	/**
	 * @param playerId Id of the player whose moves are returned
	 * @return All playable moves of the player
	 */
	public final Vector<Move> getAllMoves(int playerId) {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: getAllPieces() ) {
			if (gp.getPlayerId()==playerId)
				allMoves.addAll(gp.getPossibleMovesForPiece());
		}
		return allMoves;
	}
	
	/**
	 * 
	 * @param playerId Id of the player whose random move is returned
	 * @return Random move of the player
	 */
	public final Move getRandomMove(int playerId) {
		Vector<Move> allMoves = getAllMoves(playerId);
		int a = r.nextInt() % allMoves.size();
		return allMoves.elementAt(a>0? a: -a);
	}
	
	/**
	 * A random move is implemented
	 * @param playerId Id of the player
	 */
	public final void makeRandomMove(int playerId) {
		updateWithMove(getRandomMove(playerId));
	}
	
	/**
	 * GameBoard's (private gamePieces array) content is printed to console.
	 * Pieces owned by the player with id 0 are shown with 'A',
	 * pieces shown with a 'B' represent pieces owned by player with id 1
	 */
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
		System.out.println(getNofMovesPlayedOnTheBoard());
	}
	
	/**
	 * 
	 * @return Id of player whose turn has come to play
	 */
	public final int getNextPlayerId() {
		return nofMovesPlayedOnTheBoard%2;
	}
	
	/**
	 * 
	 * @param indexes indexes being checked
	 * @return true if the indexes are within the boundaries 
	 */
	public boolean indexesAreWithinGameBoard(int[] indexes) {
		return (0<=indexes[0] && indexes[0]<size && 0<=indexes[1] && indexes[1]<size);
	}
	
	/**
	 * 
	 * @param x x index being checked
	 * @param y y index being checked
	 * @return true if the indexes are within the boundaries
	 */
	public boolean indexesAreWithinGameBoard(int x, int y) {
		return (0<=x && x<size && 0<=y && y<size);
	}
	
	/**
	 * Used to access to a GamePiece from it's indexes.
	 * Checks if given indexes are in the boundaries
	 * @param indexes indexes of the GamePiece object
	 * @return GamePiece object at the given indexes
	 */
	public GamePiece getPieceIn(int[] indexes) {
		if(!indexesAreWithinGameBoard(indexes)) {
	        throw new IllegalArgumentException(String.format("index out of range: %d, %d", indexes[0], indexes[1]));
		}
		return gamePieces[indexes[1]][indexes[0]];
	}
	
	/**
	 * Used to access to a GamePiece from it's indexes.
	 * Checks if given indexes are in the boundaries
	 * @param x x index of the GamePiece object
	 * @param y y index of the GamePiece object
	 * @return GamePiece object at the given indexes
	 */
	public GamePiece getPieceIn(int x, int y) {
		if(!indexesAreWithinGameBoard(x,y)) {
	        throw new IllegalArgumentException(String.format("index out of range: %d, %d", x, y));
		}
		return gamePieces[y][x];
	}
	
	/**
	 * Used to get the number of pieces on the board through private allPieces Vector
	 * @return number of pieces on the board
	 */
	public int getNumberOfPiecesOnTheBoard() {
		return allPieces.size();
	}
	
	/**
	 * Used to access the private allPieces vector
	 * @return vector with all the gamePieces
	 */
	public Vector<GamePiece> getAllPieces() {
		return allPieces;
	}
	
	/**
	 * @return number of moves played
	 */
	public int getNofMovesPlayedOnTheBoard() {
		return nofMovesPlayedOnTheBoard;
	}
}
