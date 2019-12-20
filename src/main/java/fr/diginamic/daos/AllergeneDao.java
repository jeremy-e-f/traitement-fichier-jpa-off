package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Allergene;

public interface AllergeneDao {

	/**
	 * Extrait la liste de tous les allergènes
	 * @return List<Allergene>
	 * @throws SQLException 
	 */
	List<Allergene> extraire() throws SQLException;
	
	/**
	 * Récupère un allergène par son ID
	 * @return Allergene
	 * @throws SQLException 
	 */
	Allergene getById(int id) throws SQLException;
	
	/**
	 * Insère un nouvel allergène
	 * @param allergene
	 * @throws SQLException 
	 */
	void insert(Allergene allergene) throws SQLException;
	
	/**
	 * Met à jour l'allergène passé en paramètre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 */
	int update(Allergene allergene) throws SQLException;
	
	/**
	 * Supprime l'allergene passé en paramètre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Allergene allergene) throws SQLException;
}
