# team2-blog-ricette
TRACCIA 2: Blog Ricette 
 Un ristorante vuole realizzare un blog di ricette. 
 L'applicazione deve prevedere un lato di amministrazione, dal quale è possibile gestire le 
 ricette esistenti e aggiungerne di nuove, e un lato per gli utenti che fruiranno del blog. 
 Ogni ricetta è caratterizzata da titolo, lista degli ingredienti e rispettive quantità, una o più 
 foto, tempo di preparazione, livello di difficoltà e testo della ricetta. 
 L'interfaccia di amministrazione deve permettere di creare/modificare/eliminare le ricette e gli 
 ingredienti. Per ogni ingrediente, oltre al nome, deve essere prevista la possibilità di indicare 
 se esso è compatibile con una dieta vegana, vegetariana o con nessuna delle due. 
 L'home page del blog presenta una lista delle ricette più recenti, con il rispettivo titolo, foto e 
 un breve estratto del testo e un’icona che indichi se la ricetta è vegana o vegetariana (in 
 base agli ingredienti in essa contenuti). Nella sidebar o navbar, deve essere presente un 
 menu di navigazione per categorie. Per ogni ricetta è importante tenere traccia di quante 
 volte è stata visualizzata (in totale) dai clienti. 
 L'utente deve avere inoltre la possibilità di effettuare una ricerca, filtrando le ricette in base ai 
 vari dati disponibili per ognuna di esse. Inoltre, all'interno di ogni ricetta deve essere 
 presente un modulo attraverso il quale l'utente possa lasciare un commento inserendo 
 nome, email e testo del messaggio. 
 Dall’interfaccia di amministrazione deve essere possibile moderare i commenti, e deve 
 essere prevista la possibilità di bannare un utente in base all’indirizzo email inserito al 
 momento del commento (non sarà più consentito lasciare un commento con quell’indirizzo). 
 Infine, l'interfaccia di amministrazione deve fornire una schermata per visualizzare un 
 riepilogo delle attività, che includa: 
 ●   le ricette aggiunte di recente (ultimi 7gg) 
 ●   la classifica delle ricette più visualizzate 
 ●   la classifica delle ricette che hanno ricevuto più commenti 
 NICE TO HAVE 
 Possibilità di mettere “mi piace” alle ricette, implementata in maniera asincrona tramite 
 Javascript. Al click del “mi piace” parte una richiesta POST ad un Rest Controller che 
 incrementa il numero attuale e restituisce il numero finale. Il conteggio viene aggiornato 
 automaticamente nella pagina al termine della richiesta (senza dover ricaricare la pagina).
