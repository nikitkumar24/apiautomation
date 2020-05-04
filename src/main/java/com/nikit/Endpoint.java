package com.nikit;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.PATCH;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/booking")
public interface Endpoint {

	@POST
	BookingResponse createBooking(Booking booking);

	@GET
	List<Booking> getAllBooking();

	@GET
	@Path("/{id}")
	Booking getBooking(@PathParam("id") Integer id);

	@PUT
	@Path("/{id}")
	Booking updateBooking(@PathParam("id") Integer id, Booking booking);

	@PATCH
	@Path("/{id}")
	Booking updatePartialBooking(@PathParam("id") Integer id, Booking booking);

	@DELETE
	@Path("/{id}")
	Response deleteBooking(@PathParam("id") Integer id);

}
