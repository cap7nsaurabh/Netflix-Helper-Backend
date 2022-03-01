package com.capstone.NetflixHelper.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Model.User;

@Service
public class WatchLaterServiceImpl implements WatchLaterService
{
	@Autowired
	ShowsServiceImpl showsServiceImpl;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Override
	public List<Shows> getAllWatchLater(String userid) throws UserNotFoundException 
	{
		User user = userServiceImpl.findUserById(userid);
		if(user == null)
		{
			throw new UserNotFoundException("User does not exist");
		}
		return user.getUserWatchLater();
	}

	@Override
	public Shows addShowToWatchLater(Shows show, String userId)
			throws ShowAlreadyExistsException, UserNotFoundException, ShowNotFoundException 
	{
		User user = userServiceImpl.findUserById(userId);
		
		List<Shows> watchLaterShows = getAllWatchLater(userId);
		List<User> users = new ArrayList<User>();
		
		if(showsServiceImpl.findShowById(show.getId()) == null)
		{
			users.add(user);
			show.setFavourites(users);
			showsServiceImpl.saveShow(show);
			
			watchLaterShows.add(show);
			user.setUserWatchLater(watchLaterShows);
			userServiceImpl.updateUser(user);
			
			return show;
		}
		else
		{
			Shows checkShow = showsServiceImpl.findShowById(show.getId());
			watchLaterShows = user.getUserWatchLater();
			if(watchLaterShows.contains(checkShow))
			{
				throw new ShowAlreadyExistsException("Show already exists in Watch Later.");
			}
			watchLaterShows.add(checkShow);
			user.setUserWatchLater(watchLaterShows);
			userServiceImpl.updateUser(user);
			users = show.getWatchLater();
			if(users == null)
			{
				users = new ArrayList<User>();
			}
			users.add(user);
			checkShow.setWatchLater(users);
			showsServiceImpl.updateShow(checkShow);
			
			return checkShow;
			
		}
	}

	@Override
	public Shows removeShowFromWatchLater(String id, String userId)
			throws UserNotFoundException, ShowNotFoundException 
	{
		User user = userServiceImpl.findUserById(userId);
		
		List<Shows> watchLaterShows = getAllWatchLater(userId);
		
		Shows checkShow = showsServiceImpl.findShowById(id);
		
		List<User> users = checkShow.getWatchLater();
		
		if(!watchLaterShows.contains(checkShow))
		{
			throw new ShowNotFoundException("Show does not exist in Watch Later.");
		}
		
		watchLaterShows.remove(checkShow);
		user.setUserWatchLater(watchLaterShows);
		
		userServiceImpl.updateUser(user);
		
		users.remove(user);
		checkShow.setWatchLater(users);
		
		showsServiceImpl.updateShow(checkShow);
		
		return checkShow;
	}

}
