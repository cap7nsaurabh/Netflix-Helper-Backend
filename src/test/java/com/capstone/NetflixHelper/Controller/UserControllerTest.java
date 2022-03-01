package com.capstone.NetflixHelper.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.NetflixHelper.Exception.UserAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.UserIdAndPasswordMismatchException;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Service.FavouriteServiceImpl;
import com.capstone.NetflixHelper.Service.ShowsServiceImpl;
import com.capstone.NetflixHelper.Service.UserServiceImpl;
import com.capstone.NetflixHelper.Service.WatchLaterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServiceImpl userServiceImpl;
	
	@MockBean
	private FavouriteServiceImpl favouriteServiceImpl;
	
	@MockBean
	private ShowsServiceImpl showsServiceImpl;
	
	@MockBean
	private WatchLaterServiceImpl watchLaterServiceImpl;
	
	
	private User user;

	@BeforeEach
	public void setUp() throws Exception 
	{
		user = new User("U101","Demo","User","DemoPassword","9999999999");
	}

	@AfterEach
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testRegisterUserSuccess() throws Exception
	{	
		when(userServiceImpl.saveUser(any())).thenReturn(user);
		mockMvc.perform(
				post("/user/register",user)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(user))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isCreated());
	}
	
	@Test
	public void testRegisterUserFailure() throws Exception  
	{		
		when(userServiceImpl.saveUser(any())).thenThrow(UserAlreadyExistsException.class);
		mockMvc.perform(
				post("/user/register",user)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(user))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isConflict());
	}
	
	@Test
	public void testLoginUserSuccess() throws Exception
	{
		when(userServiceImpl.findByEmailAndPassword(any(), any())).thenReturn(user);
		mockMvc.perform(
				post("/user/login",user)
//				.param("userId", "U101")
//				.param("userPassword","DemoPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(user))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isOk());
	}
	
	
	@Test
	public void testLoginUserFailure() throws Exception
	{
		when(userServiceImpl.findByEmailAndPassword(any(), any())).thenThrow(UserIdAndPasswordMismatchException.class);
		mockMvc.perform(
				post("/user/login", user)
//				.param("userId", "U101")
//				.param("userPassword","WrongPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(user))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isUnauthorized());
	}
	
	
	private static String jsonToString(final Object object) 
	{
		String result;

		try 
		{
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(object);
			result = jsonContent;
		} 
		catch (Exception exception) 
		{
			result = "error in Json processing";
		}
		
		return result;
	}


}
