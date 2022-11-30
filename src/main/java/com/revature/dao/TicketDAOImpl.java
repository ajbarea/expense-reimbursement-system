package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
			ps.setTimestamp(2, Timestamp.from(Instant.now())); // set submitted time stamp
			ps.setTimestamp(3, ticket.getResolved());
			ps.setString(4, ticket.getDescription());
			ps.setString(5, ticket.getReceipt());
			ps.setInt(6, ticket.getAuthor());
			ps.setInt(7, ticket.getResolver());
			ps.setInt(8, 1); // set status to pending
			ps.setInt(9, ticket.getType());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();

			logger.info("TicketDAOImpl - createTicket() - New Ticket ID: " + rs.getInt(1));

			return rs.getInt("REIMB_ID");
		} catch (SQLException e) {
			logger.error("TicketDAOImpl::createTicket() SQLException - Message: " + e.getMessage());
		}

		return 0;
	}

	@Override
	public List<Ticket> getTicketsByAuthorId(int AuthorId) {
		try {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_AUTHOR = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, AuthorId);

			ResultSet rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}

			List<Ticket> allTickets = new ArrayList<Ticket>();
			while (rs.next()) {
				Ticket ticket = new Ticket();
				ps.setDouble(1, ticket.getAmount());
				ps.setTimestamp(2, ticket.getSubmitted());
				ps.setTimestamp(3, ticket.getResolved());
				ps.setString(4, ticket.getDescription());
				ps.setString(5, ticket.getReceipt());
				ps.setInt(6, ticket.getAuthor());
				ps.setInt(7, ticket.getResolver());
				ps.setInt(8, ticket.getStatus());
				ps.setInt(9, ticket.getType());
				allTickets.add(ticket);
			}

			return allTickets;

		} catch (SQLException e) {
			logger.error("TicketDAOImpl::getTicketsByAuthorId() SQLException - Message: " + e.getMessage());
		}

		return null;
	}

	@Override
	public boolean updateTicket(Ticket ticket) {
		try {
			String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_AMOUNT = ?, REIMB_SUBMITTED = ?, REIMB_RESOLVED = ?, REIMB_DESCRIPTION = ?, REIMB_RECEIPT = ?, REIMB_AUTHOR = ?,REIMB_RESOLVER = ?, REIMB_STATUS_ID = ?, REIMB_TYPE_ID = ? WHERE REIMB_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, ticket.getAmount());
			ps.setTimestamp(2, ticket.getSubmitted());
			ps.setTimestamp(3, Timestamp.from(Instant.now())); // set resolved time stamp
			ps.setString(4, ticket.getDescription());
			ps.setString(5, ticket.getReceipt());
			ps.setInt(6, ticket.getAuthor());
			ps.setInt(7, ticket.getResolver());
			ps.setInt(8, ticket.getStatus());
			ps.setInt(9, ticket.getType());
			ps.setInt(10, ticket.getId());

			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			logger.error("TicketDAOImpl::updateTicket() SQLException - Message: " + e.getMessage());
		}

		return false;
	}

	@Override
	public Ticket getTicketById(int id) {
		try {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			Ticket t = new Ticket();

			while (rs.next()) {
				t.setId(rs.getInt(1));
				t.setAmount(rs.getDouble(2));
				t.setSubmitted(rs.getTimestamp(3));
				t.setResolved(rs.getTimestamp(4));
				t.setDescription(rs.getString(5));
				t.setReceipt(rs.getString(6));
				t.setAuthor(rs.getInt(7));
				t.setResolver(rs.getInt(8));
				t.setStatus(rs.getInt(9));
				t.setType(rs.getInt(10));
			}

			return t;

		} catch (SQLException e) {
			logger.error("TicketDAOImpl::getTicketById() SQLException - Message: " + e.getMessage());
		}

		return null;
	}

}
