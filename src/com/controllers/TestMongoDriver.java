package com.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.HashMap;

import com.mongodb.DBCursor;
import com.beans.Bug;
import com.beans.UtilityInterface;
import com.datalayer.MongoDriverInterface;
import com.google.gson.Gson;

public class TestMongoDriver {

	static ApplicationContext context = new ClassPathXmlApplicationContext("com/controllers/SpringConfig.xml");
	
	static UtilityInterface utilObj = (UtilityInterface)context.getBean("utilityBean");
	static MongoDriverInterface mongoDriver = (MongoDriverInterface)context.getBean("mongoDriverBean");
	
	public TestMongoDriver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		
		//utilObj.getBugObjectFromDBCursor(context);
		//printAllBugs();  - Working
		//getBugDetails(); - Working
		updateBug();
		getBugDetails();
	}
	
	public static String addBug(){
		
		Map<String, String> mapObject = new <String, String>HashMap();
		mapObject.put("bugId", "1678");
		mapObject.put("bugName", "Bug 1");
		mapObject.put("projectName", "Project Motion");
		mapObject.put("category", "Design Issue");
		mapObject.put("priority", "High");
		mapObject.put("teamMember", "John Doe");
		mapObject.put("status", "New");
		mapObject.put("comments", "This is a Dev Blocker.");
		
		String result = mongoDriver.insertNewBug(utilObj.getAddBugDBObject(mapObject));
		
		return result;
		
	}
	
	
	public static void printAllBugs(){
		
		mongoDriver.queryData();
		
	}
	
	public static void getBugDetails(){
		
		Map<String, String> findBug = new <String, String>HashMap();
		findBug.put("bugId", "1678");
		
		DBCursor cursorObj = mongoDriver.getBugDetails(utilObj.getFindBugDBObject(findBug));
		Bug bugObj = utilObj.getBugObjectFromDBCursor(context, cursorObj);

		String bugDetails = new Gson().toJson(bugObj);
		
		System.out.println(bugDetails);
	}
	
	public static void updateBug(){
		
		Map<String, String> queryParams = new <String, String>HashMap();
		Map<String, String> modifiedDetails = new <String, String>HashMap();
		
		queryParams.put("bugId","1678");
		modifiedDetails.put("teamMember", "Naveen");
		
		int n = mongoDriver.updateBug(utilObj.getUpdateBugDBObject(queryParams, modifiedDetails));
		
		
	}
	
	
}
