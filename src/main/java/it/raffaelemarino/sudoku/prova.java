package it.raffaelemarino.sudoku;

import java.util.ArrayList;
import java.util.Random;

public class prova {
	
	//classe utilizzata solo per provare il funzionamento di creazione sudoku intero
	//e dal sudoku intero togliere qualche numero in modo randomico

	public static void main(String[] args) {

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

		
		int[][] campo_iniziale = a;
		//tolgo alcuni numeri al campo di gioco per poterlo dare ai giocatori
		//i numeri nel sudoku vanno da 1 a 9, piazzo uno 0 dove devo "nascondere il numero"
		Random random = new Random();

		
		for(int i=0; i<9;i++) {
			for(int j=0;j<9;j++) {
				
				if(random.nextBoolean()) {
					campo_iniziale[i][j]=0;
				}
				System.out.print(campo_iniziale[i][j]+"   ");
			}
			System.out.println();
		}
		
		
		ArrayList<String> s = new ArrayList<String>();
		for(int i = 0; i<10; i++) {
			s.add(""+random.nextDouble());
			System.out.println("aggiugno "+s.get(i));
		}
		
		while(!s.isEmpty()) {
			System.out.println("rimuovo "+s.get(0));
			s.remove(0);
			
		}

		
		
	}

}
