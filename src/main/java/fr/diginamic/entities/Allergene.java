package fr.diginamic.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Représente le concept d'Allergène présent dans un produit
 * @author DIGINAMIC
 *
 */

@Entity
@Table(name="ALLERGENE")
public class Allergene {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")	
	private int id;							/** Identifiant */
	
	@Column(name="LIBELLE", nullable= false, unique = true)
	private String libelle;					/** Libelle de l'allergène */
	
	@ManyToMany
	@JoinTable(name="PROD_ALL",
	joinColumns= @JoinColumn(name="ID_ALL", referencedColumnName="ID"),
	inverseJoinColumns= @JoinColumn(name="ID_PROD", referencedColumnName="ID"))
	private List<Produit> listeProduits; 	/** Liste de produits qui possèdent cet allergène */
	
	/**
	 * Constructeurs
	 */
	public Allergene() {
	}
	
	public Allergene(String libelle) {
		this.libelle = libelle;
	}
	
	public Allergene(int id, String libelle) {
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
		return "Allergene [id=" + id + ", libelle=" + libelle + "]";
	}
	
	@Override
	public boolean equals(Object o){
		return !(o instanceof Allergene) && libelle.toLowerCase().equals(((Allergene)o).libelle.toLowerCase());
	}

}
