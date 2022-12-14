package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//connect java app to postgreSQL database
public class JDBCConnectionUtil {
	public static Logger logger = LoggerFactory.getLogger(JDBCConnectionUtil.class);

	public static Connection getConnection() {
		// URL: JDBC:postgresql://[host]:[port]/[database_name(optional)]
		// URL: jdbc:postgresql://localhost:5433/
		Connection conn = null;

		try {
			// logger.info(String.format("Making a DB connection: [%s]",
			// System.getenv("DB_URL")));

			conn = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USERNAME"),
					System.getenv("DB_PASSWORD"));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return conn;
	}
}