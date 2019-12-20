package fr.diginamic.daos;

import java.sql.SQLException;

import fr.diginamic.entities.Produit;

public interface ProduitDao {
	
	/**
	 * R�cup�re un produit par son nom et le nom de sa marque
	 * @return Produit
	 * @throws SQLException 
	 */
	Produit getByNameMarque(String nomProduit, String nomMarque);
	
	/**
	 * Ins�re un nouvel produit
	 * @param produit
	 * @throws SQLException 
	 */
	void insert(Produit produit) throws SQLException;
	
	/**
	 * Met � jour le produit pass� en param�tre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 */
	int update(Produit produit) throws SQLException;
	
	/**
	 * Supprime le produit pass� en param�tre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Produit produit) throws SQLException;
	
}
