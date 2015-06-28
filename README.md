# Ticket-Management-System
Ticket-Management-System is a Single Page application developed by using Open source technologies **AngularJS (UI-router module), Java, Spring Framework, REST Web Services, NoSQL:MongoDB (mongolab) and Bootstrap.** 

This application can be used by any software team to keep track of the Bugs/Tickets that might arise during Sofware Development Life Cycle. Each new Bug will be assigned to a particular Project and a Team member who will be responsible to update the status of the Bug between New/Resolved in DEV/Pushed to Staging/Testing/Closed/Reopened.

Following are the mandatory details that has to be provided while adding a new Bug.
1. Bug id#
2. Bug Name
3. Project
4. Category
5. Assigned to
6. Status
7. Comments (Optional)

####Two sections present in the application (Option pane present in the left side of the page)
#####User section - User:
This section allows user to add new Bug and update details of existing Bug. All of the details except Bug id# and Bug Name can be updated.
#####Admin Section - Admin Settings: 
This section allows user to configure Bug details i.e., user can add new Project/Category/Priority/Team member/Status, which can be selected while adding/updating new/existing bugs in Users options section.

---
## Glimpse about implementation

```Java
REST API implementation
src/com/controllers/BugContoller.java
  public String addBug(@PathParam("bugDetails") String bugParams)
  public String getBugList()
  public String getBugDetails(@PathParam("bugParams") String searchParams)
  public String updateBugDetails(@PathParam("modifiedParams") String modifiedBugDetails)
  public String getNextAvailableBugId()
  public String incrementBugSequence()
  public String addAdminOption(@PathParam("newAdminOption") String newAdminOption)
  public String getAddBugInfo()


Utility class to create DBObjects, retrieve data from DBCursor (DBObject, DBCursor:- Only language understood by MongoDB)
src/com/beans/UtilityInterface.java
	public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails) throws Exception;
	public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails) throws Exception;
	public List<Bug> getBugObjectsListFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception;
	public BasicDBObject getDBObjectFromMap(Map<String, String> searchParams) throws Exception;
	public Map<String, String> getHashMapFromJSON(String jsonData) throws Exception;
	public String getAvailableBugIDFromDBCursor(DBCursor cur) throws Exception;
	public BasicDBObject getBugIDDBObject() throws Exception;
	public BasicDBObject[] getIncrementBugSeqDBObject() throws Exception;
	public BasicDBObject[] getAddAdminOptionDBObject(String optionName, String value) throws Exception;
	public AddBugInfo getAddBugInfoObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception;
	public BasicDBObject getAddBugInfoDBObject() throws Exception;
	public BasicDBObject[] getExistingOptionsDBObject(String optionName) throws Exception;


MongoDriver class to talk with MongoDB remote connection - Mongolab.
src/com/datalayer/MongoDriverInterface.java
	public String insertNewBug(BasicDBObject newBug);
	public int updateBug(BasicDBObject[] updateQueryDBObject);
	public DBCursor getBugDetails(BasicDBObject searchBugParams);
	public DBCursor find(BasicDBObject queryParams, BasicDBObject projectionParams);
	public DBCursor getBugList();
	public void setUri(String connectionString);
	public void setcollection(String collectionName);
	public void cleanUpResources();
   
AngularJS functionality
src/WebContent/Scripts/index.js
```

####Work in progress
1. Validitation for blank options before adding new option in Admin section.
2. Check whether or not the new option for Project/Category/Priority/Team member/Status already exists before adding saving it to MongoDB.

