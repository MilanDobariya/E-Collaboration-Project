/**
 * BlogInDetailCtrl
 * #/getblog/:id
 * eg, #/getblog/1
 */
app.controller('BlogInDetailCtrl',function($scope,$location,BlogService,$rootScope,$routeParams,$sce){
	var id=$routeParams.id//id of the blogpost which has to be views by the user
	BlogService.getBlogPost(id).then(function(response){
		//response.data -> blogpost object [select * from blogpost where id=?]
		$scope.blogPost=response.data
		$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
	},function(response){
		
		$scope.error=response.data
		if(response.status==401)
			$location.path('/login')
	})
	
	BlogService.hasUserLikedBlog(id).then(function(response){
		//response.data=select * from blogpostlikes where blogpost_id=? and user_email=?
		if(response.data='')
			$scope.isLiked=false//glyphicon color is black
			else
				$scope.isLiked=true//glyphicon color is blue
	},function(response){
		$scope.error=response.data
		if(response.status==401)
			$location.path('/login')
	})
	//approve button is clicked
	$scope.approved=function(blogPost){
		blogPost.approved=true
		console.log('in blogindetail')
		BlogService.updateApprovalStatus(blogPost).then(function(response){
			$location.path('/blogwaitingforapproval')
		},function(response){
		
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	//Reject button is clicked
	$scope.reject=function(blogPost){
		blogPost.approved=false
		BlogService.updateApprovalStatus(blogPost).then(function(response){
			console.log("inside blogindetail")
			$location.path('/blogwaitingforapproval')
		},function(response){
			console.log("not successfully in blogindetail")
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
	})
	}
	$scope.updateBlogPostLikes=function(blogPostId){
		BlogService.updateBlogPostLikes(blogPostId).then(function(response){
			//what is response.data?
			$scope.blogPost=response.data
			$scope.isLiked=!$scope.isLiked;
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.addComment=function(){
		//id is the id of the blogpost
		//commentTxt is the comment entered by the user
		BlogService.addComment($scope.commentTxt,id).then(function(response){
			alert('comment posted sucessfully')
			$scope.commentTxt=''
				getAllBlogComments()
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	function getAllBlogComments(){
		BlogService.getAllBlogComments(id).then(function(response){
			$scope.blogComments=response.data
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.showComments=function(){
		$scope.showCommentsIsClicked=!$scope.showCommentsIsClicked
	}
	getAllBlogComments()
	/*$scope.updateBlogPostLikes=function(blogPostId){
		BlogService.updateBlogPostLikes(blogPostId)
	}*/
})