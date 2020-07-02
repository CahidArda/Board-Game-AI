package executable;
import board.GamePiece;
import board.Move;
import game.EightPawnGame;
import game.Game;

public class Main {
	public static void main(String[] args) {
		test2();
	}
	
	public static void test1() {
		Game g = new EightPawnGame(10);
		g.gameBoard.printGameBoardToConsole();
		
		int x = 1;
		for (int y=1; y<8; y++) {
			GamePiece gp = g.getPieceIn(x, y);
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
	
	public static void test2() {
		Game g = new EightPawnGame(4);
		g.gameBoard.printGameBoardToConsole();
		for (int i=0; i<5; i++) {
			g.makeRandomMove(0);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(g.gameBoard.getNumberOfPiecesOnTheBoard());
			g.makeRandomMove(1);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(g.gameBoard.getNumberOfPiecesOnTheBoard());
			
		}
	}
}
