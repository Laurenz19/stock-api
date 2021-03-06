package org.stockapp.stock_api.resources;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.Produit;
import org.stockapp.stock_api.resources.beans.ProduitFilterBean;
import org.stockapp.stock_api.services.ProduitService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("produits")
@Produces( value= { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Consumes(MediaType.APPLICATION_JSON)
public class ProduitResource {
	
	ProduitService produitService = new ProduitService();
	
	@GET
	public List<Produit> getAllProduits(@BeanParam ProduitFilterBean filterBean) {
		List<Produit> produits= new ArrayList<Produit>();
		
		if(filterBean.getDesign() != null){
			String condition = "design LIKE '%" + filterBean.getDesign() + "%'";
			produits= this.produitService.searchFilter(condition);
		}else {
			produits= this.produitService.getAllProduits();
		}
		
		return produits;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProduit(Produit produit, @Context UriInfo uriInfo){
		Produit __produit = this.produitService.getProduitbyDesign(produit.getDesign());
		
		if(__produit != null) {
			throw new DataNotFoundException(String.format("Product with design %s exist in the database", produit.getDesign()));
		}
		produit =this.produitService.createProduit(produit);
		
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(produit.getId()).build();
		return Response.created(uri)
				       .entity(produit)
				       .build();
	}
	
	@PUT
	@Path("/{produitId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduit(Produit produit, @BeanParam ProduitFilterBean filterBean) {
		produit.setId(filterBean.getId());
		
		Produit __produit = this.produitService.getProduitbyDesign(produit.getDesign());
		
		if(__produit != null) {
			if(produit.getId() != __produit.getId()) {
				throw new DataNotFoundException(String.format("Product with design %s exist in the database", produit.getDesign()));
			}
		}
		produit = this.produitService.updateProduit(produit);
		
		return Response.ok()
				       .entity(produit)
				       .build();
	}
	
	@GET
	@Path("/{produitId}")
	public Produit getProduit(@BeanParam ProduitFilterBean filterBean) {
		Produit produit = this.produitService.getProduit(filterBean.getId());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", filterBean.getId()));
		}
		return produit;
	}
	
	
	@DELETE
	@Path("/{produitId}")
	public Response deleteProduit(@BeanParam ProduitFilterBean filterBean) {
		this.produitService.deleteProduit(filterBean.getId());
		return Response.noContent()
				       .build();
	}
	
	/**
	 * Here we implement the sub-resource
	 * it is annotated with Path annotation but no with HTTP method 
	 **/
	@Path("/{produitId}/bondeEntrees")
	public BondeEntreeResource getBondeEntreeResource() {
		return new BondeEntreeResource();
	}
	  
	/**
	 * Here we implement the sub-resource
	 * it is annotated with Path annotation but no with HTTP method 
	 **/
	@Path("/{produitId}/bondeSorties")
	public BondeSortieResource getBondeSortieResource() {
		return new BondeSortieResource();
	}
	
	@GET
	@Path("/load-fixtures")
	public List<Produit> loadFixtures(){
		return this.produitService.LoadFixtures();
	}
	
//	@GET
//	@Path("/max-id")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getMaxID() {
//		return this.produitService.maxId();
//	}
	
	
//	/**
//	 * Here we implement the sub-resource
//	 * it is annotated with Path annotation but no with HTTP method 
//	 **/
//	@GET
//	@Path("/test")
//	public String test() throws SQLException {
//		try {
//			return "test";
//		}finally {
//			 hikariConn.close();
//		}
//		
//	}
	

}
