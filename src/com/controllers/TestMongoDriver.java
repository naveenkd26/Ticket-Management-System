package com.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import com.beans.Bug;
import com.beans.UtilityInterface;
import com.datalayer.MongoDriverInterface;
import com.google.gson.Gson;
import com.mongodb.*;

public class TestMongoDriver {

	static ApplicationContext context = new ClassPathXmlApplicationContext("com/controllers/SpringConfig.xml");
	
	static UtilityInterface utilObj = (UtilityInterface)context.getBean("utilityBean");
	static MongoDriverInterface mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
	
	public TestMongoDriver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		
		try{
		//utilObj.getBugObjectFromDBCursor(context);
		//printAllBugs();  - Working
		//getBugDetails(); - Working
		//updateBug();
		//getBugDetails();
		//printAllBugs();
		//mongoDriver.queryData();
		//addBug();
	    //addBugIdSequence();
	    //getNextBugId();
	    incrementBugId();
	    getNextBugId();
		}catch(Exception e){
			e.printStackTrace();
		}
		
/*		String temp = "{'bugId':'naveen kumar dokuparthi','bugName':'','projectName':'Project Newton','category':'Internal Issues','priority':'Medium','teamMember':'Wendy Kim','status':'New','comments':''}";
        temp.replace('{','*');
		String[] details = temp.split(",");
        Map<String, String> bugDetails = new <String, String>HashMap();
        int i, j, k;
        for(String eachDetail : details){
        	i = eachDetail.indexOf("'");
        	bugDetails.put(eachDetail.substring(i+1 , j = eachDetail.indexOf("'", i+1)), eachDetail.substring((k = eachDetail.indexOf("'", j+1)) + 1, eachDetail.indexOf("'", k+1)) );
        }
        System.out.println(bugDetails);*/
		
		
	/*	String jsonData = "{\"bugId\":\"1685\"}";
		System.out.println(jsonData);
        Map<String, String> bugDetails = new <String, String>HashMap();
        int j,k;
        //bugDetails.put(jsonData.substring(2, j = jsonData.indexOf("\"", 2)), jsonData.substring( (k= jsonData.indexOf("\"", j+1)) + 1, jsonData.indexOf("\"", k+1)));
        System.out.println(bugDetails);
        System.out.println(jsonData.substring(2, j = jsonData.indexOf("\"", 2)) + "   " + jsonData.substring((k= jsonData.indexOf("\"", j+1)) + 1, jsonData.indexOf("\"", k+1)));
		*/
	}
	
	public static String addBug() throws Exception{
		
		Map<String, String> mapObject = new <String, String>HashMap();
		mapObject.put("bugId", "1685");
		mapObject.put("bugName", "Bug 5");
		mapObject.put("projectName", "Project Motion");
		mapObject.put("category", "Design Issue");
		mapObject.put("priority", "High");
		mapObject.put("teamMember", "Robert Williams");
		mapObject.put("status", "New");
		mapObject.put("comments", "No Plan information found.");
		
		String result = mongoDriver.insertNewBug(utilObj.getAddBugDBObject(mapObject));
		System.out.println(result);
		
		return result;
		
	}
	
	
	public static void printAllBugs() throws Exception{
		
		//mongoDriver.queryData();
		List<Bug> bugList = utilObj.getBugObjectsListFromDBCursor(context, mongoDriver.getBugList());
		
		for(Bug eachBug : bugList){
			String bugDetails = new Gson().toJson(eachBug);	
			System.out.println(bugDetails);
		}
		
		Gson gson= new Gson();

		System.out.println(gson.toJson(bugList));
		
	}
	
	public static void getBugDetails() throws Exception{
		
/*		Map<String, String> findBug = new <String, String>HashMap();
		findBug.put("bugId", "1681");
		
		String searchParams = new Gson().toJson(findBug);
		System.out.println(searchParams);
		System.out.println(new Gson().toJson(utilObj.getBugObjectsListFromDBCursor(context, mongoDriver.getBugDetails(utilObj.getFindBugDBObject(findBug)))));*/
		
		String jsonData = "{\"bugId\":\"1679\"}";
		     
		List<Bug> list = utilObj.getBugObjectsListFromDBCursor(context, mongoDriver.getBugDetails(utilObj.getFindBugDBObject(utilObj.getHashMapFromJSON(jsonData))));
		
		System.out.println(new Gson().toJson(list));
		     
		
/*		DBCursor cursorObj = mongoDriver.getBugDetails(utilObj.getFindBugDBObject(findBug));
		List<Bug> bugList = utilObj.getBugObjectsListFromDBCursor(context, cursorObj);

		if(!bugList.isEmpty()){
	    	Bug bugObj = bugList.get(0);
			String bugDetails = new Gson().toJson(bugObj);	
			System.out.println(bugDetails);
		}else{
			System.out.println("No Bug found with id: " + findBug.get("bugId"));
		}*/
		


	}
	
	public static void updateBug() throws Exception{
		
		Map<String, String> queryParams = new <String, String>HashMap();
		Map<String, String> modifiedDetails = new <String, String>HashMap();
		
		queryParams.put("bugId","1670");
		modifiedDetails.put("teamMember", "Prem");
		
		int n = mongoDriver.updateBug(utilObj.getUpdateBugDBObject(queryParams, modifiedDetails));
		if(n == 1){
			System.out.println("Update Successful.");
		}else{
			System.out.println("Update Not Successful.");	
		}
		
		
		
	}
	
	public static void addBugIdSequence(){
		
		MongoClientURI uri = new MongoClientURI("mongodb://testuser1:ZxCvBnM@ds037262.mongolab.com:37262/ticketsystemdb");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection collection = db.getCollection("tickets");
		
		
		BasicDBObject dbObj = new BasicDBObject();
		dbObj.put("_id", "bugSequence");
		dbObj.put("nextBugId", 1448);
		
		String dbresponse = collection.insert(dbObj).toString();
		
		client.close();
		
		
	}
	
	static void getNextBugId(){
			
		MongoClientURI uri = new MongoClientURI("mongodb://testuser1:ZxCvBnM@ds037262.mongolab.com:37262/ticketsystemdb");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection collection = db.getCollection("tickets");
		
		//Creating an DBObject with the search parameters.
		BasicDBObject searchParams = new BasicDBObject();
		searchParams.put("_id", "bugSequence");
				
		DBCursor results = collection.find(searchParams);
		DBObject doc;
		while(results.hasNext()){  
			doc = results.next();
		    System.out.println("Next bug sequence is " + "   " +  doc.get("nextBugId"));
		}	
		client.close();	
	}
	
	static void incrementBugId(){
		
		MongoClientURI uri = new MongoClientURI("mongodb://testuser1:ZxCvBnM@ds037262.mongolab.com:37262/ticketsystemdb");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection collection = db.getCollection("tickets");
		
		//db.tickets.update({_id:"userid"}, {$inc:{seq:1}});
		
		BasicDBObject searchParams = new BasicDBObject();
		searchParams.put("_id", "bugSequence");
		
		BasicDBObject updateParams = new BasicDBObject();
		updateParams.put("$inc", new BasicDBObject("nextBugId", 1));
			
		WriteResult response = collection.update(searchParams, updateParams);
		
		System.out.println("Updated number of rows " + response.getN());
		
		client.close();	
		
	}
	
	
}

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
