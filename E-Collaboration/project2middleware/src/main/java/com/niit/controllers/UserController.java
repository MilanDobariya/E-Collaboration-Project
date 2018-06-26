package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@RestController
public class UserController {
	public UserController() {
		System.out.println("userController bean is created");
	}
	
@Autowired
private UserDao userDao;
@RequestMapping(value="/register",method=RequestMethod.POST)
public ResponseEntity<?> registration(@RequestBody User user){
	try{
		//if Email is not unique
		if(!userDao.isEmailUnique(user.getEmail())){
			ErrorClazz errorClazz=new ErrorClazz(2, "Email id already exists.. so enter different email id");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.CONFLICT);
		}
		userDao.registration(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		//response.status=500, response.data is errorClazz object in JSon format
	}catch(Exception e){  //exceptions when email is null/ duplicate, firstname is null, if password is null, DBConnection error
		ErrorClazz errorClazz=new ErrorClazz(1,"Unable to regstration student"+ e.getMessage());
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		//response.status=500, response.data is errorClazz in json format
		//response.data={errorCode:1,message:"unable to register user details"}
	}
}

@RequestMapping(value="/login",method=RequestMethod.POST)
//authentication
public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
	User validUser=userDao.login(user);
	if(validUser==null){
		ErrorClazz errorClazz=new ErrorClazz(5, "Email/password is incorrect.. please enter valid credential");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.NOT_FOUND);
	}
	else
	{
		//session attribute- "email"
		//value of attribute - email address
		session.setAttribute("email", user.getEmail());
		validUser.setOnline(true);
		userDao.updateUser(validUser);//update online_status=true 
		return new ResponseEntity<User>(validUser,HttpStatus.OK);
	}
}

@RequestMapping(value="/logout",method=RequestMethod.PUT)
public ResponseEntity<?> logout(HttpSession session){
	//update online status of the user to false?
	//update user set online_status=false where email=?
	
	String email=(String)session.getAttribute("email");
	if(email!=null){
	System.out.println("logout User email "+email);
	System.out.println("Session Id "+session.getId());
	//update online status to false
	User user=userDao.getUser(email);
	user.setOnline(false);
	userDao.updateUser(user);
	
	//remove session attribute email and invalidete the session
	session.removeAttribute("email");
	session.invalidate();
	return new ResponseEntity<Void>(HttpStatus.OK);
	}
	else
	{
		ErrorClazz errorClazz=new ErrorClazz(6, "please login..");
		//in frontend
		//$scope.error=response.data
		//$scope.error={errorCode:6,message:"please login.."}
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	}

@RequestMapping(value="/update",method=RequestMethod.PUT)
public ResponseEntity<?> updateUserProfile(@RequestBody User user,HttpSession session){
	//check for Authentication
	String email=(String)session.getAttribute("email");
	if(email==null){//not logged in
		//what is the httpResponse data and status code?
		ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access.. please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	
	}
	userDao.updateUser(user);
	return new ResponseEntity<User>(user,HttpStatus.OK);
}
}
