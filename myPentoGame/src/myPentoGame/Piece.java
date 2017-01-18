package myPentoGame;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class Piece {
	
	int[] motherPiece;
	public Piece(int[] newpiece,int id){
		this.motherPiece= new int[newpiece.length+1];
		this.motherPiece[0]=id;
		for(int i=0;i<newpiece.length;i++){
			this.motherPiece[i+1]=newpiece[i];
		}
	}
	
	public int getPieceNum(){
		return this.motherPiece[0];
	}
	public int[] getCord(){
		int[] tmp= new int[this.motherPiece.length-1];
		for (int i=0;i<this.motherPiece.length-1;i++){
			tmp[i]=this.motherPiece[i+1];
		}
		return tmp;
	}
	public void setCord(int[] a){
		for (int i=0;i<this.motherPiece.length-1;i++){
			this.motherPiece[i+1]=a[i];
		}
		
	}
	public static int[] rotate(int[] a,int angle){
		double[] r = new double[a.length/2];
		double[] angles=new double[a.length/2];
		int[] rPiece=new int[a.length];
		int maxX=0;
		int maxY=0;
		for (int i=0;i<a.length/2;i++){
			r[i]=Math.sqrt(a[2*i]*a[2*i]+a[2*i+1]*a[2*i+1]);
			if(a[2*i]==0&a[2*i+1]>0)
			angles[i]=Math.PI*90/180;
			else if(a[2*i]==0&a[2*i+1]<0)
				angles[i]=Math.PI*270/180;
			else if(a[2*i]==0&a[2*i+1]==0)
				angles[i]=0;
			else 
				if(a[2*i]<0){
					double tt=(double)a[2*i+1]/a[2*i];
					angles[i]=Math.atan(tt)+Math.PI;
				}
				else{
					double tt=(double)a[2*i+1]/a[2*i];
					angles[i]=Math.atan(tt);
				}
			double newAngle= angles[i]+Math.PI*angle/180;
			rPiece[2*i]=(int) Math.round(r[i]*Math.cos(newAngle));
			rPiece[2*i+1]=(int) Math.round(r[i]*Math.sin(newAngle));
		}	
       return uniformPiece(rPiece);
	}
	
	
	public static int[] flip(int[] a){
		for (int i=0;i<a.length/2;i++){
			a[2*i]=-a[2*i];
		}
		return uniformPiece(a);
	}

	
	
	public static int[]  pieceMove(int[] piece ,int x, int y){
		for(int i=0; i<piece.length/2;i++){
			piece[2*i]=piece[2*i]+x;
			piece[2*i+1]=piece[2*i+1]+y;
		}
		return piece;
	}
	
	public static int[] uniformPiece(int[] piece){
		int[] myX=new int[piece.length/2];
		int[] myY=new int[piece.length/2];
		for (int i=0;i<myX.length;i++){
			myX[i]=piece[2*i];
			myY[i]=piece[2*i+1];
		}
		int[] myY1=myY.clone();
		Arrays.sort(myY1);
		Arrays.sort(myY1);
		ArrayList tmp=new ArrayList();
		for(int i=0;i<myX.length;i++){
			if(myY[i]==myY1[0])
				tmp.add(i);
		}
		int minX=Integer.MAX_VALUE;
		for(int i=0;i<tmp.size();i++){
			if(myX[(int)tmp.get(i)]<minX)
				minX=myX[(int)tmp.get(i)];
		}
		return(pieceMove(piece,-minX,-myY1[0]));
		
	}
	public static boolean isSame(int[]a, int[] b){
		
		int s=0;
		if(a[0]!=b[0])
			return false;
		else{
			for (int i=1;i<(a.length+1)/2;i++){
				for(int j=1;j<(a.length+1)/2;j++){
					if(a[2*i-1]==b[2*j-1] &a[2*i]==b[2*j] )
						s++;
				}
			}		
		}
		if(s==(a.length-1)/2)
			return true;
		else 
			return false;
		
	}
	
}


