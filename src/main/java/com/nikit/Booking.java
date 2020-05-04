package com.nikit;

import lombok.Data;

@Data
public class Booking {

	String bookingid;
	String firstname;
	String lastname;
	Integer totalprice;
	Boolean depositpaid;
	BookingDates bookingdates;
	String additionalneeds;
}
