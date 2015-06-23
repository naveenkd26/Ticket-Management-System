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
	

	//Function Name: getAddBugDBObject(Map<String, String> bugDetails)
	//Parameters   : Map<String, String> which contains bug information in Key-Value pairs. Ex: ("bugId","1834")
	//Description  : Generate DBObject from the Map. MongoDB API accepts querying only in terms of DBObject.   
	//Used HashMap instead of Bug class as parameter in order to facilitate any changes to the existing Bug structure,
	//say adding a new detail ("LastUpdatedBy", "XXX") to bug information just needs adding the detail to HashMap.
	public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails) throws Exception{
		
		BasicDBObject obj = new BasicDBObject();
		
		//Making an DBObject with bug details
		for(Map.Entry<String, String> eachDetail : bugDetails.entrySet()){
			obj.put(eachDetail.getKey(), eachDetail.getValue());
		}
		
		return obj;
	}
    //
	//
	//
	//Function Name: getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails)
	//Parameters   : (Map<String, String>, Map<String, String>)
	//               1st parameter contains the information about which bug has to be updated,
	//               2nd parameter contains new details.
	//Description  : Generate DBObjects from the Map.
	public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails) throws Exception{
		
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
	//
	//
	//
	//DBCursor results = collection.find(new BasicDBObject("issueName", new BasicDBObject("$gt", "1")));
	//Function Name: getFindBugDBObject(Map<String, String> searchParams)
	//Parameters   : Map<String, String> which contains information about the bug which has to be searched.
	//Description  : Generate DBObject from the Map.
	public BasicDBObject getFindBugDBObject(Map<String, String> searchParams) throws Exception{
		
		BasicDBObject obj = new BasicDBObject();
		
		for(Map.Entry<String, String> eachSearchParam : searchParams.entrySet()){
			  obj.put(eachSearchParam.getKey(), eachSearchParam.getValue());
		}
		
		return obj;
	}
	//
	//
	//
	//Function Name: getBugObjectsFromDBCursor(ApplicationContext context, DBCursor dbCursor)
	//Parameters   : (ApplicationContext, DBCursor) ApplicationContext to instantiate new bug object and DBCursor to pull the details from.
	//Description  : Returns list of Bug objects from the DBCursor(returned by mongoDB).
	public List<Bug> getBugObjectsListFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception{
		 
		List<Bug> bugList = new <Bug>ArrayList();
		DBObject doc;
		//Getting new Bug Object through Spring.
		Bug bugObj = (Bug)context.getBean("newBugBean");
			
        //Adding the retrieved details to the new bug object using Java Reflection API.	
		Field[] fields = bugObj.getClass().getDeclaredFields();	
		
		try{		
			while(dbCursor.hasNext()){
					 
			  doc = dbCursor.next();
			  if(!doc.containsField("bugId")){
				  continue;
			  }
			  for(Field eachMember : fields)	
				eachMember.set(bugObj, doc.get(eachMember.getName()));
			  
			  bugList.add(bugObj);
			  bugObj = (Bug)context.getBean("newBugBean");

			}
		}
		catch(IllegalAccessException e){
			e.printStackTrace();
		}

      return bugList;
	}
	//
	//
	//
	//Function Name: getHashMapFromJSON(String jsonData)
	//Parameters   : (jsonData) jsonData contains the data which will be added to the HashMap.
	//Description  : Returns the details as HashMap.
	public Map<String, String> getHashMapFromJSON(String jsonData) throws Exception{
		 
		Map<String, String> bugDetailsMap = new <String, String>HashMap();
		
		//check whether jsonData has single or multiple "key:value" pairs
		//because single "key:value" doesn't contain delimiter ',' and will return Null pointer Exception.
		if(jsonData.contains(",")){
			     
			String[] details = jsonData.split(",");
	        int i, j, k;
			
 	 	   for(String eachDetail : details){
	      	 i = eachDetail.indexOf("\"");
	      	bugDetailsMap.put(eachDetail.substring(i+1 , j = eachDetail.indexOf("\"", i+1)), eachDetail.substring((k = eachDetail.indexOf("\"", j+1)) + 1, eachDetail.indexOf("\"", k+1)) );
	           }
	 	   
		}else{
			//Case for single "key:vale" pair.
			//Use :when we are sending only bugId for findBugDetails()
			int j, k;
			bugDetailsMap.put(jsonData.substring(2, j = jsonData.indexOf("\"", 2)), jsonData.substring( (k= jsonData.indexOf("\"", j+1)) + 1, jsonData.indexOf("\"", k+1)));	
		}

      return bugDetailsMap;
	}
	//
	//
	//
	//Function Name: getBugIDDBObject()
	//Parameters   : None
	//Description  : Generates DBObject to enquire mongoDB for the next available Bug ID.   
	public BasicDBObject getBugIDDBObject() throws Exception{
		
		//Passing the unique _id (primary key) where next available bug id is present.
		return new BasicDBObject("_id", "addBugInfo");	
	}
	//
	//
	//
	//Function Name: getAvailableBugIDFromDBCursor(DBCursor cur)
	//Parameters   : DBCursor which contains the next available Bug id received from the mongoDB.
	//Description  : Returns the next available Bug Id.  
	public String getAvailableBugIDFromDBCursor(DBCursor cur) throws Exception{
		
		DBObject doc;
		String nextAvailBugId = null;
		//Passing through the cursor to find the next available bug id.
		while(cur.hasNext()){  
			doc = cur.next();
			nextAvailBugId = doc.get("nextAvailBugId").toString();
		}
		
		return nextAvailBugId;
	}
	//
	//
	//
	//Function Name: incrementBugSeqDBObject()
	//Parameters   : None
	//Description  : Returns the query obj to increment the bug sequence. 
	public BasicDBObject[] getIncrementBugSeqDBObject() throws Exception{
		
		//Building the query object where nextAvailable sequence is found. 
		BasicDBObject queryParamsDBObj = new BasicDBObject("_id", "addBugInfo");		
		//Building the options object which increments the existing bug sequence.
		BasicDBObject updateParamsObj = new BasicDBObject("$inc", new BasicDBObject("nextAvailBugId", 1));
		
		BasicDBObject[] queryObj = new BasicDBObject[]{queryParamsDBObj, updateParamsObj};
		
		return queryObj;
	}
	//
	//
	//
	//Function Name: getAddAdminOptionDBObject(String optionName, String value)
	//Parameters   : optionName represents the option type and value which is the new value that has to be added.
	//Description  : Returns the query obj to add new value for the selected option(project/team member/status/etc). 
	public BasicDBObject[] getAddAdminOptionDBObject(String optionName, String value) throws Exception{
		
		//Building the search parameter object.
		BasicDBObject searchParamsObj = new BasicDBObject("_id", "addBugInfo");
		//Building the new parameter object.
		BasicDBObject updateParamnsObj = new BasicDBObject("$push",  new BasicDBObject(optionName, value));

		return new BasicDBObject[]{searchParamsObj, updateParamnsObj};
	}
	//
	//
	//
	//Function Name: getAddBugInfoDBObject()
	//Parameters   : None.
	//Description  : Returns the query obj to get addBugInfo like nextAvailableBugId,
	//               list of project's, category's, status's, priority's and team members pressent in mongoDB. 
	public BasicDBObject getAddBugInfoDBObject() throws Exception{
		
		return new BasicDBObject("_id", "addBugInfo");
	}
	//
	//
	//
	//Function Name: getAddBugInfoObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor)
	//Parameters   : (ApplicationContext, DBCursor) ApplicationContext to instantiate new bug object and DBCursor to pull the details from.
	//Description  : Returns AdminInfo object from the DBCursor(returned by mongoDB).
	public AddBugInfo getAddBugInfoObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception{
		 
		List<Bug> bugList = new <Bug>ArrayList();
		DBObject doc;
		//Getting new Bug Object through Spring.
		AddBugInfo adminInfoObj = (AddBugInfo)context.getBean("addBugInfoBean");
			
        //Adding the retrieved details to the new bug object using Java Reflection API.	
		Field[] fields = adminInfoObj.getClass().getDeclaredFields();	
		
		try{		
			while(dbCursor.hasNext()){ 
			      doc = dbCursor.next();
			      for(Field eachMember : fields)	
				     eachMember.set(adminInfoObj, doc.get(eachMember.getName()));
                  break;			  
			}
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}

      return adminInfoObj;
	}
	//
	//
	//


}

















