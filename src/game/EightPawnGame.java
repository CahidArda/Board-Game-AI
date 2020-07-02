package game;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class EightPawnGame extends Game {
	public EightPawnGame(int size) {
		super(size);
	}

	@Override
	TreeMap<Integer, Vector<int[]>> setPieceMovements() {
		TreeMap<Integer, Vector<int[]>> pieceMovements = new TreeMap<Integer, Vector<int[]>>();
		
		Vector<int[]> hs1 = new Vector<int[]>();
		int[] m1 = {1,0};
		int[] m2 = {0,1};
		int[] m3 = {-1,0};
		int[] m4 = {0,-1};
		hs1.add(m1);
		hs1.add(m2);
		hs1.add(m3);
		hs1.add(m4);
		pieceMovements.put(0, hs1);
		return pieceMovements;
	}

	//{x, y, tagId, player}
	Vector<int[]> setInitialPieces() {
		int yOffset = 1;
		Vector<int[]> v = new Vector<int[]>(); 
		for (int x = 0; x < super.size; x++) {
			int[] i1 = {x, yOffset, 0, 0};
			v.add(i1);
			
			int[] i2 = {x, size-yOffset-1, 0, 1};
			v.add(i2);
		}
		return v;
	}

	@Override
	String[] setTags() {
		String[] tags = new String[1];
		tags[0] = "pawn";
		return tags;
	}
	
	
}
