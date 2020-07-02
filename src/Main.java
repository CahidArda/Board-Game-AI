
public class Main {
	public static void main(String[] args) {
		Game g = new Game(8);
		g.gameBoard.printGameBoardToConsole();
		GamePiece gp = g.getPieceIn(1,1);
		int[] to = {2,1};
		Move m = new Move(gp, to);
		
		System.out.println();
		g.gameBoard.updateWithMove(m);
		g.gameBoard.printGameBoardToConsole();
		System.out.println(g.gameBoard.getPieceIn(1, 1));
		
	}
}
