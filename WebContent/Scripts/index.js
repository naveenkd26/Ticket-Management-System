(function(){

   console.log("Angular compiling starts.");
	var myApp = angular.module('ticketSystemApp', ["ui.router"]);
		
	var DEV_ENV = 'http://localhost:8080/TicketSystem/rest/bugAPI/';
	var PROD_ENV = 'http://naveenjavaapplications-ticketsystem.rhcloud.com/TicketSystem/rest/bugAPI/';
	var ENV = DEV_ENV;
	
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");
		$(".spinner").hide();
	});
	
	$('#exampleModal').on('show.bs.modal', function (event) {
		  var modal = $(this);
		  var button = $(event.relatedTarget);
		  var statusMsg = button.data('status');
		  modal.find('.modal-title').text('Thanks for using Ticket Management System.');
		  modal.find('.modal-message').text(statusMsg);
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
               
    		             //Showing the spinner before the AJAX call.
	                     $('.spinner').show();
                        console.log("This is in addBug bugRepo function.");
                        console.log("Below are the new bug details.");
                        var bugDetails = JSON.stringify(newBugObject);
                        console.log(bugDetails);
                        $http.post(ENV + 'addbug/' + bugDetails)
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                     	       $('.spinner').hide();  
                        	   //console.log("This is in rest call success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                       	       $('.spinner').hide();
                               console.log("addBug web call failed.");
                               callback("Error occured");
                          });
                  },
        updateBug: function(newDetailsObj, callback){
                        
                        //Showing the spinner before the AJAX call.
                        $('.spinner').show();
        	            console.log("This is in update bugRepo function.");
                        console.log("Below are the update bug details.");
                        var modifiedDetails = JSON.stringify(newDetailsObj);
                        console.log(modifiedDetails);
                        $http.put(ENV + 'updatebug/' + modifiedDetails)
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                     	       $('.spinner').hide();  
                        	   //console.log("This is in update bug success function");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                               console.log("updateBug web call failed.");
                               callback("Error occured");
                          });
                  },

         getBugList: function(callback){
        	 
                         $('#temp').click();
        	             //Showing the spinner before the AJAX call.
        	             $('.spinner').show();
        	             console.log("This is in getBugLis bugRepo function.");
                         console.log(ENV);
                        $http.get(ENV + 'getbuglist')
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                        	   console.log("Loaded bug list Sucessfully.");
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                       	       //Hiding the spinner once we receive the response. 
                       	       $('.spinner').hide();
                               callback("Error occured"); 
                          });         
                      }, 

        getBugDetails: function(searchParamsObj, callback){

                         //Showing the spinner before the AJAX call.
                         $('.spinner').show();
                         console.log("This is in getBugDetails bugRepo function. BugId:  " + searchParamsObj.bugId);       
                         var searchParams = JSON.stringify(searchParamsObj);
                         console.log(searchParams);
                         $http.get(ENV + 'getbugdetails/' + searchParams)
                         .success(function(data, status, headers, config) {
                               console.log("This is in getBugDetails success function. Data : " + data);
                        	   //Hiding the spinner once we receive the response. 
                       	       $('.spinner').hide();
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                               console.log("getBugDetails web call failed.");
                               callback("Error occured");
                          });
                      },
                      
         getAvailableBugId: function(callback){

                          //Showing the spinner before the AJAX call.
                          $('.spinner').show();
        	              console.log("This is in getAvailableBugId bugRepo function.");       
                          $http.get(ENV + 'getNextAvailableBugId')
                          .success(function(data, status, headers, config) {
                                console.log("This is in getAvailableBugId success function. Data : " + data);
                         	    //Hiding the spinner once we receive the response. 
                        	    $('.spinner').hide();
                                callback(data); 
                           }).error(function(data, status, headers, config) {
                        	    //Hiding the spinner once we receive the response. 
                           	    $('.spinner').hide();
                                console.log("getAvailableBugId web call failed.");
                                callback("Error occured");
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
    
	     bugRepository.getAvailableBugId(function(resultsBack){
             if(resultsBack.toString() == "Error occured"){
           	  $('#fetchBugIdFailed').click();            	  
             }else{
    	         $scope.bugId = resultsBack.toString();   
             }
	     });
	     
	   //scope variables starts
       //$scope.bugId = "";
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
              if(data.toString() == "success"){
            	  
            	  $('#successMsg').click();
            	  $scope.resetBugDetails();
         	      //Loading the next available Bug Id once the new bug is added successfully.
            	  bugRepository.getAvailableBugId(function(resultsBack){
                     if(resultsBack.toString() == "Error occured"){
                   	  $('#fetchBugIdFailed').click();            	  
                     }else{
            	         $scope.bugId = resultsBack.toString();   
                     }
                     
         	     });  
              }else{
            	  $('#failureMsg').click();   
              }
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
    	 
    console.log("Response received from getList:"+resultsBack);	 
   	 //Checking whether the response from the server is success. 
     if(resultsBack.toString() != "Error occured"){
          $scope.issueList = resultsBack;      		  
	  }else{
    	  $('#failureMsg').click();     		  
	  }

    });
    
    /*$scope.updateBug = function(bugId){
    	console.log("The selected BugId is:::::::::::::  "+ bugId);
      }*/
  }]);
  // bugListController ends.


  // updateBugcontroller starts
  myApp.controller("updateBugController", ['$scope','$stateParams', 'bugObject', 'findBugObject', 'bugRepository', function($scope, $stateParams, bugObject, findBugObject, bugRepository){
    
    $scope.updateOptionDisabled = false;  
	//console.log("Bug Id for which the details are requested:  "+ $stateParams.bugId);
	//Getting the id of the bug for which the update option is clicked.
	var searchParamsObj = new findBugObject();
	searchParamsObj.bugId = $stateParams.bugId;
	
    bugRepository.getBugDetails(searchParamsObj, function(resultsBack){
    
      //Checking whether the response from the server is success. 
      if(resultsBack.toString() != "Error occured"){

    	  //Setting the bug details received form the server to the view.
          $scope.bugId = resultsBack.bugId;
          $scope.bugName = resultsBack.bugName;
          $scope.projectName = resultsBack.projectName;
          $scope.category = resultsBack.category;
          $scope.priority = resultsBack.priority;
          $scope.teamMember = resultsBack.teamMember;
          $scope.status = resultsBack.status;
          $scope.comments = resultsBack.comments;     
          
   	  }else{
       	  $('#loadFailureMsg').click();     		  
   	  }
    	
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
        	  if(data.toString() == "1"){
            	  $('#successMsg').click();
            	  $scope.disableUpdateOption();
        	  }else{
            	  $('#failureMsg').click();     		  
        	  }
            //console.log(data);
          });
      };
      
      $scope.disableUpdateOption = function(){

    	  $scope.updateOptionDisabled = true;    		  

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