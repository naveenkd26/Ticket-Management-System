package com.beans;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public interface UtilityInterface {
	public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails);
	public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails);
	public Bug getBugObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor);
	public BasicDBObject getFindBugDBObject(Map<String, String> searchParams);
}
