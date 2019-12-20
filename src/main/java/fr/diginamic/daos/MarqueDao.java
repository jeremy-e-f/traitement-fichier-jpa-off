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
	 * Récupère une marque par son ID
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getById(int id) throws SQLException;
	
	/**
	 * Récupère une marque par son nom
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getByName(String name) throws SQLException;
	
	/**
	 * Insère une marque
	 * @param marque
	 * @return retourne l'Id de l'objet dans la base de données
	 * @throws SQLException 
	 */
	int insert(Marque marque) throws SQLException;
	
	/**
	 * Met à jour la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	int update(Marque marque) throws SQLException;
	
	/**
	 * Supprime la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Marque marque) throws SQLException;
}
