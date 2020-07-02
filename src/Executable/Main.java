package Executable;
import Board.GamePiece;
import Board.Move;
import game.EightPawnGame;
import game.Game;

public class Main {
	public static void main(String[] args) {
		Game g = new EightPawnGame(8);
		g.gameBoard.printGameBoardToConsole();
		
		int x = 1;
		for (int y=1; y<7; y++) {
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
}
