package org.stockapp.stock_api.exception;

import org.stockapp.stock_api.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		// TODO Auto-generated method stub
		ErrorMessage error = new ErrorMessage(404, exception.getMessage());
		return Response.status(Status.NOT_FOUND)
					   .entity(error)
				       .build();
	}

}
