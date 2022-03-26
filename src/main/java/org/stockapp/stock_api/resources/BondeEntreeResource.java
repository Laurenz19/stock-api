package org.stockapp.stock_api.resources;

import java.net.URI;
import java.util.List;

import org.stockapp.stock_api.exception.DataIntegrityException;
import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeEntree;
import org.stockapp.stock_api.model.Produit;
import org.stockapp.stock_api.resources.beans.BondeEntreeParamBean;
import org.stockapp.stock_api.services.BondeEntreeService;
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
public class BondeEntreeResource {

	BondeEntreeService service = new BondeEntreeService();
	ProduitService produitService = new ProduitService();
	
	@GET
	public List<BondeEntree> getAllBondeEntrees(@BeanParam BondeEntreeParamBean param){
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		String condition = "produit='"+produit.getId()+"'";
		return service.getAllBondeEntrees(condition);
	}
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBondeEntree(BondeEntree bondeEntree, @Context UriInfo uriInfo, @BeanParam BondeEntreeParamBean param){
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		if(bondeEntree.getQteEntree() == 0) {
			throw new DataIntegrityException("The product's quantity can't be null");
		}
		
		bondeEntree.setProduit(produit);
		bondeEntree = service.createBondeEntree(bondeEntree);
		
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(bondeEntree.getId()).build();
		return Response.created(uri)
				       .entity(bondeEntree)
				       .build();
	}
	
	@PUT
	@Path("/{bondeEntreeId}/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBondeEntree(BondeEntree bondeEntree, @BeanParam BondeEntreeParamBean param) {
		
		bondeEntree.setId(param.getId());
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		String condition= "id= '"+bondeEntree.getId()+"' and produit='"+produit.getId()+"'";
		List<BondeEntree> bons = this.service.getAllBondeEntrees(condition);
		
		if(bons == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found in the product with id %s", param.getId(), param.getProduit_Id()));
		}
		
		if(bondeEntree.getQteEntree() == 0) {
			throw new DataIntegrityException("The product's quantity can't be null");
		}
		
		
		bondeEntree = service.updateBondeEntree(bondeEntree);
		produit = this.produitService.getProduit(param.getProduit_Id());
		
		return Response.ok()
				       .entity(produit)
				       .build();
	}
	
	@DELETE
	@Path("/{bondeEntreeId}/delete")
	public Response deleteBondeEntree(@BeanParam BondeEntreeParamBean param) {
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		BondeEntree bondeEntree = service.getBondeEntree(param.getId());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		
		if(bondeEntree == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found", param.getId()));
		}
		
		String condition= "id= '"+bondeEntree.getId()+"' and produit='"+produit.getId()+"'";
		List<BondeEntree> bons = this.service.getAllBondeEntrees(condition);
		
		if(bons == null) {
			throw new DataNotFoundException(String.format("The entrance voucher %s is not found in the product with id %s", param.getId(), param.getProduit_Id()));
		}
		
		produit = service.deleteBondeEntree(bondeEntree);
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
