package com.capstone.NetflixHelper.Service;

import java.util.List;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;

public interface FavouriteService 
{
	
	public List<Shows> getAllFav(String userid) throws UserNotFoundException;
	
	public Shows addShowToFav(Shows show,String userId) throws ShowAlreadyExistsException, UserNotFoundException, ShowNotFoundException;
	
	public Shows removeShowFromFav(String id, String userId) throws UserNotFoundException, ShowNotFoundException;

}
