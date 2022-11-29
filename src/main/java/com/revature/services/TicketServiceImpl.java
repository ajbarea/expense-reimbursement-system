package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.models.Ticket;

public class TicketServiceImpl implements TicketService {

	private static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

	private static TicketDAO ticketDAO;

	public TicketServiceImpl() {
		ticketDAO = new TicketDAOImpl();
	}

	public TicketServiceImpl(TicketDAOImpl td) {
		TicketServiceImpl.ticketDAO = td;
	}

	@Override
	public boolean createTicket(Ticket ticket) {
		logger.info("TicketServiceImpl::createTicket() called. Creating new reimbursement ticket...");
		int id = ticketDAO.createTicket(ticket);
		logger.info("Received from DAO. New ticket ID: " + id);
		return (id != 0) ? true : false;
	}

	@Override
	public boolean updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ticket getTicketsByAuthorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
