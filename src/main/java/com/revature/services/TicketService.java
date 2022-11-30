package com.revature.services;

import java.util.List;

import com.revature.models.Ticket;

public interface TicketService {

	public boolean createTicket(Ticket ticket);

	public boolean updateTicket(Ticket ticket);

	public Ticket getTicketById(int id);

	public List<Ticket> getTicketsByAuthorId(int id);

}
