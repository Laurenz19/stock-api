package org.stockapp.stock_api.resources;

import java.net.URI;
import java.util.List;

import org.stockapp.stock_api.exception.DataIntegrityException;
import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeSortie;
import org.stockapp.stock_api.model.Produit;
import org.stockapp.stock_api.resources.beans.BondeSortieParamBean;
import org.stockapp.stock_api.services.BondeSortieService;
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

@Consumes(MediaType.APPLICATION_JSON)
@Produces(value= {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class BondeSortieResource {
	BondeSortieService service = new BondeSortieService();
	ProduitService produitService = new ProduitService();
	
	@GET
	public List<BondeSortie> getAllBondeSorties(@BeanParam BondeSortieParamBean param){
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		String condition = "produit='"+produit.getId()+"'";
		return service.getAllBondeSorties(condition);
	}
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBondeSortie(BondeSortie bondeSortie, @Context UriInfo uriInfo, @BeanParam BondeSortieParamBean param){
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		if(bondeSortie.getQteSortie() == 0) {
			throw new DataIntegrityException("The product's quantity can't be null");
		}
		
		bondeSortie.setProduit(produit);
		bondeSortie = service.createBondeSortie(bondeSortie);
		
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(bondeSortie.getId()).build();
		return Response.created(uri)
				       .entity(bondeSortie)
				       .build();
	}
	
	@PUT
	@Path("/{bondeSortieId}/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBondeSortie(BondeSortie bondeSortie, @BeanParam BondeSortieParamBean param) {
		
		bondeSortie.setId(param.getId());
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		String condition= "id= '"+bondeSortie.getId()+"' and produit='"+produit.getId()+"'";
		List<BondeSortie> bons = this.service.getAllBondeSorties(condition);
		
		if(bons == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found in the product with id %s", param.getId(), param.getProduit_Id()));
		}
		
		if(bondeSortie.getQteSortie() == 0) {
			throw new DataIntegrityException("The product's quantity can't be null");
		}
		
		
		bondeSortie = service.updateBondeSortie(bondeSortie);
		produit = this.produitService.getProduit(param.getProduit_Id());
		
		return Response.ok()
				       .entity(produit)
				       .build();
	}
	
	@DELETE
	@Path("/{bondeSortieId}/delete")
	public Response deleteBondeEntree(@BeanParam BondeSortieParamBean param) {
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		BondeSortie bondeSortie = service.getBondeSortie(param.getId());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		if(bondeSortie == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found", param.getId()));
		}
		
		String condition= "id= '"+bondeSortie.getId()+"' and produit='"+produit.getId()+"'";
		List<BondeSortie> bons = this.service.getAllBondeSorties(condition);
		
		if(bons == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found in the product with id %s", param.getId(), param.getProduit_Id()));
		}
		
		produit = service.deleteBondeSortie(bondeSortie);
		return Response.ok()
			       .entity(produit)
			       .build();
		
	}
	
	@GET
	@Path("/load-fixture")
	public List<Produit> loadFixtures(){
		return service.LoadFixtures();
	}
}
