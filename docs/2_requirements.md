# Requisiti

In questo capitolo vengono descritti quelli che sono stati i requisiti emersi durante la fase di analisi.

Essendo il progetto suddiviso in due macro parti principali, il framework e l'applicativo dimostrativo,
verranno di seguiti esposti in sezioni differenti i requisiti specifici per ognuno dei due macro componenti individuati.

## Business Requirements

I requisiti di business riguardano la “business solution” del progetto, includendo i bisogni dei clienti e le loro aspettative.
A valle di un analisi preliminare del progetto sono stati identificati i requisiti di business identificati per il framework e l'applicativo dimostrativo possono essere riassunti come segue.

### Library 

L'obiettivo del progetto era la realizzazione di un framework per simulare e testare la memorizzazione e le policy di protezione delle credenziali 
degli utenti che possono essere sottoposti a differenti tipi di attacchi oltre che a un applicazione dimostrativa che possa mostrare come la libreria 
può essere utilizzata.

Il framework deve quindi permettere la creazione di nuovi utenti le cui credenziali devono poter essere configurate scegliendo il metodo di accesso, 
le security policy che possono essere applicate a queste ultime ed i metodi di cifratura con cui queste possono essere criptate.
Deve inoltre essere possibile definire il tipo di attacco con cui si intende effettuare il password cracking e la sorgente delle credenziali da proteggere.

### Client

Per quanto riguarda l'applicazione dimostrativa, che d'ora innanzi verrà chiamata semplicemente "Client", questa deve permettere all'utente all'utente di eseguire le operazioni di cui sopra
In particolare:

- Gestire la creazione / cancellazione degli utenti da testare nell'attacco
- Gestire la generazione degli utenti con le password secondo un criterio/preset stabilito
- Permettere di selezionare il tipo di attacco da simulare
- Permettere di selezionare il tipo di persistenza per memorizzare gli utenti (sql/mongo)
- Permettere di selezionare il tipo di policy di sicurezza da implementare per la simulazione dell'attacco
- Visualizzare l'andamento dell'attacco e le statistiche relative come il tempo impiegato e se fosse andato a buon fine.

## User Requirements

I requisiti utente sono quei requisiti che si riferiscono ai bisogni degli utenti e descrivono quali sono
le azioni che l’utente deve essere in grado di attuare sul sistema.
La raccolta di tali requisiti è stata realizzata mediante *User stories*.

### Library

#### User Story 1

|           	|   	|
|-------------	|-----	|
| WHO         	| In qualità di amministratore del sistema 	|
| WHAT        	| vorrei che il framework mi permettesse di scegliere da quale sorgente importare le credenziali dei miei utenti 	|
| WHY         	| in modo da poter evitare conversioni 	|

#### User Story 2

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di amministratore del sistema 	|
| WHAT        	| vorrei che il framework mi permettesse di scegliere quali security policy implementare nel mio sistema 	|
| WHY         	| in modo da poter scegliere quella che meglio si adatta ai requisiti del mio sistema 	|

#### User Story 3

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di amministratore del sistema 	|
| WHAT        	| vorrei che il framework mi permettesse di creare nuovi utenti dato un set di policy predefinito, o uno personalizzato 	|
| WHY         	| in modo da poter valutare quello che meglio si addice al mio sistema 	|

#### User Story 4

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di amministratore del sistema 	|
| WHAT        	| vorrei che il framework mi permettesse di scegliere quale tipo di attacco implementare contro il mio sistema 	|
| WHY         	| in modo da poter valutare quale sarebbe lo sforzo richiesto per scoprire le password dei miei utenti 	|

### Client

#### User Story 1

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di utilizzatore del simulatore	|
| WHAT        	| Vorrei poter creare nuovi utenti indicandone il username e la password |
| WHY         	| In modo da potere simulare e monitorare l'attacco su una quantità precisa degli utenti con la complessità delle password desiderata	|

#### User Story 2

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di utilizzatore del simulatore	|
| WHAT        	| Vorrei poter creare nuovi utenti generandoli a partire da un preset di complessità |
| WHY         	| In modo da velocizzare la creazione degli utenti desiderati	|


#### User Story 3

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di utilizzatore del simulatore	|
| WHAT        	| vorrei poter scegliere tra un insieme di security policy offerte per la configurazione della sicurezza delle credenziali 	|
| WHY         	| in modo da poter testare quale si adatta meglio al mio sistema e che performance avrebbe 	|

#### User Story 4

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di utilizzatore del simulatore	|
| WHAT        	| vorrei poter scegliere a quale tipo di attacco sottoporre il mio sistema 	|
| WHY         	| in modo da poter testare la sicurezza delle contromisure implementate nel mio sistema 	|

#### User Story 5

|  User Story 	|    	|
|-------------	|-----	|
| WHO         	| In qualità di utilizzatore del simulatore	|
| WHAT        	| vorrei poter visualizzare i risultati dell'attacco a cui ho sottoposto il mio sistema, in particolare in termini di tempo e di efficacia delle contromisure 	|
| WHY         	| in modo da poter effettuare delle valutazioni riguardanti il compromesso tra risorse richieste per effettuare il cracking delle password e il valore dei dati che queste proteggono 	|

## Functional Requirements

In questa sezione vengono descritti quelli che sono i requisiti funzionali del sistema, ovvero le funzionalità che il framework e il simulatore devono offrire.

### Library

Il framework deve permettere di definire:
- le security policy da applicare alle password associate agli utenti del sistema (es. lunghezza della password e caratteri richiesti)
- la tipologia di attacco all'autenticazione da eseguire (offline o online)
- le credenziali dei nuovi utenti inseriti nel sistema
- la sorgente delle credenziali da proteggere (es. DB SQL, No-Sql, file, ecc.)
- i metodi di cifratura e hashing delle credenziali da proteggere
- i metodi di accesso (es. password, token, 2FA...)

### Client

Il simulatore deve permettere di:

- Inserire gli utenti tramite un from registrando i loro username e password.
- Generare gli utenti tramite un form selezionando un modello e la quantità degli utenti da generare.
- Configurare il tipo delle contromisure (policy) da adottare per la simulazione dell'attacco.
- Configurare il tipo della persistenza (SQL o Mongo) da utilizzare per memorizzare gli utenti.
- Configurare il tipo dell'attacco da simulare.
- Lanciare l'attacco allo scopo di decifrare le password degli utenti forniti.
- Visualizzare il log, le metriche e il risultato dell'attacco.

## Non-functional Requirements

In questa parte del capitolo vengono delineati i requisiti non funzionali, ossia le proprietà che il sistema deve soddisfare.

### Library

Il framework deve essere sviluppato perseguendo i principi di modularità e decomposizione in modo da renderlo facilmente estendibile ed ispezionabile.

Inoltre, l'uso del framework deve somigliare alla descrizione di ciò che si vuole fare, quindi permettere di utilizzare la notazione infissa di Scala
per poter creare righe di codice come se fossero frasi di senso compiuto.

### Client

Il simulatore deve essere semplice e intuitivo da utilizzare da parte degli utenti del sistema che possono essere sia amministratore esperti che vogliono testare security policy avanzate, sia sviluppatori junior senza particolari conoscenze in ambito cyber security che vogliono quindi utilizzare il simulatore solo per valutare semplici configurazioni delle credenziali.

Il simulatore deve inoltre permettere di visualizzare i dati inerenti al risultato di un attacco in un modo che sia leggibili e comprensibile a tutti gli utenti.


## Implementation Requirements

In questa parte del capitolo infine vengono elencati i vincoli sullo processo di sviluppo, come
le tecnologie, quality assessment e gli standard stabiliti.

- Si devono seguire le best practice e le convention di Scala e in particolare le nuove preferenze sintattiche di Scala 3.
- Si deve avere una mentalità Agile e di seguire la metodologia Scrum modificata (delineata nel capitolo precedente).
- Si deve utilizzare la CI, in particolare per controllare l'idoneità del codice prima di chiudere un pull request.
- Nonostante l'assenza dei tool automatici, si deve evitare di produrre code smell, vulnerabilità e anti-pattern, inoltre si deve puntare ad avere la coverage dei unit test sopra 80%,
in particolare sulle sezioni critiche del codice.
- Per la parte grafica si deve utilizzare il framework ScalaFx e il pattern architetturale MVVM.
- Infine, si è deciso di omologare l'IDE utilizzato scegliendo IntelliJ come la piattaforma "standard" per lo sviluppo.
