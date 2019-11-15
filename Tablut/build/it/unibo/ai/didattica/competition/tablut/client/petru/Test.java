package it.unibo.ai.didattica.competition.tablut.client.petru;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

public class Test {
	
	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void printBoard(int[][] b) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(b[i][j]);
			}
			System.out.println("\n");
		}
	}
	
	public static void main(String[] args) {
		StateTablut s = new StateTablut();
		//System.out.println(Test.printBoard(s.getCurrentState()));
		
	}
}
