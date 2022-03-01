package com.capstone.NetflixHelper.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.NetflixHelper.Model.Shows;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, String>
{

}
