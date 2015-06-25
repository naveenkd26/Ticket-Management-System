# Ticket-Management-System
Ticket-Management-System is a Single Page application developed by using Open source technologies AngularJS (UI-router module), Java, Spring Framework, REST Web Services and NoSQL:MongoDB (mongolab). 

This application can be used by any Software Team to keep track of the Bugs/Tickets that might arise in the application. Each New Bug will be assigned to a particular Project and Team member who will be responsible to update the status of the bug between New/Resolved in DEV/Pushed to Staging/Testing/Closed/Reopened.

Below are the mandatory details that has to be provided while adding a new Bug.
Bug id#, Name, Project, Category, Priority, Assigned to, Status, Comments(Optional). All of the details except Bug id# and Name can be updated.

This aplication consists of two parts

User options  : This section allows user to add new Bug and update existing Bug.

Adimin Section: This section allows user to configure Bug details i.e., user can add new Project/Category/Priority/Team member/Status which can be selected while adding/updating new/existing bugs in Users options section. 

Glimpse about implementation


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

Utility class to create DBObjects (Only language understood by MongoDB)
src/com/beans/UtilityInterface.java
   public BasicDBObject getAddBugDBObject(Map<String, String> bugDetails) throws Exception;
   public BasicDBObject[] getUpdateBugDBObject(Map<String, String> queryParams, Map<String, String> modifiedDetails) throws Exception;
   public List<Bug> getBugObjectsListFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception;
   public BasicDBObject getFindBugDBObject(Map<String, String> searchParams) throws Exception;
   public Map<String, String> getHashMapFromJSON(String jsonData) throws Exception;
   public String getAvailableBugIDFromDBCursor(DBCursor cur) throws Exception;
   public BasicDBObject getBugIDDBObject() throws Exception;
   public BasicDBObject[] getIncrementBugSeqDBObject() throws Exception;
   public BasicDBObject[] getAddAdminOptionDBObject(String optionName, String value) throws Exception;
   public AddBugInfo getAddBugInfoObjectFromDBCursor(ApplicationContext context, DBCursor dbCursor) throws Exception;
   public BasicDBObject getAddBugInfoDBObject() throws Exception;


MongoDriver class to talk with MondoDB remote connection (Mongolab).
src/com/datalayer/MongoDriver.java
   public String insertNewBug(BasicDBObject newBug);
   public int updateBug(BasicDBObject[] updateQueryDBObject);
   public DBCursor getBugDetails(BasicDBObject searchBugParams);
   public DBCursor getBugList();
   public void setUri(String connectionString);
   public void setcollection(String collectionName);
   public void cleanUpResources();


