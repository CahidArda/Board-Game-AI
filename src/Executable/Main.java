package executable;
import java.util.Arrays;

import board.GamePiece;
import board.Move;
import game.EightPawnGame;
import game.Game;

public class Main {
	public static void main(String[] args) {
		test3();
	}
	
	
	//Move a pawn for a few rounds
	public static void test1() {
		Game g = new EightPawnGame(10);
		g.gameBoard.printGameBoardToConsole();
		
		int x = 1;
		for (int y=1; y<8; y++) {
			GamePiece gp = g.gameBoard.getPieceIn(x, y);
			int[] to = {x, y+1};
			
			
			Move m = new Move(gp, to);
			
			
			for (Move m1: gp.getPossibleMovesForPiece()) {
				System.out.println(m1);
			}
			g.gameBoard.updateWithMove(m);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(g.gameBoard.getNumberOfPiecesOnTheBoard());	
		}
	}
	
	//Make random moves
	public static void test2() {
		Game g = new EightPawnGame(4);
		g.gameBoard.printGameBoardToConsole();
		for (int i=0; i<3; i++) {
			g.gameBoard.makeRandomMove(0);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
			g.gameBoard.makeRandomMove(1);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
		}
	}
	
	//Make random moves, reverse them
	public static void test3() {
		Game g = new EightPawnGame(3);
		g.gameBoard.printGameBoardToConsole();
		int nofGames = 6;
		for (int i=0; i<nofGames/2; i++) {
			g.gameBoard.makeRandomMove(0);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
			g.gameBoard.makeRandomMove(1);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
		}
		System.out.println("---------######---------");
		
		for (int i=0; i<nofGames; i++) {
			g.gameBoard.reverseMove();

			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
		}
	}
}
