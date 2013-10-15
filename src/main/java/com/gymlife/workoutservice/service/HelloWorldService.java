package com.gymlife.workoutservice.service;
 
import java.util.List;

import com.gymlife.workoutservice.db.MySQLAccess;
import com.gymlife.workoutservice.db.Workout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gymlife.workoutservice.db.MySQLAccess;
 
@Path("/hi")
public class HelloWorldService
{
  @GET
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Workout getMessage(@PathParam("name") String name) throws Exception
  {
    //String outMsg = "Hello " + name + "!";
    
    //MySQLAccess dao = new MySQLAccess();
    //dao.readDataBase();
    //Workout workouts = dao.getWorkouts();
    //return Response.status(200).entity(outMsg).build();
	return new Workout("1", "2", "3", "4", "5");
  }
}