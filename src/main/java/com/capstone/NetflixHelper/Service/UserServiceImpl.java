package com.capstone.NetflixHelper.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.NetflixHelper.Exception.UserAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.UserIdAndPasswordMismatchException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	
	@Override
	public User findUserById(String id)
	{
		 return userRepository.findById(id).orElse(null);
	}
	
	
	@Override
	public User findByEmailAndPassword(String email, String password) throws UserNotFoundException, UserIdAndPasswordMismatchException
	{
		User user = userRepository.findByUserIdAndUserPassword(email, password);
		if (user == null) 
		{
			user = findUserById(email);
			if(user == null)
			{
				throw new UserNotFoundException("User does not exist");
			}
			else
			{
				throw new UserIdAndPasswordMismatchException("User Id or password is wrong.");
			}
			
		}
		return user;
	 }
	
	 
	@Override
    public User saveUser(User user) throws UserAlreadyExistsException  
	{	
    	User checkUser = findUserById(user.getUserId());
    	if ( checkUser ==  null ) 
    	{
    		 userRepository.save(user);
		}
    	else 
    	{
    		throw new UserAlreadyExistsException("User Already Registered");	
    	}
		return  user;	
    }
	
	
	@Override
	public User updateUser(User user) throws UserNotFoundException
	{
		User checkUser = findUserById(user.getUserId());
		if(checkUser == null)
		{
			throw new UserNotFoundException("User does not exist");
		}
		else
		{
			checkUser = userRepository.save(user);
		}
		return checkUser;
	}
	
	
	@Override
	public String removeUser(String userId) throws UserNotFoundException
	{
		User user = findUserById(userId);
		if(user == null)
		{
			throw new UserNotFoundException("User does not exist");
		}
		else
		{
			userRepository.delete(user);
		}
		
		return "User: "+ userId +" Deleted";
	}

}
