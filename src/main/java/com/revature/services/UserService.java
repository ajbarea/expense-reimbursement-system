package com.revature.services;

import com.revature.models.User;

// Business logic methods for Java application - Passes information from DAO layer to Controller layer
public interface UserService {

	public boolean registerUser(User user);

	public boolean login(String username, String password);

	public User getUserById(int id);

	public boolean updateUser(User user);

	public boolean deleteUser(int id);
}