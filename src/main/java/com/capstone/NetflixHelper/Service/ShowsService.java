package com.capstone.NetflixHelper.Service;

import java.util.List;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Model.Shows;

public interface ShowsService 
{
	public List<Shows> getAllShows();
	
	public Shows findShowById(String id);
	
	public Shows saveShow(Shows show) throws ShowAlreadyExistsException;

}
