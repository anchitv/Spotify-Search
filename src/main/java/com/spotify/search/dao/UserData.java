package com.spotify.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spotify.search.model.Users;

public interface UserData extends JpaRepository<Users, String>
{
	@Query("from Users where Client_Id=?1 and Password=?2")
	Users findByCredentials(String clientid, String password);
}
