package it.raffaelemarino.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
		return this.campo_di_gioco_completo;
	}

	public void setCampo_di_gioco_completo(Integer[][] campo_di_gioco_completo) {
		this.campo_di_gioco_completo = campo_di_gioco_completo;
	}

	public Integer[][] getCampo_di_gioco_iniziale() {
		
		Integer[][] temp = new Integer[9][9];
		for(int i=0; i<9; i++)
			temp[i] = this.campo_di_gioco_iniziale[i].clone();
		return temp;
		
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
	
	private Integer[][] generaGiocoIniziale(Integer[][] sudoku) {//DA FARE
		Integer[][] campo_iniziale = new Integer[9][9];
		
		for(int i=0; i<9; i++)
			campo_iniziale[i] = sudoku[i].clone();
		
		//tolgo alcuni numeri al campo di gioco per poterlo dare ai giocatori
		//i numeri nel sudoku vanno da 1 a 9, piazzo uno 0 dove devo "nascondere il numero"
		Random random = new Random();

		
		for(int i=0; i<9;i++) {
			for(int j=0;j<9;j++) {
				
				if(random.nextBoolean()) {
					campo_iniziale[i][j]=0;
				}
				
			}
		}
		
		
		return campo_iniziale;
	}

	public int controllaNumeroPiazzato(int i,int j,int valore) {
		
		//se è corretto e il valore nel campo di gioco dove giocano i giocatori è 0 allora dai 1 punto
		if(this.campo_di_gioco_completo[i][j]==valore && this.campo_di_gioco_iniziale[i][j]==0)
			return 1;
		//se è corretto il valore ma nel campo di gioco dove giocano i giocatori c'era già il valore allora dai 0 punti
		if(this.campo_di_gioco_completo[i][j]==valore && this.campo_di_gioco_iniziale[i][j]==valore)
			return 0;
		
		//-1 altrimenti, vuol dire che non era corretto il valore
		return -1;
	}
	
	public void aggiornaCampoDiGioco(int i,int j,int valore) {
		
		this.campo_di_gioco_iniziale[i][j]=valore;
	}
	
	
	public boolean isPeerInGame(PeerAddress peer) {
		
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
	
	public Giocatore getGiocatoreByPeer(PeerAddress peer) {
		for(Giocatore g: this.giocatori) {
			if(g.getPeerAddres().equals(peer))
				return g;
		}
		
		return null;
	}
	
	public void aggiungiGiocatore(Giocatore g) {
		this.giocatori.add(g);
		
	}
	
	public void rimuoviGiocatore(Giocatore g) {
		this.giocatori.remove(g);
		
	}
	
	public void aggiornaListaGiocatori(Giocatore g) {
		if(this.giocatori.remove(g))
			this.giocatori.add(g);
	}
	
	
	//controllo se tutte le caselle hanno tutti i numeri
	public boolean isFinish() {
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(this.campo_di_gioco_completo[i][j]!=this.campo_di_gioco_iniziale[i][j])
					return false;
			}
		}
		
		return true;
	}
	
	public String getScoreboard() {
		
		String s="";
		
		for(Giocatore g: this.giocatori)
			s+=g.getNick()+" ha punteggio: "+g.getPunteggio() +"\n";
		
		return s;
	}
	
	
}
