package board;

public class Move {
	GamePiece pieceToMove;
	int[] to;
	
	private static int lastId = 0;
	private int moveId;
	
	public Move(GamePiece pieceToMove, int[] to) {
		moveId = lastId++;
		this.pieceToMove = pieceToMove;
		this.to = to;
	}
	
	public String toString() {
		return String.format("Moving %s, from (%d, %d) to (%d, %d)", pieceToMove.tags[pieceToMove.tagId], pieceToMove.positionIndexes[0], pieceToMove.positionIndexes[1], to[0], to[1]);
	}
	
	public boolean equals(Object other){
		if (other instanceof Move) {
			Move m = (Move) other;
			return (m.pieceToMove.pieceId == pieceToMove.pieceId && m.to[0] == to[0] && m.to[1] == to[1]);
		}
		return false;
	}

	/*
	@Override
	public int compareTo(Move other) {
		return (pieceToMove.positionIndexes[0]-other.pieceToMove.positionIndexes[0])*1000+
				(pieceToMove.positionIndexes[1]-other.pieceToMove.positionIndexes[1])*100+
				(to[0]-other.to[0])*10+
				(to[1]-other.to[1]);
	}
	*/
	
	
}
