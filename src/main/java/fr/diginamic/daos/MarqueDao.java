package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Marque;
import fr.diginamic.exceptions.FunctionalException;

public interface MarqueDao {

	/**
	 * Extrait la liste de tous les marques
	 * @return List<Marque>
	 * @throws SQLException 
	 */
	List<Marque> extraire() throws SQLException;
	
	/**
	 * R�cup�re une marque par son ID
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getById(int id) throws SQLException;
	
	/**
	 * R�cup�re une marque par son nom
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getByName(String name) throws SQLException;
	
	/**
	 * Ins�re une marque
	 * @param marque
	 * @return retourne l'Id de l'objet dans la base de donn�es
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int insert(Marque marque) throws SQLException, FunctionalException;
	
	/**
	 * Met � jour la marque pass�e en param�tre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Marque marque) throws SQLException, FunctionalException;
	
	/**
	 * Supprime la marque pass�e en param�tre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Marque marque) throws SQLException, FunctionalException;
}
