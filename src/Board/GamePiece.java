package board;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class GamePiece {
	public static String[] tags;
	private int tagId;
	static int lastId;
	private int pieceId;
	
	public static Map<Integer, Vector<int[]>> pieceMovements; //from tag to all possible moves. Example move {1,1}
	private int[] positionIndexes;
	
	private int player;
	GameBoard gameBoard;	
	
	public GamePiece(int tagId, int player, int[] positionIndexes) {
		pieceId = lastId++;
		this.tagId = tagId;
		this.player = player;
		this.positionIndexes = positionIndexes;
		this.gameBoard = null;
	}
	
	private boolean CanEatEnemyPiece = true;
	public final Vector<Move> getPossibleMovesForPiece() {
		Vector<Move> possibleMoves = new Vector<Move>();
		for (int[] move: pieceMovements.get(tagId)) {
			int[] newIndexes = {positionIndexes[0] + move[0], positionIndexes[1] + move[1]};
			
			//out of the map, pass
			if (!gameBoard.indexesAreWithinGameBoard(newIndexes)) {
				continue;
			}
			
			GamePiece gpInGivenIndex = gameBoard.getPieceIn(newIndexes);
			
			if (gpInGivenIndex==null) {
				possibleMoves.add(new Move(this, newIndexes));
			} else {
				
				//own piece in the new index, pass
				if (player == gpInGivenIndex.player) {
					continue;
				}
				
				//enemy piece in the new index, add if eating is enabled
				if (CanEatEnemyPiece && player!=gpInGivenIndex.player) {
					possibleMoves.add(new Move(this, newIndexes));
				}
			}	
		}
		return possibleMoves;
	}
	
	//TODO more informative
	public String toString() {
		
		return Arrays.toString(positionIndexes);
		//return tags[tagId];
	}
	
	public boolean equals(Object other) {
		if (other instanceof GamePiece) {
			GamePiece otherGp = (GamePiece) other;
			return otherGp.pieceId == pieceId;
		}
		return false;
	}
	
	protected void setPositionIndexes(int[] positionIndexes) {
		this.positionIndexes = positionIndexes;
	}
	
	protected int[] getPositionIndexes() {
		return positionIndexes;
	}
	
	protected int getTagId() {
		return tagId;
	}
	
	public int getPlayerId() {
		return player;
	}
	
	public int getPieceId() {
		return pieceId;
	}
}