package com.capstone.NetflixHelper.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Shows 
{
	
	@Id
	@Column(length = 30)
	private String id;

	private String title;
	private String description;
	private String type;
	private String image;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			  name = "UserFavourites", 
			  joinColumns = @JoinColumn(name = "userId"), 
			  inverseJoinColumns = @JoinColumn(name = "showId"))
	private List<User> favourites;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			  name = "UserWatchLater", 
			  joinColumns = @JoinColumn(name = "userId"), 
			  inverseJoinColumns = @JoinColumn(name = "showId"))
	private List<User> watchLater;

	public Shows() 
	{
		super();
	}
	
	public Shows(String id, String title, String description, String type, String image, List<User> favourites,List<User> watchLater) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.type = type;
		this.image = image;
		this.favourites = favourites;
		this.watchLater = watchLater;
	}


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<User> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<User> favourites) {
		this.favourites = favourites;
	}

	public List<User> getWatchLater() {
		return watchLater;
	}

	public void setWatchLater(List<User> watchLater) {
		this.watchLater = watchLater;
	}
	
	

}
