package com.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.*;
import com.beans.AddBugInfo;
import com.beans.Bug;
import com.beans.BugDetails;
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

@Path("bugAPI")
public class BugController {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("SpringConfig.xml");
	UtilityInterface utilObj;
	MongoDriverInterface mongoDriver;
	Gson gson;
	
	public BugController() {
		// TODO Auto-generated constructor stub
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/addbug/{bugDetails}
	//@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//Description: This is invoked when the user adds new bug.
	//Returns    : Returns status message of the insert query.
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
        		
		   //Executing the insert query and returning the response received from mongoDB.	   
			String resp = mongoDriver.insertNewBug(mongoQueryObj);
			System.out.println("Insert query status  " + resp);
			
			//Check for the insert query status
			
			//Getting the MongoDB Object(Only format accepted by mongoDB) for incrementing the bug sequence.
			BasicDBObject[] queryObj = utilObj.getIncrementBugSeqDBObject();
			  
            //Executing the query to increment bug sequence.
			int n = mongoDriver.updateBug(queryObj);
			System.out.println("Inceremented rows  " + n);
			
			if(n== 1){
				return "success";
			}else{
				return "failure";
			}	
			
       }
       catch(Exception e){
    	   e.printStackTrace();
    	   return "failure";
       }
	
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/getbuglist
	//Description: This is invoked when the bug list screen is loaded.
	//Returns    : Returns the list of bugs present in the the database.
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
			return "Error occured";
		}	
		
		//Returning the List of Bug objects to the client in JSON format.
		return gson.toJson(bugList);
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/getbugdetails/{params}
	//Description: This is invoked when the client clicks the 'Update' button for a particular bug in the bug list.
	//Returns    : Returns the details of the bug for which the user wants to update.  
	@GET
	@Path("/getbugdetails/{bugParams}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getBugDetails(@PathParam("bugParams") String searchParams){
		
		List<Bug> bugList;
	    DBCursor cur;
	    Map<String, String> findBugMap;
	    gson = new Gson();
	    BasicDBObject queryObj;
	    AddBugInfo bugOptions;
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		BugDetails bugDetails = (BugDetails)context.getBean("bugDetailsBean");
		
      try{
	
    	  //Getting a HashMap out of the search parameters.
    	  findBugMap = utilObj.getHashMapFromJSON(searchParams); 	
    	     	          
		  //Creating a MongoDBObject(Only format accepted by mongoDB) from the findBugMap.
    	  BasicDBObject mongoQueryObj = utilObj.getFindBugDBObject(findBugMap);
    	  
    	  //Querying the bug details by passing the queryObj to mongoDB which contains the search parameters.
    	  cur = mongoDriver.getBugDetails(mongoQueryObj);
         
          //Converting the DBCursor(returned by mongoDB) to list of Bug objects.
          bugList = utilObj.getBugObjectsListFromDBCursor(context, cur);
         
		  //Getting the MongoDB Object(Only format accepted by mongoDB) for retrieving the details
		  //about Available bug id, project, category, priority, team and status.
		  queryObj = utilObj.getAddBugInfoDBObject();
		  
          //Executing the query.
		  cur = mongoDriver.getBugDetails(queryObj);
		  
		  bugOptions = utilObj.getAddBugInfoObjectFromDBCursor(context, cur);
         
          bugDetails.setBugDetails(bugList);
          bugDetails.setAddBugInfo(bugOptions);
               
      }catch(Exception e){
	        e.printStackTrace();
	        return "Error occured";
      }
      	
	  return gson.toJson(bugDetails);	
	}
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/updatebug/{params}
	//Description: This is invoked when the bug details for a particular bug are updated.
	//Returns    : Returns the status of the update operation performed in the mongoDB.
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
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/getNextAvailableBugId
	//Description: This is invoked whenever client wants to add new Bug.
	//Returns    : Returns the next available Bug Id by querying the mongoDB
	//             for the new Bug the user want to add.
	@GET
	@Path("/getNextAvailableBugId")
    @Produces(MediaType.TEXT_HTML)
	public String getNextAvailableBugId(){
		
		//System.out.println("This is in getNextAvailableBugId controller");	
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		DBCursor cur;
		
		try{
			  //Getting the MongoDB Object(Only format accepted by mongoDB) for requesting the next available Bug Id.
			  BasicDBObject queryObj = utilObj.getBugIDDBObject();
			  
              //Executing the query to pull the net available Bug Id.
			  cur = mongoDriver.getBugDetails(queryObj);
			
		      //Pulling the bugId from the DBCursor and returning it to the client.
		      return utilObj.getAvailableBugIDFromDBCursor(cur);		  
			  		      
		}catch(Exception e){
	    	e.printStackTrace();
			return "Error occured";
		}	
						
	}
	//
	//
	//
	//http://localhost:8080/TicketSystem/rest/bugAPI/incrementBugSequence
	//Description: This is invoked to increment the bug sequence.
	//Returns    : Returns status of the update query.
	@PUT
	@Path("/incrementBugSequence")
    @Produces(MediaType.TEXT_HTML)
	public String incrementBugSequence(){
		
		//System.out.println("This is in getNextAvailableBugId controller");	
		utilObj = (UtilityInterface)context.getBean("utilityBean");		
		mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
		
		try{
			  //Getting the MongoDB Object(Only format accepted by mongoDB) for incrementing the bug sequence.
			  BasicDBObject[] queryObj = utilObj.getIncrementBugSeqDBObject();
			  
              //Executing the query to increment bug sequence.
			  int n = mongoDriver.updateBug(queryObj);
			
		      if(n == 1)
		    	  return "success";
		      else
		    	  return "failure" ;
		      
		}catch(Exception e){
	    	e.printStackTrace();
			return "Error occured";
		}
	}
		//
		//
		//
		//http://localhost:8080/TicketSystem/rest/bugAPI/addAdminOption/{newAdminOption}
		//Description: This is invoked to add new admin option.
		//Returns    : Returns status of the insert query.
		@PUT
		@Path("/addAdminOption/{newAdminOption}")
	    @Produces(MediaType.TEXT_HTML)
		public String addAdminOption(@PathParam("newAdminOption") String newAdminOption){
			
			 BasicDBObject[] queryObj;
			 int j,k;
			//System.out.println("This is in getNextAvailableBugId controller");	
			utilObj = (UtilityInterface)context.getBean("utilityBean");		
			mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
					
			try{
				  //Getting the MongoDB Object(Only format accepted by mongoDB) for incrementing the bug sequence.
				  queryObj = utilObj.getAddAdminOptionDBObject(newAdminOption.substring(2, j = newAdminOption.indexOf("\"", 2)), newAdminOption.substring( (k= newAdminOption.indexOf("\"", j+1)) + 1, newAdminOption.indexOf("\"", k+1)));
				  
	              //Executing the query to increment bug sequence.
				  int n = mongoDriver.updateBug(queryObj);
				  
				  System.out.println("Adding new admin option status : " + n);
				
			      if(n == 1)
			    	  return "success";
			      else
			    	  return "failure" ;
			      
			}catch(Exception e){
		    	e.printStackTrace();
				return "Error occured";
			}
		}
			//
			//
			//
			//http://localhost:8080/TicketSystem/rest/bugAPI/getAddBugInfo
			//Description: This is invoked to add new admin option.
			//Returns    : Returns status of the insert query.
			@GET
			@Path("/getAddBugInfo")
		    @Produces(MediaType.APPLICATION_JSON)
			public String addAdminOption(){
				
    			BasicDBObject queryObj;
				utilObj = (UtilityInterface)context.getBean("utilityBean");		
				mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
						
				try{
					  //Getting the MongoDB Object(Only format accepted by mongoDB) for retrieving the details
					  //about Available bug id, project, category, priority, team and status.
					  queryObj = utilObj.getAddBugInfoDBObject();
					  
		              //Executing the query.
					  DBCursor cur = mongoDriver.getBugDetails(queryObj);
					  
					  AddBugInfo infoBug = utilObj.getAddBugInfoObjectFromDBCursor(context, cur);

                      //Returning the details in JSON format.
					  return new Gson().toJson(infoBug);
				      
				}catch(Exception e){
			    	e.printStackTrace();
					return "Error occured";
				}						
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
