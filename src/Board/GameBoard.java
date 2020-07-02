package board;
import java.util.Vector;

import game.Game;

public class GameBoard {
	private GamePiece[][] gamePieces;
	private Vector<GamePiece> allPieces;
	private int size;
	
	public GameBoard(int size, Game game, Vector<int[]> initialPieces) {
		this.size = size;
		gamePieces = new GamePiece[size][size];
		allPieces = new Vector<GamePiece>();
		for (int[] indexes_TagId_PlayerId: initialPieces) {
			int[] indexes = {indexes_TagId_PlayerId[0], indexes_TagId_PlayerId[1]};
			setPieceTo(indexes, new GamePiece(indexes_TagId_PlayerId[2], indexes_TagId_PlayerId[3], indexes, this));
		}
	}
	
	public void updateWithMove(Move move) {
		if(!move.pieceToMove.getPossibleMovesForPiece().contains(move)) {
	        throw new IllegalArgumentException("Given move is not possible");
	    }
		
		if (getPieceIn(move.to)!=null) {
			allPieces.remove(getPieceIn(move.to));
		}
		gamePieces[move.pieceToMove.positionIndexes[1]][move.pieceToMove.positionIndexes[0]] = null;
		gamePieces[move.to[1]][move.to[0]] = move.pieceToMove;
		move.pieceToMove.positionIndexes = move.to;
	}
	
	private void setPieceTo(int[] indexes, GamePiece gp) {
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
					System.out.print(String.format("%s%d ", (getPieceIn(x, y).getPlayerId()==0?"A":"B"), getPieceIn(x, y).tagId));
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
}
