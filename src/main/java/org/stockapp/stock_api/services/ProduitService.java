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
import org.stockapp.stock_api.model.BondeSortie;
import org.stockapp.stock_api.model.Produit;

import com.github.javafaker.Faker;

public class ProduitService {
	
	private Queries q = new Queries();
	private String table = "produit";
	private String column = "design, stock";
	

	public Produit createProduit(Produit produit) {
		int init_stock = produit.getStock();
		String values = "'"+produit.getDesign()+"', "+produit.getStock();
		
		q.create(table, column, values);
		
		produit = getProduit(q.maxId(this.table));
		produit.setStock(0);
		if(init_stock > 0) {
			BondeEntreeService service = new BondeEntreeService();
			BondeEntree init_bon = new BondeEntree(produit, init_stock, new Date());
			service.createBondeEntree(init_bon);
		}
		
		return this.getProduit(produit.getId());
	}
	
	
	 public List<Produit> readProducts(String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + this.table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
		}
		
		List<Produit> produits = new ArrayList<Produit>();
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			Connection connection = dbConnection.getConnection();
			System.out.println(connection);
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			
				if (result.next() == false) {
	    			produits = new ArrayList<Produit>();
		   		}else {
		   			 
		   			 do {
		   				Produit produit = new Produit(result.getString("design"), result.getInt("stock"));
		   				produit.setId(result.getString("id"));
		   				
		   				
		   				/****************************************
		   				 * Get All BondeEntree for each product *
		   				 ****************************************/
		   				
		   				String sql1 = "SELECT * FROM bondeentree WHERE produit='"+produit.getId()+"'";
		   				
		   				
		   				PreparedStatement statement1 = connection.prepareStatement(sql1);
		   				ResultSet result1 = statement1.executeQuery(sql1);
		   				
		   				while(result1.next()) {
		   					System.out.println(result1.getString("produit"));
		   					BondeEntree bondeEntree = new BondeEntree(produit,result1.getInt("qteEntree"), result1.getDate("dateEntree"));
							bondeEntree.setId(result1.getString("id"));
							produit.addBondeEntree(bondeEntree);
		   				}
		   				
		   				result1.close();
						statement1.close();
		   				
		   				/****************************************
		   				 * Get All BondeSorties for each product*
		   				 ****************************************/
		   				
		   				
		   				String sql2 = "SELECT * FROM bondesortie WHERE produit='"+produit.getId()+"'";
		   				PreparedStatement statement2 = connection.prepareStatement(sql2);
		   				ResultSet result2 = statement2.executeQuery(sql2);
		   				
		   				while(result2.next()) {
		   					BondeSortie bondeSortie = new BondeSortie(produit,result2.getInt("qteSortie"), result2.getDate("dateSortie"));
							bondeSortie.setId(result2.getString("id"));
							produit.addBondeSortie(bondeSortie);
		   				}
		   				
		   				result2.close();
						statement2.close();
		   				
		   				produits.add(produit);
		   		      } while (result.next());	
		   		}
				result.close();
				statement.close();
				connection.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		return produits;
	}
	 
	 
	 public Produit readProduct(String columns, String conditions) {
			
			String sql = "SELECT "+ columns + " FROM " + this.table;
			
			if(conditions != "") {
				sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
			}
			Produit produit = new Produit();
			
			try{
				DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
				Connection connection = dbConnection.getConnection();
				
				PreparedStatement statement = connection.prepareStatement(sql);
				
				ResultSet result = statement.executeQuery(sql);
				
					if (result.next() == false) {
		    			produit = null;
			   		}else {
			   			 
			   			 do {
			   				produit = new Produit(result.getString("design"), result.getInt("stock"));
			   				produit.setId(result.getString("id"));
			   				
			   				/****************************************
			   				 * Get All BondeEntrees for the product *
			   				 ****************************************/
			   				
			   				String sql1 = "SELECT * FROM bondeentree WHERE produit='"+produit.getId()+"'";
			   				
			   				PreparedStatement statement1 = connection.prepareStatement(sql1);
			   				ResultSet result1 = statement1.executeQuery(sql1);
			   				
			   				while(result1.next()) {
			   					BondeEntree bondeEntree = new BondeEntree(produit,result1.getInt("qteEntree"), result1.getDate("dateEntree"));
								bondeEntree.setId(result1.getString("id"));
								produit.addBondeEntree(bondeEntree);
			   				}
			   				
			   				result1.close();
							statement1.close();
			   				/****************************************
			   				 * Get All BondeSorties for the product *
			   				 ****************************************/
			   				
			   				
			   				String sql2 = "SELECT * FROM bondesortie WHERE produit='"+produit.getId()+"'";
			   				PreparedStatement statement2 = connection.prepareStatement(sql2);
			   				ResultSet result2 = statement2.executeQuery(sql2);
			   				
			   				while(result2.next()) {
			   					BondeSortie bondeSortie = new BondeSortie(produit, result2.getInt("qteSortie"), result2.getDate("dateSortie"));
								bondeSortie.setId(result2.getString("id"));
								produit.addBondeSortie(bondeSortie);
			   				}
			   				
			   				result2.close();
							statement2.close();
			   				
			   		      } while (result.next());	
			   		}
					
					result.close();
					statement.close();
					connection.close();
				
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
			
			return produit;
			
		} 
	
	public List<Produit> getAllProduits(){
    	return this.readProducts("*", "");
	}
	
	public Produit getProduit(String id) {
		String condition= "id= '"+id+"'";
		return this.readProduct("*", condition);
	}
	
	public Produit getProduitbyDesign(String design) {
		String condition= "design= '"+design+"'";
		return this.readProduct("*", condition);
	}
	
	
	public Produit updateProduit(Produit produit) {
		Produit __produit = this.getProduit(produit.getId());
		if(__produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", produit.getId()));
		}
		String values = "design='"+produit.getDesign()+"', stock="+produit.getStock();
		String condition= "id= '"+produit.getId()+"'";
		q.update(table, values, condition);
		return produit;
	}
	
	public void deleteProduit(String id) {
		String condition= "id= '"+id+"'";
		if(id == "") {
			condition = "";
		}
		
		q.delete(table, condition);
	}

	
	public List<Produit> searchFilter(String condition){
		return this.readProducts("*", condition);
	}
	
	public List<Produit> LoadFixtures(){
		List<Produit> produits = new ArrayList<Produit>();
		Faker faker = new Faker();
		this.deleteProduit("");
		
		for(int i = 0; i< 100; i++) {
			int stock =10 +  (int)(Math.random()*(100));
			Produit produit = new Produit();
			String product_name = faker.commerce().productName();
			
			produit.setDesign(this.getProductNameAvalaible(product_name));
			
			produit.setStock(stock);
			this.createProduit(produit);
		}
		
		produits = this.getAllProduits(); 
		return produits;
	}
	
	public String getProductNameAvalaible(String name) {
		Faker faker = new Faker();
		if(this.getProduitbyDesign(name) != null) {
			name=  getProductNameAvalaible(faker.commerce().productName());
		}
		return name;
	}
	
}
