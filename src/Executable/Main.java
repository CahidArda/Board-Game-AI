package executable;
import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.Scanner;

import board.GamePiece;
import board.Move;
import game.EightPawnGame;
import game.Game;

/*
 * check move
 * 0 0 0 1 3 0 3 1
 */

public class Main {
	public static void main(String[] args) {
		test4(1, 10, 5);
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
		System.out.println(Arrays.toString(g.getScores()));
		System.out.println(g.gameBoard.getNextPlayerId());
		for (int i=0; i<3; i++) {
			g.gameBoard.makeRandomMove(0);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
			System.out.println(g.gameBoard.getNextPlayerId());
			g.gameBoard.makeRandomMove(1);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
			System.out.println(g.gameBoard.getNextPlayerId());
		}
	}
	
	//Make random moves, reverse them
	public static void test3() {
		Game g = new EightPawnGame(3);
		g.gameBoard.printGameBoardToConsole();
		System.out.println(Arrays.toString(g.getScores()));
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
	
	//play with AI
	public static void test4(int idOfTheAI, int nofRoundsToPlay, int searchDepth) {
		Game g = new EightPawnGame(4);
		Scanner sc = new Scanner(System.in);
		
		if (idOfTheAI==0) {
			g.gameBoard.updateWithMove(g.getBestMoveForNextPlayer(searchDepth));
		}
		
		for (int i=0; i<nofRoundsToPlay; i++) {
			g.gameBoard.printGameBoardToConsole();
			int xi = sc.nextInt();
			int yi = sc.nextInt();
			int[] cf = {sc.nextInt(), sc.nextInt()};
			sc.nextLine();
			
			if (g.gameBoard.getPieceIn(xi, yi)==null) {
				System.out.println("ERROR: Invalid move, Enter another move.");
				i--;
				continue;
			}
			
			Move m = new Move(g.gameBoard.getPieceIn(xi, yi), cf);
			
			if (g.gameBoard.getPieceIn(xi, yi).getPlayerId()==idOfTheAI || !g.gameBoard.getAllMoves(g.gameBoard.getNextPlayerId()).contains(m)) {
				System.out.println("ERROR: Invalid move, Enter another move.");
				i--;
				continue;
			}
			
			g.gameBoard.updateWithMove(m);
			g.gameBoard.printGameBoardToConsole();
			Move bestMove = g.getBestMoveForNextPlayer(searchDepth);
			g.gameBoard.updateWithMove(bestMove);
			System.out.println("-----###-----");
			for (GamePiece gp: g.gameBoard.getAllPieces()) {
				System.out.println(gp);
			}
		}
		sc.close();
	}
}
