package it.raffaelemarino.sudoku;

import java.io.IOException;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Esempio {

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

	public static void main(String[] args) throws Exception {

		Esempio example = new Esempio();
		final CmdLineParser parser = new CmdLineParser(example);  

		try  
		{ 

			int scelta=0;

			parser.parseArgument(args);  
			TextIO textIO = TextIoFactory.getTextIO();
			TextTerminal terminal = textIO.getTextTerminal();
			SudokuGameImpl peer = new SudokuGameImpl(id,master, new MessageListenerImpl(id));

			terminal.printf("\nStaring peer id: %d on master node: %s\n", id, master);

			while(scelta!=10) {

				printMenu(terminal);

				scelta = textIO.newIntInputReader().withMaxVal(5).withMinVal(1).read("Scelta");

				switch(scelta) {

				case 1:

					break;

				case 2:

					break;
				
				case 3:
					
					break;

				case 4:
					
					break;
					
				case 5:
					
					break;
					
				default:
					break;
				}

			}


		}  
		catch (CmdLineException clEx)  
		{  
			System.err.println("ERROR: Unable to parse command-line options: " + clEx);  
		}  


	}

	public static void printMenu(TextTerminal terminal) {
		terminal.printf("\n1 - CREA GIOCO\n");
		terminal.printf("\n2 - ENTRA IN UNA PARTITA\n");
		terminal.printf("\n3 - PIAZZA NUMERO\n");
		terminal.printf("\n4 - VISUALIZZA STATO PARTITA\n");
		terminal.printf("\n5 - EXIT\n");

	}

}
