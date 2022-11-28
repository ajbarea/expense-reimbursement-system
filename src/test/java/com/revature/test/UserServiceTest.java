package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.dao.UserDAOImpl;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

class UserServiceTest {
	
	private static UserDAOImpl userDAO;
	private static UserService uServ;
	private static User expected;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		userDAO = Mockito.mock(UserDAOImpl.class);
		uServ = new UserServiceImpl(userDAO);
		expected = new User(1, "ajbarea", "password1", "AJ", "Barea", "ajb@rev.com", 2);
	}

	@Test
	@DisplayName("1. Get User by ID - Normal Test") // annotation for custom test names
	void testGetUserById() {
		when(userDAO.getUserById(1)).thenReturn(expected);
		
		User actual = uServ.getUserById(1);
		
		assertEquals(expected, actual);
		
		verify(userDAO, times(1)).getUserById(1);
	}
	
	@Test
	@DisplayName("2. Get User by ID - No User ID")
	void testNoUserFoundById() {
		when(userDAO.getUserById(0)).thenReturn(null);
		
		User actual = uServ.getUserById(0);
		
		assertEquals(null, actual);
		
		verify(userDAO, times(1)).getUserById(0);
	}
}