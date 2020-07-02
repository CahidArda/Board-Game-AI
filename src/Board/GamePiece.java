package board;
import java.util.Map;
import java.util.Vector;

public class GamePiece {
	public static String[] tags;
	int tagId;
	static int lastId;
	int pieceId;
	
	public static Map<Integer, Vector<int[]>> pieceMovements; //from tag to all possible moves. Example move {1,1}
	int[] positionIndexes;
	
	private int player;
	GameBoard gameBoard;	
	
	GamePiece(int tagId, int player, int[] positionIndexes, GameBoard gameBoard) {
		pieceId = lastId++;
		this.tagId = tagId;
		this.player = player;
		this.positionIndexes = positionIndexes;
		this.gameBoard = gameBoard;
	}
	
	boolean CanEatEnemyPiece = true;
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
		return tags[tagId];
	}
	
	public boolean equals(Object other) {
		if (other instanceof GamePiece) {
			GamePiece otherGp = (GamePiece) other;
			return otherGp.pieceId == pieceId;
		}
		return false;
	}
	
	public int getPlayerId() {
		return player;
	}
	/*
	@Override
	public int compareTo(GamePiece other) {
		return (player-other.player)*1000+
				(pieceId-other.pieceId)*100+
				(positionIndexes[0]-other.positionIndexes[0])*10+
				(positionIndexes[1]-other.positionIndexes[1]);
	}
	*/
}