package com.gymlife.workoutservice.service;
 
import java.sql.SQLException;
import java.util.List;

import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.db.dao.ExerciseDaoImp;
import com.gymlife.workoutservice.db.dao.RoutineDaoImp;
import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.dto.Routine;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/routines")
public class RoutineService
{
	
	RoutineDaoImp dao = new RoutineDaoImp();
	
 
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Routine> findAll() throws SQLException
  {
	  return dao.getRoutines();
  }
  

}