package org.stockapp.stock_api.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.stockapp.stock_api.model.Produit;

public class ProduitService {
	
	private Queries q = new Queries();
	private String table = "produit";
	private String column = "design, stock";
	
	
	public Produit createProduit(Produit produit) {
		String values = "'"+produit.getDesign()+"', "+produit.getStock();
		q.create(table, column, values);
		return produit;
	}
	
	public List<Produit> getAllProduits(){
		ResultSet result = q.read(table, "*", "");
    	System.out.println(result);
    	List<Produit> produits = new ArrayList<Produit>();
    	Produit produit = new Produit();
    	
    	try {
			while (result.next()){
				
				produit.setId(result.getString("id"));
				produit.setDesign(result.getString("design"));
				produit.setStock(result.getInt("stock"));
				produits.add(produit);
				
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
			while (result.next()){
				
				produit.setId(result.getString("id"));
				produit.setDesign(result.getString("design"));
				produit.setStock(result.getInt("stock"));
				
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
			System.out.println("The product with an id #"+produit.getId()+ " is not exist!");
		}
		String values = "design='"+produit.getDesign()+"', stock="+produit.getStock();
		String condition= "id= '"+produit.getId()+"'";
		q.update(table, values, condition);
		return produit;
	}
	
	public void deleteProduit(String id) {
		String condition= "id= '"+id+"'";
		q.delete(table, condition);
	}
	
}
