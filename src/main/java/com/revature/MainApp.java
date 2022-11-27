package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;

public class MainApp {

	public static Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args) {
		
		// start server using Javalin
		Javalin app = Javalin.create().start(9000);
		
		app.before(ctx -> {
			logger.info("Request at URL " +ctx.url() + " has started.");
		});
		
		
		app.after(ctx -> {
			logger.info("Request at URL " +ctx.url() + " is complete.");
		});
		
		
		app.get("/test", ctx -> {
			logger.info("Testing app...");
			ctx.html("Welcome to the Expense Reimbursement System");
		});
		
	}
}
