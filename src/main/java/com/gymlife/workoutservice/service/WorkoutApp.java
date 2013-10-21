package com.gymlife.workoutservice.service;
 
import java.util.HashSet;
import java.util.Set;
 
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
 
@ApplicationPath("/")
public class WorkoutApp extends Application
{
  @Override
  public Set<Class<?>> getClasses()
  {
    Set<Class<?>> s = new HashSet<Class<?>>();
    s.add(ExerciseService.class);
    return s;
  }
}