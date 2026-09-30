/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gestionMagasin;

import java.util.Scanner;

/**
 *
 * @author lochu
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Un seul magasin : le stock est partage entre les clients
        Magasin magasin = new Magasin();

        magasin.ajouterProduit(new Produit(1, "Coca", 10, 2));
        magasin.ajouterProduit(new Produit(2, "Pepsi", 20, 2));

        // Les clients sont crees une seule fois
        Client loc = new Client(1, "Loc", "loc@gmail.com");
        Client alex = new Client(2, "Alexandre", "alex@gmail.com");

        int prochainIdCommande = 1;
        boolean applicationActive = true;

        // Boucle principale : selection du client
        while (applicationActive) {
            System.out.println();
            System.out.println("--- Choisir un client ---");
            System.out.println("1. Loc");
            System.out.println("2. Alexandre");
            System.out.println("0. Quitter le programme");
            System.out.print("Votre choix : ");

            if (!sc.hasNextLine()) {
                break;
            }

            String choixClient = sc.nextLine().trim();
            Client client;

            switch (choixClient) {
                case "1":
                    client = loc;
                    break;

                case "2":
                    client = alex;
                    break;

                case "0":
                    applicationActive = false;
                    continue;

                default:
                    System.out.println("Client inconnu.");
                    continue;
            }

            // Recuperer le panier existant du client selectionne
            Panier panier = client.getPanier();

            System.out.println();
            System.out.println("Bienvenue " + client.getNom() + " !");

            boolean menuClientActif = true;

            // Boucle du magasin pour le client selectionne
            while (menuClientActif && applicationActive) {
                System.out.println();
                System.out.println(
                        "--- Menu Magasin : " + client.getNom() + " ---"
                );
                System.out.println("1. Afficher les produits disponibles");
                System.out.println("2. Ajouter un produit au panier");
                System.out.println("3. Afficher mon panier");
                System.out.println("4. Passer ma commande");
                System.out.println("5. Changer de client");
                System.out.println("6. Supprimer une unite du panier");
                System.out.println("0. Quitter le programme");
                System.out.print("Votre choix : ");

                if (!sc.hasNextLine()) {
                    applicationActive = false;
                    break;
                }

                String choix = sc.nextLine().trim();

                switch (choix) {
                    case "1": {
                        magasin.afficherProduitsDisponibles();
                        break;
                    }

                    case "2": {
                        System.out.println("Nom du produit a ajouter :");

                        if (!sc.hasNextLine()) {
                            applicationActive = false;
                            break;
                        }

                        String nomProduit = sc.nextLine();
                        Produit produit =
                                magasin.trouverProduitParNom(nomProduit);

                        if (produit == null) {
                            System.out.println("Produit introuvable.");
                        } else if (produit.getQuantité() <= 0) {
                            System.out.println("Stock epuise.");
                        } else {
                            panier.ajouterProduit(produit);

                            System.out.println(
                                    "Une unite ajoutee au panier de "
                                    + client.getNom() + "."
                            );
                        }
                        break;
                    }

                    case "3": {
                        panier.afficherPanier();
                        break;
                    }

                    case "4": {
                        if (panier.estVide()) {
                            System.out.println(
                                    "Impossible de commander un panier vide."
                            );
                        } else {
                            Commande commande = new Commande(
                                    prochainIdCommande,
                                    client,
                                    panier
                            );

                            prochainIdCommande++;

                            commande.afficherDetailsCommande();
                            panier.viderApresCommande();
                        }
                        break;
                    }

                    case "5": {
                        // Le panier est conserve pour la prochaine visite
                        menuClientActif = false;
                        break;
                    }

                    case "6": {
                        System.out.println("Nom du produit a supprimer :");

                        if (!sc.hasNextLine()) {
                            applicationActive = false;
                            break;
                        }

                        String nomProduit = sc.nextLine();
                        Produit produit =
                                magasin.trouverProduitParNom(nomProduit);

                        if (panier.getProduits().contains(produit)) {
                            panier.supprimerProduit(produit);
                            System.out.println(
                                    "Une unite supprimee du panier."
                            );
                        } else {
                            System.out.println("Produit absent du panier.");
                        }
                        break;
                    }

                    case "0": {
                        applicationActive = false;
                        break;
                    }

                    default: {
                        System.out.println("Choix invalide.");
                        break;
                    }
                }
            }
        }

        // A la fermeture, liberer les reservations non commandees
        loc.getPanier().annuler();
        alex.getPanier().annuler();

        sc.close();
        System.out.println("Au revoir !");
    }
}