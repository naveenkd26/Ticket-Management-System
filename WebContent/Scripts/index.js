(function(){

    //console.log("Angular compiling starts.");
	var myApp = angular.module('ticketSystemApp', ["ui.router"]);	
	var DEV_ENV = 'http://localhost:8080/TicketSystem/rest/bugAPI/';
	var PROD_ENV = 'http://naveenjavaapps-ticketsystem.rhcloud.com/TicketSystem/rest/bugAPI/';
	var ENV = DEV_ENV;
	
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");
		$(".spinner").hide();
	});
	     
     //Routing for the nested views starts.
     myApp.config(function($stateProvider, $urlRouterProvider){

      $urlRouterProvider.otherwise("/userOptions");

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
              controller: "updateBugController"
              /* onEnter: function(){
                 console.log("This is in updateBug onEnter state.");
              }*/
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

       //console.log("In the bug Menu controller");    	 
       //Handling bugMenu events.
       $scope.listBugs = function(){
          //$scope.hideInstruction();
       };
       
       //Function which gets triggered when AddBug option is selected. 
       $scope.addBug = function(){        
          //$scope.hideInstruction();
       };

       $scope.searchBug = function(){
         //$scope.hideInstruction();
       };

       //Function to hide the instructions in the bugMenu view.
       $scope.hideInstruction = function hideInstructions(){
        $scope.showInstructions  = false;
        };

     }]);
    //bugMenuController ends.


     // Repository class which makes all the REST calls starts.
     myApp.factory("bugRepository", ['$http', function($http){
	 
      return { 
    	   addBug: function(newBugObject, callback){
               
    		             //Showing the spinner before the AJAX call.
	                     $('.spinner').show();
                         var bugDetails = JSON.stringify(newBugObject);
                         //console.log(bugDetails);
                        $http.post(ENV + 'addbug/' + bugDetails)
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                     	       $('.spinner').hide();  
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                       	       $('.spinner').hide();
                               //console.log("addBug web call failed.");
                               callback("Error occured");
                          });
                  },
        updateBug: function(newDetailsObj, callback){
                        
                        //Showing the spinner before the AJAX call.
                        $('.spinner').show();
                        var modifiedDetails = JSON.stringify(newDetailsObj);
                        //console.log(modifiedDetails);
                        $http.put(ENV + 'updatebug/' + modifiedDetails)
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                     	       $('.spinner').hide();  
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                               //console.log("updateBug web call failed.");
                               callback("Error occured");
                          });
                  },

         getBugList: function(callback){
        	 
        	             //Showing the spinner before the AJAX call.
        	             $('.spinner').show();
                         $http.get(ENV + 'getbuglist')
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                       	       //Hiding the spinner once we receive the response. 
                               callback("Error occured");
                       	       $('.spinner').hide();
                          });         
                      }, 

        getBugDetails: function(searchParamsObj, callback){

                         //Showing the spinner before the AJAX call.
                         $('.spinner').show();  
                         var searchParams = JSON.stringify(searchParamsObj);
                         //console.log(searchParams);
                         $http.get(ENV + 'getbugdetails/' + searchParams)
                         .success(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                       	       $('.spinner').hide();
                               callback(data); 
                          }).error(function(data, status, headers, config) {
                        	   //Hiding the spinner once we receive the response. 
                        	   $('.spinner').hide();
                               callback("Error occured");
                          });
                      },
                      
         getAddBugInfo: function(callback){

                          //Showing the spinner before the AJAX call.
                          $('.spinner').show();
                          $http.get(ENV + 'getAddBugInfo')
                          .success(function(data, status, headers, config) {
                         	    //Hiding the spinner once we receive the response. 
                        	    $('.spinner').hide();
                                callback(data); 
                           }).error(function(data, status, headers, config) {
                         	    //Hiding the spinner once we receive the response. 
                         	    $('.spinner').hide();
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
                       },
                       
          addNewAdminOption: function(newAdminOption, callback){

                           //Showing the spinner before the AJAX call.
                           $('.spinner').show();
                           $http.put(ENV + 'addAdminOption/' + newAdminOption)
                           .success(function(data, status, headers, config) {
                          	     //Hiding the spinner once we receive the response. 
                         	     $('.spinner').hide();
                                 callback(data); 
                            }).error(function(data, status, headers, config) {
                         	    //Hiding the spinner once we receive the response. 
                            	$('.spinner').hide();
                                callback("Error occured");
                            });
                        }              
      };    	
    }]);
    // Repository class which makes all the REST calls ends. getAddBugInfo


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
    
  	   //Getting the next abailable Bug Id and drop down details about Project/Team member/Priority/Status.  
	   bugRepository.getAddBugInfo(function(resultsBack){
             if(resultsBack.toString() == "Error occured"){
           	  $('#fetchBugIdFailed').click();            	  
             }else{     	 
            	 $scope.bugId = resultsBack.nextAvailBugId.toString();
    	         $scope.projects = resultsBack.project;
    	         $scope.categorys = resultsBack.category;
    	         $scope.prioritys = resultsBack.priority;
    	         $scope.teamMembers = resultsBack.teamMember;
    	         $scope.statuses = resultsBack.status;
             }           
	     });
	     
	   //scope variables starts
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";
       $scope.addBtnDisabled = true;
       //scope variables ends
       

       //Handling AddBug button event.
       $scope.addNewBug = function(){
 
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

          bugRepository.addBug(newBugObject,function(data){
              if(data.toString() == "success"){
            	  
            	  $('#successMsg').click();
            	  $scope.resetBugDetails();
         	      //Loading the next available Bug Id once the new bug is added successfully.
            	  bugRepository.getAvailableBugId(function(resultsBack){
                     if(resultsBack.toString() == "Error occured"){
                   	  $('#fetchBugIdFailed').click();            	  
                     }else{
            	         $scope.bugId = parseInt(resultsBack);   
                     }                
         	     });  
              }else{
            	  $('#failureMsg').click();   
              }
          });
     };

     //Reset bug details.
     $scope.resetBugDetails = function(){
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";     
       $scope.addBtnDisabled = true;
     };
     
     //Validating for empty fields.
     $scope.changeEvent = function(){
  	   if(($scope.bugName == "" || $scope.bugName == undefined) || $scope.projectName == "" || $scope.category == "" || $scope.priority == "" || $scope.teamMember == "" || $scope.status == ""){
  	  	   $scope.addBtnDisabled = true;
   	   }else{
  		   $scope.addBtnDisabled = false;
  	   }    	   
     }

   }]);
  // addNewBugController ends.


  // bugListController starts.
  myApp.controller("bugListController", ['$scope', 'bugRepository', function($scope, bugRepository){
    
     $scope.showInstructions = false;
     
     bugRepository.getBugList(function(resultsBack){
    	 
   	   //Checking whether the response from the server is success. 
       if(resultsBack.toString() != "Error occured"){
            $scope.issueList = resultsBack;      		  
	    }else{
      	  $('#failureMsg').click();
	    }
     });
    
  }]);
  // bugListController ends.


  // updateBugcontroller starts
  myApp.controller("updateBugController", ['$scope','$stateParams', 'bugObject', 'findBugObject', 'bugRepository', function($scope, $stateParams, bugObject, findBugObject, bugRepository){
    
    $scope.updateOptionDisabled = false;  
	//Getting the id of the bug for which the update option is clicked.
	var searchParamsObj = new findBugObject();
	searchParamsObj.bugId = $stateParams.bugId;
	
    bugRepository.getBugDetails(searchParamsObj, function(resultsBack){
    
      //Checking whether the response from the server is success. 
      if(resultsBack.toString() != "Error occured"){

    	 //Setting the drop down select options to the view.
         $scope.projects = resultsBack.addBugInfo.project;
         $scope.categorys = resultsBack.addBugInfo.category;
         $scope.prioritys = resultsBack.addBugInfo.priority;
         $scope.teamMembers = resultsBack.addBugInfo.teamMember;
         $scope.statuses = resultsBack.addBugInfo.status;
    	           
   	     //Setting the bug details received form the server to the view.
         $scope.bugId = resultsBack.bugDetails[0].bugId;
         $scope.bugName = resultsBack.bugDetails[0].bugName;
         $scope.projectName = resultsBack.bugDetails[0].projectName;
         $scope.category = resultsBack.bugDetails[0].category;
         $scope.priority = resultsBack.bugDetails[0].priority;
         $scope.teamMember = resultsBack.bugDetails[0].teamMember;
         $scope.status = resultsBack.bugDetails[0].status;
         $scope.comments = resultsBack.bugDetails[0].comments;
         
         //Saving the Bug details to check and show/hide UpdateBug option.
         $scope.oldProjectName = $scope.projectName;
         $scope.oldCategory = $scope.category;
         $scope.oldPriority =  $scope.priority;
         $scope.oldTeamMember = $scope.teamMember;
         $scope.oldStatus = $scope.status;
         $scope.oldComments = $scope.comments;
         $scope.disableUpdateBtn = true;
             
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
          
          //Handling UpdateBug button event.
          bugRepository.updateBug(bug, function(data){
        	  if(data.toString() == "1"){
        		  $scope.disableUpdateBtn = true;
            	  $('#successMsg').click();
            	  $scope.disableUpdateBtn = true;
        	  }else{
            	  $('#failureMsg').click();     		  
        	  }
          });
      };
      
    
      //Validating for empty fields.
      $scope.changeEvent = function(){

    	  if($scope.projectName == $scope.oldProjectName && $scope.category == $scope.oldCategory && $scope.priority == $scope.oldPriority && $scope.teamMember == $scope.oldTeamMember && $scope.status == $scope.oldStatus && $scope.comments == $scope.oldComments){
   	  	       $scope.disableUpdateBtn = true;
     	    }else{
   		       $scope.disableUpdateBtn = false;
   		      //console.log(($scope.projectName == $scope.oldProjectName)  + "  " + ($scope.category == $scope.oldCategory)   + "  " + ($scope.priority == $scope.oldPriority)   + "  " + ($scope.teamMember == $scope.oldTeamMember)   + "  " + ($scope.status == $scope.oldStatus)   + "  " +  ($scope.comments == $scope.oldComments));
   	      }    	   
      }
      
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
  myApp.controller("adminOptionsController", ['$scope', 'bugRepository', function($scope, bugRepository){

	  $scope.newProject = "";  
	  $scope.newCategory = ""; 
	  $scope.newPriority = ""; 
	  $scope.newMember = ""; 
	  $scope.newStatus = ""; 

	       //Handling Button events in Admin Section.
	       $scope.addProject = function(){
                   $scope.addAdminOption("project", $scope.newProject);
             	   $scope.newProject = "";
	       };
	       $scope.addCategory = function(){
                   $scope.addAdminOption("category", $scope.newCategory);
             	   $scope.newCategory = ""; 
           };
           $scope.addPriority = function(){
        	       $scope.addAdminOption("priority", $scope.newPriority);
        	 	   $scope.newPriority = ""; 
            };
           $scope.addTeamMember = function(){
                   $scope.addAdminOption("teamMember", $scope.newMember);
             	   $scope.newMember = ""; 
            };
           $scope.addStatus = function(){
                   $scope.addAdminOption("status", $scope.newStatus);
             	   $scope.newStatus = ""; 
            };

            
	       //Making AJAX call to save the new option to mongoDB.
            $scope.addAdminOption = function(selectedOption, newValue){
	    	       	   
	    	   var newOption = "{\"" + selectedOption + "\":\"" + newValue +"\"}";
	           bugRepository.addNewAdminOption(newOption,function(data){
	               if(data.toString() == "success"){	             	  
	             	  $('#successMsg').click();
	               }else{
	             	  $('#failureMsg').click();   
	               }
	           });
	      };

	  }]);
  // adminOptionsController ends.

})();