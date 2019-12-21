package fr.diginamic.daos;

import java.sql.SQLException;

import fr.diginamic.entities.Produit;
import fr.diginamic.exceptions.FunctionalException;

public interface ProduitDao {
	
	/**
	 * R�cup�re un produit par son nom et le nom de sa marque
	 * @return Produit
	 * @throws SQLException 
	 */
	Produit getByNameMarque(String nomProduit, String nomMarque);
	
	/**
	 * Renvoie l'ID d'un produit s'il est pr�sent ou pas dans la base en fonction de son nom et de sa marque
	 * Renvoie 0 sinon
	 * @return boolean
	 * @throws SQLException 
	 */
	int getId(String nomProduit, String nomMarque);
	
	/**
	 * Ins�re un nouvel produit
	 * @param produit
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	void insert(Produit produit) throws SQLException, FunctionalException;
	
	/**
	 * Met � jour le produit pass� en param�tre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Produit produit) throws SQLException, FunctionalException;
	
	/**
	 * Supprime le produit pass� en param�tre
	 * @param produit
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Produit produit) throws SQLException, FunctionalException;
	
}
