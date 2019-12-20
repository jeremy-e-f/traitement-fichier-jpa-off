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
	 * R�cup�re un additif par son ID
	 * @return Additif
	 * @throws SQLException 
	 */
	Additif getById(int id) throws SQLException;
	
	/**
	 * R�cup�re un additif par son libell�
	 * @return Additif
	 * @throws SQLException 
	 */
	Additif getByName(String name) throws SQLException;
	
	/**
	 * Ins�re un additif
	 * @param additif
	 * @return retourne l'Id de l'objet dans la base de donn�es
	 * @throws SQLException 
	 */
	int insert(Additif additif) throws SQLException;
	
	/**
	 * Met � jour l'additif pass� en param�tre
	 * @param additif
	 * @return
	 * @throws SQLException 
	 */
	int update(Additif additif) throws SQLException;
	
	/**
	 * Supprime l'additif pass� en param�tre
	 * @param additif
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Additif additif) throws SQLException;
	
}

