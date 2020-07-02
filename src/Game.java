import java.util.Set;

public class Game {
	GameBoard gameBoard;
	
	Game(int size) {
		gameBoard = new GameBoard(size, this);
	}
	
	
	//abstract Set<Move> getAllPossibleMoves(int player);
	//abstract int[] getScores();
	
	GamePiece getPieceIn(int[] indexes) {
		return gameBoard.getPieceIn(indexes);
	}
	
	GamePiece getPieceIn(int x, int y) {
		return gameBoard.getPieceIn(x, y);
	} 
	
	
}
