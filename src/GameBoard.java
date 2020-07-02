import java.util.HashSet;
import java.util.Set;

public class GameBoard {
	private GamePiece[][] gamePieces;
	private Set<GamePiece> allPieces;
	private int size;
	private Game game;
	
	GameBoard(int size, Game game) {
		this.size = size;
		gamePieces = new GamePiece[size][size];
		allPieces = new HashSet<GamePiece>();
		this.game = game;
		fillBoard();
	}
	
	private void fillBoard() {
		for (int x=0; x<size; x++) {
			int[] is1 = {x, size-2};
			setPieceTo(is1, new GamePiece(0, 0, is1, game));
			int[] is2 = {x, 1};
			setPieceTo(is2, new GamePiece(0, 1, is2, game));
		}
	}
	
	public void updateWithMove(Move move) {
		if(!move.pieceToMove.getPossibleMovesForPiece().contains(move)) {
	        throw new IllegalArgumentException("Given move is not possible");
	    }
		setPieceTo(move.to, move.pieceToMove);
	}
	
	private void setPieceTo(int[] indexes, GamePiece gp) {
		
		if (getPieceIn(indexes) != null) { //no piece in target position
			allPieces.remove(getPieceIn(indexes));
		}
		if (!allPieces.contains(gp)) {
			allPieces.add(gp);
		}
		gamePieces[indexes[1]][indexes[0]] = gp;
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
					System.out.print(String.format("%s%d ", (getPieceIn(x, y).player==0?"A":"B"), getPieceIn(x, y).tagId));
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
}
