package it.raffaelemarino.sudoku;

public class prova {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int k=0;
		int fillCount =1;
		int subGrid=1;
		int N=3;
		int[][] a=new int[N*N][N*N];
		for (int i=0;i<N*N;i++){
			if(k==N){
				k=1;
				subGrid++;
				fillCount=subGrid;
			}else{
				k++;
				if(i!=0)
					fillCount=fillCount+N;
			}
			for(int j=0;j<N*N;j++){
				if(fillCount==N*N){
					a[i][j]=fillCount;
					fillCount=1;

				}else{
					a[i][j]=fillCount++;

				}
			}

		}

	}

}
