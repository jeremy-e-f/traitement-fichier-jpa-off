package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Categorie;
import fr.diginamic.exceptions.FunctionalException;

public interface CategorieDao {

	/**
	 * Extrait la liste de tous les catégories
	 * @return List<Categorie>
	 * @throws SQLException 
	 */
	List<Categorie> extraire() throws SQLException;
	
	/**
	 * Récupère une catégorie par son ID
	 * @return Categorie
	 * @throws SQLException 
	 */
	Categorie getById(int id) throws SQLException;
	
	/**
	 * Récupère une catégorie par son libellé
	 * @return Categorie
	 * @throws SQLException 
	 */
	Categorie getByName(String name) throws SQLException;
	
	/**
	 * Insère une catégorie
	 * @param categorie
	 * @return retourne l'Id de l'objet dans la base de données
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int insert(Categorie categorie) throws SQLException, FunctionalException;
	
	/**
	 * Met à jour la catégorie passée en paramètre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Categorie categorie) throws SQLException, FunctionalException;
	
	/**
	 * Supprime la categorie passée en paramètre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Categorie categorie) throws SQLException, FunctionalException;
}
