package com.spotify.search.controller;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.search.model.Session;
import com.spotify.search.model.Users;
import com.spotify.search.dao.SessionData;
import com.spotify.search.dao.UserData;

@Controller
public class MainController 
{
	@Autowired
	UserData user_repo;
	
	@Autowired
	SessionData session_repo;
	
	@RequestMapping("/")
	public String main()
	{
		return "redirect:home";
	}
	
	@RequestMapping("/home")
	public String home()
	{
		return "home.jsp";
	}
	
	@RequestMapping("/search")
	public String search()
	{
		return "search.jsp";
	}
	
	@RequestMapping("/signup")
	public String signup()
	{
		return "signup.jsp";
	}
	
	@RequestMapping("/addUser")
	public String addUser(Users user)
	{
		user_repo.save(user);
		return "redirect:home";
	}
	
	private String authorise(String clientid, String clientsecret) throws URISyntaxException, JsonMappingException, JsonProcessingException 
	{
		RestTemplate restTemplate = new RestTemplate();
	    final String baseUrl = "https://accounts.spotify.com/api/token";
	    URI uri = new URI(baseUrl);
	     
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    	    
	    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	    map.add("client_id", clientid);
	    map.add("client_secret", clientsecret);
	    map.add("grant_type", "client_credentials");
	 
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	    ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
    
	    //Verify request succeed
	    assert response.getStatusCodeValue() == 200: "Spotify error.";
	    
	    String token = processResponse(response.getBody())
	    				.get("access_token")
	    				.textValue();

	    Session entity = new Session();
	    entity.setClientId(clientid);
	    entity.setToken(token);
	    Date date = new Date(response.getHeaders().getDate()+3600000);
	    entity.setExpiry(date);
	    session_repo.save(entity);
	    
	    return token;
	}
	
	private JsonNode processResponse(String response) throws JsonMappingException, JsonProcessingException
	{
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonResponse = mapper.readTree(response);	    
	    assertNotNull(jsonResponse);		
		return jsonResponse;		
	}
	
	@RequestMapping("/getResults")
	public ModelAndView results(String searchterm, @RequestParam String clientid, String password, String track, String album, String artist, String playlist) throws URISyntaxException, JsonMappingException, JsonProcessingException
	{
		RestTemplate restTemplate = new RestTemplate();
	    final String baseUrl = "https://api.spotify.com/v1/search";

	    ModelAndView mv = new ModelAndView("results.jsp");
	    String bearer = "";
	    String searchtype = "";
	    String result;
	    JsonNode responseJson;
	    ObjectMapper mapper = new ObjectMapper();	    
	    
	    Users user = user_repo.findByCredentials(clientid, password);
	    assertNotNull(user);
	        
	    Session session = session_repo.findValidToken(clientid);

	    if (session == null) {
	    	bearer = authorise(user.getClientId(), user.getClientSecret());
	    }
	    else {
	    	bearer = session.getToken();
	    }
	    	    	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.add("Authorization", "Bearer " + bearer);
	    
	    HttpEntity<String> entity = new HttpEntity<>("body", headers);
	    
	    if (track != null) {
	    	searchtype += "track ";
	    }
	    if (album != null) {
	    	searchtype += "album ";
	    }
	    if (artist != null) {
	    	searchtype += "artist ";
	    }
	    if (playlist != null) {
	    	searchtype += "playlist ";
	    }

	    searchtype = searchtype.replace(" ", ",").replaceAll(",$", "");
	    
	    searchterm = searchterm.replace(" ", "%20");
	    URI uri = new URI(baseUrl+"?q="+searchterm+"&type="+searchtype);//track,album,artist,playlist");
	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	        
	    //Verify request succeed
	    assert response.getStatusCodeValue() == 200: response.getBody();
	    
	    responseJson = processResponse(response.getBody());	    
//	    result = JsonPath.read(responseJson, "$.[*]['items'][*]['id']").toString();
	    
	    result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson);

	    mv.addObject("data", result);
	    
	    return mv;
	}
}
