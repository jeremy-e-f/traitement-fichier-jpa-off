package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Additif;

public interface AdditifDao {

	/**
	 * Extrait la liste de tous les additifs
	 * @return List<Additif>
	 * @throws SQLException 
	 */
	List<Additif> extraire() throws SQLException;
	
	/**
	 * Récupère un additif par son ID
	 * @return Additif
	 * @throws SQLException 
	 */
	Additif getById(int id) throws SQLException;
	
	/**
	 * Récupère un additif par son libellé
	 * @return Additif
	 * @throws SQLException 
	 */
	Additif getByName(String name) throws SQLException;
	
	/**
	 * Insère un additif
	 * @param additif
	 * @return retourne l'Id de l'objet dans la base de données
	 * @throws SQLException 
	 */
	int insert(Additif additif) throws SQLException;
	
	/**
	 * Met à jour l'additif passé en paramètre
	 * @param additif
	 * @return
	 * @throws SQLException 
	 */
	int update(Additif additif) throws SQLException;
	
	/**
	 * Supprime l'additif passé en paramètre
	 * @param additif
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Additif additif) throws SQLException;
	
}

