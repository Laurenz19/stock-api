package org.stockapp.stock_api.model;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
public class Produit {
	
	private String id;
	private String design;
	private int stock;
	
	public Produit() {
		
	}
	
	public Produit(String design, int stock) {
		super();
		this.design = design;
		this.stock = stock;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
