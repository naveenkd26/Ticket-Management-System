package com.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.*;
import com.beans.BugList;
import com.beans.UtilityInterface;
import com.datalayer.MongoDriverInterface;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.WebApplicationException;

import java.util.Map;
import java.util.HashMap;


//****************Add Exceptional Handling********************************
@Path("bugAPI")
public class BugController {

	public BugController() {
		// TODO Auto-generated constructor stub
	}
	
    
	//http://localhost:8080/TicketSystem/rest/bugAPI/addbug/{asdasdasdasdasdsdad}
	//@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@PUT
	@Path("/addbug/{bugDetails}")
    @Produces(MediaType.TEXT_PLAIN)
	public String addBug(@PathParam("bugDetails") String details){
				
		ApplicationContext context = new ClassPathXmlApplicationContext("com/controllers/SpringConfig.xml");
		UtilityInterface utilObj = (UtilityInterface)context.getBean("utilityBean");
		
		MongoDriverInterface mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		;
		Map<String, String> mapObject = new <String, String>HashMap();
		mapObject.put("bugId", "1678");
		mapObject.put("bugName", "Bug 1");
		mapObject.put("projectName", "Project Motion");
		mapObject.put("category", "Design Issue");
		mapObject.put("priority", "High");
		mapObject.put("teamMember", "John Doe");
		mapObject.put("status", "New");
		mapObject.put("comments", "This is a Dev Blocker.");
		
		//Using the utilObj to convert bug details present in HashMap to DBObject
		//as MongoDB Native Java driver accepts query only in DBObject format.
		return mongoDriver.insertNewBug(utilObj.getAddBugDBObject(mapObject));
		
/*		 MongoClientURI uri = new MongoClientURI("mongodb://testuser1:ZxCvBnM@ds037262.mongolab.com:37262/ticketsystemdb");
		 MongoClient client = new MongoClient(uri);
		 DB db = client.getDB(uri.getDatabase());
		 DBCollection collection = db.getCollection("tickets");
		 
			DBCursor results = collection.find();
			System.out.println("queryData query invoked.");
			DBObject doc;
			while(results.hasNext()){
			  doc = results.next();
			  
			  System.out.println("This is the reteievd data");
			  System.out.println(doc.get("id") + "   " + doc.get("issueName") + "   " + doc.get("issueDate") + "   " +  doc.get("issueApp"));

			}*/
	
	}
	
	//http://localhost:8080/TicketSystem/rest/bugAPI/getbuglist
	@GET
	@Path("/getbuglist")
    @Produces(MediaType.APPLICATION_JSON)
	public String getBugList(){
		
		BugList list1 = new BugList();
		list1.mockInput();
		String message = new Gson().toJson(list1);
		System.out.println(message);

		return message;
	}

	
/*	//Exception handling
	try{
		
	}catch(Exception e){
		throw new WebApplicationException(
				Response.status(Status.BAD_REQUEST)
				.entity("Could parse input  " + e.getMessage())
				.build()
				);
	}*/
	
}
