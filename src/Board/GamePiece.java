package board;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

/**
 * Used to represent all the game pieces on the board
 * @author cahid
 *
 */
public class GamePiece {
	public static String[] tags;
	private int tagId;
	static int lastId;
	private int pieceId;
	
	public static Map<Integer, Vector<int[]>> pieceMovements; //from tag to all possible moves. Example move {1,1}
	private int[] positionIndexes;
	
	private int player;
	
	/**
	 * 
	 * @param tagId tag of the GamePiece, describing the possible moves and the name of the piece
	 * @param player Id of the player controlling the piece
	 * @param positionIndexes initial position indexes of the gamePiece
	 */
	public GamePiece(int tagId, int player, int[] positionIndexes) {
		pieceId = lastId++;
		this.tagId = tagId;
		this.player = player;
		this.positionIndexes = positionIndexes;
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
	
	/**
	 * Used to set the position indexes of the piece
	 * @param positionIndexes new indexes to be set to the piece
	 */
	protected void setPositionIndexes(int[] positionIndexes) {
		this.positionIndexes = positionIndexes;
	}
	
	/**
	 * Used to get the position indexes of the piece
	 * @return position indexes of the piece
	 */
	public int[] getPositionIndexes() {
		return positionIndexes;
	}
	
	public Vector<int[]> getPieceMovements() {
		return pieceMovements.get(tagId);
	}
	
	/**
	 * Used to get the tagId of the GamePiece, describing the possible moves and the name of the piece
	 * @return tagId of the GamaPiece
	 */
	protected int getTagId() {
		return tagId;
	}
	
	/**
	 * Used to get the player of the GamePiece
	 * @return player Id of the piece
	 */
	public int getPlayerId() {
		return player;
	}
	
	/**
	 * Used to get the distinctive id of the GamePiece 
	 * @return piece id
	 */
	public int getPieceId() {
		return pieceId;
	}
}