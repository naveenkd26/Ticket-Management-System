(function(){
    console.log("In the closire function Naveen kumar......");
	var myApp = angular.module('ticketSystemApp', ["ui.router"]);
     
     //Routing for the nested views. - Starts
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
          })
        .state('userOptions.addBug', {
              url: "/addBug",
              templateUrl: "addBug.html",
          })
        .state('userOptions.searchBug', {
              url: "/searchBug",
              templateUrl: "route1.list.html",
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
     //Routing for the nested views. - Ends

     //bugMenuController - Starts
     myApp.controller("bugMenuController", ['$scope','$http', 'bugRepository', function($scope, $http, bugRepository){

       /*$http.get('http://localhost:8080/TicketSystem/rest/getData/greetMessage')
       .success(function(data) {
            console.log(data);
        }); */

       //scope variables
       $scope.bugId = "naveen";
       $scope.bugName = "";
       $scope.projectName = "";
       $scope.category = "";
       $scope.priority = "";
       $scope.teamMember = "";
       $scope.status = "";
       $scope.comments = "";

       console.log("In the bug Menu controller");
       $scope.showInstructions = true;
       
       //console.log("Before");
       //bugRepository.callWebCall1("Naveen", "Temp", function(){
       //console.log("This is in call back function");
       //});
       //console.log("After");

       //Handling bugMenu events.
       $scope.listBugs = function(){
          console.log("List Bugs button clicked");
          $scope.hideInstruction();
       };
       $scope.addBug = function(){
          console.log("Add Bug button clicked");
          console.log("Bug Name:  " + $scope.bugName);
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
     //bugMenuController - Ends

     //Writing services
     //Nice article about callback func
     //http://javascriptissexy.com/understand-javascript-callback-functions-and-use-them/
     myApp.factory("bugRepository", function(){
	 
      return { 
    	   addBug: function(var1, var2, callback){
                        console.log("I am in callWebCall1. and parameters are: " + var1 + " " + var2);
                        callback(); 
                         },
         callWebCall2: function(var3){
                         console.log("IX am in callWebCall2and the parameter is: " + var3);
                      } 
      };
    	
    });

})();