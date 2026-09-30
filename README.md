# Gestion de magasin

**Étudiant :** Huu Loc TRAN  
**Formation :** Ing1-Apprenti  
**Groupe :** BDML 1  
**Enseignante :** Madame Georgina ABI SEJAAN  
**Environnement :** Java et NetBeans

## Présentation

Un magasin en console : un client peut consulter les produits, ajouter des articles à son panier, retirer un article et passer une commande.

J’ai également ajouté la possibilité de changer de client. Loc et Alexandre peuvent utiliser le programme à tour de rôle, chacun avec son propre panier, tout en partageant le même stock.

## Organisation des classes


| Classe     | Responsabilité                                                                                |
| ---------- | --------------------------------------------------------------------------------------------- |
| `Produit`  | Contient l’identifiant, le nom, le prix unitaire et la quantité disponible.                   |
| `Client`   | Contient l’identité du client et son panier personnel.                                        |
| `Panier`   | Gère les ajouts, les retraits, l’affichage et le calcul du total.                             |
| `Commande` | Conserve les informations du client, les produits commandés et le total au moment de l’achat. |
| `Magasin`  | Regroupe les produits et permet de les rechercher par leur nom.                               |
| `Main`     | Crée les objets et gère l’interaction avec l’utilisateur.                                     |


Ces six classes appartiennent au package `gestionMagasin`. La classe `exo4.Exo4` sert de point d’entrée alternatif et appelle `gestionMagasin.Main`.

## Mes choix de conception



### Séparer les responsabilités

J’ai réparti le programme entre plusieurs classes plutôt que de placer toute la logique dans `main`. Par exemple, le panier calcule son total, tandis que le magasin recherche un produit. Le menu appelle ces méthodes selon le choix de l’utilisateur.

Les attributs sont privés pour regrouper l’accès aux données dans les méthodes de chaque classe. Les getters et setters demandés dans le sujet permettent de consulter et de modifier les informations des produits et des clients.

### Utiliser des listes de produits

J’ai choisi `ArrayList<Produit>` pour représenter les produits du magasin, du panier et de la commande. Une liste permet de gérer plusieurs produits et de parcourir facilement son contenu avec une boucle `for`.

Dans le panier, chaque ajout correspond à **une unité**. Ajouter deux fois le même produit crée deux entrées dans la liste. Cette représentation reste simple pour ce TP ; l’affichage ne regroupe donc pas les articles identiques sur une seule ligne.

### Associer un panier à chaque client

Chaque objet `Client` possède son propre panier :

```java
private final Panier panier = new Panier();
```

Les clients sont créés une seule fois au démarrage. Après la sélection d’un client, le menu utilise son panier existant :

```java
Panier panier = client.getPanier();
```

Cette organisation évite de mélanger les achats de plusieurs clients. Un changement de client conserve les paniers pendant l’exécution. Le mot-clé `final` empêche de remplacer la référence du panier, mais son contenu reste modifiable.

### Partager le stock et réserver les unités

Un seul objet `Magasin` est partagé entre les clients. La quantité d’un produit représente son **stock libre**.


| Action                      | Conséquence sur le stock libre                                        |
| --------------------------- | --------------------------------------------------------------------- |
| Ajouter une unité au panier | Le stock diminue de 1.                                                |
| Retirer une unité du panier | Le stock augmente de 1.                                               |
| Changer de client           | Les réservations sont conservées.                                     |
| Passer commande             | Les unités réservées sont vendues, sans nouvelle diminution du stock. |
| Quitter le programme        | Les réservations des paniers non commandés sont annulées.             |


Ce choix empêche les deux clients de réserver une même unité disponible. Après une commande, `viderApresCommande()` vide le panier sans remettre les articles vendus en stock.

### Calculer le total et conserver la commande

Le total du panier est calculé en additionnant le prix de chaque unité :

```java
int total = 0;
for (Produit produit : produits) {
    total += produit.getPrix();
}
```

La correction importante est l’utilisation de `+=`. L’écriture `total =+ produit.getPrix()` remplaçait la valeur du total à chaque passage au lieu de l’accumuler.

J’ai conservé des prix de type `int`, exprimés en euros entiers, comme dans mon code initial. Cette version ne gère donc pas les centimes.

Lors de la création d’une commande, les informations du client et des produits sont copiées. Ainsi, vider le panier ou modifier ensuite un produit ne change pas les informations de cette commande. Son total est calculé à partir du panier et son identifiant est incrémenté après chaque commande, quel que soit le client.

### Organiser les menus avec deux boucles

La boucle extérieure permet de sélectionner un client ou de quitter le programme. La boucle intérieure affiche le menu du magasin pour le client sélectionné.

Le choix « Changer de client » quitte uniquement le menu du magasin. Le programme revient alors à la sélection du client, sans recréer les objets ni vider les paniers.

J’ai utilisé `switch` pour associer chaque choix à une action. Les saisies sont lues avec `Scanner.nextLine()`, puis les choix sont comparés sous forme de chaînes. Une saisie comme `abc` est ainsi traitée comme un choix invalide sans provoquer d’exception de conversion.

## Fonctionnement

Au démarrage, l’utilisateur choisit entre Loc et Alexandre. Le menu du client sélectionné propose les actions suivantes :


| Choix | Action                            |
| ----- | --------------------------------- |
| 1     | Afficher les produits disponibles |
| 2     | Ajouter une unité au panier       |
| 3     | Afficher le panier et son total   |
| 4     | Passer une commande               |
| 5     | Changer de client                 |
| 6     | Supprimer une unité du panier     |
| 0     | Quitter le programme              |


La recherche d’un produit ignore les différences entre majuscules et minuscules ainsi que les espaces au début et à la fin du nom.

Le programme affiche un message lorsque le choix est invalide, le produit est introuvable, le stock est épuisé, le produit à retirer est absent du panier ou le client tente de commander un panier vide.

## Exécution dans NetBeans

1. Ouvrir le projet Java dans NetBeans. Si seules les sources sont présentes, créer un projet **Java Application**.
2. Placer le package `gestionMagasin` dans le dossier source du projet : `src` pour un projet Ant ou `src/main/java` pour un projet Maven.
3. Ajouter le package `exo4` si le point d’entrée alternatif est conservé.
4. Utiliser l’encodage **UTF-8**, notamment pour les méthodes `getQuantité()` et `setQuantité()`.
5. Exécuter la classe `gestionMagasin.Main`. Les saisies se font dans la fenêtre **Output**, en validant chaque réponse avec Entrée.

Il faut conserver une seule version de chaque classe : les fichiers portant le suffixe `(1)` sont des doublons et ne doivent pas être ajoutés au projet.

## Scénarios de vérification

Les résultats attendus suivants permettent de vérifier le comportement du programme. Les produits de départ sont Coca à 10 EUR et Pepsi à 20 EUR, avec deux unités de chaque produit.


| Scénario                                               | Résultat attendu                                                       |
| ------------------------------------------------------ | ---------------------------------------------------------------------- |
| Ajouter un Coca et un Pepsi                            | Total du panier : 30 EUR.                                              |
| Retirer le Coca                                        | Total : 20 EUR ; une unité de Coca est remise en stock.                |
| Tenter de réserver trois Coca                          | Le troisième ajout est refusé.                                         |
| Loc ajoute un Coca, puis Alexandre consulte son panier | Le panier d’Alexandre est vide.                                        |
| Revenir à Loc                                          | Son Coca est toujours dans son panier.                                 |
| Loc et Alexandre ajoutent chacun un Coca               | Le stock libre de Coca atteint zéro.                                   |
| Commander un panier vide                               | La commande est refusée.                                               |
| Passer une commande puis afficher le panier            | Le panier est vide et les unités vendues ne sont pas remises en stock. |
| Saisir un choix ou un nom de produit inconnu           | Un message est affiché et le menu reste utilisable.                    |




## Résultats observés dans la console

J’ai effectué une session de test avec le client Alexandre. Les extraits suivants proviennent de cette exécution. Les menus répétés ont été retirés pour faciliter la lecture ; les messages et les valeurs sont conservés tels qu’affichés.

### Sélection du client et affichage des produits

Une saisie vide est refusée, puis le choix `2` sélectionne Alexandre. Le choix `1` du menu magasin affiche les produits.

```text
Votre choix :
Client inconnu.
```

```text
Votre choix : 2

Bienvenue Alexandre !
```

```text
Votre choix : 1
Produit{id=1, nom=Coca, prix=10, quantit�=2}
Produit{id=2, nom=Pepsi, prix=20, quantit�=2}
```



### Ajout au panier et calcul du total

Le nom `coca`, saisi en minuscules, est reconnu. L’affichage du panier confirme la présence d’une unité et un total de 10 EUR.

```text
Votre choix : 2
Nom du produit a ajouter :
coca
Une unite ajoutee au panier de Alexandre.
```

```text
Votre choix : 3
Coca : 1 unite, 10 EUR
Total : 10 EUR
```



### Validation de la commande

Le choix `4` crée la commande numéro 1 au nom d’Alexandre. Une seconde tentative est refusée parce que le panier a été vidé après la première commande.

```text
Votre choix : 4
Commande numero 1
Client{id=2, nom=Alexandre, email=alex@gmail.com}
Produit{id=1, nom=Coca, prix=10, quantit�=1}
Total de la commande : 10 EUR
```

```text
Votre choix : 4
Impossible de commander un panier vide.
```



### Retrait d’un article

La suppression d’un Coca absent du panier est refusée. Après un nouvel ajout, la suppression d’une unité réussit.

```text
Votre choix : 6
Nom du produit a supprimer :
coca
Produit absent du panier.
```

```text
Votre choix : 2
Nom du produit a ajouter :
coca
Une unite ajoutee au panier de Alexandre.
```

```text
Votre choix : 6
Nom du produit a supprimer :
coca
Une unite supprimee du panier.
```



### Gestion des saisies vides et fin du programme

Une saisie vide dans le menu du magasin est refusée sans interrompre le programme. Une recherche vide lors du retrait ne supprime aucun article. Le choix `0` permet ensuite de quitter normalement.

```text
Votre choix :
Choix invalide.
```

```text
Votre choix : 6
Nom du produit a supprimer :

Produit absent du panier.
```

```text
Votre choix : 0
Au revoir !
BUILD SUCCESSFUL (total time: 2 minutes 48 seconds)
```



### Bilan de cette session


| Vérification                         | Observation                         |
| ------------------------------------ | ----------------------------------- |
| Saisie vide à la sélection du client | Refus et retour au menu             |
| Sélection d’Alexandre                | Message de bienvenue au bon nom     |
| Recherche avec `coca` en minuscules  | Produit trouvé et ajouté            |
| Total pour un Coca                   | 10 EUR                              |
| Création d’une commande              | Client, produit et total affichés   |
| Nouvelle commande après validation   | Refus du panier vide                |
| Suppression d’un article absent      | Message explicite                   |
| Ajout puis retrait d’un article      | Retrait confirmé                    |
| Sortie                               | Fin normale avec `BUILD SUCCESSFUL` |


Cette trace ne vérifie pas encore la séparation des paniers lors d’un changement de client, le refus d’un ajout lorsque le stock est épuisé ni les valeurs du stock après un retrait. Ces points restent couverts par les scénarios de vérification ci-dessus, à exécuter séparément.

## Bilan

Ce projet m’a permis de travailler les classes, les constructeurs, l’encapsulation, les listes et les relations entre objets. L’ajout de plusieurs clients m’a surtout aidé à distinguer les données propres à chaque client, comme son panier, des données partagées, comme le stock du magasin.