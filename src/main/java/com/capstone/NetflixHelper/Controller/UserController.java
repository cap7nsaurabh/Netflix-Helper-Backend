package com.capstone.NetflixHelper.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.NetflixHelper.Exception.UserAlreadyExistsException;
import com.capstone.NetflixHelper.Exception.UserIdAndPasswordMismatchException;
import com.capstone.NetflixHelper.Exception.UserNotFoundException;
import com.capstone.NetflixHelper.Model.User;
import com.capstone.NetflixHelper.Service.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
public class UserController 
{
	
	private ResponseEntity<?> responseEntity;

    private UserServiceImpl userServiceImpl;

	public UserController() 
	{
		super();
	}

	@Autowired
	public UserController(UserServiceImpl userServiceImpl) 
	{
		super();
		this.userServiceImpl = userServiceImpl;
	}

	@PostMapping("/register")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> registerUser(@RequestBody User user) 
	{
		try 
		{
			userServiceImpl.saveUser(user);
			responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
		} 
		catch (UserAlreadyExistsException userAlreadyExistsException) 
		{
			responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> loginUser(@RequestBody User user) throws ServletException 
	{	
		Map<String, String> map =new HashMap<String,String>();
		String userId = user.getUserId();
		String userPassword = user.getUserPassword();
		
		String jwtToken = "";
		try 
		{
			userServiceImpl.findByEmailAndPassword(userId, userPassword);
			
			jwtToken = 	getToken(userId, userPassword);
			map.put("message", "User Successfully Logged In");
			map.put("token", jwtToken);
			map.put("userid",userId);
			
			responseEntity = new ResponseEntity<>(map, HttpStatus.OK);	
			
		} 
		catch (UserNotFoundException userNotFoundException) 
		{
			responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		catch(UserIdAndPasswordMismatchException userIdAndPasswordMismatchException)
		{
			responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return responseEntity;
	}

	public String getToken(String username, String password) throws ServletException 
	{
		if(username == null || password == null) 
		{
			throw new ServletException("Please provide proper username and password");
		}
				
	   String jwtToken = 	Jwts.builder()
		    .setSubject(username)
		    .setIssuedAt(new Date())
		    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10
		    		))
		    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		
		return jwtToken;
	}

}
