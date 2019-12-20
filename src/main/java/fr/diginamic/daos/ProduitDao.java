package fr.diginamic.daos;

import java.sql.SQLException;

import fr.diginamic.entities.Produit;

public interface ProduitDao {
	
	/**
	 * Récupère un produit par son ID
	 * @return Produit
	 * @throws SQLException 
	 */
	Produit getById(int id);
	
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
