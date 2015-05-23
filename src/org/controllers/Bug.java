package org.controllers;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

@Path("/getData")
public class Bug {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/greetMessage")
	public String greetMessage(){
		Gson gson = new Gson();
				
		ApplicationContext context = new ClassPathXmlApplicationContext("org/controllers/SpringConfig.xml");
		String  message = gson.toJson(context.getBean("employeeBean"));
		//System.out.println(message);
		return message;
	}

}

class Message{
	String name="naveen kumar";
	String number = "4526";
}