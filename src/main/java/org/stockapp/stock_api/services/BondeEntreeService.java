package org.stockapp.stock_api.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.stockapp.stock_api.model.BondeEntree;

public class BondeEntreeService {
	
	private Queries q = new Queries();
	private String table = "bondeentree";
	private String column = "produit, qteEntree, dateEntree";
	
	public BondeEntree createBondeEntree(BondeEntree bondeEntree) {
		
		String values = "'"+bondeEntree.getProduit().getId()+"', "+bondeEntree.getQteEntree()+", '"+bondeEntree.getDateEntree()+"'";
		q.create(table, column, values);
		return bondeEntree;
		
	}
	
	public List<BondeEntree> getAllBondeEntrees(){
		ResultSet result = q.read(table, "*", "");
    	System.out.println(result);
    	List<BondeEntree> bons = new ArrayList<BondeEntree>();
    	BondeEntree bondeEntree = new BondeEntree();
    	ProduitService produitService = new ProduitService();
    	
    	try {
			while (result.next()){
				
				bondeEntree.setId(result.getString("id"));
				bondeEntree.setProduit(produitService.getProduit(result.getString("produit")));
				bondeEntree.setQteEntree(result.getInt("qteEntree"));
				bondeEntree.setDateEntree(result.getDate("dateEntree"));
				
				bons.add(bondeEntree);
				
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
    	System.out.println(result);

    	BondeEntree bondeEntree = new BondeEntree();
    	ProduitService produitService = new ProduitService();
    	
    	try {
			while (result.next()){
				
				bondeEntree.setId(result.getString("id"));
				bondeEntree.setProduit(produitService.getProduit(result.getString("produit")));
				bondeEntree.setQteEntree(result.getInt("qteEntree"));
				bondeEntree.setDateEntree(result.getDate("dateEntree"));
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		q.delete(table, condition);
	}
}
