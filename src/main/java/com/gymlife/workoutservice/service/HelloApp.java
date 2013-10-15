package com.gymlife.workoutservice.service;
 
import java.util.HashSet;
import java.util.Set;
 
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
 
@ApplicationPath("/rest")
public class HelloApp extends Application
{
  @Override
  public Set<Class<?>> getClasses()
  {
    Set<Class<?>> s = new HashSet<Class<?>>();
    s.add(HelloWorldService.class);
    return s;
  }
}