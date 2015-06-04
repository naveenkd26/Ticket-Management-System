package com.datalayer;

public class SampleDriver {

	/*	package org.datalayer;
	import com.mongodb.*;


	public class MongoDriver {

		
		static MongoClientURI uri = new MongoClientURI("mongodb://testuser1:ZxCvBnM@ds037262.mongolab.com:37262/ticketsystemdb");
		static MongoClient client = new MongoClient(uri);
		static DB db = client.getDB(uri.getDatabase());
		static DBCollection ticketsDB = db.getCollection("tickets");
		
		public MongoDriver() {
			// TODO Auto-generated constructor stub
		}
		
		public static void main(String args[]){
			
		System.out.println("ENteres into main");	
	    //Inserting new Bug starts.
	    BasicDBObject obj = new BasicDBObject();
	    obj.put("id", "1");
	    obj.put("issueName", "Not able to see data");
		obj.put("issueDate", "05/31/2015");
		obj.put("issueApp", "Rising Star");
			
	    System.out.println("************");
		BasicDBObject[] queryCrieria = {obj};
			
	    //insertNewBug(queryCrieria);
	    //Inserting new Bug ends.

		//Querying data
		//queryData();
	    
	    //Update Bug
	    //updateBug(new BasicDBObject[]{new BasicDBObject("id", "2"), new BasicDBObject("issueName","This is modified using the update Query.")});
	    
		//Querying data
	    //queryData();
		
		//Cleaning up resources
		cleanUpResources();
		
		}
		
		public static void insertNewBug(BasicDBObject[] queryCrieria){
			
			//Format: db.collection.insert()
			//"issueName", new BasicDBObject("$gt", "1")
			System.out.println(ticketsDB.insert(queryCrieria).toString());
		 
		 }
		
		public static void updateBug(BasicDBObject[] queryCrieria){
			
		    
			//Format: db.collection.update(query, update, options)
			//db.tickets.update({"budId":"1345"}, {"$set": {"bugName":"Page Crashing"}});
			//db.tickets.update({"budId":"1345"}, {"bugName":"Dev Database connection issue."});
	        String result = ticketsDB.update(queryCrieria[0], queryCrieria[1]).toString();
	        System.out.println("Response from the Update Query: " + result);
	        				
		}
		public static void getBugList(){
			
		}
		
		
		public static void  queryData(){
			
			
			//db.collection.find(query, projection)
			//db.collection.find(query, projection, projection).limit(5)..,last one cursor modifier.
			//db.tickets.find({"bugId":"1345"})
			DBCursor results = ticketsDB.find(new BasicDBObject("issueName", new BasicDBObject("$gt", "1")));
			System.out.println("queryData query invoked.");
			DBObject doc;
			while(results.hasNext()){
			  doc = results.next();
			  
			  System.out.println("This is the reteievd data");
			  System.out.println(doc.get("id") + "   " + doc.get("issueName") + "   " + doc.get("issueDate") + "   " +  doc.get("issueApp"));

			}

		}
		
		
		public static void cleanUpResources(){
			
			 //cleaning up resources
			 client.close();
			
		}

	}
*/

}
