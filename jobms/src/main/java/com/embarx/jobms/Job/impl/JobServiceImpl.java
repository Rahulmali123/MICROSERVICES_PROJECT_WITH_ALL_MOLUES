package com.embarx.jobms.Job.impl;

import com.embarx.jobms.Job.Job;
import com.embarx.jobms.Job.JobRepo;
import com.embarx.jobms.Job.JobService;
import com.embarx.jobms.Job.dto.JobWithCompanyDTO;
import com.embarx.jobms.Job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class JobServiceImpl implements JobService {

	JobRepo jobRepo;



	public JobServiceImpl(JobRepo jobRepo) {
		super();
		this.jobRepo = jobRepo;
	}


	// own JobWithCompanyDtoCreated

	private JobWithCompanyDTO convertToDto(Job job) {

		JobWithCompanyDTO dto = new JobWithCompanyDTO();
		dto.setJob(job);

		RestTemplate  restTemplate=new RestTemplate();

		try {
			Company company = restTemplate.getForObject(
					"http://companyms:8081/companies/getById/" + job.getCompanyId(),
					Company.class
			);

			dto.setCompany(company);

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());

			Company fallback = new Company();
			fallback.setId(job.getCompanyId());
			fallback.setName("Company not available");

			dto.setCompany(fallback);
		}

		return dto; // ✅ FIXED
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
	public List<JobWithCompanyDTO> findAll() {

		List<Job> jobs = jobRepo.findAll();

		return jobs.stream()
				.map(this::convertToDto)
				.toList();
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
