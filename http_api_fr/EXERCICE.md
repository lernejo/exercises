# API HTTP : production et consommation

> Préfixé par &#x1F4D8;, des "checkpoints" pour vous aider à vérifier que vous avez tout bon.

Cet exercice a pour but de voir l'utilisation d'API HTTP JSON, en

* production : le code démarre un serveur HTTP qui répond à des requêtes spécifiques
* consommation : le code appelle un serveur HTTP distant

Nous allons utiliser :

* **Spring web-mvc** pour gérer l'exposition de l'API serveur
* **retrofit** comme client HTTP

Le thème de cet exercise est de construire un site de rencontres mondial (simplifié).  
L'algorithme de "*matching*" prendra en compte l'âge des profils (des profils de même âge à 4 ans près vont "*matcher*")
.  
L'astuce cependant sera de ne pas demander l'âge à l'utilisateur lors de l'inscription.  
Il faudra utiliser l'API publique https://agify.io afin de récupérer l'âge du profil par rapport à son nom et son
pays.  
Par exemple :

* Isabelle, Française préférant les hommes (agify donne 51 ans)  
  peut matcher avec
* Emile, Américain préférant les femmes (agify donne 53 ans)

## Notation

La notation se découpera en plusieurs parties :

* le projet compile
* le projet démarre (la méthode `fr.esiea.ex4A.Launcher#main` qui vous est fournie doit démarrer un serveur HTTP sur le
  port 8080)
* les 2 APIs `POST /api/inscription` et `GET /api/matches` décrites ci-dessous sont
    * disponibles
    * conforment aux schémas donnés
    * cohérentes (un appel à `GET /api/matches` après le démarrage de l'application ne donne rien,
      car `POST /api/inscription` n'a pas été appelée avant)
    * fonctionnelles (un appel à `GET /api/matches` retournent les "*matchs*" des personnes déjà inscrites tels que
      décrits dans l'énoncé)
* la partie sur le cache a été réalisée
* L'historique **GIT** lisible et propre
    * pas de message de commit sans _**intention**_
    * pas de commit successifs avec le même message
    * (-0.5 point de pénalité par commit ne respectant pas ces règles)
    * Voir cet article pour plus d'information https://chris.beams.io/posts/git-commit/
* Le **Style** doit permettre de lire le code facilement
    * les méthodes doivent faire moins de 10 lignes
    * les classes moins de 70 lignes (-1 point de pénalité pour les écarts)
    * pas de champs mutables (tous les champs doivent être marqués final)
    * pas de champ ou méthode statique (sauf pour la méthode `main`)
* l'intégration continue est fonctionnelle
    * les Badges de build et de couverture sont disponibles dans le README.md
    * la couverture du code est > 90%

Un front-end basique (HTML, CSS, JS) vous est fourni.  
Vous êtes libre de le modifier ou même de le remplacer par une technologie de votre choix (vue, preact, etc.).  
La note ne tiendra pas compte de cette partie.

## Partie 0 - Repository GitHub

* créer un nouveau dépôt Git public sur la plateforme GitHub avec le nom **api_training** initialisé avec un fichier
  README.md (case à cocher dans le formulaire de création de dépôt)
* cloner ce nouveau dépôt en utilisant l'url SSH
* la branche par défaut est la branche `main` c'est sur celle-ci que nous allons travailler

## Partie 1 - Création de la structure du projet Java et mise ne place de la CI (15 min)

Les archetypes Maven sont des templates de projet qui permettent de commencer rapidement à coder sans avoir à construire
le pom.xml et les différentes parties qui composent un nouveau projet.

Pour ce projet, nous allons utiliser l'archetype `com.github.lernejo:tp-maven-archetype:4A.2021.RC1` disponible dans
le *repository* JitPack. Pour cela :

* Ajouter Jitpack comme dépôt dans le fichier de configuration de Maven
    * fichier **~/.m2/settings.xml**

```xml

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <profiles>
        <profile>
            <id>jitpack</id>
            <repositories>
                <repository>
                    <id>jitpack</id>
                    <url>https://jitpack.io</url>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>jitpack</activeProfile>
    </activeProfiles>
</settings>
```

* Générer le projet grâce à l'archetype `com.github.lernejo:tp-maven-archetype:4A.2021.RC1` présent sur le
  dépôt https://jitpack.io.

Voici la commande complète permettant de générer un projet à partir de cet archetype :

```
mvn archetype:generate -B \
    -DarchetypeGroupId=com.github.lernejo \
    -DarchetypeArtifactId=tp-maven-archetype \
    -DarchetypeVersion=4A.2021.RC1 \
    -DgroupId=fr.esiea \
    -DartifactId=tp-4A-2020 \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=fr.esiea.ex4A
```

* copier l'intégralité des fichiers (y compris les fichiers et dossiers commençant par `.`) à la racine du dépôt Git
  local
* par exemple, le fichier **pom.xml** doit se trouver à la racine de votre projet
* soyez vigilant sur le bit d'exécution du fichier **mvnw** (autrement la CI échouera à construire votre projet)

* faire un commit avec l'ensemble de ces ajouts appelé **Init Java Project & CI**
* pusher la branche
* &#x1F4D8; un workflow GitHub de CI se déclenche et doit finir en succès
* &#x1F4D8; le coverage sur Codecov doit être d'environ 90% (tout sauf la méthode main)

* ajouter les deux badges (GitHub workflow et Codecov) dans le fichier README.md
* faire un commit de ce changement avec le message "Add live badges"
* pusher la branche

### :bulb: Pull Request

À partir de ce point, il est recommandé d'utiliser le mécanisme de pull request afin d'éviter de pousser un code qui ne
fonctionne pas sur la branche principale.

Pour cela, dès que vous souhaitez faire une nouvelle modification :

* se placer sur la branche principale (`main`)
* vérifier que vous êtes à jour (`fetch` + `status` et `pull` si nécessaire)
* créer une nouvelle branche `git checkout -b nom_de_la_feature`
* faire vos modifications + commit + push
* une fois la branche push, dans l'interface GitHub vous verrez une option permettant de créer une Pull Request à partir
  de cette nouvelle branche
* une fois créée, la Pull request peut être modifiée autant que nécessaire (les checks vous permettent de voir si le
  build passe, c'est-à-dire si la compilation est OK et si vos tests aussi)
* en installant l'app [GitHub Codecov](https://github.com/apps/codecov), la différence de coverage apparaitra également
  sur la PR
* une fois que vous êtes satisfait, choisissez `Rebase & merge` pour ajouter les commits de la PR à la branche
  principale
* vous pouvez supprimer la branche (sur GitHub, mais aussi en local)

### :bulb: Le code fourni dans l'archetype

L'archetype contient par défaut du code montrant comment :

* réaliser une API HTTP JSON avec **Spring web-mvc** (voir `GET /hello`, http://localhost:8080/hello)
* présenter un contenu static (voir http://localhost:8080/)
* tester une API HTTP JSON avec **Spring-test** (`MockMvc`)
* "*mocker*" un service, c'est-à-dire substituer à une implémentation un *proxy* dont le comportement peut être changé
  avec du code de test (voir https://site.mockito.org/)

L'application démarre un serveur HTTP qui écoute sur le port 8080 en lançant la méthode `Launcher#main`.

# Partie 2 - Coder les APIs HTTP JSON

Pour faire fonctionner [le frontend](http://localhost:8080/), construire deux APIs :

* `POST /api/inscription` acceptant comme entrée (`body`) un document JSON valide selon ce schéma

```json
{
    "$schema": "http://json-schema.org/schema#",
    "type": "object",
    "properties": {
        "userEmail": {
            "type": "string"
        },
        "userName": {
            "type": "string"
        },
        "userTweeter": {
            "type": "string"
        },
        "userCountry": {
            "type": "string",
            "pattern": "^[A-Z]{2}$"
        },
        "userSex": {
            "type": "string",
            "enum": [
                "M",
                "F",
                "O"
            ]
        },
        "userSexPref": {
            "type": "string",
            "enum": [
                "M",
                "F",
                "O"
            ]
        }
    },
    "required": [
        "userEmail",
        "userName",
        "userTweeter",
        "userCountry",
        "userSex",
        "userSexPref"
    ]
}
```

Par exemple :

```json
{
    "userEmail": "machin@truc.com",
    "userName": "machin",
    "userTweeter": "machin45",
    "userCountry": "FR",
    "userSex": "M",
    "userSexPref": "M"
}
```

* `GET /api/matches?userName={userName}&userCountry={userCountry}` retournant une liste des profils "*matchant*", valide
  par rapport à ce schéma :

```json
{
    "$schema": "http://json-schema.org/schema#",
    "type": "object",
    "properties": {
        "name": {
            "type": "string"
        },
        "twitter": {
            "type": "string"
        }
    },
    "required": [
        "name",
        "twitter"
    ]
}
```

Par exemple :

```json
{
    "name": "machin",
    "twitter": "machin45"
}
```

Vous pouvez ici retourner des données fixes ou aléatoires, le comportement définitif basé sur Agify sera à réaliser par
la suite.

* &#x1F4D8; le frontend fourni (http://localhost:8080) fonctionne avec les données simulées

## Partie 3 - Client HTTP

* Ajouter la dépendance **retrofit** à votre projet: `com.squareup.retrofit2:retrofit:2.9.0`
* Créer une nouvelle interface afin de requêter l'API Agigy en vous servant de la documentation
  officielle : https://square.github.io/retrofit/
* Coder le test d'intégration (nom de la classe de test finissant par **IT**) permettant de tester ce client, il est
  attendu que
  **Spring** ne soit pas utilisé dans ce test
* Ajouter ce client HTTP comme bean dans le contexte de Spring
    * pour cela ajouter une méthode comme celle-ci dans la classe Launcher (en considérant que votre interface
      s'appelle `AgifyClient`)
      ```java
      @Bean
      AgifyClient agifyClient() {
          Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api.github.com/")
          .build();
  
          return retrofit.create(AgifyClient.class);
      }    
      ```

* &#x1F4D8; l'application (`Launcher#main`) démarre sans erreur

## Partie 4 - emboiter les pièces du puzzle

* créer une nouvelle classe annotée avec `@Service` avec les méthodes nécessaires pour satisfaire aux données des 2 APIs
  et contenant la logique entre ces deux APIs
* cette classe prendra comme paramètre de constructeur un objet de type `AgifyClient`
* cette classe pourra facilement être testée (par un test unitaire, classe de test finissant par **Test**) en créant un
  mock de type `AgifyClient`
* utiliser ce service comme paramètre du *controller* codé dans la **partie 2** afin de remplacer le comportement
  temporaire par le comportement définitif (que vous venez de coder dans la classe *service*)
* modifier le test d'intégration du *controller* en conséquence

* &#x1F4D8; le frontend fourni (http://localhost:8080) fonctionne comme attendu, le POC (Proof Of Concept) est fini !

## Partie 5 - mise en place d'un cache

L'API Agify est limité à 1000 appels par jour.  
Notre site de rencontre devient de plus en plus populaire.  
La limite d'appels est atteinte régulièrement.  
Il faut donc trouver une solution afin :

* qu'aucune erreur ne parviennent aux utilisateurs
* ne pas sur-solliciter un système gratuit

Pour cela :

* mettre en place (si ce n'est pas déjà fait) un cache en mémoire stockant les âges déjà requêtés, indexés par nom et
  par pays.
* le plus simple est d'utiliser une `HashMap` avec comme clé un objet *anémique* dont l'identité (`#equals`
  & `#hashCode`)
  correspond à un nom et un pays
* d'autres solutions peuvent être élaborées si vous le souhaitez

## The end

Vous pouvez aller plus loin si vous le souhaitez, mais vous ne serez noté que sur les points énoncés plus haut.  
Les APIs, si vous les changez, doivent rester compatibles avec les schémas de l'exercice.  
C'est à dire que

* des champs peuvent être ajoutés dans les réponses
* des champs optionels peuvent être ajoutés dans les requêtes
* les champs existants ne peuvent pas être supprimés ou renommés
