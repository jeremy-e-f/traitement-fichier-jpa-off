package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Allergene;

public interface AllergeneDao {

	/**
	 * Extrait la liste de tous les allerg�nes
	 * @return List<Allergene>
	 * @throws SQLException 
	 */
	List<Allergene> extraire() throws SQLException;
	
	/**
	 * R�cup�re un allerg�ne par son ID
	 * @return Allergene
	 * @throws SQLException 
	 */
	Allergene getById(int id) throws SQLException;
	
	/**
	 * Ins�re un nouvel allerg�ne
	 * @param allergene
	 * @throws SQLException 
	 */
	void insert(Allergene allergene) throws SQLException;
	
	/**
	 * Met � jour l'allerg�ne pass� en param�tre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 */
	int update(Allergene allergene) throws SQLException;
	
	/**
	 * Supprime l'allergene pass� en param�tre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Allergene allergene) throws SQLException;
}
