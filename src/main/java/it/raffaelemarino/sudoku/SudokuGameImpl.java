package it.raffaelemarino.sudoku;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class SudokuGameImpl implements SudokuGame{

	final private Peer peer;
	final private PeerDHT _dht;
	final private int DEFAULT_MASTER_PORT=4000;
	private Integer id;

	final private ArrayList<String> s_topics=new ArrayList<String>();


	public SudokuGameImpl(int _id, String _master_peer, final MessageListenerImpl _listener) throws Exception {

		this.id=_id;

		peer= new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT+_id).start();
		_dht = new PeerBuilderDHT(peer).start();

		FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(_master_peer)).ports(DEFAULT_MASTER_PORT).start();
		fb.awaitUninterruptibly();
		if(fb.isSuccess()) {
			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		}else {
			throw new Exception("Error in master peer bootstrap.");
		}

		peer.objectDataReply(new ObjectDataReply() {

			public Object reply(PeerAddress sender, Object request) throws Exception {
				return _listener.parseMessage(request);
			}
		});

	}

	//genera nuova partita dato un nome
	public Integer[][] generateNewSudoku(String _game_name) {
		// TODO Auto-generated method stub

		try {

			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start().awaitUninterruptibly();
			//controllo se esiste il gioco con quel nome
			if (futureGet.isSuccess() && futureGet.isEmpty()) {

				CampoDiGioco gioco = new CampoDiGioco();
				//per _game_name crea il campo da gioco che do a new Data() ovviamente tutto l'oggetto
				_dht.put(Number160.createHash(_game_name)).data(new Data(gioco)).start().awaitUninterruptibly();
				//return della matrice che ho creato

				return gioco.getCampo_di_gioco_iniziale();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//entra in una partita (avvisa i giocatori in partita che sei entrato nella partita)
	public boolean join(String _game_name, String _nickname) {
		// TODO Auto-generated method stub

		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start().awaitUninterruptibly();
			//controllo se esiste il gioco con quel nome
			if (futureGet.isSuccess() && !futureGet.isEmpty()) { 
				//recupero il gioco, che contiene il campo e i giocatori
				CampoDiGioco gioco = (CampoDiGioco) futureGet.dataMap().values().iterator().next().object();


				//controllo se sono già in gioco o già esiste quel nickcanme
				if(gioco.isNickInGame(_nickname) || gioco.isPeerInGame(this.id))
					return false;

				
				Giocatore giocatore = new Giocatore(_nickname,this.peer.peerAddress(),id,0,gioco.getCampo_di_gioco_iniziale() );
				
				//devo aggiungere questo gioco alla lista dei giochi a cui il giocatore partecipa, perchè può partecipare a più giochi
				if(giocatore.addGiocoAGiocatore(gioco)) {
					//mi aggiungo alla lista giocatori di quel gioco
					gioco.aggiungiGiocatore(giocatore);

					//aggiorno lo stato
					_dht.put(Number160.createHash(_game_name)).data(new Data(gioco)).start().awaitUninterruptibly();
					
					//notifico a tutti i giocatori in quella partita l'accesso del nuovo giocatore
					for(Giocatore g : gioco.getGiocatori()) {
						FutureDirect futureDirect = _dht.peer().sendDirect(g.getPeerAddres()).object("giocatore" + _nickname + "è entrato in partita").start().awaitUninterruptibly();
					}
				}
				
				
			}else {
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}
	
	

	//visualizza stato di una partita
	public Integer[][] getSudoku(String _game_name) {
		// TODO Auto-generated method stub
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start().awaitUninterruptibly();

			//verifico se esiste
			if (futureGet.isSuccess() && !futureGet.isEmpty()) { 
				CampoDiGioco gioco = (CampoDiGioco) futureGet.dataMap().values().iterator().next().object();

				//recupero le info del giocatore con id se esiste
				Giocatore giocatore = gioco.getGiocatoreByPeer(id);

				if(giocatore != null)
					return giocatore.getGiocoGiocatore();


			}
		}catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	//piazza numero in una partita (avvisa giocatori che hai piazzato il numero e il punteggio)
	public Integer placeNumber(String _game_name, int _i, int _j, int _number) {
		// TODO Auto-generated method stub

		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start().awaitUninterruptibly();

			//verifico se esiste
			if (futureGet.isSuccess() && !futureGet.isEmpty()) { 
				CampoDiGioco gioco = (CampoDiGioco) futureGet.dataMap().values().iterator().next().object();


				//recupero il campo di gioco relativo al _game_name del giocatore con id se esiste
				Giocatore giocatore = gioco.getGiocatoreByPeer(id);
				if(giocatore == null)
					return null;

				//piazza numero e ottieni il punteggio
				int punto=gioco.controllaNumeroPiazzato(_i, _j, _number);

				//aggiorno puntegigo giocatore
				giocatore.setPunteggio(giocatore.getPunteggio()+punto);

				//aggiorno lista giocatori nel gioco
				gioco.aggiornaListaGiocatori(giocatore);

				//aggiorno lo stato
				_dht.put(Number160.createHash(_game_name)).data(new Data(gioco)).start().awaitUninterruptibly();


				//notifico a tutti i giocatori in quella partita che ho piazzato il numero e il mio punteggio
				for(Giocatore g : gioco.getGiocatori()) {
					FutureDirect futureDirect = _dht.peer().sendDirect(g.getPeerAddres()).object("giocatore" + giocatore.getNick() + "ha messo il numero" + _number +" in posizione i:"+_i+" j:"+_j+"ed ha punteggio:"+giocatore.getPunteggio()).start().awaitUninterruptibly();
					
					//se il gioco è finito lo notifico a tutti e mostro la scoreboard
					if(gioco.isFinish()) {
						FutureDirect futureDirect2= _dht.peer().sendDirect(g.getPeerAddres()).object("il gioco è finito!!!, questa è la scoreboard: "+gioco.getScoreboard()).start().awaitUninterruptibly();
					}
				}
			}
		}catch(Exception e) {
			// TODO: handle exception

		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public boolean leveGame(String _game_name) {
		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess()) {
				if(futureGet.isEmpty() ) return false;
				HashSet<PeerAddress> peers_on_topic;
				peers_on_topic = (HashSet<PeerAddress>) futureGet.dataMap().values().iterator().next().object();
				peers_on_topic.remove(_dht.peer().peerAddress());
				_dht.put(Number160.createHash(_game_name)).data(new Data(peers_on_topic)).start().awaitUninterruptibly();
				s_topics.remove(_game_name);
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean leaveNetwork() {
		//deve lasciare tutte le partite nelle quali è in gioco
		for(String topic: new ArrayList<String>(s_topics)) 
			leveGame(topic);
		_dht.peer().announceShutdown().start().awaitUninterruptibly();
		return true;
	}

}
