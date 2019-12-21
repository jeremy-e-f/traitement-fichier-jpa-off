package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Ingredient;
import fr.diginamic.exceptions.FunctionalException;

public interface IngredientDao {

	/**
	 * Extrait la liste de tous les ingrédients
	 * @return List<Ingredient>
	 * @throws SQLException 
	 */
	List<Ingredient> extraire() throws SQLException;
	
	/**
	 * Récupère un ingrédient par son ID
	 * @return Ingredient
	 * @throws SQLException 
	 */
	Ingredient getById(int id) throws SQLException;
	
	/**
	 * Récupère un ingrédient par son libellé
	 * @return Ingredient
	 * @throws SQLException 
	 */
	Ingredient getByName(String name) throws SQLException;
	
	/**
	 * Insère un nouvel ingrédient
	 * @param ingredient
	 * @return retourne l'Id de l'objet dans la base de données
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int insert(Ingredient ingredient) throws SQLException, FunctionalException;
	
	/**
	 * Met à jour l'ingrédient passé en paramètre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Ingredient ingredient) throws SQLException, FunctionalException;
	
	/**
	 * Supprime l'ingredient passé en paramètre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Ingredient ingredient) throws SQLException, FunctionalException;
}
