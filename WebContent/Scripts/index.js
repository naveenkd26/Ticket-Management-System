(function(){

  console.log("Angular compiling starts.");
	var myApp = angular.module('ticketSystemApp', ["ui.router"]);
	var DEV_ENV = "http://localhost:8080/TicketSystem/rest/bugAPI/";
	var PROD_ENV = "";
	
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});
     
     //Routing for the nested views starts.
     myApp.config(function($stateProvider, $urlRouterProvider){

      $urlRouterProvider.otherwise("/adminOptions");

      $stateProvider
        .state('bugMenu', {
            url: "/bugMenu",
            templateUrl: "bugMenu.html"
        })

        .state('userOptions', {
              url: "/userOptions",
              templateUrl: "bugMenu.html",
              controller: "bugMenuController"
          }) 
        .state('adminOptions', {
              url: "/adminOptions",
              templateUrl: "adminOptions.html",
              controller: "adminOptionsController"
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
        //State for the updateBug.html view from listBugs.html view (Navigating source).
        //Putting the view in the place holder present in bugMenu.html
        .state('bugMenu.updateBug', {
              url: "/updateBug/:bugId",
              templateUrl: "updateBug.html",
              controller: "updateBugController",
              onEnter: function(){
                 console.log("This is in updateBug onEnter state.");
              }
          })        
        //States to navigate back to listBugs, addBug sections from updateBug view using bugMenu options.
        //Reason: Once we enter into the updateBug view, the state changes from userOptions state to bugMenu state,
        //any navigation in this phase would result in navigation from the current base state bugMenu but not from
        //userOptions which will result in routing error.
        .state('bugMenu.listBugs', {
              url: "/listBugs",
              templateUrl: "bugList.html",
              controller: "bugListController"
          }) 
        .state('bugMenu.addBug', {
              url: "/addBug",
              templateUrl: "addBug.html",
              controller: "addNewBugController",
          })      

     });
     //Routing for the nested views ends.


    // bugMenuController starts.
     myApp.controller("bugMenuController", ['$scope', 'bugRepository', 'bugObject', function($scope, bugRepository, bugObject){

       console.log("In the bug Menu controller");
      
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
    	   addBug: function(newBugObject, callback){
                        
                        console.log("This is in addBug bugRepo function.");
                        console.log("Below are the new bug details.");
                        var bugDetails = JSON.stringify(newBugObject);
                        console.log(bugDetails);
                        $http.post(DEV_ENV + 'addbug/' + bugDetails)
                         .success(function(data, status, headers, config) {
                               //console.log("This is in rest call success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("addBug web call failed.");
                          });
                  },
        updateBug: function(newDetailsObj, callback){
                        
                        console.log("This is in update bugRepo function.");
                        console.log("Below are the update bug details.");
                        var modifiedDetails = JSON.stringify(newDetailsObj);
                        console.log(modifiedDetails);
                        $http.put(DEV_ENV + 'updatebug/' + modifiedDetails)
                         .success(function(data, status, headers, config) {
                               //console.log("This is in update bug success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("updateBug web call failed.");
                          });
                  },

         getBugList: function(callback){

                         console.log("This is in getBugLis bugRepo function.");
                        $http.get(DEV_ENV + 'getbuglist')
                         .success(function(data, status, headers, config) {
                               console.log("Loaded bug list Sucessfully.");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("Loading bug list failed.");
                          });
/*                          var data = [ {"bugId":"1234","bugName":"Naveen","projectName":"Project Newton","category":"Production Issue","priority":"Medium","teamMember":"John Baartz","status":"Testing","comments":""},
                                       {"bugId":"1345","bugName":"Test 2","projectName":"Project Motion","category":"Incomplete Requirements","priority":"Critical","teamMember":"John Doe","status":"New","comments":"Need ASAP."},
                                       {"bugId":"1451","bugName":"Test 3","projectName":"Project Alpha","category":"Production Issue","priority":"Medium","teamMember":"Michael Boltz","status":"Testing","comments":"Need immediate action."},
                                       {"bugId":"1898","bugName":"Test 4","projectName":"Project Alpha","category":"Internal Issues","priority":"Low","teamMember":"Wendy Kim","status":"Testing","comments":"WIll be pushed to DEV."},
                                       {"bugId":"1678","bugName":"Test 5","projectName":"Project Motion","category":"Design Issue","priority":"High","teamMember":"John Doe","status":"New","comments":"This is a Dev Blocker."}
                                     ];
                          callback(data);*/          
                      }, 

        getBugDetails: function(searchParamsObj, callback){

                         console.log("This is in getBugDetails bugRepo function. BugId:  " + searchParamsObj.bugId);       
                         var searchParams = JSON.stringify(searchParamsObj);
                         console.log(searchParams);
                         $http.get(DEV_ENV + 'getbugdetails/' + searchParams)
                         .success(function(data, status, headers, config) {
                               console.log("This is in getBugDetails success function. Data : " + data);
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                               console.log("getBugDetails web call failed.");
                          });
/*                        var data = '{"bugId":"1678","bugName":"Test 5","projectName":"Project Motion","category":"Design Issue","priority":"High","teamMember":"John Doe","status":"New","comments":"This is a Dev Blocker."}';
                        callback(data);*/

                      }
      };    	
    }]);
    // Repository class which makes all the REST calls ends.


    // Service which returns an Bug Object starts.
    myApp.factory("bugObject", function(){
      return function(){
      this.bugId = "";
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
    
    // Service which returns an findBug Object starts.
    myApp.factory("findBugObject", function(){
      return function(){
      this.bugId = "";
      };
    });
   // Service which returns findBug Object ends.

   
   // addNewBugController starts.
   myApp.controller("addNewBugController", ['$scope', 'bugRepository', 'bugObject', function($scope, bugRepository, bugObject){
    
       //scope variables starts
       $scope.bugId = "";
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";
       //scope variables ends

       console.log("This is in Add new bug controller....");   
       $scope.addNewBug = function(){
          //console.log("Adding new bug to the DB.");
          //$scope.hideInstruction();
 
          //Creating Bug object
          var newBugObject = new bugObject();
          newBugObject.bugId = $scope.bugId;
          newBugObject.bugName = $scope.bugName;
          newBugObject.projectName = $scope.projectName;
          newBugObject.category = $scope.category;
          newBugObject.priority = $scope.priority;
          newBugObject.teamMember = $scope.teamMember;
          newBugObject.status = $scope.status;
          newBugObject.comments = $scope.comments;

          //console.log(JSON.stringify(newBug));
          bugRepository.addBug(newBugObject,function(data){
            console.log("This is in the callback function.");
            console.log(data);
          });
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
  myApp.controller("bugListController", ['$scope', 'bugRepository', function($scope, bugRepository){
    
     $scope.showInstructions = false;
     
     bugRepository.getBugList(function(resultsBack){
        $scope.issueList = resultsBack;
    });
    
    /*$scope.updateBug = function(bugId){
    	console.log("The selected BugId is:::::::::::::  "+ bugId);
      }*/
  }]);
  // bugListController ends.


  // updateBugcontroller starts
  myApp.controller("updateBugController", ['$scope','$stateParams', 'bugObject', 'findBugObject', 'bugRepository', function($scope, $stateParams, bugObject, findBugObject, bugRepository){
    
    //console.log("Bug Id for which the details are requested:  "+ $stateParams.bugId);
	var searchParamsObj = new findBugObject();
	searchParamsObj.bugId = $stateParams.bugId;
	
    bugRepository.getBugDetails(searchParamsObj, function(resultsBack){
    	
       var data = resultsBack;
       //Setting the bug details received form the server to the view.
       $scope.bugId = data.bugId;
       $scope.bugName = data.bugName;
       $scope.projectName = data.projectName;
       $scope.category = data.category;
       $scope.priority = data.priority;
       $scope.teamMember = data.teamMember;
       $scope.status = data.status;
       $scope.comments = data.comments;
    });
    $scope.updateBug = function(){
          //console.log("Adding new bug to the DB.");
          //$scope.hideInstruction();
 
          //Creating Bug object
          var bug = new bugObject();
          bug.bugId = $scope.bugId;
          bug.bugName = $scope.bugName;
          bug.projectName = $scope.projectName;
          bug.category = $scope.category;
          bug.priority = $scope.priority;
          bug.teamMember = $scope.teamMember;
          bug.status = $scope.status;
          bug.comments = $scope.comments;
          
          //console.log(JSON.stringify(newBug));
          bugRepository.updateBug(bug, function(data){
            console.log("This is in the updatebug callback function.");
            //console.log(data);
          });
      };
      
  }]);
  // updateBugcontroller ends.

  // userOptionsController starts.
  myApp.controller("userOptionsController", ['$scope', function($scope){

    console.log("THis is in userOptionsController.");

    $scope.val1 = "true";
    $scope.val2 = "false";

    $scope.disableUserOptions = function(){
      console.log("Admin options clicked");
      $scope.val1 = "true";
      $scope.val2 = "false";
    };

    $scope.disableAdminOptions = function(){
      console.log("User options clicked");
      $scope.val1 = "false";
      $scope.val2 = "true";
    };

  }]);
  // userOptionsController ends.
  
  // adminOptionsController starts.
  myApp.controller("adminOptionsController", ['$scope', function($scope){

	    console.log("This is in adminOptionsController.");

	  }]);
  // adminOptionsController ends.


})();