package org.stockapp.stock_api.resources;

import java.net.URI;
import java.sql.Connection;
import java.util.List;

import org.stockapp.stock_api.HikariConnection;
import org.stockapp.stock_api.exception.DataIntegrityException;
import org.stockapp.stock_api.exception.DataNotFoundException;
import org.stockapp.stock_api.model.BondeEntree;
import org.stockapp.stock_api.model.Produit;
import org.stockapp.stock_api.resources.beans.BondeEntreeParamBean;
import org.stockapp.stock_api.services.BondeEntreeService;
import org.stockapp.stock_api.services.ProduitService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(value= {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class BondeEntreeResource {
    
	private HikariConnection hikariConn = new HikariConnection("localhost", "3306", "root", "", "stockdb");
	private Connection connection= hikariConn.getConnection();

	BondeEntreeService service = new BondeEntreeService(connection);
	ProduitService produitService = new ProduitService(connection);
	
	@GET
	public List<BondeEntree> getAllBondeEntrees(@BeanParam BondeEntreeParamBean param){
		Produit produit = this.produitService.getProduit(param.getProduit_Id());
		if(produit == null) {
			throw new DataNotFoundException(String.format("Product with id %s is not found", param.getProduit_Id()));
		}
		String condition = "produit='"+produit.getId()+"'";
		return service.getAllBondeEntrees(condition);
	}
	
	@GET
	@Path("/load-fixture")
	public List<Produit> loadFixtures(){
		return service.LoadFixtures();
	}
	
	@POST
	@Path("/add-bondeEntree")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBondeEntree(BondeEntree bondeEntree, @Context UriInfo uriInfo, @BeanParam BondeEntreeParamBean param){
		try {
			System.out.println("I am out here");
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
		}finally {
			 hikariConn.close();
		}
	}
	
	/*@GET
	@Path("/load-fixture")
	public BondeEntree loadFixtures(){
		return service.getBondeEntree("B0001");
	}*/
	
	
}
