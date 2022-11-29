package com.revature.services;

import com.revature.models.Ticket;

public interface TicketService {

	public boolean createTicket(Ticket ticket);

	public boolean updateTicket(Ticket ticket);

	public Ticket getTicketsByAuthorId(int id);

}
