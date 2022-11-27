package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

public class UserDAOImpl implements UserDAO {
	
	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	Connection conn;
	
	// Establish JBDC connection
	public UserDAOImpl() {
		conn = JDBCConnectionUtil.getConnection();
	}
	
	@Override
	public int createUser(User user) {
		try {          // INSERT INTO "ERS_USERS" ("ERS_USERNAME", "ERS_PASSWORD", "USER_FIRST_NAME", "USER_LAST_NAME", "USER_EMAIL", "USER_ROLE_ID") VALUES ('ajbarea', 'password1', 'AJ', 'Barea', 'ajb@rev.com', 2);
			String sql = "INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES (?, ?, ?, ?, ?, ?)"; // '?' placeholder
			
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			rs.next(); // move ResultSet cursor to first record
			
			logger.info("UserDAOImpl - createUser() - New User ID: " + rs.getInt(1));
			
			return rs.getInt("ERS_USERS_ID");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}