/**
 * 
 */
app.factory('FriendService',function($http){
	var friendService={}
	friendService.getAllSuggestedUsers=function(){
		console.log('in freind service')
		return $http.get("http://localhost:9090/project2middleware/suggestedusers")
	}
	
	friendService.addFriend=function(toId){
	return $http.post("http://localhost:9090/project2middleware/addfriend",toId)
	}

	friendService.getPendingRequests=function(){
	return $http.get("http://localhost:9090/project2middleware/pendingRequests")
	}

/*friendService.getPendingRequests=function(toId){
	return $http.get("http://localhost:8083/project2middleware/pendingRequests")
}*/

friendService.acceptRequest=function(request){
	return $http.put("http://localhost:9090/project2middleware/acceptrequest",request);
}

friendService.deleteRequest=function(request){
	return $http.put("http://localhost:9090/project2middleware/deleterequest",request)
}

friendService.getAllFriends=function(){
	return $http.get("http://localhost:9090/project2middleware/friends")
}

	return friendService;
})
	
	
	