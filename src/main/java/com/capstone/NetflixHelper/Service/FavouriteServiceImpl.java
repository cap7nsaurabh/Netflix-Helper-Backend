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
public class FavouriteServiceImpl implements FavouriteService
{
	
	@Autowired
	ShowsServiceImpl showsServiceImpl;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	public List<Shows> getAllFav(String userid) throws UserNotFoundException
	{
		User user = userServiceImpl.findUserById(userid);
		if(user == null)
		{
			throw new UserNotFoundException("User does not exist");
		}
		return user.getUserFavourites();
	}
	
	public Shows addShowToFav(Shows show,String userId) throws ShowAlreadyExistsException, UserNotFoundException, ShowNotFoundException
	{
		
		User user = userServiceImpl.findUserById(userId);
		
		List<Shows> favShows = getAllFav(userId);
		List<User> users = new ArrayList<User>();
		
		if(showsServiceImpl.findShowById(show.getId()) == null)
		{
			users.add(user);
			show.setFavourites(users);
			showsServiceImpl.saveShow(show);
			
			favShows.add(show);
			user.setUserFavourites(favShows);
			userServiceImpl.updateUser(user);
			
			return show;
		}
		else
		{
			Shows checkShow = showsServiceImpl.findShowById(show.getId());
			favShows = user.getUserFavourites();
			if(favShows.contains(checkShow))
			{
				throw new ShowAlreadyExistsException("Show already exists in favourites.");
			}
			favShows.add(checkShow);
			user.setUserFavourites(favShows);
			userServiceImpl.updateUser(user);
			users = show.getFavourites();
			if(users == null)
			{
				users = new ArrayList<User>();
			}
			users.add(user);
			checkShow.setFavourites(users);
			showsServiceImpl.updateShow(checkShow);
			
			return checkShow;
			
		}
	}
	
	public Shows removeShowFromFav(String id, String userId) throws UserNotFoundException, ShowNotFoundException
	{
		User user = userServiceImpl.findUserById(userId);
		
		List<Shows> favShows = getAllFav(userId);
		
		Shows checkShow = showsServiceImpl.findShowById(id);
		
		List<User> users = checkShow.getFavourites();
		
		if(!favShows.contains(checkShow))
		{
			throw new ShowNotFoundException("Show does not exist in favourites.");
		}
		
		favShows.remove(checkShow);
		user.setUserFavourites(favShows);
		
		userServiceImpl.updateUser(user);
		
		users.remove(user);
		checkShow.setFavourites(users);
		
		showsServiceImpl.updateShow(checkShow);
		
		return checkShow;
	}


}
