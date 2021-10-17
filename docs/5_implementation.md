
# Implementazione

## Testing
Lo strumento principale utilizzato all'interno del progetto per testare i componenti è stato *ScalaTest*.
Tale tool è stato usato sia per il testing degli elementi del framework, sia per quelli dell'applicativo dimostrativo.

Per lo sviluppo dell'intero framework è stato adottato l'approccio Test Driven Development, scrivendo unit test per tutte le funzionalità del framework. 
Questo metodo di sviluppo ha aiutato a sviluppare codice senza incorrere in problemi di regressione e, nel caso fossero sorti, 
a identificarli e a correggerli immediatamente.

Per l'applicazione dimostrativa, invece, sono stati eseguiti unit test solo per i componenti del modello del programma,
mentre i test sulla parte visuale sono stati eseguiti manualmente, direttamente durante l'esecuzione.

## Suddivisione del lavoro
Da un lato il lavoro è stato suddiviso in modo tale da incontrare le esigenze temporali dei singoli membri e dall'altro perché fosse il più equo possibile.
Tuttavia, a causa della limitata esperienza dei membri del gruppo nella gestione del processo di sviluppo alcuni compiti si sono dimostrati inevitabilmente più onerosi di altri.
All'inizio di ogni iterazione è stato riepilogato in maniera dettagliata quanto realizzato durante lo sprint e sono stati assegnati i compiti per l'iterazione successiva su base volontaria.

Prima dello sviluppo delle differenti componenti, il team ha realizzato assieme le interfacce dei componenti core del applicativo che dovevano essere realizzate.
A valle di questo processo ogni componente ha scelto di focalizzarsi su una macro area andando ad arricchire le sue componenti di settimana in settimana.

Di seguito viene riassunto quanto realizzato da ogni componente del gruppo.

## Brugnatti Giulia
Nello sviluppo del progetto mi sono occupata del modulo della crittografia e della gestione degli utenti così come delle `UserInformation` e di tutte le utilities ad esse collegate.

Nello specifico, dopo aver definito le interfacce dei componenti con gli altri membri del gruppo, mi sono occupata dello sviluppo di quanto contenuto nei package:
- `it.unibo.authsim.library.user`
- `it.unibo.authsim.library.cryptography`

oltre al trait `UserProvider` e di tutti i test ad essi relativi.

Per quanto riguarda il contenuto del package: `it.unibo.authsim.library.cryptography` per garantire la correttezza e la manutenibilità delle operazioni ho scelto di appoggiarmi ad alcune librerie note nell'ambito dell'implementazione di task di sicurezza informatica.

Questa scelta mi ha dato un modo da un lato di prendere confidenza con il build tool scelto per il progetto e dall'altro di sperimentare su di esse.
Nei primi sprint, infatti mi sono occupata di studiare e documentarmi a proposito della teoria inerente alle operazioni crittografiche che intendevo implementare così come le librerie scelte per lo scopo in modo tale da apprendere il linguaggio del dominio che dovevo implementare e da identificare le possibili criticità che l'utilizzo dei framework scelti avrebbe arrecato agli utenti.
Questa fase è stata essenziale da un lato per capire come sviluppare i cifrari che il framework avrebbe dovuto mettere a disposizione e dall'altro di concepire quale livello di astrazione intendevo implementare nella realizzazione dei componenti del modulo.
La definizione di tale livello ha quindi permesso di celare all'utilizzatore le criticità che i framework potevano presentare fornendo a questi ultimi un insieme ridotto di metodi uniformi, semplici ed intuitivi da utilizzare per la realizzazione delle proprie operazioni di crittografia.
Gli utilizzatori della libreria così facendo non devono quindi preoccuparsi di dettagli implementativi per loro irrilevanti inerenti ad esempio ai tipi passati, così come al modo in cui veniva effettivamente implementata dal framework l'operazione richiesta.

Per poter permettere questa agilità nell'utilizzo delle operazioni crittografiche e favorire la leggibilità del codice, si è scelto quando fosse possibile di implementare i metodi perchè prendessero in input argomenti generici che vengono poi gestiti internamente attraverso delle conversioni implicite.
Tale scelta si è resa necessaria poichè molte delle librerie utilizzate richiedevano di passare in input, ad esempio alle operazioni di cifratura, tipi specifici quali, Array di Char o di Byte, che potevano essere considerati poco intuitivi da parte degli utenti finali del sistema.
Si è quindi preferito celare questo aspetto anche per rendere possibile ed agevole un eventuale cambio di framework in futuro.

Un aspetto rilevante, delle parti del framework da me implementate riguarda il fatto che abbia fatto quanto mi fosse possibile per mettere a disposizione dell'utilizzatore operazioni sicure che non causassero eccezioni e valori `null`.
Infatti, per evitare questi funesti casi, da un lato sono stati messi a disposizione dei builder in grado di istanziare correttamente le entità prescelte e dall'altro si scelto di prediligere la restituzione di `Option` in caso l'utilizzatore tenti la definizione di valori non consistenti.
Inoltre, in un discreto numero di casi si è scelto di incapsulare l'implementazione specifica delle diverse entità del sistema in oggetti, per poter lasciare gli utenti finali astrarre dall'implementazione specifica di questi ultimi e dalle librerie utilizzate.

## Ntronov Kyrillos

Kyrillos Ntronov ha realizzato interamente la parte client (package **it.unibo.authsim.client.app**) eccetto `it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy`. 

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

Ho partecipato alla progettazione delle varie interfacce della libreria.

Successivamente, mi sono occupata della realizzazione di tutte le interfacce riguardanti le _Policy_ e le _One-Time Password_,
quindi di tutte le classi che si trovano nei seguenti package:
- `it.unibo.authsim.library.policy`
- `it.unibo.authsim.library.otp`

e inoltre del trait `it.unibo.authsim.library.Protocol`.

Ho partecipato all'integrazione della library nella parte client per quanto riguarda le security policy, implementando gli oggetti:
 - `it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy`
 - `it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy.Default`

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

![Consumer UML](/pps-2021-authsim/assets/images/ConsumerFullUml.jpg)

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
