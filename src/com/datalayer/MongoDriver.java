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
	
	//Making the below Database connection strings configurable using Spring constructor based injection.
	private MongoClientURI uri;
	private MongoClient client;
	private DB db;
	private DBCollection collection;
	//Below two data members for DEV mongoDB server use only.
	private String mongoServer;
	private int portNo;

	/************************PRODUCTION DB Connection starts*************************/
	//Database Connection setting using Spring method based injection.
	public void setUri(String connectionString) {
		this.uri = new MongoClientURI(connectionString);
		this.client = new MongoClient(this.uri);
		this.db = this.client.getDB(uri.getDatabase()); 
	}

	public void setcollection(String collectionName) {
		this.collection = db.getCollection(collectionName);
	}
	/************************PRODUCTION DB Connection ends****************************/
	
	/************************DEV DB Connection starts*********************************/
/*	//Database Connection setting the members using Spring method based injection.
	public void setMongoServer(String serverName) {
		this.mongoServer = serverName;
	}
	
	public void setPortNo(String portNo) {
		this.portNo = Integer.valueOf(portNo);
	}
	
	public void setDevDatabase(String dbName){
		this.client = new MongoClient(this.mongoServer, this.portNo);
		this.db = this.client.getDB(dbName);
	}

	public void setcollection(String collectionName) {
		this.collection = db.getCollection(collectionName);
	}*/
	/************************DDEV DB Connection ends*********************************/

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
	
	public void cleanUpResources(){
	 	 //cleaning up resources
		 client.close();	
	}

}





