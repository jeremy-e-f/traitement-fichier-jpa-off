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

import fr.diginamic.daos.AdditifDao;
import fr.diginamic.entities.Additif;
import fr.diginamic.jdbc.ConnectionJDBC;

public class AdditifDaoJdbc implements AdditifDao {

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
	
	public AdditifDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public AdditifDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqSelectId = connection.prepareStatement("SELECT ID, LIBELLE FROM ADDITIF WHERE ID = ?;");
			
			preReqSelectName = connection.prepareStatement("SELECT ID, LIBELLE FROM ADDITIF WHERE LIBELLE = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO ADDITIF(LIBELLE) VALUES(?);", generatedColumns);
			
			preReqUpdate = connection.prepareStatement("UPDATE ADDITIF SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM ADDITIF WHERE ID = ?;");
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
	public List<Additif> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet rs= null;
		List<Additif> listeAdditifs= new ArrayList<Additif>();
		
		monStatement = connection.createStatement();
		rs= monStatement.executeQuery("SELECT * FROM ADDITIF;");
		while(rs.next()){
			listeAdditifs.add(new Additif(rs.getInt("ID"), rs.getString("LIBELLE")));
		}
		rs.close();
		
		return listeAdditifs;
	}
	
	@Override
	public Additif getById(int id) throws SQLException {
		Additif additif= null;
		
		preReqSelectId.setInt(1, id);
		ResultSet rs= preReqSelectId.executeQuery();
		if(rs.next()){
			additif= new Additif(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		return additif;
	}
	
	@Override
	public Additif getByName(String name) throws SQLException {
		Additif additif= null;
		
		preReqSelectName.setString(1, name);
		ResultSet rs= preReqSelectName.executeQuery();
		if(rs.next()){
			additif= new Additif(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		return additif;
	}

	@Override
	public int insert(Additif additif) throws SQLException {
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		
		/** Si l'index dans la base de données n'a pas été initialisé */
		if(additif.getId()== 0){
			
			/** On vérifie si l'objet n'existe pas déjà dans la base de données */
			Additif objExistant= this.getByName(additif.getLibelle());
			if(objExistant!= null){
				/** On le met à jour */
				additif.setId(objExistant.getId());
				return objExistant.getId();
			}else{
				/** Sinon on l'insère dans la base de données */
				preReqInsert.setString(1, additif.getLibelle());
				preReqInsert.executeUpdate();
				/** Et récupère son Id */
				ResultSet rs= preReqInsert.getGeneratedKeys();
				int id= 0;
				if(rs.next()){
					id= rs.getInt(1);
					/** On le met à jour */
					additif.setId(id);
				}
				rs.close();
				return id;
			}
		}else{
			return additif.getId();
		}
	}

	@Override
	public int update(Additif additif) throws SQLException {
		int retour= 0;
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, additif.getLibelle());
		preReqUpdate.setInt(i++, additif.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Additif additif) throws SQLException {
		int retour= 0;
		
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, additif.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
