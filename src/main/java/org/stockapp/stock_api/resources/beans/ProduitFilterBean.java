package org.stockapp.stock_api.resources.beans;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

public class ProduitFilterBean {
	private @QueryParam("design") String design;
	private @PathParam("produitId") String id;
	
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
	
}
