package myPentoGame;

import java.util.ArrayList;

public class PentoDLXGame {
	Node h;
	int cnt;
	int rows;
	int cols;
	ArrayList steps = new ArrayList();
	ArrayList solutions = new ArrayList();
	int[][] targetMatrix;
	Node[][] targetDoubleLink;
	int[][] allPieces;
	int[][] board;

	public PentoDLXGame(int[][] allPieces, int row, int col, int[][] board) {

		h = new Node();
		cnt = 0;
		int choices = 0;
		this.rows = row;
		this.cols = col;
		this.allPieces = allPieces;
		this.board = board;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < allPieces.length; k++) {
					if (putPiece(i, j, k, this.board))
						choices++;
				}
			}
		}

		this.targetMatrix = new int[choices][row * col + 12];
		this.targetDoubleLink = new Node[choices + 1][row * col + 12];

		for (int i = 0; i < choices; i++) {
			for (int j = 0; j < (row * col + 12); j++) {
				targetMatrix[i][j] = 0;
			}
		}

		for (int i = 0; i < choices + 1; i++) {
			for (int j = 0; j < (row * col + 12); j++) {
				targetDoubleLink[i][j] = new Node();
				targetDoubleLink[i][j].row = i-1;
				targetDoubleLink[i][j].col=j;
				if (i > 0) {
					targetDoubleLink[i][j].C = targetDoubleLink[0][j];
				}
			}
		}

		int label = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < allPieces.length; k++) {
					if (putPiece(i, j, k, this.board)) {
						int[] piece = allPieces[k];
						for (int l = 1; l < piece.length; l += 2) {
							targetMatrix[label][(i + piece[l + 1]) * col + j + piece[l]] = 1;
						}
						targetMatrix[label][row * col + piece[0] - 1] = 1;
						label++;
					}
				}
			}
		}
		for (int i = 0; i < choices; i++) {

			int preLabel = -1;
			int curLabel = -1;
			int firstLabel = -1;

			for (int j = 0; j < (row*col+12); j++) {
				if (targetMatrix[i][j] == 1) {
					preLabel = curLabel;
					curLabel = j;
					if (preLabel > -1) {
						targetDoubleLink[i + 1][j].L = targetDoubleLink[i + 1][preLabel];
						targetDoubleLink[i + 1][preLabel].R = targetDoubleLink[i + 1][j];
					} else
						firstLabel = j;

				}
			}
			targetDoubleLink[i + 1][firstLabel].L = targetDoubleLink[i + 1][curLabel];
			targetDoubleLink[i + 1][curLabel].R = targetDoubleLink[i + 1][firstLabel];
		}

		for (int j = 0; j < (row*col+12); j++) {

			int preLabel = -1;
			int curLabel = -1;
			int firstLabel = -1;

			for (int i = 0; i < choices; i++) {
				if (targetMatrix[i][j] == 1) {
					preLabel = curLabel;
					curLabel = i;
					if (preLabel > -1) {
						targetDoubleLink[curLabel + 1][j].U = targetDoubleLink[preLabel + 1][j];
						targetDoubleLink[preLabel + 1][j].D = targetDoubleLink[curLabel + 1][j];
					} else
						firstLabel = i;

				}
			}
			targetDoubleLink[firstLabel + 1][j].U = targetDoubleLink[0][j];
			targetDoubleLink[0][j].D = targetDoubleLink[firstLabel + 1][j];
			targetDoubleLink[curLabel + 1][j].D = targetDoubleLink[0][j];
			targetDoubleLink[0][j].U = targetDoubleLink[curLabel + 1][j];
		}

		for (int i = 1; i < targetMatrix[0].length; i++) {
			targetDoubleLink[0][i].L = targetDoubleLink[0][i - 1];
			targetDoubleLink[0][i - 1].R = targetDoubleLink[0][i];
		}
		targetDoubleLink[0][0].L = this.h;
		targetDoubleLink[0][targetMatrix[0].length - 1].R = this.h;
		this.h.L = targetDoubleLink[0][targetMatrix[0].length - 1];
		this.h.R = targetDoubleLink[0][0];
		
	}
	int count=0;

	public void search(int k) {
		this.count ++;
//		for(Node t=h.R;t!=this.h;t=t.R){
//			if(t.R.L!=t)
//				System.out.print(1);
//		}
		
		if (this.h.R == this.h) {
			cnt++;
			int[] solution =new int[this.steps.size()];
			for(int ii=0;ii<solution.length;ii++){
				solution[ii] = (int)this.steps.get(ii);
			}
			this.solutions.add(solution);
		
			
		} else {
			int min=1000000;
			Node c =new Node();
			for(Node start=this.h.R;start!=this.h;start=start.R){
				int i=0;
				for(Node row=start.D;row!=start;row=row.D){
					i++;
				}
				if(i<min){
					min=i;
					c =start;
				}
			}
			if (min==0)
					return;
			cover(c);
			for (Node r = c.D; r != c; r = r.D) {

				this.steps.add(r.row);
				for (Node j = r.R; j != r; j = j.R) {
					cover(j.C);
				}

				search(k++);
				
				steps.remove(steps.get(steps.size()-1));
				for (Node j = r.L; j != r; j = j.L) {
					uncover(j.C);
				}
			}
			uncover(c);
		}
	}

	public static void cover(Node c) {
		c.R.L = c.L;
		c.L.R = c.R;
		for (Node i = c.D; i != c; i = i.D) {
			for (Node j = i.R; i != j; j = j.R) {
				j.D.U = j.U;
				j.U.D = j.D;
			}
		}
	}

	public static void uncover(Node c) {
		for (Node i = c.U; i != c; i = i.U) {
			for (Node j = i.L; i != j; j = j.L) {
				j.D.U = j;
				j.U.D = j;
			}
		}
		c.R.L = c;
		c.L.R = c;

	}

	public boolean putPiece(int row, int col, int p, int[][] board) {

		for (int i = 1; i < this.allPieces[p].length; i += 2) {
			if (col + this.allPieces[p][i] < 0 
					|| col + this.allPieces[p][i] >= this.cols
					|| row + this.allPieces[p][i + 1] < 0
					|| row + this.allPieces[p][i + 1] >= this.rows)
				return false;
			if (board[row][col] == 1)
				return false;
		}
		return true;
	}

	class Node {

		int row = -1;
		int col=-1;
		Node C;
		Node L;
		Node R;
		Node U;
		Node D;
	}

	
	public boolean check(){
		int s=0;
		for(Node col=this.h.R; col!=this.h; col=col.R){
			for(Node row=col.D; row!=col;row=row.D){
				if(this.targetMatrix[row.row][row.col]!=1){
				
					return false;
				}
			s++;
			}
		}
		int sum=0;
		for(int i=0;i<this.targetMatrix.length;i++){
			for(int j=0;j<72;j++){
				sum+=this.targetMatrix[i][j];
			}
		}
		if(s==sum)
			return true;
		else
			return false;
	}
	
	
	public ArrayList  convertToMatrix(){
		ArrayList solutionsMatrix=new ArrayList();
		for(int i=0;i<this.solutions.size()-1;i++){
			int[][] solution =new int[this.rows][this.cols];
			int[] aSteps= (int[])this.solutions.get(i);
			
			for(int j=0;j<aSteps.length;j++){
				int whichPiece=0;
				int row=(int)aSteps[j];
				for(int k=60;k<72;k++){
					if(this.targetMatrix[row][k]==1){
					whichPiece=k+1-60;
					break;
					}
				}
				
				for(int l=0;l<60;l++){
					if(this.targetMatrix[row][l]==1){
						int coltmp = l%(this.cols);
						int rowtmp = (int)(((double)l)/(this.cols));
						solution[rowtmp][(int)coltmp]=whichPiece;
					}
				}
			}
			solutionsMatrix.add(solution);
			
		}
		return solutionsMatrix;
	}
	
	
	
	
	
	public static void main(String[] args) {
		


	}

}