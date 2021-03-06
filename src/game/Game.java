package game;
import java.util.TreeMap;
import java.util.Vector;

import board.GameBoard;
import board.GamePiece;
import board.Move;


/**
 * Abstract class of Game acts like an interface between the classes in the board package
 * -representing the board and the pieces on it- and the game designed, opening a simpler way
 * to write new games.
 * @author cahid
 *
 */
public abstract class Game {
	public GameBoard gameBoard;
	public int size;
	
	/**
	 * GameBoard is created with the given initial pieces set and size.
	 * @param size Size of the n*n game board
	 */
	Game(int size) {
		this.size = size;
		gameBoard = new GameBoard(size, setInitialPieces());
		GamePiece.pieceMovements = setPieceMovements();	
		GamePiece.tags = setTags();
	}
	
	/**
	 * Returns best move for the next player at the current state
	 * @return
	 */
	public Move getBestMoveForNextPlayer(int searchDepth) {
		Move[] bestMove = new Move[1];
		int bestScore = -100000;
		int nextPlayerId = gameBoard.getNextPlayerId();
		
		for (Move move: gameBoard.getAllMoves(nextPlayerId)) {
			gameBoard.updateWithMove(move);
			int scoreBelow = minMax(0, searchDepth);
			if (bestMove[0] == null || scoreBelow>bestScore) {
				bestScore = scoreBelow;
				bestMove[0] = move;
			}
			gameBoard.reverseMove();
		}
		return bestMove[0];
	}
	
	private int minMax(int currentDepth, int searchDepth) {
		int idOfThePlayer = gameBoard.getNextPlayerId();	//must be below updateWithMove(m)
		int maxScore = -100000;
		
		for (Move m: gameBoard.getAllMoves(idOfThePlayer)) {
			gameBoard.updateWithMove(m);
			int scoreBelow = (currentDepth==searchDepth? getScores()[idOfThePlayer]: minMax(currentDepth+1, searchDepth));
			maxScore = (scoreBelow>maxScore? scoreBelow: maxScore);
			gameBoard.reverseMove();
		}
		return -maxScore;	//has to be minus, for the previous player
	}

	
	/**
	 * Used to test whether the updating-reversing loop works by making random moves.
	 * @param nofBranches Number of child nodes of every node
	 * @param treeDepth Depth of the seatch tree
	 * @param currentDepth Used to keep track of depth of the recursive call. Should be set to 0 by default when calling the method. 
	 */
	public void runRandomMoveTree(int nofBranches, int treeDepth, int currentDepth) {
		if (currentDepth<treeDepth) {
			for (int i=0; i<nofBranches; i++) {
				gameBoard.makeRandomMove(gameBoard.getNextPlayerId());
				runRandomMoveTree(nofBranches, treeDepth, currentDepth+1);
				gameBoard.reverseMove();
			}
		}
	}
	

	/**
	 * GamePieces on the gameBoard at the time of initialization should be created and
	 * returned in a vector 
	 * @return All the GamePieces on the board at the time of initialization
	 */
	abstract Vector<GamePiece> setInitialPieces();
	
	/**
	 * A map representing the movements of the different types of GamePiece objects 
	 * distinguished by their tagIds.
	 * <p>
	 * possibleMovementsOfTheGamePieceType = map.get(tagIdOfTheGamePiece)
	 * @return movements of all types of pieces on the board 
	 */
	abstract TreeMap<Integer, Vector<int[]>> setPieceMovements();
	
	/**
	 * A string array representing the names of the different types of GamePiece objects
	 * distinguished by their tagIds.
	 * <p>
	 * nameOfTheGamePieceType = tags[tagIdOfTheGamePiece]
	 * @return tags (names) of the different GameObjects
	 */
	abstract String[] setTags();
	
	/**
	 * Used to access the score states of the players
	 * @return Scores of the players
	 */
	abstract public int[] getScores();
	
	abstract public void printGameSettingsToConsole();
}
