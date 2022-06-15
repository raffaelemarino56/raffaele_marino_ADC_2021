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

<h3>Descrizione delle classi</h3>

<table>
    <tr><td>Classe</td>             <td>Descrizione</td></tr>
    
    <tr><td>CampoDiGioco</td>       <td>Definisce il campo di gioco completo e quello visualizzato dagli utenti, gestisce la lista di giocatori in quel gioco e i vari metodi di controllo sul campo di gioco. </td></tr>
    <tr><td>Esempio</td>            <td>Contiene ciò che l'utente poi vede, ovvero il menù e utilizzo dei metodi dichiarati in SudokuGameImpl. Qui si gioca!</td></tr>
    <tr><td>Giocatore</td>          <td>Classe nella quale è definito il giocatore, tutte le proprietà che gli appartengono </td></tr>
    <tr><td>MessageListner</td>     <td>Classe usata per ricevere messaggi </td></tr>
    <tr><td>MessageListnerImpl</td> <td>Implementazione di MessageListner </td></tr>
    <tr><td>prova</td>              <td>Classe usata solo per fare prove di metodi (creazione sudoku e campo di gioco) </td></tr>
    <tr><td>SudokuGame</td>         <td>Classe con metodi forniti, usati per poter crare i metodi del gioco </td></tr>
    <tr><td>SudokuGameImpl</td>     <td>Implementazione di SudokuGame, nella quale sono presenti metodi che creano il gioco usando una DHT, il peer che utilizza tale DHT e uso dei metodi per il gioco sudoku</td></tr>
    
</table>

<h1>Testing</h1>
<br>

<h3>descrizione classe di testing</h3>
<table>
<tr><td>Nome Test</td><td>Descrizione</td></tr>
<tr><td>Dato 1,1</td><td>Dato 1,2</td></tr>
<tr><td>Dato 2,1</td><td>Dato 2,2</td></tr>
<tr><td>Dato 3,1</td><td>Dato 3,2</td></tr>
</table>


<h1>Docker</h1>
<br>
Viene utilizzato un docker per poter lanviare facilmente il progetto.
Usando il Dockerfile nella repo avviare la build traminte il comando:

```
$ docker build --no-cache -t raffaele-marino/sudoku
```

ora lancaire i peer:

Master

```
$ docker run -i --rm --name MASTER_PEER -e ID=0 raffaele-marino/sudoku
```

Altri

```
$ docker run -i --rm --name PEER1 -e ID=1 -e MASTER=<MASTER ADDRESS> raffaele-marino/sudoku
```

Per controllare l'indirizzo del master usare il comando
```
$ docker inspect
```

