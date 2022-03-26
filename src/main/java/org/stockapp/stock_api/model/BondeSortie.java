package org.stockapp.stock_api.model;

import java.util.Date;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
@XmlRootElement
public class BondeSortie {
	
	private String id;
	
	@XmlTransient
	@JsonbTransient
	private Produit produit;
	private int qteSortie;
	private Date dateSortie;
	
	
	public BondeSortie() {
		
	}
	
	public BondeSortie(Produit produit, int qteSortie, Date dateSortie) {
		super();
		this.produit = produit;
		this.qteSortie = qteSortie;
		this.dateSortie = dateSortie;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Produit getProduit() {
		return produit;
	}
	
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	public int getQteSortie() {
		return qteSortie;
	}
	
	public void setQteSortie(int qteSortie) {
		this.qteSortie = qteSortie;
	}
	
	public Date getDateSortie() {
		return dateSortie;
	}
	
	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}
	
	
}
