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

import fr.diginamic.daos.MarqueDao;
import fr.diginamic.entities.Marque;
import fr.diginamic.jdbc.ConnectionJDBC;

public class MarqueDaoJdbc implements MarqueDao {
	
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
	
	public MarqueDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public MarqueDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqSelectId = connection.prepareStatement("SELECT ID, NOM FROM MARQUE WHERE ID = ?;");
			
			preReqSelectName = connection.prepareStatement("SELECT ID, NOM FROM MARQUE WHERE NOM = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO MARQUE(NOM) VALUES(?);", generatedColumns);
			
			preReqUpdate = connection.prepareStatement("UPDATE MARQUE SET NOM = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM MARQUE WHERE ID = ?;");
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
	public List<Marque> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet rs= null;
		List<Marque> listeMarques= new ArrayList<Marque>();
		
		monStatement = connection.createStatement();
		rs= monStatement.executeQuery("SELECT * FROM MARQUE;");
		while(rs.next()){
			int idCat= rs.getInt("ID");
			String nomCat= rs.getString("NOM");
			
			listeMarques.add(new Marque(idCat, nomCat));
		}
		rs.close();
		
		return listeMarques;
	}
	
	@Override
	public Marque getById(int id) throws SQLException {
		Marque marque= null;
		
		preReqSelectId.setInt(1, id);
		ResultSet rs= preReqSelectId.executeQuery();
		if(rs.next()){
			marque= new Marque(rs.getInt("ID"), rs.getString("NOM"));
		}
		rs.close();
		
		return marque;
	}
	
	@Override
	public Marque getByName(String name) throws SQLException {
		Marque marque= null;
		
		preReqSelectName.setString(1, name);
		ResultSet rs= preReqSelectName.executeQuery();
		if(rs.next()){
			marque= new Marque(rs.getInt("ID"), rs.getString("NOM"));
		}
		rs.close();
		
		return marque;
	}

	@Override
	public int insert(Marque marque) throws SQLException {
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		/** On vérifie si l'objet n'existe pas déjà dans la base de données */
		Marque objExistant= this.getByName(marque.getNom());
		if(objExistant!= null){
			/** On le met à jour */
			marque.setId(objExistant.getId());
			return objExistant.getId();
		}else{
			/** Sinon on l'insère dans la base de données */
			preReqInsert.setString(1, marque.getNom());
			preReqInsert.executeUpdate();
			/** Et récupère son Id */
			ResultSet rs= preReqInsert.getGeneratedKeys();
			int id= 0;
			if(rs.next()){
				id= rs.getInt(1);
				/** On le met à jour */
				marque.setId(id);
			}
			rs.close();
			
			return id;
		}
	}

	@Override
	public int update(Marque marque) throws SQLException {
		int retour= 0;
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, marque.getNom());
		preReqUpdate.setInt(i++, marque.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Marque marque) throws SQLException {
		int retour= 0;
		
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, marque.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}
}
