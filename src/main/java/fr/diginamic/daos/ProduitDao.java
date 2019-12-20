package fr.diginamic.daos;

import java.sql.SQLException;

import fr.diginamic.entities.Produit;

public interface ProduitDao {
	
	/**
	 * Récupère un produit par son nom et le nom de sa marque
	 * @return Produit
	 * @throws SQLException 
	 */
	Produit getByNameMarque(String nomProduit, String nomMarque);
	
	/**
	 * Insère un nouvel produit
	 * @param produit
	 * @throws SQLException 
	 */
	void insert(Produit produit) throws SQLException;
	
	/**
	 * Met à jour le produit passé en paramètre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 */
	int update(Produit produit) throws SQLException;
	
	/**
	 * Supprime le produit passé en paramètre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Produit produit) throws SQLException;
	
}
