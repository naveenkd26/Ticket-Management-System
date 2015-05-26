(function(){

  console.log("Angular compiling starts.");
	var myApp = angular.module('ticketSystemApp', ["ui.router"]);
     
     //Routing for the nested views starts.
     myApp.config(function($stateProvider, $urlRouterProvider){

      $urlRouterProvider.otherwise("/adminOptions");

      $stateProvider
        .state('bugMenu', {
            url: "/bugMenu",
            templateUrl: "bugMenu.html"
        })
        .state('userOptions.listBugs', {
              url: "/listBugs",
              templateUrl: "bugList.html",
              controller: "bugListController"
          })
        .state('userOptions.addBug', {
              url: "/addBug",
              templateUrl: "addBug.html",
              controller: "addNewBugController",
          })
        .state('userOptions.searchBug', {
              url: "/searchBug",
              templateUrl: "",
          })
        .state('userOptions.listBugs.updateBug', {
              url: "/updateBug",
              templateUrl: "updateBug.html",
              controller: "updateBugController",
          })
        .state('userOptions', {
              url: "/userOptions",
              templateUrl: "bugMenu.html",
          })
        .state('adminOptions', {
              url: "/adminOptions",
              templateUrl: "adminOptions.html",
          })     

     });
     //Routing for the nested views ends.


    // bugMenuController starts.
     myApp.controller("bugMenuController", ['$scope', 'bugRepository', 'bugObject', function($scope, bugRepository, bugObject){

       //console.log("In the bug Menu controller");
       $scope.showInstructions = true;
       
       //Handling bugMenu events.
       $scope.listBugs = function(){
          console.log("List Bugs button clicked");
          $scope.hideInstruction();
       };
       
       //Function whick gets triggered when AddBug option is selected. 
       $scope.addBug = function(){        
          console.log("Add Bug button clicked");
          $scope.hideInstruction();
       };

       $scope.searchBug = function(){
          console.log(" Search Bug button clicked");
          $scope.hideInstruction();
       };

       //Function to hide the instructions in the bugMenu view.
       $scope.hideInstruction = function hideInstructions(){
        $scope.showInstructions  = false;
        };

     }]);
    // bugMenuController ends.


     // Repository class which makes all the REST calls starts.
     myApp.factory("bugRepository", ['$http', function($http){
	 
      return { 
    	   addBug: function(newBug, callback){
                        
                        console.log("This is in the bugrepo addBug function.");

                        $http.get('http://localhost:8080/TicketSystem/rest/getData/greetMessage')
                         .success(function(data, status, headers, config) {
                               //console.log("This is in rest call success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("addBug web call failed.");
                          });

                  },

         getBugList: function(callback){

                        $http.get('http://localhost:8080/TicketSystem/rest/getData/greetMessage')
                         .success(function(data, status, headers, config) {
                               //console.log("This is in rest call success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("addBug web call failed.");
                          });
                      } 
      };    	
    }]);
    // Repository class which makes all the REST calls ends.


    // Service which returns an Bug Object starts.
    myApp.factory("bugObject", function(){
      return function(){
      this.bugId = "naveen";
      this.bugName = "";
      this.projectName = "";
      this.category = "";
      this.priority = "";
      this.teamMember = "";
      this.status = "";
      this.comments = "";
      };
    });
   // Service which returns Bug Object ends.

   
   // addNewBugController starts.
   myApp.controller("addNewBugController", ['$scope', 'bugRepository', 'bugObject', function($scope, bugRepository, bugObject){
    
       //scope variables starts
       $scope.bugId = "naveen kumar dokuparthi";
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";
       //scope variables ends

       //console.log("This is in Add new bug controller....");   
       $scope.addNewBug = function(){
          console.log("Adding new bug to the DB.");
          //$scope.hideInstruction();
 
          //Creating Bug object
          var newBug = new bugObject();
          newBug.bugId = $scope.bugId;
          newBug.bugName = $scope.bugName;
          newBug.projectName = $scope.projectName;
          newBug.category = $scope.category;
          newBug.priority = $scope.priority;
          newBug.teamMember = $scope.teamMember;
          newBug.status = $scope.status;
          newBug.comments = $scope.comments;

          console.log(JSON.stringify(newBug));
          /*bugRepository.addBug(newBug,function(data){
            console.log("This is in the callback function.");
            //console.log(data);
          });*/
     };

     $scope.resetBugDetails = function(){
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";     
     };

   }]);
  // addNewBugController ends.


  // bugListController starts.
  myApp.controller("bugListController", ['$scope', function($scope){
    
    $scope.issueList = [
         {"bugId":"1234","bugName":"Naveen","projectName":"Project Newton","category":"Production Issue","priority":"Medium","teamMember":"John Baartz","status":"Testing","comments":""},
         {"bugId":"1345","bugName":"Test 2","projectName":"Project Motion","category":"Incomplete Requirements","priority":"Critical","teamMember":"John Doe","status":"New","comments":"Need ASAP."},
         {"bugId":"1451","bugName":"Test 3","projectName":"Project Alpha","category":"Production Issue","priority":"Medium","teamMember":"Michael Boltz","status":"Testing","comments":"Need immediate action."},
         {"bugId":"1898","bugName":"Test 4","projectName":"Project Alpha","category":"Internal Issues","priority":"Low","teamMember":"Wendy Kim","status":"Testing","comments":"WIll be pushed to DEV."},
         {"bugId":"1678","bugName":"Test 5","projectName":"Project Motion","category":"Design Issue","priority":"High","teamMember":"John Doe","status":"New","comments":"This is a Dev Blocker."}
      ];
    
    /*$scope.updateBug = function(bugId){
    	console.log("The selected BugId is:::::::::::::  "+ bugId);
      }*/
  }]);
  // bugListController ends.


  // updateBugcontroller starts
  myApp.controller("updateBugController", ['$scope','$stateParams', function($scope, $stateParams){
    
    console.log("Bug Id :  "+ $stateParams.bugId);   
	  //console.log("(updateBug controller)The selected BugId is:  "+ $stateParams.bugId);

  }]);
  // updateBugcontroller ends.


})();