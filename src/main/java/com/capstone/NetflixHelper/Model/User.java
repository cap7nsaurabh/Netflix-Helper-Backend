package com.capstone.NetflixHelper.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User 
{
	
	@Id
	@Column(length = 60)
	private String userId;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	private String userMobile;
	private String userProfileImage;
	
	@ManyToMany(mappedBy="favourites")
	private List<Shows> userFavourites;
	
	@ManyToMany(mappedBy = "watchLater" )
	private List<Shows> userWatchLater;
	
	public User() 
	{
		super();
	}
	
	
	public User(String userId, String userFirstName, String userLastName, String userPassword, String userMobile) 
	{
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
	}
	
	public User(String userId, String userFirstName, String userLastName, String userPassword, String userMobile,
			String userProfileImage) 
	{
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userProfileImage = userProfileImage;
	}
	
	
	public User(String userId, String userFirstName, String userLastName, String userPassword, String userMobile,
			String userProfileImage, List<Shows> userFavourites, List<Shows> userWatchLater) 
	{
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userProfileImage = userProfileImage;
		this.userFavourites = userFavourites;
		this.userWatchLater = userWatchLater;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserFirstName() {
		return userFirstName;
	}


	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}


	public String getUserLastName() {
		return userLastName;
	}


	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserMobile() {
		return userMobile;
	}


	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}


	public String getUserProfileImage() {
		return userProfileImage;
	}


	public void setUserProfileImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}


	public List<Shows> getUserFavourites() {
		return userFavourites;
	}


	public void setUserFavourites(List<Shows> userFavourites) {
		this.userFavourites = userFavourites;
	}


	public List<Shows> getUserWatchLater() {
		return userWatchLater;
	}


	public void setUserWatchLater(List<Shows> userWatchLater) {
		this.userWatchLater = userWatchLater;
	}
	
	

}
