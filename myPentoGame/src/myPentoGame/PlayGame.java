package myPentoGame;

import java.util.ArrayList;

public class PlayGame {
	int rows=0;
	int cols=0;
	int[][] board;
	int[][] allPiece;
	int[] usedPieces;
	int numUsed=0;
	int steps=0;
	ArrayList outPuts =new ArrayList();
	ArrayList taskList=new ArrayList();

	public PlayGame(int row,int col,int[][] allPieces){
		this.rows=row-1;
		this.cols=col-1;
		this.board= new int[row][col];
		this.allPiece=allPieces;
		this.usedPieces=new int[12];
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				this.board[i][j]=0;
			}
		}
		for(int i=0;i<12;i++){
			this.usedPieces[i]=0;
			
		}
	}
	
	public boolean putPiece(int row,int col,int p){
		
        if (this.board[row][col] != 0)
            return false;
         for (int i = 1; i < this.allPiece[p].length; i += 2) {
            if (row+this.allPiece[p][i] < 0 || row+this.allPiece[p][i] > rows || col+this.allPiece[p][i+1] < 0 || col+this.allPiece[p][i+1] > cols)
               return false;
            else if (this.board[row+this.allPiece[p][i]][col+this.allPiece[p][i+1]] != 0)  
               return false;
         }


         for(int i=1;i<this.allPiece[p].length;i+=2){
        	 this.board[row+this.allPiece[p][i]][col+this.allPiece[p][i+1]]=this.allPiece[p][0];
         }
        this.usedPieces[this.allPiece[p][0]-1]=1;
        this.numUsed++;
        this.taskList.add(new int[]{row,col,p});
         return true;
	}
	
	public void removePiece(int row, int col ,int p){

		for(int i=1;i<this.allPiece[p].length;i+=2){
       	 this.board[row+this.allPiece[p][i]][col+this.allPiece[p][i+1]]=0;
        }
		this.usedPieces[this.allPiece[p][0]-1]=0;
		this.numUsed--;
		this.taskList.remove(this.taskList.size()-1);  
	}
	public void boardPrint(){
		for(int i=0;i<rows+1;i++){
			System.out.println(java.util.Arrays.toString(this.board[i]));
		}
	}
	
	public void nextStep(int row, int col){
		

		for(int i=0;i<this.allPiece.length;i++){
			
			if(usedPieces[this.allPiece[i][0]-1]==1)
				continue;
			else if(!this.putPiece(row, col, i))
				continue;

			steps++;
			

			if(this.numUsed==12){
				int[][] tmpboard =new int[this.board.length][this.board[0].length];
				for(int ii=0;ii<this.board.length;ii++){
					tmpboard[ii]=this.board[ii].clone();
				}
				this.outPuts.add(tmpboard);
				this.removePiece(row, col, i);
			
			}
			else{
				int[] tmp=this.bottomLeft();
				this.nextStep(tmp[0],tmp[1]);
			}

			}
		if(this.numUsed==0)
			return;
		
		else{
		int[] tmp =(int[])this.taskList.get(this.taskList.size()-1);
		this.removePiece(tmp[0], tmp[1], tmp[2]);
		}
		
	}
	
	
	public int[] bottomLeft(){
		int x=100000;
		int y=1000000;
		int i=0;
		while(y>i&i<=this.cols){
			for(int j=0;j<rows+1;j++){
				if(this.board[j][i]==0){
					y=i;
					x=j;
				return	new int[]{x,y};
				}}
				i++;	
			
		}
		return new int[]{x,y};
	}
	public static int[][] rotateMatrixLeft(int[][] matrix)
	{
	   
	    int w = matrix.length;
	    int h = matrix[0].length;   
	    int[][] ret = new int[h][w];
	    for (int i = 0; i <  h; ++i) {
	        for (int j = 0; j <  w; ++j) {
	            ret[i][j] = matrix[j][h - i - 1];
	        }
	    }
	    return ret;
	}
	
	public static int[][] reverseRows(int[][] inTwoDArray)
	{
		int[][] outMatrix=new int[inTwoDArray.length][inTwoDArray[0].length];
		for(int j = 0; j < inTwoDArray.length; j++){
		    for(int i = 0; i < inTwoDArray[j].length / 2; i++) {
		        outMatrix[j][i] = inTwoDArray[j][inTwoDArray[j].length - i - 1];
		        outMatrix[j][inTwoDArray[j].length - i - 1] = inTwoDArray[j][i];
		    }
		}
		return outMatrix;
	}
	
	public static ArrayList findAllPieces(){
		ArrayList pieces= new ArrayList();
		pieces.add(new Piece(new int[]{ 0,0,0,1,0,2,0,3,0,4 },1));
		pieces.add(new Piece(new int[]{ 0,0,1,-1,1,0,1,1,2,0 },2));
		pieces.add(new Piece(new int[]{ 0,0,0,1,1,0,2,-1,2,0 },3));
		pieces.add(new Piece(new int[]{ 0,0,1,0,2,0,2,1,2,2 },4));
		pieces.add(new Piece(new int[]{ 0,0,0,1,0,2,1,1,2,1 },5));
		pieces.add(new Piece(new int[]{ 0,0,1,0,1,1,2,1,2,2 },6));
		pieces.add(new Piece(new int[]{ 0,0,0,1,0,2,1,0,1,2 },7));
		pieces.add(new Piece(new int[]{ 0,0,1,0,1,1,1,2,1,3 },8));
		pieces.add(new Piece(new int[]{ 0,0,0,1,1,-2,1,-1,1,0 },9));
		pieces.add(new Piece(new int[]{ 0,0,1,-2,1,-1,1,0,1,1 },10));
		pieces.add(new Piece(new int[]{ 0,0,1,-1,1,0,1,1,2,1 },11));
		pieces.add(new Piece(new int[]{ 0,0,0,1,1,0,1,1,2,1 },12));
	
		
		ArrayList allPieces = new ArrayList();

		
		for (int i=0;i<12;i++){
			ArrayList pieceGroup =new ArrayList();
			Piece p1=(Piece)pieces.get(i);
			p1.setCord(p1.uniformPiece(p1.getCord()));
			pieceGroup.add(p1);
			
			for(int j=0;j<3;j++){
				Piece pTmp=new Piece(p1.rotate(p1.getCord(),(j+1)*90),p1.getPieceNum());
				int label=0;
				for(int ii=0;ii<pieceGroup.size();ii++){
					if(!(pTmp.isSame(((Piece)pieceGroup.get(ii)).motherPiece, pTmp.motherPiece)))
						label++;
				}
				if(label==pieceGroup.size())
					pieceGroup.add(pTmp);
			}
			
			Piece p2=new Piece(p1.flip(p1.getCord()),p1.getPieceNum());
			int label1=0;
			for(int ii=0;ii<pieceGroup.size();ii++){
				if(!(p2.isSame(((Piece)pieceGroup.get(ii)).motherPiece, p2.motherPiece)))
					label1++;
			}
			if(label1==pieceGroup.size()){
				pieceGroup.add(p2);
				for(int j=0;j<3;j++){
					Piece pTmp=new Piece(p2.rotate(p2.getCord(),(j+1)*90),p2.getPieceNum());
					int label12=0;
					for(int ii=0;ii<pieceGroup.size();ii++){
						if(!(pTmp.isSame(((Piece)pieceGroup.get(ii)).motherPiece, pTmp.motherPiece)))
							label12++;
				}
					if(label12==pieceGroup.size())
						pieceGroup.add(pTmp);
			}
			}
			for(int ii=0;ii<pieceGroup.size();ii++){
				allPieces.add(pieceGroup.get(ii));
			}
		}
		return allPieces;
	}
	
	public static boolean isTheSame(int[][] a,int[][] b){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				if(a[i][j]!=b[i][j])
					return false;
			}
		}
		return true;
	}
	public static ArrayList uniqueMatrices(ArrayList matrices){
		ArrayList uniqueMat=new ArrayList();
		uniqueMat.add((int[][])matrices.get(0));
		for(int i=1;i<matrices.size();i++){
			int[][] initalM= (int[][])matrices.get(i);
			int[][] rotateM= rotateMatrixLeft(rotateMatrixLeft((int[][])matrices.get(i)));
			int[][] reverseM = reverseRows((int[][])matrices.get(i));
			int[][] rotateRerverseM=rotateMatrixLeft(rotateMatrixLeft(reverseM));

			int haha=0;
			for(int j=0;j<uniqueMat.size();j++){
				if(!isTheSame((int[][])uniqueMat.get(j),initalM) & !isTheSame((int[][])uniqueMat.get(j),rotateM) & !isTheSame((int[][])uniqueMat.get(j),reverseM) &!isTheSame((int[][])uniqueMat.get(j),rotateRerverseM))
					haha++;
			}
			if(haha==uniqueMat.size())
				uniqueMat.add(initalM);
		}
		return uniqueMat;
	}
	
	
	
	
	
	
}
