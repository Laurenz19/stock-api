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
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() { 	
        return "Got it!";
    }
}
