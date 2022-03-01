package com.capstone.NetflixHelper.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstone.NetflixHelper.Exception.UserAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.UserIdAndPasswordMismatchException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Repository.UserRepository;


@SpringBootTest
class UserServiceImplTest {

	@MockBean
	private UserRepository userRepository;
	
	private UserServiceImpl userServiceImpl;
	
	User user;
	
	@Autowired
	public UserServiceImplTest(UserServiceImpl userServiceImpl) 
	{
		this.userServiceImpl = userServiceImpl;
	}
	
	@BeforeEach
	void setUp() throws Exception 
	{
		user = new User("U101","Demo","User","DemoPassword","9999999999");		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}

	@Test
	void testGetAllUsersSuccess() 
	{
		when(userRepository.findAll()).thenReturn(Stream.of(user)
				.collect(Collectors.toList()));
		assertEquals(1, userServiceImpl.getAllUsers().size());
	}
	
	@Test
	void testGetAllUsersFailure() 
	{
		when(userRepository.findAll()).thenReturn(null);
		assertEquals(null, userServiceImpl.getAllUsers());
	}
	
	@Test
	void testFindUserByIdSuccess() throws UserAlreadyExistsException
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.findUserById("U101"));
	}
	
	@Test
	void testFindUserByIdFailure() throws UserAlreadyExistsException
	{
		when(userRepository.getOne("U101")).thenReturn(null);
		assertEquals(null, userServiceImpl.findUserById("U101"));
	}
	
	@Test
	void testfindByEmailAndPasswordSuccess() throws UserNotFoundException, UserIdAndPasswordMismatchException
	{
		when(userRepository.findByUserIdAndUserPassword("U101", "DemoPassword")).thenReturn(user);
		assertEquals(user, userServiceImpl.findByEmailAndPassword("U101", "DemoPassword"));
	}
	
	@Test
	void testfindByEmailAndPasswordFailure() throws UserIdAndPasswordMismatchException
	{
		when(userRepository.findByUserIdAndUserPassword("U101", "DemoPassword")).thenReturn(null);
		User testUser;
		try
		{
			testUser = userServiceImpl.findByEmailAndPassword("U101", "DemoPassword");
		}
		catch(UserNotFoundException userNotFoundException)
		{
			testUser = null;
		}
		assertEquals(null, testUser);
	}
	
	@Test
	void testSaveUserSuccess() throws UserAlreadyExistsException 
	{
		when(userRepository.getOne("U101")).thenReturn(null);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userServiceImpl.saveUser(user));
	}
	
	@Test
	void testSaveUserFailure() throws UserAlreadyExistsException 
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(null);
		User testUser;
		try
		{
			testUser = userServiceImpl.saveUser(user);
		}
		catch(UserAlreadyExistsException userAlreadyExistsException)
		{
			testUser = null;
		}
		assertEquals(null, testUser);
	}
	
	@Test
	void testUpdateUserSuccess() throws UserNotFoundException
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		user.setUserMobile("8888888888");
		when(userRepository.save(user)).thenReturn(user);
		User testUser = userServiceImpl.updateUser(user);
		assertEquals("8888888888", testUser.getUserMobile());
	}
	
	@Test
	void testUpdateUserFailure() throws UserNotFoundException
	{
		when(userRepository.getOne("U101")).thenReturn(null);
		user.setUserMobile("8888888888");
		when(userRepository.save(user)).thenReturn(null);
		User testUser;
		try
		{
			testUser = userServiceImpl.updateUser(user);
		}
		catch(UserNotFoundException userNotFoundException)
		{
			testUser = null;
		}
		assertEquals(null, testUser);
	}
	
	@Test
	void testRemoveUserSuccess() throws UserNotFoundException
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		assertEquals("User: U101 Deleted", userServiceImpl.removeUser("U101"));
	}
	
	@Test
	void testRemoveUserfailure() throws UserNotFoundException
	{
		when(userRepository.getOne("U101")).thenReturn(null);
		String testUserMessage;
		try
		{
			testUserMessage = userServiceImpl.removeUser("U101");
		}
		catch(UserNotFoundException userNotFoundException)
		{
			testUserMessage = null;
		}
		assertEquals(null, testUserMessage);
	}

}
