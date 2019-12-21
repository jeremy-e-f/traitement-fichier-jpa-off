package fr.diginamic.daos.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.daos.CategorieDao;
import fr.diginamic.entities.Categorie;
import fr.diginamic.exceptions.FunctionalException;
import fr.diginamic.jdbc.ConnectionJDBC;

public class CategorieDaoJdbc implements CategorieDao {
	
	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelectId= null;
	private PreparedStatement preReqSelectName= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public CategorieDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public CategorieDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqSelectId = connection.prepareStatement("SELECT ID, LIBELLE FROM CATEGORIE WHERE ID = ?;");
			
			preReqSelectName = connection.prepareStatement("SELECT ID, LIBELLE FROM CATEGORIE WHERE LIBELLE = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT INTO CATEGORIE(LIBELLE) VALUES(?);", generatedColumns);
			
			preReqUpdate = connection.prepareStatement("UPDATE CATEGORIE SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM CATEGORIE WHERE ID = ?;");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * On ferme les prestatements
	 */
	public void finalize(){
          try {
          	this.preReqSelectId.close();
          	this.preReqSelectName.close();
  			this.preReqInsert.close();
  			this.preReqUpdate.close();
  			this.preReqDelete.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
    }
	
	@Override
	public List<Categorie> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet rs= null;
		List<Categorie> listeCategories= new ArrayList<Categorie>();
		
		monStatement = connection.createStatement();
		rs= monStatement.executeQuery("SELECT * FROM CATEGORIE;");
		while(rs.next()){
			int idCat= rs.getInt("ID");
			String libelleCat= rs.getString("LIBELLE");
			
			listeCategories.add(new Categorie(idCat, libelleCat));
		}
		rs.close();
		
		return listeCategories;
	}
	
	@Override
	public Categorie getById(int id) throws SQLException {
		Categorie categorie= null;
		
		preReqSelectId.setInt(1, id);
		ResultSet rs= preReqSelectId.executeQuery();
		if(rs.next()){
			categorie= new Categorie(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return categorie;
	}
	
	@Override
	public Categorie getByName(String name) throws SQLException {
		Categorie categorie= null;
		
		preReqSelectName.setString(1, name);
		ResultSet rs= preReqSelectName.executeQuery();
		if(rs.next()){
			categorie= new Categorie(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return categorie;
	}

	@Override
	public int insert(Categorie categorie) throws SQLException, FunctionalException {
		if(categorie== null){
			throw new FunctionalException("Valeur nulle!");
		}
		
		/** Si l'index dans la base de données n'a pas été initialisé */
		if(categorie.getId()== 0){
			
			/** On vérifie si l'objet n'existe pas déjà dans la base de données */
			Categorie objExistant= this.getByName(categorie.getLibelle());
			if(objExistant!= null){
				/** On le met à jour */
				categorie.setId(objExistant.getId());
				return objExistant.getId();
			}else{
				/** Sinon on l'insère dans la base de données */
				preReqInsert.setString(1, categorie.getLibelle());
				preReqInsert.executeUpdate();
				/** Et récupère son Id */
				ResultSet rs= preReqInsert.getGeneratedKeys();
				int id= 0;
				if(rs.next()){
					id= rs.getInt(1);
					/** On le met à jour */
					categorie.setId(id);
				}
				rs.close();
				
				return id;
			}
		}else{
			return categorie.getId();
		}
	}

	@Override
	public int update(Categorie categorie) throws SQLException, FunctionalException {
		int retour= 0;
		if(categorie== null){
			throw new FunctionalException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, categorie.getLibelle());
		preReqUpdate.setInt(i++, categorie.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Categorie categorie) throws SQLException, FunctionalException {
		int retour= 0;
		
		if(categorie== null){
			throw new FunctionalException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, categorie.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
