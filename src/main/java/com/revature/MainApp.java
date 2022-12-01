package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.TicketController;
import com.revature.controllers.UserController;

import io.javalin.Javalin;

public class MainApp {

	public static Logger logger = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {

		Javalin app = Javalin.create().start(9000);

		app.before(ctx -> {
			logger.info("There was a request at URL '" + ctx.url() + "'....");
		});

		app.after(ctx -> {
			logger.info("Request at URL '" + ctx.url() + "' has completed successfully.\n");
		});

		// GET
		app.get("/test", ctx -> {
			logger.info("Testing app...");
			ctx.html("Welcome to the Expense Reimbursement System");
		});
		app.get("/users/{id}", UserController.getUserById);
		app.get("/tickets/{id}", TicketController.getTicketById);
		app.get("/lister/{author}", TicketController.getTicketsByAuthorId);
		app.get("/lister", TicketController.getAllT);

		// POST
		app.post("/users/register", UserController.register);
		app.post("/users/login", UserController.login);
		app.post("/tickets/submit", TicketController.createTicket);

		// PUT
		app.put("/users/{id}", UserController.update);
		app.put("/tickets/{id}", TicketController.resolve);

		// DELETE
		app.delete("/users/{id}", UserController.delete);

	}
}