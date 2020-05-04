package com.nikit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingTest {

	Endpoint books;
	Integer id;

	private static final String BASEURL = "https://restful-booker.herokuapp.com";

	@BeforeClass(description = "Intilization of token")
	public void init() {

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJsonProvider());

		AuthEndpoint auth = JAXRSClientFactory.create(BASEURL, AuthEndpoint.class, providers);

		Credentials creds = new Credentials();
		creds.setPassword("password123");
		creds.setUsername("admin");
		AuthToken authToken = auth.getToken(creds);
		Map<String, String> headers = new HashMap<String, String>();

		System.out.println("Token " + authToken.getToken());
		headers.put("Cookie", "token=" + authToken.getToken());

		JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
		bean.setProviders(providers);
		bean.setHeaders(headers);
		bean.setResourceClass(Endpoint.class);
		bean.setAddress(BASEURL);
		books = (Endpoint) bean.create(Endpoint.class);
	}

	@Test(description = "getting all bookings")
	public void testGetAllBookings() {
		assertNotEquals(books.getAllBooking().size(), 0);
		log.info("" + books.getAllBooking());
	}

	@Test(description = "getting one booking", priority = 2, dependsOnMethods = { "testCreateBooking" })
	public void testGetBooking() {
		Booking res = books.getBooking(id);
		assertFalse(res.getDepositpaid());
	}

	@Test(description = "create new booking", priority = 1, dataProvider = "getBooking")
	public void testCreateBooking(Booking booking) {
		BookingResponse res = books.createBooking(booking);
		id = res.getBookingid();
		System.out.println("ID " + id);
	}

	@Test(description = "delete old booking", priority = 3, dependsOnMethods = { "testCreateBooking" })
	public void testDeleteBooking() {
		Response res = books.deleteBooking(id);
		res.readEntity(String.class);
	}

	@Test(description = "update booking", dependsOnMethods = { "testCreateBooking" })
	public void testUpdateBooking(Booking booking) {

		Booking res = books.updateBooking(id, booking);
	}

	@DataProvider
	public Object[][] getBooking() {
		List<Booking> bookings = new ArrayList<Booking>();
		Booking booking = new Booking();
		booking.setBookingid("101");
		booking.setDepositpaid(true);
		booking.setFirstname("nikit");
		booking.setLastname("kumar");
		booking.setTotalprice(3000);
		BookingDates bookingdate = new BookingDates();
		bookingdate.setCheckin("2018-01-01");
		bookingdate.setCheckout("2018-01-05");
		booking.setBookingdates(bookingdate);
		booking.setAdditionalneeds("NONE");
		bookings.add(booking);
		bookings.add(booking);
		return new Object[][] { { bookings.get(0) }, { bookings.get(1) } };
	}

}
