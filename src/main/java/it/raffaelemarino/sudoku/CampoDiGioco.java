package it.raffaelemarino.sudoku;

import java.io.Serializable;
import java.util.ArrayList;

import net.tomp2p.peers.PeerAddress;

public class CampoDiGioco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//campo di gioco completo
	private Integer[][] campo_di_gioco_completo;
	 
	//campo di gioco che viene mostrato all'inizio ai giocatori, quindi privo di qualche numero
	private Integer[][] campo_di_gioco_iniziale;
	
	//lista giocatori
	ArrayList<Giocatore> giocatori;
	
	
	
	public CampoDiGioco() {
		
		this.campo_di_gioco_completo = generaCampoDiGioco();
		this.campo_di_gioco_iniziale = generaGiocoIniziale(campo_di_gioco_completo);
		
	}
	
	
	
	public ArrayList<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(ArrayList<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public Integer[][] getCampo_di_gioco_completo() {
		return campo_di_gioco_completo;
	}

	public void setCampo_di_gioco_completo(Integer[][] campo_di_gioco_completo) {
		this.campo_di_gioco_completo = campo_di_gioco_completo;
	}

	public Integer[][] getCampo_di_gioco_iniziale() {
		return campo_di_gioco_iniziale;
	}

	public void setCampo_di_gioco_iniziale(Integer[][] campo_di_gioco_iniziale) {
		this.campo_di_gioco_iniziale = campo_di_gioco_iniziale;
	}

	private Integer[][] generaCampoDiGioco() {
		Integer[][] sudoku = new Integer[9][9];
		
		
		//creaa il seguente campo di gioco
		/*    1  2  3  4  5  6  7  8  9
			  4  5  6  7  8  9  1  2  3
			  7  8  9  1  2  3  4  5  6
			  2  3  4  5  6  7  8  9  1
			  5  6  7  8  9  1  2  3  4
			  8  9  1  2  3  4  5  6  7
			  3  4  5  6  7  8  9  1  2
			  6  7  8  9  1  2  3  4  5
			  9  1  2  3  4  5  6  7  8    */
		int k=0;
		int fillCount =1;
		int subGrid=1;
		int N=3;
		
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
					sudoku[i][j]=fillCount;
					fillCount=1;

				}else{
					sudoku[i][j]=fillCount++;

				}
			}

		}
		
		
		return sudoku;
	}
	
	private Integer[][] generaGiocoIniziale(Integer[][] sudoku) {
		Integer[][] campo_iniziale = new Integer[9][9];
		//tolgo alcuni numeri al campo di gioco per poterlo dare ai giocatori
		
		
		return campo_iniziale;
	}

	
	
	public boolean isPeerInGame(Integer peer) {
		
		for(Giocatore g: this.giocatori){
			if(g.getPeerAddres().equals(peer))
				return true;
		}
		return false;
	}
	
	public boolean isNickInGame(String nick) {
		
		for(Giocatore g: this.giocatori) {
			if(g.getNick().equals(nick))
				return true;
		}
		
		return false;
	}
	
	public Giocatore getGiocatoreByPeer(Integer peer) {
		for(Giocatore g: this.giocatori) {
			if(g.getPeerAddres().equals(peer))
				return g;
		}
		
		return null;
	}
	
	public void aggiungiGiocatore(Giocatore g) {
		this.giocatori.add(g);
		
	}
	
}
