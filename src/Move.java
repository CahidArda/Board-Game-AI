
public class Move {
	private static int lastId;
	int moveId;
	GamePiece pieceToMove;
	int[] to;
	
	Move(GamePiece pieceToMove, int[] to) {
		moveId = lastId++;
		this.pieceToMove = pieceToMove;
		this.to = to;
	}
	
	public String toString() {
		return String.format("Moving %s, from (%d, %d) to (%d, %d)", pieceToMove.tags[pieceToMove.tagId], pieceToMove.positionIndexes[0], pieceToMove.positionIndexes[1], to[0], to[1]);
	}
	
	public int hashCode() {
		return moveId;
	}
	
	public boolean equals(Object other){
		if (other instanceof Move) {
			Move m = (Move) other;
			return (m.pieceToMove.pieceId == pieceToMove.pieceId && m.to[0] == to[0] && m.to[1] == to[1]);
		}
		return false;
	}
	
	
}
