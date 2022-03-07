package org.stockapp.stock_api.resources.beans;

import jakarta.ws.rs.PathParam;

public class BondeEntreeParamBean {
	private @PathParam("produitId") String produit_Id;

	public String getProduit_Id() {
		return produit_Id;
	}

	public void setProduit_Id(String produit_Id) {
		this.produit_Id = produit_Id;
	}

	
	
}
