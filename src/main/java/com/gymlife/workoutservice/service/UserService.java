package com.gymlife.workoutservice.service;

import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gymlife.workoutservice.db.dao.UserDaoImp;
import com.gymlife.workoutservice.db.dto.User;

@Path("/user")
public class UserService {

	UserDaoImp dao = new UserDaoImp();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") String id){
		return dao.getById(id);
	}
	
	@GET
	@Path("/~{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByUsername(@PathParam("username") String username){
		return dao.getByUsername(username);
	}
	
	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean login(@FormParam("username") String username,
			@FormParam("password") String password){
		return dao.signIn(username, password);

	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName) {
		return dao.register(username, password, email, firstName, lastName);
	}
}