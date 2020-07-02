import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamePiece {
	static String[] tags;
	int tagId;
	static int lastId;
	int pieceId;
	
	static Map<Integer, HashSet<int[]>> moves; //from tag to all possible moves. Example move {1,1}
	int[] positionIndexes;
	
	int player;
	Game game;
	
	//TODO update to be more reasonable
	static {
		lastId = 0;
		tags = new String[1];
		tags[0] = "pawn";
		moves = new HashMap<Integer, HashSet<int[]>>();
		
		HashSet<int[]> hs1 = new HashSet<int[]>();
		int[] m1 = {1,0};
		int[] m2 = {0,1};
		int[] m3 = {1,1};
		int[] m4 = {0,0};
		hs1.add(m1);
		hs1.add(m2);
		hs1.add(m3);
		hs1.add(m4);
		moves.put(0, hs1);
	}
	
	
	GamePiece(int tagId, int player, int[] positionIndexes, Game game) {
		pieceId = lastId++;
		this.tagId = tagId;
		this.player = player;
		this.positionIndexes = positionIndexes;
		this.game = game;
	}
	
	boolean CanEatEnemyPiece = false;
	
	Set<Move> getPossibleMovesForPiece() {
		Set<Move> possibleMoves = new HashSet<Move>();
		for (int[] move: moves.get(tagId)) {
			int[] newIndexes = {positionIndexes[0] + move[0], positionIndexes[1] + move[1]};
			
			//out of the map, pass
			if (!game.gameBoard.indexesAreWithinGameBoard(newIndexes)) {
				continue;
			}
			
			GamePiece gpInGivenIndex = game.getPieceIn(newIndexes);
			
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
}