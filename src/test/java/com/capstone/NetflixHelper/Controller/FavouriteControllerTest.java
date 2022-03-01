package com.capstone.NetflixHelper.Controller;

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
class FavouriteControllerTest 
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

	private List<Shows> favShows;
	private List<User> users;

	@BeforeEach
	void setUp() throws Exception 
	{
		users = new ArrayList<User>();
		users.add(user);
		show = new Shows("S101","Demo Show","Show for testing demo","Movie",null,users,null);
		favShows = new ArrayList<Shows>();
		favShows.add(show);
		user = new User("U101","Demo","User","DemoPassword","9999999999",null,favShows,null) ;
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testGetAllFavSuccess() throws Exception
	{	
		when(favouriteServiceImpl.getAllFav(any())).thenReturn(favShows);
		mockMvc.perform(
				get("/fav/U101")
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllFavFailure() throws Exception
	{	
		when(favouriteServiceImpl.getAllFav(any())).thenThrow(UserNotFoundException.class);
		mockMvc.perform(
				get("/fav/U101")
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isConflict());
	}
	
	@Test
	public void testAddShowToFavSuccess() throws Exception
	{
		when(favouriteServiceImpl.addShowToFav(show, "U101")).thenReturn(show);
		mockMvc.perform(
				post("/fav/U101", show)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonToString(show))
				.accept(MediaType.APPLICATION_JSON)
				)
					.andExpect(status().isCreated());
	}
	
	@Test
	public void testAddShowToFavFailure() throws Exception
	{
		when(favouriteServiceImpl.addShowToFav(show, "U101")).thenThrow(ShowAlreadyExistsException.class);
		mockMvc.perform(
				post("/fav/U101",show)
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
		when(favouriteServiceImpl.removeShowFromFav("S101", "U101")).thenReturn(show);
		mockMvc.perform(
				delete("/fav/U101/S101", show)
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
		when(favouriteServiceImpl.addShowToFav(show, "U101")).thenThrow(ShowNotFoundException.class);
		mockMvc.perform(
				post("/fav/U101",show)
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
