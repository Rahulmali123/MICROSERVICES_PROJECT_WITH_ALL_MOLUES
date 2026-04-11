package com.embarx.jobms.Job;

import java.util.List;

public interface JobService 
{
	
	public void createJob(Job job);

	public List<Job> findAll();
	
	public Job findByJobId(Long id);
	
	 boolean deleteById(Long id);
	 
	 boolean updateJob(Job job, Long id);
	

}
