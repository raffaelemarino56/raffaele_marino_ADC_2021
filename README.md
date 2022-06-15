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

<h1>Testing</h1>
<br>




<h1>Docker</h1>
<br>
