package org.stockapp.stock_api.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.Produit;

import com.github.javafaker.Faker;

public class ProduitService {
	
	private Queries q = new Queries();
	private String table = "produit";
	private String column = "design, stock";
	
	
	public Produit createProduit(Produit produit) {
		String values = "'"+produit.getDesign()+"', "+produit.getStock();
		q.create(table, column, values);
		
		produit = getProduit(maxId());
		
		return produit;
	}
	
	public List<Produit> getAllProduits(){
		ResultSet result = q.read(table, "*", "");
    	System.out.println(result);
    	List<Produit> produits = new ArrayList<Produit>();
    	
    	try {
    			
	    		if (result.next() == false) {
	    			produits = new ArrayList<Produit>();
		   		}else {
		   			 
		   			 do {
		   				Produit produit = new Produit();
		   				
		   				produit.setId(result.getString("id"));
		   				produit.setDesign(result.getString("design"));
		   				produit.setStock(result.getInt("stock"));
		   				
		   				produits.add(produit);
		   		      } while (result.next());	
		   			
		   		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return produits;
	}
	
	public Produit getProduit(String id) {
		String condition= "id= '"+id+"'";
		ResultSet result = q.read(table, "*", condition);
    	System.out.println(result);
    	Produit produit = new Produit();
    	
    	try {
    		if (result.next() == false) {
    		     produit = null;
    		}else {
    			 
    			 do {
    				produit.setId(result.getString("id"));
    				produit.setDesign(result.getString("design"));
    				produit.setStock(result.getInt("stock"));
    		      } while (result.next());	
    			
    		}
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return produit;
    
	}
	
	public Produit getProduitbyDesign(String design) {
		String condition= "design= '"+design+"'";
		ResultSet result = q.read(table, "*", condition);
    	System.out.println(result);
    	Produit produit = new Produit();
    	
    	try {
    		if (result.next() == false) {
    		     produit = null;
    		}else {
    			 
    			 do {
    				produit.setId(result.getString("id"));
    				produit.setDesign(result.getString("design"));
    				produit.setStock(result.getInt("stock"));
    		      } while (result.next());	
    			
    		}
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return produit;
    
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
	
	public List<Produit> LoadFixtures(){
		List<Produit> produits = new ArrayList<Produit>();
		Faker faker = new Faker();
		Produit produit = new Produit();
		this.deleteProduit("");
		
		for(int i = 0; i< 100; i++) {
			int stock =10 +  (int)(Math.random()*(100));
			produit.setDesign(faker.commerce().productName());
			produit.setStock(stock);
			this.createProduit(produit);
		}
		
		produits = this.getAllProduits(); 
		return produits;
	}
	
	public String maxId() {
		return q.maxId(table);
	}
	
	public List<Produit> searchFilter(String condition){
		ResultSet result = q.read(table, "*", condition);
    	System.out.println(result);
    	List<Produit> produits = new ArrayList<Produit>();
    	
    	try {
    		if (result.next() == false) {
    			produits = new ArrayList<Produit>();
	   		}else {
	   			 
	   			 do {
	   				Produit produit = new Produit();
	   				
	   				produit.setId(result.getString("id"));
	   				produit.setDesign(result.getString("design"));
	   				produit.setStock(result.getInt("stock"));
	   				
	   				produits.add(produit);
	   		      } while (result.next());	
	   			
	   		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return produits;
	}
	
}
