package com.revature.dao;

import com.revature.models.Ticket;

public interface TicketDAO {

	int createTicket(Ticket ticket);

	Ticket getTicketsByAuthorId(int AuthorId);

	boolean updateTicket(Ticket ticket);

}