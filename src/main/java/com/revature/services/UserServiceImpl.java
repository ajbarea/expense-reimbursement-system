package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;

public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static UserDAO userDAO;

	public UserServiceImpl() {
		userDAO = new UserDAOImpl();
	}

	public UserServiceImpl(UserDAOImpl ud) {
		UserServiceImpl.userDAO = ud;
	}

	@Override
	public boolean registerUser(User user) {
		logger.info("UserService::registerUser() called. Registering new User...");

		int id = userDAO.createUser(user);

		if (id != 0)
			logger.info("User was registered successfully. New User ID: " + id);

		return id != 0 ? true : false;
	}

	@Override
	public boolean login(String username, String password) {
		logger.info("UserService::login() called. Trying to find username " + username + "...");

		User user = userDAO.getUserByUsername(username);

		if (user.getUsername() == null)
			return false;

		if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
			switch (user.getRole()) {
			case 1:
				System.out.println("Welcome Employee");
				break;
			case 2:
				System.out.println("Welcome Admin");
				break;
			default:
			}
			return true;
		}
		return false;
	}

	@Override
	public User getUserById(int id) {
		logger.info("UserService::getUserById() called. Trying to find user ID# " + id + "...");

		return userDAO.getUserById(id);
	}

	@Override
	public boolean updateUser(User user) {
		logger.info("UserService::updateUser() called. Updating user ID# " + user.getId() + "...");

		return userDAO.updateUser(user);
	}

	@Override
	public boolean deleteUser(int id) {
		logger.info("UserService::deleteUserById() called. Deleting user ID# " + id + "...");

		return userDAO.deleteUserById(id);
	}

	@Override
	public User getUserByUsername(String ticket) {
		logger.info("UserService::getUserByUsername() called. Trying to find username " + ticket + "...");

		return userDAO.getUserByUsername(ticket);
	}
}