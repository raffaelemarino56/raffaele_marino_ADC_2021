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

	final private ArrayList<String> s_topics=new ArrayList<String>();


	public SudokuGameImpl(int _id, String _master, final MessageListenerImpl _peerid) throws Exception {
		peer= new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT+_id).start();
		_dht = new PeerBuilderDHT(peer).start();

	}

	//genera nuova partita
	public Integer[][] generateNewSudoku(String _game_name) {
		// TODO Auto-generated method stub

		try {
			FutureGet futureGet = _dht.get(Number160.createHash(_game_name)).start();
			futureGet.awaitUninterruptibly();
			if (futureGet.isSuccess() && futureGet.isEmpty()) 
				_dht.put(Number160.createHash(_game_name)).data(new Data(new HashSet<PeerAddress>())).start().awaitUninterruptibly();

			//crea campo di gioco
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//entra in una partita (avvisa i giocatori in partita che sei entrato nella partita)
	public boolean join(String _game_name, String _nickname) {
		// TODO Auto-generated method stub



		return false;
	}

	//visualizza stato di una partita
	public Integer[][] getSudoku(String _game_name) {
		// TODO Auto-generated method stub
		return null;
	}

	//piazza numero in una partita (avvisa giocatori che hai piazzato il numero e il punteggio)
	public Integer placeNumber(String _game_name, int _i, int _j, int _number) {
		// TODO Auto-generated method stub
		return null;
	}


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

		for(String topic: new ArrayList<String>(s_topics)) leveGame(topic);
		_dht.peer().announceShutdown().start().awaitUninterruptibly();
		return true;
	}

}
