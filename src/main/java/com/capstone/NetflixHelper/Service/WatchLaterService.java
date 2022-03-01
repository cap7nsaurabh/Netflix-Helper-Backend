package com.capstone.NetflixHelper.Service;

import java.util.List;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;

public interface WatchLaterService 
{
	
	public List<Shows> getAllWatchLater(String userid) throws UserNotFoundException;
	
	public Shows addShowToWatchLater(Shows show,String userId) throws ShowAlreadyExistsException, UserNotFoundException, ShowNotFoundException;
	
	public Shows removeShowFromWatchLater(String id, String userId) throws UserNotFoundException, ShowNotFoundException;
	
}
