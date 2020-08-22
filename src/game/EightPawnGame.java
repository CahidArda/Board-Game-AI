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
		int yOffset = 1;
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
			if (gp.getPlayerId()==0) {
				scores[0]+=10;
				scores[1]-=10;
				scores[0]+=(int) Math.pow(gp.getPositionIndexes()[1]+1, 2);
				scores[1]-=(int) Math.pow(gp.getPositionIndexes()[1]+1, 2);
			} else {
				scores[1]+=10;
				scores[0]-=10;
				scores[1]+=(int) Math.pow(super.size-gp.getPositionIndexes()[1], 2);
				scores[0]-=(int) Math.pow(super.size-gp.getPositionIndexes()[1], 2);
			}
		}
		/*
		for (GamePiece gp: super.gameBoard.getAllPieces()) {
			if (gp.getPlayerId()==0) {
				scores[0]+=10;
				scores[1]-=10;
				scores[0]+=gp.getPositionIndexes()[1]+1; //(int) Math.pow(gp.getPositionIndexes()[1]+1, 2);
				scores[1]-=gp.getPositionIndexes()[1]+1; //(int) Math.pow(gp.getPositionIndexes()[1]+1, 2);
			} else {
				scores[1]+=10;
				scores[0]-=10;
				scores[1]+=super.size-gp.getPositionIndexes()[1]; //(int) Math.pow(super.size-gp.getPositionIndexes()[1], 2);
				scores[0]-=super.size-gp.getPositionIndexes()[1]; //(int) Math.pow(super.size-gp.getPositionIndexes()[1], 2);
			}
		}
		*/
		return scores;
	}

	@Override
	public void printGameSettingsToConsole() {
		System.out.println("In this game, there is only one type of piece.");
		System.out.println("That piece can move up, down, left and right.");
		System.out.println();
		System.out.println("When it is your turn; type the x, y indexes of the piece you want to move.");
		System.out.println("Then type where you want to move the piece.");
		System.out.println();
		
	}
}
