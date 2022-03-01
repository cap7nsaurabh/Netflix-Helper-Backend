package com.capstone.NetflixHelper.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Service.WatchLaterServiceImpl;

@RestController
@RequestMapping("/watchLater")
public class WatchLaterController 
{
	
	@Autowired
	WatchLaterServiceImpl watchLaterServiceImpl;
	
	@GetMapping("/{userid}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> GetAllWatchLater(@PathVariable String userid)
	{
		ResponseEntity<?> responseEntity;
		try
		{
			List<Shows> watchLaterShows = watchLaterServiceImpl.getAllWatchLater(userid);
			responseEntity = new ResponseEntity<>(watchLaterShows,HttpStatus.OK);
		}
		catch(Exception exception)
		{
			responseEntity = new ResponseEntity<>(exception.getMessage() ,HttpStatus.CONFLICT);
		}
		
		return responseEntity;
	}
	
	@PostMapping("/{userId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> addShowToWatchLater(@RequestBody Shows show,@PathVariable String userId) 
	{
		ResponseEntity<?> responseEntity;

		try 
		{
			Shows checkShow = watchLaterServiceImpl.addShowToWatchLater(show, userId);
			responseEntity = new ResponseEntity<Shows>(checkShow, HttpStatus.CREATED);
		}  
		catch (Exception exception) 
		{
			responseEntity = new ResponseEntity<>(exception.getMessage() ,HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{userId}/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> removeShowFromFav(@PathVariable String userId,@PathVariable String id)
	{
		ResponseEntity<?> responseEntity;
		try 
		{
			Shows checkShow = watchLaterServiceImpl.removeShowFromWatchLater(id, userId);
			responseEntity = new ResponseEntity<Shows>(checkShow, HttpStatus.OK);
		}
		catch (Exception exception) 
		{
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);

		}
		return responseEntity;

	}

}
