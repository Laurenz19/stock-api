package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.stockapp.stock_api.model.BondeSortie;

public class BondeSortieService {
	
	private Queries q;
	private String table = "bondesortie";
	private String column = "produit, qteSortie, dateSortie";
	
	
	public BondeSortieService(Connection con) {
		super();
		this.q = new Queries(con);
	}

	public BondeSortie createBondeEntree(BondeSortie bondeSortie) {
		
		String values = "'"+bondeSortie.getProduit().getId()+"', "+bondeSortie.getQteSortie()+", '"+bondeSortie.getDateSortie()+"'";
		q.create(table, column, values);
		return bondeSortie;
		
	}
	
	public List<BondeSortie> getAllBondeSorties(){
		ResultSet result = q.read(table, "*", "");
    	System.out.println(result);
    	List<BondeSortie> bons = new ArrayList<BondeSortie>();
    	BondeSortie bondeSortie = new BondeSortie();
    	ProduitService produitService = new ProduitService(q.getConnection());
    	
    	try {
			while (result.next()){
				
				bondeSortie.setId(result.getString("id"));
				bondeSortie.setProduit(produitService.getProduit(result.getString("produit")));
				bondeSortie.setQteSortie(result.getInt("qteSortie"));
				bondeSortie.setDateSortie(result.getDate("dateSortie"));
				
				bons.add(bondeSortie);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bons;
	}
	
	public BondeSortie getBondeSortie(String id){
		String condition= "id= '"+id+"'";
		ResultSet result = q.read(table, "*", condition);
    	System.out.println(result);

    	BondeSortie bondeSortie = new BondeSortie();
    	ProduitService produitService = new ProduitService(q.getConnection());
    	
    	try {
			while (result.next()){
				
				bondeSortie.setId(result.getString("id"));
				bondeSortie.setProduit(produitService.getProduit(result.getString("produit")));
				bondeSortie.setQteSortie(result.getInt("qteSortie"));
				bondeSortie.setDateSortie(result.getDate("dateSortie"));
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bondeSortie;
	}
	
	public BondeSortie updateBondeSortie(BondeSortie bondeSortie) {
		BondeSortie __bondeSortie = this.getBondeSortie(bondeSortie.getId());
		if(__bondeSortie == null) {
			System.out.println("The exit ticket with an id #"+bondeSortie.getId()+ " is not exist!");
		}
		String values = "produit='"+bondeSortie.getProduit().getId()+"', qteSortie="+bondeSortie.getQteSortie()+", dateSortie='"+bondeSortie.getDateSortie()+"'";
		String condition= "id= '"+bondeSortie.getId()+"'";
		q.update(table, values, condition);
		return bondeSortie;
	}
	
	public void deleteBondeEntree(String id) {
		String condition= "id= '"+id+"'";
		q.delete(table, condition);
	}
}
