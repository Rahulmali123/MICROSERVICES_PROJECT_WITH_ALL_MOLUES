package com.embarx.jobms.Job;

import com.embarx.jobms.Job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService 
{
	
	public void createJob(Job job);

	public List<JobWithCompanyDTO> findAll();

	JobWithCompanyDTO findJobWithCompanyById(Long id);
	
	 boolean deleteById(Long id);
	 
	 boolean updateJob(Job job, Long id);
	

}
