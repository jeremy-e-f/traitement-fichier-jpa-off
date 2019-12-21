package fr.diginamic.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Représente le concept de marque d'un produit
 * @author DIGINAMIC
 *
 */
@Entity
@Table(name="MARQUE")
public class Marque {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;						/** Identifiant */
	
	@Column(name="NOM", nullable= false, unique = true)
	private String nom;					/** Nom de la marque */
	
	@OneToMany(mappedBy="marque", cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Produit> listeProduits; /** Liste des produits appartenant à la marque */
	
	/**
	 * Constructeurs
	 */
	public Marque(){
	}
	
	public Marque(String nom){
		this.nom = nom;
	}
	
	public Marque(int id, String nom){
		this(nom);
		this.id =  id;
	}
	
	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the listeProduits
	 */
	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	/** Setter
	 * @param listeProduits the listeProduits to set
	 */
	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Marque [id=" + id + ", nom=" + nom + "]";
	}
	
	@Override
	public boolean equals(Object o){
		return !(o instanceof Marque) && nom.toLowerCase().equals(((Marque)o).nom.toLowerCase());
	}

}
