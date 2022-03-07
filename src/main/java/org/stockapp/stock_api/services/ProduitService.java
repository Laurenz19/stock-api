package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeEntree;
import org.stockapp.stock_api.model.Produit;

import com.github.javafaker.Faker;

public class ProduitService {
	
	private Queries q;
	private String table = "produit";
	private String column = "design, stock";
	
	
	
	
	public ProduitService(Connection con) {
		super();
		this.q = new Queries(con);
	}

	public Produit createProduit(Produit produit) {
		int init_stock = produit.getStock();
		String values = "'"+produit.getDesign()+"', "+produit.getStock();
		
		q.create(table, column, values);
		
		produit = getProduit(maxId());
		produit.setStock(0);
		if(init_stock > 0) {
			BondeEntreeService service = new BondeEntreeService(q.getConnection());
			BondeEntree init_bon = new BondeEntree(produit, init_stock, new Date());
			service.createBondeEntree(init_bon);
		}
		
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
		   				Produit produit = new Produit(result.getString("design"), result.getInt("stock"));
		   				
		   				produit.setId(result.getString("id"));
		   				BondeEntreeService service = new BondeEntreeService(q.getConnection());
		   				List<BondeEntree> bons = service.getAllBondeEntrees("produit='"+produit.getId()+"'");
		   				
		   				for(BondeEntree bon : bons) {
		   					produit.addBondeEntree(bon);
		   				}
		   				
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
    				Produit __produit = new Produit(result.getString("design"), result.getInt("stock"));
    				__produit.setId(result.getString("id"));
    				
    				produit = __produit;
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
    				  Produit __produit = new Produit(result.getString("design"), result.getInt("stock"));
      				__produit.setId(result.getString("id"));
      				
      				produit = __produit;
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
	   				Produit produit = new Produit(result.getString("design"), result.getInt("stock"));
	   				
	   				produit.setId(result.getString("id"));
	   				
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
