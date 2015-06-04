package com.datalayer;

import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.DBCursor;

public interface MongoDriverInterface {
    String insertNewBug(BasicDBObject newBug);
    int updateBug(BasicDBObject[] updateQueryDBObject);
	void getBugList();
	void cleanUpResources();
	void queryData();
	DBCursor getBugDetails(BasicDBObject searchBugParams);	
}
