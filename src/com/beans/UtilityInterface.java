package com.beans;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public interface UtilityInterface {
	public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails) throws Exception;
	public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails) throws Exception;
	public List<Bug> getBugObjectsListFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception;
	public BasicDBObject getFindBugDBObject(Map<String, String> searchParams) throws Exception;
	public Map<String, String> getHashMapFromJSON(String jsonData) throws Exception;
	public String getAvailableBugIDFromDBCursor(DBCursor cur) throws Exception;
	public BasicDBObject getBugIDDBObject() throws Exception;
	public BasicDBObject[] getIncrementBugSeqDBObject() throws Exception;
}
