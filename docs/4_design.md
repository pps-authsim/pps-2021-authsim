

# Design di dettaglio

## Library

### `AbstractBruteForceAttackBuilder`
#### metodi `protected`
Nella classe `AbstractBruteForceBuilder` sono stati inseriti i metodi di configurazione relativi 
agli attacchi bruteforce con visibilità `protected` per permettere alle classi che la estendono di
esporre metodi con nomi più semantici: ad esempio, un attacco bruteforce basato su dizionario 
(generato dal builder `DictionaryAttackBuilder`) richiede un alfabeto in senso generico, quindi un insieme di 
parole con cui generare le stringhe (non obbligatoriamente le parole sono lunghe 1 solo carattere), 
perciò è semanticamente più corretto esporre un metodo nominato `withDictionary` rispetto a `withAlphabet`, 
infatti rispecchia il tipo richiesto dal metodo.

### `Alphabet`
#### `extends Set[String]`
Il trait `Alphabet` estende il trait dei set, in particolare specificando che si tratta di un insieme di stringhe.
Questa estensione è stata fatta per poter utilizzare (e raggiungere) il `Set` all'interno dell'alfabeto stesso 
in modo trasparente e rendere il codice meno verboso.

#### generico rispetto a sè stesso
`Alphabet` è generico rispetto a sè stesso, infatti la sua signature è `trait Alphabet[T <: Alphabet[T]`.
Questa definizione particolare è utile per aiutare il compilatore a conoscere il tipo del valore di ritorno
del metodo `and` (che unisce due alfabeti in una nuova istanza), il quale generalmente ritorna un oggetto dello stesso
tipo dell'alfabeto sul quale si chiama il metodo stesso: ad esempio, se si chiama `and` su un alfabeto di tipo
`Dictionary`, il nuovo alfabeto prodotto sarà anch'esso di tipo `Dictionary`.

### `Builder`
#### `builderMethod`
Il metodo `builderMethod` all'interno del trait `Builder` è stato inserito per rendere meno verboso il codice dei vari 
builder presenti, in quanto incapsula la restituzione del builder stesso. Ciò è stato possibile grazie alla possibilità
di Scala di accettare funzioni come argomenti per altre funzioni. `builderMethod` richiede una funzione che accetta un 
solo valore del tipo specificato e restituisce il tipo `Unit`, che normalmente si associa a una funzione di assegnamento.

### `ConcurrentStringCombinator`
#### restituisce un solo `Option[String]` per chiamata
Questa classe funge da monitor per le combinazioni di stringhe richieste. In questo modo, procedure in esecuzione in 
parallelo non interferiscono tra di loro. Inoltre, fornisce anche una condizione di terminazione, 
in quanto quando restituisce un `Option` vuoto significa che le combinazioni di stringhe richieste sono terminate.


### Security Policy

![Policy Model Package UML](assets/images/policy/policy-model-package.svg)

Le interfacce `Policy`, `UserIDPolicy`,`PasswordPolicy`,`OTPPolicy`,`Saltpolicy` definiscono un tipo immutabile di politica di sicurezza.

- `Policy` è un' interfaccia che rappresenta una container d' informazioni riguardanti le credenziali dell' utente.
  - `credentialPolicies: Seq[CredentialPolicy]` identifica le policy relative alle credenziali
  - `cryptographicAlgorithm: Option[CryptographicAlgorithm]` identifica l'eventuale algoritmo crittografico utilizzato per memorizzare le credenziali nel database. Nel caso non sia presente, significa che sono state memorizzate in _plaintext_
  - `saltPolicy: Option[SaltPolicy]` identifica una policy su un eventuale valore di sale utilizzato per crittografare le credenziali
  - `transmissionProtocol: Option[Protocol]` identifica l'eventuale protocollo (interfaccia `Protocol`) utilizzato per trasferire le credenziali dal host locale a un host remoto. Se non è presente significa che le credenziali sono usate in locale
   

- `UserIDPolicy`,`PasswordPolicy`,`OTPPolicy` sono interfacce di security policy riguardanti le credenziali vere e proprie (userID, password, otp), definite dall'amministratore di sistema.


- `Saltpolicy` è l'interfaccia che definisce come deve essere un valore di sale.

Le credenziali e i valori di sale sono di tipo stringa, quindi le policy corrispondenti sono state progettare in modo da essere un estensione dell' interfaccia `StringPolicy` 
che richiede un alfabeto (`PolicyAlphabet`), grazie al quale possono essere generate automaticamente le stringhe, attraverso il metodo `generate(implicit policyAutoBuilder: StringPolicy => PolicyAutoBuilder[String]): String`, senza che l' utilizzatore debba generarle manualmente.

Il metodo utilizzato per generare automaticamente le stringhe è una Higher-Order Function, ovvero una funzione che accetta altre funzioni come parametri, quindi 
il metodo delega alla funzione passatagli ( `implict StringPolicy => PolicyAutoBuilder[String]` ) la generazione della stringa basata sulla `StringPolicy`.

L'alfabeto `PolicyAlphabet` è stato progettato utilizzando il pattern template method, in modo tale che l'utilizzatore debba definire solamente una parte o tutti i seguenti metodi:
- `lowers` restituisce un alfabeto contenente solamente i caratteri minuscoli
- `uppers` restituisce un alfabeto contenente solamente i caratteri maiuscoli
- `digits` restituisce un alfabeto contenente solamente i caratteri numerici
- `symbols` restituisce un alfabeto contenente solamente caratteri speciali

Questi metodi restituiscono un alfabeto del tipo `SymbolicAlphabet` (extends [`Alphabet`](#alphabet)).

L'alfabeto è stato _arricchito_ con il mixin `RandomAlphabet` che definisce metodi che restituiscono una lazy list di caratteri
e con il mixin `RegexAlphabet` che definisce metodi che restituiscono una espressione regolare.

Ogni `StringPolicy` può essere _arricchita_ da una o più interfacce (mixins) che definiscono dei metodi utili per la generazione e la validazione delle stesse.

- `RestrictStringPolicy` definisce metodi riguardanti la lunghezza della stringa
  - `minimumLength: Int` restituisce il valore minimo della lunghezza della stringa
  - `maximumLength: Option[Int]` restituisce opzionalmente il valore della massima lunghezza. Se non è presente il valore massimo, significa che non c'è un limite superiore alla lunghezza che una stringa può avere.


- `MoreRestrictStringPolicy` definisce metodi riguardanti il contenuto della stringa
  - `minimumUpperChars: Option[Int]` : restituisce opzionalmente il valore minimo di caratteri maiuscolo che una stringa deve contenere
  - `minimumLowerChars: Option[Int]` : restituisce opzionalmente il valore minimo di caratteri minuscoli che una stringa deve contenere
  - `minimumSymbols: Option[Int]` : restituisce opzionalmente il valore minimo di caratteri speciali che una stringa deve contenere
  - `minimumNumbers: Option[Int]` : restituisce opzionalmente il valore minimo di caratteri numerici che una stringa deve contenere
  
   Nel caso in cui uno o più di questi metodi non restituisca un valore, significa che non c'è un limite inferiore per il contenuto della stringa. 
   Per esempio se `minimumUpperChars = None` significa che in una stringa può non esserci nessun carattere maiuscolo.


La creazione delle security policy è delegata alle classi `PolicyBuilder`, `UserIDPolicyBuilder`, `PasswordPolicyBuilder`, `OTPPolicyBuilder`, `SaltPolicyBuilder`, progettate utilizzando il pattern _Builder_.
La struttura dei vari builder rispecchia il modello descritto precedentemente.

In più è stata inserita una interfaccia (mixin) `SettableAlphabetBuilder`, utile al settaggio di un nuovo alfabeto. Questa interfaccia è stata creata per necessita visto che non tutte le `StringPolicy` devono cambiare alfabeto, 
infatti le One Time Password (OTP), per definizione, hanno un alfabeto fisso di soli caratteri numerici.


![Policy Builders Package UML](assets/images/policy/policy-builders-package.svg)

Essendo le security policy immutabili, esiste la possibilità di modificare delle policy già definite tramite l' oggetto `PolicyChanger`, il quale definisce dei metodi factory per ogni policy progetta.
 - `apply(policy: Policy): BasicPolicyBuilder with PolicyChanger[Policy]`
 - `userID(userIDPolicy: UserIDPolicy): UserIDPolicyChanger`
 - `password(passwordPolicy: PasswordPolicy):PasswordPolicyChanger`
 - `otp(otpPolicy: OTPPolicy): OTPPolicyChanger`
 - `salt(saltPolicy: SaltPolicy): SaltPolicyChanger`
 
Tutti i PolicyChanger estendono il builder corrispondente e l'interfaccia mixin `PolicyChanger[T]`, nella quale è definito un metodo `rebuild: T` che ricostruisce la security policy con le eventuali modifiche.

![Policy Changers Package UML](assets/images/policy/policy-changers-package.svg)

Una prerogativa delle security policy relative alle stringhe è quella di essere validate/verificate, ciò è possibile utilizzando l' oggetto `StringPolicyChecker` 
che possiede il metodo factory `apply(policy: Stringpolicy): PolicyChecker[String]` che crea un istanza di `PolicyChecker[String]` la quale possiede un metodo `check(value: String): Boolean`
che verifica che il valore passatogli sia conforme alla policy precedentemente inserita.
  
![Policy Checkers Package UML](assets/images/policy/policy-checkers-package.svg)


Per facilitare l'assegnamento delle credenziali generate in base al tipo di credenziale (userID, password, otp) sono stati progettati degli extractor object (ovvero oggetti che definiscono un metodo `unapply(credentialPolicy: CredentialPolicy): Option[String]`).
 - `UserIDGenerate` estrae l'userID generato dalla `CredentialPolicy`
 - `PasswordGenerate`  estrae la password generata dalla `CredentialPolicy`
 - `OTPGenerate`  estrae l'OTP generato dalla `CredentialPolicy`

![Policy Extractors Package UML](assets/images/policy/policy-extractors-package.svg)

Sono state progettate delle Security Policy di Default che sfruttano le varie funzionalità descritte precedentemente.

![Policy Defaults Package UML](assets/images/policy/policy-defaults-package.svg)

### Generatore di One-Time Password (OTP)

La One Time Password è una password che è valida solo per una singola sessione di accesso o una transazione. 
Per questo suo scopo l'OTP è anche detta password usa e getta.
Le OTP possono essere utilizzate come unico fattore di autenticazione, o in aggiunta ad un altro fattore, 
come può essere la password dell'utente, in modo da realizzare una autenticazione a due fattori.

![OTP Model Package UML](assets/images/otp/otp-model-package.svg)

Le interfacce `OTP`, `HOTP`, `TOTP` definiscono un tipo immutabile di One-Time Password.

`OTP` è l' interfaccia che definisce un tipo generico di one time password.
 - `polciy: OTPPolicy` restituisce la policy che viene applicata alla otp
 - `length: Int` restituisce l'effettiva lunghezza dell'otp 
 - `reset: Unit` effettua il reset del otp generatore
 - `generate: String` restituisce una valida otp
 - `check(pincode: String): Boolean` verifica che il _pincode_ passatogli sia una valida otp

Le sue estensioni, ovvero le effettive interfacce, sono:

 - `HOTP` rappresenta una OTP basata sulla hash-based message authentication codes (HMAC).
   - `hashFunction: HashFunction` restituisce la funzione hash con cui viene generata l'otp
   

 - `TOTP` rappresenta una particolare OTP basata sulla hash-based message authentication codes (HMAC), in aggiunta possiede un limite di tempo di utilizzo.
 
   - `timeout: Duration` restituisce la durata di tempo di validità della otp
   - `createDate: Option[Long]` restituisce opzionalmente la data di generazione della otp. Se non presente, significa che la otp non è stata ancora generata.

La creazione delle one time password è delegata alle classi `HOTPBuilder`, `TOTPBuilder`, progettate utilizzando il pattern _Builder_.
La struttura dei vari builder rispecchia il modello descritto precedentemente.

L'interfaccia `OTPBuilder` può essere _arrichita_ con le interfacce (mixins) `SecretBuilder`, `HmacOTPBuilder` e `TimeOTPBuilder` a seconda di quale tipo di OTP si vuole implementare.
![OTP Builders Package UML](assets/images/otp/otp-builders-package.svg)

L'algoritmo dell'HOTP è stato incapsulato nell'oggetto `OTPGenerator`, in modo tale da poterlo utilizzare anche standalone.

La generazione della lunghezza della OTP è delegata alla interfaccia `LengthGenerator`, la quale possiede un metodo che prende in input una OTP policy e genera la lunghezza effettiva (`length(optPolicy: OTPPolicy): Int`).
Questa interfaccia viene utilizza nel `OTPBuilder` attraverso il metodo `withPolicy(policy: OTPPolicy)(implicit generateLength: LengthGenerator)`.

![OTP Builders Package UML](assets/images/otp/otp-generators-package.svg)

### Cryptography 
Il modulo di crittografia è la parte del sistema adibita a tutte le operazioni crittografiche.
Quest'ultima incapsula gli algoritmi crittografici così come i cifrari che ne permettono le operazioni principali e le utilities ad essi collegati.

#### Algorithm
Gli algoritmi crittografici sono stati modellati tramite una gerarchia che trova la sua *root* nell'interfaccia `CryptographicAlgorithm`.

- `CryptographicAlgorithm`
   Il trait `CryptographicAlgorithm` modella un generico algoritmo di crittografia racchiudendone i metodi core comuni a tutti i suddetti algoritmi.

Nel framework si è deciso di supportare tre macro tipologie di algoritmi crittografici, in modo da lasciare all'utilizzatore decidere se garantire l'integrità, l'autenticità, o la confidenzialità dei dati.
Di seguito vengono quindi descritte le tre famiglie supportate partendo dalle funzioni hash, per poi arrivare agli algoritmi a chiave simmetrica ed asimmetrica.

  - `HashFunction`
  Il trait `HashFunction`, modella una generica funzione hash[^Hash], esponendone le funzionalità principali.
  Nel framework è stato deciso di mettere a disposizione i più noti algoritmi per fare hashing; nello specifico, non solo funzioni hash considerate attualmente sicure, ma bensì di permettere all'utente di disporre anche di funzioni non sicure, in modo da permettere confronti tra algoritmi resistenti alla crittoanalisi e altri che violano almeno una delle proprietà di sicurezza delle funzioni hash[^HashProprieta].
Per rendere il processo maggiormente sicuro è stato deciso di lasciare all'utente la possibilità di definire anche un valore di sale per garantire una maggiore sicurezza agli attacchi crittografici.

[^Hash]: Gli algoritmi hash sono particolari tipi di funzioni utilizzati per garantire la confidenzialità dei dati.
Questi ultimi infatti permettono di convertire input di una lunghezza arbitraria, in stringhe di lunghezza fissa, questo mapping deve essere infeasible da invertire e resistente alle collisioni per essere considerato sicuro.

[^HashProprieta]: Le proprietà di sicurezza di riferimento delle funzioni hash sono tre: resistenza alla preimmagine, resistenza alla seconda preimmagine e resistenza alla collisione. Per maggiori informazioni consultare \[link to Funzioni Crittofiche di hash!](https://it.wikipedia.org/wiki/Funzione_crittografica_di_hash).
  
 Entrambe le famiglie di algoritmi di encryption estendono da un trait condiviso che ne modella operazioni comuni; questi prende il nome di `EncryptionAlgoritm` .
  
  - `SymmetricAlgoritm`
  Il trait `SymmetricAlgoritm` è una delle due macrocategorie di algoritmi di crittografia; nello specifico questi modella la famiglia di algoritmi di crittografia simmetrica[^CrittografiaSimmetrica].
  
 [^CrittografiaSimmetrica]: Gli algoritmi di crittografia simmetrica sono gli algoritmi crittografici in grado di garantire la confidenzialità in un sistema.
 
Per quanto riguarda questa tipologia di algoritmi si è scelto di mettere a disposizione tre algoritmi piuttosto differenti, il *Cifrario di Cesare*, *DES*, *AES*, anche in questo caso si è scelto di lasciare gli utilizzatori liberi di disporre anche di algoritmi la cui insicurezza è nota da lungo tempo [^CifrarioCesareInsicuro] ricordando che lo scopo del framework è quello di permettere all'utente di fare di confronti.

[^CifrarioCesareInsicuro]: L'insicurezza del Cifrario di Cesare è stata evidente fin dal XI a seguito degli studi sulle tecniche di crittoanalisi del arabo *Al-Kindi*. La sicurezza di DES invece è stata messa in questione dal 1997 quando per la prima volta sono stati violati messaggi criptati col suddetto algoritmo. Attualmente AES è l'unico algoritmo proposto ad essere approvato dal *NSA* per il passaggio di informazioni *top secret*. 


 - `AsymmetricAlgoritm`
  Il trait `AsymmetricAlgoritm` come l'interfaccia precedente estende dal trait `EncryptionAlgoritm` modellando in questo caso gli algoritmi a chiave asimmetrica[^AlgoritmiAsimmetrici].
  
[^AlgoritmiAsimmetrici]:  Il modulo di crittografia a chiave asimmetrica, concerne quella categoria di algoritmi in grado di garantire l'autenticità.

Il framework in questo caso mette a disposizione un solo algoritmo *RSA*.
Quest'ultimo viene utilizzato sia per quanto riguarda la generazione delle chiavi stesse da utilizzare durante le operazioni di crittografia, sia per la generazioni delle chiavi stesse.

La libreria separa quelle che sono le caratteristiche statiche degli algoritmi, da quelle che sono le modalità in cui queste vengono sfruttate per l'implementazione delle operazioni crittografiche.
Gli algoritmi appena descritti modellano le prime e di seguito verranno esposti i cifrari, che implementano le seconde.

![Algorithm](assets/images/library-cryptography/algoritm/algorithm.png)

- `Cipher`
Trait che modella un generico cifrario, esponendo i metodi comuni a tutte le tipologie di cifrari identificati, questi come `CryptographicAlgorithm` rappresenta la *root* per tutte le diverse categorie di cifrari: `SymmetricCipher` e  `AsymmetricCipher`.

Infatti, ad ogni algoritmo di encryption [^EncryptionVSHashing] identificato (algoritmi a chiave simmetrica, o asimmetrica) è stato associato un cifrario che incapsula la logica con cui l'algoritmo viene utilizzato per criptare e successivamente decriptare una segreto.

[^EncryptionVSHashing]: Nel caso del framework le funzioni hash vengono utilizzate per evitare all'utente di salvare le password in chiaro nel database, tuttavia essendo queste non invertibili, perchè l'utente possa controllare l'integrità della password non potrà decriptare il valore hash ma fare un confronto tra il valore hash della password passata in input e quello salvata sul database.

I cifrari sono stati implementati cercando di incapsulare le operazioni comuni in un abstract class applicando il pattern `Template Method` per seguire il principio *DRY*, tuttavia, sebbene il paradigma funzionale si appoggi sull'idea che ogni cosa dovrebbe essere una funzione, in questo caso si è preferito non implementare factories per la creazione di oggetti, in quanto avrebbe potuto impendire l'estensibilità del framework o, minarne la consistenza.

L'entità adibita a tale compito è il `BasicCipher`, classe astratta che fornisce le implementazioni dei metodi di *encryption* e *decryption* oltre che un metodo, `crypto`,  per l'implementazione dell'operazione di cifratura.

  Di seguito vengono descritti i cifrari implementati, nell'ordine prima quelli simmetrici e poi quelli asimettrici.
  
  - `CaesarCipher`
  Cifrario che modella la logica per l'implementazione di un cifrario di Cesare.
  Si tratta dell'unico cifrario a non avvalersi dell'implementazione di base per i cifrari a causa della natura intrinseca dell'algoritmo.
  Questi infatti al contrario degli altri nasce per essere utilizzato con un numero intero come segreto e non con una stringa.
  
  - `AESCipher`, `DESCipher` ed `RSACipher`
 Sono i cifrari che sfruttando rispettivamente gli algoritmi `AES`, `DES` ed `RSA`per l'implementazione delle operazioni di cifratura.
`RSACipher`: oltre alle operazioni base della crittografia, un cifrario relativo alla crittografia asimmetrica deve mettere a disposizione un insieme di operazioni per la gestione delle chiavi, sfruttando delle specifiche strutture, la più importante delle quali è `KeyGenerator`.
    - `KeyGenerator`
  Componente del sistema adibito alla gestione delle chiavi da utilizzare durante le operazioni di crittografia con chiave asimmetrica.
  Questo componente per evitare incosistenze deve essere accessibile solo dal cifrario che lo utilizza e permettere di generare, o caricare delle chiavi pre-esistenti.
      - `KeyPair`: entità che rappresenta una coppia di chiavi: una privata da utilizzare durante l'encryption ed una pubblica per permettere la decryption.
     
![Cipher](assets/images/library-cryptography/cipher/cipher.png)
### User
- `User`
Il trait `User` modella un generico utente, il quale è caratterizzato da uno `userName` e da una `password`.
- `UserInformation`
Il trait `UserInformation` modella d'altro canto l'informazione che deve essere salvata, questo estende il trait `User` aggiungendo un nuovo campo che permette di memorizzare l'algoritmo utilizzato per la cifratura della password.
Difatti, in questo a differenza di quanto accade per l'entità precedente, la password viene salvata criptata (se l'utente ha deciso di farlo) per evitare attacchi di tipo *credential stuffing*.
![User Builder e UserInformation](assets/images/library-user/user/user_model.PNG)

Per garantire la consistenza delle informazioni e il rispetto delle policy sono stati implementati due macro tipologie di builder in grado di implementare la costruzione da un lato degli utenti e dall'altro delle informazioni ad esse relative.
I builder afferenti alle due categorie builder, così come tutti i builder del framework, estendendono l'interfaccia  `Builder`.
Nello specifico per l'implementazione dei builder degli utenti si è scelto nuovamente di applicare il principio *Dry* mettendo a disposizione una classe astratta `UserBuilder`che implementi i metodi di base comuni a tutti i builder degli utenti.
Vengono quindi creati due builder specifici per gli utenti: il primo `UserCostumBuilder` in grado di permettere la costruizione di utenti a partire da credenziali e opzionalmente policy scelte dall'utente.
Il secondo  `UserAutoBuilder` che al contrario permette la definzioni di un numero prestabilito di utenti a partire da un set di policy definite dagli utenti.
Quest'ultimo è infatti in grado di generare credenziali randomiche che rispettino le *regole* scelte dall'utilizzatore per il numero richiesto di utenti.

Come per gli User anche per le `UserInformation` è stato implementato un builder in grado assicurarne una corretta istanziazione, questi prende il nome di: `UserInformationBuilder`.
![User Builder e UserInformation](assets/images/library-user/user/user_builder.PNG)


## Pattern di progettazione
### Creazionali
- `Builder` 
Il pattern `Builder` è stato usato in gran parte del framework sia nell'accezione di Scala attraverso l'utilizzo di valori predefiniti nei costruttori, sia nella versione tradizionale del pattern in quanto la maggior parte degli oggetti da costruire
non richiedevano di specificare tutti i parametri di configurazione, restituendo un oggetto di un tipo generale.
Un esempio è `BruteForceAttackBuilder`, il quale costruisce un oggetto di tipo `Attack` e non `BruteforceAttack`.

- `Chaining Method`
Il pattern `Chaining Method` è stato usato implementando i metodi dei builder con il metodo `builderMethod`,
il quale restituisce un riferimento al tipo più specifico possibile del `Builder` su cui è chiamato.
L'uso di questo pattern permette di potenziare la notazione infissa dei metodi di Scala e raggiungere una scrittura di
codice simile al linguaggio naturale.
Un esempio di ciò è la seguente stringa (fonte: `BruteForceAttackBuilderTest`):
```
(new BruteForceAttackBuilder() against myProxy usingAlphabet myAlphabet maximumLength maximumPasswordLength hashingWith HashFunction.MD5() jobs 4 logTo consumer timeout Duration.Zero).executeNow()
```
Si può vedere che la configurazione della costruzione di un attacco (cioè il codice all'interno delle parentesi tonde
più esterne) si avvicina molto alla descrizione a parole (in lingua inglese) di ciò che si richiede.

- `Factory`
Il pattern `Factory` è stato utilizzato nella libreria nell'accezione di Scala, ossia utilizzando il metodo `apply` nei *companion objects* per istanziare oggetti mantenendo privata l'implementazione delle classi.

- `Singleton`
Il pattern `Singleton`, come accaduto per il pattern `Factory` non è stato implementato nella sua versione tradizionale, bensì è stato declinato sfruttando le potenzialità messe a disposizione dal linguaggio Scala.
Nello specifico, gli *Object* sono stati utilizzati spesso come `Singleton` nella libreria, un esempio si può notare nel `KeyGenerator`, struttura responsabile della creazione delle chiavi necessarie alle implementazioni di crittografia asimettrica.
Tale oggetto infatti è stato reso visibile solo a livello di package ed accessibile dal solo cifrario che sfrutta un algoritmo di crittografia asimmetrica, ossia il `RSACipher` che quindi risulta essere l'unico punto di accesso globale al generatore di chiavi. La scelta, è stata fatta per assicurare l'assenza di incosistenze ed errori nella generazione della coppia di chiavi crittografiche.

### Strutturali 

- `Decorator`
Il pattern `Decorator` è stato usato nella dichiarazione del trait `Alphabet` rispetto al trait `Set[String]` di Scala.
Infatti, oltre a estendere il trait, contiene anche un oggetto dello stesso tipo e infatti le operazioni su un alfabeto
sono delegate al set interno, di cui però non si conosce il tipo specifico.

### Comportamentali

- `Template Method`
Il pattern `Template Method` è stato utilizzato nella realizzazione dei cifrari, per poter portare a fattore comune il comportamento di questi nella realizzazione delle operazioni di cifratura evitando inutili duplicazioni di codice.
Questi infatti estendono da una classe astratta `BasicCipher` la quale espone le implementazioni di base dei metodi `encrypt` e `decrypt` lasciando ai cifrari specifici l'implementazione del metodo `crypto` responsabile dell'implementazione delle operazioni di cifratura.
Tali metodi sono infatti invarianti rispetto ai cifrari proposti[^CaesarCipher].

[^CaesarCipher]: L'unica eccezione è rappresentata dal `CaesarCipher` in quanto unico cifrario a non estendere dalla classe astratta per la natura intrinseca dell'algoritmo.

## Client

### ScalaFx Task

La simulazione di un attacco potrebbe durare per molto tempo, quindi non sarebbe
accettabile bloccare il thread della gui con una computazione impegnativa.

La libreria ScalaFx offre `Task[T]` una classe utility analoga a `SwingWorker` che permette di
eseguire una computazione non bloccante su un thread diverso da quello di EDT.

Permette inoltre di comunicare con la GUI tramite il metodo `updateMessage` che permette di aggiornare
un StringProperty, `messageProperty`, disponibile alla GUI.

In questo modo si riesce a eseguire la simulazione e stampare su un elemento della gui i log della sua esecuzione.

L'esecuzione del task viene effettuata da un component `SimulationRunnerComponent` responsabile di avviare e di fermare il task in esecuzione.
Questa classe utilizza `ExecutorService` istanziato con thread singolo (`newSingleThreadExecutor`) per gestire il task.

![Simulation Runner](assets/images/runner.png)

### Repository

Si possono persistere gli utenti in due modi: Database SQL e Database Mongo.
Per facilitare il deploy dell'applicazione, è stato deciso di utilizzare i database in-memory:

 - SLQ - con h2 database
 - MongoDB - con flapdoodle embedded mongo

Entrambi sono delle dipendenze Java e quindi sono state integrate con il codice Scala.

Queste dipendenze sono state pensate per gli ambienti di testing, 
tuttavia con qualche accorgimento sono state adattate ad essere utilizzare nell'applicativo.

L'accesso alla persistenza è stato gestito nelle classi `Repository` ispirate a *Domain Driven Design*, forniscono
un'interfaccia che astrae l'accesso ai dati e rende trasparente l'implementazione.

![Repositories](assets/images/repositories.png)

### Service

Per gestire la business logic dell'applicativo sono state create delle classi `Service` ispirate a *Domain Driven Design*.
  - `Properties Service` - cui compito è quello di fornire un'astrazione per il caricamente e l'accesso alle properties definite nel file di configurazione esterna
    `application.properties`
  - `SimulationRunner` - cui compito è quello di astrarre la gestione dell'esecuzione del task della simulazione
  
### Error Handling Funzionale

Nel client si cercava di preferire error handling funzionale.
In particolare sono stati usati i costrutti `Try` e `Using`.

### Promise

Il driver di MongoDB per Scala 2 (portato nell'ambiente del progetto con cross building) 
esponse un API asincrona basata sul pattern `Observer`. Per le esigenze della simulazione è stato deciso
di rendere sequenziali le operazioni con il database. Per questo motivo si è ricorso al costrutto `Promise`.
Semplicemente ogni volta che si sesegue un'operazione su MongoDB viene restituito un `Observable` e viene creata 
una `Promise` che viene risolta in certe condizioni (che dipendono dall'operazioni in questione), oppure rigettata nel caso d'errore.

In questo modo eseguendo `Await.result` sulla promise, il flusso diventa sequenziale.

### Factory

Per costruire gli attacchi preconfigurati è stato utilizzato il pattern `Factory`.
La classe `AttacksFactory` viene istanziata con `UserProvider` e `StatisticsConsumer` necessari per costruire `AttackBuilder` richiesto.

### Observer

Per gestire le notifiche dinamiche tra ViewModel e Model, è stata creata la classe `ObservableListBuffer[A]` che permette di eseguire delle operazioni
semplici con una collection (mutable) di elementi di tipo `A` notificando i listener su quel evento.

In particolare `ObservableListBuffer[A]` supporta i listener per gli eventi
  - add element
  - remove element
  
I listener si possono passare in costruzione utilizzando uno degli `apply` nel suo Companion Object oppure anche post-costruzione con i metodi appositi.

![ObservableListBuffer](assets/images/observable.png)

## Organizzazione del codice

Il codice del progetto è diviso in due marco-package: `client` e `library`

### Library 
L'organizzazione del codice della libreria può essere riassunta con il seguente diagramma:

![Client Packages](assets/images/library-packages.svg)

### Client

L'organizzazione del codice del client può essere riassunta con il seguente diagramma:

![Client Packages](assets/images/client-packages.png)
