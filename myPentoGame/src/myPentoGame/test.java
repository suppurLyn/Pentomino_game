package myPentoGame;

import java.util.ArrayList;

import myPentoGame.PentoDLXGame.Node;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ArrayList allPieces=PlayGame.findAllPieces();
		int[][] allPieces1=new int[allPieces.size()][11];
		for(int i =0;i<allPieces.size();i++){
			allPieces1[i]=((Piece)allPieces.get(i)).motherPiece;
		}


		PlayGame pg=new PlayGame(3,20,allPieces1);
		pg.nextStep(0, 0);
		ArrayList uniqueSolutions1 = pg.uniqueMatrices(pg.outPuts);
		

		PlayGame pg1= new PlayGame(20,3,allPieces1);
		PentoDLXGame pg2=new PentoDLXGame(allPieces1,20,3,pg1.board);
		pg2.search(0);
		ArrayList solutionMatrice= pg2.convertToMatrix();
		ArrayList uniqueSolutions2= PlayGame.uniqueMatrices(solutionMatrice);
				
		System.out.println("the two solutions solved by method 1(backtracking) is :");
		
		for(int i=0;i<((int[][])uniqueSolutions1.get(0)).length;i++){
			System.out.println(java.util.Arrays.toString(((int[][])uniqueSolutions1.get(0))[i]));
		}
		System.out.println("##########");
		
		for(int i=0;i<((int[][])uniqueSolutions1.get(1)).length;i++){
			System.out.println(java.util.Arrays.toString(((int[][])uniqueSolutions1.get(1))[i]));
		}
		System.out.println("   ");
		System.out.println("the two solutions solved by dancing links algorithm is :");
		
		for(int i=0;i<((int[][])uniqueSolutions2.get(0)).length;i++){
			System.out.println(java.util.Arrays.toString(((int[][])uniqueSolutions2.get(0))[i]));
		}
		System.out.println("##########");
		
		for(int i=0;i<((int[][])uniqueSolutions2.get(1)).length;i++){
			System.out.println(java.util.Arrays.toString(((int[][])uniqueSolutions2.get(1))[i]));
		}
		
		
				
		
		System.out.print("done");
		

//		for(int i=0;i<bb.length;i++){
//			System.out.println(java.util.Arrays.toString(bb[i]));
//		}
//		int[][] bbb=pg1.rotateMatrixLeft(bb);
//		int[][] ccc=pg1.reverseRows(bbb);
//		for(int i=0;i<ccc.length;i++){
//			System.out.println(java.util.Arrays.toString(ccc[i]));
//		}
//		
//		System.out.print("done");
	}
}
