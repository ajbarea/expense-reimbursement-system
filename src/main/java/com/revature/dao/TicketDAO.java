package com.revature.dao;

import java.util.List;

import com.revature.models.Receipt;
import com.revature.models.Ticket;

public interface TicketDAO {

	int createTicket(Ticket ticket);

	Ticket getTicketById(int id);

	List<Ticket> getTicketsByAuthorId(int AuthorId);

	boolean updateTicket(Ticket ticket);

	// BYTEA TEST
	int createReceipt(Receipt r);

}