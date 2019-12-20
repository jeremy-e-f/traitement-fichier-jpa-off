package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Marque;

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
	 * Ins�re une nouvelle marque
	 * @param marque
	 * @throws SQLException 
	 */
	void insert(Marque marque) throws SQLException;
	
	/**
	 * Met � jour la marque pass�e en param�tre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	int update(Marque marque) throws SQLException;
	
	/**
	 * Supprime la marque pass�e en param�tre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Marque marque) throws SQLException;
}
