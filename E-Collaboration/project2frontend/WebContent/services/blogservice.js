/**
* BlogService
*/
app.factory('BlogService',function($http){
	var blogService={}

	blogService.addBlog=function(blog){
		console.log('in blogservice')
		return $http.post("http://localhost:9090/project2middleware/addblogpost",blog)
		
	}
	blogService.blogsApproved=function(){
		return $http.get("http://localhost:9090/project2middleware/blogsapproved")
	}
	blogService.blogsWaitingForApproval=function(){
		
		return $http.get("http://localhost:9090/project2middleware/blogswaitingforapproval")
	}
	blogService.getBlogPost=function(id){
		return $http.get("http://localhost:9090/project2middleware/getblogpost/"+id)
	}
	blogService.updateApprovalStatus=function(blogPost){
		//if admin approves the blogPost, blogPost.approved=?
		//if admin rejects the blogpost, blogPost.approved=?
		console.log("inside blogservis")
		return $http.put("http://localhost:9090/project2middleware/updateapprovalstatus",blogPost)
	}
	blogService.hasUserLikedBlog=function(blogpostId){
		console.log('inside blogserviceeee')
		return $http.get("http://localhost:9090/project2middleware/hasuserlikedblog/"+blogpostId)
	}
	blogService.updateBlogPostLikes=function(blogPostId){
		return $http.put("http://localhost:9090/project2middleware/updateblogpostlikes/"+blogPostId)
	}
	//blogpost id and commenttxt
	//id is the id of BlogPost
	//commentTxt=Good,Well Explained
	//id=27
	//http://........../addcomment/Good,Well Explained/27
	blogService.addComment=function(commentTxt,id){
		return $http.post("http://localhost:9090/project2middleware/addcomment/"+commentTxt+"/"+id)
	}
	blogService.getAllBlogComments=function(blogPostId){
		return $http.get("http://localhost:9090/project2middleware/getblogcomments/"+blogPostId)
	}
	return blogService;
})