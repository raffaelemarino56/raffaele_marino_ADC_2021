# Raffaele Marino, Sudoku Game
<br>
MD5 di "raffaelemarino-09": 7b0c1ffcab627351ec15fe0dead4f720

Progetto: <b>Sudoku</b>
Studente: <b>Raffaele Marino</b> (matricola 0522500868)

<h1> Problema </h1>
<br>
Sviluppare un gioco sudou su rete P2P.
Ogni giocatore può mettere un numero nel gioco:
<ul>
<li> se non è stato piazzato allora prende 1 punto
<li> se è stato piazzato prende 0 punti
<li> altrimenti -1 (se sbaglia numero)
</ul>

Verrà fornita una matrice 9x9


Tutti i giocatori sono informati quando un giocatore incrementa il suo punteggio e quando il gioco è finito (in quella partita)


I giocatori possono generare delle partite indentificate da un nome


Si può entrare in un gioco con un nickname


Dato l'indice della matrice(i,j) piazzare un numero

<h1>Soluzione</h1>
<br>
Per la soluzione è stato creato un progetto Maven e aggiunte le seguenti dipendenze di TomP2P nel file pom.xml:

```
<repositories>
    <repository>
        <id>tomp2p.net</id>
         <url>http://tomp2p.net/dev/mvn/</url>
     </repository>
</repositories>
<dependencies>
   <dependency>
     <groupId>net.tomp2p</groupId>
     <artifactId>tomp2p-all</artifactId>
      <version>5.0-Beta8</version>
   </dependency>
</dependencies>
```

La soluzione è un gioco sudoku su terminale, che permette ai giocatori di creare partite, entrare in una partita (o più), visualizzare lo stato della partita, lascaire partite anche in corso, ma cosa più importante di giocare!

Il gioco consiste in un sudoku con matrice 9x9, dove ogni giocatore dovrà inserire un numero dato il nome del gioco, riga, colonna e valore che si vuole inserire. All'immisione del valore sarà possibile vedere se è corretto il valore inserito o meno, e ottenere un puteggio di conseguenza. 

Il gioco termina quando tutta la griglia è completa.


Di seguito una descrizione delle classi nel progetto:
<h3>Descrizione delle classi</h3>

<table>
    <tr><td><b>Classe</b></td><td><b>Descrizione</b></td></tr>
    <tr><td>_CampoDiGioco_</td><td>Definisce il campo di gioco completo e quello visualizzato dagli utenti, gestisce la lista di giocatori in quel gioco e i vari metodi di controllo sul campo di gioco. </td></tr>
    <tr><td>_Esempio_</td><td>Contiene ciò che l'utente poi vede, ovvero il menù e utilizzo dei metodi dichiarati in SudokuGameImpl. Qui si gioca!</td></tr>
    <tr><td>_Giocatore_</td><td>Classe nella quale è definito il giocatore, tutte le proprietà che gli appartengono </td></tr>
    <tr><td>_MessageListner_</td><td>Classe usata per ricevere messaggi </td></tr>
    <tr><td>_MessageListnerImpl_</td><td>Implementazione di MessageListner </td></tr>
    <tr><td>_prova_</td><td>Classe usata solo per fare prove di metodi (creazione sudoku e campo di gioco) </td></tr>
    <tr><td>_SudokuGame_</td><td>Classe con metodi forniti, usati per poter crare i metodi del gioco </td></tr>
    <tr><td>_SudokuGameImpl_</td><td>Implementazione di SudokuGame, nella quale sono presenti metodi che creano il gioco usando una DHT, il peer che utilizza tale DHT e uso dei metodi per il gioco sudoku</td></tr>
    
</table>

<h1>Testing</h1>
<br>

<h3>Descrizione classe di testing</h3>
<table>
    <tr><td><b>Nome Test</b></td><td><b>Descrizione</b></td></tr>
    <tr><td>_setup_</td><td>Istanzio i 4 peer che userò nei test </td></tr>
    <tr><td>_testCaseGenerateNewSudoku_</td><td>Genero un nuovo sudoku </td></tr>
    <tr><td>_testCaseGenerateNewSudokuStessoNome_</td><td>Genero un nuovo sudoku e un'altro da parte di un altro peer con lo stesso nome del precedente, aspettandomi che il risultato del secondo sia null </td></tr>
    <tr><td>_testCaseJoinGame_</td><td>Faccio entrare in una nuova partita i peer in modo corretto </td></tr>
    <tr><td>_testCaseJoinConStessoNome_</td><td>Faccio entrare in una partita due peer con lo stesso nome aspettandomi che il secondo dia come risutlato null </td></tr>
    <tr><td>_testCasePlaceNumber_</td><td>Piazzo un valore nel sudoku </td></tr>
    <tr><td>_testCaseGetLead_</td><td>Recupera la lead board di una partita </td></tr>
    <tr><td>_testCaseIsTerminated_</td><td>Controlla se il gioco è terminato </td></tr>
     <tr><td>_testCaseLeaveAllGames_</td><td>Lascia tutti i giochi che hai joinato </td></tr>
    <tr><td>_testCasePlaceNumberInSamePosition_</td><td>Piazzo lo stesso valore in un sudoku aspettandomi che uno dei due sia pari a 0 </td></tr>
    <tr><td>_testGetSudoku_</td><td>Mi recupero le informazioni di una partita alla quale sono entrato, successivamente provo a far fare la stessa cosa a un altro peer che non era in partita aspettandomi null come risultato </td></tr>
    <tr><td>_testLeaveGame_</td><td>Faccio il test del leave game con due peer, il primo che era in partita e la lascia, il secondo non era in partita e mi aspetto null come risultato </td></tr>
    <tr><td>_testLeaveAllGame_</td><td>leave da tutti i giochi e della network (con tag AfterAll perchè deve essere fatto alla fine di tutti gli altri test)</td></tr>
</table>


<h1>Docker</h1>
<br>
Viene utilizzato un docker per poter lanviare facilmente il progetto.
Usando il Dockerfile nella repo avviare la build traminte il comando:

```
$ docker build --no-cache -t raffaelemarinosudoku .
```

ora lancaire i peer:

Master

```
$ docker run -i --rm --name MASTER_PEER -e MASTER=<MASTER ADDRESS> -e ID=0 raffaelemarinosudoku
```

Altri

```
$ docker run -i --name PEER1 -e MASTER=<MASTER ADDRESS> -e ID=1 raffaelemarinosudoku
```

Per controllare l'indirizzo del master usare il comando
```
$ docker inspect
```

