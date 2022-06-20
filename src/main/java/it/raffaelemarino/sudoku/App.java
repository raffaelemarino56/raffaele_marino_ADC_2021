package it.raffaelemarino.sudoku;

import java.io.IOException;
import java.util.Random;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class App {

	/*
		 ogni giocatore può mettere un numero nel gioco:
			- se non è stato piazzato allora prende 1 punto
			- se è stato piazzato prende 0 punti
			- altrimenti -1 (se sbaglia numero)

		matrice 9x9

		tutti i giocatori sono informati quando un giocatore incrementa il suo punteggio e quando il gioco è finito (in quella partita)

		i giocatori possono generare delle partite indentificate da un nome

		si può entrare in un gioco con un nickname

		dato l'indice della matrice piazzare un numero
	 */

	@Option(name="-m", aliases="--masterip", usage="the master peer ip address", required=true)
	private static String master;

	@Option(name="-id", aliases="--identifierpeer", usage="the unique identifier for this peer", required=true)
	private static int id;

	public static void main(String[] args){
		
		final CmdLineParser parser = new CmdLineParser(new App()); 
		TextIO textIO = TextIoFactory.getTextIO();
		TextTerminal<?> terminal = textIO.getTextTerminal();

		try  
		{ 

			int scelta=0;

			parser.parseArgument(args);  

			SudokuGameImpl peer = new SudokuGameImpl(id,master, new MessageListenerImpl(id));

			terminal.printf("\nStaring peer id: %d on master node: %s\n", id, master);

			while(scelta!=10) {

				printMenu(terminal);

				scelta = textIO.newIntInputReader().withMaxVal(5).withMinVal(1).read("Scelta");
				String nome_gioco="";

				switch(scelta) {

				case 1:
					Random random = new Random();
					nome_gioco = textIO.newStringInputReader().withDefaultValue("sudoku"+random.nextInt()).read("Game name");
					Integer[][] nuovo_gioco = peer.generateNewSudoku(nome_gioco);
					if(nuovo_gioco==null)
						System.out.println("il gioco con nome: "+ nome_gioco +" già esiste");
					break;

				case 2:
					nome_gioco = textIO.newStringInputReader().read("Game name");
					String nickname = textIO.newStringInputReader().withDefaultValue("giocatore").read("Nickname");
					if(peer.join(nome_gioco, nickname)) {
						System.out.println("entrato correttamente nel gioco:"+nome_gioco+"di seguito lo stato della partita: ");


						Integer[][] campo_di_gioco = peer.getSudoku(nome_gioco);

						for(int i=0; i<9;i++) {
							for(int j=0;j<9;j++) {
								System.out.print(campo_di_gioco[i][j]+"   ");
							}
							System.out.println();
						}

					}

					break;

				case 3:
					nome_gioco = textIO.newStringInputReader().read("Game name");
					int i = textIO.newIntInputReader().withMinVal(1).withMaxVal(9).read("valore i (riga)");
					int j = textIO.newIntInputReader().withMinVal(1).withMaxVal(9).read("valore j (colonna)");
					int numero = textIO.newIntInputReader().withMinVal(1).withMaxVal(9).read("valore");
					Integer punto = peer.placeNumber(nome_gioco, i-1, j-1, numero);
					switch(punto){
					case 1:
						System.out.println("corretto, hai guadagnato un punto");
						break;

					case 0:
						System.out.println("valore corretto ma già inserito, non perdi niente");
						break;

					case -1:
						System.out.println("hai perso un punto");
						break;

					default:
						System.out.println("error");
						break;


					}

					break;

				case 4:
					nome_gioco = textIO.newStringInputReader().read("Game name");

					Integer[][] campo_di_gioco = peer.getSudoku(nome_gioco);

					for(int i1=0; i1<9;i1++) {
						for(int j1=0;j1<9;j1++) {
							System.out.print(campo_di_gioco[i1][j1]+"   ");
						}
						System.out.println();
					}

					break;

				case 5:

					nome_gioco = textIO.newStringInputReader().read("Game name");
					peer.leaveGame(nome_gioco);

					break;

				case 6:


					peer.leaveNetwoks();

					break;

				case 7:

					nome_gioco = textIO.newStringInputReader().read("Game name");
					String s = peer.getLeadboard(nome_gioco);
					if(s.equals(null)) {
						System.out.println("Non sei un giocatore che partecipa a questa partita");
					}else {
						System.out.println(s);
					}


					break;
				
					
				case 8:

					nome_gioco = textIO.newStringInputReader().read("Game name");
					
					switch(peer.isTeerminated(nome_gioco)) {
					case 0:
						System.out.println("non sei in partita!!! entra per scoprire l ostato della partita!");
						break;
					case 1:
						System.out.println("il gioco "+nome_gioco+" è terminato");
						break;
					case -1:
						System.out.println("si è verificato un errore");
						break;
					default:
						System.out.println("il gioco "+nome_gioco+" non è ancora terminato, fai la tua mossa!");
						break;
					
					}
					
					break;
					
				case 9:


					peer.leveAllGames();

					break;

				default:
					break;
				}

			}


		}  
		catch (CmdLineException clEx)  
		{  
			System.err.println("ERROR: Unable to parse command-line options: " + clEx);  
		}  catch (IOException e) {
			//errore bootstrap P2P
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			terminal.abort();
			System.exit(0);
		}


	}

	
	public static void printMenu(TextTerminal<?> terminal) {
		
		terminal.printf("\n1 - CREA GIOCO\n");
		terminal.printf("\n2 - ENTRA IN UNA PARTITA\n");
		terminal.printf("\n3 - PIAZZA NUMERO\n");
		terminal.printf("\n4 - VISUALIZZA STATO PARTITA\n");
		terminal.printf("\n5 - ESCI DA GIOCO\n");
		terminal.printf("\n6 - ESCI DA NETWORK\n");
		//funzioni in più rispetto alla traccia
		terminal.printf("\n7 - VISUALIZZA LEAD BOARD\n");
		terminal.printf("\n8 - CONTROLLA SE UN GIOCO E' TERMINATO\n");
		terminal.printf("\n9 - ESCI DA TUTTE LE PARTITE E DALLA NETWORK\n");




	}

}
