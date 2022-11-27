package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.UserController;

import io.javalin.Javalin;

public class MainApp {

	public static Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(9000);
		
		app.before(ctx -> {
			logger.info(">>> There was a request at URL '" +ctx.url() + "'....");
		});
		
		app.after(ctx -> {
			logger.info(">>> Request at URL '" +ctx.url() + "' has connected successfully.");
		});
		
		// GET
		app.get("/test", ctx -> {
			logger.info(">>> Testing app...");
			ctx.html("Welcome to the Expense Reimbursement System");
		});
		
		// POST
		app.post("users/register", UserController.register);
	}
}