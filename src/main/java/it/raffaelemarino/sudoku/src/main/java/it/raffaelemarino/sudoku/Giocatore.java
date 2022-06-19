package it.raffaelemarino.sudoku;

import java.io.Serializable;
import java.util.ArrayList;

import net.tomp2p.peers.PeerAddress;

public class Giocatore implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nick;
	private PeerAddress peerAddres;
	private Integer peerID;
	private Integer punteggio;
	private Integer[][] giocoGiocatore;
	private ArrayList<CampoDiGioco> giochi;

	public Giocatore() {		this.giochi=new ArrayList<CampoDiGioco>();
	};


	public Giocatore(String nick, PeerAddress peerAddres, Integer peerID, Integer punteggio, Integer[][] giocoGiocatore) {
		this.nick = nick;
		this.peerAddres = peerAddres;
		this.peerID = peerID;
		this.punteggio = punteggio;
		this.giocoGiocatore = giocoGiocatore;
		this.giochi=new ArrayList<CampoDiGioco>();
	}


	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public PeerAddress getPeerAddres() {
		return peerAddres;
	}

	public void setPeerAddres(PeerAddress peerAddres) {
		this.peerAddres = peerAddres;
	}

	public Integer getPeerID() {
		return peerID;
	}

	public void setPeerID(Integer peerID) {
		this.peerID = peerID;
	}

	public Integer getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(Integer punteggio) {
		this.punteggio = punteggio;
	}

	public Integer[][] getGiocoGiocatore() {
		return giocoGiocatore;
	}

	public void setGiocoGiocatore(Integer[][] giocodelGiocatore) {

		
		this.giocoGiocatore=giocodelGiocatore;
		//for(int i=0; i<9; i++) {this.giocoGiocatore[i] = giocodelGiocatore[i].clone();}

	}

	public String stampaGiocoDelGiocatore() {
		String s="";
		
		for(int i =0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print(" "+this.giocoGiocatore[i][j]);
			}
			
			System.out.println("\n");
		}
		
		return s;
	}
	
	public void setId(Integer id) {
		this.peerID=id;

	}

	public ArrayList<CampoDiGioco> getListaGiochi(){

		return giochi;
	}

	public void addGiocoAGiocatore(CampoDiGioco g) {
		giochi.add(g);

	}

	public void removeGiocoDaGiocatore(CampoDiGioco g) {
		
		giochi.remove(g);
		
	}
}
