package board;

public class Move {
	GamePiece pieceToMove;
	int[] from;
	int[] to;
	
	public Move(GamePiece pieceToMove, int[] to) {
		this.pieceToMove = pieceToMove;
		from = new int[2];
		from[0] = pieceToMove.getPositionIndexes()[0];
		from[1] = pieceToMove.getPositionIndexes()[1];
		this.to = to;
	}
	
	public String toString() {
		return String.format("Moving %s, from (%d, %d) to (%d, %d)", pieceToMove.tags[pieceToMove.getTagId()], from[0], from[1], to[0], to[1]);
	}
	
	public int hashCode() {
		return from[0]*10000 +
			from[1]*1000+
			to[0]*100+
			to[1]*10+
			pieceToMove.getPieceId();
	}
	
	public boolean equals(Object other){
		if (other instanceof Move) {
			Move m = (Move) other;
			return (m.pieceToMove.getPieceId() == pieceToMove.getPieceId() && m.to[0] == to[0] && m.to[1] == to[1]);
		}
		return false;
	}	
}
