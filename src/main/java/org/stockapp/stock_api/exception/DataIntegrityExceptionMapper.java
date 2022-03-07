package org.stockapp.stock_api.exception;

import org.stockapp.stock_api.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataIntegrityExceptionMapper  implements ExceptionMapper<DataIntegrityException>{

	@Override
	public Response toResponse(DataIntegrityException exception) {
		// TODO Auto-generated method stub
		ErrorMessage error = new ErrorMessage(406, exception.getMessage());
		return Response.status(Status.NOT_ACCEPTABLE)
					   .entity(error)
				       .build();
	}

}
