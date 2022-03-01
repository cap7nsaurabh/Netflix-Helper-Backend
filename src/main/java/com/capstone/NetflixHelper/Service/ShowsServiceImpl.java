package com.capstone.NetflixHelper.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.ShowNotFoundException;
import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Repository.ShowsRepository;



@Service
public class ShowsServiceImpl implements ShowsService
{
	@Autowired
	ShowsRepository showsRepository;
	
	@Override
	public List<Shows> getAllShows()
	{
		return showsRepository.findAll();
	}
	
	
	@Override
	public Shows findShowById(String id)
	{
		return showsRepository.findById(id).orElse(null);
	}
	
	
	@Override
	public Shows saveShow(Shows show) throws ShowAlreadyExistsException
	{
    		Shows checkShow = findShowById(show.getId());
    		if (checkShow == null ) 
    		{
    			showsRepository.save(show);
			}
    		else 
    		{
    			throw new ShowAlreadyExistsException("Show already exists");	
    		}
    		
		return show;
	}
	
	public Shows updateShow(Shows show) throws ShowNotFoundException
	{
		Shows checkshow = findShowById(show.getId());
		if(checkshow == null)
		{
			throw new ShowNotFoundException("Show does not exist");
		}
		else
		{
			showsRepository.save(show);
		}
		
		return show;
	}

}
