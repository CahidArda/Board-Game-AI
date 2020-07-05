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
	
	public Move getBestMoveForNextPlayer(int searchDepth) {
		int highestScore = -10000;
		Move[] bestMove = new Move[1];
		int nextPlayerId = gameBoard.getNextPlayerId();
		for (Move m: gameBoard.getAllMoves(nextPlayerId)) {
			
			//System.out.println("-----###----- -----###----- -----###-----");
			//gameBoard.printGameBoardToConsole();
			
			gameBoard.updateWithMove(m);
			int mScore = getMoveScore(2, nextPlayerId, searchDepth);
			if (bestMove[0]==null || highestScore<mScore) {
				highestScore = mScore;
				bestMove[0] = m;
			}
			gameBoard.reverseMove();
			
			//gameBoard.printGameBoardToConsole();
		}
		return bestMove[0];
	}
	
	private int getMoveScore(int currentDepth, int playerId, int searchDepth) {
		int scoreSum = 0;
		for (Move m: gameBoard.getAllMoves(gameBoard.getNextPlayerId())) {
			
			//System.out.println("              -----###-----");
			//gameBoard.printGameBoardToConsole();
			
			gameBoard.updateWithMove(m);
			if (currentDepth==searchDepth) {
				scoreSum+=getScores()[playerId];
			} else {
				scoreSum+=getMoveScore(currentDepth+1, playerId, searchDepth);
			}
			gameBoard.reverseMove();
			
			//gameBoard.printGameBoardToConsole();
		}
		return scoreSum;
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
}
