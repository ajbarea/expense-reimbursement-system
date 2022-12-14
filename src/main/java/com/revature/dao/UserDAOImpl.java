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
		try {
			String sql = "INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES(?, ?, ?, ?, ?, ?)";

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

		} catch (SQLException e) {
			logger.error("UserDAOImpl::createUser() SQLException - Message: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public User getUserById(int id) {
		try {
			logger.error("UserDAOImpl::getUserById() Searching database for User ID: " + id + "...");
			String sql = "SELECT * FROM ERS_USERS WHERE ERS_USERS_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			User user = new User();

			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setRole(rs.getInt(7));
			}

			return user;

		} catch (SQLException e) {
			logger.error("UserDAOImpl::getUserById() SQLException - Message: " + e.getMessage());
		}

		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		try {
			String sql = "SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			User user = new User();

			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setRole(rs.getInt(7));
			}

			return user;

		} catch (SQLException e) {
			logger.error("UserDAOImpl::getUserByUsername() SQLException - Message: " + e.getMessage());
		}

		return null;
	}

	@Override
	public boolean updateUser(User user) {
		try {
			String sql = "UPDATE ERS_USERS SET ERS_USERNAME = ?, ERS_PASSWORD = ?, USER_FIRST_NAME = ?, USER_LAST_NAME = ?, USER_EMAIL = ?, USER_ROLE_ID = ? WHERE ERS_USERS_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole());
			ps.setInt(7, user.getId());

			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			logger.error("UserDAOImpl::updateUser() SQLException - Message: " + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean deleteUserById(int id) {
		try {
			String sql = "DELETE FROM ERS_USERS WHERE ERS_USERS_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			if (ps.executeUpdate() > 0)
				return true;

		} catch (Exception e) {
			logger.error("UserDAOImpl::deleteUserById() exception - Message: " + e.getMessage());
		}

		return true;
	}
}
