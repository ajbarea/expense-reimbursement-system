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
		
		logger.info("New User: " + target);
		
		boolean created = uServ.registerUser(target);
		
		if(created) {
			ctx.html("New User was created successfully.");
			ctx.status(HttpStatus.CREATED);
		}
		else {
			ctx.html(">>> ERROR: User was not created. Please try again.");
			ctx.status(HttpStatus.NO_CONTENT);
		}
	};
}