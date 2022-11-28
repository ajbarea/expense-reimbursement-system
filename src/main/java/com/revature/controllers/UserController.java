package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	private static UserService uServ = new UserServiceImpl();
	
	public static Handler register = ctx -> {
		logger.info("A new User is making a registration request...");
		
		String body = ctx.body(); // .body() gets info from http request
		
		ObjectMapper om = new ObjectMapper(); // converts body into User object
		
		om.registerModule(new JavaTimeModule()); // used for time / date data types
		
		User target = om.readValue(body, User.class);
		
		logger.info("New " + target);
		
		boolean created = uServ.registerUser(target);
		
		if(created) {
			ctx.html("New User was created successfully.");
			ctx.status(HttpStatus.CREATED);
		}
		else {
			ctx.html("ERROR: User was not created. Please try again.");
			ctx.status(HttpStatus.NO_CONTENT);
		}
	};
	
	public static Handler getUserById = ctx -> {
		logger.info("A database search request has been recieved...");
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		User target = uServ.getUserById(id);
		
		if(target != null && target.getUsername() != null) {
			logger.info("User successfully retrieved from database.");
			ctx.json(target);
		}
		else {
			ctx.html("ERROR: Could not find User ID " + id + " in the database. Please try again.");
			ctx.status(HttpStatus.NOT_FOUND);
		}
	};
}