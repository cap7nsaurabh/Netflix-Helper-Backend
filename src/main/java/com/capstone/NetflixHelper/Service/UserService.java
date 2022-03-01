package com.capstone.NetflixHelper.Service;

import java.util.List;

import com.capstone.NetflixHelper.Exception.UserAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.UserIdAndPasswordMismatchException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.User;

public interface UserService 
{
	
	public List<User> getAllUsers();
	
	public User findUserById(String id);
	
	public User findByEmailAndPassword(String email, String password) throws UserNotFoundException, UserIdAndPasswordMismatchException;
	
	public User saveUser(User user) throws UserAlreadyExistsException;
	
	public User updateUser(User user) throws UserNotFoundException;

	public String removeUser(String userId) throws UserNotFoundException;

}
