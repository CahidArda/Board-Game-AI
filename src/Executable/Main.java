package executable;
import java.util.Arrays;
import java.util.Scanner;

import board.GamePiece;
import board.Move;
import game.EightPawnGame;
import game.Game;


public class Main {
	public static void main(String[] args) {
		test4(0, 40, 3);
	}
	
	
	//Move a pawn for a few rounds
	public static void test1() {
		Game g = new EightPawnGame(6);
		g.gameBoard.printGameBoardToConsole();
		
		int x = 1;
		for (int y=1; y<5; y++) {
			GamePiece gp = g.gameBoard.getPieceIn(x, y);
					
			Move m = new Move(x, y, x, y+1);
			
			
			for (Move m1: g.gameBoard.getPossibleMovesForPiece(gp)) {
				System.out.println(m1);
			}
			g.gameBoard.updateWithMove(m);
			System.out.println();
			g.gameBoard.printGameBoardToConsole();
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
		Game g = new EightPawnGame(6);
		g.gameBoard.printGameBoardToConsole();
		System.out.println(Arrays.toString(g.getScores()));
		int nofGames = 100;
		for (int i=0; i<nofGames; i++) {
			g.gameBoard.makeRandomMove(g.gameBoard.getNextPlayerId());
			g.gameBoard.printGameBoardToConsole();
			
			if (g.gameBoard.getNumberOfPiecesOnTheBoard()==4) {
				System.out.println("Low number of pieces on the board");
			}
		}
		
		for (int i=0; i<nofGames; i++) {
			g.gameBoard.reverseMove();
		}
		g.gameBoard.printGameBoardToConsole();
		System.out.println(g.gameBoard.allPiecesOnTheBoard.size());
		System.out.println(g.gameBoard.pastMoves.size());
		
	}
	
	//play with AI
	public static void test4(int idOfTheAI, int nofRoundsToPlay, int searchDepth) {
		Game g = new EightPawnGame(6);
		Scanner sc = new Scanner(System.in);
		g.printGameSettingsToConsole();
		System.out.println("Eating enemy pieces 'may' be enabled.");
		
		if (idOfTheAI==0) {
			g.gameBoard.updateWithMove(g.getBestMoveForNextPlayer(searchDepth));
		}
		
		for (int i=0; i<nofRoundsToPlay; i++) {
			g.gameBoard.printGameBoardToConsole();
			System.out.println(Arrays.toString(g.getScores()));
			int xi = sc.nextInt();
			int yi = sc.nextInt();
			int xf = sc.nextInt();
			int yf = sc.nextInt();
			
			if (g.gameBoard.getPieceIn(xi, yi)==null) {
				System.out.println("ERROR: Invalid move, Enter another move.");
				i--;
				continue;
			}
			
			Move m = new Move(xi, yi, xf, yf);
			
			if (g.gameBoard.getPieceIn(xi, yi).getPlayerId()==idOfTheAI || !g.gameBoard.getAllMoves(g.gameBoard.getNextPlayerId()).contains(m)) {
				System.out.println("ERROR: Invalid move, Enter another move.");
				i--;
				continue;
			}
			
			g.gameBoard.updateWithMove(m);
			g.gameBoard.printGameBoardToConsole();
			System.out.println("Your score: " + g.getScores()[g.gameBoard.getNextPlayerId()]);
			Move bestMove = g.getBestMoveForNextPlayer(searchDepth);
			g.gameBoard.updateWithMove(bestMove);
		}
		sc.close();
	}
	
	//calls random search tree
	public static void test5() {
		Game g = new EightPawnGame(8);
		System.out.println();
		g.gameBoard.printGameBoardToConsole();
		System.out.println();
		g.runRandomMoveTree(5, 5, 0);
		g.gameBoard.printGameBoardToConsole();
	}
	
}
