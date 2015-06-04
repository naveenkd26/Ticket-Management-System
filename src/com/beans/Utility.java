package com.beans;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.BasicDBObject; 
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Member;
import java.lang.reflect.Field;

public class Utility implements UtilityInterface{

	public Utility() {
		// TODO Auto-generated constructor stub
	}
	
	//Using HashMap instead of Bug class as parameter in order to avoid any changes to the Bug class
	//if in future we have to add more details to the new Bug, we don't have to change the Bug class.
	public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails){
		
		BasicDBObject obj = new BasicDBObject();
		
		//Making an DBObject with bug details
		for(Map.Entry<String, String> eachDetail : bugDetails.entrySet()){
			obj.put(eachDetail.getKey(), eachDetail.getValue());
		}
			
		return obj;
	}
	
	public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails){
		
        //Building the object with query parameters.
		BasicDBObject queryParamsDBObj = new BasicDBObject();
		
		for(Map.Entry<String, String> eachDetail : queryParams.entrySet()){
			queryParamsDBObj.put(eachDetail.getKey(), eachDetail.getValue());
		}
		
		//Building the object with new details.
		BasicDBObject modifiedDetailsDBObj = new BasicDBObject();
		
		for(Map.Entry<String, String> eachDetail : modifiedDetails.entrySet()){
			modifiedDetailsDBObj.put(eachDetail.getKey(), eachDetail.getValue());
		}
		
		
		BasicDBObject[] updateQueryObj = new BasicDBObject[]{queryParamsDBObj, new BasicDBObject("$set", modifiedDetailsDBObj )};
		
		return updateQueryObj;
	}
	
	
	//DBCursor results = collection.find(new BasicDBObject("issueName", new BasicDBObject("$gt", "1")));
	public BasicDBObject getFindBugDBObject(Map<String, String> searchParams){
		
		BasicDBObject obj = new BasicDBObject();
		
		for(Map.Entry<String, String> eachSearchParam : searchParams.entrySet()){
			  obj.put(eachSearchParam.getKey(), eachSearchParam.getValue());
		}
		
		return obj;
	}
	
	public Bug getBugObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor){
		 
		Bug bugObj = (Bug)context.getBean("newBugBean");
			
		DBObject doc;	
		Field[] fields = bugObj.getClass().getDeclaredFields();	
		
		try{
			
			while(dbCursor.hasNext()){
			  doc = dbCursor.next();
			  for(Field eachMember : fields)	
				eachMember.set(bugObj, doc.get(eachMember.getName()));
			}
		}
		catch(IllegalAccessException e){
			e.printStackTrace();
		}

      return bugObj;
	}
	

}
