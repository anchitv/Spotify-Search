package com.spotify.search.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Session {
	@Id
	private String ClientId;
	private String Token;
	@Temporal(TemporalType.TIMESTAMP)
	private Date Expiry;
	
	@Override
	public String toString() {
		return "Session [ClientId=" + ClientId + ", Token=" + Token + ", Expiry=" + Expiry + "]";
	}
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public Date getExpiry() {
		return Expiry;
	}
	public void setExpiry(Date expiry) {
		Expiry = expiry;
	}

}
