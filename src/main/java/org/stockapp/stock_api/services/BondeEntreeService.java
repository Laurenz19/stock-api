package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stockapp.stock_api.DatabaseConnection;
import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeEntree;
import org.stockapp.stock_api.model.Produit;

public class BondeEntreeService {
	
	private Queries q = new Queries();
	private String table = "bondeentree";
	private String column = "produit, qteEntree, dateEntree";
	private UsefulFunctions useful = new UsefulFunctions();


	public BondeEntree createBondeEntree(BondeEntree bondeEntree) {
		
		String produit_id = bondeEntree.getProduit().getId();
		
		String values = "'"+produit_id+"', "+bondeEntree.getQteEntree()+", '"+useful.formatDate(bondeEntree.getDateEntree())+"'";
		q.create(table, column, values);
		
		ProduitService produitService = new ProduitService();
		if(bondeEntree.getProduit().getStock() > 0) {
			bondeEntree.getProduit().setStock(useful.new_stock(bondeEntree.getProduit().getStock(), bondeEntree.getQteEntree(), 0));
		}else {
			bondeEntree.getProduit().setStock(useful.new_stock(0, bondeEntree.getQteEntree(), 0));
		}
		
		produitService.updateProduit(bondeEntree.getProduit());
	    
		
		bondeEntree = this.getBondeEntree(q.maxId(this.table));
		return bondeEntree;
		
	}
	
	public List<BondeEntree> readBondeEntrees(String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + this.table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
		}
		
		List<BondeEntree> bons = new ArrayList<BondeEntree>();
		ProduitService produitService = new ProduitService();
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			Connection connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			
				if (result.next() == false) {
	    			bons = new ArrayList<BondeEntree>();
		   		}else {
		   			 
		   			 do {
		   				BondeEntree bondeEntree = new BondeEntree(produitService.getProduit(result.getString("produit")),result.getInt("qteEntree"), result.getDate("dateEntree"));
						bondeEntree.setId(result.getString("id"));
		   				
		   				bons.add(bondeEntree);
		   		      } while (result.next());	
		   		}
				result.close();
				statement.close();
				connection.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		return bons;
	}
	
    public BondeEntree readBondeEntree(String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + this.table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
		}
		
		BondeEntree bondeEntree = new BondeEntree();
		ProduitService produitService = new ProduitService();
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			Connection connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			
				if (result.next() == false) {
					bondeEntree = null;
		   		}else {
		   			 
		   			 do {
		   				bondeEntree = new BondeEntree(produitService.getProduit(result.getString("produit")),result.getInt("qteEntree"), result.getDate("dateEntree"));
						bondeEntree.setId(result.getString("id"));
		   				
		   		      } while (result.next());	
		   		}
				result.close();
				statement.close();
				connection.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		return bondeEntree;
	}
	
	public List<BondeEntree> getAllBondeEntrees(String condition){
    	return this.readBondeEntrees("*", condition);
	}
	
	public BondeEntree getBondeEntree(String id){
		String condition= "id= '"+id+"'";
		return this.readBondeEntree("*", condition);
	}
	
	public BondeEntree updateBondeEntree(BondeEntree bondeEntree) {
		BondeEntree __bondeEntree = this.getBondeEntree(bondeEntree.getId());
		ProduitService produitService = new ProduitService();
		
		if(__bondeEntree == null) {
			throw new DataNotFoundException(String.format("The entrance voucher with id %s is not found", bondeEntree.getId()));
		}
		
		int new_stock = __bondeEntree.getProduit().getStock() - (__bondeEntree.getQteEntree() - bondeEntree.getQteEntree());
		bondeEntree.setProduit(__bondeEntree.getProduit());
		bondeEntree.getProduit().setStock(new_stock);
		
		produitService.updateProduit(bondeEntree.getProduit());
		
		String values = "produit='"+bondeEntree.getProduit().getId()+"', qteEntree="+bondeEntree.getQteEntree()+", dateEntree='"+useful.formatDate(bondeEntree.getDateEntree())+"'";
		String condition= "id= '"+bondeEntree.getId()+"'";
		q.update(table, values, condition);
		return bondeEntree;
	}
	
	public Produit deleteBondeEntree(BondeEntree bondeEntree) {	
		String condition= "id= '"+bondeEntree.getId()+"'";
		ProduitService produitService = new ProduitService();
		
		String id_produit = bondeEntree.getProduit().getId();
		
		int new_stock = bondeEntree.getProduit().getStock() - bondeEntree.getQteEntree();
		bondeEntree.getProduit().setStock(new_stock);
		
		produitService.updateProduit(bondeEntree.getProduit());
		
		q.delete(table, condition);
		return produitService.getProduit(id_produit);
	}
	
	public List<Produit> LoadFixtures(){
		ProduitService produitService = new ProduitService();
		List<Produit> produits= produitService.getAllProduits();
		
		  for (Produit produit : produits) {
			 
				for(int i=0; i<3; i++) {
					int qte =10 +  (int)(Math.random()*(100));
					BondeEntree bondeEntree = new BondeEntree(produit, qte, new Date());
					bondeEntree = this.createBondeEntree(bondeEntree);
					produit.addBondeEntree(bondeEntree);
				}
			
	      }
		
		return produits;
	}
}
