# Design architetturale

In questo capitolo vengono descritti i principali elementi dell'architettura adottata durante lo sviluppo.

La fase di design ha rappresentato il core del progetto, durante quest'ultima è stata definita l'architettura del complessiva del framework, 
così come quella dell'applicativo dimostrativo.

## Architettura complessiva

Il progetto è stato diviso in due sotto-progetti: il framework e l'applicazione.

Il framework è una libreria a sè stante e non dipende in alcun modo dall'applicazione, mentre quest'ultima deve utilizzare il framework.

## Pattern architetturali usati

### Library

Fin dall'inizio della pianificazione dell'architettura del framework è stato chiaro che questi doveva essere progettato mettendo 
al centro dello sviluppo tre principi su tutti: la modularità, l'anticipazione dei cambiamenti e l'incrementalità.
Tenendo in mente questi obiettivi è stata quindi architettata una struttura che potesse essere componibile in maniera modulabile 
per poterne permettere il rilascio incrementale e l'estensione in maniera quanto più semplice possibile.

Il framework è stato progettato per essere il più dichiarativo possibile, avvalendosi della notazione infissa dei metodi offerta da Scala.

### Client

#### MVVM

Il client utilizza un design pattern architetturale che si basa sul design pattern "Presentation Model" di Fowler.
Questo pattern mira a separare nettamente la logica di presentazione e business logic dalla UI per favorire 
lo sviluppo di applicativi più facili da testare, mantenere ed evolvere gli applicativi sviluppati.

I componenti centrali del pattern sono Views, ViewModels e Models le cui interazioni possono essere riassunti
con lo seguente schema:

![MVVM](/pps-2021-authsim/assets/images/mvvm.png)

#### View

La view è responsabile di definire la struttura, il layout e la presentazione grafica della interfaccia utente.

Nel progetto questa parte è stata implementata con le componenti grafiche della libreria per GUI *ScalaFx*.
In particolare la gui è stata modularizzata in tre view aggregate sotto forma di tab della finestra visibile all'utente:

- **Users** - responsabile della definizione e gestione degli utenti coinvolti nella simulazione dell'attacco.
- **Security** - responsabile della scelta delle policy di sicurezza da applicare e del modo in cui gli utenti vengono memorizzati.
- **Attack** - responsabile della scelta del preset dell'attacco preconfigurato da eseguire e del suo avvio. Inoltre offre la possibilità di visualizzare le
statistiche e le informazioni relative allo svolgimento dell'attacco.

Le view sono state abbozzate preliminarmente con i seguenti mockup che sono stati visualizzati e approvati dal product owner e gli stakeholder:

![Users Tab](/pps-2021-authsim/assets/images/users.png)

![Security Tab](/pps-2021-authsim/assets/images/security.png)

![Attack Tab](/pps-2021-authsim/assets/images/attack.png)

#### ViewModel

ViewModel implementa le *properties* e i comandi che vengono legati logicamente alla view (*binding*).

ViewModel notifica la View dello cambiamento dello stato tramite *notifications* (eventi, callback...).
In sintesi, view model definisce la funzionalità offerta dalla UI, ma non influisce sulla presentazione grafica.

Inoltre, ViewModel è responsabile della coordinazione delle interazioni propagate dalla view a Model interessato.
Per questo motivo, spesso model offrono la possibilità di eseguire *data binding* e reagire alle notification o eventi.

A tale fine, il binding tra View e ViewModel e tra ViewModel e Model è stato implementato con l'utilizzo delle *properties* e delle strutture dati
che implementano il pattern Observer / Observable.

Si nota anche la necessità di separare gli oggetti del dominio di model dagli oggetti del dominio di view in quanto spesso ci si trova davanti ai mapping che non siano one-to-one.

#### Model

Model rappresentano l'incapsulamento dei dati dell'applicativo. Da questo punto di vista, si potrebbe dire che model rappresenta il *domain model* dell'applicativo.
Solitamente i model includono anche la business logic e la logica di validazione, tuttavia per questo progetto è stato deciso mantenere un ulteriore separazione tra la business logic e i model
in quanto la prima è incapsulata quasi interamente nel dominio del *task* della simulazione dell'attacco.

![Authsim MVVM](/pps-2021-authsim/assets/images/mvvmauthsim.png)

Questo diagramma riassume l'implementazione del pattern nel progetto.
Si nota la presenza di una classe intermedia *AuthsimViewModelDeferedProxy* che effettua l'handling dei messaggi provenienti dalla view
e delega la loro esecuzione alla classe ViewModel concreta. ViewModel concreta è stata implementata utilizzando le *properties* offerte da ScalaFx.

#### Business Logic e Dependency Injection

La parte di business logic del progetto viene implementata nel dominio sotto forma della gerarchia dei 
componenti iniettabili. Quindi si implementa la tecnica di *inversion of control* denominata *dependency injection*.

In essenza, si tratta di separare i *concern* di costruire e di utilizzare gli oggetti per favorire la leggibilità e il riutilizzo del codice.
Una classe che utilizza degli oggetti di business logic (detti Service) non necessita di sapere come istanziarli, invece la loro costruzione 
viene delegata a un *injection* e vengono portati come dipendenze tramite la loro iniezione nel costruttore della classe utilizzata.

Inoltre facilita il testing dei component singoli permettendo il mocking facile delle dipendenze iniettate.

![Dependency Injection](/pps-2021-authsim/assets/images/DI.png)

In Scala questo pattern è ottenibile anche con le feature del linguaggio piuttosto che l'utilizzo delle librerie esterne o framework (che vengono comunque offerti dall'ecosistema).

Una possibilità consiste nell'implementare il *"Cake Pattern"*, proposto nel paper *Scalable Component Abstractions*, che 
tramite l'utilizzo di self-types permette la composizione di componenti modulari con dei trait multipli, stabilendo la relazione
di dipendenza tra i trait ma mantenendo sempre l'incapsulamento (a differenza dell'utilizzo di inhertitance).

Nel progetto il *"top layer"* dell'ambiente di DI è stato rappresentato con la classe *ComponentRegistry* che espone i component singleton che ottengono le dipendenze "iniettate" tramite
*self-type annotations*.

La business logic non è stata sufficientemente complessa in modo da permettere delle gerarchie delle dipendenze complesse, tuttavia rimane facilmente
estendibile con altri *component* qualora servisse ampliare l'applicativo con delle funzionalità nuove.

![Component Registry](/pps-2021-authsim/assets/images/registry.png)

In fatto che UserSqlComponent dichiara PropertiesServiceComponent come self-type implica che il trait deve essere portato come mix-in senza estenderlo direttamente.
In questo modo UserSqlComponent ha l'accesso a PropertiesService che viene istanziato nella classe ComponentRegistry

## Scelte tecnologiche

### Client

#### ScalaFx

La scelta di ScalaFx come libreria UI ha influenzato anche l'implementazione di MVVM. L'implementazione di ViewModel infatti utilizza
le properties e Observers offerti dalla libreria. Questo ha facilitato lo sviluppo, semplificando il binding con la View.
