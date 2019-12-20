package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Categorie;

public interface CategorieDao {

	/**
	 * Extrait la liste de tous les cat�gories
	 * @return List<Categorie>
	 * @throws SQLException 
	 */
	List<Categorie> extraire() throws SQLException;
	
	/**
	 * R�cup�re une cat�gorie par son ID
	 * @return Categorie
	 * @throws SQLException 
	 */
	Categorie getById(int id) throws SQLException;
	
	/**
	 * Ins�re une nouvelle cat�gorie
	 * @param categorie
	 * @throws SQLException 
	 */
	void insert(Categorie categorie) throws SQLException;
	
	/**
	 * Met � jour la cat�gorie pass�e en param�tre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 */
	int update(Categorie categorie) throws SQLException;
	
	/**
	 * Supprime la categorie pass�e en param�tre
	 * @param categorie
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Categorie categorie) throws SQLException;
}
