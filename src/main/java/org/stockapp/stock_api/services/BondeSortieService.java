package org.stockapp.stock_api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stockapp.stock_api.DatabaseConnection;
import org.stockapp.stock_api.exception.DataIntegrityException;
import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeSortie;
import org.stockapp.stock_api.model.Produit;

public class BondeSortieService {
	
	private Queries q = new Queries();
	private String table = "bondesortie";
	private String column = "produit, qteSortie, dateSortie";
	private UsefulFunctions useful = new UsefulFunctions();
	

	public BondeSortie createBondeSortie(BondeSortie bondeSortie) {
		
		String values = "'"+bondeSortie.getProduit().getId()+"', "+bondeSortie.getQteSortie()+", '"+useful.formatDate(bondeSortie.getDateSortie())+"'";
		q.create(table, column, values);
		
		ProduitService produitService = new ProduitService();
		
		if(bondeSortie.getProduit().getStock() >= bondeSortie.getQteSortie()) {
			bondeSortie.getProduit().setStock(useful.new_stock(bondeSortie.getProduit().getStock(),0, bondeSortie.getQteSortie()));
		}else {
			throw new DataIntegrityException("The product's quantity is less than your demand");
		}
		
		produitService.updateProduit(bondeSortie.getProduit());
	    
		
		bondeSortie = this.getBondeSortie(q.maxId(this.table));
		return bondeSortie;
		
	}
	
    public List<BondeSortie> readBondeSorties(String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + this.table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
		}
		
		List<BondeSortie> bons = new ArrayList<BondeSortie>();
		ProduitService produitService = new ProduitService();
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			Connection connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			
				if (result.next() == false) {
	    			bons = new ArrayList<BondeSortie>();
		   		}else {
		   			 
		   			 do {
		   				BondeSortie bondeSortie = new BondeSortie(produitService.getProduit(result.getString("produit")),result.getInt("qteSortie"), result.getDate("dateSortie"));
						bondeSortie.setId(result.getString("id"));
		   				
		   				bons.add(bondeSortie);
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
    
    public BondeSortie readBondeSortie(String columns, String conditions) {
		
		String sql = "SELECT "+ columns + " FROM " + this.table;
		
		if(conditions != "") {
			sql = "SELECT "+ columns + " FROM " + this.table + " WHERE " + conditions;
		}
		
		BondeSortie bondeSortie = new BondeSortie();
		ProduitService produitService = new ProduitService();
		
		try{
			DatabaseConnection dbConnection = new DatabaseConnection("localhost", "3306", "stockdb", "root", "");
			Connection connection = dbConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(sql);
			
				if (result.next() == false) {
					bondeSortie = null;
		   		}else {
		   			 
		   			 do {
		   				bondeSortie = new BondeSortie(produitService.getProduit(result.getString("produit")),result.getInt("qteSortie"), result.getDate("dateSortie"));
						bondeSortie.setId(result.getString("id"));
		   				
		   		      } while (result.next());	
		   		}
				result.close();
				statement.close();
				connection.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		return bondeSortie;
	}
    
	public List<BondeSortie> getAllBondeSorties(String condition){
		return this.readBondeSorties("*", condition);
	}
	
	public BondeSortie getBondeSortie(String id){
		String condition= "id= '"+id+"'";
    	return this.readBondeSortie("*", condition);
	}
	
	public BondeSortie updateBondeSortie(BondeSortie bondeSortie) {
		BondeSortie __bondeSortie = this.getBondeSortie(bondeSortie.getId());
		ProduitService produitService = new ProduitService();
		
		if(__bondeSortie == null) {
			throw new DataNotFoundException(String.format("The exit voucher with id %s is not found", bondeSortie.getId()));
		}
		
		int new_stock = __bondeSortie.getProduit().getStock() + (__bondeSortie.getQteSortie() - bondeSortie.getQteSortie());
		if(new_stock<0) {
			throw new DataIntegrityException("The product's quantity is less than your demand");
		}
		bondeSortie.setProduit(__bondeSortie.getProduit());
		bondeSortie.getProduit().setStock(new_stock);
		
		produitService.updateProduit(bondeSortie.getProduit());
		
		String values = "produit='"+bondeSortie.getProduit().getId()+"', qteSortie="+bondeSortie.getQteSortie()+", dateSortie='"+useful.formatDate(bondeSortie.getDateSortie())+"'";
		
		String condition= "id= '"+bondeSortie.getId()+"'";
		q.update(table, values, condition);
		return bondeSortie;
	}
	
	public Produit deleteBondeSortie(BondeSortie bondeSortie) {
		
		String condition= "id= '"+bondeSortie.getId()+"'";
		ProduitService produitService = new ProduitService();
		String id_produit = bondeSortie.getProduit().getId();
		
		int new_stock = bondeSortie.getProduit().getStock() + bondeSortie.getQteSortie();
		bondeSortie.getProduit().setStock(new_stock);
		
		produitService.updateProduit(bondeSortie.getProduit());
		
		q.delete(table, condition);
		return produitService.getProduit(id_produit);
	}
	
	public List<Produit> LoadFixtures(){
		ProduitService produitService = new ProduitService();
		List<Produit> produits= produitService.getAllProduits();
		
		  for (Produit produit : produits) {
			 
				for(int i=0; i<3; i++) {
					int qte = this.generateQte(produit.getStock());
					
					BondeSortie bondeSortie = new BondeSortie(produit, qte, new Date());
					bondeSortie = this.createBondeSortie(bondeSortie);
					produit.addBondeSortie(bondeSortie);
				}
			
	      }
		
		return produits;
	}
	
	public int generateQte(int qteRestante) {
		int qte =  (int)(Math.random()*(100));
		
		if(qte>qteRestante) {
			qte = qteRestante;
		}
		
		return qte;
	}
}
