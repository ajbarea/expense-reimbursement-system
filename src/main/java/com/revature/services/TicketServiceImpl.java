package com.revature.services;

import java.util.List;

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
		logger.info("UserService::updateTicket() called. Updating ticket # " + ticket.getId() + "...");
		return ticketDAO.updateTicket(ticket);
	}

	@Override
	public List<Ticket> getTicketsByAuthorId(int id) {
		logger.info("TicketServiceImpl::getTicketsByAuthorId() called. Trying to find Author ID# " + id + "...");
		return ticketDAO.getTicketsByAuthorId(id);
	}

	@Override
	public Ticket getTicketById(int id) {
		logger.info("TicketServiceImpl::getTicketById() called. Trying to find ticket ID# " + id + "...");
		return ticketDAO.getTicketById(id);
	}

	@Override
	public List<Ticket> getAllT() {
		logger.info("TicketServiceImpl::getAllT() called. Trying to return all tickets");
		return ticketDAO.getAllT();
	}

}
