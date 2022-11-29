package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Ticket;
import com.revature.util.JDBCConnectionUtil;

public class TicketDAOImpl implements TicketDAO {

	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	Connection conn;

	// Establish JBDC connection
	public TicketDAOImpl() {
		conn = JDBCConnectionUtil.getConnection();
	}

	@Override
	public int createTicket(Ticket ticket) {
		try {
			String sql = "INSERT INTO ERS_REIMBURSEMENT (REIMB_AMOUNT, REIMB_SUBMITTED, REIMB_RESOLVED, REIMB_DESCRIPTION, REIMB_RECEIPT, REIMB_AUTHOR, REIMB_RESOLVER, REIMB_STATUS_ID, REIMB_TYPE_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, ticket.getAmount());
			ps.setTimestamp(2, ticket.getSubmitted());
			ps.setTimestamp(3, ticket.getResolved());
			ps.setString(4, ticket.getDescription());
			ps.setString(5, ticket.getReceipt());
			ps.setInt(6, ticket.getAuthor());
			ps.setInt(7, ticket.getResolver());
			ps.setInt(8, ticket.getStatus());
			ps.setInt(9, ticket.getType());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next(); // move ResultSet cursor to first record

			logger.info("TicketDAOImpl - createTicket() - New Ticket ID: " + rs.getInt(1));

			return rs.getInt("REIMB_ID");
		} catch (SQLException e) {
			logger.error("TicketDAOImpl::createTicket() SQLException - Message: " + e.getMessage());
		}

		return 0;
	}

	@Override
	public Ticket getTicketsByAuthorId(int AuthorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return false;
	}

}
