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
Nello sviluppo del progetto mi sono occupata del modulo della crittografia e della gestione degli utenti così come delle \texttt{UserInformation}.
Nello specifico, dopo aver definito le interfacce dei componenti con gli altri membri del gruppo, mi sono occupata dello sviluppo di quanto contenuto nei package:
- user
- cryptography
- 
## Ntronov Kyrillos

Kyrillos Ntronov ha realizzato interamente la parte client (package **it.unibo.authsim.client.app**). 

In particolare sono stati realizzati:

- GUI
- MVVM & Data binding
- Dependency Injection (Cake Pattern) & components
- Persistence (SQL, Mongo in memory DB)
- AttackSimulation task
- Integrazione client con la libreria

e i test relativi.

Inoltre ha svolto i seguenti compiti DevOps e orgnaizzativi:

- Integrazione di **Travis CI** con la repository (organization) github
- Tentativo d'integrazione di **SonarCould** (l'integrazione è di fatto configurata ma non si riesce utilizzarla a causa dell'assenza di supporto per Scala3)
- **SBT build automation** & scripts
- **Scrum Master**

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

### Statistics
La classe `Statistics` racchiude le metriche utili ad analizzare l'esecuzione di un attacco; esse sono:
- l'insieme degli utenti la cui password è stata trovata;
- il numero di tentativi fatti;
- la durata dell'attacco;
- se l'attacco è terminato prima a causa dello scadere del tempo di timeout.

È possibile anche sommare oggetti di questa classe, utilizzando il metodo `+` che restituisce una nuova istanza di `Statistics`:
- l'insieme di utenti è l'unione degli insiemi degli addendi;
- il numero di tentativi e la durata sono calcolati sommando le rispettive metriche;
- l'indicazione di timeout è l'OR logico tra il valore booleano degli addendi.

In questo modo è molto semplice aggregare delle statistiche ottenute da diversi thread di calcolo.

![Statistics UML](/pps-2021-authsim/assets/images/StatisticsFullUml.jpg)

### Consumer e StatisticsConsumer
Il trait `Consumer` rappresenta un qualsiasi consumatore di oggetti (infatti il trait definisce un tipo generico),
quindi il metodo principale `consume` richiede un argomento e non restituisce alcun risultato.

Il trait `StatisticsConsumer` rappresenta un `Consumer` di `Statistics`, e deve essere esteso da chiunque sia interessato
a consumare le statistiche di un attacco, sia per semplicemente stamparle sul terminale, che eventualmente controllare che un
attacco non sia andato a buon fine (cioè se non è stata trovata la password di alcun utente).

Il companion object di `StatisticsConsumer` offre anche un'implementazione di base che scrive sul terminale i dati consumati.

|[Consumer UML](/pps-2021-authsim/assets/images/ConsumerFullUml.jpg)

### ConcurrentStringCombinator
I `ConcurrentStringCombinator` sono oggetti il cui compito è di produrre tutte le combinazioni dei simboli dell'alfabeto
passato come argomento fino a uno specificato numero massimo di simboli consecutivi: utilizzando un alfabeto (`a`, `b`, `c`, `d`)
e chiedendo una lunghezza massima di 4 simboli, verranno prodotte tutte le stringhe da "`a`" fino a "`dddd`", ma ad esempio la stringa
"`adddd`" non sarà prodotta (in quanto è lunga 5 simboli).

Il companion object fornisce delle scorciatoie per alcuni alfabeti standard: le lettere dell'alfabeto inglese minuscole e maiuscole,
le cifre da `0` a `9` e i simboli principali della tabella ASCII originale.

![ConcurrentStringCombinator UML](/pps-2021-authsim/assets/images/ConcurrentStringCombinatorUml.jpg)

### Attacks

Per gli attacchi è stata specificato solo un trait `Attack` pubblico, che tutti gli attacchi devono estendere.
Il trait definisce il metodo `start`, che permette di eseguire l'attacco.

Per creare un attacco si fa uso di builder (che estendono il trait `Builder[Attack]`), definiti a cascata in base alla tipologia dell'attacco e dei parametri che la tipologia stessa richiede:
- `AttackBuilder`: si è supposto che, oltre a poter costruire un attacco, si possa anche eseguire subito col metodo `executeNow`; 
   inoltre, per un qualsiasi attacco si può specificare uno `StatisticsConsumer` a cui inviare le statistiche dell'attacco e un tempo di timeout, oltre al quale l'attacco viene considerato interrotto.
- `OfflineAttackBuilder`: per gli attacchi offline è stato ipotizzato che fosse necessario scegliere un provider di informazioni delgi utenti e, 
   dato che questo tipo di attacchi si presta bene alla parallelizzazione delle operazioni basilari, il numero di thread da utilizzare per eseguire l'attacco.
- `AbstractBruteForceAttackBuilder`: questa classe astratta generalizza i parametri necessari per tutti gli attacchi bruteforce, 
   cioè quelli semplici e gli attacchi a dizionario, permettendo di impostare il tipo di `Alphabet` da usare e il numero 
   massimo di simboli concatenati per generare le stringhe da usare. I metodi per fare ciò hanno visibilità `protected`
   per permettere alle classi più specifiche di esporli con nomi più semantici rispetto all'attacco stesso (ad esempio, 
   un attacco a dizionario espone il metodo `protectedUsingAlphabet` con il metodo `withDictionary` per rendere esplicito
   che necessita di un `Alphabet` di tipo `Dictionary`). Questa classe implementa la generazione dell'attacco di classe `BruteForceAttack`.
- `BruteForceAttackBuilder` e `DictionaryAttackBuilder`: queste due classi estendono la classe astratta descritta sopra 
   per esporre metodi con nomi specifici per il campo semantico dell'attacco stesso.

![Attacks UML](/pps-2021-authsim/assets/images/AttackMainUml.jpg)
