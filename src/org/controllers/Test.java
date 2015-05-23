package org.controllers;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String args[]){
		System.out.println("This is in local");
		ApplicationContext context = new ClassPathXmlApplicationContext("org/controllers/SpringConfig.xml");
		//ApplicationContext Context = new ClassPathXmlApplicationContext("/spring/constructorBeanInjectionXML/SpringConfig.xml");
		
		Gson gson = new Gson();
		String  message = gson.toJson(context.getBean("employeeBean"));
		System.out.println("The message is:  "+ message);
	}

}
