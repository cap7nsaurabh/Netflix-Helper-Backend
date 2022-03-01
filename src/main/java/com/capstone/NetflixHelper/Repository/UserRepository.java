package com.capstone.NetflixHelper.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.NetflixHelper.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	User findByUserIdAndUserPassword(String userId , String password);
}
