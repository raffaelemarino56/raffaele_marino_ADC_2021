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
	
	public Giocatore() {};
	
	
	public Giocatore(String nick, PeerAddress peerAddres, Integer peerID, Integer punteggio, Integer[][] giocoGiocatore) {
		this.nick = nick;
		this.peerAddres = peerAddres;
		this.peerID = peerID;
		this.punteggio = punteggio;
		this.giocoGiocatore = giocoGiocatore;
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
	public void setGiocoGiocatore(Integer[][] giocoGiocatore) {
		this.giocoGiocatore = giocoGiocatore;
	}

	public ArrayList<CampoDiGioco> getListaGiochi(){
		
		return giochi;
	}
	
	public boolean addGiocoAGiocatore(CampoDiGioco g) {
		if(giochi.add(g))
			return true;
		return false;
	}
	
	public void removeGiocoDaGiocatore(CampoDiGioco g) {
		giochi.remove(g);
	}
}
