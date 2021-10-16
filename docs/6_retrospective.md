# Retrospettiva
In questo capitolo vengono descritti gli Sprint settimanali, i quali descrivono come lo sviluppo del progetto è stato suddiviso in task e come questi sono stati portati avanti nelle settimane, indicando le attività il cui risultato è stato inserito nul branch `develop` durante lo sprint.
## Sprint

### Iterazione 1: 25/08/2021 - 01/09/2021
Durante il primo sprint il team si è concentrato sul setup del progetto e sull'analisi del problema, iniziando lo sviluppo del design architetturale.

Nello specifico:
- scelto Scala3 come linguaggio di programmazione target per il progetto è stata fatta un'analisi sulla compatibilità dei principali strumenti di testing e building che con esso potessero essere integrati;
- è stato creato il repository ed il progetto sbt in Scala3;
- è stata sviluppata la board su Trello;
- si è iniziato a delineare quelli che dovevano essere i componenti principali del sistema; iniziando a definire una loro implementazione di base su carta;
- è stato sviluppato il mockup iniziale della GUI;

### Iterazione 2: 01/09/2021 - 08/09/2021
Nella seconda iterazione è stata fatta una revisione dei task implementati e sono stati implementati un ingente numero dei componenti core definiti nel primo Sprint.

In particolare:
- a seguito di un incompatibilità con la versione gratuita di Travis CI con la gestione di un repository privato, è stata creata un'organizzazione su GitHub e il progetto è stato importato su quest'ultima in modo da garantire il corretto funzionamento dello strumento di CI;
- sono state sviluppate le card inerenti alle user-stories per facilitare l'implementazione dei componenti core;
- è stato terminato il mockup della GUI del simulatore ed stata fornita una sua implementazione di base che rispecchiasse quanto più possibile il mockup;
- è stata revisionata la struttura delle sezioni board di Trello perché si adattasse meglio alle esigenze del gruppo;
- sono state implementate le versioni base dei componenti core:
   - funzioni Hash:
      - differenti versioni di SHA;
      - MD5;
   - DSL attacchi con implementazione di un primo attacco: brute force;
   - DSL Proxy e Logger;
   - DSL Policy e Policy Checker;

### Iterazione 3: 08/09/2021 - 15/09/2021
La terza iterazione aveva come obiettivo principale quello di completare la realizzazione di componenti core della DSL e della ri-modularizzazione della GUI, oltre all'inizio della stesura della relazione.

Nello specifico ha visto lo sviluppo di:
- dei cifrari per la crittografia simmetrica:
   - AES;
   - DES;
- implementazione di un attacco a dizionario;

### Iterazione 4: 15/09/2021 - 22/09/2021
La quarta iterazione si è articolata perseguendo gli obiettivi di unificazione dei concetti comuni e dell'estensione del framework.

L'unificazione dei concetti comuni ha interessato principalmente l'unificazione dei *builder* utilizzati all'interno della libreria.

Lo sviluppo del framework ha riguardato:
- le policy:
   - introduzione di Policy di default;
   - realizzazione di utility per meglio modularizzare il codice;
- del cifrario per la crittografia asimmetrica: RSA e delle utility per la gestione delle chiavi ad esso collegate;

Inoltre sono state ripensate e quindi revisione le implementazioni dei Logger e del Proxy perché meglio aderissero alle nuove esigenze emerse a valle degli ultimi sviluppi del framework.

### Iterazione 5: 22/09/2021 - 29/09/2021
La quinta iterazione ha visto nuovamente una revisione di quanto implementato negli sprint precedenti da un lato e dall'altro ha visto lo sviluppo di ulteriori funzionalità del framework.
Per quanto riguarda la revisione in questo caso ha riguardato principalmente:
- una re-implementazione di una sotto-porzione dell'applicativo dimostrativo per rispettare il SRP;
- l'implementazione di alcune utility per migliorare l'efficienza dell'interazione con le policies;
- un perfezionamento dei builder per gli attacchi;

Le estensioni invece hanno riguardato:
- il supporto per la lettura degli utenti all'interno dell'applicazione dimostrativa;
- l'integrazione del trait di input `UserProvider` all'interno dell'applicazione.

### Iterazione 6: 29/09/2021 - 06/10/2021
Durante la sesta iterazione sono state svolte le seguenti attività:
- un'unificazione del concetto di alfabeto nel framework;
- un'ulteriore revisione degli attacchi e le funzionalità ad essi collegati;
- lo sviluppo delle funzionalità di supporto per OTP (One-Time Password);
- revisione delle *User information* e delle utility ad esse collegate;
- l'aggiunta del *Cifrario di Cesare* tra gli algoritmi per la crittografia simmetrica supportati;
- revisione degli algoritmi crittografici e relativi cifrari.

### Iterazione 7: 06/10/2021 - 13/10/2021
La settima iterazione è stata usata per integrare completamente tutti i componenti del sistema e ultimare gli aspetti finali del progetto. 
Infine, è stata testata l'applicazione dimostrativa.

Nello specifico è stata quasi completata la stesura della relazione, così come la documentazione formale su *github pages* e sul codice sorgente del progetto.

### Iterazione 8: 13/10/2021 - 17/10/2021
Nell'ultima iterazione, sono state eseguite le ultime revisioni al codice ed ultimata la relazione di progetto.

## Gestione del backlog

% E quanto ne è rimasto in ogni sprint
## Gestione delle iterazioni

# Conclusione e commenti finali
Lo sviluppo del progetto è stata un esperienza essenziale nel nostro percorso di studi in quanto ha permesso di toccare con mano molti degli aspetti centrali affrontanti nel corso.
Infatti, la realizzazione del sistema ha non solo permesso ai membri di testare cosa volesse dire lavorare seguendo un processo di programmazione agile, ma anche di sviluppare un applicativo avendo bene in mente un set di pattern e principi che ne potessero garantire la qualità.
Lo studio di ognuno di questi aspetti è stato un fondamentale momento di arricchimento personale per ognuno dei membri del gruppo che ha potuto quindi estendere la propria conoscenza attraverso i contenuti del corso e il background degli altri membri.

#### Client
Il client non ha avuto modo di utilizzare la persistenza in modo significativo ai fini della simulazione, tuttavia rimane sempre la possibilità di estendere
la libreria per supportare gli attacchi che sviluppano le vulnerabilità dei database specifici.

Per quanto riguarda MVVM, al momento l'implementazione rimane vincolata alle properties di ScalaFx, si potrebbe fare un ViewModel più generico
e riutilizzabile con UI diverse se le properties fossero implementate "in casa".

## Sviluppi futuri
Molti sono gli sviluppi futuri di cui il framework potrebbe giovare.
Infatti, grazie all'architettura definita si potrebbe facilmente pensare di introdurre nuovi tipi di attacco, metodi di cifratura e policy.
Tuttavia, un estensione del framework potrebbe non essere la sola strada possibile, difatti si potrebbe anche pensare di sfruttare il paradigma logico per l'implementazione di alcune operazioni core.
Per la sua intrinseca natura esplorativa, Prolog potrebbe essere utilizzato negli attacchi di tipo Brute Force ad esempio.




//TODO: aggiungere PRODUCT BACKLOG
