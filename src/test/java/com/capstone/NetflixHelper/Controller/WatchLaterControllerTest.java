package com.capstone.NetflixHelper.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Service.FavouriteServiceImpl;
import com.capstone.NetflixHelper.Service.ShowsServiceImpl;
import com.capstone.NetflixHelper.Service.UserServiceImpl;
import com.capstone.NetflixHelper.Service.WatchLaterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest
class WatchLaterControllerTest 
{
	
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
	private Shows show;

	private List<Shows> wlShows;
	private List<User> users;

	@BeforeEach
	void setUp() throws Exception 
	{
		users = new ArrayList<User>();
		users.add(user);
		show = new Shows("S101","Demo Show","Show for testing demo","Movie",null,null,users);
		wlShows = new ArrayList<Shows>();
		wlShows.add(show);
		user = new User("U101","Demo","User","DemoPassword","9999999999",null,null,wlShows) ;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetAllWatchLaterSuccess() throws Exception
	{	
		when(watchLaterServiceImpl.getAllWatchLater(any())).thenReturn(wlShows);
		mockMvc.perform(
				get("/watchLater/U101")
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllWatchLaterFailure() throws Exception
	{	
		when(watchLaterServiceImpl.getAllWatchLater(any())).thenThrow(UserNotFoundException.class);
		mockMvc.perform(
				get("/watchLater/U101")
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isConflict());
	}
	
	@Test
	public void testAddShowToWatchLaterSuccess() throws Exception
	{
		when(watchLaterServiceImpl.addShowToWatchLater(show, "U101")).thenReturn(show);
		mockMvc.perform(
				post("/watchLater/U101", show)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(show))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isCreated());
	}
	
	@Test
	public void testAddShowToWatchLaterFailure() throws Exception
	{
		when(watchLaterServiceImpl.addShowToWatchLater(show, "U101")).thenThrow(ShowAlreadyExistsException.class);
		mockMvc.perform(
				post("/watchLater/U101",show)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(show))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isCreated());
	}
	
	@Test
	public void testRemoveShowFromFavSuccess() throws Exception
	{
		when(watchLaterServiceImpl.removeShowFromWatchLater("S101", "U101")).thenReturn(show);
		mockMvc.perform(
				delete("/watchLater/U101/S101", show)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(show))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveShowFromFavFailure() throws Exception
	{
		when(watchLaterServiceImpl.addShowToWatchLater(show, "U101")).thenThrow(ShowNotFoundException.class);
		mockMvc.perform(
				post("/watchLater/U101",show)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(show))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isCreated());
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
