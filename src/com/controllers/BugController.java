package com.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.*;
import com.beans.Bug;
import com.beans.BugList;
import com.beans.UtilityInterface;
import com.datalayer.MongoDriverInterface;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.WebApplicationException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


//****************Add Exceptional Handling********************************
@Path("bugAPI")
public class BugController {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("com/controllers/SpringConfig.xml");
	UtilityInterface utilObj;
	MongoDriverInterface mongoDriver;
	Gson gson;
	
	
	public BugController() {
		// TODO Auto-generated constructor stub
	}
	
    
	//http://localhost:8080/TicketSystem/rest/bugAPI/addbug/{asdasdasdasdasdsdad}
	//@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@POST
	@Path("/addbug/{bugDetails}")
    @Produces(MediaType.TEXT_PLAIN)
	public String addBug(@PathParam("bugDetails") String bugParams){
		
		System.out.println("THis is in add bug controller");
				
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
        Map<String, String> bugDetails = new HashMap<String, String>();
        BasicDBObject mongoQueryObj;
        
		try{
		
	        //Getting a HashMap for the bug details received in JSON format.
			bugDetails = utilObj.getHashMapFromJSON(bugParams);			
			
			//Creating a Mongo DBObject(Only format accepted by mongodb) from the bugDetails HashMap.
			mongoQueryObj = utilObj.getAddBugDBObject(bugDetails); 
        		
/*        		new <String, String>HashMap();
        int i, j, k;
        
       try{
           //Creating a HashMap out of the JSON string which contain new bug details.
           //Gain: If in future, if we have to capture a  new detail for the bug, we don't
    	   //have to modify anything since we are processing the whole JSON string that we are
    	   //getting from the screen using delimiters ' , ' and ' " '. 
    	   for(String eachDetail : details){
        	 i = eachDetail.indexOf("\"");
        	 bugDetails.put(eachDetail.substring(i+1 , j = eachDetail.indexOf("\"", i+1)), eachDetail.substring((k = eachDetail.indexOf("\"", j+1)) + 1, eachDetail.indexOf("\"", k+1)) );
             }*/
           //System.out.println(bugDetails);		
		
		   //Executing the insert query and returning the response received from mongoDB.
		   return mongoDriver.insertNewBug(mongoQueryObj);
       }
       catch(Exception e){
    	   e.printStackTrace();
    	   return "Error occured.";
       }
	
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/getbuglist
	@GET
	@Path("/getbuglist")
    @Produces(MediaType.APPLICATION_JSON)
	public String getBugList(){
		
		//System.out.println("This is in getBugList controller");	
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		List<Bug> bugList;
		DBCursor cur;
		gson = new Gson();
		
		try{
			
			  //Querying all the bugs present in the mongoDB.
			  cur = mongoDriver.getBugList();
			  
		      //Converting the DBCursor(returned by mongoDB) to list of Bug objects.
			  bugList = utilObj.getBugObjectsListFromDBCursor(context, cur);
		      
		}catch(Exception e){
	    	e.printStackTrace();
			return "Error occured.";
		}	
		
		//Returning the List of Bug objects to the client in JSON format.
		return gson.toJson(bugList);				
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/getbugdetails/{params}
	@GET
	@Path("/getbugdetails/{bugParams}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getBugDetails(@PathParam("bugParams") String searchParams){
		
		List<Bug> bugList;
	    DBCursor cur;
	    Map<String, String> findBugMap;
	    gson = new Gson();
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		
      try{
	
    	  //Getting a HashMap out of the search parameters.
    	  findBugMap = utilObj.getHashMapFromJSON(searchParams); 	
    	     	          
		  //Creating a MongoDBObject(Only format accepted by mongoDB) from the findBugMap.
    	  BasicDBObject mongoQueryObj = utilObj.getFindBugDBObject(findBugMap);
    	  
    	  //Querying the bug details by passing the queryObj to mongoDB which contains the search parameters.
    	  cur = mongoDriver.getBugDetails(mongoQueryObj);
         
         //Converting the DBCursor(returned by mongoDB) to list of Bug objects.
         bugList = utilObj.getBugObjectsListFromDBCursor(context, cur);
         
      }catch(Exception e){
	        e.printStackTrace();
	        return "Error occured";
      }
      	
	  return gson.toJson(bugList.get(0));	
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/updatebug/{params}
	@PUT
	@Path("/updatebug/{modifiedParams}")
    @Produces(MediaType.APPLICATION_JSON)
	public String updateBugDetails(@PathParam("modifiedParams") String modifiedBugDetails){

	    int status;
		Map<String, String> modDetailsMap;
		Map<String, String> searchParamsMap = new HashMap<String, String>();
	    
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		
      try{
	
    	  //Getting a HashMap out of the modified parameters and search params.
    	  modDetailsMap = utilObj.getHashMapFromJSON(modifiedBugDetails); 	
    	  searchParamsMap.put("bugId", modDetailsMap.get("bugId") );
    	  
		  //Creating MongoDBObjects(Only format accepted by mongoDB) for modified parameters and search params. 	  
    	  BasicDBObject[] updateQueryDBObject = utilObj.getUpdateBugDBObject(searchParamsMap, modDetailsMap);
    	  
    	  //Executing the update query.
    	  status = mongoDriver.updateBug(updateQueryDBObject);
                
      }catch(Exception e){
	        e.printStackTrace();
	        return "Error occured";
      }
      	
	  return String.valueOf(status);	
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
