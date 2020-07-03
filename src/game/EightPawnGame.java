package game;

import java.util.TreeMap;
import java.util.Vector;

import board.GamePiece;

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
	Vector<GamePiece> setInitialPieces() {
		int yOffset = 0;
		Vector<GamePiece> v = new Vector<GamePiece>(); 
		for (int x = 0; x < super.size; x++) {
			int[] p1 = {x, yOffset};
			v.add(new GamePiece(0, 0, p1));
			
			int[] p2 = {x, size-yOffset-1};
			v.add(new GamePiece(0,1,p2));
		}
		return v;
	}

	@Override
	String[] setTags() {
		String[] tags = new String[1];
		tags[0] = "pawn";
		return tags;
	}

	@Override
	public int[] getScores() {
		int[] scores = new int[2];
		for (GamePiece gp: super.gameBoard.getAllPieces()) {
			scores[gp.getPlayerId()] += 1;
		}
		return scores;
	}

	
}
