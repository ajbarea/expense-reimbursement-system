package com.revature.dao;

import com.revature.models.User;

// CRUD (Create, Read, Update, Delete) methods for ERS Users
public interface UserDAO {

	int createUser(User user);

	User getUserById(int id);

	User getUserByUsername(String username);

	boolean updateUser(User user);

	boolean deleteUserById(int id);
}