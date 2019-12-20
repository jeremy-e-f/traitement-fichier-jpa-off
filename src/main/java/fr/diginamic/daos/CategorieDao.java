package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Categorie;

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
	 */
	int insert(Categorie categorie) throws SQLException;
	
	/**
	 * Met à jour la catégorie passée en paramètre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 */
	int update(Categorie categorie) throws SQLException;
	
	/**
	 * Supprime la categorie passée en paramètre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Categorie categorie) throws SQLException;
}
