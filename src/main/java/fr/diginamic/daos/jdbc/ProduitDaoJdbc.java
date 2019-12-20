package fr.diginamic.daos.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.daos.AdditifDao;
import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.daos.CategorieDao;
import fr.diginamic.daos.IngredientDao;
import fr.diginamic.daos.MarqueDao;
import fr.diginamic.daos.ProduitDao;
import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Produit;
import fr.diginamic.jdbc.ConnectionJDBC;

public class ProduitDaoJdbc implements ProduitDao {
	
	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqInsertProdIng= null;
	private PreparedStatement preReqInsertProdAdd= null;
	private PreparedStatement preReqInsertProdAll= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public ProduitDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public ProduitDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO PRODUIT(NOM, SCORE_NUTRITIONNEL, ENERGIE, GRAISSE, GRAISSE_SATUREE, HYDRATES_CARBONES, SUCRES, FIBRES,"+
			"PROTEINES, SEL, VIT_A, VIT_D, VIT_E, VIT_K, VIT_C, VIT_B1, VIT_B2, VIT_PP, VIT_B6, VIT_B9, VIT_B12, CALCIUM, MAGNESIUM, FER, BETA_CAROTENE, PRESENCE_HUILE_PALME,"+
			"POURCENTAGE_FRUITS_LEGUMES, ID_CAT, ID_MQ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);", generatedColumns);
			
			preReqInsertProdIng = connection.prepareStatement("INSERT IGNORE INTO PROD_ING(ID_PROD, ID_ING) VALUES(?,?);");
			
			preReqInsertProdAdd = connection.prepareStatement("INSERT IGNORE INTO PROD_ADD(ID_PROD, ID_ADD) VALUES(?,?);");
			
			preReqInsertProdAll = connection.prepareStatement("INSERT IGNORE INTO PROD_ALL(ID_PROD, ID_ALL) VALUES(?,?);");
			
			preReqUpdate = connection.prepareStatement("UPDATE PRODUIT SET NOM = ?, SCORE_NUTRITIONNEL = ?, ENERGIE = ?, GRAISSE = ?,"+
			" GRAISSE_SATUREE = ?, HYDRATES_CARBONES = ?, SUCRES = ?, FIBRES = ?, PROTEINES = ?, SEL = ?, VIT_A = ?, VIT_D = ?, VIT_E = ?, VIT_K = ?, VIT_C = ?,"+
			" VIT_B1 = ?, VIT_B2 = ?, VIT_PP = ?, VIT_B6 = ?, VIT_B9 = ?, VIT_B12 = ?, CALCIUM = ?, MAGNESIUM = ?, FER = ?, BETA_CAROTENE = ?, PRESENCE_HUILE_PALME = ?,"+
			"POURCENTAGE_FRUITS_LEGUMES = ?, ID_CAT = ?, ID_MQ = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM PRODUIT WHERE ID = ?;");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * On ferme les prestatements
	 */
	public void finalize(){
          try {
  			this.preReqInsert.close();
  			this.preReqInsertProdIng.close();
  			this.preReqInsertProdAdd.close();
  			this.preReqInsertProdAll.close();
  			this.preReqUpdate.close();
  			this.preReqDelete.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
    }
	
	@Override
	public Produit getByNameMarque(String nomProduit, String nomMarque) {
		
		/** On vérifie les paramètres */
		if(nomProduit== null || nomMarque== null){
			LOG.error("Nom du produit ou de la marque manquante.");
			return null;
		}
		nomProduit= nomProduit.trim().replace("'", "''");
		nomMarque= nomMarque.trim().replace("'", "''");
		
		Produit produit= null;
		Statement monStatement= null;
		ResultSet rs= null;
		
		try {
			monStatement = connection.createStatement();
			rs= monStatement.executeQuery("SELECT * FROM PRODUIT p JOIN Marque mq ON(p.ID_MQ = mq.ID) WHERE p.NOM= '"+nomProduit+"' AND mq.NOM= '"+nomMarque+"';");
			if(rs.next()){
				/** On récupère les données du produit */
				int idProduit = rs.getInt("p.ID");
				String nom = rs.getString("p.NOM");
				char scoreNutritionnel = rs.getString("p.SCORE_NUTRITIONNEL").charAt(0);
				Double energie = rs.getDouble("p.ENERGIE");
				Double graisse = rs.getDouble("p.GRAISSE");
				Double graisseSaturee = rs.getDouble("p.GRAISSE_SATUREE");
				Double hydratesCarbones = rs.getDouble("p.HYDRATES_CARBONES");
				Double sucres = rs.getDouble("p.SUCRES");
				Double fibres = rs.getDouble("p.FIBRES");
				Double proteines = rs.getDouble("p.PROTEINES");
				Double sel = rs.getDouble("p.SEL");
				Double vitA = rs.getDouble("p.VIT_A");
				Double vitD = rs.getDouble("p.VIT_D");
				Double vitE = rs.getDouble("p.VIT_E");
				Double vitK = rs.getDouble("p.VIT_K");
				Double vitC = rs.getDouble("p.VIT_C");
				Double vitB1 = rs.getDouble("p.VIT_B1");
				Double vitB2 = rs.getDouble("p.VIT_B2");
				Double vitPP = rs.getDouble("p.VIT_PP");
				Double vitB6 = rs.getDouble("p.VIT_B6");
				Double vitB9 = rs.getDouble("p.VIT_B9");
				Double vitB12 = rs.getDouble("p.VIT_B12");
				Double calcium = rs.getDouble("p.CALCIUM");
				Double magnesium = rs.getDouble("p.MAGNESIUM");
				Double fer = rs.getDouble("p.FER");
				Double betaCarotene = rs.getDouble("p.BETA_CAROTENE");
				boolean presenceHuilePalme = rs.getBoolean("p.PRESENCE_HUILE_PALME");
				Double pourcentageFruitsLegumes = rs.getDouble("p.POURCENTAGE_FRUITS_LEGUMES");
				
				/** On extrait la Categorie correspondante */
				CategorieDao catDao= new CategorieDaoJdbc(connection);
				Categorie categorie= catDao.getById(rs.getInt("p.ID_CAT"));
				catDao= null;
				
				/** On récupère la Marque */
				MarqueDao mqDao= new MarqueDaoJdbc(connection);
				Marque marque= mqDao.getById(rs.getInt("p.ID_MQ"));
				mqDao= null;
				
				rs.close();
				
				/** On récupère la liste des ingrédients */
				ArrayList<Ingredient> listeIngredients= new ArrayList<Ingredient>();
				rs= monStatement.executeQuery("SELECT ID_ING FROM PROD_ING WHERE ID_PROD= '"+idProduit+"';");
				if(rs.next()){
					IngredientDao ingDao= new IngredientDaoJdbc(connection);
					do{
						Ingredient ingredient= ingDao.getById(rs.getInt("ID_ING"));
						listeIngredients.add(ingredient);
					}while(rs.next());
				}
				rs.close();
				
				/** On récupère la liste des allergènes */
				ArrayList<Allergene> listeAllergenes= new ArrayList<Allergene>();
				rs= monStatement.executeQuery("SELECT ID_ALL FROM PROD_ALL WHERE ID_PROD= '"+idProduit+"';");
				if(rs.next()){
					AllergeneDao allDao= new AllergeneDaoJdbc(connection);
					do{
						Allergene allergene= allDao.getById(rs.getInt("ID_ALL"));
						listeAllergenes.add(allergene);
					}while(rs.next());
				}
				rs.close();
				
				/** On récupère la liste des additifs */
				ArrayList<Additif> listeAdditifs= new ArrayList<Additif>();
				rs= monStatement.executeQuery("SELECT ID_ADD FROM PROD_ADD WHERE ID_PROD= '"+idProduit+"';");
				if(rs.next()){
					AdditifDao addDao= new AdditifDaoJdbc(connection);
					do{
						Additif additif= addDao.getById(rs.getInt("ID_ADD"));
						listeAdditifs.add(additif);
					}while(rs.next());
				}
				rs.close();
				
				produit= new Produit(idProduit, nom, categorie, marque, scoreNutritionnel, listeIngredients, listeAllergenes, 
						listeAdditifs, energie, graisse, graisseSaturee, hydratesCarbones, sucres, fibres, proteines, sel, vitA ,vitD ,vitE ,vitK ,vitC ,
						vitB1 ,vitB2 ,vitPP ,vitB6 ,vitB9 ,vitB12 ,calcium ,magnesium, fer ,betaCarotene, presenceHuilePalme, pourcentageFruitsLegumes);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				monStatement.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return produit;
	}

	@Override
	public void insert(Produit produit) throws SQLException {
		if(produit== null){
			throw new SQLException("Valeur nulle!");
		}
		
		Produit objExistant= this.getByNameMarque(produit.getNom(), produit.getMarque().getNom());
		
		/** Si le produit n'existe pas, on le cree */
		if(objExistant== null){
		
			/** Insertion de la catégorie correspondante */
			if(produit.getCategorie()!= null){
				CategorieDao catDao= new CategorieDaoJdbc(this.connection);
				catDao.insert(produit.getCategorie());
				catDao= null;
			}
			
			/** Insertion de la marque correspondante */
			if(produit.getMarque()!= null){
				MarqueDao mqDao= new MarqueDaoJdbc(this.connection);
				mqDao.insert(produit.getMarque());
				mqDao= null;
			}
			
			/** Insertion du produit */
			int i= 1;
			preReqInsert.setString(i++,produit.getNom());
			preReqInsert.setString(i++,String.valueOf(produit.getScoreNutritionnel()));
			preReqInsert.setObject(i++,produit.getEnergie());
			preReqInsert.setObject(i++,produit.getGraisse());
			preReqInsert.setObject(i++,produit.getGraisseSaturee());
			preReqInsert.setObject(i++,produit.getHydratesCarbones());
			preReqInsert.setObject(i++,produit.getSucres());
			preReqInsert.setObject(i++,produit.getFibres());
			preReqInsert.setObject(i++,produit.getProteines());
			preReqInsert.setObject(i++,produit.getSel());
			preReqInsert.setObject(i++,produit.getVitA());
			preReqInsert.setObject(i++,produit.getVitD());
			preReqInsert.setObject(i++,produit.getVitE());
			preReqInsert.setObject(i++,produit.getVitK());
			preReqInsert.setObject(i++,produit.getVitC());
			preReqInsert.setObject(i++,produit.getVitB1());
			preReqInsert.setObject(i++,produit.getVitB2());
			preReqInsert.setObject(i++,produit.getVitPP());
			preReqInsert.setObject(i++,produit.getVitB6());
			preReqInsert.setObject(i++,produit.getVitB9());
			preReqInsert.setObject(i++,produit.getVitB12());
			preReqInsert.setObject(i++,produit.getCalcium());
			preReqInsert.setObject(i++,produit.getMagnesium());
			preReqInsert.setObject(i++,produit.getFer());
			preReqInsert.setObject(i++,produit.getBetaCarotene());
			preReqInsert.setBoolean(i++,produit.isPresenceHuilePalme());
			preReqInsert.setObject(i++,produit.getPourcentageFruitsLegumes());
			preReqInsert.setInt(i++,produit.getCategorie().getId());
			preReqInsert.setInt(i++,produit.getMarque().getId());
			//System.err.println(preReqInsert);
			preReqInsert.executeUpdate();
			
			/** On récupère l'Id du produit inséré et on met à jour le produit */
			ResultSet rs= preReqInsert.getGeneratedKeys();
			if(rs.next()){
				int id= rs.getInt(1);
				produit.setId(id);
			}
			rs.close();
			
			/** Insertion des ingrédients */
			if(!produit.getListeIngredients().isEmpty()){
				IngredientDao ingDao= new IngredientDaoJdbc(this.connection);
				preReqInsertProdIng.setInt(1, produit.getId());
				
				for(Ingredient ingredient : produit.getListeIngredients()){
					ingDao.insert(ingredient);
					/** Insertion dans la table d'association PROD_ING */
					preReqInsertProdIng.setInt(2, ingredient.getId());
					preReqInsertProdIng.executeUpdate();
				}
				ingDao= null;
			}
			
			/** Insertion des allergènes */
			if(!produit.getListeAllergenes().isEmpty()){
				AllergeneDao allDao= new AllergeneDaoJdbc(this.connection);
				preReqInsertProdAll.setInt(1, produit.getId());
				
				for(Allergene allergene : produit.getListeAllergenes()){
					allDao.insert(allergene);
					/** Insertion dans la table d'association PROD_ALL */
					preReqInsertProdAll.setInt(2, allergene.getId());
					preReqInsertProdAll.executeUpdate();
				}
				allDao= null;
			}
			
			/** Insertion des additifs */
			if(!produit.getListeAdditifs().isEmpty()){
				AdditifDao addDao= new AdditifDaoJdbc(this.connection);
				preReqInsertProdAdd.setInt(1, produit.getId());
				
				for(Additif additif : produit.getListeAdditifs()){
					addDao.insert(additif);
					/** Insertion dans la table d'association PROD_ADD */
					preReqInsertProdAdd.setInt(2, additif.getId());
					preReqInsertProdAdd.executeUpdate();
				}
				addDao= null;
			}
		}else{
			/** On met le produit à jour */
			produit.setId(objExistant.getId());
		}
		
	}

	@Override
	public int update(Produit produit) throws SQLException {
		int retour= 0;
		if(produit== null){
			throw new SQLException("Valeur nulle!");
		}

		int i= 1;
		preReqUpdate.setString(i++,produit.getNom());
		preReqUpdate.setInt(i++,produit.getScoreNutritionnel());
		preReqUpdate.setDouble(i++,produit.getEnergie());
		preReqUpdate.setDouble(i++,produit.getGraisse());
		preReqUpdate.setDouble(i++,produit.getGraisseSaturee());
		preReqUpdate.setDouble(i++,produit.getHydratesCarbones());
		preReqUpdate.setDouble(i++,produit.getSucres());
		preReqUpdate.setDouble(i++,produit.getFibres());
		preReqUpdate.setDouble(i++,produit.getProteines());
		preReqUpdate.setDouble(i++,produit.getSel());
		preReqUpdate.setDouble(i++,produit.getVitA());
		preReqUpdate.setDouble(i++,produit.getVitD());
		preReqUpdate.setDouble(i++,produit.getVitE());
		preReqUpdate.setDouble(i++,produit.getVitK());
		preReqUpdate.setDouble(i++,produit.getVitC());
		preReqUpdate.setDouble(i++,produit.getVitB1());
		preReqUpdate.setDouble(i++,produit.getVitB2());
		preReqUpdate.setDouble(i++,produit.getVitPP());
		preReqUpdate.setDouble(i++,produit.getVitB6());
		preReqUpdate.setDouble(i++,produit.getVitB9());
		preReqUpdate.setDouble(i++,produit.getVitB12());
		preReqUpdate.setDouble(i++,produit.getCalcium());
		preReqUpdate.setDouble(i++,produit.getMagnesium());
		preReqUpdate.setDouble(i++,produit.getFer());
		preReqUpdate.setDouble(i++,produit.getBetaCarotene());
		preReqUpdate.setBoolean(i++,produit.isPresenceHuilePalme());
		preReqUpdate.setDouble(i++,produit.getPourcentageFruitsLegumes());
		preReqUpdate.setInt(i++,produit.getCategorie().getId());
		preReqUpdate.setInt(i++,produit.getMarque().getId());
		preReqUpdate.setInt(i++,produit.getId());
		retour= preReqUpdate.executeUpdate();

		return retour;
	}

	@Override
	public boolean delete(Produit produit) throws SQLException {
		int retour= 0;
		
		if(produit== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, produit.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
