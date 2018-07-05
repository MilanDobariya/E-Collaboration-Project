/**
 * 
 */
app.controller('ChatCtrl',function($rootScope,$scope,ChatService){
	$scope.stompClient=ChatService.stompClient
	$scope.users=[];
	$scope.chats=[];
	
	$scope.$on('sockConnected',function(event,frame){
		alert('Successfully connected with WebSocket')
		$scope.userName=$rootScope.loggedInUser.firstname
		alert($scope.userName+'joined the chat room')
		
		
		$scope.stompClient.subscribe("/app/join/"+$scope.userName,function(message){
			console.log(message.body)
			alert(message.body)
			
			$scope.users=JSON.parse(message.body)
			$scope.$apply();
		})
		
		
		
		$scope.stompClient.subscribe("/topic/join",function(message){
			console.log('in chat')
			user=JSON.parse(message.body);
			//console.log(user)
			if(user != $scope.userName && $.inArray(user,$scope.user) == -1){
				$scope.addUser(user);
				$scope.latestUser = user;
				$scope.$apply();
				alert($scope.latestUser + 'has joined the chat')
				$('#joinedChat').fadeIn(500).delay(10000).fadeOut(500);
	
			}
			
		})
	})
	
	$scope.addUser=function(user){
		$scope.users.push(user)
		$scope.$apply()
	}
	
	$scope.sendMessage=function(chat){
		chat.from=$scope.userName
		$scope.stompClient.send("/app/chat",{},JSON.stringify(chat))
		console.log('in chat controller')
		$rootScope.$broadcast('sendingChat',chat)
		$scope.chat.message=''
	}
	
	$scope.$on('sockConnected',function(event,frame){
		$scope.userName=$rootScope.loggedInUser.firstname;
		
	$scope.stompClient.subscribe("/queue/chats",function(message){
		console.log('in chat controller 1')
		alert('message' + message.body)
		$scope.processIncomingMessage(message,true)
	
	});
	
	$scope.stompClient.subscribe("/queue/chats/"+$scope.userName,function(message){
		alert('message is' + message.body)
		console.log('in chat controller 2')
		$scope.processIncomingMessage(message,false)
	})
	})
	
	$scope.processIncomingMessage=function(message,broadcast){
		message=JSON.parse(message.body)
		message.direction='incoming'
			console.log('in chat controller 5')
		if(message.from!=$scope.userName)
		{
			$scope.addChat(message);
			$scope.$apply()
		}
	}
	
	$scope.addChat=function(chat){
		console.log('in chat controller 3')
		$scope.chats.push(chat)
	}
	
	$scope.$on('sendingChat',function(event,sentChat){
		chat=angular.copy(sentChat)
		console.log('in chat controller 4')
		chat.from='Me'
		chat.direction='outgoing'
		$scope.addChat(chat)
	})
	
	})