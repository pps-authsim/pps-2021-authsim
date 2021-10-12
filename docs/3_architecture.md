# Design architetturale

In questo capitolo vengono descritti i principali elementi dell'architettura adottata
durante lo sviluppo

## Architettura complessiva

// TODO parlare di divisone tra client/library

## Pattern architetturali usati

### Library

// TODO magari qualcosa su DSL

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
In particolare la gui e' stata modularizzata in tre view aggregate sotto forma di tab della finestra visibile all'utente:

- **Users** - responsabile della definizione e gestione degli utenti coinvolti nella simulazione dell'attacco.
- **Security** - responsabile della scelta delle policy di sicurezza da applicare e del modo in cui gli utenti vengono memorizzati
- **Attack** - responsabile della scelta del preset dell'attacco preconfigurato da eseguire e del suo avvio. Inoltre offre la possibilita' di visualizzare le
statistiche e le informazioni relative allo svolgimento dell'attacco.

Le view sono state abbozzate preliminarmente con i seguenti mockup che sono stati visualizzati e approvati dal product owner e gli stakeholder:

![Users Tab](/pps-2021-authsim/assets/images/users.png)

![Security Tab](/pps-2021-authsim/assets/images/security.png)

![Attack Tab](/pps-2021-authsim/assets/images/attack.png)

#### ViewModel

ViewModel implementa le *properties* e i commandi che vengono legati logicamente alla view (*binding*).

ViewModel notifica la View dello cambiamento dello stato tramite *notifications* (eventi, callback...).
In sintesi, view model definisce la funzionalita' offerta dalla UI, ma non influsice sulla presentazione grafica.

Inoltre, viewmodel e' responsabile della coordinazione delle interazioni propagate dalla view al model interessato.
Per questo motivo, spesso model offrono la possibilita' di eseguire *data binding* e reagire alle notification o eventi.

A tale fine, il binding tra View e ViewModel e tra ViewModel e Model e' stato implementato con l'utilizzo delle *properties* e delle struture dati
che implementano il pattern Observer / Observable.

Si nota anche la neccessita' di separare gli oggetti del dominio di model dagli oggetti del dominio di view in quanto spesso ci si trova davanti ai mapping che non siano one-to-one.

#### Model

Model rappresentano l'incapsulamento dei dati dell'applicativo. Da questo punto di vista, si potrebe dire che model rappresenta il *domain model* dell'applicativo.
Solitamente i model includono anche la business logic e la logica di validazione, tuttavia per questo progetto e' stato deciso mantenere un ulteriore separazione tra la business logic e i model
in quanto la business logic e' incapsulata quasi interamente nel dominio del *task* della simulazione dell'attacco.

// TODO aggiungere UML concreto di MVVM

#### Business Logic e Dependency Injection

La parte di business logic del progetto viene implementata nel dominio sotto forma della gerarchia dei 
componenti iniettabili. Quindi si implementa la tecnica di *inversion of control* denominata *dependency injection*.

In essenza, si tratta di separare i *concern* di costruire e di utilizzare gli oggetti per favorire la leggibilita' e il riutilizzo del codice.
Una classe che utilizza degli oggetti di business logic (detti Service) non necessita di sapere come istanziarli, invece la loro costruzione 
viene delegata a un *injection* e vengono portati come dipendenze tramite la loro iniezione nel costruttore della classe utilizzata.

Inoltre facilita il testing dei component singoli permettendo il mocking delle dipendenze iniettate.g

![Dependency Injection](/pps-2021-authsim/assets/images/attack.png)

In Scala questo pattern e' ottenibile anche con le feature del linguaggio piuttosto che utilizzo delle librerie esterne o framework (che vengano comunque offerti dall'ecosistema).

Una possibilita' consiste nell'implementare il *"Cake Pattern"*, proposto nel paper *Scalable Component Abstractions*, che 
tramire l'utilizzo di self-types permette la composizione di componenti modulari con dei trait multipli, stabilendo la relazione
di dipendenza tra i trait ma mantenendo sempre l'incapsulamento (a differenza dell'utilizzo di inhertitance).

Nel progetto il *"top layer"* dell'ambiente di DI e' stato rappresentato con la classe *ComponentRegistry* che espone i component singleton che ottengono le dipendenze "iniettate" tramite
*self-type annotations*.

La business logic non e' stata sufficientemente complessa in modo da permettere delle gerarchie delle dipendenze complesse, tuttavia rimane facilmente
estendibile con altri *component* qualora servisse ampliare l'applicativo con delle funzionalita' nuove.

// TODO aggiungere UML di component registry e i component

## Scelte tecnologiche

