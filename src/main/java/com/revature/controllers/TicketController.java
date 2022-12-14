package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.TicketServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class TicketController {

	private static Logger logger = LoggerFactory.getLogger(TicketController.class);

	private static TicketService tServ = new TicketServiceImpl();
	private static UserService uServ = new UserServiceImpl();

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

	public static Handler getTicketById = ctx -> {
		logger.info("A database search request has been recieved...");

		int id = Integer.parseInt(ctx.pathParam("id"));

		Ticket ticket = tServ.getTicketById(id);

		if (ticket != null && ticket.getDescription() != null) {
			ctx.html("Ticket successfully retrieved from database.");
			ctx.json(ticket);
		} else {
			ctx.html("ERROR: Could not find Ticket ID " + id + " in the database. Please try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};

	public static Handler getTicketsByAuthorId = ctx -> {
		logger.info("A database search request has been recieved...");

		int author = Integer.parseInt(ctx.pathParam("author"));

		List<Ticket> allTickets = tServ.getTicketsByAuthorId(author);

		if (allTickets != null) {
			ctx.html("Tickets successfully retrieved from database.");
			ctx.json(allTickets);
		} else {
			ctx.html("ERROR: Could not find Author ID " + author + " in the database. Please try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};

	public static Handler resolve = ctx -> {

		try {
			String user_name = ctx.req().getHeader("Auth-Cookie").replaceAll("woof9000bark", "");
			logger.info("Authentication cookie: " + user_name);

			User target = uServ.getUserByUsername(user_name);
			logger.info("Based on cookie, this is your logged in user: " + target);

			if (target.getRole() == 2) {
				int id = Integer.parseInt(ctx.pathParam("id"));

				Ticket temp = tServ.getTicketById(id);

				if (temp.getStatus() != 1) {
					String s = temp.getStatus() == 2 ? "APPROVED" : "DENIED";
					ctx.html("Ticket " + id + " has already been " + s + ".");
					return;
				}

				String body = ctx.body();

				ObjectMapper om = new ObjectMapper();

				om.registerModule(new JavaTimeModule());

				Ticket ticket = om.readValue(body, Ticket.class);

				ticket.setId(id);

				boolean isResolved = tServ.updateTicket(ticket);

				if (isResolved == true) {
					ctx.html("Ticket " + id + " has been resolved successfully.");
					ctx.status(HttpStatus.OK);
				} else {
					ctx.html("ERROR: Could not resolve Ticket #" + id + " in the database. Please try again.");
					ctx.status(HttpStatus.BAD_REQUEST);
				}
			} else {
				ctx.html("Sorry, this user is not authorized to perform this operation.");
				logger.info("Sorry, this user is not authorized to perform this operation.");
			}
		} catch (NullPointerException e) {
			logger.info("NullPointerException while updating. Error:" + e.getMessage());
			ctx.html("Sorry, this user is not authorized to perform this operation.");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
	};

	public static Handler getAllT = ctx -> {
		logger.info("TicketController::getAllT - A database search request has been recieved...");
		List<Ticket> allTickets = tServ.getAllT();
		if (allTickets != null) {
			ctx.html("Tickets successfully retrieved from database.");
			ctx.json(allTickets);
		} else {
			ctx.html("ERROR: Could not find ticket table in the database. Please try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};

	public static Handler getAllByStatus = ctx -> {
		logger.info("TicketController::getAllByStatus - A database search request has been recieved...");

		int status = Integer.parseInt(ctx.pathParam("status"));

		List<Ticket> allTickets = tServ.getAllByStatus(status);
		if (allTickets != null) {
			ctx.html("Tickets successfully retrieved from database.");
			ctx.json(allTickets);
		} else {
			ctx.html("ERROR: Could not find ticket table in the database. Please try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
}
