package com.datalayer;
import com.beans.UtilityInterface;
import com.mongodb.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoDriver implements MongoDriverInterface {
	
	//Although the Spring Framework doesn't gives us new bean  
	//instance time as we set mongoDriverBean scope to singleton.
	//Just making its constructor private (Doesn't harm).
	private MongoDriver() {
		// TODO Auto-generated constructor stub
	}
	
	//Making the below Database connection strings configurable using .
	private MongoClientURI uri;
	private MongoClient client;
	private DB db;
	private DBCollection collection;

	//Setting the members using Spring constructor based injection.
	public void setUri(String connectionString) {
		this.uri = new MongoClientURI(connectionString);
		this.client = new MongoClient(this.uri);
		this.db = this.client.getDB(uri.getDatabase()); 
	}

	public void setcollection(String collectionName) {
		this.collection = db.getCollection(collectionName);
	}
	

	//Description : This function is used to add New Bug into the database. 
	//Parameters  : bugDetails
	//Returns     : Nothing
	public String insertNewBug(BasicDBObject newBug){
				
		//Insert query format: db.collection.insert()
	    WriteResult status = collection.insert(newBug);
		
		if(status.getN() == 1){
			return "success";
		}else{
			return "failure";
		}
	 
	 }
	
	public DBCursor getBugDetails(BasicDBObject searchBugParams){
		
		return collection.find(searchBugParams);
	}
	
	
	public int updateBug(BasicDBObject[] updateQueryDBObject){
		
		//Format : db.collection.update(query, update, options)
		//Sample : db.tickets.update({new BasicDBObject("id", "2"), new BasicDBObject("issueName","This is modified using the update Query.")});
        WriteResult response = collection.update(updateQueryDBObject[0], updateQueryDBObject[1]);
        return response.getN();
        //System.out.println("Response from the Update Query: " + result);
        				
	}
	
	public DBCursor getBugList(){
		
		DBCursor bugList = collection.find();
		
		return bugList;
		
	}
	
	
	public void queryData(){
		
		//db.collection.find(query, projection)
		//Format : db.collection.find(query, projection).limit(5) - last one cursor modifier.
		//Sample : db.tickets.find(new BasicDBObject("bugId":"1345"));	
		BasicDBObject obj = new BasicDBObject();
		obj.put("bugId", "1678");
		
		//DBCursor results = collection.find(new BasicDBObject("bugId", "1678"));
		DBCursor results = collection.find(obj);
		DBObject doc;
		while(results.hasNext()){
		  doc = results.next();
		  	  System.out.println(doc.get("bugId") + "   " + doc.get("bugName") + "   " + doc.get("projectName") + "   " +  doc.get("category") + "   " +  doc.get("priority") + "   " +  doc.get("teamMember") + "   " +  doc.get("status") + "   " +  doc.get("comments"));

		}

	}
	
	public void cleanUpResources(){
	 	 //cleaning up resources
		 client.close();	
	}

}





