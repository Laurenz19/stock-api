package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stockapp.stock_api.HikariConnection;
import org.stockapp.stock_api.model.BondeEntree;
import org.stockapp.stock_api.model.Produit;

public class BondeEntreeService {
	
	private Queries q;
	private String table = "bondeentree";
	private String column = "produit, qteEntree, dateEntree";
	private HikariConnection hikariConn = new HikariConnection("localhost", "3306", "root", "", "stockdb");
	
	
	public BondeEntreeService(Connection con) {
		super();
		this.q = new Queries(con);
	}

	public BondeEntree createBondeEntree(BondeEntree bondeEntree) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(bondeEntree.getDateEntree());
		
		String produit_id = bondeEntree.getProduit().getId();
		
		String values = "'"+produit_id+"', "+bondeEntree.getQteEntree()+", '"+date+"'";
		q.create(table, column, values);
		
		
		bondeEntree = this.getBondeEntree(maxId());
		
	    
		return bondeEntree;
		
	}
	
	public List<BondeEntree> getAllBondeEntrees(String condition){
		ResultSet result = q.read(table, "*", condition);
    	System.out.println(result);
    	List<BondeEntree> bons = new ArrayList<BondeEntree>();
   
    	ProduitService produitService = new ProduitService(q.getConnection());
    	
    	try {
    		
    		if (result.next() == false) {
    			bons = new ArrayList<BondeEntree>();
    			System.out.println("vide");
	   		}else {
	   			 
	   			 do {
	   				BondeEntree bondeEntree = new BondeEntree(produitService.getProduit(result.getString("produit")),result.getInt("qteEntree"), result.getDate("dateEntree"));
					bondeEntree.setId(result.getString("id"));
					/*bondeEntree.setProduit(produitService.getProduit(result.getString("produit")));
					bondeEntree.setQteEntree(result.getInt("qteEntree"));
					bondeEntree.setDateEntree(result.getDate("dateEntree"));*/
	   				
	   				bons.add(bondeEntree);
	   		      } while (result.next());	
	   			
	   		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bons;
	}
	
	public BondeEntree getBondeEntree(String id){
		String condition= "id= '"+id+"'";
		ResultSet result = q.read(table, "*", condition);
    	System.out.println("I am here");
    	
    	BondeEntree bondeEntree = new BondeEntree();
    	ProduitService produitService = new ProduitService(q.getConnection());
    	
    	try {
    		
    		if (result.next() == false) {
    			bondeEntree = null;
	   		}else {
	   			 
	   			 do {
	   				BondeEntree __bondeEntree = new BondeEntree(produitService.getProduit(result.getString("produit")),result.getInt("qteEntree"), result.getDate("dateEntree"));
					__bondeEntree.setId(result.getString("id"));
					/*bondeEntree.setProduit(produitService.getProduit(result.getString("produit")));
					bondeEntree.setQteEntree(result.getInt("qteEntree"));
					bondeEntree.setDateEntree(result.getDate("dateEntree"));*/
					
					
					bondeEntree = __bondeEntree;
	   		      } while (result.next());	
	   			 
	   			//q.connection().close();
	   		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 hikariConn.close();
		}
    	
    	return bondeEntree;
	}
	
	public BondeEntree updateBondeEntree(BondeEntree bondeEntree) {
		BondeEntree __bondeEntree = this.getBondeEntree(bondeEntree.getId());
		if(__bondeEntree == null) {
			System.out.println("The entrance voucher with an id #"+bondeEntree.getId()+ " is not exist!");
		}
		String values = "produit='"+bondeEntree.getProduit().getId()+"', qteEntree="+bondeEntree.getQteEntree()+", dateEntree='"+bondeEntree.getDateEntree()+"'";
		String condition= "id= '"+bondeEntree.getId()+"'";
		q.update(table, values, condition);
		return bondeEntree;
	}
	
	public void deleteBondeEntree(String id) {	
		String condition= "id= '"+id+"'";
		if(id == "") {
			condition = "";
		}
		
		q.delete(table, condition);
	}
	
	public List<Produit> LoadFixtures(){
		ProduitService produitService = new ProduitService(q.getConnection());
		List<Produit> produits= produitService.getAllProduits();
		this.deleteBondeEntree("");
		
		  for (Produit produit : produits) {
			 
				for(int i=0; i<3; i++) {
					int qte =10 +  (int)(Math.random()*(100));
					BondeEntree bondeEntree = new BondeEntree(produit, qte, new Date());
					bondeEntree = this.createBondeEntree(bondeEntree);
					produit.addBondeEntree(bondeEntree);
					produit.setStock(new_stock(produit.getStock(), bondeEntree.getQteEntree()));
					
				}
			
	      }
		
		return produits;
	}
	
	public int new_stock(int ancien_stock, int qteEntree) {
		return ancien_stock + qteEntree;
	}
	
	public String maxId() {
		return q.maxId(table);
	}
}
