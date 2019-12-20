package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Ingredient;

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
	 * Insère un nouvel ingrédient
	 * @param ingredient
	 * @throws SQLException 
	 */
	void insert(Ingredient ingredient) throws SQLException;
	
	/**
	 * Met à jour l'ingrédient passé en paramètre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 */
	int update(Ingredient ingredient) throws SQLException;
	
	/**
	 * Supprime l'ingredient passé en paramètre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Ingredient ingredient) throws SQLException;
}
