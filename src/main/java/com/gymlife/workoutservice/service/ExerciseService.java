package com.gymlife.workoutservice.service;
 
import java.sql.SQLException;
import java.util.List;

import com.gymlife.workoutservice.db.Difficulty;
import com.gymlife.workoutservice.db.ExerciseDaoImp;
import com.gymlife.workoutservice.db.Routine;
import com.gymlife.workoutservice.db.Exercise;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gymlife.workoutservice.db.ExerciseDaoImp;
 
@Path("/exercises")
public class ExerciseService
{
	
	ExerciseDaoImp dao = new ExerciseDaoImp();
	
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Exercise findById(@PathParam("id") String id) throws NumberFormatException, SQLException
  {
	  return dao.getWorkout(Integer.parseInt(id));
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Exercise> findAll() throws SQLException
  {
	  return dao.getAllWorkouts();
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Exercise create(Exercise exercise){
	  return null;
  }
}