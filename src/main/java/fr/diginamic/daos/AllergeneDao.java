package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Allergene;
import fr.diginamic.exceptions.FunctionalException;

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
	 * R�cup�re un allerg�ne par son libell�
	 * @return Allergene
	 * @throws SQLException 
	 */
	Allergene getByName(String name) throws SQLException;
	
	/**
	 * Ins�re un allerg�nes
	 * @param allergene
	 * @return retourne l'Id de l'objet dans la base de donn�es
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int insert(Allergene allergene) throws SQLException, FunctionalException;
	
	/**
	 * Met � jour l'allerg�ne pass� en param�tre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Allergene allergene) throws SQLException, FunctionalException;
	
	/**
	 * Supprime l'allergene pass� en param�tre
	 * @param allergene
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Allergene allergene) throws SQLException, FunctionalException;
}
