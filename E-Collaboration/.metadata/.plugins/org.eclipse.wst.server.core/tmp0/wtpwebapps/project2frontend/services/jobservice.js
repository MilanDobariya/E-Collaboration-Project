/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={}
	
	jobService.addJob=function(job){
		console.log('in jobservice')
		return $http.post("http://localhost:9090/project2middleware/addjob",job)
	}
	
	jobService.getActiveJobs=function(){
		return $http.get("http://localhost:9090/project2middleware/activejobs")
	}
	
	jobService.getInActiveJobs=function(){
		return $http.get("http://localhost:9090/project2middleware/inactivejobs")
	}
	jobService.updateActiveStatus=function(job){
		return $http.put("http://localhost:9090/project2middleware/updatejob",job)
	}
	return jobService;
})
