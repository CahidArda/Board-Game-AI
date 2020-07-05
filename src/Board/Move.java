package board;

/**
 * Used to represent the moves being implemented during gameplay
 * @author cahid
 *
 */
public class Move {
	private int[] from;
	private int[] to;
	private static int lastId;
	private int moveId;
	private boolean implemented;
	private GamePiece pieceRemovedAfterImplementation;
	
	/**
	 * 
	 * @param from indexes of the first position
	 * @param to indexes of the target position
	 */
	public Move(int[] from, int[] to) {
		moveId=lastId++;
		this.from = new int[2];
		this.from[0] = from[0];
		this.from[1] = from[1];
		this.to = new int[2];
		this.to[0] = to[0];
		this.to[1] = to[1];
		implemented = false;
	}
	
	/**
	 * 
	 * @param xi x index of first position
	 * @param yi y index of first position
	 * @param xf x index of target position
	 * @param yf y index of target position
	 */
	public Move(int xi, int yi, int xf, int yf) {
		moveId=lastId++;
		from = new int[2];
		from[0] = xi;
		from[1] = yi;
		to = new int[2];
		to[0] = xf;
		to[1] = yf;
		implemented = false;
	}
	
	/**
	 * Called to make necessary updates after implementation
	 * @param removedPiece Piece removed when the move is implemented
	 */
	public void updateAfterImplementation(GamePiece removedPiece) {
		pieceRemovedAfterImplementation = removedPiece;
		implemented = true;
	}
	
	/**
	 * Used to access the GamePiece object removed after implementation
	 * @return removed piece
	 */
	public GamePiece getRemovedPieceAfterImplementation() {
		if (!implemented) {
			throw new IllegalStateException("Move needs to be implemented before having a removed piece.");
		} else if (pieceRemovedAfterImplementation==null) {
			throw new IllegalStateException("Move doesn't have a piece removed to return.");
		}
		return pieceRemovedAfterImplementation;
	}
	
	/**
	 * Used to check if there is a removed piece attached
	 * @return true if there is a removed piece attached
	 */
	public boolean hasRemovedPiece() {
		if (!implemented) {
			throw new IllegalStateException("Move needs to be implemented before having a removed piece.");
		}
		return (pieceRemovedAfterImplementation!=null);
	}
	
	/**
	 * Used to check if the move is implemented
	 * @return true if move is implemented
	 */
	public boolean isImplemented() {
		return implemented;
	}
	
	/**
	 * Get first indexes of the move
	 * @return first indexes
	 */
	public int[] getFrom() {
		return from;
	}
	
	/**
	 * Get the target indexes of the move
	 * @return target indexes
	 */
	public int[] getTo() {
		return to;
	}
	
	/**
	 * @return distinctive Id of the Move object
	 */
	public int getMoveId() {
		return moveId;
	}
	
	//TODO more informative
	public String toString() {
		return String.format("Moving from (%d, %d) to (%d, %d)", from[0], from[1], to[0], to[1]);
	}
	
	public int hashCode() {
		return from[0]*1000+
			from[1]*100+
			to[0]*10+
			to[1]*1;
			
	}
	
	public boolean equals(Object other){
		if (other instanceof Move) {
			Move m = (Move) other;
			return (m.to[0] == to[0] && 
					m.to[1] == to[1] && 
					m.from[0]==from[0] && 
					m.from[1] == from[1]);
		}
		return false;
	}
	
}
