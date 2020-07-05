package board;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * Used to represent the game board.
 * @author cahid
 *
 */
public class GameBoard {
	public  Vector<GamePiece> allPiecesOnTheBoard;
	public  Stack<Move> pastMoves;
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
		//gamePieces = new GamePiece[size][size];
		allPiecesOnTheBoard = new Vector<GamePiece>();
		pastMoves = new Stack<Move>();
		for (GamePiece gp: initialPieces) {
			setPieceTo(gp.getPositionIndexes(), gp);
		}
		r = new Random(0);
		nofMovesPlayedOnTheBoard = 0;
	}
	
	/**
	 * Checks if the move is plausible, gives error if it isn't.
	 * Makes necessary updates when a move is implemented.
	 * @param move Move being implemented
	 */
	public void updateWithMove(Move move) {
		if (!indexesAreWithinGameBoard(move.getTo()) || !indexesAreWithinGameBoard(move.getFrom())) {
			System.out.println(move);
			throw new IllegalArgumentException("Move's indexes are not within the ranges.");
		}
		//update piece at move.to
		GamePiece gp = getPieceIn(move.getTo());
		if (gp!=null) {
			allPiecesOnTheBoard.remove(gp);
			gp.setPositionIndexes(null);
		}
		//update move
		move.updateAfterImplementation(gp);
		pastMoves.add(move);
		//update piece at move.from
		gp = getPieceIn(move.getFrom());
		if (!getPossibleMovesForPiece(gp).contains(move)) {
			System.out.println(move);
			throw new IllegalArgumentException("Given move is impossible to implement");
		}
		gp.setPositionIndexes(move.getTo());
		nofMovesPlayedOnTheBoard++;
	}
	
	/**
	 * Makes the necessary updates to reverse a move
	 */
	public void reverseMove() {		
		Move move = pastMoves.pop();
		if (getPieceIn(move.getFrom())!=null) {
			throw new IllegalStateException("There can not be a piece at move.from when reversing a move.");
		} else if (getPieceIn(move.getTo())==null) {
			throw new IllegalStateException("There must be a piece at move.to when reversing a move.");
		}
		//move object from move.to to move.from
		GamePiece gp = getPieceIn(move.getTo());
		gp.setPositionIndexes(move.getFrom());
		//bring back the removed piece if there is one
		if (move.hasRemovedPiece()) {
			gp = move.getRemovedPieceAfterImplementation();
			gp.setPositionIndexes(move.getTo());
			allPiecesOnTheBoard.add(gp);
		}
		nofMovesPlayedOnTheBoard--;	
	}
	
	
	
	/**
	 * Used to set the pieces to their positions on the gamePieces array and
	 * add the pieces to the allPieces vector at the time of initialization.
	 * @param indexes indexes of the 
	 * @param gp
	 */
	private void setPieceTo(int[] to, GamePiece gp) {
		allPiecesOnTheBoard.add(gp);
		gp.setPositionIndexes(to);
		
	}
	
	/**
	 * @return All playable moves of both players
	 */
	public Vector<Move> getAllMoves() {
		Vector<Move> allMoves = new Vector<Move>();
		for (GamePiece gp: getAllPieces() ) {
			allMoves.addAll(getPossibleMovesForPiece(gp));
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
				allMoves.addAll(getPossibleMovesForPiece(gp));
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
	
	private boolean CanEatEnemyPiece = false;
	/**
	 * Used to get all possible moves of a piece
	 * Eating the enemy pieces can be disabled or enabled from source code
	 * @return Vector with all possible moves of the piece
	 */
	public final Vector<Move> getPossibleMovesForPiece(GamePiece gp) {
		Vector<Move> possibleMoves = new Vector<Move>();
		for (int[] move: gp.getPieceMovements()) {
			int[] newIndexes = {gp.getPositionIndexes()[0] + move[0], gp.getPositionIndexes()[1] + move[1]};
			
			//out of the map, pass
			if (!indexesAreWithinGameBoard(newIndexes)) {
				continue;
			}
			
			GamePiece gpInGivenIndex = getPieceIn(newIndexes);
			
			if (gpInGivenIndex==null) {
				possibleMoves.add(new Move(gp.getPositionIndexes(), newIndexes));
			} else {
				
				//own piece in the new index, pass
				if (gp.getPlayerId() == gpInGivenIndex.getPlayerId()) {
					continue;
				}
				
				//enemy piece in the new index, add if eating is enabled
				if (CanEatEnemyPiece && gp.getPlayerId()!=gpInGivenIndex.getPlayerId()) {
					possibleMoves.add(new Move(gp.getPositionIndexes(), newIndexes));
				}
			}	
		}
		return possibleMoves;
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
		String[][] str = new String[size][size];
		for (GamePiece gp: allPiecesOnTheBoard) {
			int x = gp.getPositionIndexes()[0];
			int y = gp.getPositionIndexes()[1];
			if (str[y][x]!=null) {
				throw new IllegalStateException("Two gamePieces can not have the same indexes");
			}
			str[y][x] = String.format("%s%d ", (getPieceIn(x, y).getPlayerId()==0?"A":"B"), getPieceIn(x, y).getTagId());
			
		}
		for (int y=0; y<size; y++) {
			System.out.print(y + "   ");
			for (int x=0; x<size; x++) {
				if (str[y][x]==null) {
					System.out.print(".. ");
				} else {
					System.out.print(str[y][x]);
				}
			}
			System.out.println();
		}
		System.out.println("Moves played: " + getNofMovesPlayedOnTheBoard());
		System.out.println("Number of pieces: " + getNumberOfPiecesOnTheBoard());
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
		return getPieceIn(indexes[0], indexes[1]);
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
		for (GamePiece gp: allPiecesOnTheBoard) {
			if (x==gp.getPositionIndexes()[0] && y==gp.getPositionIndexes()[1]) {
				return gp;
			}
		}
		return null;
	}
	
	/**
	 * Used to get the number of pieces on the board through private allPieces Vector
	 * @return number of pieces on the board
	 */
	public int getNumberOfPiecesOnTheBoard() {
		return allPiecesOnTheBoard.size();
	}
	
	/**
	 * Used to access the private allPieces vector
	 * @return vector with all the gamePieces
	 */
	public Vector<GamePiece> getAllPieces() {
		return allPiecesOnTheBoard;
	}
	
	/**
	 * @return number of moves played
	 */
	public int getNofMovesPlayedOnTheBoard() {
		return nofMovesPlayedOnTheBoard;
	}
}
