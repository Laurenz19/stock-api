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
public class BondeEntree {
	
	private String id;
	
	@XmlTransient
	@JsonbTransient
	private Produit produit;
	private int qteEntree;
	private Date dateEntree;
	
	
	public BondeEntree() {
		
	}
	
	public BondeEntree(Produit produit, int qteEntree, Date dateEntree) {
		super();
		this.produit = produit;
		this.qteEntree = qteEntree;
		this.dateEntree = dateEntree;
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
	public int getQteEntree() {
		return qteEntree;
	}
	public void setQteEntree(int qteEntree) {
		this.qteEntree = qteEntree;
	}
	public Date getDateEntree() {
		return dateEntree;
	}
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}
	
	
}
