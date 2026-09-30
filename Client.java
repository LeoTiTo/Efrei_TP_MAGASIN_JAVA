/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionMagasin;

/**
 *
 * @author lochu
 */
public class Client {
    private int id;
    private String nom;
    private String email;
    
    // Chaque client possede son propre panier
    private final Panier panier = new Panier();

    public Client(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void afficherDetails() {
        System.out.println(this);
    }

    public Panier getPanier() {
        return panier;
    }
    

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", email=" + email + '}';
    }
        
}
