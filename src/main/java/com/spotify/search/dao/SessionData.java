package com.spotify.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spotify.search.model.Session;

public interface SessionData extends JpaRepository<Session, String>
{
	@Query("from Session where Client_Id=?1 and expiry > CURRENT_TIMESTAMP")
	Session findValidToken(String clientid);
}

