package com.spotify.search.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users 
{
	@Id
	private String ClientId;
	private String ClientSecret;
	private String UserName;
	private String Password;
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getClientSecret() {
		return ClientSecret;
	}
	public void setClientSecret(String clientSecret) {
		ClientSecret = clientSecret;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "Users [ClientId=" + ClientId + ", ClientSecret=" + ClientSecret + ", UserName=" + UserName
				+ ", Password=" + Password + "]";
	}

}
