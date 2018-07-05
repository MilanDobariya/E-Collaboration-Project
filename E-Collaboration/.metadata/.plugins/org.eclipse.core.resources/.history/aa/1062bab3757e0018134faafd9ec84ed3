/**
 * 
 */
app.filter('reverse', function(){
	return function(items){
		console.log('in chat service 1')
		return items.slice().reverse();
		
	};
});

	app.directive('ngFocus',function(){
		console.log('in chat service 2')
		return function(scope,element,attrs){
			element.bind('click',function(){
				$('.'+attrs.ngFocus)[0].focus();
			});
		};
	});

app.factory('ChatService',function($rootScope){
	var socket=new SockJS("/project2middleware/chatmodule")
	var stompClient=Stomp.over(socket);
	console.log(stompClient)
	
	stompClient.connect('','',function(frame){
		alert('in connect in service')
		console.log('in chat service')
		$rootScope.$broadcast('sockConnected',frame)
	});
	return{
		stompClient:stompClient
	}
})