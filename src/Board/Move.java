package board;

/**
 * Used to represent the moves being implemented during gameplay
 * @author cahid
 *
 */
public class Move {
	GamePiece pieceToMove;
	int[] from;
	int[] to;
	private static int lastId;
	private int moveId;
	/**
	 * 
	 * @param pieceToMove Piece being moved
	 * @param to target indexes of the move
	 */
	public Move(GamePiece pieceToMove, int[] to) {
		moveId=lastId++;
		this.pieceToMove = pieceToMove;
		from = new int[2];
		from[0] = pieceToMove.getPositionIndexes()[0];
		from[1] = pieceToMove.getPositionIndexes()[1];
		this.to = to;
	}
	
	public int getMoveId() {
		return moveId;
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
			return (m.pieceToMove.getPieceId() == pieceToMove.getPieceId() && 
					m.to[0] == to[0] && 
					m.to[1] == to[1] && 
					m.from[0]==from[0] && 
					m.from[1] == from[1]);
		}
		return false;
	}	
}
