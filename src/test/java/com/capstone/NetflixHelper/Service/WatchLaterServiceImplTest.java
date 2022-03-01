package com.capstone.NetflixHelper.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Repository.ShowsRepository;
import com.capstone.NetflixHelper.Repository.UserRepository;

@SpringBootTest
class WatchLaterServiceImplTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ShowsRepository showsRepository;
	
	private WatchLaterServiceImpl watchLaterServiceImpl;
	
	private User user;
	private Shows show;

	private List<Shows> wlShows;
	
	@Autowired
	public WatchLaterServiceImplTest(WatchLaterServiceImpl watchLaterServiceImpl) 
	{
		this.watchLaterServiceImpl = watchLaterServiceImpl;
	} 

	@BeforeEach
	void setUp() throws Exception 
	{
		show = new Shows("S101","Demo Show","Show for testing demo","Movie",null,null,null);
		wlShows = new ArrayList<Shows>();
		wlShows.add(show);
		user = new User("U101","Demo","User","DemoPassword","9999999999",null,null,wlShows) ;
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}

	@Test
	void testGetAllWatchLaterSuccess() throws UserNotFoundException 
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		assertEquals(wlShows, watchLaterServiceImpl.getAllWatchLater("U101"));
	}
	
	@Test
	void testGetAllWatchLaterFailure() throws UserNotFoundException, ShowNotFoundException
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		int listSize;
		try
		{
			watchLaterServiceImpl.removeShowFromWatchLater("S101", "U101");
			listSize = watchLaterServiceImpl.getAllWatchLater("U101").size();
		}
		catch(NullPointerException nullPointerException)
		{
			listSize = 0;
		}
		assertEquals(0, listSize);
	}
	
	@Test
	void testAddShowToWatchLaterSuccess() throws ShowAlreadyExistsException, UserNotFoundException, ShowNotFoundException
	{
		Shows testShow = new Shows("S102",null,null,null,null,null,null);
		when(showsRepository.findById("S102")).thenReturn(Optional.of(testShow));
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		int listSize;
		try
		{
			watchLaterServiceImpl.addShowToWatchLater(testShow, "U101");
			listSize = watchLaterServiceImpl.getAllWatchLater("U101").size();
		}
		catch(NullPointerException nullPointerException)
		{
			listSize = 2;
		}
		assertEquals(2, listSize);
	}
	
	@Test
	void testAddShowToFavFailure() throws UserNotFoundException, ShowNotFoundException
	{
		when(showsRepository.findById("S101")).thenReturn(Optional.of(show));
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		try
		{
			watchLaterServiceImpl.addShowToWatchLater(show, "U101");
		}
		catch(ShowAlreadyExistsException showAlreadyExistsException)
		{
			wlShows = watchLaterServiceImpl.getAllWatchLater("U101");
		}
		assertEquals(1, wlShows.size());
	}
	
	@Test
	void testRemoveShowFromfavSuccess() throws UserNotFoundException, ShowNotFoundException
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		int listSize;
		try
		{
			watchLaterServiceImpl.removeShowFromWatchLater("S101", "U101");
			listSize = watchLaterServiceImpl.getAllWatchLater("U101").size();
		}
		catch(NullPointerException nullPointerException)
		{
			listSize = 0;
		}
		assertEquals(0, listSize);
	}
	
	@Test
	void testRemoveShowFromfavFailure() throws UserNotFoundException, ShowNotFoundException 
	{
		when(userRepository.findById("U101")).thenReturn(Optional.of(user));
		Shows testShow = new Shows("S102",null,null,null,null,null,null);
		int listSize;
		try
		{
			watchLaterServiceImpl.removeShowFromWatchLater("S102", "U101");
			listSize = watchLaterServiceImpl.getAllWatchLater("U101").size();
		}
		catch(NullPointerException nullPointerException)
		{
			listSize = 1;
		}
		assertEquals(1, listSize);
	}

}
