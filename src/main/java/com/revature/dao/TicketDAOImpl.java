package com.revature.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Receipt;
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
			logger.error("TicketDAOImpl::getTicketsByAuthorId() Searching database for Author ID: " + AuthorId + "...");

			List<Ticket> tickets = new ArrayList<Ticket>();

			String sqlQuery = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_AUTHOR = ?";

			PreparedStatement p = conn.prepareStatement(sqlQuery);
			p.setInt(1, AuthorId);

			ResultSet r = p.executeQuery();

			while (r.next()) {
				Ticket t = new Ticket();
				t.setId(r.getInt(1));
				t.setAmount(r.getDouble(2));
				t.setSubmitted(r.getTimestamp(3));
				t.setResolved(r.getTimestamp(4));
				t.setDescription(r.getString(5));
				t.setReceipt(r.getString(6));
				t.setAuthor(r.getInt(7));
				t.setResolver(r.getInt(8));
				t.setStatus(r.getInt(9));
				t.setType(r.getInt(10));
				tickets.add(t);
			}

			return tickets;

		} catch (SQLException e) {
			logger.error("TicketDAOImpl::getTicketsByAuthorId() SQLException - Message: " + e.getMessage());
		} finally {

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

	@Override
	public int createReceipt(Receipt r) {
		try {

			File file = new File("r.png");
			FileInputStream fis = new FileInputStream(file);

			String sql = "INSERT INTO Ticket (title, image) VALUES (?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, r.getTitle());
			pstmt.setBinaryStream(2, fis, file.length());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();

			logger.info("TicketDAOImpl - createReceipt() - New Ticket ID: " + rs.getInt(1));
			fis.close();
			return rs.getInt("REIMB_ID");

		} catch (SQLException | IOException e) {
			logger.error("TicketDAOImpl::createReceipt() SQLException - Message: " + e.getMessage());
		}

		return 0;
	}

	@Override
	public List<Ticket> getAllT() {
		try {
			List<Ticket> tickets = new ArrayList<Ticket>();

			String sqlQuery = "SELECT * FROM ERS_REIMBURSEMENT ORDER BY REIMB_AUTHOR";

			PreparedStatement p = conn.prepareStatement(sqlQuery);

			ResultSet r = p.executeQuery();

			while (r.next()) {
				Ticket t = new Ticket();
				t.setId(r.getInt(1));
				t.setAmount(r.getDouble(2));
				t.setSubmitted(r.getTimestamp(3));
				t.setResolved(r.getTimestamp(4));
				t.setDescription(r.getString(5));
				t.setReceipt(r.getString(6));
				t.setAuthor(r.getInt(7));
				t.setResolver(r.getInt(8));
				t.setStatus(r.getInt(9));
				t.setType(r.getInt(10));
				tickets.add(t);
			}

			return tickets;

		} catch (SQLException e) {
			logger.error("TicketDAOImpl::getAllT() SQLException - Message: " + e.getMessage());
		}

		return null;
	}

}
