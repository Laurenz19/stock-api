package org.stockapp.stock_api.resources.beans;

import jakarta.ws.rs.PathParam;

public class BondeSortieParamBean {
	private @PathParam("produitId") String produit_Id;
	private @PathParam("bondeSortieId") String id;
	
	public String getProduit_Id() {
		return produit_Id;
	}
	public void setProduit_Id(String produit_Id) {
		this.produit_Id = produit_Id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
}
