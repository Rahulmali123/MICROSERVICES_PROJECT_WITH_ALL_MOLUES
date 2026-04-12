package com.embarx.jobms.Job.impl;

import com.embarx.jobms.Job.Job;
import com.embarx.jobms.Job.JobRepo;
import com.embarx.jobms.Job.JobService;
import com.embarx.jobms.Job.dto.JobDTO;
import com.embarx.jobms.Job.external.Company;
import com.embarx.jobms.Job.external.Review;
import com.embarx.jobms.Job.mapper.JobMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;



@Service
public class JobServiceImpl implements JobService {

	JobRepo jobRepo;

	private final RestTemplate restTemplate;

	public JobServiceImpl(JobRepo jobRepo, RestTemplate restTemplate) {
		this.jobRepo = jobRepo;
		this.restTemplate = restTemplate;
	}


	// own JobWithCompanyDtoCreated

	private JobDTO convertToDto(Job job) {

		Company company;
		List<Review> reviews;

		try {
			// 🔹 COMPANY CALL
			company = restTemplate.getForObject(
					"http://companyms:8081/companies/getById/" + job.getCompanyId(),
					Company.class
			);

			// 🔹 REVIEW CALL
			ResponseEntity<List<Review>> reviewResponse =
					restTemplate.exchange(
							"http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
							HttpMethod.GET,
							null,
							new ParameterizedTypeReference<List<Review>>() {}
					);

			reviews = reviewResponse.getBody();

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());

			// 🔹 fallback company
			company = new Company();
			company.setId(job.getCompanyId());
			company.setName("Company not available");

			// 🔹 fallback reviews
			reviews = List.of();  // ✅ empty list (best practice)
		}

		// ✅ FINAL MAPPING
		return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
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
	public List<JobDTO> findAll() {

		List<Job> jobs = jobRepo.findAll();

		return jobs.stream()
				.map(this::convertToDto)
				.toList();
	}

	@Override
	public JobDTO findJobWithCompanyById(Long id) {

		Job job = jobRepo.findById(id).orElse(null);

		if (job == null) {
			return null;
		}

		return convertToDto(job);
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
