package com.niit.dao;

import java.util.List;

import com.niit.model.Job;

public interface JobDao {
	void saveJob(Job job);
	List<Job> getActiveJobs();//active is true, for other roles
	//for admin to return all jobs
	List<Job> getInActiveJobs();
	void updateJob(Job job);
}
