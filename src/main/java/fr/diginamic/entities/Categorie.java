package fr.diginamic.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Représente le concept de Catégorie d'un produit
 * @author DIGINAMIC
 *
 */
@Entity
@Table(name="CATEGORIE")
public class Categorie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;						/** Identifiant */
	
	@Column(name="LIBELLE", nullable= false, unique = true)
	private String libelle;				/** Libellé de la catégorie */
	
	@OneToMany(mappedBy="categorie")
	private List<Produit> listeProduits; /** Liste des produits appartenant à la catégorie */
	
	/**
	 * Constructeur
	 */
	public Categorie(){	
	}
	
	public Categorie(String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public Categorie(int id, String libelle) {
		this(libelle);
		this.id = id;
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
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/** Setter
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
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
		return "Categorie [id=" + id + ", libelle=" + libelle + "]";
	}
	
	@Override
	public boolean equals(Object o){
		return !(o instanceof Categorie) && libelle.toLowerCase().equals(((Categorie)o).libelle.toLowerCase());
	}

}
