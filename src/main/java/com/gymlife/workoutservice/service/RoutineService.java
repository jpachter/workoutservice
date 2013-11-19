package com.gymlife.workoutservice.service;
 
import java.util.List;

import com.gymlife.workoutservice.db.dao.RoutineDaoImp;
import com.gymlife.workoutservice.db.dto.Routine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/routines")
public class RoutineService
{
	
	RoutineDaoImp dao = new RoutineDaoImp();
	
 
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Routine> getRoutines()
  {
	  return dao.getRoutines();
  }
  /*
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Routine findById(@PathParam("id") String id) throws SQLException
  {
	  return dao.getRoutine(Integer.parseInt(id));
  }
  */
  @GET
  @Path("/current/user/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Routine> findCurrentRoutine(@PathParam("id") String id)
  {
	  return dao.getCurrentRoutine(Integer.parseInt(id));
  }
  
  @GET
  @Path("/completed/user/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Routine> findCompletedRoutines(@PathParam("id") String id)
  {
	  return dao.getCompletedRoutines(Integer.parseInt(id));
  }
}