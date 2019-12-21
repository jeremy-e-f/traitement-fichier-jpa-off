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

import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.entities.Allergene;
import fr.diginamic.exceptions.FunctionalException;
import fr.diginamic.jdbc.ConnectionJDBC;

public class AllergeneDaoJdbc implements AllergeneDao {

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
	
	public AllergeneDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public AllergeneDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqSelectId = connection.prepareStatement("SELECT ID, LIBELLE FROM ALLERGENE WHERE ID = ?;");
			
			preReqSelectName = connection.prepareStatement("SELECT ID, LIBELLE FROM ALLERGENE WHERE LIBELLE = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT INTO ALLERGENE(LIBELLE) VALUES(?);", generatedColumns);
			
			preReqUpdate = connection.prepareStatement("UPDATE ALLERGENE SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM ALLERGENE WHERE ID = ?;");
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
	public List<Allergene> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet rs= null;
		List<Allergene> listeAllergenes= new ArrayList<Allergene>();
		
		monStatement = connection.createStatement();
		rs= monStatement.executeQuery("SELECT * FROM ALLERGENE;");
		while(rs.next()){
			listeAllergenes.add(new Allergene(rs.getInt("ID"), rs.getString("LIBELLE")));
		}
		rs.close();
		
		return listeAllergenes;
	}
	
	@Override
	public Allergene getById(int id) throws SQLException {
		Allergene allergene= null;
		
		preReqSelectId.setInt(1, id);
		ResultSet rs= preReqSelectId.executeQuery();
		if(rs.next()){
			allergene= new Allergene(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return allergene;
	}
	
	@Override
	public Allergene getByName(String name) throws SQLException {
		Allergene allergene= null;
		
		preReqSelectName.setString(1, name);
		ResultSet rs= preReqSelectName.executeQuery();
		if(rs.next()){
			allergene= new Allergene(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return allergene;
	}

	@Override
	public int insert(Allergene allergene) throws SQLException, FunctionalException {
		if(allergene== null){
			throw new FunctionalException("Valeur nulle!");
		}
		
		/** Si l'index dans la base de données n'a pas été initialisé */
		if(allergene.getId()== 0){
		
			/** On vérifie si l'objet n'existe pas déjà dans la base de données */
			Allergene objExistant= this.getByName(allergene.getLibelle());
			if(objExistant!= null){
				/** On le met à jour */
				allergene.setId(objExistant.getId());
				return objExistant.getId();
			}else{
				/** Sinon on l'insère dans la base de données */
				preReqInsert.setString(1, allergene.getLibelle());
				preReqInsert.executeUpdate();
				/** Et récupère son Id */
				ResultSet rs= preReqInsert.getGeneratedKeys();
				int id= 0;
				if(rs.next()){
					id= rs.getInt(1);
					/** On le met à jour */
					allergene.setId(id);
				}
				rs.close();
				
				return id;
			}
		}else{
			return allergene.getId();
		}
	}

	@Override
	public int update(Allergene allergene) throws SQLException, FunctionalException {
		int retour= 0;
		if(allergene== null){
			throw new FunctionalException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, allergene.getLibelle());
		preReqUpdate.setInt(i++, allergene.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Allergene allergene) throws SQLException, FunctionalException {
		int retour= 0;
		
		if(allergene== null){
			throw new FunctionalException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, allergene.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
