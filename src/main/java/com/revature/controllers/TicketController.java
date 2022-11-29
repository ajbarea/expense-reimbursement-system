package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.Ticket;
import com.revature.services.TicketService;
import com.revature.services.TicketServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class TicketController {

	private static Logger logger = LoggerFactory.getLogger(TicketController.class);

	private static TicketService tServ = new TicketServiceImpl();

	public static Handler createTicket = ctx -> {
		logger.info("User is making a reimbursement ticket submission...");

		String body = ctx.body();

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		Ticket target = om.readValue(body, Ticket.class);
		logger.info("New reimbursement ticket: " + target);

		boolean isCreated = tServ.createTicket(target);

		if (isCreated == true) {
			ctx.html("The new reimbursement ticket has been submitted successfully.");
			ctx.status(HttpStatus.CREATED);
		} else {
			ctx.html("Error during ticket creation. Please try again.");
			ctx.status(HttpStatus.NO_CONTENT);
		}
	};

}
