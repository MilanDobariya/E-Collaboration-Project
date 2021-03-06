package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.Job;
import com.niit.model.User;

@RestController
public class JobController {
@Autowired
private JobDao jobDao;
@Autowired
private UserDao userDao;
@RequestMapping(value="/addjob",method=RequestMethod.POST)
public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){
	//Authentication and Authorization
	//Authentication - who u are?
	//Authorization - role?
	//String email="milan@gmail.com";
	String email=(String)session.getAttribute("email");
	if(email==null){//not loggedf in
		ErrorClazz errorClazz=new ErrorClazz(7, "Unauthorized access.. please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		
	}
	//If email != null,user is an authentication user
	//check user is authorized to insert a new job or not. check the role
	//check if the logged in user is admin or not
	User user=userDao.getUser(email);
	if(!user.getRole().equals("ADMIN")){
		ErrorClazz errorClazz=new ErrorClazz(8, "Access Denied..");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		
	}
	//Authentiacated and authorized to insert new job details

try{
	job.setPostdOn(new Date());
	job.setActive(true);
	jobDao.saveJob(job);
	return new ResponseEntity<Void>(HttpStatus.OK);
}catch(Exception e){
	ErrorClazz errorClazz=new ErrorClazz(4, "Unable to insert job details.."+e.getMessage());
	return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
}
}
@RequestMapping(value="/activejobs",method=RequestMethod.GET)//sele
public ResponseEntity<?> getActiveJobs(HttpSession session){//authentication
	String email=(String)session.getAttribute("email");
	if(email==null){
		ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access.. please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	List<Job> activeJobs=jobDao.getActiveJobs();
	return new ResponseEntity<List<Job>>(activeJobs,HttpStatus.OK);
}
@RequestMapping(value="/inactivejobs",method=RequestMethod.GET)//select * from job where active = false
public ResponseEntity<?> getInActiveJobs(HttpSession session){//authenctication and authorization
	String email=(String)session.getAttribute("email");
	if(email==null){
		ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access.. please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.OK);
	}
//	String email="milan@gmail.com";
	User user=userDao.getUser(email);
	if(!user.getRole().equals("ADMIN")){
		ErrorClazz errorClazz=new ErrorClazz(8,"Access Denide..");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	List<Job> inActiveJobs=jobDao.getInActiveJobs();
	return new ResponseEntity<List<Job>>(inActiveJobs,HttpStatus.OK);
}
@RequestMapping(value="/updatejob",method=RequestMethod.PUT)
public ResponseEntity<?> updateJob(HttpSession session, @RequestBody Job job){//authenctication and authorization
	String email=(String)session.getAttribute("email");
	if(email==null){
		ErrorClazz errorClazz=new ErrorClazz(7,"Unauthorized access.. please login");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.OK);
	}
//	String email="milan@gmail.com";
	User user=userDao.getUser(email);
	if(!user.getRole().equals("ADMIN")){
		ErrorClazz errorClazz=new ErrorClazz(8,"Access Denide..");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	jobDao.updateJob(job);
	return new ResponseEntity<Void>(HttpStatus.OK);
}
}