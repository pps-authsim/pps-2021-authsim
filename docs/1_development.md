# Processo di sviluppo adottato

In questo capitolo viene presentata la metodologia di lavoro adottata dal gruppo e la conseguente organizzazione per lo sviluppo del progetto.
Inoltre vengono presentati gli strumenti e gli ambienti utilizzati nello sviluppo.

### Metodologia Agile

Il team di sviluppo consistente di tipologie di studenti eterogenee ha adottato un processo di sviluppo **agile** basato sul framework *Scrum*.

Il framework è stato opportunamente adattato alle esigenze personali dovute alle necessità universitarie e lavorative dei membri del gruppo, prendendo spunto dalle guideline e principi agile.

Per questa ragione, per poter sperimentare a pieno il potenzialità della metodologia prescelta alcuni membri del gruppo hanno impersonato un duplice ruolo nella realizzazione del progetto: da un lato quello di sviluppatori e dall'altro quello di alcune delle figure principali coinvolte in un processo Scrum.

Nello specifico:
- Giulia Brugnatti e Marica Pasquali hanno interpretato le esperte del dominio, in quanto il progetto proposto ha un aspetto interdisciplinare con la *Sicurezza delle reti* di cui i membri del gruppo hanno varia padronanza.
- Kyrillos Ntronov ha assunto il ruolo di *Scrum Master*, il cui compito è stato quello di promuovere la "Agile culture", favorire la comunicazione tra i membri del gruppo e prendersi carico degli aspetti organizzativi dei meet.
- Alex Speranza è stato il *Product Owner*, il cui compito è stato sia quello di stabilire il valore degli sviluppi proposti nell'ottica del prodotto finale.

Il processo di *Scrum* è stato modificato per rispecchiare meglio la tipologia e le scadenze imposte del progetto.
In particolare:

- gli sprint sono effettuati con la cadenza settimanale, all'inizio della quale viene proposto un meet di tutti i membri del team per effettuare lo Sprint Planning mentre la Sprint Review e la retrospettiva dell'iterazione appena conclusa avvenivano generalmente nella giornata precedente.
- a causa degli impegni universitari e lavorativi così come la dislocazione dei membri in diverse sedi non è stato possibile svolgere il daily scrum in maniera formale con stand-up meeting.
La coordinazione e l'aggiornamento è quindi avvenuta attraverso canali ufficiali, sebbene più informali, quali ad esempio il canale del gruppo su *Microsoft Teams*.
- Backlog refinement si è svolto in maniera dinamica avvenendo durante la settimana dello sprint.

Durante le riunioni veniva discusso lo stato del progetto e stabilito il backlog dello sprint in base al product backlog.
Inoltre veniva fatta una retrospettiva sullo sprint precedente dove veniva mostrato e spiegato il lavoro svolto da ciascun membro e stabilito l'andamento del progetto.

Lo sviluppo del progetto è stato esteso su 8 sprint settimanali in totale.

La comunicazione del team è avvenuta tramite vari canali, per i meet è stato scelto *Microsoft Teams* per la comunicazione a voce e a video,
mentre in settimana i membri del team erano in contatto sul canale *Telegram* creato per il progetto.

### Divisione dei task

Durante gli sprint settimanali sono stati definiti una serie di task che ogni membro poteva scegliere di implementare su base volontaria.
Per favorire il processo di sviluppo agile e tracciare la roadmap e il backlog del progetto è stata utilizzata una board su *Trello*. 

Sulla board kanban del progetto è stato stabilito il **Product Backlog** e durante gli sprint settimanali anche lo **Sprint Backlog**. 
Le card associate ai task del backlog seguivano il classico flusso del kanban e venivano spostati nelle corsie:
- Todo / Sprint Backlog - Il task è stato assegnato a un membro del team e in attesa di lavorazione.
- Doing - Il task è il lavorazione. Normalmente questa corsia viene limitata per evitare di avere troppi task in lavorazione parallela, 
ma per questo progetto è stato deciso di utilizzare il buon senso come il criterio.
- Review - Il task è in attesa di revisione da parte di un membro del team per ottenere un feedback, commenti ed eventuali correzioni. 
- Done - Il task è stato completato e, nel caso fosse associato a uno sviluppo, il branch è chiuso e unito con `develop`.

Inoltre su Trello si è tenuta traccia di "Epics", cioè attività troppo grandi da inserire in un unico sprint, ma il cui tracciamento
è utile ai fini di avere un punto di riferimento per le attività degli sprint successivi e altri materiali relativi allo sviluppo
come i mockup, diagrammi e appunti.

### Strumenti e CI

Nell'ottica del progetto Agile è stato deciso di creare una pipeline di CI e build automation allo scopo di chiudere frequentemente
e fluidamente gli sviluppi che coprono delle feature stabilite.

#### Scala 3
Scala3 è stato scelto per le nuove feature aggiunte, tra le quali:
- Sintassi senza parentesi per la definizione di strutture dati come classi e trait.
- Sintassi più dichiarativa per le strutture di controllo del flusso come `if`, `while` e `for`.
- La possibilità di definire parametri per i trait.

#### Github

Per il versioning distribuito del progetto è stato utilizzato *GitHub*, nel quale venne creata un *organization* per il progetto.
Sono state utilizzate anche delle feature specifiche di Github come *GitHub Pages*.

#### Gitflow

Per rendere più fluido il processo di versioning dello sviluppo è stato adottato il workflow *Gitflow* dove
le feature venivano sviluppate su dei branch separati da quella di rilascio (master o main).

Per il progetto è stato utilizzato il branch "develop" per il codice in sviluppo, da cui venivano staccati i "feature branch" con gli sviluppi in corso.

Inoltre è stata adottata la prassi dei *pull request* e code review prima della chiusura dei feature branch per garantire la qualità del codice
prodotto e agevolare i merge.

#### Build Environment

- IntelliJ: target IDE del progetto per il supporto completo che questi offre alla programmazione in Scala, tra cui il supporto sperimentale a Scala3 e vari plugin per la "quality of life" indispensabili.
- SBT: strumento di build automation e dependency management attraverso il quale è stato possibile automatizzare e personalizzare i test e le build degli artefatti. Infine è stato integrato nella toolchain CI basata su *Travis*.
- Travis CI: è il servizio di *Continuous Integration* in cloud, è stato agganciato alla repository per essere attivato sui pull request dove eseguiva i controlli di qualità del codice, in specifico venivano verificati i test con sbt e compilati gli artefatti.

Avendo utilizzato Scala 3 purtroppo si è dovuto rinunciare ad alcuni strumenti importantissimi per motivi di compatibilità:

- SonarCloud - Un tool in cloud che consente di effettuare analisi statica del codice prodotto al fine di determinarne i code smell, vulnerabilità e altri difetti. Inoltre consente di fare i check sulla coverage dei test.
Purtroppo non ha ancora il supporto per Scala 3 e non ne riconosce la sintassi. SonarCloud sarebbe stato agganciato alla CI per effettuare i check sulla *quality gate* configurata e la build sarebbe fallita se uno dei criteri stabiliti non fosse stato soddisfatto.
- Wartremover - Strumento sviluppato da Apache per eseguire linting del codice durante la compilazione. 
  L'uso permette di evitare alcune tipologie di codice nocivo, ad esempio l'inferenza automatica del tipo Any o una possibile ricorsione divergente.
  Sfortunatamente anche WartRemover non è ancora compatibile con Scala3.
- Scalafix - Tool molto simile a Wartremover che permette anche di eseguire la riscrittura del codice segnalato. Il supporto per Scala3 è definito "sperimentale" dagli sviluppatori e molte delle regole non sono ancora supportate. 
- Scalastyle - Questo strumento controlla il codice e avvisa dei potenziali problemi al suo interno, similmente al tool Checkstyle per Java. Il supporto per questo tool si ferma alla versione 2.12 di Scala, perciò non è stato possibile installarlo.  

#### Realizzazione Mockup e UML

Per la parte grafica del progetto sono stati prodotti dei mockup statici della gui con *MockPlus*.
UML è stato realizzato con *Star UML* e un plugin per la generazione automatica dell'IDE.

