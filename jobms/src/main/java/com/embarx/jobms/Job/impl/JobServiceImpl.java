package com.embarx.jobms.Job.impl;

import com.embarx.jobms.Job.Job;
import com.embarx.jobms.Job.JobRepo;
import com.embarx.jobms.Job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class JobServiceImpl implements JobService {

	JobRepo jobRepo;

	public JobServiceImpl(JobRepo jobRepo) {
		super();
		this.jobRepo = jobRepo;
	}

	@Override
	public void createJob(Job job)
	{
		 this.jobRepo.save(job);
	}
	
	@Override
	public boolean updateJob(Job job, Long id) {
	    Optional<Job> jobOptional = jobRepo.findById(id);
	    if (jobOptional.isPresent()) {
	        Job existingJob = jobOptional.get();

	        existingJob.setTitle(job.getTitle());
	        existingJob.setDescription(job.getDescription());
	        existingJob.setMinSalary(job.getMinSalary());
	        existingJob.setMaxSalary(job.getMaxSalary());
	        existingJob.setLocation(job.getLocation());

	        jobRepo.save(existingJob);
	        return true;
	    }
	    return false;
	}

	@Override
	public List<Job> findAll() {
		return jobRepo.findAll();
	}

	@Override
	public Job findByJobId(Long id) {
		Job job = jobRepo.findById(id).orElse(null);
		return job;
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			jobRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
