# Implementazione

## Testing
Lo strumento principale utilizzato all'interno del progetto per testare i componenti è stato *ScalaTest*.
Tale tool è stato usato sia per il testing degli elementi del framework, sia per quelli dell'applicativo dimostrativo.
Nello specifico per testare il comportamento del sistema sono stati realizzati degli Unit test per tutte le componenti rilevanti della GUI e della DSL.

## Suddivisione del lavoro
Da un lato il lavoro è stato suddiviso in modo tale da incontrare le esigenze temporali dei singoli membri e dall'altro perché fosse il più equo possibile.
Tuttavia, a causa della limitata esperienza dei membri del gruppo nella gestione del processo di sviluppo alcuni compiti si sono dimostrati inevitabilmente più onerosi di altri.
All'inizio di ogni iterazione è stato riepilogato in maniera dettagliata quanto realizzato durante lo sprint e sono stati assegnati i compiti per l'iterazione successiva su base volontaria.

Prima dello sviluppo delle differenti componenti, il team ha realizzato assieme le interfacce dei componenti core del applicativo che dovevano essere realizzate.
A valle di questo processo ogni componente ha scelto di focalizzarsi su una macro area andando ad arricchire le sue componenti di settimana in settimana.

Di seguito viene riassunto quanto realizzato da ogni componente del gruppo.

## Brugnatti Giulia

## Ntronov Kyrillos

## Pasquali Marica

## Speranza Alex
### Alphabet
Il trait `Alphabet` rappresenta un insieme di simboli utilizzata per comporre stringhe.

Un alfabeto è composto dal proprio insieme di simboli e da una funzione che analizza l'insieme dei simboli e mostra un errore se almeno uno di essi non è conforme:
ad esempio, in un alfabeto simbolico sono ammessi solo simboli con lunghezza 1 (quindi `a`, `b`, `1`, `$`, ...).

Dato che con più copie dello stesso simbolo non cambia l'insieme di stringhe generabili, `Alphabet` estende l'interfaccia `Set[String]`,
e il `Set` all'interno ha lo stesso tipo per contenere i simboli; il pattern `Decorator` è stato usato per rendere trasparente l'uso di un `Alphabet`, come se fosse un semplice `Set[String]`.

Inoltre, avendo `String` come tipo specifico del `Set`, è possibile utilizzare il trait anche per definire dizionari, cioè insiemi di parole con lunghezza maggiore di 1.

Infine, è stato dato un tipo generico al trait stesso per permettere al metodo `and` di avere un tipo di ritorno specifico in base alla classe che lo ridefinisce:
ad esempio, un alfabeto `SymbolicAlphabet`, quando unito con un altro alfabeto, restituisce un oggetto dello stesso tipo,
in quanto l'operazione di unione avviene sui `Set` interni, mentre la funzione `acceptor` rimane la stessa dell'alfabeto su cui è stato chiamato `and`.

![Alphabet UML](/pps-2021-authsim/assets/images/AlphabetFullUml.jpg)

### Builder
Il trait `Builder` è stato progettato come base per gli eventuali builder del progetto, pertanto definisce un proprio tipo generico,
che rappresenta il tipo dell'oggetto che costruirà infatti è il tipo di ritorno del metodo `build`.

Inoltre, il trait definisce un metodo ausiliario per definire altri metodi compatibili con il pattern `Chaining Method`, proprio per questo
restituisce `this.type`, che è tradotto nel tipo più specifico della classe o trait che lo utilizza. Il metodo fa uso della natura funzionale di Scala
e anch'esso è generico: richiede come primo argomento una funzione che, a sua volta, necessita di un argomento e non restituisce alcun valore (pattern comune alle funzioni "setter"),
mentre il secondo argomento è il valore da passare alla prima funzione; infine restituisce l'istanza dell'oggetto su cui è chiamata la funzione (ritorna `this`).

Questo permette di definire delle funzioni nel seguente modo:
```
def setX(value: Int) = this.builderMethod[Int](x => this._x = x)(value)
```
e di poter concatenare metodi che, unito alla notazione infissa di Scala, permette di utilizzare i builder con un linguaggio quasi naturale:
```
myBuilder setX 5 setY 4 build
```

![Buider UML](/pps-2021-authsim/assets/images/BuilderFullUml.jpg)

### Consumer
### Statistics
### ConcurrentStringCombinator
### Attacks