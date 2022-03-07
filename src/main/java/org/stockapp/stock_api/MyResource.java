package org.stockapp.stock_api;

/*import java.sql.ResultSet;
import java.sql.SQLException;*/

//import org.stockapp.stock_api.services.Queries;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	
	//Queries queries = new Queries();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	//queries.create("produit", "design, stock", "'bleu', 2");
    	//queries.update("produit", "design='rouge', stock=0", "id='P0001'");
    	/*ResultSet result = querie.read("produit", "*", "");
    	System.out.println(result);
    	
    	try {
			while (result.next()){
				
				String id = result.getString("id");
			    String design = result.getString("design");
			    int stock = result.getInt("stock");
			 
			    String output = "Produit #%s: %s - %d";
			    System.out.println(String.format(output, id, design, stock));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	//queries.delete("produit", "id='P0000'");
    	
        return "Got it!";
    }
}
